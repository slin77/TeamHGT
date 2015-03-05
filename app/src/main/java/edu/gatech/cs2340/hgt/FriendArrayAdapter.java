package edu.gatech.cs2340.hgt;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by root on 2/18/15.
 */
public class FriendArrayAdapter extends ArrayAdapter<User> {
    private Context context;
    private List<User> friends;

    /**
     *
     * @param context
     * @param resource
     * @param objects
     */
    public FriendArrayAdapter(Context context, int resource, List<User> objects) {
        super(context, resource, objects);
        this.context = context;
        this.friends = objects;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = friends.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.friend_listitem, null);
        ImageView image = (ImageView)view.findViewById(R.id.ivFriendImage);
        image.setImageResource(user.getImageResource());
        TextView text = (TextView)view.findViewById(R.id.ivFriendName);
        text.setText(user.getName());

        return view;
    }
}
