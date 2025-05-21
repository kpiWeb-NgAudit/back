package kpiweb.app.dev.repository;

import kpiweb.app.dev.entity.Fact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FactRepository extends JpaRepository<Fact, Integer> {

    @Query("SELECT MAX(f.factWorkorder) FROM Fact f WHERE f.customer.cubeIdPk = :customerCubeIdPk")
    Integer findMaxFactWorkorderForCustomer(@Param("customerCubeIdPk") String customerCubeIdPk);

    boolean existsByFactShortcubenameAndCustomer_CubeIdPk(String factShortcubename, String customerCubeIdPk);
    boolean existsByFactTnameAndCustomer_CubeIdPk(String factTname, String customerCubeIdPk);
    // boolean existsByFactWorkorderAndCustomer_CubeIdPk(Integer factWorkorder, String customerCubeIdPk); // Might be useful for proactive checks

}
