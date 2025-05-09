package com.seleniumtraining.seleniumapp.services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seleniumtraining.seleniumapp.domain.Yritys;
import com.seleniumtraining.seleniumapp.domain.YritysRepository;

@Service
public class DataScraper {

    @Autowired
    private YritysRepository yritysRepository;

    // CSS-selektorit yrityksen tiedoille
    String odotettavaElementtiEnnenAvaamista = "h2#basic-information + ul li span span";
    String yritykseStatusAktiivinen = ".sc-5564dx-3.lnxvuP";
    String yrityksenStatusLopettanut = ".sc-5564dx-3.cdlHEM";
    String yrityksenNimi = ".sc-16wyvoq-1.sc-5564dx-1.iFCgCK.gpFhZT";
    String yritykserDataElementti = "h2#basic-information + ul li";
    String lueLisääPainike = ".sc-pek81u-0.kErPiI.sc-pymjmu-2.fqoWtH";
    String username = "publicTest";

    // haetaan yritysten listaus-sivulta sivun yritysten url-osoitteet
    // ja tallennetaan ne listaan
    public List<String> searchCompanyUrls(WebDriver driver, String baseUrl, int totalPages) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<String> yritysUrls = new ArrayList<>();

        for (int page = 1; page <= totalPages; page++) {
            String url = baseUrl + page;
            driver.get(url);

            // Odotetaan, että elementit latautuvat
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#main_content_anchor ul li a")));

            // Haetaan yritysten URL-osoitteet
            List<WebElement> yritykset = driver.findElements(By.cssSelector("div#main_content_anchor ul li a"));
            for (WebElement elementti : yritykset) {
                try {
                    String yritysUrl = elementti.getAttribute("href");
                    System.out.println(yritysUrl);
                    yritysUrls.add(yritysUrl);
                } catch (StaleElementReferenceException e) {
                    System.out.println("Elementti ei ole enää kelvollinen: " + e.getMessage());
                }
            }
        }

        return yritysUrls;
    }

    public void searchCompanyData(WebDriver driver, List<String> yritykset) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String status = "";

        for (String yritysSivu : yritykset) {
            Yritys yritys = new Yritys();
            String datatype = "";
            String data = "";
            driver.get(yritysSivu);

            try {
                wait.until(
                        ExpectedConditions.presenceOfElementLocated(By.cssSelector(odotettavaElementtiEnnenAvaamista)));

                // Haetaan yrityksen status
                try {
                    status = driver.findElement(By.cssSelector(yritykseStatusAktiivinen)).getText();
                } catch (Exception e) {
                    status = driver.findElement(By.cssSelector(yrityksenStatusLopettanut)).getText();
                }

                // Haetaan yrityksen tiedot, jos status on aktiivinen
                if (status.equals("AKTIIVINEN")) {
                    yritys.setWwwOsoite(yritysSivu);
                    try {
                        String[] yritysotsikko = driver.findElement(By.cssSelector(yrityksenNimi))
                                .getText().split("\n");
                        yritys.setYritysNimi(yritysotsikko[0].trim());
                    } catch (Exception e) {
                        System.out.println("Yrityksen nimi ei löytynyt: ");
                    }

                    List<WebElement> companyData = driver.findElements(By.cssSelector(yritykserDataElementti));

                    try {
                        WebElement luelisää = driver.findElement(By.cssSelector(lueLisääPainike));
                        luelisää.click();
                    } catch (Exception e) {
                    }

                    for (WebElement dataRow : companyData) {
                        try {
                            wait.until(ExpectedConditions.visibilityOf(dataRow));
                            String[] typeAndData = dataRow.getText()
                                    .replace("(YTJ)", "").replace("Katso sijainti kartalta", "")
                                    .replace("(Kaupparekisteri)", "").replace("\n", "").replace("Piilota", "")
                                    .replace("Lue lisää", "").replace("(PRH)", "").trim()
                                    .split(":");

                            datatype = typeAndData[0];
                            data = typeAndData[1];

                            if (datatype.equals("Y-tunnus")) {
                                yritys.setyTunnus(data);
                                System.out.println("Y-tunnus: ");
                                System.out.println(data);
                            } else if (datatype.equals("Yrityksen nimi")) {
                                System.out.println("Yrityksen nimi: ");
                                System.out.println(data);
                                yritys.setYritysNimi(data);
                            } else if (datatype.equals("Toimitusjohtaja")) {
                                System.out.println("Toimitusjohtaja: ");
                                System.out.println(data);
                                yritys.setToimitusjohtaja(data);
                            } else if (datatype.equals("Postiosoite")) {
                                String[] osoitedata = data.split(",");
                                yritys.setPostiosoite(osoitedata[0]);
                                String[] postinumeroJaPostitoimipaikka = osoitedata[1].trim().split(" ");
                                System.out.println("Postinumero ja postitoimipaikka: ");
                                System.out.println(postinumeroJaPostitoimipaikka[0] + " "
                                        + postinumeroJaPostitoimipaikka[1]);
                                yritys.setPostinumero(postinumeroJaPostitoimipaikka[0]);
                                yritys.setPostitoimipaikka(postinumeroJaPostitoimipaikka[1]);
                            } else if (datatype.equals("Puhelin")) {
                                System.out.println("Puhelin: ");
                                System.out.println(data);
                                yritys.setPuhelinnumero(data);
                            } else if (datatype.equals("Sähköposti")) {
                                System.out.println("Sähköposti: ");
                                System.out.println(data);
                                yritys.setSahkoposti(data);
                            } else if (datatype.equals("Toimialakuvaus")) {
                                System.out.println("Toimialakuvaus: ");
                                System.out.println(data);
                                yritys.setToimialakuvaus(data);
                            }
                        } catch (Exception e) {
                            System.out.println("Elementti ei ole enää kelvollinen: ");
                            System.out.println(e.getMessage());
                        }
                    }

                    if (!yritys.getPuhelinnumero().isEmpty() || !yritys.getSahkoposti().isEmpty()) {
                        yritys.setUsername(username);
                        yritysRepository.save(yritys);
                    } else {
                        System.out.println(
                                "Yrityksellä ei ole puhelinnumeroa tai sähköpostia: " + yritys.getYritysNimi());
                    }
                }
            } catch (TimeoutException e) {
                System.out.println("Elementtiä ei löytynyt ajoissa: ");
            }
        }
    }
}