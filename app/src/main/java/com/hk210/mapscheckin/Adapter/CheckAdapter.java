package com.hk210.mapscheckin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hk210.mapscheckin.Model.Check;
import com.hk210.mapscheckin.R;

import org.w3c.dom.Text;

import java.util.List;

public class CheckAdapter extends RecyclerView.Adapter<CheckAdapter.ViewHolder> {
    private Context context;
    private List<Check> data;

    public CheckAdapter(Context context, List<Check> data) {
        this.data = data;
        this.context = context;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_checkins,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.t1.setText("Latitude: "+data.get(position).getLat());
        holder.t2.setText("Longitude: "+data.get(position).getLang());
        holder.t3.setText("Time: "+data.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView t1,t2,t3;

        public ViewHolder(View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.latitudeText);
            t2 = itemView.findViewById(R.id.logitudeText);
            t3 = itemView.findViewById(R.id.timeText);
        }
    }
}
