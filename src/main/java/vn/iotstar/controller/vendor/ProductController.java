package vn.iotstar.controller.vendor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import vn.iotstar.dto.ProductRequestDTO;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.ProductImage;
import vn.iotstar.service.impl.CategoryService;
import vn.iotstar.service.impl.ProductImageService;
import vn.iotstar.service.impl.ProductService;

@Controller
@RequestMapping("/Vendor/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    
    @GetMapping("")
    public String listProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);  
        return "Vendor/product_list"; 
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {       
    	List<Category> activeCategories = categoryService.getActiveCategories();
        model.addAttribute("productRequestDTO", new ProductRequestDTO());
        model.addAttribute("categories", activeCategories);
        return "Vendor/product/create"; // Tên file Thymeleaf
    }

    @PostMapping("/create")
    public String createProduct(ProductRequestDTO productRequestDTO, Model model) {
        productService.createProduct(productRequestDTO);
        return "redirect:/Vendor/products"; // Chuyển hướng đến danh sách sản phẩm sau khi thêm
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Optional<Product> productOptional = productService.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductRequestDTO productRequestDTO = new ProductRequestDTO();
            productRequestDTO.setProductName(product.getProductName());
            productRequestDTO.setPrice(product.getPrice());
            productRequestDTO.setDescription(product.getDescription());
            productRequestDTO.setBrand(product.getBrand());
            productRequestDTO.setExpirationDate(product.getExpirationDate());
            productRequestDTO.setManufactureDate(product.getManufactureDate());
            productRequestDTO.setIngredient(product.getIngredient());
            productRequestDTO.setInstruction(product.getInstruction());
            productRequestDTO.setVolumeOrWeight(product.getVolumeOrWeight());
            productRequestDTO.setBrandOrigin(product.getBrandOrigin());
            productRequestDTO.setStock(product.getStock());
            productRequestDTO.setWarehouse_date_first(product.getWarehouseDateFirst());
            productRequestDTO.setCategoryId(product.getCategory().getCategoryId()); // Lấy ID thể loại
            // Chuyển danh sách ảnh sang URL
           


            model.addAttribute("productRequestDTO", productRequestDTO);
            model.addAttribute("categories", categoryService.getActiveCategories());
            return "Vendor/product/edit"; // Tên file Thymeleaf
        }
        return "redirect:/Vendor/products"; // Nếu không tìm thấy sản phẩm
    }
    
    
    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Integer id, @ModelAttribute ProductRequestDTO productRequestDTO) {
        try {
            productService.updateProduct(id, productRequestDTO);
            return "redirect:/Vendor/products";  // Chuyển hướng đến trang danh sách sản phẩm sau khi cập nhật thành công
        } catch (Exception e) {
            e.printStackTrace();
            return "Vendor/product/edit";  // Quay lại trang chỉnh sửa nếu có lỗi
        }
    }
    
    
    
    
    @GetMapping("/delete")
    public String deleteProduct(@RequestParam Integer id) {
        productService.delete(id); 
        return "redirect:/Vendor/products"; 
    }



}

