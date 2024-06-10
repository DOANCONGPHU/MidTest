package com.example.midtest;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.content.ContentResolver;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddEmployeeActivity extends AppCompatActivity {

    private EditText etMaNhanVien, etHoTen, etChucVu, etEmail, etSoDienThoai, etMaDonVi;
    private Button btnSave;
    private CircleImageView imageView;
    private Uri imageUri;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    ProgressDialog progressDialog;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        // Khởi tạo Firebase
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        // Ánh xạ các view
        etMaNhanVien = findViewById(R.id.etMaNhanVien);
        etHoTen = findViewById(R.id.etHoTen);
        etChucVu = findViewById(R.id.etChucVu);
        etEmail = findViewById(R.id.etEmail);
        etSoDienThoai = findViewById(R.id.etSoDienThoai);
        etMaDonVi = findViewById(R.id.etMaDonVi);
        btnSave = findViewById(R.id.btnSave);
        imageView = findViewById(R.id.avatar);

        // Xử lý sự kiện click vào button Save
        btnSave.setOnClickListener(v -> saveEmployee());

        // Xử lý sự kiện click vào ImageView để chọn ảnh
        imageView.setOnClickListener(v -> openFileChooser());

        // Tạo ProgressDialog
        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("Đang tải lên");
        progressDialog.setMessage("Loading");
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void saveEmployee() {
        progressDialog.show();
        String maNhanVien = etMaNhanVien.getText().toString();
        String hoTen = etHoTen.getText().toString();
        String chucVu = etChucVu.getText().toString();
        String email = etEmail.getText().toString();
        String soDienThoai = etSoDienThoai.getText().toString();
        String maDonVi = etMaDonVi.getText().toString();
        if (imageUri != null) {
            StorageReference storageRef = storage.getReference("nhanvien");
            StorageReference fileRef = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            fileRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();

                        Map<String, Object> employee = new HashMap<>();
                        employee.put("maNhanVien", maNhanVien);
                        employee.put("hoTen", hoTen);
                        employee.put("chucVu", chucVu);
                        employee.put("email", email);
                        employee.put("soDienThoai", soDienThoai);
                        employee.put("maDonVi", maDonVi);
                        employee.put("anhDaiDien", imageUrl);

                        db.collection("nhanvien").add(employee)
                                .addOnSuccessListener(documentReference -> {
                                    progressDialog.dismiss();
                                    Toast.makeText(this, "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    progressDialog.dismiss();
                                    Toast.makeText(this, "Lỗi khi thêm nhân viên", Toast.LENGTH_SHORT).show();
                                });
                    }))
                    .addOnFailureListener(e ->{progressDialog.dismiss(); Toast.makeText(this, "Lỗi khi tải ảnh lên", Toast.LENGTH_SHORT).show();});
        } else {
            Toast.makeText(this, "Vui lòng chọn ảnh đại diện", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
