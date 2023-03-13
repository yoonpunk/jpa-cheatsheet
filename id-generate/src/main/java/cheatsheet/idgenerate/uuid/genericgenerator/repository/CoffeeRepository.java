package cheatsheet.idgenerate.uuid.genericgenerator.repository;

import cheatsheet.idgenerate.uuid.genericgenerator.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, UUID> {
}
