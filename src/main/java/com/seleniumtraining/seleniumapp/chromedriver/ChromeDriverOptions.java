package com.seleniumtraining.seleniumapp.chromedriver;

import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import java.util.Map;

public class ChromeDriverOptions {
    public ChromeOptions ChromeOptions() {
        // ChromeOptions luodaan
        ChromeOptions options = new ChromeOptions();

        // 1. Simuloidaan normaalia selaimen käyttäjää
        options.addArguments("start-maximized"); // Käynnistää koko näytölle
        options.addArguments(
                "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"); // Normaali
                                                                                                                                               // käyttäjäagentti
        options.addArguments("--disable-blink-features=AutomationControlled"); // Poistaa automaatio-ominaisuudet

        // 2. Estetään pop-up ikkunat
        options.addArguments("--disable-popup-blocking"); // Ei anna pop-uppien häiritä
        options.addArguments("--disable-notifications"); // Ei salli ilmoituksia

        // 3. Latausasetukset (estää kyselyt, minne tallennetaan)
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.prompt_for_download", false);
        prefs.put("plugins.always_open_pdf_externally", true); // Avaa PDF:t suoraan
        options.setExperimentalOption("prefs", prefs);

        // 4. Poistetaan Seleniumin tunnistettavat ominaisuudet
        options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
        options.setExperimentalOption("useAutomationExtension", false);

        return options;
    }
}
