package bg.ittalents.olxlike.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bg.ittalents.olxlike.R;

/**
 * Created by owner on 12/03/2016.
 */
public class CategoryAdapter extends ArrayAdapter<String> {

    private Activity context;
    private List<String> categories;

    public CategoryAdapter(Activity context, List<String> categories) {
        super(context, R.layout.category_view, categories);
        this.context = context;
        this.categories = categories;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.category_view, parent, false);
        }

        String category = categories.get(position);

        ImageView image = (ImageView) convertView.findViewById(R.id.catImage);
        TextView name = (TextView) convertView.findViewById(R.id.catName);

        switch (category){
            case "Electronic" : image.setImageResource(R.drawable.electronic); break;
            case "Books" : image.setImageResource(R.drawable.book); break;
            case "Clothes" : image.setImageResource(R.drawable.clothes); break;
            case "Furniture" : image.setImageResource(R.drawable.furniture); break;
            case "Accessories" : image.setImageResource(R.drawable.accessories); break;
            case "Sport" : image.setImageResource(R.drawable.sport); break;
            case "Shoes" : image.setImageResource(R.drawable.shoes); break;
            case "Animals" : image.setImageResource(R.drawable.animals); break;
            case "Cosmetics" : image.setImageResource(R.drawable.cosmetics); break;
            case "Music" : image.setImageResource(R.drawable.music); break;
            case "Auto" : image.setImageResource(R.drawable.car); break;
        }
        name.setText(category);

        return convertView;
    }
}
