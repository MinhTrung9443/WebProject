package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Account;
import vn.iotstar.entity.Person;

@Repository
public interface IPersonRepository  extends JpaRepository<Person, Integer>{
	Person findByEmail(String email);
	Person findByPhone(String phone);

}
