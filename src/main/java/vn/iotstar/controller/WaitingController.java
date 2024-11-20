package vn.iotstar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Account;

@Controller
public class WaitingController {

    @GetMapping("/waiting")
    public ModelAndView waiting(ModelMap model,HttpSession session, RedirectAttributes redirectAttributes) {
        if (session != null && session.getAttribute("account") != null) {
            Account account = (Account) session.getAttribute("account");

            // Lấy roleId từ đối tượng Role của Account
            int roleId = account.getRole().getRoleId();

            // Điều hướng người dùng theo roleId
            if (roleId == 1) {
            	return new ModelAndView("redirect:/Admin",model);
                // Điều hướng đến trang Admin
            } else if (roleId == 2) {
            	return new ModelAndView("redirect:/User",model);
                // Điều hướng đến trang User
            } else if (roleId == 3) {
            	return new ModelAndView("redirect:/Vendor",model); // Điều hướng đến trang Vendor
            } else if (roleId == 4) {
            	return new ModelAndView("redirect:/Shipper",model); // Điều hướng đến trang Shipper
            } else {
            	return new ModelAndView("redirect:/",model);  // Điều hướng đến trang Guest
            }
        } else {
            // Nếu không có session hoặc không tìm thấy tài khoản, điều hướng về trang login
        	return new ModelAndView("redirect:/login",model);
        }
    }
}
