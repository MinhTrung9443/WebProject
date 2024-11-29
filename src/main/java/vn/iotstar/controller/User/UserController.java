package vn.iotstar.controller.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.iotstar.entity.Product;
import vn.iotstar.service.IProductService;

@Controller
@RequestMapping("/User")
public class UserController {
	@Autowired
	private IProductService productService;
	  @GetMapping("")
	    public String home() {
////		  danh sách sản phẩm mới
//		  List<Product> productNew = productService.findTop20ByOrderByWarehouseDateFirstDesc();
////		  danh sách bán chạy
//		  List<Product> productSale = productService.findTop20BySalesQuantity();
////		  danh sách đánh giá cao
//		  List<Product> productRate = productService.findTop20ByAverageRating();
////		  danh sách yêu thích
//		  List<Product> productFavou = productService.findTop20ByFavouriteCount();
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
		public String dashboard()
		{
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
