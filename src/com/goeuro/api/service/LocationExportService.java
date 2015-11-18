package com.goeuro.api.service;

import java.io.IOException;
import java.util.List;

import com.goeuro.api.entity.Location;

/**
 * 
 * @author lizardo
 *
 */
public interface LocationExportService {

	/**
	 * Take a list locations and export them to CSV file. The file will be
	 * stored at the execution directory and its name will be CITY_NAME.csv.
	 * 
	 * @param cityName:
	 *            Name of the city. Used to set file name.
	 * @param locations:
	 *            List of locations to be exported to the CSV file.
	 * @throws IOException:
	 */
	public void exportToCSV(String cityName, List<Location> locations) throws IOException;
}
