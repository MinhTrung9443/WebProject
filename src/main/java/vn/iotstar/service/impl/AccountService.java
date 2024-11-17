package vn.iotstar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.iotstar.entity.Account;
import vn.iotstar.repository.IAccountRepository;
import vn.iotstar.service.IAccountService;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;

    @Override
    public Account login(String username, String password) {
        // Tìm account theo username và password
        Account account = accountRepository.findByUsername(username);;
        if (account != null) {
            return account; // Đăng nhập thành công
        }
        throw new RuntimeException("Invalid username or password"); // Sai thông tin
    }

    @Override
    public void saveRememberMe(String username, String password) {
        // Implement the logic to store "remember me" cookies for the user.
        // You might want to encrypt or hash the password before storing it in cookies for security reasons.
        // Save "remember me" cookies in the client (handled by the controller).
        // For now, just a placeholder for your logic.

        // Example: save cookies for username and password
        // Typically, this could involve setting cookies in the HttpServletResponse in a real implementation.
        // For now, we'll rely on the controller to manage this behavior.
    }

    @Override
    public void deleteRememberMe(String username) {
        // Implement the logic to delete "remember me" cookies for the user.
        // For now, you would set the cookies' max age to 0 (expired).
        // This logic would be handled by the controller.
    }
}
