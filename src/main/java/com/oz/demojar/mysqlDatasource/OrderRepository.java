package com.oz.demojar.mysqlDatasource;

import com.oz.demojar.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called MysqlDataSource
// CRUD refers Create, Read, Update, Delete

@EnableJpaRepositories(basePackages = "com.oz.demojar.dao")
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findById(Long id);

    @Query(value = "SELECT MAX(id) FROM person.order", nativeQuery = true)
    long findLastId();
}
