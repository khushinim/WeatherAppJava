import org.json.simple.JSONObject;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGui extends JFrame {
    private JSONObject weatherData;
    private BackgroundPanel backgroundPanel;

    public WeatherAppGui(){
        // setup our gui and add a title
        super("Weather App");

        // configure gui to end the program's process once it has been closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // set the size of our gui (in pixels)
        setSize(450, 650);

        // load our gui at the center of the screen
        setLocationRelativeTo(null);

        // prevent any resize of our gui
        setResizable(false);

        // add our custom background panel
        backgroundPanel = new BackgroundPanel();
        setContentPane(backgroundPanel);

        // set the layout of the background panel to null
        backgroundPanel.setLayout(null);

        addGuiComponents(backgroundPanel);
    }

    private void addGuiComponents(JPanel panel){
        // Create a Font object for "Andalus"
        Font andalusPlain24 = new Font("Andalus", Font.PLAIN, 24);
        Font andalusBold48 = new Font("Andalus", Font.BOLD, 48);
        Font andalusPlain32 = new Font("Andalus", Font.PLAIN, 32);
        Font andalusPlain16 = new Font("Andalus", Font.PLAIN, 16);

        // search field
        JTextField searchTextField = new JTextField();

        // set the location and size of our component
        searchTextField.setBounds(15, 15, 351, 45);

        // change the font style and size
        searchTextField.setFont(andalusPlain24);
        searchTextField.setForeground(Color.BLACK);  // set text color to black

        panel.add(searchTextField);

        // weather image
        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy.png"));
        weatherConditionImage.setBounds(0, 125, 450, 217);
        panel.add(weatherConditionImage);

        // temperature text
        JLabel temperatureText = new JLabel("10\u00B0 C"); // Initial temperature text with degree symbol
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setFont(andalusBold48);
        temperatureText.setForeground(Color.BLACK);  // set text color to black

        // center the text
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(temperatureText);

        // weather condition description
        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0, 405, 450, 36);
        weatherConditionDesc.setFont(andalusPlain32);
        weatherConditionDesc.setForeground(Color.BLACK);  // set text color to black
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(weatherConditionDesc);

        // humidity image
        JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
        humidityImage.setBounds(15, 500, 74, 66);
        panel.add(humidityImage);

        // humidity text
        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(andalusPlain16);
        humidityText.setForeground(Color.BLACK);  // set text color to black
        panel.add(humidityText);

        // windspeed image
        JLabel windspeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        windspeedImage.setBounds(220, 500, 74, 66);
        panel.add(windspeedImage);

        // windspeed text
        JLabel windspeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windspeedText.setBounds(310, 500, 85, 55);
        windspeedText.setFont(andalusPlain16);
        windspeedText.setForeground(Color.BLACK);  // set text color to black
        panel.add(windspeedText);

        // search button
        JButton searchButton = new JButton(loadImage("src/assets/search.png"));

        // change the cursor to a hand cursor when hovering over this button
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 47, 45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get location from user
                String userInput = searchTextField.getText();

                // validate input - remove whitespace to ensure non-empty text
                if(userInput.replaceAll("\\s", "").length() <= 0){
                    return;
                }

                // retrieve weather data
                weatherData = WeatherApp.getWeatherData(userInput);

                // update gui

                // update weather image
                String weatherCondition = (String) weatherData.get("weather_condition");

                // depending on the condition, we will update the weather image that corresponds with the condition
                switch(weatherCondition){
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("src/assets/clear.png"));
                        backgroundPanel.setWeatherCondition("Clear");
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("src/assets/cloudy.png"));
                        backgroundPanel.setWeatherCondition("Cloudy");
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("src/assets/rain.png"));
                        backgroundPanel.setWeatherCondition("Rain");
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("src/assets/snow.pngImage"));
                        backgroundPanel.setWeatherCondition("Snow");
                        break;
                }

                // update temperature text
                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + "\u00B0 C");

                // update weather condition text
                weatherConditionDesc.setText(weatherCondition);

                // update humidity text
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                // update windspeed text
                double windspeed = (double) weatherData.get("windspeed");
                windspeedText.setText("<html><b>Windspeed</b> " + windspeed + "km/h</html>");
            }
        });
        panel.add(searchButton);
    }

    // used to create images in our gui components
    private ImageIcon loadImage(String resourcePath){
        try{
            // read the image file from the path given
            BufferedImage image = ImageIO.read(new File(resourcePath));

            // returns an image icon so that our component can render it
            return new ImageIcon(image);
        }catch(IOException e){
            e.printStackTrace();
        }

        System.out.println("Could not find resource");
        return null;
    }

    // custom JPanel class to paint a gradient background based on weather condition
    class BackgroundPanel extends JPanel {
        private String weatherCondition = "Cloudy"; // default condition

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();

            Color color1;
            Color color2;

            switch (weatherCondition) {
                case "Clear":
                    color1 = new Color(255, 255, 53); //light YELLOW
                    color2 = new Color(255, 255, 153);
                    break;
                case "Cloudy":
                    color1 = new Color(135, 206, 235); // light blue
                    color2 = new Color(76, 147, 234);  // dark blue
                    break;
                case "Rain":
                    color1 = new Color(77, 160, 234); // steel blue
                    color2 = new Color(40, 62, 119);  // dark blue
                    break;
                case "Snow":
                    color1 = new Color(232, 251, 255);
                    color2 = new Color(154, 229, 239); // light blue
                    break;
                default:
                    color1 = new Color(135, 206, 235); // default to light blue
                    color2 = new Color(25, 25, 112);  // default to dark blue
                    break;
            }

            GradientPaint gp1 = new GradientPaint(0, 0, color1, width / 2, height / 2, color2, true);
            GradientPaint gp2 = new GradientPaint(width, height, color1, width / 2, height / 2, color2, true);
            g2d.setPaint(gp1);
            g2d.fillRect(0, 0, width, height);
            g2d.setPaint(gp2);
            g2d.fillRect(0, 0, width, height);
        }

        public void setWeatherCondition(String weatherCondition) {
            this.weatherCondition = weatherCondition;
            repaint(); // trigger a repaint to update the background
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WeatherAppGui().setVisible(true);
            }
        });
    }
}