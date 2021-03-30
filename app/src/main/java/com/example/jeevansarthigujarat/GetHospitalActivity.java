package com.example.jeevansarthigujarat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GetHospitalActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<GetHospitalList> arrayList;

    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_hospital);

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);
        getSupportActionBar().setTitle(sp.getString(ConstantSp.SPECIALITY_NAME,""));

        recyclerView = findViewById(R.id.gethospital_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (new ConnectionDetector(GetHospitalActivity.this).isConnectingToInternet()){
            new getHospitalData().execute();
        }
        else {
            new ConnectionDetector(GetHospitalActivity.this).connectiondetect();
        }
    }

    private class getHospitalData extends AsyncTask<String,String,String> {
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(GetHospitalActivity.this);
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("type",sp.getString(ConstantSp.TYPE,""));
            hashMap.put("speciality",sp.getString(ConstantSp.SPECIALITY_NAME,""));
            return new MakeServiceCall().MakeServiceCall(ConstantUrl.URL+"getHospital.php",MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("Status").equalsIgnoreCase("True")){

                    JSONArray array = object.getJSONArray("response");
                    arrayList = new ArrayList<>();
                    for (int i=0;i<array.length();i++){
                        JSONObject jsonObject = array.getJSONObject(i);
                        GetHospitalList list = new GetHospitalList();
                        /*doctor_id,hospitalName,doctorName,doctorName1,doctorName2,specialty_of_hospital,address,area,
                                city,pincode,contact_person_name,contact_person_mobileNo,contact_person_phoneNo,Emailid;*/

                        list.setDoctor_id(jsonObject.getString("doctor_id"));
                        list.setHospitalName(jsonObject.getString("hospitalName"));
                        list.setDoctorName(jsonObject.getString("doctorName"));
                        list.setDoctorName1(jsonObject.getString("doctorName1"));
                        list.setDoctorName2(jsonObject.getString("doctorName2"));
                        list.setSpecialty_of_hospital(jsonObject.getString("specialty_of_hospital"));
                        list.setAddress(jsonObject.getString("address"));
                        list.setArea(jsonObject.getString("area"));
                        list.setCity(jsonObject.getString("city"));
                        list.setPincode(jsonObject.getString("pincode"));
                        list.setContact_person_name(jsonObject.getString("contact_person_name"));
                        list.setContact_person_mobileNo(jsonObject.getString("contact_person_mobileNo"));
                        list.setContact_person_phoneNo(jsonObject.getString("contact_person_phoneNo"));
                        list.setEmailid(jsonObject.getString("Emailid"));
                        list.setImage(jsonObject.getString("hospital_image"));
                        arrayList.add(list);

                    }
                    GetHospitalAdapter adapter = new GetHospitalAdapter(GetHospitalActivity.this,arrayList);
                    recyclerView.setAdapter(adapter);
                }
                else {

                    new ToastIntentClass(GetHospitalActivity.this,object.getString("Message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}