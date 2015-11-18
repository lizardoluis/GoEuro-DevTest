import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.goeuro.api.entity.Location;
import com.goeuro.api.service.LocationExportService;
import com.goeuro.api.service.LocationRequestService;
import com.goeuro.management.LocationExportManager;
import com.goeuro.management.LocationRequestManager;

/**
 * 
 */

/**
 * @author lizardo
 *
 */
public class NameTest {

	
	/**
	 * Test latitude and longitude error handling.
	 */
	public static Location locationTest(long id, String name, String type, double lat, double lon) {
		try {
			Location location = new Location(id, name, type, lat, lon);
			
			System.out.println(location.getId() + ", " + location.getName() + ", " + location.getType() + ", "
					+ location.getLatitude() + ", " + location.getLongitude());
			
			return location;
			
		} catch (IllegalArgumentException e) {
			System.err.println("Exception catched at location: " + id + " - " + name);
		}
		
		return new Location(id, name, type, 0.000, 0.000);
	}

	/**
	 * Test the names of the cities for request.
	 */
	public static void cityNameTest(String cityName) {
		LocationRequestService locService = new LocationRequestManager();

		try {
			List<Location> locs = locService.requestGoEuroLocations(cityName);

			if (locs.isEmpty()) {
				System.err.println("Empty locations for: " + cityName);
			} else {
				System.out.println(locs.size() + " locations found for: " + cityName);
			}

		} catch (JSONException e) {
			System.err.println("JSON parser error for: " + cityName);
		} catch (IOException e) {
			System.err.println("Request error for: " + cityName);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		List<Location> locs = new ArrayList<>();

		// Lat lon range tests
		locs.add(locationTest(10, "loc-1", "station", 10.2254, -45.0258));
		locs.add(locationTest(20, "loc-2", "airport", -10.2254, +45.0258));
		
		System.out.println();
		
		locs.add(locationTest(30, "loc-3", "station", 0.0, 180.0001));
		locs.add(locationTest(40, "loc-4", "airport", 0.0, -180.0001));
		locs.add(locationTest(50, "loc-5", "airport", +90.0001, 9.0001));
		locs.add(locationTest(60, "loc-6", "bike-station", -90.0001, 10.0001));

		// Export test
		LocationExportService expService = new LocationExportManager();
		try {
			expService.exportToCSV("randomTest", locs);
		} catch (IOException e) {
			System.err.println("Erro to export.");
		}
		
		System.out.println();

		// City names tests
		cityNameTest("Zella Mehlis");
		cityNameTest("Zella%20Mehlis");

		cityNameTest("Frankfurt");
		cityNameTest("Frankfurt am Main");
		cityNameTest("Frankfurt (Oder)");

		cityNameTest("Brand-Erbisdorf");

		cityNameTest("Böblingen");
		cityNameTest("Boeblingen");

		cityNameTest("Creußen");
		cityNameTest("Creussen");

		cityNameTest("Düsseldorf");
		cityNameTest("Duesseldorf");

	}

}
