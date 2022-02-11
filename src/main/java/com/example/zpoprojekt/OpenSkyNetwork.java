package com.example.zpoprojekt;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The class that connects to Open Sky Network Api
 */
public class OpenSkyNetwork {
    private String OpenSkyUser = "https://254745:7eGN7yfe@opensky-network.org/api/flights/";

    private FlightData[] getFlight(URL url) throws IOException {
        StringBuffer response = new StringBuffer();
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line;
            while((line = bufferedReader.readLine()) != null ) {
                response.append(line);
            }
            bufferedReader.close();

        } catch (IOException e){
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FlightData[] flightsdata = gson.fromJson(response.toString(),FlightData[].class);
        return flightsdata;
    }

    /**
     * The method that retrieve flights for a certain airport which arrived
     * within a given time interval [begin, end].
     * @param airport String
     * @param begin long starting time
     * @param end long ending time
     * @return FlightData[] arrival information
     * @throws IOException
     */
    public FlightData[] getArrival(String airport, long begin, long end) throws IOException {
        URL url = new URL(OpenSkyUser + "arrival?" + "airport=" + airport + "&begin=" + begin + "&end=" + end);
        return getFlight(url);
    }

    /**
     * The method that retrieve flights for a certain airport which departed
     * within a given time interval [begin, end].
     * @param airport String
     * @param begin long starting time
     * @param end long ending time
     * @return FlightData[] departure information
     * @throws IOException
     */
    public FlightData[] getDepartures(String airport, long begin, long end) throws IOException {
        URL url = new URL(OpenSkyUser + "departure?" + "airport=" + airport + "&begin=" + begin + "&end=" + end);
        return getFlight(url);
    }

    /**
     * The method that call retrieves flights for a particular aircraft within a certain time interval.
     * @param aircraft
     * @param begin long starting time
     * @param end long ending time
     * @return FlightData[] aircraft information
     * @throws IOException
     */
    public FlightData[] getAircraft(String aircraft, long begin, long end) throws IOException {
        URL url = new URL(OpenSkyUser + "aircraft?" + "icao24=" + aircraft + "&begin=" + begin + "&end=" + end);
        return getFlight(url);
    }
}
