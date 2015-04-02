package edu.gatech.cs2340.hgt;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
@SuppressWarnings("ALL")
public class UserDetailFragment extends Fragment {
    private TextView name;
    private ImageView profileImg;
    private TextView email;
    private TextView shortDes;
    private ListView interests;
    private UserDetailCallback activity;

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
        Button newSaleBtn = (Button) rootView.findViewById(R.id.user_detail_new_sale_btn);
        Button editBtn = (Button) rootView.findViewById(R.id.user_detail_edit_btn);
        interests = (ListView)rootView.findViewById(R.id.friend_detail_interest_list);
        newSaleBtn.setOnClickListener(new NewSaleBtnListener());
        Button newReportBtn = (Button) rootView.findViewById(R.id.user_detail_reportBtn);
        setValues();
        newReportBtn.setOnClickListener(new ReportBtnListener());
        return rootView;
    }

    private void loadInterests(String username) {
        UserDetailDB db =  new UserDetailDB(getActivity());
        List<Report> list = db.getReports(username);
        SalesListAdapter adapter = new SalesListAdapter(getActivity(),
                list);
        interests.setAdapter(adapter);
        interests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SalesListAdapter adapter = (SalesListAdapter)interests.getAdapter();
                Report report = adapter.getSale(position);
                activity.displayReport(report);
            }
        });
    }

    /**
     * set the value for all fragments
     */
    private void setValues() {
        Bundle b = getArguments();
        UserDetailDB db = new UserDetailDB(getActivity());
        User currentUser = db.getFullDetailUser(b.getString("username"));
        name.setText(currentUser.getName());
        email.setText(currentUser.getEmail());
        shortDes.setText("not yet available");
        profileImg.setImageResource(R.drawable.profile_icon_temp);
        loadInterests(currentUser.getUsername());
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

       public void displayReport(Report report);
   }


}
