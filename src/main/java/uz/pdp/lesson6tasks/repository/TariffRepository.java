package uz.pdp.lesson6tasks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson6tasks.entity.Tariff;
import uz.pdp.lesson6tasks.entity.USSD;

import java.util.Optional;

public interface TariffRepository extends JpaRepository<Tariff, Integer> {

    Optional<Tariff> findByUssd(USSD ussd);
}
