package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.iotstar.entity.Account;

public interface IAccountRepository  extends JpaRepository<Account, Integer> {

	Account findByUsername(String username);

}
