package vn.iotstar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Address;
import vn.iotstar.entity.User;
import vn.iotstar.repository.IAddressRepository;
import vn.iotstar.repository.IUserRepository;
import vn.iotstar.service.IAddressService;
@Service
public class AddressServiceImpl implements IAddressService{
	@Autowired
	private IAddressRepository addressRepository;

	@Override
	public void save(Address user) {
		addressRepository.save(user);
	}
}
