package vn.iotstar.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Favourite;
import vn.iotstar.entity.User;
import vn.iotstar.service.IFavouriteService;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.IUserService;

@Controller
@RequestMapping("/User")
public class FavouriteController {
	@Autowired
	private IFavouriteService favouriteService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IProductService productService;
	
	@PostMapping("/addlove")
	public ResponseEntity<String> addlove(ModelMap model, HttpSession session, @RequestParam int productId)
	{
		User user = (User) session.getAttribute("user");
		List<Favourite> listfavou = user.getFavourite();
		if (listfavou == null)
		{
			listfavou = new ArrayList<>();
			user.setFavourite(listfavou);
			userService.save(user);
		}
		
		Optional<Favourite> favourite = favouriteService.findByUserId(user.getId(),productId);
		
		if (favourite.isPresent())
		{
			favouriteService.deleteById(favourite.get().getFavouriteId().intValue());
			return ResponseEntity.ok("Bỏ yêu thích thành công");
		}
		else 
		{
			Favourite newfavou = new Favourite();
			newfavou.setProduct(productService.findById(productId).get());
			newfavou.setUser(user);
			favouriteService.save(newfavou);
		}
		
		return ResponseEntity.ok("Thêm yêu thích thành công");
	}
}
