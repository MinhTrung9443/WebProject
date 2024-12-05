package vn.iotstar.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Product;




@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {
	List<Product> findTop20ByOrderByWarehouseDateFirstDesc();

	@Query("SELECT p FROM Product p " +
		       "LEFT JOIN p.feedbacks f " +
		       "GROUP BY p " +
		       "ORDER BY COALESCE(AVG(f.rating), 0) DESC")
	Page<Product> findTopProductsByAverageRating(Pageable pageable);

	@Query("SELECT p FROM Product p " + "LEFT JOIN p.favourite f " + "GROUP BY p " + "ORDER BY COUNT(f) DESC")
	List<Product> findTop20ByFavouriteCount();

	@Query("SELECT ol.product FROM OrderLine ol " + "GROUP BY ol.product " + "ORDER BY SUM(ol.quantity) DESC")
	List<Product> findTop20BySalesQuantity();
	
	@Query("SELECT p FROM Product p " + "LEFT JOIN p.favourite f " + "GROUP BY p " + "ORDER BY COUNT(f) DESC")
	List<Product> findTop5ByFavouriteCount();
}
