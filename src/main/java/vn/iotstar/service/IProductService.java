package vn.iotstar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Product;


@Service
public interface IProductService {

	List<Product> findTop20BySalesQuantity();

	List<Product> findTop20ByFavouriteCount();

	Page<Product> findTop20ByAverageRating();

	List<Product> findTop20ByOrderByWarehouseDateFirstDesc();

	Optional<Product> findById(Integer id);

	void decreaseProductStock(int productId);

	List<Product> findTop5ByFavouriteCount(long categoryId);

	<S extends Product> S save(S entity);

}
