package vn.iotstar.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Account;
import vn.iotstar.entity.User;
import vn.iotstar.entity.Vendor;
import vn.iotstar.service.ICartItemService;
import vn.iotstar.service.ICartService;
import vn.iotstar.service.IUserService;

@Controller
@RequestMapping("/User")
public class CheckOutController {
	@Autowired
	private ICartService cartService;
	@Autowired
	private ICartItemService cartItemService;
	@Autowired
	private IUserService userService;
	
	@GetMapping("/checkout")
	public String checkout(ModelMap model, HttpSession session)
	{
		Account acc = (Account) session.getAttribute("account");
		int roleid = acc.getRole().getRoleId();
		if (roleid == 2) {
			User customer = (User) userService.findByAccountUsername(acc.getUsername());
			session.setAttribute("user", customer);
		} else if (roleid == 3) {
			Vendor employee = (Vendor) userService.findByAccountUsername(acc.getUsername());
			session.setAttribute("user", employee);
		}
		return "/User/checkout";
	}
}
