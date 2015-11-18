package com.goeuro.api.entity;

/**
 * This entity represents a Location.
 * 
 * @author lizardo
 */
public class Location {

	private long id;
	private String name;
	private String type;
	private double latitude;
	private double longitude;

	/**
	 * Default constructor.
	 * 
	 * @param id
	 * @param name
	 * @param type
	 * @param latitude
	 * @param longitude
	 * @throws IllegalArgumentException
	 */
	public Location(long id, String name, String type, double latitude, double longitude)
			throws IllegalArgumentException {
		super();
		this.setId(id);
		this.setName(name);
		this.setType(type);
		this.setLatitude(latitude);
		this.setLongitude(longitude);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {

		if (latitude >= -90.0000 && latitude <= 90.0000) {
			this.latitude = latitude;
		} else {
			throw new IllegalArgumentException("Illegal latitude at location: " + this.getId() + ".");
		}
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		if (longitude >= -180.0000 && longitude <= 180.0000) {
			this.longitude = longitude;
		} else {
			throw new IllegalArgumentException("Illegal longitude at location: " + this.getId() + ".");
		}
	}
}
