package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.CartItem;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItem, Integer> {

}
