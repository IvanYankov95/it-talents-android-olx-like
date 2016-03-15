package bg.ittalents.olxlike.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import bg.ittalents.olxlike.EditOffer;
import bg.ittalents.olxlike.R;
import model.Offer;

/**
 * Created by Mitakaa on 08-Mar-16.
 */
public class OfferAdapter extends ArrayAdapter<Offer> {

    Offer offer;
    Context context;

    public OfferAdapter(Context context, ArrayList<Offer> offers) {
        super(context, 0, offers);
        this.context = context;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        offer = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.offer_view_layout, parent, false);
        }

        Bitmap bmp = BitmapFactory.decodeByteArray(offer.getImages().get(0), 0, offer.getImages().get(0).length);
        ImageView imView = (ImageView) convertView.findViewById(R.id.viewImageOffer);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.titleViewOffer);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.priceViewOffer);
        TextView tvIDOffer = (TextView) convertView.findViewById(R.id.idOffer);
        Button editButton = (Button) convertView.findViewById(R.id.editOffer);
        // Populate the data into the template view using the data object


        imView.setImageBitmap(bmp);
        tvTitle.setText(offer.getName());
        tvPrice.setText(String.valueOf(offer.getPrice()));
        tvIDOffer.setText(String.valueOf(offer.getId()));
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditOffer.class);
                intent.putExtra("idOffer", getItemId(position));
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }
}
