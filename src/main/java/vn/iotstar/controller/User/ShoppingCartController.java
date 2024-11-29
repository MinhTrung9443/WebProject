package vn.iotstar.controller.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.CartItem;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.ShoppingCart;
import vn.iotstar.entity.User;
import vn.iotstar.repository.ICartItemRepository;
import vn.iotstar.service.ICartService;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.IUserService;

@Controller
@RequestMapping("/User")
public class ShoppingCartController {
	@Autowired
	private IProductService productService;
	@Autowired
	private ICartItemRepository cartItemService;
	@Autowired
	private IUserService userService;
	@PostMapping("/add-to-cart")
	public String returnCartView(ModelMap model, HttpSession session, @RequestParam int productId, @RequestParam int quantity)
	{
		Product product = productService.findById(productId).get();
		User user = (User) session.getAttribute("user");
		if (user == null)
		{
			return "redirect:/login";
		}
		ShoppingCart cart = user.getCart();
		if (cart == null)
		{
			System.out.println("hello");
			cart = new ShoppingCart();
			List<CartItem> lcart = new ArrayList<>();
			cart.setItems(lcart);
			CartItem cartitem = new CartItem();
			cartitem.setProduct(product);
			cartitem.setQuantity(quantity);
			cartitem.setCart(cart);
			cart.getItems().add(cartitem);
			cart.setUser(user);
			user.setCart(cart);
			userService.save(user);
			
		}
		else {
			CartItem cartitem = new CartItem();
			cartitem.setProduct(product);
			cartitem.setQuantity(quantity);
			cartitem.setCart(cart);
			cartItemService.save(cartitem);
		}

		return "redirect:/User/cart";
	}
}
