package edu.gatech.cs2340.hgt;

import android.app.Activity;
import android.app.ListFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

/**
 * Created by root on 2/16/15.
 */
public class FriendListFragment extends ListFragment{
    List<User> friends;
    private Callbacks activity;
    private Button newFriendBtn;
    public FriendListFragment() {

    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FriendDB friendDB = new FriendDB(getActivity());
        //insert dummy date for testing
        friendDB.addNewFriendship("samlin950205", "fakerUser1");
        friendDB.addNewFriendship("samlin950205", "fakerUser2");
        friendDB.addNewFriendship("samlin950205", "fakerUser3");
        friendDB.addNewFriendship("samlin950205", "fakerUser4");
        //
//        SharedPreferences sp = getActivity()
//                .getApplicationContext()
//                .getSharedPreferences("userSession", 0);
//        String currentUser = sp.getString("curUsername", null);
//        FriendArrayAdapter adapter = new FriendArrayAdapter(getActivity(),
//                R.layout.friend_listitem, friendDB.getFriendList(currentUser));
//
//        setListAdapter(adapter);
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        newFriendBtn = (Button) rootView.findViewById(R.id.addFriendBtn);
        newFriendBtn.setOnClickListener(new newFriendBtnListener());
        return rootView;
    }

    /**
     *
     */
    private class newFriendBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            activity.showAddFriendFragment();
        }
    }

    /**
     *
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        User user = friends.get(position);
        activity.onItemSelected(user);
    }

    /**
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (Callbacks) activity;
        FriendDB friendDB = new FriendDB(getActivity().getApplicationContext());
        String username = getActivity() //this will be null if the fragment is detached
                .getApplicationContext()
                .getSharedPreferences("userSession", 0)
                .getString("curUsername", null);
        friends = friendDB.getFriendList(username);

        FriendArrayAdapter adapter = new FriendArrayAdapter(getActivity(),
                R.layout.friend_listitem, friendDB.getFriendList(username));

        setListAdapter(adapter);
    }

    public interface Callbacks {
        public void onItemSelected(User user);
        public void showAddFriendFragment();
    }
}
