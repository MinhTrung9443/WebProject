package vn.iotstar.controller.Admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import vn.iotstar.entity.Account;
import vn.iotstar.entity.User;
import vn.iotstar.service.impl.UserService;

@Controller
@RequestMapping("/Admin/user")
public class ManageUserController {

	@Autowired
	UserService userService;

	@RequestMapping("")
	public String getUser(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
		
		int count = (int) userService.count();
		int currentPage = page.orElse(1);
		int pageSize=size.orElse(5);
		
		Pageable pageable = PageRequest.of(currentPage-1, pageSize);
		
		int totalPages = userService.findAll(pageable).getTotalPages();
		Page<User>list = userService.findAll(pageable);
		int start = Math.max(1, currentPage - 2);
		int end = Math.min(currentPage + 2, totalPages);
		
		if (totalPages > count) {
			if (end == totalPages)
				start = end - count;
			else if (start == 1) {
				end = start + count;
			}
		}
		
		List <Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
		
		model.addAttribute("list", list);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pageNumbers", pageNumbers);	
		return "Admin/user/list";
	}

	@PostMapping("/save")
	public ModelAndView save(ModelMap model, @Valid @ModelAttribute User user, BindingResult result, @Valid @ModelAttribute Account account) {
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return new ModelAndView("Admin/user/detail", model);
		}
		Optional<User> optUser = userService.findById(user.getId());
		
		if (optUser.isPresent()) {
			User existUser = optUser.get();
			Account existAccount = existUser.getAccount();
			
			if (account != null) {
				existAccount.setActive(account.getActive());
				existUser.setAccount(existAccount);
				userService.save(existUser);
			}
		}
		
		return new ModelAndView("forward:/Admin/user", model);
	}

	@GetMapping("/detail/{id}")
	public String viewUserDetail(@PathVariable("id") int id, Model model) {
		Optional<User> optUser = userService.findById(id);
		if (optUser.isPresent()) {
			User user = optUser.get();
			model.addAttribute("user", user);
			return "Admin/user/detail";
		} 
		return "Admin/user";
	}

}
