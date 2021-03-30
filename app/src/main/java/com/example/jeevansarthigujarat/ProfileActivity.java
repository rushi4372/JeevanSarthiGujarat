package com.example.jeevansarthigujarat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    EditText name,email,contact,password,dob,age,weight,height;
    RadioGroup gender;
    RadioButton male,female,other;
    Button editprofile,submit,logout;
    String sGender;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);
        name = findViewById(R.id.profile_name);
        email = findViewById(R.id.profile_email);
        contact = findViewById(R.id.profile_contact);
        password = findViewById(R.id.profile_password);
        dob = findViewById(R.id.profile_dob);
        age = findViewById(R.id.profile_age);
        weight = findViewById(R.id.profile_weight);
        height = findViewById(R.id.profile_height);

        editprofile = findViewById(R.id.profile_Edit_profile_button);
        submit = findViewById(R.id.profile_submit_profile_button);
        logout = findViewById(R.id.profile_logout_profile_button);

        male = findViewById(R.id.profile_gender_male);
        female = findViewById(R.id.profile_gender_female);
        other = findViewById(R.id.profile_gender_other);
        gender = findViewById(R.id.profile_gender);

        name.setText(sp.getString(ConstantSp.NAME,""));
        email.setText(sp.getString(ConstantSp.EMAIL,""));
        contact.setText(sp.getString(ConstantSp.CONTACT,""));
        password.setText(sp.getString(ConstantSp.PASSWORD,""));
        dob.setText(sp.getString(ConstantSp.DOB,""));
        age.setText(sp.getString(ConstantSp.AGE,""));
        weight.setText(sp.getString(ConstantSp.WEIGHT,""));
        height.setText(sp.getString(ConstantSp.HEIGHT,""));

        sGender = sp.getString(ConstantSp.GENDER,"");
        if (sp.getString(ConstantSp.GENDER,"").equalsIgnoreCase("Male")){
            male.setChecked(true);
        }
        else if(sp.getString(ConstantSp.GENDER,"").equalsIgnoreCase("Female")){
            female.setChecked(true);
        }
        else if (sp.getString(ConstantSp.GENDER,"").equalsIgnoreCase("Other")){
            other.setChecked(true);
        }
        else {

        }


        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = gender.getCheckedRadioButtonId();
                RadioButton rb = findViewById(id);
                sGender = rb.getText().toString();


            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setEnabled(true);
                email.setEnabled(true);
                contact.setEnabled(true);
                password.setEnabled(true);
                dob.setEnabled(true);
                age.setEnabled(true);
                weight.setEnabled(true);
                height.setEnabled(true);
                male.setEnabled(true);
                female.setEnabled(true);
                other.setEnabled(true);

                editprofile.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().clear().commit();
                new ToastIntentClass(ProfileActivity.this,LoginActivity.class);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().trim().equalsIgnoreCase("")){
                    name.setError("Name Required");
                }
                else if (email.getText().toString().trim().equalsIgnoreCase("")){
                    email.setError("Email Id Required");
                }
                else if (contact.getText().toString().length()<10 || contact.getText().toString().length()>10){
                    contact.setError("Valid Contact Required");
                }
                else if (password.getText().toString().trim().equalsIgnoreCase("")){
                    password.setError("Password Required");
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
                    new ToastIntentClass(ProfileActivity.this,"Please Select Gender");
                }
                else {
                    //new ToastIntentClass(SignupActivity.this,"Signup Successfull");
                    if (new ConnectionDetector(ProfileActivity.this).isConnectingToInternet()){
                        new updateData().execute();

                    }
                    else {
                        new ConnectionDetector(ProfileActivity.this).connectiondetect();
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


            //RadioButton male = findViewById(R.id.signup_gender_male);
            //RadioButton female = findViewById(R.id.signup_gender_female);
            //RadioButton transgender = findViewById(R.id.signup_gender_other);

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

    @Override
    public void onBackPressed() {
        finishAffinity();

    }

    private class updateData extends AsyncTask<String,String,String> {
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ProfileActivity.this);
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("androidId",sp.getString(ConstantSp.USERID,""));
            hashMap.put("androidName",name.getText().toString());
            hashMap.put("androidEmail",email.getText().toString());
            hashMap.put("androidContact",contact.getText().toString());
            hashMap.put("androidPassword",password.getText().toString());
            hashMap.put("androidDob",dob.getText().toString());
            hashMap.put("androidAge",age.getText().toString());
            hashMap.put("androidWeight",weight.getText().toString());
            hashMap.put("androidHeight",height.getText().toString());
            hashMap.put("androidGender",sGender);
            return new MakeServiceCall().MakeServiceCall(ConstantUrl.URL+"updateProfile.php",MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("Status").equalsIgnoreCase("True")){
                    new ToastIntentClass(ProfileActivity.this,object.getString("Message"));

                    name.setEnabled(false);
                    email.setEnabled(false);
                    contact.setEnabled(false);
                    password.setEnabled(false);
                    dob.setEnabled(false);
                    age.setEnabled(false);
                    weight.setEnabled(false);
                    height.setEnabled(false);
                    male.setEnabled(false);
                    female.setEnabled(false);
                    other.setEnabled(false);

                    editprofile.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.GONE);

                    sp.edit().putString(ConstantSp.NAME,name.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.EMAIL,email.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.CONTACT,contact.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.PASSWORD,password.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.DOB,dob.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.AGE,age.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.WEIGHT,weight.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.HEIGHT,height.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.GENDER,sGender).commit();


                }
                else{
                    new ToastIntentClass(ProfileActivity.this,object.getString("Message"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}