<h1>WeatherAppJava </h1>
<h2>Introduction</h2>
<p>
    The Weather App is a Java-based application that provides users with real-time weather information fetched from the external API. Users can enter a location, and can retrieves weather details, including temperature, weather condition, humidity, and wind speed.
</p>

<p align="center">
    <img src="" align="center">
</p>

<h2>Accuracy</h2>
    <img src="" align="center">


<h2>Technologies Used</h2>

<ul>
  <li>Java 20</li>
  <li><a href="https://code.google.com/archive/p/json-simple/downloads">JSON Simple</a> - Used to parse and read through JSON data</li>
  <li><a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.net/java/net/HttpURLConnection.html">HTTPURLConnection</a>: Java's built-in library for making HTTP requests to fetch data from external APIs.</li>
</ul>
<h3>API used</h3>
<ul>
<li><a href="https://open-meteo.com/en/docs/geocoding-api">Geolocation API</a> - Retrieves geographic coordinates, latitude and longitude for given location name</li>
<li><a href="https://open-meteo.com/en/docs#latitude=33.767&longitude=-118.1892">Weather Forecast API</a> - Fetch the latest weather data of location from the external API and the GUI will display this data to the user</li>
<h2>Class involved</h2>

<h3>3.1. AppLauncher</h3>
<p>
    <strong>Description:</strong> The AppLauncher class is the entry point for the Weather App. It sets up the graphical interface and presents the main application window.
</p>

<h3>3.2. WeatherAppGui</h3>
<p>
    <strong>Description:</strong> WeatherAppGui is responsible for displaying weather details for a chosen location in the Weather App's graphical interface. It manages the layout and presentation of various GUI elements like text fields, labels, buttons, and images. Additionally, it facilitates user interaction by providing an interface for entering locations and updating weather information accordingly.
</p>

<h3>3.3. WeatherApp</h3>
<p>
    <strong>Description:</strong> WeatherApp handles the backend operations of the Weather App. It retrieves weather data from an external API by fetching geographic coordinates for specified locations. Furthermore, it includes functions to convert weather codes into understandable weather conditions and manages API requests seamlessly. Acting as a mediator between the GUI and external weather data source, WeatherApp ensures accurate retrieval and presentation of weather information.
</p>


