package vn.iotstar.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Order;
import vn.iotstar.enums.OrderStatus;
import vn.iotstar.repository.IOrderRepository;
import vn.iotstar.service.IOrderService;

@Service
public class OrderService implements IOrderService{
	@Autowired
	IOrderRepository orderRepository;

	@Override
	public Page<Order> findByOrderStatus(int id, OrderStatus status, Pageable page) {
		return orderRepository.findByUserIdAndOrderStatus(id,status, page);
	}

	@Override
	public int countByOrderStatus(int id,OrderStatus status) {
		return orderRepository.countByUserIdAndOrderStatus(id,status);
	}

	@Override
	public Page<Order> findAllByUserId(int id, Pageable page) {
		return orderRepository.findAllByUserId(id, page);
	}

	@Override
	public Optional<Order> findById(Integer id) {
		return orderRepository.findById(id);
	}


	
}
