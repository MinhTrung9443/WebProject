package vn.iotstar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Category;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer> {
	 List<Category> findByActive(int active);
}
