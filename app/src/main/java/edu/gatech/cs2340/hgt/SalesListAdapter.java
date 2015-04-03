package edu.gatech.cs2340.hgt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SalesListAdapter extends ArrayAdapter<Report> {
   private final Context context;
   private final List<Report> list;

    /**
     *  @param context the context of parent
     * @param list list of item within the list view
     */
   public SalesListAdapter(Context context, List<Report> list) {
       super(context, R.layout.sale_item, list);
       this.context = context;
       this.list = list;
   }

    /**
     * inflate the view for each sale item
     * @param position the position of sale
     * @param convertView convert view
     * @param parent parent
     * @return view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Report sale = list.get(position);
        LayoutInflater inflater = (LayoutInflater)context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder")
        View view = inflater.inflate(R.layout.sale_item, null);
        ImageView image = (ImageView)view.findViewById(R.id.itemImage);
        image.setImageResource(R.drawable.item_image2);
        TextView name = (TextView)view.findViewById(R.id.sale_name);
        name.setText(sale.getName());
        TextView price = (TextView)view.findViewById(R.id.threshold_price);
        price.setText(sale.getPrice() + "$ on:  1995/02/05");
        return view;
    }

    public Report getSale(int position) {
        return list.get(position);
    }

}
