package model;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import bg.ittalents.olxlike.R;

/**
 * Created by Mitakaa on 08-Mar-16.
 */
public class OfferAdapter extends ArrayAdapter<Offer> {
    public OfferAdapter(Context context, ArrayList<Offer> offers) {
        super(context, 0, offers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Offer offer = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.offer_view_layout, parent, false);
        }

        ImageView imView = (ImageView) convertView.findViewById(R.id.viewImageOffer);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.titleViewOffer);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.priceViewOffer);
        // Populate the data into the template view using the data object

        //imView.setImageResource(offer.getImages().get(0));
        tvTitle.setText(offer.getName());
        tvPrice.setText(String.valueOf(offer.getPrice()));

        return convertView;
    }
}
