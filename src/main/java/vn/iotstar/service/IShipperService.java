package vn.iotstar.service;

import org.springframework.stereotype.Service;

import vn.iotstar.entity.Shipper;
@Service
public interface IShipperService {

	Shipper findByPersonId(int id);

	Shipper findByAccount_AccountId(int accountId);

}
