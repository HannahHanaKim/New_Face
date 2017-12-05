package kr.newface.new_face.new_face;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Activity_signup extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    RelativeLayout sliding;
    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_studentID) EditText _studentIdText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;
    @BindView(R.id.input_introduct) EditText _introductText;
    static String send_str = "";

    ImageView image;
    int count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);
        // Click the sign-up button to execute the sign-up function
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        // If already a member, you can return to the login screen
        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intentIT = new Intent(Activity_signup.this, Activity_login.class);
                startActivity(intentIT);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        sliding = (RelativeLayout)findViewById(R.id.sliding);
        // Press the image button to display a screen to select the image
        image = (ImageView)findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sliding.getVisibility()==View.VISIBLE){
                    sliding.setVisibility(View.GONE);
                }
                else {
                    sliding.setVisibility(View.VISIBLE);
                }
            }
        });

        ImageView image1, image2, image3, image4, image5, image6;
        image1 = (ImageView)findViewById(R.id.asian_man);
        image2 = (ImageView)findViewById(R.id.asian_woman);
        image3 = (ImageView)findViewById(R.id.black_man);
        image4 = (ImageView)findViewById(R.id.black_woman);
        image5 = (ImageView)findViewById(R.id.white_man);
        image6 = (ImageView)findViewById(R.id.white_woman);
        // When selecting an image, select the image selection screen again and replace it with the selected image

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image.setImageDrawable(getResources().getDrawable(R.drawable.asian_man));
                sliding.setVisibility(View.GONE);
                count =1;
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image.setImageDrawable(getResources().getDrawable(R.drawable.asian_woman));
                sliding.setVisibility(View.GONE);
                count = 2;
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image.setImageDrawable(getResources().getDrawable(R.drawable.black_man));
                sliding.setVisibility(View.GONE);
                count = 3;
            }
        });
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image.setImageDrawable(getResources().getDrawable(R.drawable.black_woman));
                sliding.setVisibility(View.GONE);
                count =4;
            }
        });
        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image.setImageDrawable(getResources().getDrawable(R.drawable.white_man));
                sliding.setVisibility(View.GONE);
                count = 5;
            }
        });
        image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image.setImageDrawable(getResources().getDrawable(R.drawable.white_woman));
                sliding.setVisibility(View.GONE);
                count = 6;
            }
        });

    }
    // Sign in if the condition is correct.
    public void signup() {
        Log.d(TAG, "Signup");
        if (!validate()) {
            onSignupFailed();
            return;
        }
        if(count == 0){
            Toast.makeText(getApplicationContext(),"이미지를 선택해주세요",Toast.LENGTH_SHORT).show();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Activity_signup.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onSignupSuccess();
                        progressDialog.dismiss();
                    }
                }, 1000);
    }

    // Sign-up succeeds and turns to survey screen
    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        Intent it = new Intent(this, searchActivity.class);
        startActivity(it);
    }
    // Stop button if sign-up fails
    public void onSignupFailed() {
        _signupButton.setEnabled(true);
    }

    // Function to judge membership type
    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String address = _studentIdText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();
        String introduct = _introductText.getText().toString();

        if (name.isEmpty() || name.length() < 2) {
            _nameText.setError("2자 이상이어야 합니다.");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (address.length()!=9||address.isEmpty()) {
            _studentIdText.setError("학번은 9자 입니다");
            valid = false;
        } else {
            _studentIdText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("4자 이상에서 10자이하의 숫자와 알파벳이어야 합니다.");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("비밀번호가 다릅니다.");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }
        if(introduct.isEmpty() || introduct.length() >20){
            _introductText.setError("20자 이하로 작성하여 주십시오");
            valid = false;
        }
        else{
            introduct = introduct.replace(" ","_");
            _introductText.setError(null);
        }

        send_str = name + " " + address +" "+password+" " +String.valueOf(count) + " " + introduct;
        return valid;
    }
}
