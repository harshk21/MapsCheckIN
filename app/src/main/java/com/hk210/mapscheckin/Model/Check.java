package com.hk210.mapscheckin.Model;

public class Check {
    private String lat,lang,time;

    public Check(String time, String lang, String lat) {
        this.lat = lat;
        this.lang = lang;
        this.time = time;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
