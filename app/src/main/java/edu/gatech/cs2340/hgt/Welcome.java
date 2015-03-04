package edu.gatech.cs2340.hgt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 *
 */
public class Welcome extends ActionBarActivity {
   static Context APP_CONTEXT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APP_CONTEXT = getApplicationContext();//this is the context of the whole application
        setContentView(R.layout.activity_welcome);
        Thread logoTimer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                    Intent i = new Intent(Welcome.this, SelectActivity.class);
                    startActivityForResult(i, 0);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }

            }
        };
        logoTimer.start();

    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            String result = data.getStringExtra("resultName");
            if (!result.isEmpty()) {
                //welcomeBtn.setText(result);
            }
            super.onActivityResult(requestCode, resultCode, data);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    /**
     *
     */
    @Override
    protected void onStop() {
        super.onStop();
//        welcomeBtn.setText("Welcome Back");
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
