package vn.iotstar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {
	private String username;
    private String password;
    private int roleId;
    private String email;
    private String fullName;
    private String gender;
    private String phone;
    private String birthday;
}
