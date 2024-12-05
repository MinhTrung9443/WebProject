package vn.iotstar.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Product;
import vn.iotstar.repository.IProductRepository;
import vn.iotstar.service.IProductService;


@Service
public class ProductService implements IProductService{
	@Autowired
	private IProductRepository productrepo;

	@Override
	public List<Product> findTop20ByOrderByWarehouseDateFirstDesc() {
		return productrepo.findTop20ByOrderByWarehouseDateFirstDesc();
	}

	@Override
	public Page<Product> findTop20ByAverageRating() {
		Pageable page = PageRequest.of(0, 20);
		return productrepo.findTopProductsByAverageRating(page);
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
	
	@Override
	public void decreaseProductStock(int productId)
	{
		Product product = this.findById(productId).get();
		long stock = product.getStock();
		product.setStock(stock - 1);
		productrepo.save(product);
	}

	@Override
	public List<Product> findTop5ByFavouriteCount() {
		return productrepo.findTop5ByFavouriteCount();
	}
	
	
}
