package vn.iotstar.service;

import java.util.List;

import vn.iotstar.entity.Order;
import vn.iotstar.enums.OrderStatus;

public interface IOrderService {
	List<Order> findByDeliveryIdAndStatus(int deliveryId, OrderStatus status);
	Order findById(int id);
    void save(Order order);
}
