package edu.gatech.cs2340.hgt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
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
    Button LoginBtn;
    Button SignupBtn;
    EditText nameField;
    EditText passwordField;
    TextView loginStatus;
    ProgressBar loginBar;
    ProgressDialog loginDia;
    RadioButton returnWelcome;
    UserService loginChecker;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginStatus = (TextView) findViewById(R.id.loginStatus);
        LoginBtn = (Button) findViewById(R.id.LoginBtn);
        SignupBtn = (Button) findViewById(R.id.signupBtn);
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

        LoginBtn.setOnClickListener(new View.OnClickListener() {
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
                    return;
                }
            }
        });

        SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

        public Timer timer = null;
        public  TimerTask timerTask = null;

    /**
     *
     * @param username
     */
    private void doLogin(String username) {
        UserDB db = new UserDB(getApplicationContext());

        db.insertUser("fakename1","fakerUser1", "123456789", "email" );
        db.insertUser("fakename2","fakerUser2", "123456789", "email" );
        db.insertUser("fakename3","fakerUser3", "123456789", "email" );
        db.insertUser("fakename4","fakerUser4", "123456789", "email" );

        Intent i = new Intent(LoginActivity.this, FriendsActivity.class);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("userSession", 0);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("curUsername", username);
        ed.commit();
        startActivity(i);
        finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //startTimer();
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
            hidePB();;
        }
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
}
