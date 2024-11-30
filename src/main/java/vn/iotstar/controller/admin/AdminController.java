package vn.iotstar.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.iotstar.entity.Category;

@Controller
@RequestMapping("/Admin")
public class AdminController {
    @GetMapping("")
    public String home() {
        return "Admin/home";
    }
}
