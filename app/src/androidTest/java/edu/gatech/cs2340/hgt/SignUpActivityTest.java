package edu.gatech.cs2340.hgt;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by sizhe on 3/29/15.
 */
public class SignUpActivityTest extends ActivityInstrumentationTestCase2<SignUpActivity>{
    private Activity activity;
    private EditText nameText;
    private EditText usernameText;
    private EditText passwordText;
    private EditText passwordRepeatText;
    private EditText emailText;
    private TextView signUpStatus;
    private Button submitBtn;
    private Button cancelBtn;

    public SignUpActivityTest() {
        super(SignUpActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        nameText = (EditText)activity.findViewById(R.id.signup_name);
        usernameText = (EditText)activity.findViewById(R.id.signup_username);
        passwordText = (EditText)activity.findViewById(R.id.signup_password);
        passwordRepeatText = (EditText)activity.findViewById((R.id.signup_password_repeat));
        emailText = (EditText)activity.findViewById(R.id.signup_email);
        signUpStatus = (TextView)activity.findViewById(R.id.signup_status);
        submitBtn = (Button)activity.findViewById(R.id.signup_submit);

    }

    @UiThreadTest
    public void testEmptyFields() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                submitBtn.callOnClick();
            }
        });

        assertEquals("name can not be empty", signUpStatus.getText().toString());
    }

    @UiThreadTest
    public void testEmpptyUsername() {
        reset();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nameText.setText("samlin");
                submitBtn.callOnClick();
            }
        });

        assertEquals("username can not be empty", signUpStatus.getText().toString());
    }

    @UiThreadTest
    public void testUsernameExists() {
        reset();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nameText.setText("samlin");
                usernameText.setText("samlin950205");
                passwordText.setText("123456789");
                passwordRepeatText.setText("1234567890");
                emailText.setText("slin77@gatech.edu");
                submitBtn.callOnClick();
            }
        });
        assertEquals("username already exist", signUpStatus.getText().toString());
    }

    @SmallTest
    public void testSuccess() {
        reset();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nameText.setText("samlin");
                usernameText.setText("samlin950205");
                passwordText.setText("123456789");
                passwordRepeatText.setText("123456789");
                emailText.setText("slin77@gatech.edu");
                submitBtn.callOnClick();
            }
        });
        assertEquals("click the button to sign up", signUpStatus.getText().toString());

    }

    private void reset(){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nameText.setText("");
                usernameText.setText("");
                passwordText.setText("");
                passwordRepeatText.setText("");
                emailText.setText("");
                signUpStatus.setText("");
            }
        });
    }

}


