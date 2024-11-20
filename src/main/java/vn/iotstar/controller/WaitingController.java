package vn.iotstar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Account;

@Controller
public class WaitingController {

    @GetMapping("/waiting")
    public String waiting(HttpSession session, RedirectAttributes redirectAttributes) {
        if (session != null && session.getAttribute("account") != null) {
            Account account = (Account) session.getAttribute("account");

            // Lấy roleId từ đối tượng Role của Account
            int roleId = account.getRole().getRoleId();

            // Điều hướng người dùng theo roleId
            if (roleId == 1) {
                return "Admin/index";  // Điều hướng đến trang Admin
            } else if (roleId == 2) {
                return "User/index";  // Điều hướng đến trang User
            } else if (roleId == 3) {
                return "Vendor/index"; // Điều hướng đến trang Vendor
            } else if (roleId == 4) {
                return "Shipper/index"; // Điều hướng đến trang Shipper
            } else {
                return "Guest/index";  // Điều hướng đến trang Guest
            }
        } else {
            // Nếu không có session hoặc không tìm thấy tài khoản, điều hướng về trang login
            return "redirect:/login";
        }
    }
}
