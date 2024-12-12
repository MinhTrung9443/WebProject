package vn.iotstar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
	
	List<User> findByFullnameContaining(String fullname);
}
