package com.example.midtest;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddUnitActivity extends AppCompatActivity {
    private EditText etMaDonVi, etTenDonVi, etEmail, etWebsite, etLogo, etDiaChi, etSoDienThoai, etMaDonViCha;
    private Button btnSave;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_unit);

        db = FirebaseFirestore.getInstance();

        etMaDonVi = findViewById(R.id.etMaDonVi);
        etTenDonVi = findViewById(R.id.etTenDonVi);
        etEmail = findViewById(R.id.etEmail);
        etWebsite = findViewById(R.id.etWebsite);

        etDiaChi = findViewById(R.id.etDiaChi);
        etSoDienThoai = findViewById(R.id.etSoDienThoai);
        etMaDonViCha = findViewById(R.id.etMaDonViCha);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> saveUnit());
    }

    private void saveUnit() {
        String maDonVi = etMaDonVi.getText().toString();
        String tenDonVi = etTenDonVi.getText().toString();
        String email = etEmail.getText().toString();
        String website = etWebsite.getText().toString();
        String logo = etLogo.getText().toString();
        String diaChi = etDiaChi.getText().toString();
        String soDienThoai = etSoDienThoai.getText().toString();
        String maDonViCha = etMaDonViCha.getText().toString();

        Map<String, Object> unit = new HashMap<>();
        unit.put("maDonVi", maDonVi);
        unit.put("tenDonVi", tenDonVi);
        unit.put("email", email);
        unit.put("website", website);
        unit.put("logo", logo);
        unit.put("diaChi", diaChi);
        unit.put("soDienThoai", soDienThoai);
        unit.put("maDonViCha", maDonViCha);

        db.collection("donvi").add(unit)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Thêm đơn vị thành công", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Lỗi khi thêm đơn vị", Toast.LENGTH_SHORT).show();
                });
    }
}
