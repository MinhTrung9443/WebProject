package vn.iotstar.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Product;
import vn.iotstar.repository.IProductRepository;
import vn.iotstar.service.IProductService;

@Service
public class ProductServiceImpl implements IProductService {
	@Autowired
	private IProductRepository productRepository;

	@Override
	public Page<Product> getProductsByPriceRange(int minPrice, int maxPrice, Pageable pageable) {
		return productRepository.findByPriceRange(minPrice, maxPrice, pageable);
	}

	@Override
	public Page<Product> getProductsByBrand(String brand, Pageable pageable) {
		return productRepository.findByBrandContaining(brand, pageable);
	}

	@Override
	public Page<Product> getProductsByCategoryName(String categoryName, Pageable pageable) {
		return productRepository.findByCategoryName(categoryName, pageable);
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Page<Product> getProductsByBrandOrigin(String brandOrigin, Pageable pageable) {
		return productRepository.findByBrandOriginContaining(brandOrigin, pageable); // Tìm theo brandOrigin
	}

	@Override
	public List<Product> searchProducts(String query) {
		return productRepository.findByProductNameContainingIgnoreCase(query);
	}

	@Override
	public List<Product> searchProductsWithMultipleKeywords(Integer minPrice, Integer maxPrice, String brand,
			String brandOrigin, String categoryName) {
		return productRepository.searchProductsWithMultipleKeywords(minPrice, maxPrice, brand, brandOrigin,
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

	@Override
	public Page<Product> getAllProducts(Pageable pageable) {
		return productRepository.findAll(pageable); // Trả về tất cả sản phẩm phân trang
	}

	@Override
	public Page<Product> getProductsByName(String keyword, Pageable pageable) {
		return productRepository.findByProductNameContaining(keyword, pageable);
	}

	@Override
	public Page<Product> searchProductsWithMultipleKeywords(Integer minPrice, Integer maxPrice, String brand,
			String brandOrigin, String categoryName, Pageable pageable) {
		// Áp dụng các bộ lọc và phân trang
		return productRepository.findByFilters(minPrice, maxPrice, brand, brandOrigin, categoryName, pageable);
	}

	@Override
	public Page<Product> getProductsByCategoryName(String categoryName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> getProductsByBrand(String brand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> getProductsByPriceRange(int minPrice, int maxPrice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> getProductsByBrandOrigin(String brandOrigin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getTop10BestSellingProducts() {
		return productRepository.findTop10BestSellingProducts();
	}

}
