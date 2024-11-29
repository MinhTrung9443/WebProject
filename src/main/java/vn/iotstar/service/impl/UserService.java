package vn.iotstar.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.User;
import vn.iotstar.repository.IUserRepository;
import vn.iotstar.service.IUserService;

@Service
public class UserService implements IUserService{
	@Autowired
	private IUserRepository userrepo;

	@Override
	public User findByAccountUsername(String username) {
		return userrepo.findByAccountUsername(username);
	}

	@Override
	public <S extends User> S save(S entity) {
		return userrepo.save(entity);
	}

	@Override
	public Optional<User> findById(Integer id) {
		return userrepo.findById(id);
	}
	
	

}
