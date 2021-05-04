package uz.pdp.lesson6tasks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson6tasks.entity.TurniketHistory;

public interface TurniketHistoryRepository extends JpaRepository<TurniketHistory, Integer> {
}
