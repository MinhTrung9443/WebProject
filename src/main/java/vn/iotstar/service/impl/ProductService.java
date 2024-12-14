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
	private IProductRepository productRepository;
	
	@Override
	public List<Product> getTop10BestSellingProducts() {
		return productRepository.findTop20BySalesQuantity();
	}

}
