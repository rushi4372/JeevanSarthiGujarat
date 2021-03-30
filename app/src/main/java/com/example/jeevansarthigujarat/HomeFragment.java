package com.example.jeevansarthigujarat;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class HomeFragment extends Fragment {

    ImageSlider slider;

    RecyclerView recyclerView;
    ArrayList<SpecialitiesList> arrayList;
    TextView topText;


    public HomeFragment() {
        // Required empty public constructor
    }
//#0AB2CC colour code


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        slider = view.findViewById(R.id.home_banner_slider);
        topText = view.findViewById(R.id.home_commonsymptom_text);

        recyclerView = view.findViewById(R.id.home_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());



        if (new ConnectionDetector(getActivity()).isConnectingToInternet()){
                new getBannerData().execute();
                new getSpecialitiesData().execute();



        }
        else {
                new ConnectionDetector(getActivity()).connectiondetect();
        }
        return view;
    }

    private class getBannerData extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            return new MakeServiceCall().MakeServiceCall(ConstantUrl.URL+"getBanner.php",MakeServiceCall.POST,new HashMap<>());
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("Status").equalsIgnoreCase("True")){

                    JSONArray array = object.getJSONArray("response");
                    ArrayList<SlideModel> arrayList = new ArrayList<>();
                    for (int i=0;i<array.length();i++){
                        JSONObject jsonObject = array.getJSONObject(i);
                        arrayList.add(new SlideModel(jsonObject.getString("image"),ScaleTypes.FIT));
                        slider.setImageList(arrayList, ScaleTypes.FIT);


                    }

                }
                else {
                    new ToastIntentClass(getActivity(),object.getString("Message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class getSpecialitiesData extends AsyncTask<String,String,String> {
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            return new MakeServiceCall().MakeServiceCall(ConstantUrl.URL+"getSpecialities.php",MakeServiceCall.POST,new HashMap<>());
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("Status").equalsIgnoreCase("True")){
                        topText.setVisibility(View.VISIBLE);
                        JSONArray array = object.getJSONArray("response");
                        arrayList = new ArrayList<>();
                        for (int i=0;i<array.length();i++){
                            JSONObject jsonObject = array.getJSONObject(i);
                            SpecialitiesList list = new SpecialitiesList();
                            list.setId(jsonObject.getString("id"));
                            list.setName(jsonObject.getString("name"));
                            list.setImage(jsonObject.getString("image"));
                            arrayList.add(list);
                        }
                        SpecialitiesAdapter adapter = new SpecialitiesAdapter(getActivity(),arrayList);
                        recyclerView.setAdapter(adapter);
                }
                else {
                    topText.setVisibility(View.GONE);
                    new ToastIntentClass(getActivity(),object.getString("Message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}