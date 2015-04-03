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


public class FriendArrayAdapter extends ArrayAdapter<User> {
    private final Context context;
    private final List<User> friends;

    /**
     *
     * @param context the context that display to the screen
     * @param resource the passed on information
     * @param objects the object that contain a list
     */
    public FriendArrayAdapter(Context context, int resource, List<User> objects) {
        super(context, resource, objects);
        this.context = context;
        this.friends = objects;
    }

    /**
     *
     * @param position previous position
     * @param convertView convertview that was passed on as a variable
     * @param parent the information passsed on
     * @return the new view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = friends.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.friend_listitem, parent);
        ImageView image = (ImageView)view.findViewById(R.id.ivFriendImage);
        image.setImageResource(user.getImageResource());
        TextView text = (TextView)view.findViewById(R.id.ivFriendName);
        text.setText(user.getName());

        return view;
    }
}
