package vn.iotstar.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Account;
import vn.iotstar.entity.Order;
import vn.iotstar.enums.OrderStatus;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer>  {

	List<Order> findByDelivery_DeliveryIdAndOrderStatus(int deliveryId, OrderStatus orderStatus);
    Page<Order> findByOrderStatus(OrderStatus status, Pageable pageable);

    long countByOrderStatus(OrderStatus status);
    Optional<Order> findById(int id);
    Page<Order> findAll(Pageable pageable);
    List<Order> findByOrderStatus(OrderStatus orderStatus);
	List<Order> findByOrderStatusAndCompletionTimeBetween(OrderStatus completed, LocalDateTime startOfMonth,
			LocalDateTime endOfMonth);
	Page<Order> findByUserIdAndOrderStatus(int id,OrderStatus status, Pageable page);
	int countByUserIdAndOrderStatus(int id,OrderStatus status);
	Page<Order> findAllByUserId(int id, Pageable page);




}
