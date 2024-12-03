package vn.iotstar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Product;




@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {
	List<Product> findTop20ByOrderByWarehouseDateFirstDesc();

	@Query("SELECT p FROM Product p " + "LEFT JOIN p.feedbacks f " + "GROUP BY p "
			+ "ORDER BY COALESCE(AVG(f.rating), 0) DESC")
	List<Product> findTop20ByAverageRating();

	@Query("SELECT p FROM Product p " + "LEFT JOIN p.favourite f " + "GROUP BY p " + "ORDER BY COUNT(f) DESC")
	List<Product> findTop20ByFavouriteCount();

	@Query("SELECT ol.product FROM OrderLine ol " + "GROUP BY ol.product " + "ORDER BY SUM(ol.quantity) DESC")
	List<Product> findTop20BySalesQuantity();
    Optional<Product> findById(Integer id);

    List<Product> findAll();
}
