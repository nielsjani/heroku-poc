package flyway;

import org.flywaydb.core.Flyway;

public class Migrator {
    public static void main(String[] args) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(getDatasourceUrl(), getUsername(), getPassword());
//        flyway.baseline();
        flyway.migrate();
    }

    private static String getPassword() {
        return isLocal() ? "localdev" : System.getenv("SPRING_DATASOURCE_PASSWORD");
    }

    private static String getUsername() {
        return isLocal() ? "localdev" : System.getenv("JDBC_DATABASE_USERNAME");
    }

    private static String getDatasourceUrl() {
        return isLocal() ? "jdbc:postgresql://localhost:5432/postgres"
                : System.getenv("SPRING_DATASOURCE_URL");
    }

    private static boolean isLocal() {
        return System.getenv("HABIT_ENV").equals("dev");
    }
}
