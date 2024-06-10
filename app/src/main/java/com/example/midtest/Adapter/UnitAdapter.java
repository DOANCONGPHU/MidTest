package com.example.midtest.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtest.R;

import java.util.List;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.UnitViewHolder> {

    private List<String> unitNames;
    private OnItemClickListener onItemClickListener;

    public UnitAdapter(List<String> unitNames, OnItemClickListener onItemClickListener) {
        this.unitNames = unitNames;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public UnitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new UnitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitViewHolder holder, int position) {
        String unitName = unitNames.get(position);
        holder.tvUnitName.setText(unitName);
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return unitNames.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class UnitViewHolder extends RecyclerView.ViewHolder {
        TextView tvUnitName;

        public UnitViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUnitName = itemView.findViewById(R.id.NAME);
        }
    }
}
