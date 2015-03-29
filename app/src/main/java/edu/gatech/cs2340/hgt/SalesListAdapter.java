package edu.gatech.cs2340.hgt;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by root on 3/5/15.
 */
public class SalesListAdapter extends ArrayAdapter<Sale> {
   private Context context;
   private List<Sale> list;

    /**
     *
     * @param context the context of parent
     * @param resouces the resources xml fils
     * @param list list of item within the list view
     */
   public SalesListAdapter(Context context,int resouces, List<Sale> list) {
       super(context, resouces, list);
       this.context = context;
       this.list = list;
   }

    /**
     * inflate the view for each sale item
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Sale sale = list.get(position);
        LayoutInflater inflater = (LayoutInflater)context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.sale_item, null);
        ImageView image = (ImageView)view.findViewById(R.id.itemImage);
        image.setImageResource(R.drawable.item_image2);
        TextView name = (TextView)view.findViewById(R.id.sale_name);
        name.setText(sale.getItemName());
        TextView price = (TextView)view.findViewById(R.id.threshold_price);
        price.setText(sale.getThresholdPrice() + "$ on:  1995/02/05");
        return view;
    }

}