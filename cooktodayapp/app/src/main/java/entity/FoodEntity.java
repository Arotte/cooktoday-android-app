package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.SplittableRandom;

public class FoodEntity implements Serializable {
    private static final long serialVersionUID = 1l;
    // dish id
    private int id;
    // dish name
    private String foodName;
    // dish taste
    private String taste;
    // price
    private Double price;
    // number
    private int count;
    // dish pic
    private String foodPic;
    // dish type
    private  String foodType;
    private String ycl; // metarial
    private String zf; // way to do
    private Long viewnum; // view numbers
    public String getFoodType(){ return foodType; }
    public void setFoodType(String foodType){this.foodType = foodType;}
    public int getId(){return id;}
    public void setId(int id){this.id = id; }
    public String getFoodName(String foodName){this.foodName= foodName;}
    public String getTaste(){
        return taste;
    }
    public void setTaste(String taste){this.taste = taste;}
    public Double getPrice() {return price;}
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getFoodPic(){return foodPic;}
    public void setFoodPic(String foodPic) {
        this.foodPic = foodPic;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getYcl() {
        return ycl;
    }

    public void setYcl(String ycl) {
        this.ycl = ycl;
    }

    public String getZf() {
        return zf;
    }

    public void setZf(String zf) {
        this.zf = zf;
    }

    public Long getViewnum() {
        return viewnum;
    }

    public void setViewnum(Long viewnum) {
        this.viewnum = viewnum;
    }
}
























