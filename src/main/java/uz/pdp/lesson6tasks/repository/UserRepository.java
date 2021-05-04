package uz.pdp.lesson6tasks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson6tasks.entity.User;

import javax.validation.constraints.Email;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(@Email String email);
}
