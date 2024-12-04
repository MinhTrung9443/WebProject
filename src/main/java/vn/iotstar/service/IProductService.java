package vn.iotstar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.iotstar.entity.Product;
@Service
public interface IProductService {

	List<Product> getProductsByCategoryName(String categoryName);
	List<Product> getProductsByName(String Name);

	List<Product> getProductsByBrand(String brand);

	List<Product> getProductsByPriceRange(int minPrice, int maxPrice);

	List<Product> getAllProducts();

	List<Product> getProductsByBrandOrigin(String brandOrigin);

	List<Product> searchProducts(String query);

	List<Product> searchProductsWithMultipleKeywords(Integer minPrice, Integer maxPrice, String brand,
			String brandOrigin, String categoryName);

	Product getProductById(int productId);

}
