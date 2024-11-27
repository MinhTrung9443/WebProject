package vn.iotstar.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Account;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.User;
import vn.iotstar.service.IAccountService;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.IUserService;

@Controller
@RequestMapping("/User")
public class UserController {
	@Autowired
	private IProductService productService;
	@Autowired
	private IUserService userService;
	  @GetMapping("")
	    public String home(ModelMap model) {
//		  danh sách sản phẩm mới
		  List<Product> productNew = productService.findTop20ByOrderByWarehouseDateFirstDesc();
////		  danh sách bán chạy
//		  List<Product> productSale = productService.findTop20BySalesQuantity();
////		  danh sách đánh giá cao
//		  List<Product> productRate = productService.findTop20ByAverageRating();
////		  danh sách yêu thích
//		  List<Product> productFavou = productService.findTop20ByFavouriteCount();
		  model.addAttribute("productNew",productNew);
//		  model.addAttribute("productSale",productSale);
//		  model.addAttribute("productRate",productRate);
//		  model.addAttribute("productFavou",productFavou);
	        return "User/index";
	    }
	  @GetMapping("/Search")
	    public String search() {
	        return "User/category-list";
	    }
		@GetMapping("/product")
		public String productDetail()
		{
			return "User/product";
		}
		@GetMapping("/cart")
		public String cart()
		{
			return "User/cart";
		}
		@GetMapping("/wishlist")
		public String wishlist()
		{
			return "User/wishlist";
		}
		@GetMapping("/dashboard")
		public String dashboard(HttpSession session,ModelMap model)
		{
			Account account =(Account) session.getAttribute("account");
			User user = userService.findByAccountUsername(account.getUsername());
			session.setAttribute("user", user);
			model.addAttribute("user", user);
			return "User/dashboard";
		}
		@GetMapping("/checkout")
		public String checkout()
		{
			return "User/checkout";
		}
}
