package vn.iotstar.service;

import vn.iotstar.entity.Account;

public interface IAccountService {

	Account login(String username, String password);

	void saveRememberMe(String username, String password);

	void deleteRememberMe(String username);

}
