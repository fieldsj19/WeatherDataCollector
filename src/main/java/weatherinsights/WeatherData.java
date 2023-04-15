package weatherinsights;

public class WeatherData {
    private double temperature;
    private int humidity;
    private int pressure;
    private double windSpeed;

    public WeatherData(double temperature, int humidity, int pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }
    public WeatherData(double temperature, int humidity, int pressure, double windSpeed) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
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

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String toString() {
        return "Temperature:" + temperature + ", Humidity:" + humidity + ", Pressure:" + pressure
        + ", Wind Speed: " + windSpeed;
    }
}
