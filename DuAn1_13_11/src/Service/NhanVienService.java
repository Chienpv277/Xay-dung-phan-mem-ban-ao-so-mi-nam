/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Service;

import DomainModel.NhanVien;
import java.util.List;

/**
 *
 * @author ADAMIN
 */
public interface NhanVienService {

    List<NhanVien> getAll();

    String add(NhanVien nhanVien);
    
    List<NhanVien> getAllNghi();
    
    String update(NhanVien nhanVien, int id);

    List<NhanVien> searchByName(String hoTen);

    List<NhanVien> searchByEmail(String email);

    List<NhanVien> searchBySdt(String sdt);
}
