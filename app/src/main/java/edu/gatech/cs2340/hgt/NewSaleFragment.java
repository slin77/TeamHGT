package edu.gatech.cs2340.hgt;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class
        NewSaleFragment extends Fragment {

    private EditText itemName;
    private EditText thresholdPrice;
    private EditText status;
    private Back activity;
    private SalesNotifier sn;

    /**
     * required
     */
    public NewSaleFragment() {
        // Required empty public constructor
    }

    /**
     * default onCreate Method
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * create the views for the fragments
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_sale, container, false);
        //TextView name = (TextView) rootView.findViewById(R.id.new_sale_name);
        //TextView price = (TextView) rootView.findViewById(R.id.new_sale_price);
        itemName = (EditText) rootView.findViewById(R.id.new_sale_item_name);
        thresholdPrice = (EditText)rootView.findViewById(R.id.new_sale_threshold_price);
        Button submitBtn = (Button) rootView.findViewById(R.id.new_sale_submitBtn);
        Button cancelBtn = (Button) rootView.findViewById(R.id.new_sale_cancelBtn);
        submitBtn.setOnClickListener(new SubmitListener());
        cancelBtn.setOnClickListener(new CancelListener());
        return rootView;
    }


    private class CancelListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            activity.returnToUserHomeActivity();
        }
    }
    private class SubmitListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String iName = itemName.getText().toString();
            String tPrice = thresholdPrice.getText().toString();
            if (iName.isEmpty()) {
                displayAlert("item name can not be empty");
                //status.setText("item name can not be empty");
            } else if (tPrice.isEmpty()) {
                //status.setText("price field can not be empty");
                displayAlert("please enter a valid threshold price");
            } else {
                UserDetailDB db = new UserDetailDB(getActivity());
                String username = activity.getCurrentUsername();
                Time now  = new Time();
                now.setToNow();
                if (db.insertNewSale(username,iName, tPrice, now.toString())) {
                   displayAlert("Sale Successfully posted");
                    sn.notifyMatch(iName);
                   //SalesNotifier sn = new SalesNotifier(username, getActivity());
                   //System.out.println("number of matches: " + sn.getMatches().size());
                } else {
                    displayAlert("failed To Post Sale, try again");
                }
            }
        }
    }

    /**
     * the helper method for creating a alert box to display a message
     * @param s
     */
    private void displayAlert(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder((Activity)activity);
        builder.setMessage(s)
                .setTitle("Notification")
                .create()
                .show();
    }

    /**
     * save the reference to parent activity, which has to implements callback interface
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (Back)activity;
        String username = ((Back) activity).getCurrentUsername();
        sn = new SalesNotifier(username, activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface Back {
        /**
         * return to User's home page
         * @return if is has successfully returned
         */
        public void returnToUserHomeActivity();

        /**
         * get the logged in username
         * @return the current user's username
         */
        public String getCurrentUsername();
    }



}
