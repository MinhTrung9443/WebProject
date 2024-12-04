package vn.iotstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Account;
import vn.iotstar.entity.Person;
import vn.iotstar.service.IAccountService;
import vn.iotstar.service.IEmailService; // Giả sử bạn có một service gửi email
import vn.iotstar.service.IPersonService;

@Controller
public class LoginController {

	@Autowired
	private IAccountService accountSer;
	@Autowired
	private IPersonService personSer;

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
			@RequestParam(value = "remember-me", required = false) String rememberMe, HttpServletRequest request,
			HttpServletResponse response) { // Thêm HttpServletResponse ở đây
		// Tìm tài khoản dựa trên username
		Account account = accountSer.findByUsername(username);

		if (account != null && account.getPassword().equals(password)) {
			// Đăng nhập thành công, lưu thông tin tài khoản vào session
			HttpSession session = request.getSession();
			session.setAttribute("account", account);

			// Nếu người dùng chọn "Nhớ tôi"
			if ("on".equals(rememberMe)) {
				// Tạo cookie lưu thông tin tài khoản
				Cookie cookie = new Cookie("rememberMe", account.getUsername());
				cookie.setMaxAge(10 * 60); // Cookie tồn tại
				cookie.setPath("/"); // Áp dụng cookie trên toàn bộ ứng dụng
				response.addCookie(cookie); // Sử dụng đối tượng response
			}

			return "redirect:/waiting"; // Chuyển đến trang chờ
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
		Person person = personSer.findByEmail(email); // Hoặc findByUsername nếu dùng username
		System.out.println(person);
		if (person != null) {
			Account account = accountSer.findById(person.getAccount().getAccountId()); // Sửa thành đúng ID tài khoản
			if (account != null) {
				// Tạo OTP và lưu vào token
				String otp = generateOtp();
				account.setToken(otp);
				accountSer.update(account); // Lưu lại thay đổi vào cơ sở dữ liệu

				// Gửi OTP qua email
				emailService.sendOtp(email, otp);
				model.addAttribute("account", account);// Truyền account vào model
				model.addAttribute("email", email); // Truyền email vào model
				return "Guest/verify-otp"; // Chuyển đến trang xác minh OTP
			} else {
				model.addAttribute("error", "Không tìm thấy tài khoản liên kết với email này.");
				return "Guest/forgot-password"; // Trả về form quên mật khẩu
			}
		} else {
			model.addAttribute("error", "Email không tồn tại trong hệ thống!");
			return "Guest/forgot-password"; // Quay lại form quên mật khẩu nếu email không hợp lệ
		}
	}

	// Xử lý form xác minh OTP
	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam("otp") String otp, @RequestParam("email") String email, Model model) {
		if (isOtpValid(otp, email)) {
			// Nếu đúng, chuyển sang form đặt lại mật khẩu
			model.addAttribute("token", otp);
			return "Guest/process-reset-password";
		}

		else {
			model.addAttribute("error", "Mã OTP không chính xác!");
			return "Guest/verify-otp"; // Quay lại form xác minh OTP nếu sai
		}
	}

	// Xử lý form đặt lại mật khẩu
	@PostMapping("/process-reset-password")
	public String processResetPassword(@RequestParam("token") String token,
			@RequestParam("newPassword") String newPassword, Model model) {
		Account acc = accountSer.findByToken(token);
		if (acc == null) {
			model.addAttribute("error", "Không có mã otp hoặc tài khoản này!");
			return "Guest/process-reset-password";
		}

		// Xử lý đặt lại mật khẩu (có thể sử dụng token để xác minh)
		if (accountSer.resetPassword(token, newPassword)) {
			model.addAttribute("success", "Đổi mật khẩu thành công vui lòng đăng nhập lại!");
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
		Person person = personSer.findByEmail(email);
		// Tìm tài khoản theo email (hoặc username nếu cần)
		Account account = accountSer.findById(person.getAccount().getAccountId());

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