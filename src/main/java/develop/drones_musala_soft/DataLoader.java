package develop.drones_musala_soft;

import develop.drones_musala_soft.model.drones.Drone;
import develop.drones_musala_soft.model.drones.DroneModel;
import develop.drones_musala_soft.model.drones.DroneState;
import develop.drones_musala_soft.model.medication.Medication;
import develop.drones_musala_soft.repository.DroneRepository;
import develop.drones_musala_soft.repository.MedicationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DataLoader implements CommandLineRunner {

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;
    private final Random random = new Random();

    public DataLoader(DroneRepository droneRepository, MedicationRepository medicationRepository) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String[] models = {"LIGHTWEIGHT", "MIDDLEWEIGHT", "CRUISERWEIGHT", "HEAVYWEIGHT"};
        String[] states = {"IDLE", "LOADING", "LOADED", "DELIVERING", "DELIVERED", "RETURNING"};

        for (int i = 1; i <= 10; i++) {
            int number = random.nextInt(101);
            String serialNumber = String.format("SN-%03d", number);
            String model = models[random.nextInt(models.length)];
            int weightLimit = 500;
            int batteryCapacity = random.nextInt(101);
            String state = states[random.nextInt(states.length)];

            Drone drone = Drone.builder()
                    .serialNumber(serialNumber)
                    .model(DroneModel.valueOf(model))
                    .weightLimit(weightLimit)
                    .batteryCapacity(batteryCapacity)
                    .state(DroneState.valueOf(state))
                    .build();
            droneRepository.save(drone);
        }

        for (int i = 1; i <= 50; i++) {
            String name = "Medication_" + i;
            double weight = random.nextDouble() * 500;
            String code = "CODE" + i;
            String image = "image" + i + ".jpg";

            Medication medication = Medication.builder()
                    .name(name)
                    .weight(weight)
                    .code(code)
                    .image(image)
                    .build();
            medicationRepository.save(medication);
        }
    }
}
