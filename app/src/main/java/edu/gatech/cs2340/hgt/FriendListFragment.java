package edu.gatech.cs2340.hgt;

import android.app.Activity;
import android.app.AlertDialog;
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
    SalesNotifier sn;
    String username;
    private Callbacks activity;
    private Button newFriendBtn;
    private Button userhomeBtn;
    public FriendListFragment() {

    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        userhomeBtn = (Button)rootView.findViewById(R.id.currentUserHomeBtn);
        userhomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showUserHomePage();
            }
        });
        //sn = new SalesNotifier(username, getActivity());
        //notifyMatch();
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
        this.username = username;
        friends = friendDB.getFriendList(username);

        FriendArrayAdapter adapter = new FriendArrayAdapter(getActivity(),
                R.layout.friend_listitem, friendDB.getFriendList(username));
        sn = new SalesNotifier(username, getActivity());
        sn.notifyMatch();
        setListAdapter(adapter);
    }

    public interface Callbacks {
        public void onItemSelected(User user);
        public void showAddFriendFragment();
        public void showUserHomePage();
    }
}
