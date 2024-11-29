package vn.iotstar.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.ShoppingCart;
import vn.iotstar.repository.IShoppingCartRepository;
import vn.iotstar.service.ICartService;

@Service
public class CartService implements ICartService {
	@Autowired
	private IShoppingCartRepository cartRepository;

	@Override
	public <S extends ShoppingCart> S save(S entity) {
		return cartRepository.save(entity);
	}

	@Override
	public Optional<ShoppingCart> findByUserId(int id) {
		return cartRepository.findByUserId(id);
	}
	
	
}
