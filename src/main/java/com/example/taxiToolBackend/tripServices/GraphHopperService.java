package com.example.taxiToolBackend.tripServices;



import com.example.taxiToolBackend.data.AdminSettings;
import com.example.taxiToolBackend.repository.AdminSettings_Repository;
import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.config.Profile;
import com.graphhopper.util.PointList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
/**
 * The GraphHopperService class provides functionality for interacting with GraphHopper,
 * calculating routes, distances, and trip details.
 * <p>
 * This service is responsible for managing the GraphHopper instance and handling
 * route calculation based on user requests.
 *<p>
 * todo// An expert system with machine learning is to be created to determine the departure times.
 * todo// However, this will be set up and tested in an external program and only implemented if it is successful.
 *
 * @author Daniel Altenburg
 * @version 1.0
 * @since 19.01.2023
 */
@Service
public class GraphHopperService {

    @Autowired
    private AdminSettings_Repository adminSettingsRepository;
    private final GraphHopper graphHopper;
    public double tripTime = 0;
    private double tripDistance = 0;

    /**
     * Constructs a new GraphHopperService. Initializes the GraphHopper instance with
     * the specified OSM file, data folder, and car profile.
     */
    public GraphHopperService(AdminSettings_Repository adminSettingsRepository) {
        this.adminSettingsRepository = adminSettingsRepository;
        // Create GraphHopper data folder if it doesn't exist
        createGraphHopperFolder();

        // Set the file paths for OSM data and GraphHopper data folder
        String dataFolderPath = "/home/daniel/graphHopper";
        String osmFilePath = "/home/daniel/Dokumente/Meine_Spring_Projekte/taxi_tool_demo/src/main" +
                            "/resources/graphHopper/baden-wuerttemberg-latest.osm.pbf";

            //Initialize GraphHopper instance with car profile and import or load data
            this.graphHopper = new GraphHopper()
                    .setOSMFile(osmFilePath)
                    .setGraphHopperLocation(dataFolderPath)
                    .setProfiles(Collections.singletonList(
                            new Profile("car").setVehicle("car").setWeighting("fastest")

                    ))
                    .importOrLoad();

    }

    /**
     * Retrieves the route points for the specified start and end coordinates.
     *
     * @param startLat The latitude of the starting point.
     * @param startLon The longitude of the starting point.
     * @param endLat   The latitude of the destination point.
     * @param endLon   The longitude of the destination point.
     * @return A PointList representing the route points.
     */
   public PointList getRoutePoints(double startLat, double startLon, double endLat, double endLon) {

       Logger log = LoggerFactory.getLogger(GraphHopperService.class);
       // Create a request and get a response from GraphHopper
       GHRequest request = new GHRequest(startLat, startLon, endLat, endLon).setProfile("car");//The "car" profile is necessary
       GHResponse response = graphHopper.route(request);

       // Check for errors in the response
       if (response.hasErrors()) {
           for (Throwable error : response.getErrors()) {
               log.error("Fehler während der Routenberechnung:", error);
           }
       } else {
           // Retrieve information about the best route
           tripDistance= response.getBest().getDistance();
           tripTime= response.getBest().getTime();

       }
       // Return the route points
        return response.getBest().getPoints();
    }

    //Creates the GraphHopper data folder if it doesn't exist.
    private void createGraphHopperFolder() {

        String folderPath = "/home/daniel/graphHopper";
        Path folder = Paths.get(folderPath);

        // Check if the folder doesn't exist and create it
        if (Files.notExists(folder)) {
            try {
                Files.createDirectories(folder);
                System.out.println("Folder created: " + folderPath);
            } catch (Exception e) {
                System.err.println("Error creating folder: " + e.getMessage());
            }
        } else {
            System.out.println("Folder already exists: " + folderPath);
        }
    }


     // Retrieves the rounded trip distance in kilometers and return the rounded trip distance
    public int getTripDistance(){

        double tempDistance = tripDistance/1000;
        return (int) Math.round(tempDistance);
    }

    /**
     * Calculates the trip price based on the specified parameters.
     *
     * @param tempGebiet    The requested area.
     * @param tempAnfahrt   The requested approach.
     * @param tempGrossraum The requested large area.
     * @return The calculated trip price as a string.
     */
    public String calculateTrip(String tempGebiet, String tempAnfahrt, String tempGrossraum){
        double kmPrice;

        AdminSettings adminSettings = adminSettingsRepository.findByGebiet(tempGebiet);

        if (adminSettings != null) {
             kmPrice = Double.parseDouble(adminSettings.getKilometerpreis()) * getTripDistance()
                + Double.parseDouble(adminSettings.getGrundgebuer());

            if (tempAnfahrt.equals("J"))
                kmPrice = kmPrice + Double.parseDouble(adminSettings.getAnfahrt());

            if (tempGrossraum.equals("J"))
                kmPrice = kmPrice + Double.parseDouble(adminSettings.getGrossraum());

            return  Double.toString(kmPrice);
        } else {

            return "Der Preis konnte nicht ermittelt werden!";
        }
    }

    /**
     * Calculates the departure time based on the specified parameters.
     *
     * @param tempTermin The requested departure time.
     * @param tempLaufweg The requested walking time.
     * @param tempPuffer  The requested buffer time.
     * @return The calculated departure time as a string.
     */
    public String calulateTime(String tempTermin, String tempLaufweg, String tempPuffer){

        int tempTime = (int)(tripTime / 60000) + Integer.parseInt(tempLaufweg)+Integer.parseInt(tempPuffer);

        LocalTime startTime = LocalTime.parse(tempTermin, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime endTime = startTime.plusMinutes(tempTime);

        return endTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }



}
