package edu.gatech.cs2340.hgt;

import android.app.Activity;
import android.app.AlertDialog;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class UserDetailFragment extends Fragment {
    private TextView name;
    private ImageView profileImg;
    private TextView email;
    private TextView shortDes;
    private Button newSaleBtn;
    private  Button editBtn;
    private ListView interests;
    private UserDetailCallback activity;
    private User currentUser;
    private Button newReportBtn;
    public UserDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * to render the view from inflator
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_user_detail, container, false);
        profileImg = (ImageView) rootView.findViewById(R.id.friend_profile_picture);
        email = (TextView)rootView.findViewById(R.id.friend_detail_email);
        shortDes = (TextView) rootView.findViewById(R.id.friend_detail_description);
        name = (TextView) rootView.findViewById((R.id.friend_detail_name));
        interests = (ListView) rootView.findViewById(R.id.friend_detail_interest_list);
        newSaleBtn = (Button)rootView.findViewById(R.id.user_detail_new_sale_btn);
        editBtn = (Button)rootView.findViewById(R.id.user_detail_edit_btn);
        interests = (ListView)rootView.findViewById(R.id.friend_detail_interest_list);
        newSaleBtn.setOnClickListener(new NewSaleBtnListener());
        newReportBtn = (Button)rootView.findViewById(R.id.user_detail_reportBtn);
        setValues();
        newReportBtn.setOnClickListener(new ReportBtnListener());
        return rootView;
    }

    private void loadInterests(String username) {
        UserDetailDB db =  new UserDetailDB(getActivity());
        List<Sale> list = db.getSales(username);
        SalesListAdapter adapter = new SalesListAdapter(getActivity(), R.layout.sale_item
        , list);
        interests.setAdapter(adapter);
    }

    /**
     * set the value for all fragments
     */
    private void setValues() {
        Bundle b = getArguments();
        UserDetailDB db = new UserDetailDB(getActivity());
        currentUser = db.getFullDetailUser(b.getString("username"));
        name.setText(currentUser.getName());
        email.setText(currentUser.getEmail());
        shortDes.setText("not yet available");
        profileImg.setImageResource(R.drawable.profile_icon_temp);
        loadInterests(currentUser.getUsername());

        //not yet implement interest list
    }


    /**
     * set the reference to the host activity
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (UserDetailCallback)activity;

    }

    private class ReportBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            activity.displayNewReport();
        }
    }

    private class NewSaleBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            activity.displayNewSalesFragment();
        }
    }
    private class ReturnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            activity.returnToFriendList();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

   public interface UserDetailCallback{
       /**
        * the is the callback method for displaying the new sales fragment
        */
       public void displayNewSalesFragment();

       /**
        * this is the fragment to return t friend activity
        */
       public void returnToFriendList();

       public void displayNewReport();
   }


}
