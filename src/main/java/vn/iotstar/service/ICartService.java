package vn.iotstar.service;

import org.springframework.stereotype.Service;

import vn.iotstar.entity.ShoppingCart;

@Service
public interface ICartService {

	<S extends ShoppingCart> S save(S entity);

}
