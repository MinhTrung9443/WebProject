package vn.iotstar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

	// Lọc theo khoảng giá
	@Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
	List<Product> findByPriceRange(@Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice);

	// Tìm kiếm theo brand
	List<Product> findByBrandContaining(String brand);

	// Tìm kiếm theo category
	@Query("SELECT p FROM Product p WHERE p.category.categoryName LIKE %:categoryName%")
	List<Product> findByCategoryName(@Param("categoryName") String categoryName);

	// Tìm kiếm theo nguồn gốc (origin)
	List<Product> findByBrandOriginContaining(String brandOrigin);

	// Tìm sản phẩm theo tên, không phân biệt chữ hoa/thường
	List<Product> findByProductNameContainingIgnoreCase(String productName);

	@Query("SELECT p FROM Product p " + "WHERE (:minPrice IS NULL OR p.price >= :minPrice) "
			+ "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " + "AND (:brand IS NULL OR p.brand = :brand) "
			+ "AND (:brandOrigin IS NULL OR p.brandOrigin = :brandOrigin) "
			+ "AND (:categoryName IS NULL OR p.category.categoryName LIKE %:categoryName%)")
	List<Product> searchProductsWithMultipleKeywords(@Param("minPrice") Integer minPrice,
			@Param("maxPrice") Integer maxPrice, @Param("brand") String brand, @Param("brandOrigin") String brandOrigin,
			@Param("categoryName") String categoryName);

}
