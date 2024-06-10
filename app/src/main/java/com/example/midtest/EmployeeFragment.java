package com.example.midtest;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtest.Adapter.EmployeeAdapter;
import com.example.midtest.Model.EmployeeModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;
public class EmployeeFragment extends Fragment {

    private RecyclerView recyclerView;
    private EmployeeAdapter employeeAdapter;
    private FirebaseFirestore db;

    // Không cần List riêng cho tên và ảnh, dùng List<EmployeeModel>
    private List<EmployeeModel> employees = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_employee_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewEmployee);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        employeeAdapter = new EmployeeAdapter(employees, position -> {

        });
        recyclerView.setAdapter(employeeAdapter);

        db = FirebaseFirestore.getInstance();
        loadEmployeeData();
    }

    private void loadEmployeeData() {
        db.collection("nhanvien").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                employees.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String maNhanVien = document.getString("maNhanVien"); // Lấy maNhanVien kiểu Long và chuyển về int
                    String hoTen = document.getString("hoTen");
                    String chucVu = document.getString("chucVu");
                    String email = document.getString("email");
                    String soDienThoai = document.getString("soDienThoai");
                    String anhDaiDien = document.getString("imageUrl");
                    String maDonVi = document.getString("maDonVi");
                    String image = document.getString("image"); // Lấy maDonVi kiểu Long và chuyển về int

                    // Thêm thông tin vào mô hình EmployeeModel
                    employees.add(new EmployeeModel(maNhanVien, hoTen, chucVu, email, soDienThoai, anhDaiDien, maDonVi,image));
                }
                employeeAdapter.notifyDataSetChanged();
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());
                Toast.makeText(getContext(), "Lỗi khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
