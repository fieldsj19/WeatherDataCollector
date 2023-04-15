# Weather Data Manager
Weather Data Manager is a java-based project to manage, store, and retrieve weather data. The project uses Maven for dependency management.

## Features
* Adding, removing, and displaying weather data manually
* Reading weather data from a file and writing it to a file
* Fetching current weather data from OpenWeatherMap API
* Incorporating latitude and longitude data to fetch location-based weather information
* Using a configuration file to store API key
* WeatherData class with temperature, humidity, pressure, and wind speed attributes


## Dependencies
* Gson 2.10.1
- Apache HttpClient 5.2.1

## Classes
***WeatherData:*** A class representing a single weather data record, containing temperature, humidity, and pressure.

***WeatherDataManager:*** A class that manages a collection of WeatherData objects, providing methods to add, remove, and display weather data.

## Usage
1. Compile the Java files in terminal:  
***javac project4/*.java***

2. Run the WeatherDataManager class:  
***java project4.WeatherDataManager***

3. Follow the on-screen instructions to add, remove, and display weather data.

## Future Improvements
* Save and load weather data from a file.
* Fetch weather data from a web API.
* Provide more detailed statistics, such as average temperature or humidity.
* Implement a better user interface with input validation and error handling.

Feel free to contribute to this project by submitting a pull request.
