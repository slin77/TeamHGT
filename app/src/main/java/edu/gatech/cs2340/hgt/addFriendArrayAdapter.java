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
public class addFriendArrayAdapter extends ArrayAdapter<User>{
        private Context context;
        private List<User> allUsers;
        private String loggedInName;

    /**
     *
     * @param context
     * @param resource
     * @param objects
     */
    public addFriendArrayAdapter(Context context, int resource, List<User> objects) {
        super(context, resource, objects);
        this.context = context;
        this.allUsers = objects;
        this.loggedInName = context
                .getApplicationContext()
                .getSharedPreferences("userSession", 0)
                .getString("curUsername",null);
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
        User curUser = allUsers.get(position);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        FriendDB db = new FriendDB(context.getApplicationContext());

        View view = inflater.inflate(R.layout.add_friend_listitem, null);
        ImageView image = (ImageView) view.findViewById(R.id.afFriendImage);
        image.setImageResource(curUser.getImageResource());
        TextView text = (TextView) view.findViewById(R.id.afFriendName);
        text.setText(curUser.getName());
        ImageView isFriend = (ImageView)view.findViewById(R.id.afCross);
        isFriend.setImageResource(R.drawable.isfriend);

        if (db.areFriend(loggedInName, curUser.getUsername())) {
            isFriend.setVisibility(View.INVISIBLE);
        }

        return view;
    }


}
