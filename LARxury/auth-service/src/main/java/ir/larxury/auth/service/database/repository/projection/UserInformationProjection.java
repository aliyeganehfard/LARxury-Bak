package ir.larxury.auth.service.database.repository.projection;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInformationProjection implements Serializable {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public UserInformationProjection(String username,String firstName, String lastName, String email, String phoneNumber) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
