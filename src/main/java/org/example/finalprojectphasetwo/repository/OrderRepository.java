package org.example.finalprojectphasetwo.repository;

import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("FROM Order o JOIN o.subService s JOIN s.specialists sp WHERE sp = :specialist")
    List<Order> findOrdersBySpecialist(Specialist specialist);

}