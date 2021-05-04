package uz.pdp.lesson6tasks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.lesson6tasks.dto.LoginDto;
import uz.pdp.lesson6tasks.dto.RegisterDto;
import uz.pdp.lesson6tasks.entity.Company;
import uz.pdp.lesson6tasks.entity.Response;
import uz.pdp.lesson6tasks.entity.Role;
import uz.pdp.lesson6tasks.entity.User;
import uz.pdp.lesson6tasks.entity.enums.RoleName;
import uz.pdp.lesson6tasks.repository.CompanyRepository;
import uz.pdp.lesson6tasks.repository.RoleRepository;
import uz.pdp.lesson6tasks.repository.UserRepository;
import uz.pdp.lesson6tasks.security.JwtProvider;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    public Response register(RegisterDto registerDto) {
        final Role roleFromToken = getRole();

        final Optional<Role> optionalRole = roleRepository.findById(registerDto.getRoleId());
        if (optionalRole.isEmpty()) {
            return new Response("Role not found!", false);
        }
        final Role roleDto = optionalRole.get();

        final Optional<User> optionalUser
                = userRepository.findByEmail(registerDto.getEmail());
        if (optionalUser.isPresent()) {
            return new Response("Email already exists!", false);
        }

        if (roleFromToken == null) {
            final User user = saveUser(registerDto);
            userRepository.save(user);
            return new Response("Director Saved", true, user);
        }


        if (roleFromToken.getLevel() < roleDto.getLevel()) {
            if (roleDto.getRoleName().equals(RoleName.DIRECTOR)
                    && roleDto.getLevel() < roleFromToken.getLevel()
            ) {
                return new Response("Director can't add Director", false);
            }
            final User user = saveUser(registerDto);
            userRepository.save(user);
            return new Response(roleDto.getLevel() + " " + roleDto.getRoleName() + " saved!", true, user);
        } else if (roleFromToken.getRoleName().equals(RoleName.HR_MANAGER)) {
            if (roleDto.getRoleName().equals(RoleName.EMPLOYEE)) {
                final User user = saveUser(registerDto);
                userRepository.save(user);
                return new Response(roleDto.getRoleName() + " saved", true);
            } else {
                return new Response("You don't have privileges", false);
            }
        }
        return null;
    }

    public Response login(LoginDto loginDto) {
        try {
            final Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginDto.getUsername(),
                                    loginDto.getPassword()));

            final User user = (User) authenticate.getPrincipal();
            final String token = jwtProvider.generateToken(loginDto.getUsername(), user.getRole());

            return new Response("Token", true, token);
        } catch (BadCredentialsException e) {
            return new Response("Login or password incorrect", true);
        }
    }

    public User saveUser(RegisterDto registerDto) {
        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        final Optional<Role> optionalRole = roleRepository.findById(registerDto.getRoleId());
        if (optionalRole.isEmpty()) {
            return null;
        }

        final Optional<Company> optionalCompany = companyRepository.findById(registerDto.getCompanyId());
        if (optionalCompany.isEmpty()) {
            return null;
        }
        final Company company = optionalCompany.get();

        user.setCompany(company);
        user.setRole(optionalRole.get());
        user.setPassportId(registerDto.getPassportId());
        user.setPhysicalBody(registerDto.isPhysicalBody());

        return user;
    }

    public Role getRole() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.getPrincipal().equals("anonymousUser")) {
            final User user = (User) authentication.getPrincipal();
            return user.getRole();
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        final Optional<User> optionalUser = userRepository.findByEmail(s);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException(s + " not found");
        }
        return optionalUser.get();

    }
}
