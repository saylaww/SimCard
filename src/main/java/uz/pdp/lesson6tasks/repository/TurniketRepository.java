package uz.pdp.lesson6tasks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson6tasks.entity.Turniket;

public interface TurniketRepository extends JpaRepository<Turniket, Integer> {
}
