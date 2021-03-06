package application.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import application.model.database.IData;

@Entity
public class ContainerStatus implements IData {

	// class members describing a status
	private long date;
	private float temperature;
	private float pressure;
	private float humidity;
	@Id
	private int csId;
	private int journeyId;
	private int containerId;

	// method to get the time difference between two statuses, used for the
	// getclosestStatus method
	public long getDifference(long date2) {
		long diff = date2 - date;
		return Math.abs(diff);
	}

	public ContainerStatus(long date, float temperature, float pressure, float humidity, int journeyId,
			int containerId) {
		super();
		this.date = date;
		this.temperature = temperature;
		this.pressure = pressure;
		this.humidity = humidity;
		this.journeyId = journeyId;
		this.containerId = containerId;
	}

	// Default Constructor for MySql
	public ContainerStatus() {

	}

	// Getters and Setters
	public int getJourneyId() {
		return journeyId;
	}

	public long getDate() {
		return date;
	}

	public float getTemperature() {
		return temperature;
	}

	public float getPressure() {
		return pressure;
	}

	public float getHumidity() {
		return humidity;
	}

	@Override
	public int getID() {
		return this.csId;
	}

	@Override
	public void setID(int id) {
		this.csId = id;

	}

	public int getContainerId() {
		return this.containerId;
	}

}
