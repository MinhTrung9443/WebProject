package vn.iotstar.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.iotstar.entity.User;

@Service
public interface IUserService {

	User findByAccountUsername(String username);

	<S extends User> S save(S entity);

	Optional<User> findById(Integer id);

}
