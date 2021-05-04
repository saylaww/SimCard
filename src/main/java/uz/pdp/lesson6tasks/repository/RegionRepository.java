package uz.pdp.lesson6tasks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson6tasks.entity.Region;

public interface RegionRepository extends JpaRepository<Region, Integer> {
}
