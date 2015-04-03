package edu.gatech.cs2340.hgt;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class FriendDetailFragment extends Fragment {
    private TextView name;
    private ImageView profileImg;
    private TextView email;
    private TextView shortDes;
    private RatingBar rate;
    private Button unfriendBtn;
    private RemoveFragmentable activity;
    public FriendDetailFragment() {
        // Required empty public constructor
    }


    /**
     * to render the view from inflator
     * @param inflater the layout component inflate the screen
     * @param container the container in the layout that contain all the boxes
     * @param savedInstanceState a variable keep tract the instance
     * @return the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_friend_detail, container, false);
        profileImg = (ImageView) rootView.findViewById(R.id.friend_profile_picture);
        email = (TextView)rootView.findViewById(R.id.friend_detail_email);
        shortDes = (TextView) rootView.findViewById(R.id.friend_detail_description);
        name = (TextView) rootView.findViewById((R.id.friend_detail_name));
        //ListView interests = (ListView) rootView.findViewById(R.id.friend_detail_interest_list);
        rate = (RatingBar)rootView.findViewById(R.id.friend_detail_rating);
        unfriendBtn  = (Button)rootView.findViewById(R.id.friend_detail_unfriendBtn);
        setValues();
        return rootView;
    }

    /**
     * set the value for all fragments
     */
    private void setValues() {
        Bundle b = getArguments();
        name.setText(b.getString("name"));
        email.setText(b.getString("email"));
        shortDes.setText(b.getString("description"));
        rate.setRating(b.getFloat("rating"));
        if (b.getBoolean("isMale")) {
            profileImg.setImageResource(R.drawable.profile_icon_temp);
        } else {
            profileImg.setImageResource(R.drawable.profile_icon_temp);
        }
        unfriendBtn.setOnClickListener(new UnFriendListener(b.getString("currentUser")
                , b.getString("targetUser")));
        //not yet implement interest list

    }


    /**
     * set the reference to the host activity
     * @param activity the activity that on attach to the hosting activity.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (RemoveFragmentable)activity;
    }



    private class UnFriendListener implements View.OnClickListener {
        String currUser;
        String targetUser;

        private void setCurrUser(String currUser) {
            this.currUser = currUser;
        }

        private void setTargetUser(String targetUser) {
            this.targetUser = targetUser;
        }

        public UnFriendListener(String currentUser, String targetUser) {
            setCurrUser(currentUser);
            setTargetUser(targetUser);
        }
        @Override
        public void onClick(View v) {
            FriendDB db = new FriendDB(getActivity().getApplicationContext());
            if (db.deleteFriendShip(currUser, targetUser)) {
                   activity.returnToFriendListFragment();
            } else {
                    new AlertDialog.Builder(getActivity())
                                   .setMessage("unfriend unsuccessful")
                                   .show();
            }
        }
    }

    public interface RemoveFragmentable {
        public void returnToFriendListFragment();
    }
}
