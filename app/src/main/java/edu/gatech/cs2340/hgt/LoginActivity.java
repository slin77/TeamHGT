package edu.gatech.cs2340.hgt;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends ActionBarActivity {
    Button LoginBtn;
    Button SignupBtn;
    EditText nameField;
    EditText passwordField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginBtn = (Button)findViewById(R.id.LoginBtn);
        SignupBtn = (Button)findViewById(R.id.signupBtn);
        nameField = (EditText)findViewById(R.id.name);
        nameField.setText(getIntent().getStringExtra("Mess1"));
        passwordField = (EditText)findViewById((R.id.password));
        String name = nameField.getText().toString();
        String password = nameField.getText().toString();
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameField.getText().toString();
                String password = passwordField.getText().toString();
                Intent i = new Intent(LoginActivity.this, Welcome.class);
                startActivity(i);
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
