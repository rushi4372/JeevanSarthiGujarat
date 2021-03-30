package com.example.jeevansarthigujarat;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GetHospitalAdapter extends RecyclerView.Adapter<GetHospitalAdapter.MyHolder> {
    Context context;
    ArrayList<GetHospitalList> arrayList;

    SharedPreferences sp;

    public GetHospitalAdapter(GetHospitalActivity getHospitalActivity, ArrayList<GetHospitalList> arrayList) {
        this.context = getHospitalActivity;
        this.arrayList = arrayList;
        sp = context.getSharedPreferences(ConstantSp.PREF,Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_gethospital,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        holder.hospitalName.setText(arrayList.get(position).getHospitalName());
        holder.hospitalEmail.setText("  "+arrayList.get(position).getEmailid());
        holder.hospitalContact.setText("  "+arrayList.get(position).getContact_person_mobileNo());

        Picasso.get().load(arrayList.get(position).getImage()).placeholder(R.drawable.placeholder).into(holder.iv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().putString(ConstantSp.HOSPITALID,arrayList.get(position).getDoctor_id()).commit();
                sp.edit().putString(ConstantSp.HNAME,arrayList.get(position).getHospitalName()).commit();
                sp.edit().putString(ConstantSp.DOCNAME,arrayList.get(position).getDoctorName()).commit();
                sp.edit().putString(ConstantSp.DOCNAME1,arrayList.get(position).getDoctorName1()).commit();
                sp.edit().putString(ConstantSp.DOCNAME2,arrayList.get(position).getDoctorName2()).commit();

                sp.edit().putString(ConstantSp.SPECIALITY_NAME,arrayList.get(position).getSpecialty_of_hospital()).commit();

                sp.edit().putString(ConstantSp.ADDRESS,arrayList.get(position).getAddress()).commit();
                sp.edit().putString(ConstantSp.AREA,arrayList.get(position).getArea()).commit();
                sp.edit().putString(ConstantSp.CITY,arrayList.get(position).getCity()).commit();
                sp.edit().putString(ConstantSp.PINCODE,arrayList.get(position).getPincode()).commit();

                sp.edit().putString(ConstantSp.CONTACT_PERSON,arrayList.get(position).getContact_person_name()).commit();
                sp.edit().putString(ConstantSp.CONTACT_PERSON_MOBILE,arrayList.get(position).getContact_person_mobileNo()).commit();
                sp.edit().putString(ConstantSp.CONTACT_PERSON_PHONE,arrayList.get(position).getContact_person_phoneNo()).commit();
                sp.edit().putString(ConstantSp.EMAIL,arrayList.get(position).getEmailid()).commit();


                sp.edit().putString(ConstantSp.IMAGE,arrayList.get(position).getImage()).commit();
                new ToastIntentClass(context,HospitalDetailActivity.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView hospitalName,hospitalEmail,hospitalContact;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.custom_gethospital_iv);
            hospitalName = itemView.findViewById(R.id.custom_gethospital_hospitalname);
            hospitalEmail = itemView.findViewById(R.id.custom_gethospital_hospita_email);
            hospitalContact = itemView.findViewById(R.id.custom_gethospital_hospita_contact);
        }
    }
}
