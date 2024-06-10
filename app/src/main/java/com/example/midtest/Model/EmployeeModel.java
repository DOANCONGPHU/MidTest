package com.example.midtest.Model;


public class EmployeeModel {
    private String maNhanVien;     // Mã nhân viên (ID)
    private String hoTen;       // Họ và tên
    private String chucVu;      // Chức vụ (có thể null)
    private String email;       // Email
    private String soDienThoai;  // Số điện thoại
    private String anhDaiDien;  // Đường dẫn hoặc URI của ảnh đại diện (có thể null)
    private String maDonVi;        // Mã đơn vị (đơn vị trực thuộc)
    private String imageUrl;

    public EmployeeModel(String maNhanVien, String hoTen, String chucVu, String email, String soDienThoai, String anhDaiDien, String maDonVi, String imageUrl) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.chucVu = chucVu;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.anhDaiDien = anhDaiDien;
        this.maDonVi = maDonVi;
        this.imageUrl = imageUrl;
    }

    public EmployeeModel() {
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public String getMaDonVi() {
        return maDonVi;
    }

    public void setMaDonVi(String maDonVi) {
        this.maDonVi = maDonVi;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
