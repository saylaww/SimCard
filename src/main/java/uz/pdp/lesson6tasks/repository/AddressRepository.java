package uz.pdp.lesson6tasks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson6tasks.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
