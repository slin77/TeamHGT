package edu.gatech.cs2340.hgt;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class NewSaleFragment extends Fragment {

    private TextView name;
    private TextView price;
    private EditText itemName;
    private EditText thresholdPrice;
    private EditText status;
    private RadioButton submitBtn;
    private RadioButton cancelBtn;
    private Back activity;

    public NewSaleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_sale, container, false);
        name = (TextView)rootView.findViewById(R.id.new_sale_name);
        price = (TextView) rootView.findViewById(R.id.new_sale_price);
        itemName = (EditText) rootView.findViewById(R.id.new_sale_item_name);
        thresholdPrice = (EditText)rootView.findViewById(R.id.new_sale_threshold_price);
        submitBtn  = (RadioButton)rootView.findViewById(R.id.new_sale_submitBtn);
        cancelBtn  = (RadioButton)rootView.findViewById(R.id.new_sale_cancelBtn);
        submitBtn.setOnClickListener(new SubmitListener());
        cancelBtn.setOnClickListener(new CancelListener());
        return rootView;
    }

    /**
     * set OnClickListeners

    private void setOCL() {
        submitBtn.setOnClickListener(new SubmitListener());
        cancelBtn.setOnClickListener(new CancelListener());

    }*/

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
                status.setText("item name can not be empty");
                return;
            } else if (tPrice.isEmpty()) {
                status.setText("price field can not be empty");
                return;
            }
            // update db
            activity.returnToUserHomeActivity();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (Back)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface Back {
        public boolean returnToUserHomeActivity();
    }

}
