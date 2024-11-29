package vn.iotstar.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import vn.iotstar.entity.ProductFeedback;
import vn.iotstar.entity.User;
import vn.iotstar.service.impl.UserService;

@Controller
@RequestMapping("/Admin/user")
public class ManageUserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("")
	public String getUser(Model model) {
		List<User> list = userService.findAll();
		model.addAttribute("list",list);
		return "Admin/user/list";
	}
	
	@PostMapping("/save")
	public ModelAndView saveOrUpdate(ModelMap model, 
			@Valid @ModelAttribute("user") User user, BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("forward:/Admin/user",model);
		}
		User entity = new User();
		BeanUtils.copyProperties(user, entity);
		userService.save(entity);
		return new ModelAndView("forward:/Admin/user",model);
	}
	
	@GetMapping("/detail/{id}")
	public String viewUserDetail(@PathVariable("id") int id, Model model) {
	    Optional<User> optUser = userService.findById(id);
	    User entity = new User();
	    if (optUser.isPresent()) {
	        User user = optUser.get(); // Lấy đối tượng User từ Optional
	        BeanUtils.copyProperties(user, entity);
	        model.addAttribute("user", user);

	        return "Admin/user/detail"; // Trả về tên template
	    } else {
	        // Xử lý nếu không tìm thấy user
	        return "redirect:/error-page"; // Hoặc trang thông báo lỗi tùy ý
	    }
	}


}
