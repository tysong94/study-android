package com.songtaeyang.mobilestock;

public class StockItem {

    // 변수
    String code = "";   // 종목 코드
    String name = "";   // 종목명
    String cost = "";   // 현재가
    String updn= "";    // 전일비
    String rate = "";   // 등락률

    // 생성자
    public StockItem(String code, String name, String cost, String updn, String rate) {
        this.code = code;
        this.name = name;
        this.cost = cost;
        this.updn = updn;
        this.rate = rate;
    }

    // 게터세터
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getUpdn() {
        return updn;
    }

    public void setUpdn(String updn) {
        this.updn = updn;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    // toString
    @Override
    public String toString() {
        return "StockItem{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", cost='" + cost + '\'' +
                ", updn='" + updn + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }
}
