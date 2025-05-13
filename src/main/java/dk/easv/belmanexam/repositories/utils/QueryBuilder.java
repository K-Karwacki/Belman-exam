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
    private Integer limit;
    private Integer offset;
    private String operation = "SELECT";
    private BaseMapper<T> rowMapper;

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

    public Collection<T> executeSelect(Connection conn) throws SQLException {
        if (rowMapper == null) {
            throw new IllegalStateException("Row mapper is required for SELECT queries");
        }

        StringBuilder sql = new StringBuilder("SELECT * FROM ").append(tableName);
        List<Object> parameters = new ArrayList<>();
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

    private void buildWhereClause(StringBuilder sql, List<Object> parameters) {
        if (!conditions.isEmpty()) {
            sql.append(" WHERE ");
            conditions.forEach((key, value) -> {
                sql.append(key).append(" = ? AND ");
                parameters.add(value);
            });
            sql.setLength(sql.length() - 5);
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
}