package vn.iotstar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.Account;
import vn.iotstar.repository.IAccountRepository;
import vn.iotstar.service.IAccountService;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;

    @Override
    public Account login(String username, String password) {
        // Tìm tài khoản theo username
        Account account = accountRepository.findByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            return account; // Đăng nhập thành công
        }
        throw new RuntimeException("Invalid username or password"); // Sai thông tin
    }

    // Tìm tài khoản bằng email
    @Override
    public Account findByUsername(String email) {
        return accountRepository.findByUsername(email);
    }
    @Override
    public boolean resetPassword(String token, String newPassword) {
        // Tìm tài khoản với token (có thể bạn lưu token vào cơ sở dữ liệu khi gửi email xác minh)
        Account account = accountRepository.findByToken(token);
        
        if (account != null) {
            // Nếu tìm thấy tài khoản, cập nhật mật khẩu mới
            account.setPassword(newPassword);
            account.setToken(null);  // Xóa token sau khi thay đổi mật khẩu (hoặc thay đổi trạng thái token)

            accountRepository.save(account);  // Lưu tài khoản với mật khẩu mới
            return true;
        }

        return false;  // Trả về false nếu không tìm thấy tài khoản với token hợp lệ
    }

    

    // Lưu tài khoản (sau khi reset mật khẩu hoặc các cập nhật khác)
    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    // Tạo reset token cho email
    @Override
    public String generateResetToken(String email) {
        Account account = accountRepository.findByUsername(email);  // Tìm tài khoản theo email
        if (account != null) {
            String resetToken = generateRandomToken();
            account.setToken(resetToken);  // Cập nhật token cho tài khoản
            accountRepository.save(account); // Lưu lại tài khoản sau khi cập nhật token
            return resetToken;
        }
        throw new RuntimeException("Account with email " + email + " not found.");
    }

    // Phương thức tạo token ngẫu nhiên
    private String generateRandomToken() {
        return Long.toHexString(System.currentTimeMillis()); // Tạo token ngẫu nhiên từ thời gian hiện tại
    }

    // Lưu trạng thái "remember me" - Placeholder
    @Override
    public void saveRememberMe(String username, String password) {
        // Logic lưu thông tin "remember me" vào cookie hoặc session
        // Ví dụ: lưu thông tin đăng nhập trong cookie hoặc session
    }

    // Xóa trạng thái "remember me" khi người dùng đăng xuất
    @Override
    public void deleteRememberMe(String username) {
        // Logic xóa thông tin "remember me" khỏi cookie hoặc session
        // Ví dụ: xóa cookie khi người dùng đăng xuất
    }

	@Override
	public Account findByToken(String resetToken) {
		// TODO Auto-generated method stub
		return accountRepository.findByToken(resetToken);
	}
	 @Override
	    public void update(Account account) {
	        accountRepository.save(account); // Sử dụng phương thức `save` của Spring Data JPA
	    }

}
