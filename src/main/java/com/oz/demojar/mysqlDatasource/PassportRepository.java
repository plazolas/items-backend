package com.oz.demojar.mysqlDatasource;

import com.oz.demojar.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// This will be AUTO IMPLEMENTED by Spring into a Bean called MysqlDataSource
// CRUD refers Create, Read, Update, Delete

@EnableJpaRepositories(basePackages = "com.oz.demojar.dao")
public interface PassportRepository extends JpaRepository<Passport, Long> {
    @Query(value = "SELECT MAX(id) FROM person.password", nativeQuery = true)
    long findLastId();

}
