package vn.iotstar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.iotstar.entity.Category;
@Service

public interface ICategoryService {
	List<Category> findById(int id);
}
