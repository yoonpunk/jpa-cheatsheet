package cheatsheet.idgenerate.uuid.manual.repository;

import cheatsheet.idgenerate.uuid.manual.entity.Cake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CakeRepository extends JpaRepository<Cake, UUID> {
}
