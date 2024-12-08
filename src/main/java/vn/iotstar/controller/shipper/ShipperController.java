package vn.iotstar.controller.shipper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Account;
import vn.iotstar.entity.Order;
import vn.iotstar.entity.OrderAssignment;
import vn.iotstar.entity.Shipper;
import vn.iotstar.enums.OrderStatus;
import vn.iotstar.service.IOrderService;
import vn.iotstar.service.IShipperService;
import vn.iotstar.service.impl.OrderAssignmentService;
import vn.iotstar.service.impl.OrderService;

@Controller
public class ShipperController {
	@Autowired
    private IShipperService shipperService;

    @PersistenceContext
    private EntityManager entityManager;
    
	@Autowired
    private OrderAssignmentService orderAssignmentService;
	
    @Autowired
    private IOrderService orderService;

    @GetMapping("/Shipper")
    public String home() {
        return "Shipper/index";
    }
    
    @GetMapping("/Shipper1")
    public String getCompletedOrders(Model model, HttpSession session) {
        // Lấy tài khoản từ session
        Account account = (Account) session.getAttribute("account");
        
        Shipper shipper = shipperService.findByAccount_AccountId(account.getAccountId());
        
        // Lấy thông tin shipper
        Shipper shipper1 = shipperService.findByPersonId(shipper.getId());
        if (shipper1 == null) {
            model.addAttribute("error", "Không tìm thấy thông tin Shipper.");
            return "error"; 
        }

        int deliveryId = shipper1.getDelivery().getDeliveryId();

        List<Order> completedOrders = orderService.findByDeliveryIdAndStatus(deliveryId, OrderStatus.CONFIRMED);

        // Truyền danh sách đơn hàng vào model
        model.addAttribute("completedOrders", completedOrders);

        return "Shipper/order/list";
    }
    @PostMapping("/Shipper/confirmDelivery")
    public String confirmDelivery(@RequestParam int orderId, HttpSession session) {
        // Cập nhật trạng thái đơn hàng
        Order order = orderService.findById(orderId);
        if (order != null) {
            order.setOrderStatus(OrderStatus.SHIPPING); // Cập nhật trạng thái đơn hàng
            orderService.save(order); // Lưu lại trạng thái mới
            
            // Lấy shipper từ session
            Account account = (Account) session.getAttribute("account");
            Shipper shipper = shipperService.findByAccount_AccountId(account.getAccountId());
            
            // Tạo mới bản ghi OrderAssignment
            OrderAssignment orderAssignment = new OrderAssignment();
            orderAssignment.setOrder(order);
            orderAssignment.setShipper(shipper);
            
            // Lưu OrderAssignment vào cơ sở dữ liệu
            orderAssignmentService.save(orderAssignment); // Đảm bảo bạn đã khai báo OrderAssignmentService
            
        }

        // Sau khi cập nhật, tải lại danh sách đơn hàng
        return "redirect:/Shipper1";
    }

    @GetMapping("/Shipper/delivered")
    public String getShipperConfirmedOrders(Model model, HttpSession session) {
        // Lấy tài khoản từ session
        Account account = (Account) session.getAttribute("account");
        
        // Lấy shipper từ tài khoản
        Shipper shipper = shipperService.findByAccount_AccountId(account.getAccountId());
        
        if (shipper == null) {
            model.addAttribute("error", "Không tìm thấy thông tin Shipper.");
            return "error"; 
        }

        int deliveryId = shipper.getDelivery().getDeliveryId();

        // Tạo một truy vấn JPQL trực tiếp để lấy đơn hàng có trạng thái là CANCELLED, SHIPPING, COMPLETED
        String jpql = "SELECT o FROM Order o WHERE o.delivery.deliveryId = :deliveryId AND o.orderStatus IN :statuses";
        List<OrderStatus> statuses = Arrays.asList(OrderStatus.CANCELLED, OrderStatus.SHIPPING, OrderStatus.COMPLETED);
        
        List<Order> confirmedOrders = entityManager.createQuery(jpql, Order.class)
                .setParameter("deliveryId", deliveryId)
                .setParameter("statuses", statuses)
                .getResultList();

        model.addAttribute("confirmedOrders", confirmedOrders);

        return "Shipper/order/shipperOrder"; // Chuyển đến view hiển thị danh sách đơn hàng đã xác nhận giao
    }


    @PostMapping("/Shipper/order/{orderId}/updateStatus")
    public String updateOrderStatus(@PathVariable int orderId, @RequestParam String status, HttpSession session, Model model) {
        // Lấy tài khoản từ session
        Account account = (Account) session.getAttribute("account");
        Shipper shipper = shipperService.findByAccount_AccountId(account.getAccountId());

        if (shipper == null) {
            model.addAttribute("error", "Không tìm thấy thông tin Shipper.");
            return "error"; 
        }

        Order order = orderService.findById(orderId);
        if (order == null) {
            model.addAttribute("error", "Đơn hàng không hợp lệ.");
            return "error";
        }

        // Kiểm tra quyền cập nhật trạng thái đơn hàng
        if (order.getDelivery().getDeliveryId() != shipper.getDelivery().getDeliveryId()) {
            model.addAttribute("error", "Bạn không có quyền cập nhật đơn hàng này.");
            return "error";
        }

        // Chỉ cho phép chuyển từ SHIPPING sang COMPLETED
        if ("COMPLETED".equals(status) && order.getOrderStatus().equals(OrderStatus.SHIPPING)) {
            order.setOrderStatus(OrderStatus.COMPLETED);
            order.setCompletionTime(LocalDateTime.now());
            orderService.save(order);
        }

        return "redirect:/Shipper/delivered"; // Quay lại trang danh sách đơn hàng sau khi cập nhật trạng thái
    }



}