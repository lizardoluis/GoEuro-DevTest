package com.goeuro.api.service;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import com.goeuro.api.entity.Location;

/**
 * 
 * @author lizardo
 *
 */
public interface LocationRequestService {

	/**
	 * This method makes a request at GoEuro API, expects a JSON file as a
	 * response and parses it into a list of locations.
	 * 
	 * @param cityName.
	 *            The city name to be requested to the API
	 * @return a list of locations corresponding to the city searched.
	 * 
	 * @throws IOException:
	 *             in case of problems with the request.
	 * @throws JSONException:
	 *             in case of problem during JSON parse.
	 */
	public List<Location> requestGoEuroLocations(String cityName) throws IOException, JSONException;
}
