package com.example.jeevansarthigujarat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity {

    EditText name,email,contact,dob,age,weight,height;
    RadioGroup gender;
    Button signup;
    String sGender;
    Calendar calendar;
    TextInputEditText password;

    TextInputLayout passwordInput;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String passwordPATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

    //ImageView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.signup_name);

        email = findViewById(R.id.signup_email);
        contact = findViewById(R.id.signup_contact);
        password = findViewById(R.id.signup_password);
        passwordInput = findViewById(R.id.signup_password_input);
        dob = findViewById(R.id.signup_dob);
        age = findViewById(R.id.signup_age);
        weight = findViewById(R.id.signup_weight);
        height = findViewById(R.id.signup_height);
        signup = findViewById(R.id.signup_button);
        gender = findViewById(R.id.signup_gender);
        calendar = calendar.getInstance();
        //logo = findViewById(R.id.signup_logo_image);

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = gender.getCheckedRadioButtonId();
                RadioButton rb = findViewById(id);
                sGender = rb.getText().toString();


            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                dob.setText(dateFormat.format(calendar.getTime()));
            }
        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SignupActivity.this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().trim().equalsIgnoreCase("")){
                    name.setError("Name Required");
                }
                else if (email.getText().toString().trim().equalsIgnoreCase("")){
                    email.setError("Email Id Required");
                }
                else if (!email.getText().toString().matches(emailPattern)){
                    email.setError("Enter Valid Email Id Contains [a-zA-Z0-9._-]+@[a-z]+.+[a-z] ");
                }

                else if (password.getText().toString().trim().equalsIgnoreCase("")){
                    password.setError("Password Required");
                }
                else if (!password.getText().toString().matches(passwordPATTERN)){
                    password.setError("Valid Passord Required [0-9] [a-z] [A-Z] [@#$%^&+=]");
                }
                else if (contact.getText().toString().length()<10 || contact.getText().toString().length()>10){
                    contact.setError("Valid Contact Required");
                }

                else if (dob.getText().toString().trim().equalsIgnoreCase("")){
                    dob.setError("Date of Birth Required");
                }
                else if (age.getText().toString().trim().equalsIgnoreCase("")){
                    age.setError("Age Required");
                }
                else if (weight.getText().toString().trim().equalsIgnoreCase("")){
                    weight.setError("Weight Required");
                }
                else if (height.getText().toString().trim().equalsIgnoreCase("")){
                    height.setError("Height Required");
                }
                else if (gender.getCheckedRadioButtonId()==-1){
                    new ToastIntentClass(SignupActivity.this,"Please Select Gender");
                }
                else {
                    //new ToastIntentClass(SignupActivity.this,"Signup Successfull");
                    if (new ConnectionDetector(SignupActivity.this).isConnectingToInternet()){
                        new addSignupData().execute();

                    }
                    else {
                        new ConnectionDetector(SignupActivity.this).connectiondetect();
                    }
                }

            }
        });






    }

   /* @SuppressLint("ResourceAsColor")
    @Override
    protected void onResume() {
        super.onResume();


        int darkFlag = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;

        if (darkFlag == Configuration.UI_MODE_NIGHT_YES){


            RadioButton male = findViewById(R.id.signup_gender_male);
            RadioButton female = findViewById(R.id.signup_gender_female);
            RadioButton other = findViewById(R.id.signup_gender_other);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                name.setForceDarkAllowed(false);
                name.setTextColor(getResources().getColor(R.color.black));
                name.setHintTextColor(getResources().getColor(R.color.black_lite_hint));

                email.setForceDarkAllowed(false);
                email.setTextColor(getResources().getColor(R.color.black));
                email.setHintTextColor(getResources().getColor(R.color.black_lite_hint));

                contact.setForceDarkAllowed(false);
                contact.setTextColor(getResources().getColor(R.color.black));
                contact.setHintTextColor(getResources().getColor(R.color.black_lite_hint));

                password.setForceDarkAllowed(false);
                password.setTextColor(getResources().getColor(R.color.black));
                password.setHintTextColor(getResources().getColor(R.color.black_lite_hint));

                dob.setForceDarkAllowed(false);
                dob.setTextColor(getResources().getColor(R.color.black));
                dob.setHintTextColor(getResources().getColor(R.color.black_lite_hint));

                age.setForceDarkAllowed(false);
                age.setTextColor(getResources().getColor(R.color.black));
                age.setHintTextColor(getResources().getColor(R.color.black_lite_hint));

                weight.setForceDarkAllowed(false);
                weight.setTextColor(getResources().getColor(R.color.black));
                weight.setHintTextColor(getResources().getColor(R.color.black_lite_hint));

                height.setForceDarkAllowed(false);
                height.setTextColor(getResources().getColor(R.color.black));
                height.setHintTextColor(getResources().getColor(R.color.black_lite_hint));




                other.setTextColor(getResources().getColor(R.color.black));
                female.setTextColor(getResources().getColor(R.color.black));
                male.setTextColor(getResources().getColor(R.color.black));
            }
        }
    }*/

    private class addSignupData extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(SignupActivity.this);
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("androidName",name.getText().toString());
            hashMap.put("androidEmail",email.getText().toString());
            hashMap.put("androidContact",contact.getText().toString());
            hashMap.put("androidPassword",password.getText().toString());
            hashMap.put("androidDob",dob.getText().toString());
            hashMap.put("androidAge",age.getText().toString());
            hashMap.put("androidWeight",weight.getText().toString());
            hashMap.put("androidHeight",height.getText().toString());
            hashMap.put("androidGender",sGender);
            return new MakeServiceCall().MakeServiceCall(ConstantUrl.URL+"signup.php",MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("Status").equalsIgnoreCase("True")){
                    new ToastIntentClass(SignupActivity.this,object.getString("Message"));
                    new ToastIntentClass(SignupActivity.this,LoginActivity.class);

                }
                else {
                    new ToastIntentClass(SignupActivity.this,object.getString("Message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}