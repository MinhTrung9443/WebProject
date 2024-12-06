package vn.iotstar.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Category;
import vn.iotstar.repository.IAccountRepository;
import vn.iotstar.repository.ICategoryRepository;
import vn.iotstar.service.IAccountService;
import vn.iotstar.service.ICategoryService;
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;{

}

	@Override
	public List<Category> findById(int id) {
		return categoryRepository.findByCategoryId(id);
	}
}