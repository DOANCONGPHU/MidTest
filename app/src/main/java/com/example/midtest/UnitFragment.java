package com.example.midtest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtest.Adapter.UnitAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class UnitFragment extends Fragment {

    private RecyclerView recyclerView;
    private UnitAdapter unitAdapter;
    private FirebaseFirestore db;
    private List<String> unitNames;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_unit_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        unitNames = new ArrayList<>();
        unitAdapter = new UnitAdapter(unitNames, position -> {
            // Xử lý khi người dùng nhấp vào một đơn vị
            String unitId = unitNames.get(position);
            // Ví dụ: chuyển sang màn hình chi tiết đơn vị
            // Bạn có thể truyền unitId vào Intent hoặc Fragment khác
        });
        recyclerView.setAdapter(unitAdapter);

        db = FirebaseFirestore.getInstance();
        loadUnitNames();
    }

    private void loadUnitNames() {
        db.collection("donvi").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String unitName = document.getString("tenDonVi");
                    unitNames.add(unitName);
                }
                unitAdapter.notifyDataSetChanged();
            } else {
                // Xử lý lỗi khi lấy dữ liệu
            }
        });
    }
}
