package vn.iotstar.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.User;
import vn.iotstar.repository.IUserRepository;
import vn.iotstar.service.IUserService;

@Service
public class UserService implements IUserService{

	private IUserRepository userRepository;
	
	@Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	public <S extends User> S save(S entity) {
		return userRepository.save(entity);
	}
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	public Optional<User> findById(Integer id) {
		return userRepository.findById(id);
	}
	public long count() {
		return userRepository.count();
	}
	public void deleteById(Integer id) {
		userRepository.deleteById(id);
	}
	public void delete(User entity) {
		userRepository.delete(entity);
	}

}
