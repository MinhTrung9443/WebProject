package vn.iotstar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Product;


@Repository
public interface IProductRepository extends JpaRepository<Product, Long>{
	@Query("SELECT ol.product FROM OrderLine ol " + "GROUP BY ol.product " + "ORDER BY SUM(ol.quantity) DESC")
	List<Product> findTop20BySalesQuantity();
}
