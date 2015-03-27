package edu.gatech.cs2340.hgt;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;


public class FriendsActivity extends ActionBarActivity implements FriendListFragment.Callbacks
        , addFriendListFragment.CallBack2, FriendDetailFragment.RemoveFragmentable {
    //FriendDB db;
    String currentUser;
    /**
     *
     */
    @Override
    public void showAddFriendFragment() {
        getFragmentManager().beginTransaction()
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.myFriendContainer, new addFriendListFragment(), "addFriendFragment")
                .commit();

    }

    /**
     * show FriendList fragment and replace the top one
     */
    @Override
    public void showFriendList() {
        getFragmentManager().beginTransaction()
                .setTransitionStyle(FragmentTransaction.TRANSIT_ENTER_MASK)
                .replace(R.id.myFriendContainer, new FriendListFragment())
                .commit();
    }

    public String getCurrentUser() {
        return currentUser;
    }

    /**
     * create the activity and show friend list fragment
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_friends);
        currentUser = getSharedPreferences("userSession", 0).getString("curUsername", null);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.myFriendContainer, new FriendListFragment(), "FriendListFragment")
                    .commit();
        }

        //db = new FriendDB(getApplicationContext());
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends, menu);
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * select the user and show the fragment of the user's detail
     * @param user
     */
    @Override
    public void onItemSelected(User user) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("You have selected " + user.getName() + "!")
//               .setTitle("selected")
//                .create()
//                .show();
          User fullDetailedUser = new UserDetailDB(this).getFullDetailUser(user.getUsername());
          Bundle b = new Bundle();
        if (!fullDetailedUser.isHasDetail()) {
            putDefault(b, fullDetailedUser);
        }
        else{
            b.putString("name", fullDetailedUser.getName());
            b.putString("email", fullDetailedUser.getEmail());
            b.putString("description", "Number of Reports: 0");
            b.putFloat("rating", fullDetailedUser.getRating());
            b.putBoolean("isMale", fullDetailedUser.isMale());
            b.putString("currentUser", currentUser);
            b.putString("targetUser", fullDetailedUser.getUsername());
        }
        FriendDetailFragment fragment =  new FriendDetailFragment();
        fragment.setArguments(b);
        getFragmentManager().beginTransaction()
                .replace(R.id.myFriendContainer, fragment, "FriendDetailFragment")
                .commit();


    }

    /**
     * put the default values is the user is not fulldetailed
     * @param b
     * @param fullDetailedUser
     */
    private void putDefault(Bundle b, User fullDetailedUser) {
        b.putString("currentUser", currentUser);
        b.putString("targetUser", fullDetailedUser.getUsername());
        b.putString("name", fullDetailedUser.getName());
        b.putString("email", fullDetailedUser.getEmail());
        b.putString("description", "Number of Reports: 0");
        b.putFloat("rating", fullDetailedUser.getRating());
        b.putBoolean("isMale",true);
    }

    @Override
    public void showUserHomePage() {
        Intent i =  new Intent(this, UserHomeActivity.class);
        startActivity(i);
        finish();

    }

    /**
     *
     * @param username1
     * @param username2
     * @return
     */
    @Override
    public boolean addFriendship(String username1, String username2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        FriendDB db = new FriendDB(getApplicationContext());
        if(db.addNewFriendship(username1, username2)) {
            builder.setMessage(("You are now friend with: " + username2))
                    .setTitle("Congradulations")
                    .create()
                    .show();
            return true;
        } else {
            builder.setMessage("You Two Are Already Friend")
                    .setTitle("Whoops")
                    .create()
                    .show();
            return false;
        }
    }

    @Override
    public boolean returnToFriendListFragment() {
        showFriendList();
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
            return rootView;
        }
    }
}
