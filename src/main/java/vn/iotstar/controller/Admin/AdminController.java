package vn.iotstar.controller.Admin;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.iotstar.entity.Product;
import vn.iotstar.service.IProductService;

@Controller
@RequestMapping("/Admin")
public class AdminController {

	@Autowired
	private IProductService productService;

	@GetMapping("")
	public String home(Model model) {	
//		List<Product> topSellingProducts = productService.getTop10BestSellingProducts();
//        model.addAttribute("products", topSellingProducts);
		return "Admin/home";
	}

}
