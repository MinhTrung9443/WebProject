package vn.iotstar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.iotstar.entity.Product;


public interface IProductService {
	List<Product> getTop10BestSellingProducts();
}
