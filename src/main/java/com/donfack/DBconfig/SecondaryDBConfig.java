package com.donfack.DBconfig;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
@EnableJpaRepositories(
        entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef = "secondaryTransactionManager",
        basePackages = {"com.donfack.employee.repository"})
public class SecondaryDBConfig {


    @Value("${spring.secondary.datasource.url}")
    private String url;

    @Value("${spring.secondary.datasource.username}")
    private String username;

    @Value("${spring.secondary.datasource.password}")
    private String password;


    @Bean(name = "secondaryDbDataSource")
    public DataSource secondaryDbDataSource(){
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    @Bean(name = "secondaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
            secondaryEntityManagerFactory(EntityManagerFactoryBuilder builder,
                  @Qualifier("secondaryDbDataSource")DataSource secondaryDataSource){
        return builder
                .dataSource(secondaryDataSource)
                .packages("com.donfack.employee.model")
                .build();
    }

    @Bean(name = "secondaryTransactionManager")
    public PlatformTransactionManager secondaryTransactionManager(
            @Qualifier("secondaryEntityManagerFactory") EntityManagerFactory
                    secondaryEntityManagerFactory){
        return new JpaTransactionManager(secondaryEntityManagerFactory);
    }


    /*@Bean(name = "employeeDataSource")
    @ConfigurationProperties(prefix = "employee.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }*/

    /*@Bean(name = "employeeEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean employeeEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("employeeDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.donfack.employee.model")
                .build();
    }

    @Bean(name = "employeeTransactionManager")
    public PlatformTransactionManager employeeTransactionManager(
            @Qualifier("employeeEntityManagerFactory") EntityManagerFactory employeeEntityManagerFactory) {
        return new JpaTransactionManager(employeeEntityManagerFactory);
    }*/





























}
