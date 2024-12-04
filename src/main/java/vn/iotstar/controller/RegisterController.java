package vn.iotstar.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import vn.iotstar.dto.UserRegistrationDTO;
import vn.iotstar.entity.Account;
import vn.iotstar.entity.Address;
import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.service.IAccountService;
import vn.iotstar.service.IAddressService;
import vn.iotstar.service.IPersonService;
import vn.iotstar.service.IRoleService;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.impl.AccountServiceImpl;

@Controller
public class RegisterController {
	@Autowired
	private IAccountService accountService;

	@Autowired
	private IUserService userService;
	@Autowired
	private IPersonService personService;
	@Autowired
	private IAddressService addressService;
	@Autowired
	private IRoleService roleService;

	// Hiển thị trang đăng nhập
	@GetMapping("/register")
	public String showRegisterPage() {
		return "Guest/register"; // Trả về tên file `register.html` trong thư mục templates
	}

	@PostMapping("/process-register")
	public String processRegister(@ModelAttribute("user") UserRegistrationDTO userDTO, Model model) {
		boolean check = false;
		if (personService.findByEmail(userDTO.getEmail())!= null) {
			model.addAttribute("errorMail", "Email đã tồn tại!");
			return "Guest/register"; 
		}
		if (personService.findByPhone(userDTO.getPhone()) != null)
		{
			model.addAttribute("errorPhone", "Số điện thoại đã tồn tại!");
			return "Guest/register"; 
		}
		String phoneRegex = "^\\d{10}$";
		if (!Pattern.matches(phoneRegex, userDTO.getPhone())) {
			model.addAttribute("errorPhone", "Số điện thoại không hợp lệ");
			return "Guest/register"; 
		}
		if(accountService.findByUsername(userDTO.getUsername())!= null)
		{
			model.addAttribute("errorUsername", "Tên đăng nhập đã tồn tại!");
			return "Guest/register"; 
		}
		try {
			
			// 1. Tạo Account
			Account account = new Account();
			account.setUsername(userDTO.getUsername());
			account.setPassword(userDTO.getPassword());
		// Đặt giá trị cho active trước khi lưu

			// Set vai trò mặc định (ví dụ: customer)
			Role role = roleService.findByName("User"); // Lấy role từ cơ sở dữ liệu
			account.setRole(role);

			accountService.save(account); // Lưu Account trước

			// 2. Tạo User
			User user = new User();
			user.setFullname(userDTO.getFullName());
			user.setEmail(userDTO.getEmail());
			user.setPhone(userDTO.getPhone());
			// Chuyển gender từ string "Male" hoặc "Female" thành số (0 hoặc 1)
			if ("Nam".equals(userDTO.getGender())) {
				user.setGender(0); // Male -> 0
			} else if ("Nữ".equals(userDTO.getGender())) {
				user.setGender(1); // Female -> 1
			}

			user.setBirthday(userDTO.getBirthday());
			user.setAccount(account); // Liên kết Account với User
			
			userService.save(user); // Lưu User

			// 3. Tạo Address (nếu có)
			if (userDTO.getAddress_detail() != null && !userDTO.getAddress_type().isEmpty()) {
				Address address = new Address();
				address.setAddressDetail(userDTO.getAddress_detail());
				address.setAddressType(userDTO.getAddress_type());
				address.setUser(user); // Liên kết với User
				addressService.save(address); // Lưu Address
			}

			// 4. Thông báo thành công
			model.addAttribute("successMessage", "Đăng ký thành công!");
			return "redirect:/login"; // Chuyển hướng tới trang đăng nhập
			
		} catch (Exception e) {
			e.printStackTrace(); // Debug lỗi nếu có
			model.addAttribute("error", "Đã xảy ra lỗi khi đăng ký. Vui lòng thử lại!");
			return "Guest/register"; // Quay lại trang đăng ký nếu lỗi
		}
	}

}
