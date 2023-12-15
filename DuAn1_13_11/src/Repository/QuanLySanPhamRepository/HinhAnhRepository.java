/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository.QuanLySanPhamRepository;

import DomainModel.QuanLySanPham.HinhAnh;
import Utilities.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class HinhAnhRepository {
    private DBConnection connection;

    public ArrayList<HinhAnh> getListHinhAnh() {
        ArrayList<HinhAnh> list = new ArrayList<>();
        String sql = "select id_chat_lieu, ma, ten from chat_lieu";
        try ( Connection con = connection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HinhAnh hinhAnh = new HinhAnh();
                hinhAnh.setId(rs.getInt(1));
                hinhAnh.setMa(rs.getString(2));
                hinhAnh.setTen(rs.getString(3));
                list.add(hinhAnh);
            }
        } catch (Exception e) {

        }
        return list;
    }

    public HinhAnh getHinhAnhById(int id) {
    HinhAnh hinhAnh = null;
    String sql = "select id_chat_lieu, ma, ten from chat_lieu where id_chat_lieu = ?";
    
    try (Connection con = connection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            hinhAnh = new HinhAnh();
            hinhAnh.setId(rs.getInt(1));
            hinhAnh.setMa(rs.getString(2));
            hinhAnh.setTen(rs.getString(3));
        }
    } catch (Exception e) {
       e.getMessage();
    }
    
    return hinhAnh;
}

    
    public Boolean add(HinhAnh hinhAnh) {
        String sql = "insert into chat_lieu(ma, ten) values (?, ?)";
        try ( Connection con = connection.getConnection();  
               PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, hinhAnh.getMa());
            ps.setObject(2, hinhAnh.getTen());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {

        }
        return false;
    }
    
    public Boolean update(Integer id, HinhAnh hinhAnh){
        String sql = "update chat_lieu set ma = ?, ten = ?  where id_chat_lieu = ?";
        try ( Connection con = connection.getConnection();  
               PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, hinhAnh.getMa());
            ps.setObject(2, hinhAnh.getTen());
            ps.setObject(3, hinhAnh.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public Boolean delete(Integer id){
        String sql = "delete from chat_lieu where id_chat_lieu = ?";
        try ( Connection con = connection.getConnection();  
               PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {

        }
        return false;
    }
    public static void main(String[] args) {
        HinhAnh list = new HinhAnhRepository().getHinhAnhById(1);
        
            System.out.println(list.toString());
        
    }
}
