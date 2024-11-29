package vn.iotstar.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.CartItem;
import vn.iotstar.repository.ICartItemRepository;
import vn.iotstar.service.ICartItemService;

@Service
public class CartItemService implements ICartItemService{
	@Autowired
	ICartItemRepository cartRepository;

	@Override
	public <S extends CartItem> S save(S entity) {
		return cartRepository.save(entity);
	}

	@Override
	public Optional<CartItem> findByCart_CartIdAndProduct_ProductId(int cartId, int productId) {
		return cartRepository.findByCart_ShoppingCartIdAndProduct_ProductId(cartId, productId);
	}
	
	
}	
