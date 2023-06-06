package develop.drones_musala_soft.model.medication;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Medication implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Error in the medication's name: allowed only (letters, numbers, ‘-‘, ‘_’)")
    private String name;
    private double weight;
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Error in the medication's code: allowed only upper case letters, underscore and numbers")
    private String code;
    private String image;

}
