package weatherinsights;

import java.util.Scanner;
import com.google.gson.*;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;
import java.io.InputStream;
public class WeatherDataManager {
    private static ArrayList<WeatherData> weatherDataList = new ArrayList<>();
    private static boolean stop = false;
    private static Scanner scanner = new Scanner(System.in);



    private static String getApiKey() {
        Properties properties = new Properties();
        String apiKey = null;

        try (InputStream inputStream = WeatherDataManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(inputStream);
            apiKey = properties.getProperty("api_key");
        } catch (IOException e) {
            System.out.println("Error while reading API key from config.properties file");
            e.printStackTrace();
        }

        return apiKey;
    }

    //Adds weather data to arraylist of WeatherData objects
    public static void addData(WeatherData weatherData) {
        System.out.println("Enter temp:");
        weatherData.setTemperature(scanner.nextDouble());

        System.out.println("Enter humidity:");
        weatherData.setHumidity(scanner.nextInt());

        System.out.println("Enter pressure:");
        weatherData.setPressure(scanner.nextInt());
        weatherDataList.add(weatherData);
    }

    public static boolean removeData() {
        int index;
        System.out.println("Enter index to remove:");
        index = scanner.nextInt();

        if (index >= weatherDataList.size() || index < 0) {
            return false;
        }
        weatherDataList.remove(index);
        return true;
    }

    //Prints out arrayList of WeatherData objects to console.
    public static void display() {
        for (WeatherData data : weatherDataList) {
            System.out.println(data.toString());
        }
    }
    

    //Reads file from user input adn
    public static void readFromFile() {
        System.out.println("Enter file name to read data from:");
        String filename = scanner.next();
        
        try {
            File file = new File(filename);
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNext()) {
                double temperature = fileScanner.nextDouble();
                int humidity = fileScanner.nextInt();
                int pressure = fileScanner.nextInt();

                WeatherData weatherData = new WeatherData(temperature, humidity, pressure);
                weatherDataList.add(weatherData);
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.\n");
        }
    }
    

    public static void writeToFile() {
        System.out.println("Enter filename to write data to:");
        String filename = scanner.next();
        
        try {
            FileWriter writer = new FileWriter(filename);

            for (WeatherData weatherData : weatherDataList) {
                writer.write(weatherData.getTemperature() + " " + weatherData.getHumidity() + " " + weatherData.getPressure() + "\n");
            }

            writer.close();
            System.out.println("Data has been written to the file.\n");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.\n");
        }
    }

    public static int promptMenu() {
        int option = -1;

        System.out.println("Please select a menu option [1-6]:");
        System.out.println("""
            1. Add weather data.
            2. Remove weather data.
            3. Display all weather data.
            4. Read weather data from file.
            5. Write weather data to file.
            6. Get current weather data.
            7. Exit program.
                """);
        try {
            option = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Please enter a valid integer in range [1-6]");
            scanner.next();
        }
        return option;
    }

    public static WeatherData fetchCurrentWeather(String locationName, String apiKey) throws Exception {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + locationName + "&appid=" + apiKey;
        JsonObject jsonObject = fetchJsonObjectFromUrl(url);

        JsonObject coordJson = jsonObject.getAsJsonObject("coord");
        if (coordJson == null) {
            throw new RuntimeException("Invalid response: 'coord' field missing.");
        }
        double lat = coordJson.get("lat").getAsDouble();
        double lon = coordJson.get("lon").getAsDouble();

        String url2 = "https://api.openweathermap.org/data/3.0/onecall?lat=" + lat + "&lon=" + lon + "&exclude=hourly&appid=" + apiKey + "&units=imperial";
        JsonObject jsonObject2 = fetchJsonObjectFromUrl(url2);

        JsonObject currentJson = jsonObject2.getAsJsonObject("current");
        if (currentJson == null) {
            throw new RuntimeException("Invalid response: 'current' field missing.");
        }
        JsonArray dailyJson = jsonObject2.getAsJsonArray("daily");
        if (dailyJson == null || dailyJson.size() == 0) {
            throw new RuntimeException("Invalid response: 'daily' field missing.");
        }

        String summary = dailyJson.get(0).getAsJsonObject().get("summary").getAsString();
        double temperature = currentJson.get("temp").getAsDouble();
        int humidity = currentJson.get("humidity").getAsInt();
        int pressure = currentJson.get("pressure").getAsInt();
        double windSpeed = currentJson.get("wind_speed").getAsDouble();


        return new WeatherData(temperature, humidity, pressure, windSpeed, summary);
    }

    private static JsonObject fetchJsonObjectFromUrl(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            Gson gson = new Gson();
            JsonObject errorJson = gson.fromJson(response.body(), JsonObject.class);
            String errorMessage = errorJson.get("message").getAsString();
            throw new RuntimeException("Failed to fetch weather data: " + errorMessage);
        }

        Gson gson = new Gson();
        return gson.fromJson(response.body(), JsonObject.class);
    }

    public static void main(String[] args) throws Exception {
        String apiKey = getApiKey();

        WeatherData weatherData = new WeatherData(0, 0, 0);
        while (!stop) {
            switch (promptMenu()) {
                case 1 -> WeatherDataManager.addData(weatherData);
                case 2 -> {
                    boolean status = WeatherDataManager.removeData();
                    if (!status) {
                        System.out.println("Index out of range.\n");
                    }
                }
                case 3 -> WeatherDataManager.display();
                case 4 -> WeatherDataManager.readFromFile();
                case 5 -> WeatherDataManager.writeToFile();
                case 6 -> {
                    System.out.println("Location Name:");
                    String locationName = scanner.next();
                    WeatherData currentWeather = fetchCurrentWeather(locationName, apiKey);
                    System.out.println(currentWeather.toString());
                }
                case 7 -> stop = true;
            }
        }
    }
}
