package project4;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

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
        System.out.println("Enter file name to write data to:");
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
            6. Exit
                """);
        try {
            option = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Please enter a valid integer in range [1-6]");
            scanner.next();
        }
        return option;
    }

    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData(0, 0, 0);
        while (!stop) {
            switch (promptMenu()) {
                case 1:
                    WeatherDataManager.addData(weatherData);
                    break;
                case 2:
                    boolean status = WeatherDataManager.removeData();
                    if (status == false) {
                        System.out.println("Index out of range.\n");
                    }
                    break;
                case 3:
                    WeatherDataManager.display();
                    break;
                case 4:
                    WeatherDataManager.readFromFile();
                    break;
                case 5:
                    WeatherDataManager.writeToFile();
                    break;
                case 6:
                    stop = true;
                    break;
            }
        }
    }
}
