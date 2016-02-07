package model;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mitakaa on 30-Jan-16.
 * Merged with Product by Ivan on 07-Feb-16.
 */
public class Offer implements Comparable<Offer> {

    public enum Category {
        CLOTHES, ELECTRONIC, FURNITURE, SPORT, MUSIC, BOOK, ANIMALS, SHOES, COSMETICS, ACCESSORIES
    }

    public enum ProductCondition {
        NEW, USED
    }

    private String name;
    private String description;
    private double price;
    private UserAcc seller;
    private ProductCondition condition;
    private Category category;

    private boolean isActive;
    private Date creationDate;
    private Date expiryDate;

    public Offer(UserAcc seller, String name, String description, double price, ProductCondition productCondition, Category category) throws IllegalArgumentException {
        if(seller == null)
            throw new IllegalArgumentException();
        this.setPrice(price);
        this.name = name;
        this.setDescription(description);
        this.condition = productCondition;
        this.category = category;
        this.creationDate = new Date();
            Calendar dateExpire = Calendar.getInstance();
            dateExpire.add(Calendar.MONTH, 1);
        this.expiryDate = dateExpire.getTime();
        this.isActive = true;
    }

    public boolean checkIfActive() {
        return isActive;
    }

    public void autoSetIfActive() {
        if (expiryDate.after(new Date())) {
            isActive = false;
        }
    }

    public void switchIfActive(boolean isActive){
        this.isActive = isActive;
    }

    public void changeExpireDay() {
        Calendar newDateExpire = Calendar.getInstance();
        newDateExpire.setTime(expiryDate);
        newDateExpire.add(Calendar.MONTH, 1);
        expiryDate = newDateExpire.getTime();
        isActive = true;
    }

    @Override
    public int compareTo(@NonNull Offer another) {
        if(creationDate.after(another.creationDate))
            return 1;
        if(creationDate.before(another.creationDate))
            return -1;

        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) throws IllegalArgumentException{
        if(price < 0)
            throw new IllegalArgumentException();
        this.price = price;
    }

    public ProductCondition getCondition() {
        return condition;
    }

    public void setCondition(ProductCondition condition) {
        this.condition = condition;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
