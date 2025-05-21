package testBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseClass {
    
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>(); // Launch browser using Web Driver Interface
    public Properties p;
    private static final Logger logger = LogManager.getLogger(BaseClass.class);
    
    public static WebDriver getDriver() {
        return driver.get();
    }
    
    @BeforeClass(groups = { "sanity", "regression", "datadriven" })
    @Parameters({"os","browser"})
    public void openApp(String os, String br) {
        logger.info("Starting test execution with OS: {} and Browser: {}", os, br);
        
        try {
            // Load properties file
            logger.debug("Attempting to load config.properties file");
            FileReader file = new FileReader(".//src//test/resources//config.properties");
            p = new Properties();
            p.load(file);
            logger.debug("Successfully loaded properties file");
            
            WebDriver localDriver = null;
            
            if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
                DesiredCapabilities capabilities = new DesiredCapabilities();

                // os
                if (os.equalsIgnoreCase("windows")) {
                    capabilities.setPlatform(Platform.WIN11);
                } else if (os.equalsIgnoreCase("mac")) {
                    capabilities.setPlatform(Platform.MAC);
                } else {
                    System.out.println("No matching os");
                    return;
                }

                String gridURL = "http://localhost:4444/wd/hub"; // Update if needed
                
                // Browser selection
                logger.info("Initializing browser: {}", br);
                switch (br.toLowerCase()) {
                    case "chrome":
                        logger.debug("Setting up Chrome browser");
                        ChromeOptions chromeOptions = new ChromeOptions();
                        localDriver = new RemoteWebDriver(URI.create(gridURL).toURL(), chromeOptions.merge(capabilities));
                        break;
                    case "firefox":
                        logger.debug("Setting up Firefox browser");
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        localDriver = new RemoteWebDriver(URI.create(gridURL).toURL(), firefoxOptions.merge(capabilities));
                        
                        try {
                            logger.debug("Creating WebDriverWait for Firefox");
                            WebDriverWait wait = new WebDriverWait(localDriver, Duration.ofSeconds(10));
                        } catch (Exception e) {
                            logger.error("Error creating WebDriverWait: {}", e.getMessage(), e);
                        }
                        break;
                    case "edge":
                        logger.debug("Setting up Edge browser");
                        EdgeOptions edgeOptions = new EdgeOptions();
                        localDriver = new RemoteWebDriver(URI.create(gridURL).toURL(), edgeOptions.merge(capabilities));
                        break;
                    default:
                        logger.error("No matching browser for: {}", br);
                        return;
                }
            } else if (p.getProperty("execution_env").equalsIgnoreCase("local")) {
                switch (br.toLowerCase()) {
                    case "chrome":
                        localDriver = new ChromeDriver();
                        logger.debug("ChromeDriver initialized");
                        break;
                    case "edge":
                        localDriver = new EdgeDriver();
                        logger.debug("EdgeDriver initialized");
                        break;
                    case "firefox":
                        localDriver = new FirefoxDriver();
                        logger.debug("FirefoxDriver initialized");
                        break;
                    default:
                        logger.error("Unsupported browser: {}", br);
                        return;
                }
            }
            
            driver.set(localDriver);
            logger.debug("Driver assigned to thread-local storage");
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
            getDriver().manage().window().maximize();
            getDriver().get(p.getProperty("appURL"));

            logger.info("Application launched successfully: {}", p.getProperty("appURL"));

        } catch (IOException e) {
            logger.error("Failed to load config.properties file", e);
        } catch (Exception e) {
            logger.error("Error occurred while launching the application", e);
        }
    }
    
    @AfterClass(groups = { "sanity", "regression"})
    public void closeApp() {
        try {
            getDriver().quit();
            logger.info("Browser session closed successfully");
        } catch (Exception e) {
            logger.error("Failed to close the browser session", e);
        }
    }
    
    public String captureScreen(String tname) {
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";

        try {
            File sourceFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
            File targetFile = new File(targetFilePath);

            if (sourceFile.renameTo(targetFile)) {
                logger.info("Screenshot captured: {}", targetFilePath);
            } else {
                logger.warn("Screenshot could not be renamed/moved");
            }

        } catch (Exception e) {
            logger.error("Failed to capture screenshot for test: {}", tname, e);
        }

        return targetFilePath;
    }
}			