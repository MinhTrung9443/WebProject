package vn.iotstar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Voucher;

@Repository
public interface IVoucherRepository  extends JpaRepository<Voucher, Integer>{
	List<Voucher> findByVoucherType(String voucherType);
}
