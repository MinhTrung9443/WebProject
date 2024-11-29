package vn.iotstar.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.iotstar.entity.ShoppingCart;

@Service
public interface ICartService {

	<S extends ShoppingCart> S save(S entity);

	Optional<ShoppingCart> findByUserId(int id);
	
}
