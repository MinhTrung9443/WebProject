package vn.iotstar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Account;
import vn.iotstar.entity.Order;
import vn.iotstar.enums.OrderStatus;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer>  {

    Page<Order> findByOrderStatus(OrderStatus status, Pageable pageable);

    long countByOrderStatus(OrderStatus status);
    
    Page<Order> findAll(Pageable pageable);
}
