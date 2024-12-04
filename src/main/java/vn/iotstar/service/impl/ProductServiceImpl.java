package vn.iotstar.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Product;
import vn.iotstar.repository.IProductRepository;
import vn.iotstar.service.IProductService;

@Service
public class ProductServiceImpl implements IProductService {
	@Autowired
	private IProductRepository productRepository;

	@Override
	public List<Product> getProductsByPriceRange(int minPrice, int maxPrice) {
		return productRepository.findByPriceRange(minPrice, maxPrice);
	}

	@Override
	public List<Product> getProductsByBrand(String brand) {
		return productRepository.findByBrandContaining(brand);
	}

	@Override
	public List<Product> getProductsByCategoryName(String categoryName) {
		return productRepository.findByCategoryName(categoryName);
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductsByBrandOrigin(String brandOrigin) {
		return productRepository.findByBrandOriginContaining(brandOrigin); // Tìm theo brandOrigin
	}

	@Override
	public List<Product> searchProducts(String query) {
		return productRepository.findByProductNameContainingIgnoreCase(query);
	}

	@Override
	public List<Product> searchProductsWithMultipleKeywords( Integer minPrice, Integer maxPrice,
			String brand, String brandOrigin, String categoryName) {
		return productRepository.searchProductsWithMultipleKeywords( minPrice, maxPrice, brand, brandOrigin,
				categoryName);
	}

	@Override
	public Product getProductById(int productId) {
		return productRepository.findById(productId).orElse(null);
	}

	@Override
	public List<Product> getProductsByName(String Name) {
		// TODO Auto-generated method stub
		return productRepository.findByProductNameContainingIgnoreCase(Name);
	}

}
