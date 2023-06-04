package develop.drones_musala_soft.services;

import develop.drones_musala_soft.model.drones.Drone;
import develop.drones_musala_soft.model.drones.DroneState;
import develop.drones_musala_soft.model.medication.Medication;
import develop.drones_musala_soft.repository.DroneRepository;
import develop.drones_musala_soft.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class DroneServiceImpl implements DroneService{

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;

    @Autowired
    public DroneServiceImpl(DroneRepository droneRepository, MedicationRepository medicationRepository) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
    }


    @Override
    public Drone createDrone(Drone drone) {
        return droneRepository.save(drone);
    }

    @Override
    public Drone getDroneById(Long droneId) {

        return droneRepository.findById(droneId)
                .orElseThrow(() -> new IllegalArgumentException("The Drone with id: "+ droneId + " does not exist"));
    }

    @Override
    public List<Drone> getAllDrones() {
        return droneRepository.findAll();
    }

    @Override
    public Drone updateDrone(Drone drone) {
        return droneRepository.save(drone);
    }

    @Override
    public void deleteDrone(Long droneId) {
         droneRepository.deleteById(droneId);
    }

    @Override
    @Transactional
    public void loadDrone(Long droneId, List<Long> medicationIds) {

        Drone drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid drone id: " + droneId));

        if (drone.getState() != DroneState.IDLE) {
            throw new IllegalStateException("Drone must be in IDLE state to be loaded");
        }

        if (drone.getBatteryCapacity() < 25) {
            throw new IllegalStateException("Drone battery level must be above 25% to be loaded");
        }

        List<Medication> medications = medicationRepository.findAllById(medicationIds);

        if (medications.size() != medicationIds.size()) {
            throw new IllegalArgumentException("Invalid medication ids");
        }

        double totalWeight = medications.stream()
                .mapToDouble(Medication::getWeight)
                .sum();

        if (totalWeight > drone.getWeightLimit()) {
            throw new IllegalStateException("Total weight of medications exceeds weight limit of drone");
        }

        drone.setMedications(medications);
        drone.setState(DroneState.LOADED);
    }

    @Override
    public List<Medication> getLoadedMedications(Long droneId) {

        Drone drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new IllegalArgumentException("The Drone with id: "+ droneId + " does not exist"));

        return drone.getMedications();
    }

    @Override
    public List<Drone> getDronesFreeLoad() {
        return droneRepository.dronesFreeLoad();
    }
}
