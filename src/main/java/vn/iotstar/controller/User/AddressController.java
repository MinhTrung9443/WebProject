package vn.iotstar.controller.user;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Address;
import vn.iotstar.entity.User;
import vn.iotstar.service.IAddressService;
import vn.iotstar.service.IUserService;


@Controller
@RequestMapping("/User/Address") 
public class AddressController {
	@Autowired
    private IAddressService addressService;
	@Autowired
    private IUserService userService;
	
	@PostMapping("/edit")
	public ResponseEntity<String> editAdress(ModelMap model, HttpSession session, @RequestParam int addressId, 
			@RequestParam String addressType, @RequestParam String addressDetail)
	{
		Optional<Address> add = addressService.findById(addressId);
		if(add.isPresent())
		{
			Address address = add.get();
			address.setAddressType(addressType);
			address.setAddressDetail(addressDetail);
			addressService.save(address);
			return ResponseEntity.ok("Cập nhật thành công");
		}
		return ResponseEntity.badRequest().body("Không tìm thấy");
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> addAdress(ModelMap model, HttpSession session, @RequestParam String newAddressType, @RequestParam String newAddressDetail)
	{
		
		User user = (User) session.getAttribute("user");
		
		Address address = new Address();
		address.setAddressType(newAddressType);
		address.setAddressDetail(newAddressDetail);
		address.setUser(user);
		user.getAddress().add(address);
		for (Address add : user.getAddress())
		{
			System.out.println(add.getAddressDetail()+ "+++++++++++++++++++++  " + user.getId());
		}
		addressService.save(address);
		return ResponseEntity.ok("Thêm thành công");
	}
}
