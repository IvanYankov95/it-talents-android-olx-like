package model.dao;

import java.util.List;

import model.Offer;

/**
 * Created by owner on 01/03/2016.
 */
public interface IOfferDAO {

    long addOffer(Offer offer, long userId, String category);
    Offer getOffer(long id);
    List<Offer> getOffersByCategory(long categoryId);
    void deleteOffer(Offer offer);
    long updateOffer(Offer offer);
    String getCategory(long id);
    long getCategory(String name);
    List<byte[]> getImages(long offerId);

}
