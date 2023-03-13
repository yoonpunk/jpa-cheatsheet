package cheatsheet.idgenerate.uuid.manual.repository;

import cheatsheet.idgenerate.uuid.manual.entity.Bread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BreadRepository extends JpaRepository<Bread, UUID> {
}
