package vn.iotstar.controller.Admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.iotstar.entity.User;
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
	
	
    @GetMapping("/search")
	public String search(Model model, @RequestParam(value = "fullname", required = false) String fullname) {
    	
    	List<User> list = new ArrayList<>();
		if (StringUtils.hasText(fullname)) {
			Optional<User> user = userService.findByFullnameContaining(fullname);
			if (user.isPresent()) {
				list = List.of(user.get());
			}
			else {
				model.addAttribute("message", "KHÔNG CÓ KẾT QUẢ NÀO ĐƯỢC TÌM THẤY");
			}		
		}
		model.addAttribute("list",list);
		return "Admin/search";
	}

}
