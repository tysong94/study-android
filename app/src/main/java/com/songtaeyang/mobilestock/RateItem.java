package com.songtaeyang.mobilestock;

public class RateItem {

    // 변수
    String name = "";   // 종목명
    String rate = "";   // 등락률

    // 생성자
    public RateItem(String name, String rate) {
        this.name = name;
        this.rate = rate;
    }

    // 게터세터
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
