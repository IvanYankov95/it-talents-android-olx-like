package model;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mitakaa on 30-Jan-16.
 */
public class Offer implements Comparable<Offer> {

    private boolean isActive;
    private Date dateOfCreating;
    private Date dateOfExpire;
    private Product product;
    private int quantity;

    public Offer(Product product, int quantity) throws IllegalArgumentException {
        if(product == null) {
            throw new IllegalArgumentException();
        }
        else {
            this.product = product;
        }
        if (quantity < 1) {
            throw new IllegalArgumentException();
        }
        else {
            this.quantity = quantity;
        }
        dateOfCreating = new Date();
        Calendar dateExpire = Calendar.getInstance();
        dateExpire.add(Calendar.MONTH, 1);
        dateOfExpire = dateExpire.getTime();
        isActive = true;
    }

    public boolean checkIsOnDate() {
        return isActive;
    }

    public void changeExpiredOffer() {
        if (dateOfExpire.after(new Date())) {
            isActive = false;
        }
    }

    public void changeExpireDay() {
        Calendar newDateExpire = Calendar.getInstance();
        newDateExpire.setTime(dateOfExpire);
        newDateExpire.add(Calendar.MONTH, 1);
        dateOfExpire = newDateExpire.getTime();
        isActive = true;
    }

    @Override
    public int compareTo(@NonNull Offer another) {
        if(dateOfCreating.after(another.dateOfCreating))
            return 1;
        if(dateOfCreating.before(another.dateOfCreating))
            return -1;

        return 0;
    }

}
