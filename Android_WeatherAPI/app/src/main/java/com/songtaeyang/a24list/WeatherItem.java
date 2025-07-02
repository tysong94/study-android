package com.songtaeyang.a24list;

public class WeatherItem {

    // 변수
    int sky;            // 하늘 상태코드
    String day = "";    // 날짜
    String hour = "";   // 시각
    String temp = "";   // 현재 온도
    String tmx = "";    // 최고 온도
    String tmn = "";    // 최저 온도
    String pty = "";    // 강수 상태코드
    String wfKor = "";  // 날씨 한국어

    // 생성자
    public WeatherItem(int sky, String day, String hour, String temp, String tmx, String tmn, String pty, String wfKor) {
        this.sky = sky;
        this.day = day;
        this.hour = hour;
        this.temp = temp;
        this.tmx = tmx;
        this.tmn = tmn;
        this.pty = pty;
        this.wfKor = wfKor;
    }

    // 게터세터
    public int getSky() {
        return sky;
    }

    public void setSky(int sky) {
        this.sky = sky;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTmx() {
        return tmx;
    }

    public void setTmx(String tmx) {
        this.tmx = tmx;
    }

    public String getTmn() {
        return tmn;
    }

    public void setTmn(String tmn) {
        this.tmn = tmn;
    }

    public String getPty() {
        return pty;
    }

    public void setPty(String pty) {
        this.pty = pty;
    }

    public String getWfKor() {
        return wfKor;
    }

    public void setWfKor(String wfKor) {
        this.wfKor = wfKor;
    }

    @Override
    public String toString() {
        return "WeatherItem{" +
                "day='" + day + '\'' +
                ", hour='" + hour + '\'' +
                ", sky='" + sky + '\'' +
                ", pty='" + pty + '\'' +
                ", wfKor='" + wfKor + '\'' +
                '}';
    }
}
