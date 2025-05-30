package dk.easv.belmanexam.repositories.utils;

import dk.easv.belmanexam.repositories.utils.mappers.BaseMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryBuilder<T> {
    private final Class<T> entityClass;
    private final String tableName;
    private final Map<String, Object> conditions = new HashMap<>();
    private final List<String> orderBy = new ArrayList<>();
    private final Map<String, Object> updates = new HashMap<>();
    private final List<JoinClause> joins = new ArrayList<>();
    private Integer limit;
    private Integer offset;
    private String operation = "SELECT";
    private BaseMapper<T> rowMapper;

    // Inner class to store join information
    private static class JoinClause {
        private final String joinType;
        private final String table;
        private final String onCondition;

        JoinClause(String joinType, String table, String onCondition) {
            this.joinType = joinType;
            this.table = table;
            this.onCondition = onCondition;
        }

        String toSQL() {
            return String.format("%s JOIN %s ON %s", joinType, table, onCondition);
        }
    }

    public QueryBuilder(Class<T> entityClass, String tableName) {
        this.entityClass = entityClass;
        this.tableName = tableName;
    }

    public QueryBuilder<T> where(String column, Object value) {
        conditions.put(column, value);
        return this;
    }

    public QueryBuilder<T> orderBy(String column, String direction) {
        orderBy.add(column + " " + direction);
        return this;
    }

    public QueryBuilder<T> limit(int limit) {
        this.limit = limit;
        return this;
    }

    public QueryBuilder<T> offset(int offset) {
        this.offset = offset;
        return this;
    }

    public QueryBuilder<T> withRowMapper(BaseMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
        return this;
    }

    public QueryBuilder<T> set(String column, Object value) {
        updates.put(column, value);
        return this;
    }

    // New join methods
    public QueryBuilder<T> innerJoin(String table, String onCondition) {
        joins.add(new JoinClause("INNER", table, onCondition));
        return this;
    }

    public QueryBuilder<T> leftJoin(String table, String onCondition) {
        joins.add(new JoinClause("LEFT", table, onCondition));
        return this;
    }

    public QueryBuilder<T> rightJoin(String table, String onCondition) {
        joins.add(new JoinClause("RIGHT", table, onCondition));
        return this;
    }

    public Collection<T> executeSelect(Connection conn) throws SQLException {
        if (rowMapper == null) {
            throw new IllegalStateException("Row mapper is required for SELECT queries");
        }

        StringBuilder sql = new StringBuilder("SELECT * FROM ").append(tableName);
        List<Object> parameters = new ArrayList<>();
        buildJoinClause(sql);
        buildWhereClause(sql, parameters);
        buildOrderByClause(sql);
        buildLimitOffsetClause(sql);

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                List<T> results = new ArrayList<>();
                while (rs.next()) {
                    results.add(rowMapper.mapRow(rs));
                }
                return results;
            }
        }
    }

    public void executeUpdate(Connection conn) throws SQLException {
        if (updates.isEmpty()) {
            throw new IllegalStateException("At least one update value must be specified");
        }

        StringBuilder sql = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
        List<Object> parameters = new ArrayList<>();

        // Build SET clause
        updates.forEach((key, value) -> {
            sql.append(key).append(" = ?, ");
            parameters.add(value);
        });
        sql.setLength(sql.length() - 2); // Remove last comma and space

        // Build JOIN clause for UPDATE
        buildJoinClause(sql);
        // Build WHERE clause
        buildWhereClause(sql, parameters);

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }
            stmt.executeUpdate();
        }
    }

    public void executeInsert(Connection conn) throws SQLException {
        if (updates.isEmpty()) {
            throw new IllegalStateException("At least one value must be specified for INSERT");
        }

        StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
        StringBuilder placeholders = new StringBuilder(" VALUES (");
        List<Object> parameters = new ArrayList<>();

        // Build columns and placeholders
        updates.forEach((key, value) -> {
            sql.append(key).append(", ");
            placeholders.append("?, ");
            parameters.add(value);
        });
        sql.setLength(sql.length() - 2); // Remove last comma and space
        placeholders.setLength(placeholders.length() - 2); // Remove last comma and space
        sql.append(")").append(placeholders).append(")");

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }
            stmt.executeUpdate();
        }
    }

    private void buildWhereClause(StringBuilder sql, List<Object> parameters) {
        if (!conditions.isEmpty()) {
            sql.append(" WHERE ");
            conditions.forEach((key, value) -> {
                sql.append(key).append(" = ? AND ");
                parameters.add(value);
            });
            sql.setLength(sql.length() - 5); // Remove last " AND "
        }
    }

    private void buildOrderByClause(StringBuilder sql) {
        if (!orderBy.isEmpty()) {
            sql.append(" ORDER BY ").append(String.join(", ", orderBy));
        }
    }

    private void buildLimitOffsetClause(StringBuilder sql) {
        if (limit != null) {
            sql.append(" LIMIT ").append(limit);
            if (offset != null) {
                sql.append(" OFFSET ").append(offset);
            }
        }
    }

    private void buildJoinClause(StringBuilder sql) {
        if (!joins.isEmpty()) {
            for (JoinClause join : joins) {
                sql.append(" ").append(join.toSQL());
            }
        }
    }
}