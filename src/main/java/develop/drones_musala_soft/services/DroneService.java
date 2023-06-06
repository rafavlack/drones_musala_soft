package develop.drones_musala_soft.services;

import develop.drones_musala_soft.model.drones.Drone;
import develop.drones_musala_soft.model.medication.Medication;

import java.util.List;

public interface DroneService {

    Drone createDrone(Drone drone);

    Drone getDroneById(Long droneId);

    List<Drone> getAllDrones();

    Drone updateDrone(Drone drone);

    void deleteDrone(Long droneId);

    void loadDrone(Long droneId, List<Long> medicationIds);

    List<Medication> getLoadedMedications(Long droneId);

    List<Drone> getDronesFreeLoad();
}
