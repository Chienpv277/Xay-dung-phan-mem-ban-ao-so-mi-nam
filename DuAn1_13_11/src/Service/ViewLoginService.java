/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Service;

import DomainModel.NhanVien;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author ADAMIN
 */
public interface ViewLoginService {
    NhanVien getOne(String user, String password);
    
    String validateLogin(JTextField username, JPasswordField pass);
}
