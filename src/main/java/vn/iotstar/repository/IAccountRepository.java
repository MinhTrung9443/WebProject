package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Account;

@Repository
public interface IAccountRepository  extends JpaRepository<Account, Integer> {

	Account findByUsername(String username);

}
