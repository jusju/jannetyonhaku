package com.seleniumtraining.seleniumapp.chromedriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import io.github.bonigarcia.wdm.WebDriverManager;

// komponentti chromediverin alustamiseen ja lopettamiseen
@Component
public class ChromeDriverSetup {
    private ChromeOptions options;
    private WebDriver driver;

    public ChromeDriverSetup(ChromeDriverOptions chromeDriverOptions) {
        this.options = chromeDriverOptions.ChromeOptions();
    }

    public WebDriver startDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
        }
        return driver;
    }

    public void stopDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
