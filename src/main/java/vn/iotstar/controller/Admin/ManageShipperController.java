package vn.iotstar.controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Admin/shipper")
public class ManageShipperController {
	
	@GetMapping("")
	public String home(Model model) {
		return "Admin/shipper/list";
	}

}
