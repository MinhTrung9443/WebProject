package vn.iotstar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import vn.iotstar.entity.User;

public interface IUserService {

	void delete(User entity);

	void deleteById(Integer id);

	long count();

	Optional<User> findById(Integer id);

	Page<User> findAll(Pageable pageable);

	<S extends User> S save(S entity);

	List<User> findAll();

}
