package vn.iotstar.controller.User;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/User")
public class UserController {
	  @GetMapping("")
	    public String home() {
	        return "User/index";
	    }
	  
}
