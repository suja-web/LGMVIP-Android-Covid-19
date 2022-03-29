package com.example.trackercovid_19.Model;

public class District {

    private String districtName, active, recovered, confirmed, deaths;

    public District(String districtName, String active, String recovered, String confirmed, String deaths) {
        this.districtName = districtName;
        this.active = active;
        this.recovered = recovered;
        this.confirmed = confirmed;
        this.deaths = deaths;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }
}
