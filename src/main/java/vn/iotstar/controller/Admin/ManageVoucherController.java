package vn.iotstar.controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Admin/voucher")
public class ManageVoucherController {
	
	@GetMapping("")
	public String home() {
		return "Admin/voucher/list";
	}

}
