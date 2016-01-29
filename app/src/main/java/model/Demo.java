package model;

import java.util.concurrent.locks.Condition;

/**
 * Created by owner on 29/01/2016.
 */
public class Demo {

    public static void main(String[] args){
        Product jeans = new Product("Jeans", "Gotini danki", 20, null, ProductCondition.NEW, Category.CLOTHES);
        jeans.displayProduct();
    }


}
