package vn.iotstar.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.*;
import vn.iotstar.service.*;

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
	@Autowired
	private IViewHistoryService viewService;

	@GetMapping("")
	public String home(HttpSession session, ModelMap model) {
		Account account = (Account) session.getAttribute("account");
		User user = userService.findByAccountUsername(account.getUsername());
		session.setAttribute("user", user);
//		  danh sách sản phẩm mới
		List<Product> productNew = productService.findTop20ByOrderByWarehouseDateFirstDesc();
//		  danh sách bán chạy
		  List<Product> productSale = productService.findTop20BySalesQuantity();
//		  danh sách đánh giá cao
		  Page<Product> productRate = productService.findTop20ByAverageRating();
//		  danh sách yêu thích
		  List<Product> productFavou = productService.findTop20ByFavouriteCount();
		model.addAttribute("productNew", productNew);
		  model.addAttribute("productSale",productSale);
		  model.addAttribute("productRate",productRate);
		  model.addAttribute("productFavou",productFavou);
		return "User/index";
	}

	@GetMapping("/Search")
	public String search() {
		return "User/category-list";
	}

	@GetMapping("/product")
	public String productDetail() {
		return "User/product";
	}

	@GetMapping("/cart")
	public String cart(HttpSession session, ModelMap model) {
		Account account = (Account) session.getAttribute("account");
		User user = userService.findByAccountUsername(account.getUsername());
		session.setAttribute("user", user);
		ShoppingCart cart = cartService.findByUserId(user.getId()).get();
		if (cart == null) {
			cart = new ShoppingCart();
			List<CartItem> cartItems = new ArrayList<>();
			cart.setItems(cartItems);
		}
		model.addAttribute("listCart", cart.getItems());
		return "User/cart";
	}

	@GetMapping({ "/wishlist" })
	public String wishlist(HttpSession session, ModelMap model,
			@RequestParam(value = "pageNo", defaultValue = "1") int pageNo) {
		int pageSize = 5;
		if (pageNo <= 0) {
			pageNo = 1;
		}
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		User user = (User) session.getAttribute("user");

		Page<Favourite> listfavou = favouriteService.findAllByUserId(user.getId(),pageable);

		model.addAttribute("listProduct", listfavou);
		model.addAttribute("currentPage", pageNo);
	    model.addAttribute("pageSize", pageSize);
	    model.addAttribute("totalItems", favouriteService.countAllByUserId(user.getId()));
	    model.addAttribute("totalPages", (int) Math.ceil((double) favouriteService.countAllByUserId(user.getId()) / pageSize));
		return "User/wishlist";
	}
	
	@GetMapping({ "/ViewHistory" })
	public String ViewHistory(HttpSession session, ModelMap model,
			@RequestParam(value = "pageNo", defaultValue = "1") int pageNo) {
		int pageSize = 5;
		if (pageNo <= 0) {
			pageNo = 1;
		}
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		User user = (User) session.getAttribute("user");

		Page<ViewHistory> listView = viewService.findAllByUserId(user.getId(),pageable);

		model.addAttribute("listProduct", listView);
		model.addAttribute("currentPage", pageNo);
	    model.addAttribute("pageSize", pageSize);
	    model.addAttribute("totalItems", viewService.countAllByUserId(user.getId()));
	    model.addAttribute("totalPages", (int) Math.ceil((double) viewService.countAllByUserId(user.getId()) / pageSize));
		return "User/ViewHistory";
	}
	
	
	@GetMapping("/dashboard")
	public String dashboard(HttpSession session, ModelMap model) {
		Account account = (Account) session.getAttribute("account");
		User user = userService.findByAccountUsername(account.getUsername());
		session.setAttribute("user", user);
		model.addAttribute("user", user);
		return "User/dashboard";
	}

	@GetMapping("/checkout")
	public String checkout() {
		return "User/checkout";
	}

	@GetMapping("/productDetail/{productId}")
	public String productDetail(HttpSession session, ModelMap model, @PathVariable("productId") int productId) {
		User user = (User) session.getAttribute("user");
		Optional<Product> opProduct = productService.findById(productId);

		if (opProduct.isPresent()) {
			Product product = opProduct.get();
			model.addAttribute("product", product);
			if (user != null) {
				ViewHistory newView = viewService.findByUserIdAndProduct_ProductId(user.getId(), productId);
				if (newView == null)
				{
					newView = new ViewHistory();
					newView.setProduct(product);
					newView.setUser(user);
					viewService.save(newView);
				}
			}
			return "/User/product";
		}
		return "redirect/User";
	}
}
