package edu.gatech.cs2340.hgt;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;


/**
 * Created by sizhe on 4/22/15.
 */
public class EmailRecoverer {
    Context context;
    UserDB db;

    public EmailRecoverer(Context context) {
        this.context = context;
        this.db = new UserDB(context);
    }

    public void recover(String username) {
        String pw = db.getPassword(username);
        if (pw == null) {
            Toast.makeText(context, "Username Does not exist", Toast.LENGTH_SHORT).show();
            return;
        } else {
            String email = db.getEmail(username);
            sendRequest(email, pw);
        }

    }

    private void sendRequest(final String email, final String password) {
        AsyncTask task = new AsyncTask() {
            private String message;
            private boolean success;
            @Override
            protected Object doInBackground(Object[] params) {
                HttpClient client = new DefaultHttpClient();
                String url = "http://52.10.15.94:8080/recover?email="+email
                        +"&password="+password;
                HttpGet get = new HttpGet(url);
                HttpResponse response;
                try {
                    response =  client.execute(get);
                } catch (IOException e) {
                    ///Toast.makeText(context, "Network Error occurs", Toast.LENGTH_SHORT).show();
                    success = false;
                    return false;
                }
                success = true;
                message = response.getEntity().toString();
                return true;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (success) {
                    Toast.makeText(context, "Your password has been sent to your email", Toast.LENGTH_SHORT)
                    .show();
                } else {
                    Toast.makeText(context, "Network Error occurs", Toast.LENGTH_SHORT).show();
                }
            }
        };

        task.execute();
    }
}
