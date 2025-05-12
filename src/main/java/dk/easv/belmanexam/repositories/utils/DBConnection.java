package dk.easv.belmanexam.repositories.utils;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.belmanexam.utils.Env;

import java.sql.Connection;

public class DBConnection implements AutoCloseable {
    public Connection getConnection() throws SQLServerException {
        SQLServerDataSource ds;
        ds = new SQLServerDataSource();
        ds.setDatabaseName(Env.get("DB_NAME"));
        ds.setUser(Env.get("DB_USER"));
        ds.setPassword(Env.get("DB_PASSWORD"));
        ds.setServerName(Env.get("DB_HOST"));
        ds.setPortNumber(Integer.parseInt(Env.get("DB_PORT", "1433")));
        ds.setTrustServerCertificate(true);
        return ds.getConnection();
    }
    @Override
    public void close() throws Exception {
    }
}
