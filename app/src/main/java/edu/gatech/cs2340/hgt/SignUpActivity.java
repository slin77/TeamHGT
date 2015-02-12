package edu.gatech.cs2340.hgt;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SignUpActivity extends ActionBarActivity {
    private EditText nameText;
    private EditText usernameText;
    private EditText passwordText;
    private EditText passwordRepeatText;
    private EditText emailText;
    private TextView signUpStatus;
    private Button submitBtn;
    private UserService signUpchecker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nameText = (EditText)findViewById(R.id.signup_name);
        usernameText = (EditText)findViewById(R.id.signup_username);
        passwordText = (EditText)findViewById(R.id.signup_password);
        passwordRepeatText = (EditText)findViewById((R.id.signup_password_repeat));
        emailText = (EditText)findViewById(R.id.signup_email);
        signUpStatus = (TextView)findViewById(R.id.signup_status);
        submitBtn = (Button)findViewById(R.id.signup_submit);
        submitBtn.setOnClickListener(new SubmitListener());
        signUpchecker = new UserService(this.getApplicationContext());

    }

    private class SubmitListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String name = nameText.getText().toString();
            String username = usernameText.getText().toString();
            String pw = passwordText.getText().toString();
            String pwRepeat = passwordRepeatText.getText().toString();
            String email = emailText.getText().toString();

            if (name.isEmpty()) {
                signUpStatus.setText("name can not be empty");
                return;
            } else if (username.isEmpty()) {
                signUpStatus.setText("username can not be empty");
                return;
            } else if (pw.isEmpty()) {
                signUpStatus.setText("password can not be empty");
                return;
            } else if (pwRepeat.isEmpty()) {
                signUpStatus.setText("please Repeat your password");
                return;
            } else if (email.isEmpty()) {
                signUpStatus.setText("email can not be empty");
                return;
            } else {
                if (signUpchecker.isUsernameExist(username)) {
                    signUpStatus.setText("username already exist");
                    return;
                } else if (!signUpchecker.checkUsernameFormat(username)) {
                    signUpStatus.setText("username format was not right");
                    return;
                } else if (!signUpchecker.checkPasswordFormat(pw)) {
                    signUpStatus.setText("password format was not right");
                    return;
                } else if (!signUpchecker.checkPasswordMatch(pw, pwRepeat)) {
                    signUpStatus.setText("passwords do not match");
                    return;
                } else if (!signUpchecker.checkEmailFormat(email)) {
                    signUpStatus.setText("please use a valid email format");
                    return;
                } else if (signUpchecker.createUser(name,username,pw, email)){
                    signUpStatus.setText("you have successfully sign up!");
                    Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                    return;
                }

                signUpStatus.setText("some problem occurs");
                return;
            }



        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
}
