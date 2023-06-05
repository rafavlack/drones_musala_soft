package develop.drones_musala_soft.dto;

import develop.drones_musala_soft.model.drones.DroneModel;
import develop.drones_musala_soft.model.drones.DroneState;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DroneDto {

    private Long id;
    @NotBlank
    @Size(max = 100, message = "Drone serial number must have a maximum of 100 characters")
    private String serialNumber;
    @NotNull
    private DroneModel model;
    @Max(value = 500, message = "The weight that a drone can carry is up to 500 grams")
    private int weightLimit;
    private int batteryCapacity;
    @NotNull
    private DroneState state;
    private MedicationDto medicationDto;
}
