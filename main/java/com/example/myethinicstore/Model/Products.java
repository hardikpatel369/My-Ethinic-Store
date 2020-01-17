package com.example.myethinicstore.Model;

public class Products {

    private String pname,description,price,category,date,time,pid,image;

    public Products(){}

    public Products(String pname, String description, String price, String category, String date, String time, String pid, String image) {
        this.pname = pname;
        this.description = description;
        this.price = price;
        this.category = category;
        this.date = date;
        this.time = time;
        this.pid = pid;
        this.image = image;

    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Products{" +
                "pname='" + pname + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", category='" + category + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", pid='" + pid + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
