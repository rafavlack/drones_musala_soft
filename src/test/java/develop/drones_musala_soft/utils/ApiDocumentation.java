package develop.drones_musala_soft.utils;
import develop.drones_musala_soft.model.drones.DroneModel;
import develop.drones_musala_soft.model.medication.Medication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import develop.drones_musala_soft.controller.AppController;
import develop.drones_musala_soft.model.drones.Drone;
import develop.drones_musala_soft.model.drones.DroneState;
import develop.drones_musala_soft.services.DroneService;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(AppController.class)
@AutoConfigureRestDocs(outputDir = "src/docs/asciidoc")
public class ApiDocumentation {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DroneService droneService;

    @Test
    public void getDrones() throws Exception {
        List<Drone> drones = new ArrayList<>();
        List<Medication> medications = new ArrayList<>();
        medications.add(new Medication(1L, "Medication_1", 256, "CODE1", "image1.jpg"));

        drones.add(Drone.builder()
                .id(1L)
                .serialNumber("SN-056")
                .model(DroneModel.CRUISERWEIGHT)
                .weightLimit(500)
                .batteryCapacity(28)
                .state(DroneState.IDLE)
                .medications(medications)
                .build());

        drones.add(Drone.builder()
                .id(2L)
                .serialNumber("SN-057")
                .model(DroneModel.LIGHTWEIGHT)
                .weightLimit(500)
                .batteryCapacity(95)
                .state(DroneState.LOADING)
                .medications(medications)
                .build());

        given(droneService.getAllDrones()).willReturn(drones);

        this.mockMvc.perform(get("/api/v1/drones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].serialNumber", is("SN-056")))
                .andExpect(jsonPath("$[0].model", is("CRUISERWEIGHT")))
                .andExpect(jsonPath("$[0].weightLimit", is(500)))
                .andExpect(jsonPath("$[0].batteryCapacity", is(28)))
                .andExpect(jsonPath("$[0].state", is("IDLE")))
                .andExpect(jsonPath("$[0].medications[0].id", is(1)))
                .andDo(document("get-drones",
                        responseFields(
                                fieldWithPath("[].id").description("The ID of the drone."),
                                fieldWithPath("[].serialNumber").description("The name of the drone."),
                                fieldWithPath("[].model").description("The battery capacity of the drone."),
                                fieldWithPath("[].weightLimit").description("The state of the drone."),
                                fieldWithPath("[].batteryCapacity").description("The battery capacity of the drone."),
                                fieldWithPath("[].state").description("The state of the drone."),
                                fieldWithPath("[].medications[].id").description("The ID of the medication."),
                                fieldWithPath("[].medications[].name").description("The name of the medication."),
                                fieldWithPath("[].medications[].weight").description("The weight of the medication."),
                                fieldWithPath("[].medications[].code").description("The code of the medication."),
                                fieldWithPath("[].medications[].image").description("The image of the medication.")
                        )
                ));
    }
}
