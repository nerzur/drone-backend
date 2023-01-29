package com.musala.demo.drone.util;

import com.musala.demo.drone.entity.Drone;
import com.musala.demo.drone.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * This class contains the scheduled task to save the drones battery status every 30 minutes.
 * @author Gabriel
 * @version 1.0.0
 */
@Component
public class ScheduleTask {

    /**
     * Path where the log file is located.
     */
    private static final String logStorageDir = System.getProperty("user.home") + File.separator + "droneLog.log";

    @Autowired
    private DroneService droneService;

    /**
     * Scheduled task to save the drones battery status every 30 minutes.
     * @throws IOException This exception is thrown if it is impossible to write to the log file due to insufficient permissions.
     */
    @Scheduled (fixedRate = 1800000)
    public void saveDronesBatteryStatus() throws IOException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaAc = dateFormat.format(date);
        FileWriter fileWriter;
        List<Drone> droneList = droneService.listAllDrone();
        fileWriter = new FileWriter(new File(logStorageDir), true);
        if(droneList.isEmpty())
            fileWriter.write(fechaAc + " --- " + "No drones in db yet.\n");
        for (Drone drone : droneList) {
            fileWriter.write(fechaAc + " --- " + drone.toString()+"\n");
        }
        fileWriter.close();
    }


}
