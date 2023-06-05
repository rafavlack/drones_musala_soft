package develop.drones_musala_soft.services;


import develop.drones_musala_soft.model.event.AuditEvent;

import java.util.List;

public interface AuditEventService {

    List<AuditEvent> getAllAuditEvent();
    List<AuditEvent> getAllByDroneSerialNumber(String droneSerialNumber);
}
