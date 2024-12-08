package vn.iotstar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.iotstar.entity.Product;


@Service
public interface IProductService {

	List<Product> findTop20BySalesQuantity();

	List<Product> findTop20ByFavouriteCount();

	List<Product> findTop20ByAverageRating();

	List<Product> findTop20ByOrderByWarehouseDateFirstDesc();

	Optional<Product> findById(Integer id);

}
