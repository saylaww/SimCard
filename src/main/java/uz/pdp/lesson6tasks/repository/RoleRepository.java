package uz.pdp.lesson6tasks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson6tasks.entity.Role;
import uz.pdp.lesson6tasks.entity.enums.RoleName;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleName(RoleName roleName);
}
