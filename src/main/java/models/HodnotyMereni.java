package models;

import java.util.List;

public class HodnotyMereni {

    private String place;
    private long dateTime;
    private double temperature;
    private double pressure;
    private long precipation;

    public HodnotyMereni(String place, long datetime, double temperature, double pressure, long precipation) {
        this.place = place;
        this.dateTime = datetime;
        this.temperature = temperature;
        this.pressure = pressure;
        this.precipation = precipation;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public long getPrecipation() {
        return precipation;
    }

    public void setPrecipation(long precipation) {
        this.precipation = precipation;
    }

    @Override
    public String toString() {
        return "HodnotyMereni{" +
                "place='" + place + '\'' +
                ", dateTime=" + dateTime +
                ", temperature=" + temperature +
                ", pressure=" + pressure +
                ", precipation=" + precipation +
                '}';
    }
}
