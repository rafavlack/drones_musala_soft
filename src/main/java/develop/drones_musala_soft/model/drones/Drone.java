package develop.drones_musala_soft.model.drones;

import develop.drones_musala_soft.model.medication.Medication;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Drone implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @Size(max = 100, message = "Drone serial number must have a maximum of 100 characters")
    private String serialNumber;
    @NotNull
    @Enumerated(EnumType.STRING)
    private DroneModel model;
    @Max(value = 500, message = "The weight that a drone can carry is up to 500 grams")
    private int weightLimit;
    private int batteryCapacity;
    @NotNull
    @Enumerated(EnumType.STRING)
    private DroneState state;

    // Add a relationship with the Medication class
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "drone_medications",
            joinColumns = @JoinColumn(name = "drone_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id"))
    private List<Medication> medications;
}

