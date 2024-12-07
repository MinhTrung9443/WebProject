package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.OrderLine;
import vn.iotstar.enums.OrderStatus;

@Repository
public interface IOrderLineRepository extends JpaRepository<OrderLine, Integer>{




}
