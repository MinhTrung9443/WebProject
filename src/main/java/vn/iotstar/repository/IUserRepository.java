package vn.iotstar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByFullnameContaining(String fullname);
}
