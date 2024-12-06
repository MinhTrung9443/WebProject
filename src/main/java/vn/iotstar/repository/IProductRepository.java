package vn.iotstar.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

	// Lọc theo khoảng giá
	@Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
	Page<Product> findByPriceRange(@Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice, Pageable pageable);

	// Tìm kiếm theo brand
	Page<Product> findByBrandContaining(String brand, Pageable pageable);

	// Tìm kiếm theo category
	@Query("SELECT p FROM Product p JOIN p.category c WHERE TRIM(c.categoryName) = :categoryName")
	Page<Product> findByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);

	// Tìm kiếm theo nguồn gốc (origin)
	Page<Product> findByBrandOriginContaining(String brandOrigin, Pageable pageable);

	// Tìm sản phẩm theo tên, không phân biệt chữ hoa/thường
	List<Product> findByProductNameContainingIgnoreCase(String productName);

	@Query("SELECT p FROM Product p " + "WHERE (:minPrice IS NULL OR p.price >= :minPrice) "
			+ "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " + "AND (:brand IS NULL OR p.brand = :brand) "
			+ "AND (:brandOrigin IS NULL OR p.brandOrigin = :brandOrigin) "
			+ "AND (:categoryName IS NULL OR p.category.categoryName LIKE %:categoryName%)")
	List<Product> searchProductsWithMultipleKeywords(@Param("minPrice") Integer minPrice,
			@Param("maxPrice") Integer maxPrice, @Param("brand") String brand, @Param("brandOrigin") String brandOrigin,
			@Param("categoryName") String categoryName);

	public Page<Product> findAll(Pageable pageable);

	public Page<Product> findByProductNameContaining(String keyword, Pageable pageable);

	 @Query("SELECT p FROM Product p " +
	           "JOIN p.category c " +
	           "WHERE (:minPrice IS NULL OR p.price >= :minPrice) " +
	           "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
	           "AND (:brandOrigin IS NULL OR p.brandOrigin LIKE CONCAT('%', :brandOrigin, '%')) " +
	           "AND (:brand IS NULL OR p.brand LIKE CONCAT('%', :brand, '%')) " +
	           "AND (:categoryName IS NULL OR c.categoryName LIKE CONCAT('%', :categoryName, '%'))")
	    Page<Product> findByFilters(@Param("minPrice") Integer minPrice,
	                                @Param("maxPrice") Integer maxPrice,
	                                @Param("brandOrigin") String brandOrigin,
	                                @Param("brand") String brand,
	                                @Param("categoryName") String categoryName,
	                                Pageable pageable);


	@Query(value = """
			    SELECT TOP 10  p.product_id,
			  p.product_name,
			  p.price,
			  p.images,
			  p.brand,
			  p.brand_origin,
			  p.expiration_date,
			  p.ingredient,
			  p.instruction,
			  p.manufacture_date,
			  p.stock,
			  p.volume_or_weight,
			  p.warehouse_date_first,
			  p.category_id,
			  p.image,
			  p.description, SUM(ol.quantity) AS total_quantity
			    FROM Product p
			    JOIN order_line ol ON p.product_id = ol.product_id
			    GROUP BY  p.product_id,
			  p.product_name,
			  p.price,
			  p.images,
			  p.brand,
			  p.brand_origin,
			  p.expiration_date,
			  p.ingredient,
			  p.instruction,
			  p.manufacture_date,
			  p.stock,
			  p.volume_or_weight,
			  p.warehouse_date_first,
			  p.category_id,
			  p.image,
			  p.description
			    ORDER BY total_quantity ASC
			""", nativeQuery = true)
	List<Product> findTop10BestSellingProducts();

}
