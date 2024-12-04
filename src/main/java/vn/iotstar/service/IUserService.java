package vn.iotstar.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.iotstar.entity.Person;


@Service
public interface IUserService {

	Person findByAccountUsername(String username);

	<S extends Person> S save(S entity);

	Optional<Person> findById(Integer id);

}
