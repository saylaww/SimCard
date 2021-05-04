package uz.pdp.lesson6tasks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson6tasks.dto.EmployeeDto;
import uz.pdp.lesson6tasks.entity.Response;
import uz.pdp.lesson6tasks.entity.User;
import uz.pdp.lesson6tasks.entity.enums.RoleName;
import uz.pdp.lesson6tasks.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    UserRepository userRepository;

    public Response getEmployeeList() {
        List<User> users = new ArrayList<>();
        final List<User> userList = userRepository.findAll();
        for (User user : userList) {
            if (
//                    user.getRole().getLevel().equals(Level.BRANCH) &&
                    user.getRole().getRoleName().equals(RoleName.EMPLOYEE)) {
                users.add(user);
            }
        }
        return new Response("List", true, users);
    }

    public Response edit(EmployeeDto employeeDto, UUID userId) {
        final Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return new Response("Employee not found", false);
        }
        final User user = optionalUser.get();

        if (
//                user.getRole().getLevel().equals(
//                Level.BRANCH) &&
                user.getRole().getRoleName().equals(RoleName.EMPLOYEE)) {
            User u = new User();
            u.setFirstName(employeeDto.getFirstName());
            u.setLastName(employeeDto.getLastName());
            u.setPassword(employeeDto.getPassword());
            u.setPassportId(employeeDto.getPassportId());
            final User savedUser = userRepository.save(u);
            return new Response("User edited!", true, savedUser);
        }
        return null;
    }
}
