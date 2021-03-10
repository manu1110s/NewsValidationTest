package dataprovider;

import enums.DriverType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/*This duty of this Class is to read the properties from the Configuration.properties file so that the hardcoding in code is avoided */
public class ConfigFileReader {
    private final Properties properties;
    private final String propertyFilePath = "configs//Configuration.properties";

    /*Constructor for the class*/
    public ConfigFileReader() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }

    /*This method gets the driver path from the Configuration.properties file where the drivers exe file is stored*/
    public String getDriverPath(){
        String driverPath = properties.getProperty("driverPath");
        if (driverPath != null) return driverPath;
        else throw new RuntimeException("driverPath not specified in the Configuration.properties file.");
    }

    /*This method gets the implicit wait time from the Configuration.properties file */
    public long getImplicitWait() {
        String implicitWait = properties.getProperty("implicitWait");
        if (implicitWait != null) return Long.parseLong(implicitWait);
        else throw new RuntimeException("implicitlyWait not specified in the Configuration.properties file.");
    }

    /*This method gets the time out wait time from the Configuration.properties file */
    public long getTimeOutInSeconds() {
        String timeOutInSeconds = properties.getProperty("timeOutinSeconds");
        if (timeOutInSeconds != null) return Long.parseLong(timeOutInSeconds);
        else throw new RuntimeException("timeOutInSeconds not specified in the Configuration.properties file.");
    }

    /*This method gets the News Page URL from the Configuration.properties file */
    public String getNewsPageUrl() {
        String url = properties.getProperty("newsHomePageUrl");
        if (url != null) return url;
        else throw new RuntimeException("url not specified in the Configuration.properties file.");
    }

    /*This method gets the Google Page URL from the Configuration.properties file */
    public String getGoogleHomePageUrl() {
        String url = properties.getProperty("googleHomePageUrl");
        if (url != null) return url;
        else throw new RuntimeException("url not specified in the Configuration.properties file.");
    }

    /*This method gets the Driver type chrome/IE/firefox from the Configuration.properties file */
    public DriverType getBrowser() {
        String browserName = properties.getProperty("browser");
        if (browserName == null || browserName.equals("chrome")) return DriverType.CHROME;
        else if (browserName.equalsIgnoreCase("firefox")) return DriverType.FIREFOX;
        else if (browserName.equals("iexplorer")) return DriverType.INTERNETEXPLORER;
        else
            throw new RuntimeException("Browser Name Key value in Configuration.properties is not matched : " + browserName);
    }

    /*This method gets the report config path from the Configuration.properties file */
    public String getReportConfigPath() {
        String reportConfigPath = properties.getProperty("reportConfigPath");
        if (reportConfigPath != null) return reportConfigPath;
        else
            throw new RuntimeException("Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");
    }
}
