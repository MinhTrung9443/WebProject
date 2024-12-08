package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Shipper;

@Repository
public interface IShipperRepository extends JpaRepository<Shipper, Integer> {
    Shipper findByAccount_AccountId(int accountId);
}
