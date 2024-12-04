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
import vn.iotstar.enums.OrderStatus;
import vn.iotstar.repository.IOrderRepository;
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
	@Autowired
	private IOrderService orderService;

	@GetMapping("")
	public String home(HttpSession session, ModelMap model) {
		Account account = (Account) session.getAttribute("account");
		int roleid = account.getRole().getRoleId();
		if (roleid == 2) {
			User customer = (User) userService.findByAccountUsername(account.getUsername());
			session.setAttribute("user", customer);
		} else if (roleid == 3) {
			Vendor employee = (Vendor) userService.findByAccountUsername(account.getUsername());
			session.setAttribute("user", employee);
		}
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
		int roleid = account.getRole().getRoleId();
		int id=0;
		if (roleid == 2) {
			User customer = (User) userService.findByAccountUsername(account.getUsername());
			id = customer.getId();
			session.setAttribute("user", customer);
		} else if (roleid == 3) {
			Vendor employee = (Vendor) userService.findByAccountUsername(account.getUsername());
			id = employee.getId();
			session.setAttribute("user", employee);
		}
		
		ShoppingCart cart = cartService.findByUserId(id).get();
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

		Person person = (Person) session.getAttribute("user");
        
        int Id=0;
        if (person.getAccount().getRole().getRoleId() == 2)
        {
        	User u = (User) person;
        	Id = u.getId();
        }
        else if (person.getAccount().getRole().getRoleId() == 3) {
        	Vendor u = (Vendor) person;
        	Id = u.getId();
        }

		Page<Favourite> listfavou = favouriteService.findAllByUserId(Id,pageable);

		model.addAttribute("listProduct", listfavou);
		model.addAttribute("currentPage", pageNo);
	    model.addAttribute("pageSize", pageSize);
	    model.addAttribute("totalItems", favouriteService.countAllByUserId(Id));
	    model.addAttribute("totalPages", (int) Math.ceil((double) favouriteService.countAllByUserId(Id) / pageSize));
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

		Person person = (Person) session.getAttribute("user");
        
        int Id=0;
        if (person.getAccount().getRole().getRoleId() == 2)
        {
        	User u = (User) person;
        	Id = u.getId();
        }
        else if (person.getAccount().getRole().getRoleId() == 3) {
        	Vendor u = (Vendor) person;
        	Id = u.getId();
        }

		Page<ViewHistory> listView = viewService.findAllByUserId(Id,pageable);

		model.addAttribute("listProduct", listView);
		model.addAttribute("currentPage", pageNo);
	    model.addAttribute("pageSize", pageSize);
	    model.addAttribute("totalItems", viewService.countAllByUserId(Id));
	    model.addAttribute("totalPages", (int) Math.ceil((double) viewService.countAllByUserId(Id) / pageSize));
		return "User/ViewHistory";
	}
	@Autowired
	IOrderRepository repo;
	
	@GetMapping("/dashboard")
	public String dashboard(HttpSession session, ModelMap model) {
		Account account = (Account) session.getAttribute("account");
		int roleid = account.getRole().getRoleId();
		if (roleid == 2) {
			User customer = (User) userService.findByAccountUsername(account.getUsername());
			session.setAttribute("user", customer);
			model.addAttribute("user", customer);
		} else if (roleid == 3) {
			Vendor employee = (Vendor) userService.findByAccountUsername(account.getUsername());
			session.setAttribute("user", employee);
			model.addAttribute("user", employee);
		}
		
		return "User/dashboard";
	}

	@Autowired
	private IProductFeedbackService feedbackService;
	@GetMapping("/productDetail/{productId}")
	public String productDetail(HttpSession session, ModelMap model, @PathVariable("productId") int productId) {
		Person person = (Person) session.getAttribute("user");
        
        int Id=0;
        if (person.getAccount().getRole().getRoleId() == 2)
        {
        	User u = (User) person;
        	Id = u.getId();
        }
        else if (person.getAccount().getRole().getRoleId() == 3) {
        	Vendor u = (Vendor) person;
        	Id = u.getId();
        }
		Optional<Product> opProduct = productService.findById(productId);

		if (opProduct.isPresent()) {
			Product product = opProduct.get();
			List<ProductFeedback> feedback = feedbackService.findByProduct_ProductId(productId);
			model.addAttribute("product", product);
			model.addAttribute("feedback", feedback);


			ViewHistory newView = viewService.findByUserIdAndProduct_ProductId(Id, productId);
			if (newView == null)
			{
				newView = new ViewHistory();
				newView.setProduct(product);
				newView.setUser(person);
				viewService.save(newView);
			}
			
			return "/User/product";
		}
		return "redirect/User";
	}
}
