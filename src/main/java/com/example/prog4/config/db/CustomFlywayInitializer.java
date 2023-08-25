package com.example.prog4.config.db;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomFlywayInitializer {
  private final DataSource employeeDatasource;
  private final DataSource cnapsDatasource;

  public CustomFlywayInitializer(DataSource employeeDatasource,
                                 @Qualifier("cnapsDataSource") DataSource cnapsDatasource) {
    this.employeeDatasource = employeeDatasource;
    this.cnapsDatasource = cnapsDatasource;
  }

  @PostConstruct
  public void migrate() {
    migrateDataSource(employeeDatasource, "classpath:/db/migration/employee");
    migrateDataSource(cnapsDatasource, "classpath:/db/migration/cnaps");
  }

  private void migrateDataSource(DataSource dataSource, String... locations) {
    Flyway flyway = Flyway.configure()
        .dataSource(dataSource)
        .locations(locations).load();
    flyway.migrate();
  }
}
