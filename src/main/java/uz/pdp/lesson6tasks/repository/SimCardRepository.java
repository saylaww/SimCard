package uz.pdp.lesson6tasks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson6tasks.entity.SimCard;

public interface SimCardRepository extends JpaRepository<SimCard, Integer> {

    boolean existsByNumber(String number);

}
