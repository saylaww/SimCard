package uz.pdp.lesson6tasks.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class RegisterDto {

    private String firstName;

    private String lastName;

    @Email
    private String email;

    private String password;

    private Integer roleId;

    private String phoneNumber;

    private String passportId;

    private Integer companyId;

    private boolean isPhysicalBody;


}
