package uz.pdp.lesson6tasks.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class LoginDto {
    @Email
    private String username;
    private String password;
}
