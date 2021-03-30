package com.example.jeevansarthigujarat;

import android.content.Context;
import android.content.SharedPreferences;
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

public class SpecialitiesAdapter extends RecyclerView.Adapter<SpecialitiesAdapter.MyHolder> {
    Context context;
    ArrayList<SpecialitiesList> arrayList;

    SharedPreferences sp;

    public SpecialitiesAdapter(FragmentActivity activity, ArrayList<SpecialitiesList> arrayList) {
        this.context = activity;
        this.arrayList = arrayList;
        sp = context.getSharedPreferences(ConstantSp.PREF,Context.MODE_PRIVATE);

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_specialities,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.name.setText(arrayList.get(position).getName());
        Picasso.get().load(arrayList.get(position).getImage()).placeholder(R.drawable.placeholder).into(holder.iv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putString(ConstantSp.SPECIALITY_ID,arrayList.get(position).getId()).commit();
                sp.edit().putString(ConstantSp.SPECIALITY_NAME,arrayList.get(position).getName()).commit();
                new ToastIntentClass(context,GetHospitalActivity.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView name;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.custom_specialities_iv);
            name = itemView.findViewById(R.id.custom_specialities_name);
        }
    }
}
