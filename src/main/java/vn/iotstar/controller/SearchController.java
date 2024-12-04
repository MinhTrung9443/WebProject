package vn.iotstar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import vn.iotstar.entity.Product;
import vn.iotstar.entity.ProductFeedback;
import vn.iotstar.service.IProductService;

@Controller
public class SearchController {

	@Autowired
	private IProductService productService;

	// Lọc sản phẩm theo các điều kiện
	@GetMapping("/search")
	public String listProducts(@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "minPrice", required = false) Integer minPrice,
			@RequestParam(value = "maxPrice", required = false) Integer maxPrice,
			@RequestParam(value = "brand", required = false) String brand,
			@RequestParam(value = "category_name", required = false) String categoryName,
			@RequestParam(value = "brandOrigin", required = false) String brandOrigin, Model model) {
		List<Product> products;
		if (keyword != null) {
			products=productService.getProductsByName(keyword);
		}else {
		
		if (minPrice == null && maxPrice == null && (brand == null  || brand == "" )&& (brandOrigin == null || brandOrigin== "" ) && (categoryName == null|| categoryName== "" )) {
			// If no filters are applied, fetch all products
			products = productService.getAllProducts();
		} else {
			if (minPrice!=null &&  maxPrice != null && (brand == null  || brand == "" )&& (brandOrigin == null || brandOrigin== "" ) && (categoryName == null|| categoryName== "" ))
			{
				products = productService.getProductsByPriceRange(minPrice, maxPrice);
			}
			else
				if(minPrice == null && maxPrice == null && brand!= null && (brandOrigin == null || brandOrigin== "" ) && (categoryName == null|| categoryName== "" ))
			{
				products = productService.getProductsByBrand(brand);
			}
				else 
					if (minPrice == null && maxPrice == null && brand!= null && brandOrigin!= null && (categoryName == null|| categoryName== "" ))
					{
						products = productService.getProductsByBrandOrigin(brandOrigin);
					}
					else if(minPrice == null && maxPrice == null && (brand == null  || brand == "" )&& (brandOrigin == null || brandOrigin== "" ) && categoryName != null)
					{
						products = productService.getProductsByCategoryName(categoryName);
					}
					else {
			// Apply filters if any are provided
			products = productService.searchProductsWithMultipleKeywords(minPrice, maxPrice, brand,
					brandOrigin, categoryName);
			System.out.println(minPrice +" 1 "+ maxPrice+" 2 "+ brand+" 3 " +brandOrigin+" 4 "+ categoryName);
		}
		}}
		

		model.addAttribute("products", products);
		model.addAttribute("minPrice", minPrice);
		model.addAttribute("maxPrice", maxPrice);
		model.addAttribute("brand", brand);
		model.addAttribute("category_name", categoryName);
		model.addAttribute("brandOrigin", brandOrigin);
		

		return "/product-search";
	}

	// Xử lý yêu cầu hiển thị chi tiết sản phẩm
	@GetMapping("/product-details/{productId}")
	public String showProductDetails(@PathVariable("productId") int productId, Model model) {
		// Lấy sản phẩm theo productId
		Product product = productService.getProductById(productId);
		List<ProductFeedback> feedbacks = product.getFeedbacks();
		// Nếu không tìm thấy sản phẩm, chuyển đến trang lỗi
		if (product == null) {
			return "error"; // Có thể bạn cần tạo một trang error.html để xử lý
		}

		// Thêm sản phẩm vào mô hình (model)
		model.addAttribute("product", product);
		model.addAttribute("feedbacks", feedbacks);
		// Trả về trang chi tiết sản phẩm
		return "product-details"; // Tên của trang HTML chi tiết sản phẩm (product-details.html)
	}
}
