package develop.drones_musala_soft.dto;

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
    private String name;
    private int weight;
    private String code;
    private String image;
}
