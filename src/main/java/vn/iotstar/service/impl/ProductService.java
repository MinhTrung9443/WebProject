package vn.iotstar.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	
}
