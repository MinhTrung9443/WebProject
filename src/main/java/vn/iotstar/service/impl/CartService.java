package vn.iotstar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.ShoppingCart;
import vn.iotstar.repository.IShoppingCartRepository;
import vn.iotstar.service.ICartService;

@Service
public class CartService implements ICartService{
	@Autowired
	IShoppingCartRepository cartRepository;

	@Override
	public <S extends ShoppingCart> S save(S entity) {
		return cartRepository.save(entity);
	}
	
	
}
