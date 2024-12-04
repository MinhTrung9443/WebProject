package vn.iotstar.service;

import org.springframework.stereotype.Service;

import vn.iotstar.entity.Address;
@Service
public interface IAddressService {
	void save(Address address);
}
