package model;

/**
 * Created by owner on 29/01/2016.
 */
public class Product {

    private String name;
    private String description;
    private double price;
    private UserAcc seller;
    private ProductCondition condition;
    private Category category;

    public Product(String name, String description, double price, UserAcc user, ProductCondition condition, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.seller = user;
        this.condition = condition;
        this.category = category;
    }

    public void displayProduct(){
        System.out.println("Name: " + this.name);
        System.out.println("Category: " + this.category);
        System.out.println("Description: " + this.description);
        System.out.println("Price: " + this.price);
        //System.out.println("Seller: " + this.seller.getUserName());
        System.out.println("Product COndition: " + this.condition);
    }


    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name != null || name != " "){
            this.name = name;
        }
    }

    public UserAcc getSeller() {
        return seller;
    }

    public void setSeller(UserAcc seller) {
        this.seller = seller;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if(price > 0){
            this.price = price;
        }
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ProductCondition getCondition() {
        return condition;
    }

    public void setCondition(ProductCondition condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description != null) {
            this.description = description;
        }
    }
}
