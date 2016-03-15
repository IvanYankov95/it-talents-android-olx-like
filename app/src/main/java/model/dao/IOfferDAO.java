package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.Offer;

/**
 * Created by owner on 01/03/2016.
 */
public interface IOfferDAO {

    long addOffer(Offer offer, long userId, String category);
    Offer getOffer(long id);
    ArrayList<Offer> getOffersByCategory(String category);
    void deleteOffer(Offer offer);
    long updateOffer(Offer offer);
    String getCategory(long id);
    long getCategory(String name);
    List<byte[]> getImages(long offerId);
    ArrayList<String> getAllCategories();
    ArrayList<Offer> getOffers(String word);
    ArrayList<Offer> getOffersByUser(long userId);
    int getOffersCount();

}
