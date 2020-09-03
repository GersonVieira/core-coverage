package db.migration;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Use this class for a Spring Jdbc migration.
 */
public class V1_1__Init_Migration implements SpringJdbcMigration { // NOSONAR
    public void migrate(JdbcTemplate jdbcTemplate) throws Exception {
        // Method for migrations
    }
}
