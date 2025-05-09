package com.seleniumtraining.seleniumapp;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.seleniumtraining.seleniumapp.chromedriver.ChromeDriverSetup;
import com.seleniumtraining.seleniumapp.services.DataScraper;

@SpringBootApplication
public class SeleniumappApplication implements CommandLineRunner {

	@Autowired
	private DataScraper scraper;

	@Autowired
	private ChromeDriverSetup driverManager;

	public static void main(String[] args) {
		SpringApplication.run(SeleniumappApplication.class, args);
	}

	@Override
	public void run(String... args) {
		WebDriver driver = driverManager.startDriver();
		try {
			String url = "https://www.kauppalehti.fi/yritykset/toimialat/atklaitteisto-ja-ohjelmistokonsultointi/62020?page=";
			List<String> urList = scraper.searchCompanyUrls(driver, url, 20);
			scraper.searchCompanyData(driver, urList);
		} finally {
			driverManager.stopDriver();
		}
	}
}
// testidata

// List<String> urList =
// List.of("https://www.kauppalehti.fi/yritykset/yritys/09514243",
// "https://www.kauppalehti.fi/yritykset/yritys/32176748",
// "https://www.kauppalehti.fi/yritykset/yritys/31706313");
// scraper.searchCompanyData(driver, urList);