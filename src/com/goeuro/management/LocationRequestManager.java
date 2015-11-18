package com.goeuro.management;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.goeuro.api.entity.Location;
import com.goeuro.api.service.LocationRequestService;

/**
 * This class implements the LocationRequestService.
 * 
 * @author lizardo
 */
public class LocationRequestManager implements LocationRequestService {

	/**
	 * Constants.
	 */
	private static final String GOEURO_ENDPOINT = "http://api.goeuro.com/api/v2/position/suggest/en/";
	private static final String ID = "_id";
	private static final String NAME = "name";
	private static final String TYPE = "type";
	private static final String LATITUDE = "latitude";
	private static final String LONGITUDE = "longitude";
	private static final String GEO_POSITION = "geo_position";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.goeuro.api.service.LocationService#getLocation(java.lang.String)
	 */
	@Override
	public List<Location> requestGoEuroLocations(String cityName) throws IOException, JSONException {

		// Request GoEuro API Endpoint for the locations related to the city
		// name.
		String strJSON = requestJSON(cityName);

		// Parse the JSON and create locations entities.
		List<Location> locations = parseJSON(strJSON);

		return locations;
	}

	/**
	 * This method do a HTTP request to GoEuro API rest server and returns a
	 * string in JSON format with the locations related to the city name.
	 * 
	 * @param cityName:
	 *            the city name to be requested to the API.
	 * @return string at JSON format with the locations.
	 * @throws IOException:
	 *             in case of problems during request.
	 */
	private String requestJSON(String cityName) throws IOException {

		StringBuffer response = new StringBuffer();
		BufferedReader bufferedReader = null;

		try {

			// Avoid unsafe characters in the city name and also substitutes
			// possible spaces to %20.
			String cleanName = cityName.trim()
					.replaceAll("\\ ", "%20")
					.replaceAll("ü", "ue")
					.replaceAll("ö", "oe")
                    .replaceAll("ä", "ae")
                    .replaceAll("ß", "ss");
			
			URL url = new URL(GOEURO_ENDPOINT + cleanName);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String inputLine;

			while ((inputLine = bufferedReader.readLine()) != null) {
				response.append(inputLine);
			}

			return response.toString();

		} finally {
			bufferedReader.close();
		}
	}

	/**
	 * This method takes a string at JSON format and parses it into Location
	 * entities.
	 * 
	 * @param strJSON:
	 *            Valid JSON string to be parsed.
	 * @return: List of Location entities.
	 * @throws JSONException:
	 *             in case of problem during parse.
	 */
	private List<Location> parseJSON(String strJSON) throws JSONException {

		List<Location> locations = new ArrayList<>();

		if (strJSON != null && !strJSON.isEmpty()) {

			JSONArray jArray = new JSONArray(strJSON);

			// Parse each location and add them to the location arraylist
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject obj = jArray.getJSONObject(i);

				long id = obj.getLong(ID);
				String name = obj.getString(NAME);
				String type = obj.getString(TYPE);

				JSONObject geoObj = obj.getJSONObject(GEO_POSITION);
				double latitude = geoObj.getDouble(LATITUDE);
				double longitude = geoObj.getDouble(LONGITUDE);

				try {
					locations.add(new Location(id, name, type, latitude, longitude));
				} catch (IllegalArgumentException e) {
					System.err.println(e.getMessage() + " This location will be ignored.");
				}
			}
		}

		return locations;
	}
}
