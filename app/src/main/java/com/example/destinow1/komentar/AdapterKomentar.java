package com.example.destinow1.komentar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.example.destinow1.R;

import java.util.ArrayList;
import java.util.List;
public class AdapterKomentar extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private DatabaseReference ulasanRef;
    private List<Datakomentar> dataList;
    public AdapterKomentar (Context context, List<Datakomentar> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.ulasanRef = ulasanRef;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_komentar, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Datakomentar review = dataList.get(position);

        Glide.with(context).load(dataList.get(position).getDataGambarkomentar()).into(holder.gambarkomentar);
        holder.txtnamakomentar.setText(dataList.get(position).getNamaKomentar());
        holder.txtisikomentar.setText(dataList.get(position).getKomentar());

    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public void searchDataList(ArrayList<Datakomentar> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }
}
class MyViewHolder extends RecyclerView.ViewHolder{
    TextView txtnamakomentar, txtisikomentar;
    ImageView gambarkomentar;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        gambarkomentar = itemView.findViewById(R.id.gmabrkomentar1);
        txtnamakomentar = itemView.findViewById(R.id.nama_komentar);
        txtisikomentar = itemView.findViewById(R.id.textkomentar);


    }
}