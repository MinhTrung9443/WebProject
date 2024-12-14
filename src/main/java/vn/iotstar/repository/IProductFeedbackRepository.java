package vn.iotstar.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.ProductFeedback;

@Repository
public interface IProductFeedbackRepository extends JpaRepository<ProductFeedback, Integer>{
	List<ProductFeedback> findByProduct_ProductId(int productId);
	Page<ProductFeedback> findByProduct_ProductId(int productId, Pageable page);
	List<ProductFeedback> findByUserIdAndProduct_ProductId(int id, int productId);
	
	
	@Query("SELECT AVG(f.rating) FROM ProductFeedback f " +
	           "WHERE f.product.productId = :productId")
	Double findAverageRatingByProductId(@Param("productId") Long productId);
}
