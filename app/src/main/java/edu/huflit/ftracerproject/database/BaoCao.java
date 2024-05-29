package edu.huflit.ftracerproject.database;

public class BaoCao {
    String username;
    Double money;
    String datetime;
    String kind ;


    public BaoCao(String username, Double money, String datetime, String kind) {
        this.username = username;
        this.money = money;
        this.datetime = datetime;
        this.kind = kind;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
