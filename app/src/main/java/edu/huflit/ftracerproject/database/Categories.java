package edu.huflit.ftracerproject.database;

public class Categories {
    public String idcate;
    public String Namecate;
    public String ImgCate;
    public Double total;
    public Categories(String idcate, String namecate, String imgCate) {
        this.idcate = idcate;
        Namecate = namecate;
        ImgCate = imgCate;
    }

    public Categories(String idcate, String namecate, String imgCate, Double total) {
        this.idcate = idcate;
        Namecate = namecate;
        ImgCate = imgCate;
        this.total = total;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getIdcate() {
        return idcate;
    }

    public void setIdcate(String idcate) {
        this.idcate = idcate;
    }

    public String getNamecate() {
        return Namecate;
    }

    public void setNamecate(String namecate) {
        Namecate = namecate;
    }

    public String getImgCate() {
        return ImgCate;
    }

    public void setImgCate(String imgCate) {
        ImgCate = imgCate;
    }
}
