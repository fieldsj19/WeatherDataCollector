package project4;

public class WeatherData {
    private double temperature;
    private int humidity;
    private int pressure;

    public WeatherData(double temperature, int humidity, int pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getPressure() {
        return pressure;
    }

    public String toString() {
        return "Temperature:" + temperature + ", Humidity:" + humidity + ", Pressure:" + pressure;
    }
}
