package uz.pdp.lesson6tasks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson6tasks.entity.USSD;

public interface USSDRepository extends JpaRepository<USSD, Integer> {
}
