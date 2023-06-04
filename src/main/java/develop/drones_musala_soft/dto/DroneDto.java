package develop.drones_musala_soft.dto;

import develop.drones_musala_soft.model.drones.DroneState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DroneDto {

    private String serialNumber;
    private String model;
    private int weightLimit;
    private int batteryCapacity;
    private DroneState state;
}
