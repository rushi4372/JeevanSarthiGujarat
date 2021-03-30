package com.example.jeevansarthigujarat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button login, signup;
    SharedPreferences sp;
    String sType;
    TextView forgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE);

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        login = findViewById(R.id.login_login_button);
        signup = findViewById(R.id.login_signup_button);

        forgotPassword = findViewById(R.id.login_forgot_password);

        getSupportActionBar().hide();

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Select Login Type!");
                builder.setIcon(R.drawable.error);
                builder.setPositiveButton("User", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new ToastIntentClass(LoginActivity.this, SignupActivity.class);
                    }
                });
                builder.setNegativeButton("Doctor", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new ToastIntentClass(LoginActivity.this, HospitalRegisterActivity.class);
                    }
                });
                builder.show();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().trim().equalsIgnoreCase("")) {
                    email.setError("Email Required");
                } else if (password.getText().toString().trim().equalsIgnoreCase("")) {
                    password.setError("Password Required");
                } else {
                    //new ToastIntentClass(LoginActivity.this,"Login Successfully");
                    if (email.getText().toString().equalsIgnoreCase("admin@gmail.com") && password.getText().toString().equalsIgnoreCase("admin@123")) {
                        sp.edit().putString(ConstantSp.TYPE, "2").commit();
                        sp.edit().putString(ConstantSp.USERID, "0").commit();
                        sp.edit().putString(ConstantSp.NAME, "Admin").commit();
                        sp.edit().putString(ConstantSp.EMAIL, email.getText().toString()).commit();
                        sp.edit().putString(ConstantSp.CONTACT, "9876543210").commit();
                        sp.edit().putString(ConstantSp.PASSWORD, password.getText().toString()).commit();
                        sp.edit().putString(ConstantSp.DOB, "22-03-2021").commit();
                        sp.edit().putString(ConstantSp.AGE, "18").commit();
                        sp.edit().putString(ConstantSp.WEIGHT, "90").commit();
                        sp.edit().putString(ConstantSp.HEIGHT, "161").commit();
                        sp.edit().putString(ConstantSp.GENDER, "Male").commit();
                        new ToastIntentClass(LoginActivity.this, AdminHomeActivity.class);
                    } else {
                        if (new ConnectionDetector(LoginActivity.this).isConnectingToInternet()) {
                            new loginData().execute();
                        } else {
                            new ConnectionDetector(LoginActivity.this).connectiondetect();
                        }
                    }
                }
            }
        });
    }

    private class loginData extends AsyncTask<String, String, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(LoginActivity.this);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("androidEmail", email.getText().toString());
            hashMap.put("androidPassword", password.getText().toString());
            return new MakeServiceCall().MakeServiceCall(ConstantUrl.URL + "login.php", MakeServiceCall.POST, hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("Status").equalsIgnoreCase("True")) {
                    if (object.getString("type").equals("2")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("Select Login Type!");
                        builder.setIcon(R.drawable.error);
                        builder.setPositiveButton("User", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sType = "0";
                                new loginTypeData().execute();
                            }
                        });
                        builder.setNegativeButton("Doctor", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sType = "1";
                                new loginTypeData().execute();
                            }
                        });
                        builder.show();
                    } else {
                        new ToastIntentClass(LoginActivity.this, object.getString("Message"));
                        JSONArray array = object.getJSONArray("response");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            sp.edit().putString(ConstantSp.TYPE, object.getString("type")).commit();
                            if (object.getString("type").equals("1")) {
                                sp.edit().putString(ConstantSp.USERID, jsonObject.getString("doctor_id")).commit();
                                sp.edit().putString(ConstantSp.HNAME, jsonObject.getString("hospitalName")).commit();
                                sp.edit().putString(ConstantSp.DOCNAME, jsonObject.getString("doctorName")).commit();
                                sp.edit().putString(ConstantSp.DOCNAME1, jsonObject.getString("doctorName1")).commit();
                                sp.edit().putString(ConstantSp.DOCNAME2, jsonObject.getString("doctorName2")).commit();
                                sp.edit().putString(ConstantSp.SPECIALITY, jsonObject.getString("specialty_of_hospital")).commit();
                                sp.edit().putString(ConstantSp.ADDRESS, jsonObject.getString("address")).commit();
                                sp.edit().putString(ConstantSp.AREA, jsonObject.getString("area")).commit();
                                sp.edit().putString(ConstantSp.CITY, jsonObject.getString("city")).commit();
                                sp.edit().putString(ConstantSp.PINCODE, jsonObject.getString("pincode")).commit();
                                sp.edit().putString(ConstantSp.CONTACT_PERSON, jsonObject.getString("contact_person_name")).commit();
                                sp.edit().putString(ConstantSp.CONTACT_PERSON_MOBILE, jsonObject.getString("contact_person_mobileNo")).commit();
                                sp.edit().putString(ConstantSp.CONTACT_PERSON_PHONE, jsonObject.getString("contact_person_phoneNo")).commit();
                                sp.edit().putString(ConstantSp.EMAIL, jsonObject.getString("Emailid")).commit();
                                sp.edit().putString(ConstantSp.PASSWORD, jsonObject.getString("password")).commit();
                                new ToastIntentClass(LoginActivity.this, HospitalRegisterActivity.class);
                            } else {
                                sp.edit().putString(ConstantSp.USERID, jsonObject.getString("userId")).commit();
                                sp.edit().putString(ConstantSp.NAME, jsonObject.getString("name")).commit();
                                sp.edit().putString(ConstantSp.EMAIL, jsonObject.getString("email")).commit();
                                sp.edit().putString(ConstantSp.CONTACT, jsonObject.getString("contact")).commit();
                                sp.edit().putString(ConstantSp.PASSWORD, jsonObject.getString("password")).commit();
                                sp.edit().putString(ConstantSp.DOB, jsonObject.getString("dob")).commit();
                                sp.edit().putString(ConstantSp.AGE, jsonObject.getString("age")).commit();
                                sp.edit().putString(ConstantSp.WEIGHT, jsonObject.getString("weight")).commit();
                                sp.edit().putString(ConstantSp.HEIGHT, jsonObject.getString("height")).commit();
                                sp.edit().putString(ConstantSp.GENDER, jsonObject.getString("gender")).commit();
                                new ToastIntentClass(LoginActivity.this, BottomTabActivity.class);
                            }
                        }
                    }
                } else {
                    new ToastIntentClass(LoginActivity.this, object.getString("Message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class loginTypeData extends AsyncTask<String, String, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(LoginActivity.this);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("androidEmail", email.getText().toString());
            hashMap.put("androidPassword", password.getText().toString());
            hashMap.put("type", sType);
            return new MakeServiceCall().MakeServiceCall(ConstantUrl.URL + "loginType.php", MakeServiceCall.POST, hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("Status").equalsIgnoreCase("True")) {
                    new ToastIntentClass(LoginActivity.this, object.getString("Message"));
                    JSONArray array = object.getJSONArray("response");
                    for (int i = 0; i < array.length(); i++) {
                        sp.edit().putString(ConstantSp.TYPE, sType).commit();
                        JSONObject jsonObject = array.getJSONObject(i);
                        if (object.getString("type").equals("1")) {
                            sp.edit().putString(ConstantSp.USERID, jsonObject.getString("doctor_id")).commit();
                            sp.edit().putString(ConstantSp.HNAME, jsonObject.getString("hospitalName")).commit();
                            sp.edit().putString(ConstantSp.DOCNAME, jsonObject.getString("doctorName")).commit();
                            sp.edit().putString(ConstantSp.DOCNAME1, jsonObject.getString("doctorName1")).commit();
                            sp.edit().putString(ConstantSp.DOCNAME2, jsonObject.getString("doctorName2")).commit();
                            sp.edit().putString(ConstantSp.SPECIALITY, jsonObject.getString("specialty_of_hospital")).commit();
                            sp.edit().putString(ConstantSp.ADDRESS, jsonObject.getString("address")).commit();
                            sp.edit().putString(ConstantSp.AREA, jsonObject.getString("area")).commit();
                            sp.edit().putString(ConstantSp.CITY, jsonObject.getString("city")).commit();
                            sp.edit().putString(ConstantSp.PINCODE, jsonObject.getString("pincode")).commit();
                            sp.edit().putString(ConstantSp.CONTACT_PERSON, jsonObject.getString("contact_person_name")).commit();
                            sp.edit().putString(ConstantSp.CONTACT_PERSON_MOBILE, jsonObject.getString("contact_person_mobileNo")).commit();
                            sp.edit().putString(ConstantSp.CONTACT_PERSON_PHONE, jsonObject.getString("contact_person_phoneNo")).commit();
                            sp.edit().putString(ConstantSp.EMAIL, jsonObject.getString("Emailid")).commit();
                            sp.edit().putString(ConstantSp.PASSWORD, jsonObject.getString("password")).commit();
                            new ToastIntentClass(LoginActivity.this, HospitalRegisterActivity.class);
                        } else {
                            sp.edit().putString(ConstantSp.USERID, jsonObject.getString("userId")).commit();
                            sp.edit().putString(ConstantSp.NAME, jsonObject.getString("name")).commit();
                            sp.edit().putString(ConstantSp.EMAIL, jsonObject.getString("email")).commit();
                            sp.edit().putString(ConstantSp.CONTACT, jsonObject.getString("contact")).commit();
                            sp.edit().putString(ConstantSp.PASSWORD, jsonObject.getString("password")).commit();
                            sp.edit().putString(ConstantSp.DOB, jsonObject.getString("dob")).commit();
                            sp.edit().putString(ConstantSp.AGE, jsonObject.getString("age")).commit();
                            sp.edit().putString(ConstantSp.WEIGHT, jsonObject.getString("weight")).commit();
                            sp.edit().putString(ConstantSp.HEIGHT, jsonObject.getString("height")).commit();
                            sp.edit().putString(ConstantSp.GENDER, jsonObject.getString("gender")).commit();
                            new ToastIntentClass(LoginActivity.this, BottomTabActivity.class);
                        }
                    }
                } else {
                    new ToastIntentClass(LoginActivity.this, object.getString("Message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}