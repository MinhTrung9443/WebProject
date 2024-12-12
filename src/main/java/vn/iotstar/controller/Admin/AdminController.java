package vn.iotstar.controller.Admin;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.iotstar.service.IUserService;

@Controller
@RequestMapping("/Admin")
public class AdminController {

	@Autowired
	IUserService userService;

	@GetMapping("")
	public String home() {
		return "Admin/home";
	}

}
