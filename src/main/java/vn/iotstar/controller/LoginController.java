package vn.iotstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Account;
import vn.iotstar.service.IAccountService;
import vn.iotstar.service.IEmailService; // Giả sử bạn có một service gửi email

@Controller
public class LoginController {

	@Autowired
	private IAccountService accountSer;

	@Autowired
	private IEmailService emailService; // Dịch vụ gửi email

	// Hiển thị trang đăng nhập
	@GetMapping("/login")
	public String showLoginPage() {
		return "Guest/login"; // Trả về tên file `login.html` trong thư mục templates
	}

	// Xử lý form đăng nhập
	@PostMapping("/process-login")
	public String processLogin(@RequestParam("username") String username, @RequestParam("password") String password,
			HttpServletRequest request) {
		// Tìm tài khoản dựa trên username (hoặc email nếu cần)
		Account account = accountSer.findByUsername(username); // Hoặc findByEmail nếu dùng email
		System.out.println(username + " " + password); // In ra để kiểm tra

		if (account != null && account.getPassword().equals(password)) {
			// Đăng nhập thành công, lưu thông tin tài khoản vào session
			HttpSession session = request.getSession();
			session.setAttribute("account", account);
			return "redirect:/waiting"; // Chuyển đến trang đợi
		} else {
			return "redirect:/login?error=true"; // Nếu thất bại, quay lại trang đăng nhập
		}
	}

	// Hiển thị trang quên mật khẩu
	@GetMapping("/forgot-password")
	public String showForgotPasswordPage() {
		return "Guest/forgot-password"; // Trả về file forgot-password.html
	}

	// Xử lý form gửi yêu cầu lấy lại mật khẩu
	@PostMapping("/process-forgot-password")
	public String processForgotPassword(@RequestParam("email") String email, Model model) {
		// Tìm tài khoản theo email
		Account account = accountSer.findByUsername(email); // Hoặc findByUsername nếu dùng username

		if (account != null) {
			// Tạo OTP và lưu vào token
			String otp = generateOtp();
			account.setToken(otp);
			accountSer.update(account); // Lưu lại thay đổi vào cơ sở dữ liệu

			// Gửi OTP qua email
			emailService.sendOtp(email, otp);

			model.addAttribute("email", email); // Truyền email vào model
			return "Guest/verify-otp"; // Chuyển đến trang xác minh OTP
		} else {
			model.addAttribute("error", "Email không tồn tại trong hệ thống!");
			return "Guest/forgot-password"; // Quay lại form quên mật khẩu nếu email không hợp lệ
		}
	}

	// Xử lý form xác minh OTP
	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam("otp") String otp, @RequestParam("email") String email, Model model) {
		// Kiểm tra OTP có đúng không
		if (isOtpValid(otp, email)) {
			// Nếu đúng, chuyển sang form đặt lại mật khẩu
			return "Guest/process-reset-password";
		} else {
			model.addAttribute("error", "Mã OTP không chính xác!");
			return "Guest/verify-otp"; // Quay lại form xác minh OTP nếu sai
		}
	}

	// Xử lý form đặt lại mật khẩu
	@PostMapping("/process-reset-password")
	public String processResetPassword(@RequestParam("token") String token,
			@RequestParam("newPassword") String newPassword, Model model) {
		// Xử lý đặt lại mật khẩu (có thể sử dụng token để xác minh)
		if (accountSer.resetPassword(token, newPassword)) {
			return "redirect:/login"; // Quay lại trang đăng nhập
		} else {
			model.addAttribute("error", "Có lỗi xảy ra khi đặt lại mật khẩu!");
			return "Guest/process-reset-password";
		}
	}

	// Phương thức tạo mã OTP ngẫu nhiên (có thể sử dụng thư viện hoặc tự viết
	// logic)
	private String generateOtp() {
		int otp = 100000 + (int) (Math.random() * 900000); // Sinh mã OTP 6 chữ số
		return String.valueOf(otp);
	}

	// Kiểm tra tính hợp lệ của OTP
	private boolean isOtpValid(String otp, String email) {
		// Tìm tài khoản theo email (hoặc username nếu cần)
		Account account = accountSer.findByUsername(email);

		// Kiểm tra nếu tài khoản tồn tại và mã OTP khớp với token đã lưu trong cơ sở dữ
		// liệu
		if (account != null && account.getToken() != null && account.getToken().equals(otp)) {
			// Kiểm tra nếu OTP chưa hết hạn (nếu bạn lưu thời gian hết hạn OTP)
			// Nếu có thêm thuộc tính otpExpirationTime trong Account, bạn có thể kiểm tra
			// thêm
			
				return true; // OTP hợp lệ
			
		}
		return false; // OTP không hợp lệ hoặc hết hạn
	}

}
