package uz.pdp.lesson6tasks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson6tasks.entity.Paket;

public interface PaketRepository extends JpaRepository<Paket, Integer> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Integer paketId);
}
