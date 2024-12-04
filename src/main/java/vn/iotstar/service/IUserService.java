package vn.iotstar.service;

import org.springframework.stereotype.Service;

import vn.iotstar.entity.User;
@Service
public interface IUserService {

	void save(User user);

}
