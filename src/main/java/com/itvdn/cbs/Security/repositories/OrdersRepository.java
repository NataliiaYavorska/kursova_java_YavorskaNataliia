package com.itvdn.cbs.Security.repositories;

import com.itvdn.cbs.Security.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    Optional<Orders> findById(int id);
    List<Orders> findByPersonId(int personId);
    List<Orders> findByBooksIdAndStatus(int Id, int status);
    List<Orders> findByPersonIdAndStatus(int Id, int status);
    List<Orders> findByStatus(int status);
}
