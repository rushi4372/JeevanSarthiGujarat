package com.example.jeevansarthigujarat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;

import net.gotev.uploadservice.MultipartUploadRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.HashMap;

public class HospitalRegisterActivity extends AppCompatActivity {

    EditText hospitalName, doctorName1, doctorName2, doctorName3,/*speciality,*/
            address, area, pincode, contactPersonName, contactPersonMobileno,
            contactPersonPhoneno, emailid, password, totalbed, avaliblebed;
    Button selectiv;
    ImageView iv;
    int PICK_IMAGE = 100;
    Uri filePath;
    private TextInputLayout hospitalspeciality, city, numberInput;
    private AutoCompleteTextView citydropdown;
    private MultiAutoCompleteTextView specialitydropDownText;
    Button register;
    String[] cityArray = new String[]{"Ahmedabad", "Surat", "Vadodara", "Rajkot", "Palanpur", "Jamnagar", "Himmatnagar", "Mahesana"};
    String sCity, speciality;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String passwordPATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";


    String[] items = new String[]{
            "Child Care", "Dentist", "Covid 19", "Cardiologist", "Gynecologist", "Kidney", "Hair and Skin care", "Mental Health"
            , "Neurologist", "Orthopedist"
    };

    private static final int STORAGE_PERMISSION_CODE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_register);
        requestStoragePermission();
        hospitalName = findViewById(R.id.hospital_register_hospitalName);
        doctorName1 = findViewById(R.id.hospital_register_doctorName1);
        doctorName2 = findViewById(R.id.hospital_register_doctorName2);
        doctorName3 = findViewById(R.id.hospital_register_doctorName3);
        /* speciality = findViewById(R.id.hospital_register_speciality_of_hospital);*/
        address = findViewById(R.id.hospital_register_address);
        area = findViewById(R.id.hospital_register_area);
        pincode = findViewById(R.id.hospital_register_pincode);
        contactPersonName = findViewById(R.id.hospital_register_contact_personName);
        numberInput = findViewById(R.id.hospital_register_contact_person_mobileno_input);
        contactPersonMobileno = findViewById(R.id.hospital_register_contact_person_mobileno);
        contactPersonPhoneno = findViewById(R.id.hospital_register_contact_person_phoneno);
        emailid = findViewById(R.id.hospital_register_emailid);
        password = findViewById(R.id.hospital_register_password);
        totalbed = findViewById(R.id.hospital_register_totalbed);
        avaliblebed = findViewById(R.id.hospital_register_avaliblebed);
        selectiv = findViewById(R.id.select_image);
        iv = findViewById(R.id.select_image_iv);

        selectiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });


        contactPersonMobileno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (contactPersonMobileno.getText().toString().length() <= 0) {
                    numberInput.setCounterEnabled(false);
                    numberInput.setErrorEnabled(false);
                } else {
                    numberInput.setCounterEnabled(true);
                    numberInput.setErrorEnabled(true);
                    numberInput.setCounterMaxLength(10);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        contactPersonPhoneno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (contactPersonPhoneno.getText().toString().length() <= 0) {
                    numberInput.setCounterEnabled(false);
                    numberInput.setErrorEnabled(false);
                } else {
                    numberInput.setCounterEnabled(true);
                    numberInput.setErrorEnabled(true);
                    numberInput.setCounterMaxLength(11);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        hospitalspeciality = findViewById(R.id.text_input_layout_speciality_hospitalregister);
        specialitydropDownText = findViewById(R.id.hospital_register_speciality_of_hospital);

        city = findViewById(R.id.text_input_layout_city_hospitalregister);
        citydropdown = findViewById(R.id.hospital_register_city);

        //city autocomplete text view

        ArrayAdapter<String> cityadapter = new ArrayAdapter<>(HospitalRegisterActivity.this, R.layout.dropdown_item, cityArray);
        citydropdown.setAdapter(cityadapter);
        citydropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sCity = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //speciality autocomplete text view

        ArrayAdapter<String> spacilityadapter = new ArrayAdapter<>(HospitalRegisterActivity.this, R.layout.dropdown_item, items);
        specialitydropDownText.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        specialitydropDownText.setAdapter(spacilityadapter);
        specialitydropDownText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                speciality = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        register = findViewById(R.id.hospital_register_button);

       /* ArrayAdapter adapter = new ArrayAdapter(HospitalRegisterActivity.this, android.R.layout.simple_list_item_1,cityArray);
        citySpinner.setAdapter(adapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sCity = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hospitalName.getText().toString().trim().equalsIgnoreCase("")) {
                    hospitalName.setError("Hospital Name Required");
                } else if (doctorName1.getText().toString().trim().equalsIgnoreCase("")) {
                    doctorName1.setError("Doctor Name Required");
                }
               /* else if (doctorName2.getText().toString().trim().equalsIgnoreCase("")){
                    doctorName2.setError("Second Doctor Name Required");
                }
                else if (doctorName3.getText().toString().trim().equalsIgnoreCase("")){
                    doctorName3.setError("Third Doctor Name Required");
                }*/
                /*else if (speciality.getText().toString().trim().equalsIgnoreCase("")){
                    speciality.setError("Hospital Speciality Required");
                }*/
                else if (address.getText().toString().trim().equalsIgnoreCase("")) {
                    address.setError("Address Required");
                } else if (area.getText().toString().trim().equalsIgnoreCase("")) {
                    area.setError("Area Required");
                } else if (pincode.getText().toString().length() < 6 || pincode.getText().toString().length() > 6) {
                    pincode.setError("Valid Pincode Required");
                } else if (contactPersonName.getText().toString().trim().equalsIgnoreCase("")) {
                    contactPersonName.setError("Contect Person Name Requires");
                } else if (contactPersonMobileno.getText().toString().length() < 10 || contactPersonMobileno.getText().toString().length() > 10) {
                    contactPersonMobileno.setError("Valid Mobile Number Required");
                } else if (contactPersonPhoneno.getText().toString().length() < 11 || contactPersonPhoneno.getText().toString().length() > 11) {
                    contactPersonPhoneno.setError("Valid Phone Number Required");
                } else if (emailid.getText().toString().trim().equalsIgnoreCase("")) {
                    emailid.setError("Email Id Required");
                } else if (!emailid.getText().toString().matches(emailPattern)) {
                    emailid.setError("Enter Valid Email Id Contains [a-zA-Z0-9._-]+@[a-z]+.+[a-z] ");
                } else if (password.getText().toString().trim().equalsIgnoreCase("")) {
                    password.setError("Password Required");
                } else if (!password.getText().toString().matches(passwordPATTERN)) {
                    password.setError("Valid Password Required [0-9] [a-z] [A-Z] [@#$%^&+=]");
                } else if (totalbed.getText().toString().trim().equalsIgnoreCase("")) {
                    totalbed.setError("Number Required");
                } else if (avaliblebed.getText().toString().trim().equalsIgnoreCase("")) {
                    avaliblebed.setError("Number Required");
                } else if (specialitydropDownText.getText().toString().equalsIgnoreCase("")) {
                    specialitydropDownText.setError("Speciality Required");
                } else {
                    /*int count=0;
                    for(int i=0;i<items.length;i++){
                        if(items[i].equalsIgnoreCase(specialitydropDownText.getText().toString())){
                            count=1;
                        }
                    }*/

                    int count1 = 0;
                    for (int i = 0; i < cityArray.length; i++) {
                        if (cityArray[i].equalsIgnoreCase(citydropdown.getText().toString())) {
                            count1 = 1;
                        }
                    }

                    /*if(count==1){*/
                    if (count1 == 1) {
                        if (new ConnectionDetector(HospitalRegisterActivity.this).isConnectingToInternet()) {
                            //new hospitalRegisterData().execute();
                            String path = getImage(filePath);
                            if (path != "") {
                                try {
                                    ProgressDialog pd = new ProgressDialog(HospitalRegisterActivity.this);
                                    pd.setMessage("Please Wait");
                                    pd.setCancelable(false);
                                    pd.show();

                                    new MultipartUploadRequest(HospitalRegisterActivity.this, ConstantUrl.URL + "hospitalRegistration.php")
                                            .addParameter("androidHospitalName", hospitalName.getText().toString())
                                            .addParameter("androidDoctorName", doctorName1.getText().toString())
                                            .addParameter("androidDoctorName1", doctorName2.getText().toString())
                                            .addParameter("androidDoctorName2", doctorName3.getText().toString())
                                            .addParameter("androidSpecialty", specialitydropDownText.getText().toString())
                                            .addParameter("androidAddress", address.getText().toString())
                                            .addParameter("androidArea", area.getText().toString())
                                            .addParameter("androidCity", citydropdown.getText().toString())
                                            .addParameter("androidPincode", pincode.getText().toString())
                                            .addParameter("androidConPerName", contactPersonName.getText().toString())
                                            .addParameter("androidConPerMob", contactPersonMobileno.getText().toString())
                                            .addParameter("androidConPerPho", contactPersonPhoneno.getText().toString())
                                            .addParameter("androidEmail", emailid.getText().toString())
                                            .addParameter("androidPassword", password.getText().toString())
                                            .addParameter("totalBed", totalbed.getText().toString())
                                            .addParameter("availableBed", avaliblebed.getText().toString())
                                            .addFileToUpload(path, "file")
                                            .setMaxRetries(2)
                                            .startUpload();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            pd.dismiss();
                                            new ToastIntentClass(HospitalRegisterActivity.this, "Hospital Registered Successfully");
                                        }
                                    }, 3000);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                new ToastIntentClass(HospitalRegisterActivity.this, "Please Select Image");
                            }
                        } else {
                            new ConnectionDetector(HospitalRegisterActivity.this).connectiondetect();
                        }
                    } else {
                        citydropdown.setError("Valid City Required");
                    }
                    /*}
                    else{
                        specialitydropDownText.setError("Valid Speciality Required");
                    }*/

                }

            }
        });
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getImage(Uri uri) {
        if (uri != null) {
            String path = null;
            String[] a_array = {MediaStore.Images.Media.DATA};
            Cursor c = managedQuery(uri, a_array, null, null, null);
            int id = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (c.moveToFirst()) {
                do {
                    path = c.getString(id);
                }
                while (c.moveToNext());

                if (path != null) {
                    return path;
                }
            }
        }
        return "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            filePath = data.getData();
            iv.setImageURI(filePath);
        }
    }

    private class hospitalRegisterData extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(HospitalRegisterActivity.this);
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("androidHospitalName", hospitalName.getText().toString());
            hashMap.put("androidDoctorName", doctorName1.getText().toString());
            hashMap.put("androidDoctorName1", doctorName2.getText().toString());
            hashMap.put("androidDoctorName2", doctorName3.getText().toString());
            hashMap.put("androidSpecialty", specialitydropDownText.getText().toString());
            hashMap.put("androidAddress", address.getText().toString());
            hashMap.put("androidArea", area.getText().toString());
            hashMap.put("androidCity", citydropdown.getText().toString());
            hashMap.put("androidPincode", pincode.getText().toString());
            hashMap.put("androidConPerName", contactPersonName.getText().toString());
            hashMap.put("androidConPerMob", contactPersonMobileno.getText().toString());
            hashMap.put("androidConPerPho", contactPersonPhoneno.getText().toString());
            hashMap.put("androidEmail", emailid.getText().toString());
            hashMap.put("androidPassword", password.getText().toString());

            return new MakeServiceCall().MakeServiceCall(ConstantUrl.URL + "hospitalRegistration.php", MakeServiceCall.POST, hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("Status").equalsIgnoreCase("True")) {
                    new ToastIntentClass(HospitalRegisterActivity.this, object.getString("Message"));
                } else {
                    new ToastIntentClass(HospitalRegisterActivity.this, object.getString("Message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}