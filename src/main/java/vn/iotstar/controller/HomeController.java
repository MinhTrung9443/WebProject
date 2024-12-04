package vn.iotstar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.iotstar.entity.Product;
import vn.iotstar.service.IProductService;

@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	private IProductService productService; // Inject service lấy dữ liệu

	@GetMapping("/")
	public String showHomePage(Model model) {
		// Lấy tất cả sản phẩm từ database
		List<Product> products = productService.getAllProducts();

		// Truyền danh sách sản phẩm vào model để hiển thị trong view
		model.addAttribute("products", products);

		// Trả về tên của trang HTML để Thymeleaf render (ví dụ: guest/index.html)
		return "Guest/index";
	}
}
