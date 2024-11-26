package vn.iotstar.service;

import org.springframework.stereotype.Service;

import vn.iotstar.entity.Account;

@Service
public interface IAccountService {

	Account login(String username, String password);

	void saveRememberMe(String username, String password);

	void deleteRememberMe(String username);

}
