package vn.iotstar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Shipper;
import vn.iotstar.repository.IShipperRepository;
import vn.iotstar.service.IShipperService;
@Service
public class ShipperService implements IShipperService{
	@Autowired
	private IShipperRepository shipperRepository;
	 
	@Override
	public Shipper findByPersonId(int id) {
		return shipperRepository.findById(id).get();
	}

	@Override
	public Shipper findByAccount_AccountId(int accountId) {
		return shipperRepository.findByAccount_AccountId(accountId);
	}
	
}
