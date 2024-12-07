package vn.iotstar.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Order;
import vn.iotstar.enums.OrderStatus;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer>{
	Page<Order> findByUserIdAndOrderStatus(int id,OrderStatus status, Pageable page);
	int countByUserIdAndOrderStatus(int id,OrderStatus status);
	Page<Order> findAllByUserId(int id, Pageable page);

}
