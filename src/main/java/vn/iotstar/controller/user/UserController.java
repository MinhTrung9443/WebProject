package vn.iotstar.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Account;
import vn.iotstar.entity.CartItem;
import vn.iotstar.entity.Favourite;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.ShoppingCart;
import vn.iotstar.entity.User;
import vn.iotstar.service.ICartService;
import vn.iotstar.service.IFavouriteService;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.IUserService;

@Controller
@RequestMapping("/User")
public class UserController {
	@Autowired
	private IProductService productService;
	@Autowired
	private IUserService userService;
	@Autowired
	private ICartService cartService;
	@Autowired
	private IFavouriteService favouriteService;
	  @GetMapping("")
	    public String home(HttpSession session,ModelMap model) {
		  Account account = (Account) session.getAttribute("account");
			User user = userService.findByAccountUsername(account.getUsername());
			session.setAttribute("user", user);
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
		public String cart(HttpSession session,ModelMap model)
		{
			Account account = (Account) session.getAttribute("account");
			User user = userService.findByAccountUsername(account.getUsername());
			session.setAttribute("user", user);
			ShoppingCart cart = cartService.findByUserId(user.getId()).get();
			if (cart == null)
			{
				cart = new ShoppingCart();
				List<CartItem> cartItems = new ArrayList<>();
				cart.setItems(cartItems);
			}
			model.addAttribute("listCart", cart.getItems());
			return "User/cart";
		}
		@GetMapping("/wishlist")
		public String wishlist(HttpSession session,ModelMap model)
		{
			User user = (User) session.getAttribute("user");
			
			List<Favourite> listfavou = favouriteService.findAllByUserId(user.getId());

			model.addAttribute("listProduct", listfavou);
			
			return "User/wishlist";
		}
		@GetMapping("/dashboard")
		public String dashboard(HttpSession session,ModelMap model)
		{
			Account account = (Account) session.getAttribute("account");
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
		
		@GetMapping("/productDetail/{productId}")
		public String productDetail(HttpSession session,ModelMap model, @PathVariable("productId") int productId)
		{
			Optional<Product> opProduct = productService.findById(productId);
			
			if (opProduct.isPresent())
			{
				Product product = opProduct.get();
				model.addAttribute("product", product);
				return "/User/product";
			}
			return "redirect/User";
		}
}
