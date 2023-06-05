package develop.drones_musala_soft.services;

import develop.drones_musala_soft.model.drones.Drone;
import develop.drones_musala_soft.model.event.AuditEvent;
import develop.drones_musala_soft.repository.AuditEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class AuditEventServiceImpl implements AuditEventService{

    private final DroneService droneService;
    private final AuditEventRepository auditEventRepository;

    @Autowired
    public AuditEventServiceImpl(DroneService droneService, AuditEventRepository auditEventRepository) {
        this.droneService = droneService;
        this.auditEventRepository = auditEventRepository;
    }

    @Scheduled(fixedRate = 60000) //60 seconds
    public void taskCheckDroneBattery() {
        List<Drone> drones = droneService.getAllDrones();
        for (Drone drone : drones) {
            int batteryLevel = drone.getBatteryCapacity();
            String droneSerialNumber = drone.getSerialNumber();
            LocalDateTime timestamp = LocalDateTime.now();
            AuditEvent auditEvent = new AuditEvent(null, timestamp, droneSerialNumber, batteryLevel);
            auditEventRepository.save(auditEvent);
            System.out.println("Task Check Drone Batery done at: " + new Date());
        }
    }

    @Override
    public List<AuditEvent> getAllAuditEvent() {
        return auditEventRepository.findAll();
    }

    @Override
    public List<AuditEvent> getAllByDroneSerialNumber(String droneSerialNumber) {
        return auditEventRepository.findAllByDroneSerialNumber(droneSerialNumber);
    }


}
