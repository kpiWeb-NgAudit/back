package kpiweb.app.dev.repository;

import kpiweb.app.dev.entity.Fact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactRepository extends JpaRepository<Fact, Integer> {

}
