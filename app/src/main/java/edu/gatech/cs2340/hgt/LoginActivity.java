package edu.gatech.cs2340.hgt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;


import java.util.Timer;
import java.util.TimerTask;


public class LoginActivity extends ActionBarActivity {
    private EditText nameField;
    private EditText passwordField;
    private TextView loginStatus;
    private ProgressBar loginBar;
    ProgressDialog loginDia;
    private RadioButton returnWelcome;
    private UserService loginChecker;

    /**
     *
     * @param savedInstanceState the saved instance of item
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginStatus = (TextView) findViewById(R.id.loginStatus);
        Button loginBtn = (Button) findViewById(R.id.LoginBtn);
        Button signupBtn = (Button) findViewById(R.id.signupBtn);
        nameField = (EditText) findViewById(R.id.name);
        nameField.setText(getIntent().getStringExtra("Mess1"));
        passwordField = (EditText) findViewById((R.id.password));
        loginBar = (ProgressBar) findViewById(R.id.loginBar);
        returnWelcome = (RadioButton)findViewById(R.id.returnWelcomeBtn);

        returnWelcome.setVisibility(View.INVISIBLE);

        loginChecker = new UserService(this.getApplicationContext());


        returnWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SelectActivity.class);
                startActivity(i);
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = nameField.getText().toString();
                String password = passwordField.getText().toString();
                if (loginChecker.validate(username, password)) {
                    startTimer();
                    returnWelcome.setVisibility(View.VISIBLE);
                    doLogin(username);

                } else {
                    loginStatus.setText("login Failed, please check Again");
                }
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

        private Timer timer = null;
        private TimerTask timerTask = null;

    /**
     *
     * @param username user who want to log in
     */
    private void doLogin(String username) {
        Intent i = new Intent(LoginActivity.this, FriendsActivity.class);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("userSession", 0);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("curUsername", username);
        ed.apply();
        startActivity(i);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTimer();
    }

    private void startTimer() {
        if (timer == null) {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {

                    loginBar.incrementProgressBy(5);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                    //must run UI Thread to touch UI elements
                            if (loginBar.getProgress() == loginBar.getMax()) {
                                loginStatus.setText("You have successfully logged in");
                                loginBar.setVisibility(View.INVISIBLE);
                                stopTimer();

                            }
                        }
                    });
                }

                };
            timer.schedule(timerTask, 100, 100);
            }
    }
    private void hidePB(){
        loginBar.setVisibility(View.INVISIBLE);
    }
    private void showPB(){
        if (!loginBar.isActivated()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loginBar.setVisibility(View.VISIBLE);
                }
            });

        }
    }
    private void stopTimer() {
        if (timer != null) {
            timerTask.cancel();
            timer.cancel();
            timerTask = null;
            timer = null;
            hidePB();
        }
    }

    /**
     *
     * @param menu the menu
     * @return boolean true of false
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    /**
     *
     * @param item the item which is selected
     * @return boolean true of false
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
}
