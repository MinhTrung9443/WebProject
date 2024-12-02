package vn.iotstar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.Category;

public interface ICategoryService {

	long count();

	Optional<Category> findById(int id);

	List<Category> findAll();

	Page<Category> findAll(Pageable pageable);

	<S extends Category> S save(S entity);

	Page<Category> findByCategoryNameContaining(String categoryName, Pageable pageable);

	Optional<Category> findByCategoryName(String categoryName);

	void deleteById(Integer id);
	
	
	

}
