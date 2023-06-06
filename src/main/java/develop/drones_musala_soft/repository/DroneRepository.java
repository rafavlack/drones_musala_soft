package develop.drones_musala_soft.repository;

import develop.drones_musala_soft.model.drones.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {

    @Query("select d from Drone d where d.batteryCapacity > 25 and  d.state = 'IDLE' ")
    List<Drone> dronesFreeLoad();

}