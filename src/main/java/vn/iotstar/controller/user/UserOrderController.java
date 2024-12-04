package vn.iotstar.controller.user;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Order;
import vn.iotstar.entity.Person;
import vn.iotstar.entity.User;
import vn.iotstar.entity.Vendor;
import vn.iotstar.enums.OrderStatus;
import vn.iotstar.service.IOrderService;


@Controller
@RequestMapping("/User/Order")
public class UserOrderController {
	@Autowired
	private IOrderService orderService;
	
	@GetMapping({"","/page"})
    public String getOrderHistory(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "tab", defaultValue = "don-cho-xac-nhan") String tab,
            Model model, HttpSession session) {

        Person user = (Person) session.getAttribute("user");
        if (user == null) {
            return "redirect:user/signin";
        }
        
        int customerId=0;
        if (user.getAccount().getRole().getRoleId() == 2)
        {
        	User u = (User) user;
        	 customerId = u.getId();
        }
        else if (user.getAccount().getRole().getRoleId() == 3) {
        	Vendor u = (Vendor) user;
        	 customerId = u.getId();
        }

        
        Page<Order> orders;
        int pageSize = 10;
        Pageable page = PageRequest.of(pageNo-1, pageSize);
        int count = 0;
        switch (tab) {
            case "don-cho-xac-nhan":
                orders = orderService.findByOrderStatus(customerId, OrderStatus.PENDING,page ); 
                count=orderService.countByOrderStatus(customerId, OrderStatus.PENDING);
                break;
            case "don-da-xac-nhan":
                orders = orderService.findByOrderStatus(customerId, OrderStatus.CONFIRMED,page ); 
                count=orderService.countByOrderStatus(customerId, OrderStatus.CONFIRMED);
                break;
            case "don-dang-van-chuyen":
                orders = orderService.findByOrderStatus(customerId, OrderStatus.SHIPPING,page ); 
                count=orderService.countByOrderStatus(customerId, OrderStatus.SHIPPING);
                break;
            case "don-da-giao":
                orders = orderService.findByOrderStatus(customerId, OrderStatus.COMPLETED,page ); 
                count=orderService.countByOrderStatus(customerId, OrderStatus.COMPLETED);
                break;
            case "don-huy":
                orders = orderService.findByOrderStatus(customerId, OrderStatus.CANCELLED,page );
                count=orderService.countByOrderStatus(customerId, OrderStatus.CANCELLED);
                break;
            case "don-hoan-tra":
            	orders =orderService.findByOrderStatus(customerId, OrderStatus.REFUNDED,page);
            	count=orderService.countByOrderStatus(customerId, OrderStatus.REFUNDED);
                break;
            default:
            	orders = orderService.findByOrderStatus(customerId, OrderStatus.PENDING,page ); 
                count=orderService.countByOrderStatus(customerId, OrderStatus.PENDING);
                break;
        }
        System.out.println(pageNo + tab);
        model.addAttribute("orders", orders);
        model.addAttribute("tab", tab);
        model.addAttribute("currentPage", pageNo);
	    model.addAttribute("pageSize", pageSize);
	    model.addAttribute("totalItems", count);
	    model.addAttribute("totalPages", (int) Math.ceil((double) count / pageSize));
        return "User/UserOrder";
    }
	@GetMapping("/detail/{orderId}")
	public String orderDetail(@PathVariable int orderId,ModelMap model) {
		Order order = orderService.findById(orderId).get();
		model.addAttribute("order",order);
		return "User/OrderDetail";
	}
	
}
