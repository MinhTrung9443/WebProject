package vn.iotstar.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.iotstar.entity.Order;
import vn.iotstar.enums.OrderStatus;
import vn.iotstar.repository.IOrderRepository;
@Service
public class OrderService {

    @Autowired
    private IOrderRepository orderRepository;

    public Page<Order> getOrdersPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable);
    }
    
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public long countOrdersByStatus(OrderStatus status) {
        return orderRepository.countByOrderStatus(status);
    }


    public Page<Order> getOrdersByStatus(OrderStatus status, Pageable pageable) {
        return orderRepository.findByOrderStatus(status, pageable);}
    
    @Transactional
    public boolean updateOrderStatus(int orderId, OrderStatus newStatus) {
        Optional<Order> orderOpt = orderRepository.findById(orderId); // Tìm đơn hàng theo ID
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            
            // Kiểm tra trạng thái hiện tại của đơn hàng và chỉ cho phép chuyển từ trạng thái hợp lệ
            OrderStatus currentStatus = order.getOrderStatus();

            // Chỉ cho phép chuyển trạng thái nếu trạng thái hiện tại hợp lệ
            switch (currentStatus) {
                case PENDING:
                    if (newStatus == OrderStatus.CONFIRMED || newStatus == OrderStatus.CANCELLED) {
                        order.setOrderStatus(newStatus);
                        orderRepository.save(order);
                        return true;
                    }
                    break;
                case CONFIRMED:
                    if (newStatus == OrderStatus.REFUNDED || newStatus == OrderStatus.CANCELLED) {
                        order.setOrderStatus(newStatus);
                        orderRepository.save(order);
                        return true;
                    }
                    break;
                case REFUNDED:
                case CANCELLED:
                    // Không cho phép thay đổi trạng thái nữa
                    return false;
                // Thêm các trạng thái khác nếu cần
                default:
                    return false;
            }
        }
        return false;
    }
}
