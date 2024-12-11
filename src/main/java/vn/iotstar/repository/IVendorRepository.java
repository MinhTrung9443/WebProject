package vn.iotstar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Vendor;

@Repository
public interface IVendorRepository extends JpaRepository<Vendor, Integer>{
	
	Vendor findByEmail(String email);
	
	Vendor findByPhone(String phone);
	
	Optional <Vendor> findByFullnameContaining(String fullname);

}