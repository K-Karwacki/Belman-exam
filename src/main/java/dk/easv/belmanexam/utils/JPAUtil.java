package dk.easv.belmanexam.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class JPAUtil {

    private static final EntityManagerFactory entityManagerFactory;

    static {

        // Set up properties for Hibernate
        Map<String, String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.url", Env.get("DB_URL"));
        properties.put("jakarta.persistence.jdbc.user", Env.get("DB_USER"));
        properties.put("jakarta.persistence.jdbc.password", Env.get("DB_PASSWORD"));

        // Create the EntityManagerFactory
        entityManagerFactory = Persistence.createEntityManagerFactory("MSSQL_PU", properties);
    }

    private JPAUtil() {}

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
