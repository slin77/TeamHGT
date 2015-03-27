package edu.gatech.cs2340.hgt;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class UserHomeActivity extends ActionBarActivity implements UserDetailFragment.UserDetailCallback,
        NewSaleFragment.Back, NewReportFragment.NewReportCallBack{
    String currentUserName;

    @Override
    public String getCurrentUsername() {
        return currentUserName;
    }

    @Override
    public void returnToFriendList() {
        Intent i = new Intent(this, FriendsActivity.class);
        startActivity(i);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        currentUserName = getSharedPreferences("userSession", 0).getString("curUsername", null);
        if (savedInstanceState == null) {
            Fragment fragment = new UserDetailFragment();
            Bundle b= new Bundle();
            b.putString("username", currentUserName);
            fragment.setArguments(b);
            getFragmentManager().beginTransaction()
                    .add(R.id.UserHomeContainer, fragment, "UserDetailFragment")
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_home, menu);
        return true;
    }

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

    @Override
    public void displayNewSalesFragment() {
        Fragment fragment = new NewSaleFragment();
        Bundle b =  new Bundle();
        b.putString("username", currentUserName);
        fragment.setArguments(b);
        getFragmentManager().beginTransaction()
                .replace(R.id.UserHomeContainer, fragment)
                .commit();
    }

    @Override
    public boolean returnToUserHomeActivity() {
        Fragment fragment = new UserDetailFragment();
        Bundle b =  new Bundle();
        b.putString("username", currentUserName);
        fragment.setArguments(b);
        getFragmentManager().beginTransaction()
                           .replace(R.id.UserHomeContainer, fragment)
                           .commit();
        return true;
    }

    @Override
    public boolean addNewReport(String itemName, String price, String location) {
        UserDetailDB db = new UserDetailDB(this);
        return db.insertNewReport(itemName, price, location, currentUserName);
    }

    @Override
    public void displayNewReport() {
        getFragmentManager().beginTransaction()
                .replace(R.id.UserHomeContainer,new NewReportFragment())
                .commit();
    }
}
