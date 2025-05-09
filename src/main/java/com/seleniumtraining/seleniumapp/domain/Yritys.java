package com.seleniumtraining.seleniumapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Table(name = "yritys", indexes = { @Index(name = "username_index", columnList = "username") })
@Entity
public class Yritys {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username = "";
    private String yritysNimi = "";
    private String toimitusjohtaja = "";
    private String yTunnus = "";
    private String postiosoite = "";
    private String postinumero = "";
    private String postitoimipaikka = "";
    private String puhelinnumero = "";
    private String sahkoposti = "";
    @Column(length = 5000)
    private String toimialakuvaus = "";
    private String wwwOsoite = "";
    private InterestStatusType interestStatus = null;

    public Yritys() {
    }

    public Yritys(String yritysNimi, String toimitusjohtaja, String yTunnus, String postiosoite,
            String postinumero, String postitoimipaikka, String puhelinnumero, String sahkoposti, String wwwOsoite,
            String toimialakuvaus,
            InterestStatusType interestStatus) {
        this.yritysNimi = yritysNimi;
        this.toimitusjohtaja = toimitusjohtaja;
        this.yTunnus = yTunnus;
        this.postiosoite = postiosoite;
        this.postinumero = postinumero;
        this.postitoimipaikka = postitoimipaikka;
        this.puhelinnumero = puhelinnumero;
        this.sahkoposti = sahkoposti;
        this.wwwOsoite = wwwOsoite;
        this.toimialakuvaus = toimialakuvaus;
        this.interestStatus = interestStatus;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getyTunnus() {
        return yTunnus;
    }

    public void setyTunnus(String yTunnus) {
        this.yTunnus = yTunnus;
    }

    public String getPostiosoite() {
        return postiosoite;
    }

    public void setPostiosoite(String postiosoite) {
        this.postiosoite = postiosoite;
    }

    public String getPostinumero() {
        return postinumero;
    }

    public void setPostinumero(String postinumero) {
        this.postinumero = postinumero;
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

    public String getWwwOsoite() {
        return wwwOsoite;
    }

    public void setWwwOsoite(String wwwOsoite) {
        this.wwwOsoite = wwwOsoite;
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

    @Override
    public String toString() {
        return "Yritys{" +
                "id=" + id +
                ", yritysNimi='" + yritysNimi + '\'' +
                ", username='" + username + '\'' +
                ", toimitusjohtaja='" + toimitusjohtaja + '\'' +
                ", postitoimipaikka='" + postitoimipaikka + '\'' +
                ", puhelinnumero='" + puhelinnumero + '\'' +
                ", sahkoposti='" + sahkoposti + '\'' +
                ", toimialakuvaus='" + toimialakuvaus + '\'' +
                ", interestStatus=" + interestStatus +
                '}';
    }

}