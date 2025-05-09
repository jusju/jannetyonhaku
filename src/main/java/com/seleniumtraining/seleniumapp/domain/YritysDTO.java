package com.seleniumtraining.seleniumapp.domain;

public class YritysDTO {
    private Long id;
    private String yritysNimi;
    private String toimitusjohtaja;
    private String postitoimipaikka;
    private String puhelinnumero;
    private String sahkoposti;
    private String toimialakuvaus;
    private InterestStatusType interestStatus;

    public YritysDTO() {
    }

    public YritysDTO(Long id, String yritysNimi, String toimitusjohtaja, String postitoimipaikka, String puhelinnumero,
            String sahkoposti, String toimialakuvaus, InterestStatusType interestStatus) {
        this.id = id;
        this.yritysNimi = yritysNimi;
        this.toimitusjohtaja = toimitusjohtaja;
        this.postitoimipaikka = postitoimipaikka;
        this.puhelinnumero = puhelinnumero;
        this.sahkoposti = sahkoposti;
        this.toimialakuvaus = toimialakuvaus;
        this.interestStatus = interestStatus;
    }

    public Long getId() {
        return id;
    }

    public String getYritysNimi() {
        return yritysNimi;
    }

    public void setYritysNimi(String yritysNimi) {
        this.yritysNimi = yritysNimi;
    }

    public String getToimitusjohtaja() {
        return toimitusjohtaja;
    }

    public void setToimitusjohtaja(String toimitusjohtaja) {
        this.toimitusjohtaja = toimitusjohtaja;
    }

    public String getPostitoimipaikka() {
        return postitoimipaikka;
    }

    public void setPostitoimipaikka(String postitoimipaikka) {
        this.postitoimipaikka = postitoimipaikka;
    }

    public String getPuhelinnumero() {
        return puhelinnumero;
    }

    public void setPuhelinnumero(String puhelinnumero) {
        this.puhelinnumero = puhelinnumero;
    }

    public String getSahkoposti() {
        return sahkoposti;
    }

    public void setSahkoposti(String sahkoposti) {
        this.sahkoposti = sahkoposti;
    }

    public String getToimialakuvaus() {
        return toimialakuvaus;
    }

    public void setToimialakuvaus(String toimialakuvaus) {
        this.toimialakuvaus = toimialakuvaus;
    }

    public InterestStatusType getInterestStatus() {
        return interestStatus;
    }

    public void setInterestStatus(InterestStatusType interestStatus) {
        this.interestStatus = interestStatus;
    }
}
