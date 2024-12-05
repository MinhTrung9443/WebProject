package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Category;

@Repository
public interface ICategoryRepository extends JpaRepository<Category,Integer>{
	
	boolean existsByCategoryName(String categoryName);
}
