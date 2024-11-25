package vn.iotstar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class RegisterController {
	 // Hiển thị trang đăng nhập
    @GetMapping("/register")
    public String showLoginPage() {
        return "Guest/register"; // Trả về tên file `register.html` trong thư mục templates
    }
}
