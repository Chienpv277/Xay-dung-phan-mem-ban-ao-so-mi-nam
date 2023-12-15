/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServiceImpl;

import DomainModel.NhanVien;
import Repository.NhanVienRepository;
import Service.ViewLoginService;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author ADAMIN
 */
public class ViewLoginServiceImpl implements ViewLoginService {

    private NhanVienRepository nvr = new NhanVienRepository();

    @Override
    public NhanVien getOne(String user, String password) {
        return nvr.getOne(user, password);
    }

    @Override
    public String validateLogin(JTextField username, JPasswordField pass) {
        if (username.getText().trim().isEmpty()) {
            return "Tài khoản không được trống";
        } else if (pass.getText().trim().isEmpty()) {
            return "Mật khẩu không được trống";
        } else if (username.getText().trim().length() < 8) {
            return "Tài khoản phải lớn hơn 8 kí tự";
        } else if (pass.getText().trim().length() < 8) {
            return "Mật khẩu phải lớn hơn 8 kí tự";
        } else {
            return " ";
        }
    }

}
