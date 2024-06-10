package com.example.midtest.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.midtest.Model.EmployeeModel;
import com.example.midtest.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private List<EmployeeModel> employees;
    private OnItemClickListener onItemClickListener;

    public EmployeeAdapter(List<EmployeeModel> employees, OnItemClickListener onItemClickListener) {
        this.employees = employees;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        EmployeeModel employee = employees.get(position);

        // Đảm bảo tên nhân viên không null
        if (employee.getHoTen() != null) {
            holder.tvEmployeeName.setText(employee.getHoTen());
        } else {

        }

        // Đảm bảo URL hình ảnh không null hoặc trống
        if (employee.getImageUrl() != null && !employee.getImageUrl().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(employee.getImageUrl())
                    .placeholder(R.drawable.ic_person) // Hình ảnh chờ
                    .into(holder.ivEmployeeImage);
        } else {
            holder.ivEmployeeImage.setImageResource(R.drawable.ic_person); // Hình ảnh mặc định
        }

        // Đặt trình nghe sự kiện click
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return employees != null ? employees.size() : 0;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmployeeName;
        CircleImageView ivEmployeeImage;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmployeeName = itemView.findViewById(R.id.NAME);
            ivEmployeeImage = itemView.findViewById(R.id.image_topic);
            if (ivEmployeeImage == null) {
                Log.e("EmployeeViewHolder", "ivEmployeeImage is null");
            }
        }
    }
}

