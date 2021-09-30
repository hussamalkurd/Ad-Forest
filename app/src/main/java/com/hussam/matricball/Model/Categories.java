package com.hussam.matricball.Model;

public class Categories {

    private String Pname;
    private String image;
    private String pid;
    public Categories()
    {}

    public Categories(String pname, String image,String pid) {
        Pname = pname;
        this.image = image;
        this.pid = pid;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
