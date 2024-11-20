package vn.iotstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Account;
import vn.iotstar.service.IAccountService;

@Controller
public class LoginController {

    @Autowired
    private IAccountService accountSer;

    // Hiển thị trang đăng nhập
    @GetMapping("/login")
    public String showLoginPage() {
        return "Guest/login"; // Trả về tên file `login.html` trong thư mục templates
    }

    // Xử lý form đăng nhập
    @PostMapping("/process-login")
    public String processLogin(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                HttpServletRequest request) {
        Account account = accountSer.login(username, password);
        System.out.println(username + " " + password);  // In ra để kiểm tra
        if (account != null && account.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("account", account);
            return "redirect:/waiting";
        } else {
            return "redirect:/login?error=true";
        }
    }

}
