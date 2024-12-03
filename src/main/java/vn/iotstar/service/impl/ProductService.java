package vn.iotstar.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import vn.iotstar.dto.ProductRequestDTO;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.ProductImage;
import vn.iotstar.repository.ICategoryRepository;
import vn.iotstar.repository.IProductRepository;
import vn.iotstar.service.IProductService;

@Service
public class ProductService implements IProductService {
	@Autowired
	private IProductRepository productrepo;


    @Autowired
    private ICategoryRepository categoryRepository;

	@Override
	public List<Product> findTop20ByOrderByWarehouseDateFirstDesc() {
		return productrepo.findTop20ByOrderByWarehouseDateFirstDesc();
	}

	@Override
	public List<Product> findTop20ByAverageRating() {
		return productrepo.findTop20ByAverageRating();
	}

	@Override
	public List<Product> findTop20ByFavouriteCount() {
		return productrepo.findTop20ByFavouriteCount();
	}

	@Override
	public List<Product> findTop20BySalesQuantity() {
		return productrepo.findTop20BySalesQuantity();
	}

	@Override
	public Optional<Product> findById(Integer id) {
		return productrepo.findById(id);
	}

	public List<Product> findAll() {
		return productrepo.findAll();
	}

	public void updateProduct(Integer productId, ProductRequestDTO productRequestDTO) {
	    Optional<Product> optionalProduct = productrepo.findById(productId);
	    if (optionalProduct.isPresent()) {
	        Product product = optionalProduct.get();

	        // Cập nhật các thông tin sản phẩm
	        product.setProductName(productRequestDTO.getProductName());
	        product.setPrice(productRequestDTO.getPrice());
	        product.setDescription(productRequestDTO.getDescription());
	        product.setBrand(productRequestDTO.getBrand());
	        product.setExpirationDate(productRequestDTO.getExpirationDate());
	        product.setManufactureDate(productRequestDTO.getManufactureDate());
	        product.setIngredient(productRequestDTO.getIngredient());
	        product.setInstruction(productRequestDTO.getInstruction());
	        product.setVolumeOrWeight(productRequestDTO.getVolumeOrWeight());
	        product.setBrandOrigin(productRequestDTO.getBrandOrigin());
	        product.setStock(productRequestDTO.getStock());
	        product.setWarehouseDateFirst(productRequestDTO.getWarehouse_date_first());

	        // Cập nhật thể loại
	        Category category = new Category();
	        category.setCategoryId(productRequestDTO.getCategoryId());
	        product.setCategory(category);

	       

	        // Lưu sản phẩm sau khi cập nhật
	        productrepo.save(product);
	    }
	}

	public Product createProduct(ProductRequestDTO productRequestDTO) {
		Category category = categoryRepository.findById(productRequestDTO.getCategoryId())
	            .orElseThrow(() -> new RuntimeException("Category not found"));

		 if (category.getActive() != 1) {
		        throw new RuntimeException("Category is not active. Product cannot be created.");
		    }
		 
	    Product product = new Product();
	    product.setProductId(productRequestDTO.getProductId());
	    product.setProductName(productRequestDTO.getProductName());
	    product.setPrice(productRequestDTO.getPrice());
	    product.setDescription(productRequestDTO.getDescription());
	    product.setBrand(productRequestDTO.getBrand());
	    product.setStock(productRequestDTO.getStock());
	    product.setBrandOrigin(productRequestDTO.getBrandOrigin());
	    product.setIngredient(productRequestDTO.getIngredient());
	    product.setInstruction(productRequestDTO.getInstruction());
	    product.setManufactureDate(productRequestDTO.getManufactureDate());
	    product.setExpirationDate(productRequestDTO.getExpirationDate());
	    product.setWarehouseDateFirst(productRequestDTO.getWarehouse_date_first());
	    product.setVolumeOrWeight(productRequestDTO.getVolumeOrWeight());
	    product.setCategory(category);

	    List<ProductImage> images = new ArrayList<>();
	    for (String url : productRequestDTO.getImageUrls()) {
	        ProductImage image = new ProductImage();
	        image.setImageUrl(url.trim());
	        image.setProduct(product);
	        images.add(image);
	    }
	    product.setImages(images);

	    return productrepo.save(product);
    }


    
	@Transactional
	public void delete(Integer id) {
		productrepo.deleteById(id);
	}

	public List<String> getImageUrlsByProductId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateProductImages(Integer id, List<String> urlsToAdd) {
		// TODO Auto-generated method stub
		
	}



}
