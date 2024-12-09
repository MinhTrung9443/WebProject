package vn.iotstar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Shipper;

import vn.iotstar.entity.Shipper;

@Repository
public interface IShipperRepository extends JpaRepository<Shipper, Integer> {
	Shipper findByPhone(String phone);

	Shipper findByEmail(String email);

	Optional<Shipper> findByFullnameContaining(String fullname);

	Shipper findByAccount_AccountId(int accountId);
}
