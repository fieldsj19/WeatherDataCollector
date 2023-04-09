package project4;

import java.util.Scanner;
import java.util.ArrayList;

public class WeatherDataManager {
    private static ArrayList<WeatherData> weatherDataList = new ArrayList<>();
    private static boolean stop = false;
    private static Scanner scanner = new Scanner(System.in);

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

        if (index >= weatherDataList.size() || index < 0 || weatherDataList.size() == 0) {
            return false;
        }
        weatherDataList.remove(index);
        return true;
    }

    public static void display() {
        for (WeatherData data : weatherDataList) {
            System.out.println(data.toString());
        }
    }    

    public static int promptMenu() {
        int option = -1;

        System.out.println("Please select a menu option [1-4]:");
        System.out.println("""
            1. Add weather data.
            2. Remove weather data.
            3. Display all weather data.
            4. Exit
                """);
        try {
            option = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Please enter a valid integer in range [1-4]");
            scanner.next();
        }
        return option;
    }



    public static void main(String[] args) {
        while (!stop) {
            switch (promptMenu()) {
                case 1:
                    WeatherData weatherData = new WeatherData(0, 0, 0);
                    WeatherDataManager.addData(weatherData);
                    break;
                case 2:
                    boolean status = WeatherDataManager.removeData();
                    if (status == false) {
                        System.out.println("Index out of range.");
                    }
                    break;
                case 3:
                    WeatherDataManager.display();
                    break;
                case 4:
                    stop = true;
                    break;
            }
        }
    
    }
}
