package develop.drones_musala_soft.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MedicationDto {

    private Long id;
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Error in the medication's name: allowed only (letters, numbers, ‘-‘, ‘_’)")
    private String name;
    private double weight;
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Error in the medication's code: allowed only upper case letters, underscore and numbers")
    private String code;
    private String image;
}
