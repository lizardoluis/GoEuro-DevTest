package com.goeuro.management;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.goeuro.api.entity.Location;
import com.goeuro.api.service.LocationExportService;

/**
 * This class implements LocationExportService.
 * 
 * @author lizardo
 *
 */
public class LocationExportManager implements LocationExportService {

	private static final String CSV_SEPARATOR = ", ";
	private static final String CSV_NEWLINE = "\n";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.goeuro.api.service.LocationExportService#exportToCSV(java.lang.
	 * String, java.util.List)
	 */
	@Override
	public void exportToCSV(String cityName, List<Location> locations) throws IOException {

		FileWriter writer = null;

		try {
			cityName = cityName.replaceAll("\\ ", "_");
			writer = new FileWriter(cityName + ".csv");

			// Print each location at the CSV file
			for (Location location : locations) {
				writer.append(String.valueOf(location.getId()) + CSV_SEPARATOR);
				writer.append("\"" + location.getName() + "\"" + CSV_SEPARATOR);
				writer.append("\"" + location.getType() + "\"" + CSV_SEPARATOR);
				writer.append(String.valueOf(location.getLatitude()) + CSV_SEPARATOR);
				writer.append(String.valueOf(location.getLongitude()) + CSV_NEWLINE);
			}
		} finally {
			writer.close();
		}
	}
}
