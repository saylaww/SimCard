package uz.pdp.lesson6tasks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson6tasks.dto.EmployeeDto;
import uz.pdp.lesson6tasks.dto.LoginDto;
import uz.pdp.lesson6tasks.dto.RegisterDto;
import uz.pdp.lesson6tasks.entity.Response;
import uz.pdp.lesson6tasks.service.AuthService;
import uz.pdp.lesson6tasks.service.EmployeeService;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/getEmployeeList")
    public HttpEntity<?> register() {
        final Response response = employeeService.getEmployeeList();
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @GetMapping("/editEmployee")
    public HttpEntity<?> edit(@RequestBody EmployeeDto employeeDto, @RequestParam UUID userId) {
        final Response response = employeeService.edit(employeeDto, userId);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody RegisterDto registerDto) {
        final Response response = authService.register(registerDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto) {
        final Response response = authService.login(loginDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }
}
