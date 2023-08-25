package com.example.prog4.config.db;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.example.prog4.repository.cnaps",
    entityManagerFactoryRef = "cnapsEntityManagerFactory",
    transactionManagerRef = "cnapsTransactionManager"
)
public class CnapsDbConfig {
  @Bean(name = "cnapsDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.db2")
  @FlywayDataSource
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "cnapsEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      EntityManagerFactoryBuilder builder,
      @Qualifier("cnapsDataSource") DataSource dataSource) {
    return builder
        .dataSource(dataSource)
        .packages("com.example.prog4.repository.cnaps")
        .persistenceUnit("db2")
        .build();
  }

  @Bean(name = "cnapsTransactionManager")
  public PlatformTransactionManager transactionManager(
      @Qualifier("cnapsEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
