package vn.iotstar.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Category;
import vn.iotstar.repository.ICategoryRepository;
import vn.iotstar.service.ICategoryService;

@Service
public class CategoryService implements ICategoryService{
	
	private ICategoryRepository cateRepository;
	
	@Autowired
	public CategoryService(ICategoryRepository cateRepository) {
		this.cateRepository = cateRepository;
	}

	@Override
	public long count() {
		return cateRepository.count();
	}

	@Override
	public Optional<Category> findById(int id) {
		return cateRepository.findById(id);
	}

	@Override
	public List<Category> findAll() {
		return cateRepository.findAll();
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		return cateRepository.findAll(pageable);
	}

	@Override
	public <S extends Category> S save(S entity) {
		return cateRepository.save(entity);
	}

	@Override
	public boolean existsByCategoryName(String categoryName) {
		return cateRepository.existsByCategoryName(categoryName);
	}

	@Override
	public void deleteById(Integer id) {
		cateRepository.deleteById(id);
	}
	
	
	

}
