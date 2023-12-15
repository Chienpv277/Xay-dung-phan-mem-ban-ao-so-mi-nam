/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DomainModel;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author 84386
 */
public class KhachHang {

    private int id_khach_hang;

    private String ma;

    private String ho_ten;

    private String sdt;

    private String email;

    private String dia_chi;

    private LocalDate created_at;

    private LocalDate updated_at;

    private String created_by;

    private String updated_by;

    private String deleleted;

    public KhachHang() {
    }

    public KhachHang(int id_khach_hang, String ma, String ho_ten, String sdt, String email, String dia_chi, LocalDate created_at, LocalDate updated_at, String created_by, String updated_by, String deleleted) {
        this.id_khach_hang = id_khach_hang;
        this.ma = ma;
        this.ho_ten = ho_ten;
        this.sdt = sdt;
        this.email = email;
        this.dia_chi = dia_chi;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.created_by = created_by;
        this.updated_by = updated_by;
        this.deleleted = deleleted;
    }

    public int getId_khach_hang() {
        return id_khach_hang;
    }

    public void setId_khach_hang(int id_khach_hang) {
        this.id_khach_hang = id_khach_hang;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getHo_ten() {
        return ho_ten;
    }

    public void setHo_ten(String ho_ten) {
        this.ho_ten = ho_ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDia_chi() {
        return dia_chi;
    }

    public void setDia_chi(String dia_chi) {
        this.dia_chi = dia_chi;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public LocalDate getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDate updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getDeleleted() {
        return deleleted;
    }

    public void setDeleleted(String deleleted) {
        this.deleleted = deleleted;
    }

    @Override
    public String toString() {
        return "KhachHang{" + "id_khach_hang=" + id_khach_hang + ", ma=" + ma + ", ho_ten=" + ho_ten + ", sdt=" + sdt + ", email=" + email + ", dia_chi=" + dia_chi + '}';
    }
    
    
}
