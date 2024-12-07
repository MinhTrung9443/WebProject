package vn.iotstar.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Person;
import vn.iotstar.entity.User;
import vn.iotstar.repository.IPersonRepository;
import vn.iotstar.repository.IUserRepository;
import vn.iotstar.service.IUserService;

@Service
public class UserService implements IUserService{
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IPersonRepository personRepository;
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	public <S extends Person> S save(S entity) {
		return personRepository.save(entity);
	}
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	public Optional<Person> findById(Integer id) {
		return personRepository.findById(id);
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
	@Override
	public Person findByAccountUsername(String username) {
		return personRepository.findByAccountUsername(username);
	}
}
