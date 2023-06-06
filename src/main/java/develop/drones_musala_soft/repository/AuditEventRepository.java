package develop.drones_musala_soft.repository;

import develop.drones_musala_soft.model.event.AuditEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditEventRepository extends JpaRepository<AuditEvent, Long> {

    List<AuditEvent> findAllByDroneSerialNumber(String droneSerialNumber);

}
