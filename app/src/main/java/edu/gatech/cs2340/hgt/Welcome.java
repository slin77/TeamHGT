package edu.gatech.cs2340.hgt;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Welcome extends ActionBarActivity {
    TextView helloWord;
    Button welcomeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcomeBtn = (Button)findViewById(R.id.welcomeBtn);
//        helloWord = (TextView)findViewById(R.id.helloword );
        welcomeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Welcome.this, LoginActivity.class);
                i.putExtra("Mess1",welcomeBtn.getText().toString());
//                startActivity(i);
                startActivityForResult(i, 0);
        }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            String result = data.getStringExtra("resultName");
            if (!result.isEmpty()) {
                welcomeBtn.setText(result);
            }
            super.onActivityResult(requestCode, resultCode, data);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        welcomeBtn.setText("Welcome Back");
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
