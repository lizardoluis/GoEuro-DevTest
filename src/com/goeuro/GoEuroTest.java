package com.goeuro;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import com.goeuro.api.entity.Location;
import com.goeuro.api.service.LocationExportService;
import com.goeuro.api.service.LocationRequestService;
import com.goeuro.management.LocationExportManager;
import com.goeuro.management.LocationRequestManager;

/**
 * @author lizardo
 *
 */
public class GoEuroTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Check number of arguments form the command line
		if (args.length != 1) {
			printMsgAndExit("Usage: java -jar GoEuroTest.jar \"CITY NAME\"");
		}

		String cityName = new String(args[0]).toLowerCase();
		List<Location> locations = null;

		// Request GoEuro API for the city locations
		try {
			LocationRequestService requestService = new LocationRequestManager();
			locations = requestService.requestGoEuroLocations(cityName);

			// Check if the request returned any data
			if (locations.isEmpty()) {
				printMsgAndExit("No locations found for this city.");
			}
		} catch (IOException e) {
			printMsgAndExit("Fail to request GoEuro API.");
		} catch (JSONException e) {
			printMsgAndExit("Fail to parse JSON file.");
		}

		
		// Export the locations to a CSV file
		try {
			LocationExportService exportService = new LocationExportManager();
			exportService.exportToCSV(cityName, locations);
		} catch (IOException e) {
			printMsgAndExit("Fail to export to CSV file.");
		}

		System.out.println("Locations successfully exported to CSV file.");
	}

	/**
	 * Print a message and exit.
	 * 
	 * @param msg
	 *            Printed message before exit.
	 */
	public static void printMsgAndExit(String msg) {
		System.err.println(msg);
		System.exit(1);
	}
}
