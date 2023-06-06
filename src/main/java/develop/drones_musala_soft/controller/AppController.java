package develop.drones_musala_soft.controller;

import develop.drones_musala_soft.dto.DroneDto;
import develop.drones_musala_soft.dto.MedicationDto;
import develop.drones_musala_soft.model.drones.Drone;
import develop.drones_musala_soft.model.event.AuditEvent;
import develop.drones_musala_soft.services.AuditEventService;
import develop.drones_musala_soft.services.DroneService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class AppController {

    private final ModelMapper modelMapper;
    private final AuditEventService eventService;
    private final DroneService droneService;

    public AppController(ModelMapper modelMapper, AuditEventService eventService, DroneService droneService) {
        this.modelMapper = modelMapper;
        this.eventService = eventService;
        this.droneService = droneService;
    }


    @Autowired


    @GetMapping("/drones")
    public List<DroneDto> pasarDatos(){

        List<DroneDto> droneDtos = new ArrayList<>();
        droneService.getAllDrones().forEach(drone -> droneDtos.add(modelMapper.map(drone, DroneDto.class)));

        return droneDtos;
    }

    @GetMapping("/drones/{id}")
    public ResponseEntity<DroneDto> findDrone(@PathVariable("id") Long id){

        DroneDto droneDto = modelMapper.map(droneService.getDroneById(id), DroneDto.class);

        return ResponseEntity.ok().body(droneDto);
    }

    @PostMapping("/drones")
    public ResponseEntity<DroneDto> saveDrone(@Valid @RequestBody DroneDto droneDto) {
        return new ResponseEntity(droneService.createDrone(modelMapper.map(droneDto, Drone.class)), HttpStatus.CREATED);
    }

    @PutMapping("/drones")
    public ResponseEntity<?> updateDrone(@Valid @RequestBody DroneDto droneDto) {
        droneService.updateDrone(modelMapper.map(droneDto, Drone.class));
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/drones/{id}")
    public ResponseEntity<?> deleteDrone(@PathVariable("id") Long id) {
        droneService.deleteDrone(id);
        return ResponseEntity.ok().build();
    }

    //Load de drone with medication
    @PostMapping("/drones/{droneId}/load")
    public ResponseEntity<?> load(@PathVariable Long droneId, @RequestBody List<Long> medicationIds) {
        try {
            droneService.loadDrone(droneId, medicationIds);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/drones/{droneId}/medicines")

    public ResponseEntity<List<MedicationDto>> getLoadedMedications(@PathVariable Long droneId) {

        List<MedicationDto> medicationDtoList = droneService.getLoadedMedications(droneId).stream().map(medication -> modelMapper.map(medication, MedicationDto.class)).collect(Collectors.toList());

        return ResponseEntity.ok(medicationDtoList);
    }

    @GetMapping("/drones/available")
    public List<DroneDto> dronesAvailable(){

        return droneService.getDronesFreeLoad().stream().map(drone -> modelMapper.map(drone, DroneDto.class)).collect(Collectors.toList());
    }


    @GetMapping("/audit_event")
    public List<AuditEvent> listAuditEvent(){
        return eventService.getAllAuditEvent();
    }

    @GetMapping("/audit_event/drone/{serialNumber}")
    public List<AuditEvent> listAuditEventByDrone(@PathVariable("serialNumber") String serialNumber){
        return eventService.getAllByDroneSerialNumber(serialNumber);
    }

}
