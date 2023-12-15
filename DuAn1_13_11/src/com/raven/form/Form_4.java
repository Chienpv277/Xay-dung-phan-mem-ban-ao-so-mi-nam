/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import DomainModel.ChatLieu;
import DomainModel.ChiTietSanPham;
import DomainModel.CoAo;
import DomainModel.CoTay;
import DomainModel.DangAo;
import DomainModel.Hang;
import DomainModel.MauSac;
import DomainModel.MyCombobox;
import DomainModel.SanPham;
import DomainModel.Size;
import Service.ChatLieuService;
import Service.ChiTietService;
import Service.CoAoService;
import Service.CoTayService;
import Service.DangAoService;
import Service.HangService;
import ServiceImpl.ChatLieuServiceImpl;
import ServiceImpl.ChiTietServiceImpl;
import ServiceImpl.CoAoServiceImpl;
import ServiceImpl.CoTayServiceImpl;
import ServiceImpl.DangAoServiceImpl;
import ServiceImpl.HangServiceImpl;
import ServiceImpl.MauSacServiceImpl;
import ServiceImpl.SanPhamSrviceImpl;
import ServiceImpl.SizeServiceImpl;
import Service.MauSacService;
import Service.SanPhamService;
import Service.SizeService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Form_4 extends javax.swing.JPanel {

    private DefaultTableModel defaultTableModel = new DefaultTableModel();
    private DefaultComboBoxModel defaultComboBoxModel;
    private ChiTietService chiTietService = new ChiTietServiceImpl();
    private ChatLieuService chatLieuService = new ChatLieuServiceImpl();
    private CoAoService coAoService = new CoAoServiceImpl();
    private CoTayService coTayService = new CoTayServiceImpl();
    private DangAoService dangAoService = new DangAoServiceImpl();
    private HangService hangService = new HangServiceImpl();
    private MauSacService mauSacService = new MauSacServiceImpl();
    private SanPhamService sanPhamService = new SanPhamSrviceImpl();
    private SizeService sizeService = new SizeServiceImpl();
    private DefaultTableModel model;

    private ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
    private MauSac mauSac = new MauSac();
    private ChatLieu chatLieu = new ChatLieu();
    private SanPham sanPham = new SanPham();
    private Hang hang = new Hang();
    private DangAo dangAo = new DangAo();
    private CoAo coAo = new CoAo();
    private CoTay coTay = new CoTay();
    private Size size = new Size();

    private ArrayList<MauSac> listMauSacs = new ArrayList<>();
    private ArrayList<ChatLieu> listChatLieus = new ArrayList<>();
    private ArrayList<SanPham> listSanPhams = new ArrayList<>();
    private ArrayList<Hang> listHangs = new ArrayList<>();
    private ArrayList<DangAo> listdDangAos = new ArrayList<>();
    private ArrayList<CoAo> listCoAos = new ArrayList<>();
    private ArrayList<CoTay> listCoTays = new ArrayList<>();
    private ArrayList<Size> listSizes = new ArrayList<>();
    private ArrayList<ChiTietSanPham> listChiTiet = new ArrayList<>();

    public Form_4() {
        initComponents();
        loadSanPham(sanPhamService.getListSanPham());
        loadDataCTSP(chiTietService.getListChiTiet());
        addCboChatLieu();
        addCboCoAo();
        addCboCoTay();
        addCboDangAo();
        addCboHang();
        addCboMauSac();
        addCboSanPham();
        addCboSize();
        addCboFilterSP();
        addCboFilterHang();
        addCboFilterChatLieu();
        addCboFilterDangAo();
        addCboFilterCoAo();
        addCboFilterCoTay();
        addCboFilterSize();
        addCboFilterMauSac();
        rdoHang.setSelected(true);
        loadHang(hangService.getListHang());
    }

    public void loadDataCTSP(ArrayList<ChiTietSanPham> chiTietSanPhams) {
        int i = 1;
        defaultTableModel = (DefaultTableModel) tblChiTiet.getModel();
        defaultTableModel.setRowCount(0);
        for (DomainModel.ChiTietSanPham x : chiTietSanPhams) {
            defaultTableModel.addRow(new Object[]{
                i++,
                x.getMaCTSP(),
                x.getSanPham().getTen(),
                x.getHang().getTen(),
                x.getMauSac().getTen(),
                x.getChatLieu().getTen(),
                x.getSize().getTen(),
                x.getDangAo().getTen(),
                x.getCoAo().getTen(),
                x.getCoTay().getTen(),
                x.getGia(),
                x.getSoLuong(),
                x.trangThai()
            });
        }
    }

    private void loadSanPham(ArrayList<SanPham> sanPhams) {
        int stt = 1;
        defaultTableModel = (DefaultTableModel) tblSanPham.getModel();
        defaultTableModel.setRowCount(0);
        Map<String, Integer> soLuongTheoTen = chiTietService.countSanPhamByTen();
        for (SanPham s : sanPhams) {
            String tenSP = s.getTen();
            int soLuong = soLuongTheoTen.getOrDefault(tenSP, 0);
            defaultTableModel.addRow(new Object[]{
                stt++, s.getMa(), tenSP, soLuong
            });
        }
    }

    public void addCboChatLieu() {
        DefaultComboBoxModel cboChatLieuModel = new DefaultComboBoxModel<>();

        cboChatLieu.setModel(cboChatLieuModel);
        cboChatLieuModel.removeAllElements();

        ArrayList<ChatLieu> list = chatLieuService.getListChatLieu();
        for (ChatLieu x : list) {
            cboChatLieuModel.addElement(x.getTen());
        }
    }

    public void addCboCoAo() {
        DefaultComboBoxModel cboCoAoModel = new DefaultComboBoxModel<>();

        cboCoAo.setModel(cboCoAoModel);
        cboCoAoModel.removeAllElements();

        ArrayList<CoAo> list = coAoService.getListCoAo();
        for (CoAo x : list) {
            cboCoAoModel.addElement(x.getTen());
        }
    }

    public void addCboCoTay() {
        DefaultComboBoxModel cboCoTayModel = new DefaultComboBoxModel<>();

        cboCoTay.setModel(cboCoTayModel);
        cboCoTayModel.removeAllElements();

        ArrayList<CoTay> list = coTayService.getListCoTay();
        for (CoTay x : list) {
            cboCoTayModel.addElement(x.getTen());
        }
    }

    public void addCboDangAo() {
        DefaultComboBoxModel cboDangAoModel = new DefaultComboBoxModel<>();

        cboDangAo.setModel(cboDangAoModel);
        cboDangAoModel.removeAllElements();

        ArrayList<DangAo> list = dangAoService.getListDangAo();
        for (DangAo x : list) {
            cboDangAoModel.addElement(x.getTen());
        }
    }

    public void addCboHang() {
        DefaultComboBoxModel cboHangModel = new DefaultComboBoxModel<>();

        cboHang.setModel(cboHangModel);
        cboHangModel.removeAllElements();

        ArrayList<Hang> list = hangService.getListHang();
        for (Hang x : list) {
            cboHangModel.addElement(x.getTen());
        }
    }

    public void addCboMauSac() {
        DefaultComboBoxModel cboMauSacModel = new DefaultComboBoxModel<>();

        cboMauSac.setModel(cboMauSacModel);
        cboMauSacModel.removeAllElements();

        ArrayList<MauSac> list = mauSacService.getListMauSac();
        for (MauSac x : list) {
            cboMauSacModel.addElement(x.getTen());
        }
    }

    public void addCboSanPham() {
        DefaultComboBoxModel cboSanPhamModel = new DefaultComboBoxModel<>();

        cboTenSP.setModel(cboSanPhamModel);
        cboSanPhamModel.removeAllElements();

        ArrayList<SanPham> list = sanPhamService.getListSanPham();
        for (SanPham x : list) {
            cboSanPhamModel.addElement(x.getTen());
        }
//        ArrayList<SanPham> list = sanPhamService.getListSanPham();
//        cboTenSP.removeAllItems();
//        for(SanPham sanPham : list){
//            cboTenSP.addItem(sanPham.getTen());
//        }
//        cboTenSP.revalidate();
//        cboTenSP.repaint();
    }

    public void addCboSize() {
        DefaultComboBoxModel cboSizeModel = new DefaultComboBoxModel<>();

        cboSize.setModel(cboSizeModel);
        cboSizeModel.removeAllElements();

        ArrayList<Size> list = sizeService.getListSize();
        for (Size x : list) {
            cboSizeModel.addElement(x.getTen());
        }
    }

    private void loadChatLieu(ArrayList<ChatLieu> list) {
        int i = 1;
        defaultTableModel = (DefaultTableModel) tblThuocTinh
                .getModel();
        defaultTableModel.setRowCount(0);
        for (ChatLieu x : list) {
            defaultTableModel.addRow(new Object[]{
                i++, x.getMa(), x.getTen()
            });
        }
    }

    private void loadMauSac(ArrayList<MauSac> list) {
        int i = 1;
        defaultTableModel = (DefaultTableModel) tblThuocTinh.getModel();
        defaultTableModel.setRowCount(0);
        for (MauSac x : list) {
            defaultTableModel.addRow(new Object[]{
                i++, x.getMa(), x.getTen()
            });
        }
    }

    private void loadHang(ArrayList<Hang> list) {
        int i = 1;
        defaultTableModel = (DefaultTableModel) tblThuocTinh.getModel();
        defaultTableModel.setRowCount(0);
        for (Hang x : list) {
            defaultTableModel.addRow(new Object[]{
                i++, x.getMa(), x.getTen()
            });
        }
    }

    private void loadSize(ArrayList<Size> list) {
        int i = 1;
        defaultTableModel = (DefaultTableModel) tblThuocTinh.getModel();
        defaultTableModel.setRowCount(0);
        for (Size x : list) {
            defaultTableModel.addRow(new Object[]{
                i++, x.getMa(), x.getTen()
            });
        }
    }

    private void loadDangAo(ArrayList<DangAo> list) {
        int i = 1;
        defaultTableModel = (DefaultTableModel) tblThuocTinh.getModel();
        defaultTableModel.setRowCount(0);
        for (DangAo x : list) {
            defaultTableModel.addRow(new Object[]{
                i++, x.getMa(), x.getTen()
            });
        }
    }

    private void loadCoTay(ArrayList<CoTay> list) {
        int i = 1;
        defaultTableModel = (DefaultTableModel) tblThuocTinh.getModel();
        defaultTableModel.setRowCount(0);
        for (CoTay x : list) {
            defaultTableModel.addRow(new Object[]{
                i++, x.getMa(), x.getTen()
            });
        }
    }

    private void loadCoAo(ArrayList<CoAo> list) {
        int i = 1;
        defaultTableModel = (DefaultTableModel) tblThuocTinh.getModel();
        defaultTableModel.setRowCount(0);
        for (CoAo x : list) {
            defaultTableModel.addRow(new Object[]{
                i++, x.getMa(), x.getTen()
            });
        }
    }

    private boolean showConfirmDialog(String message) {
        int confirmResult = JOptionPane.showConfirmDialog(this, message, "Xác nhận cập nhật", JOptionPane.YES_NO_OPTION);
        return confirmResult == JOptionPane.YES_OPTION;
    }

    private void clearThuocTinh() {
        txtTen.setText("");
        txtGia.setText("");
    }

    public enum ThuocTinhType {
        CHAT_LIEU,
        CO_AO,
        CO_TAY,
        DANG_AO,
        HANG,
        MAU_SAC,
        SIZE
    }

    private void loadThuocTinh(ThuocTinhType thuocTinhType) {
        defaultTableModel = (DefaultTableModel) tblThuocTinh.getModel();
        defaultTableModel.setRowCount(0);

        switch (thuocTinhType) {
            case CHAT_LIEU:
                loadChatLieu(chatLieuService.getListChatLieu());
                break;
            case CO_AO:
                loadCoAo(coAoService.getListCoAo());
                break;
            case CO_TAY:
                loadCoTay(coTayService.getListCoTay());
                break;
            case DANG_AO:
                loadDangAo(dangAoService.getListDangAo());
                break;
            case HANG:
                loadHang(hangService.getListHang());
                break;
            case MAU_SAC:
                loadMauSac(mauSacService.getListMauSac());
                break;
            case SIZE:
                loadSize(sizeService.getListSize());
                break;
            // Thêm các case khác tương ứng với các loại thuộc tính
            default:
                break;
        }
    }

    private void addCboFilterSP() {
        ArrayList<SanPham> list = sanPhamService.getListSanPham();
        DefaultComboBoxModel defaultComboBoxModel1 = (DefaultComboBoxModel) cboFilterSanPham.getModel();
        for (SanPham x : list) {
            defaultComboBoxModel1.addElement(x.getTen());
        }
    }

    private void addCboFilterSize() {
        ArrayList<Size> list = sizeService.getListSize();
        DefaultComboBoxModel defaultComboBoxModel1 = (DefaultComboBoxModel) cboFilterSize.getModel();
        for (Size x : list) {
            defaultComboBoxModel1.addElement(x.getTen());
        }
    }

    private void addCboFilterMauSac() {
        ArrayList<MauSac> list = mauSacService.getListMauSac();
        DefaultComboBoxModel defaultComboBoxModel1 = (DefaultComboBoxModel) cboFilterMauSac.getModel();
        for (MauSac x : list) {
            defaultComboBoxModel1.addElement(x.getTen());
        }
    }

    private void addCboFilterCoAo() {
        ArrayList<CoAo> list = coAoService.getListCoAo();
        DefaultComboBoxModel defaultComboBoxModel1 = (DefaultComboBoxModel) cboFilterCoAo.getModel();
        for (CoAo x : list) {
            defaultComboBoxModel1.addElement(x.getTen());
        }
    }

    private void addCboFilterCoTay() {
        ArrayList<CoTay> list = coTayService.getListCoTay();
        DefaultComboBoxModel defaultComboBoxModel1 = (DefaultComboBoxModel) cboFilterCoTay.getModel();
        for (CoTay x : list) {
            defaultComboBoxModel1.addElement(x.getTen());
        }
    }

    private void addCboFilterHang() {
        ArrayList<Hang> list = hangService.getListHang();
        DefaultComboBoxModel defaultComboBoxModel1 = (DefaultComboBoxModel) cboFilterHang.getModel();
        for (Hang x : list) {
            defaultComboBoxModel1.addElement(x.getTen());
        }
    }

    private void addCboFilterChatLieu() {
        ArrayList<ChatLieu> list = chatLieuService.getListChatLieu();
        DefaultComboBoxModel defaultComboBoxModel1 = (DefaultComboBoxModel) cboFilterChatLieu.getModel();
        for (ChatLieu x : list) {
            defaultComboBoxModel1.addElement(x.getTen());
        }
    }

    private void addCboFilterDangAo() {
        ArrayList<DangAo> list = dangAoService.getListDangAo();
        DefaultComboBoxModel defaultComboBoxModel1 = (DefaultComboBoxModel) cboFilterDangAo.getModel();
        for (DangAo x : list) {
            defaultComboBoxModel1.addElement(x.getTen());
        }
    }

    private ChiTietSanPham getFormData() {
        if (txtSoLuong.getText().isEmpty() || txtGia.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin");
            return null;
        }
        try {
            Integer.parseInt(txtSoLuong.getText());
            Float.parseFloat(txtGia.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Bạn phải nhập số");
            return null;
        }
        String tenChatLieu = cboChatLieu.getSelectedItem().toString();
        String tenCoAo = cboCoAo.getSelectedItem().toString();
        String tenCoTay = cboCoTay.getSelectedItem().toString();
        String tenDangAO = cboDangAo.getSelectedItem().toString();
        String tenHang = cboHang.getSelectedItem().toString();
        String tenMauSac = cboMauSac.getSelectedItem().toString();
        String tenSanPham = cboTenSP.getSelectedItem().toString();
        String tenSize = cboSize.getSelectedItem().toString();
        int soLuong = Integer.valueOf(txtSoLuong.getText());
        float gia = Float.valueOf(txtGia.getText());

        chatLieu = chatLieuService.getIdByTenChatLieu(tenChatLieu);
        coAo = coAoService.getIdByTenCoAo(tenCoAo);
        coTay = coTayService.getIdByTenCoTay(tenCoTay);
        dangAo = dangAoService.getIdByTenDangAo(tenDangAO);
        hang = hangService.getIdByTenHang(tenHang);
        mauSac = mauSacService.getIdByTenMauSac(tenMauSac);
        sanPham = sanPhamService.getIdByTenSanPham(tenSanPham);
        size = sizeService.getIdByTenSize(tenSize);

        ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
        chiTietSanPham.setChatLieu(chatLieu);
        chiTietSanPham.setCoAo(coAo);
        chiTietSanPham.setCoTay(coTay);
        chiTietSanPham.setDangAo(dangAo);
        chiTietSanPham.setHang(hang);
        chiTietSanPham.setMauSac(mauSac);
        chiTietSanPham.setSanPham(sanPham);
        chiTietSanPham.setSize(size);
        chiTietSanPham.setSoLuong(soLuong);
        chiTietSanPham.setGia(gia);
        return chiTietSanPham;
    }

    private boolean validateFormData() {
        String ten = txtTen.getText();
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!ten.matches("^[a-zA-Z0-9\\s]+$")) {
            JOptionPane.showMessageDialog(this, "Không được chứa ký tự đặc biệt", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void addChatLieu() {

        ArrayList<String> existingMaList = new ArrayList<>();
        for (ChatLieu existingChatLieu : chatLieuService.getListChatLieu()) {
            existingMaList.add(existingChatLieu.getMa());
        }
        // Sinh mã mới
        String newMa;
        int suffix = 1;
        do {
            newMa = "CL" + String.format("%03d", suffix); // Ví dụ: CL001
            suffix++;
        } while (existingMaList.contains(newMa));

        String ten = txtTen.getText();

        ChatLieu cl = new ChatLieu();
        cl.setMa(newMa);
        cl.setTen(ten);

        if (chatLieuService.add(cl)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            loadChatLieu(chatLieuService.getListChatLieu());
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
        }
    }

    private void addCoAo() {
        // Lấy danh sách mã hiện có (để kiểm tra trùng lặp)
        ArrayList<String> existingMaList = new ArrayList<>();
        for (CoAo existingCoAo : coAoService.getListCoAo()) {
            existingMaList.add(existingCoAo.getMa());
        }
        // Sinh mã mới
        String newMa;
        int suffix = 1;
        do {
            newMa = "CA" + String.format("%03d", suffix); // Ví dụ: CA001
            suffix++;
        } while (existingMaList.contains(newMa));

        String ten = txtTen.getText();

        CoAo ca = new CoAo();
        ca.setMa(newMa);
        ca.setTen(ten);

        if (coAoService.add(ca)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            loadCoAo(coAoService.getListCoAo());
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
        }
    }

    private void addCoTay() {
        // Lấy danh sách mã hiện có (để kiểm tra trùng lặp)
        ArrayList<String> existingMaList = new ArrayList<>();
        for (CoTay existingCoTay : coTayService.getListCoTay()) {
            existingMaList.add(existingCoTay.getMa());
        }
        // Sinh mã mới
        String newMa;
        int suffix = 1;
        do {
            newMa = "CT" + String.format("%03d", suffix); // Ví dụ: CL001
            suffix++;
        } while (existingMaList.contains(newMa));

        String ten = txtTen.getText();

        CoTay cl = new CoTay();
        cl.setMa(newMa);
        cl.setTen(ten);

        if (coTayService.add(cl)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            loadCoTay(coTayService.getListCoTay());
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
        }
    }

    private void addDangAo() {
        // Lấy danh sách mã hiện có (để kiểm tra trùng lặp)
        ArrayList<String> existingMaList = new ArrayList<>();
        for (DangAo existingDangAo : dangAoService.getListDangAo()) {
            existingMaList.add(existingDangAo.getMa());
        }
        // Sinh mã mới
        String newMa;
        int suffix = 1;
        do {
            newMa = "DA" + String.format("%03d", suffix); // Ví dụ: CL001
            suffix++;
        } while (existingMaList.contains(newMa));

        String ten = txtTen.getText();

        DangAo cl = new DangAo();
        cl.setMa(newMa);
        cl.setTen(ten);

        if (dangAoService.add(cl)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            loadDangAo(dangAoService.getListDangAo());
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
        }
    }

    private void addHang() {
        // Lấy danh sách mã hiện có (để kiểm tra trùng lặp)
        ArrayList<String> existingMaList = new ArrayList<>();
        for (Hang existingHang : hangService.getListHang()) {
            existingMaList.add(existingHang.getMa());
        }
        // Sinh mã mới
        String newMa;
        int suffix = 1;
        do {
            newMa = "H" + String.format("%03d", suffix); // Ví dụ: CL001
            suffix++;
        } while (existingMaList.contains(newMa));

        String ten = txtTen.getText();

        Hang cl = new Hang();
        cl.setMa(newMa);
        cl.setTen(ten);

        if (hangService.add(cl)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            loadHang(hangService.getListHang());
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
        }
    }

    private void addMauSac() {
        // Lấy danh sách mã hiện có (để kiểm tra trùng lặp)
        ArrayList<String> existingMaList = new ArrayList<>();
        for (MauSac existingMauSac : mauSacService.getListMauSac()) {
            existingMaList.add(existingMauSac.getMa());
        }
        // Sinh mã mới
        String newMa;
        int suffix = 1;
        do {
            newMa = "MS" + String.format("%03d", suffix); // Ví dụ: CL001
            suffix++;
        } while (existingMaList.contains(newMa));

        String ten = txtTen.getText();

        MauSac cl = new MauSac();
        cl.setMa(newMa);
        cl.setTen(ten);

        if (mauSacService.add(cl)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            loadMauSac(mauSacService.getListMauSac());
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
        }
    }

    private void addSize() {
        // Lấy danh sách mã hiện có (để kiểm tra trùng lặp)
        ArrayList<String> existingMaList = new ArrayList<>();
        for (Size existingSize : sizeService.getListSize()) {
            existingMaList.add(existingSize.getMa());
        }
        // Sinh mã mới
        String newMa;
        int suffix = 1;
        do {
            newMa = "Sz" + String.format("%03d", suffix); // Ví dụ: CL001
            suffix++;
        } while (existingMaList.contains(newMa));

        String ten = txtTen.getText();

        Size cl = new Size();
        cl.setMa(newMa);
        cl.setTen(ten);

        if (sizeService.add(cl)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            loadSize(sizeService.getListSize());
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
        }
    }

    private void addThuocTinh(ThuocTinhType thuocTinhType) {
        String ma = txtMa.getText();
        String ten = txtTen.getText();

        switch (thuocTinhType) {
            case CHAT_LIEU:
                addChatLieu();
                break;
            case CO_AO:
                addCoAo();
                break;
            case CO_TAY:
                addCoTay();
                break;
            case DANG_AO:
                addDangAo();
                break;
            case HANG:
                addHang();
                break;
            case MAU_SAC:
                addMauSac();
                break;
            case SIZE:
                addSize();
                break;
            default:
                break;
        }
    }

    private void deleteChatLieu() {
        int row = tblThuocTinh.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một chất liệu để xóa");
            return;
        }
        ChatLieu chatLieu = chatLieuService.getListChatLieu().get(row);
        if (chatLieuService.delete(chatLieu.getId())) {
            JOptionPane.showMessageDialog(this, "Xóa chất liệu thành công");
            loadChatLieu(chatLieuService.getListChatLieu());
        } else {
            JOptionPane.showMessageDialog(this, "Xóa chất liệu thất bại");
        }
    }

    private void deleteCoAo() {
        int row = tblThuocTinh.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một cổ áo để xóa");
            return;
        }
        CoAo coAo = coAoService.getListCoAo().get(row);
        if (coAoService.delete(coAo.getId())) {
            JOptionPane.showMessageDialog(this, "Xóa cổ áo thành công");
            loadCoAo(coAoService.getListCoAo());
        } else {
            JOptionPane.showMessageDialog(this, "Xóa cổ áo thất bại");
        }
    }

    private void deleteCoTay() {
        int row = tblThuocTinh.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một cổ tay để xóa");
            return;
        }
        CoTay coTay = coTayService.getListCoTay().get(row);
        if (coTayService.delete(coTay.getId())) {
            JOptionPane.showMessageDialog(this, "Xóa cổ tay thành công");
            loadCoTay(coTayService.getListCoTay());
        } else {
            JOptionPane.showMessageDialog(this, "Xóa cổ tay thất bại");
        }
    }

    private void deleteDangAo() {
        int row = tblThuocTinh.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dáng áo để xóa");
            return;
        }
        ArrayList<DangAo> listDangAo = dangAoService.getListDangAo();

        DangAo dangAoToDelete = listDangAo.get(row);
        int comfirmResult = JOptionPane.showConfirmDialog(this,
                "Bạn chắc chắn muốn xóa dáng áo có ID: " + dangAoToDelete.getId(),
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
        if (comfirmResult == JOptionPane.YES_OPTION) {
            listDangAo.remove(dangAoToDelete);

            if (dangAoService.delete(dangAo.getId())) {
                JOptionPane.showMessageDialog(this, "Xóa dáng áo thành công");
                loadDangAo(dangAoService.getListDangAo());
            } else {
                JOptionPane.showMessageDialog(this, "Xóa dáng áo thất bại");
            }
        }
    }

    private void deleteHang() {
        int row = tblThuocTinh.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hãng để xóa");
            return;
        }
        Hang hang = hangService.getListHang().get(row);
        if (hangService.delete(hang.getId())) {
            JOptionPane.showMessageDialog(this, "Xóa hãng thành công");
            loadHang(hangService.getListHang());
        } else {
            JOptionPane.showMessageDialog(this, "Xóa hãng thất bại");
        }
    }

    private void deleteMauSac() {
        int row = tblThuocTinh.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một màu sắc để xóa");
            return;
        }
        MauSac mauSac = mauSacService.getListMauSac().get(row);
        if (mauSacService.delete(mauSac.getId())) {
            JOptionPane.showMessageDialog(this, "Xóa màu sắc thành công");
            loadMauSac(mauSacService.getListMauSac());
        } else {
            JOptionPane.showMessageDialog(this, "Xóa màu sắc thất bại");
        }
    }

    private void deleteSize() {
        int row = tblThuocTinh.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một size để xóa");
            return;
        }
        Size size = sizeService.getListSize().get(row);
        if (sizeService.delete(size.getId())) {
            JOptionPane.showMessageDialog(this, "Xóa size thành công");
            loadSize(sizeService.getListSize());
        } else {
            JOptionPane.showMessageDialog(this, "Xóa size thất bại");
        }
    }

    private void deleteThuocTinh(ThuocTinhType thuocTinhType) {
        String ma = txtMa.getText();
        String ten = txtTen.getText();

        switch (thuocTinhType) {
            case CHAT_LIEU:
                deleteChatLieu();
                break;
            case CO_AO:
                deleteCoAo();
                break;
            case CO_TAY:
                deleteCoTay();
                break;
            case DANG_AO:
                deleteDangAo();
                break;
            case HANG:
                deleteHang();
                break;
            case MAU_SAC:
                deleteMauSac();
                break;
            case SIZE:
                deleteSize();
                break;
            default:
                break;
        }
    }

    private void updateChatLieu() {
        int row = tblThuocTinh.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một chất liệu để cập nhật");
            return;
        }
        ChatLieu chatLieu = chatLieuService.getListChatLieu().get(row);
        ChatLieu cl = new ChatLieu();
        cl.setMa(txtMa.getText());
        cl.setTen(txtTen.getText());

        if (chatLieuService.update(chatLieu.getId(), cl)) {
            JOptionPane.showMessageDialog(this, "Cập nhật chất liệu thành công");
            loadChatLieu(chatLieuService.getListChatLieu());
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật chất liệu thất bại");
        }
    }

    private void updateCoAo() {
        int row = tblThuocTinh.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một cổ áo để cập nhật");
            return;
        }
        CoAo coAo = coAoService.getListCoAo().get(row);
        CoAo cl = new CoAo();
        cl.setMa(txtMa.getText());
        cl.setTen(txtTen.getText());

        if (coAoService.update(coAo.getId(), cl)) {
            JOptionPane.showMessageDialog(this, "Cập nhật cổ áo thành công");
            loadCoAo(coAoService.getListCoAo());
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật cổ áo thất bại");
        }
    }

    private void updateCoTay() {
        int row = tblThuocTinh.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một cổ tay để cập nhật");
            return;
        }
        CoTay coTay = coTayService.getListCoTay().get(row);
        CoTay cl = new CoTay();
        cl.setMa(txtMa.getText());
        cl.setTen(txtTen.getText());

        if (coTayService.update(coTay.getId(), cl)) {
            JOptionPane.showMessageDialog(this, "Cập nhật cổ tay thành công");
            loadCoTay(coTayService.getListCoTay());
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật cổ tay thất bại");
        }
    }

    private void updateDangAo() {
        int row = tblThuocTinh.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dáng áo để cập nhật");
            return;
        }
        DangAo dangAo = dangAoService.getListDangAo().get(row);
        DangAo cl = new DangAo();
        cl.setMa(txtMa.getText());
        cl.setTen(txtTen.getText());

        if (dangAoService.update(dangAo.getId(), cl)) {
            JOptionPane.showMessageDialog(this, "Cập nhật dáng áo thành công");
            loadDangAo(dangAoService.getListDangAo());
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật dáng áo thất bại");
        }
    }

    private void updateHang() {
        int row = tblThuocTinh.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hãng để cập nhật");
            return;
        }
        Hang hang = hangService.getListHang().get(row);
        Hang cl = new Hang();
        cl.setMa(txtMa.getText());
        cl.setTen(txtTen.getText());

        if (hangService.update(hang.getId(), cl)) {
            JOptionPane.showMessageDialog(this, "Cập nhật hãng thành công");
            loadHang(hangService.getListHang());
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật hãng thất bại");
        }
    }

    private void updateMauSac() {
        int row = tblThuocTinh.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một màu sắc để cập nhật");
            return;
        }
        MauSac mauSac = mauSacService.getListMauSac().get(row);
        MauSac cl = new MauSac();
        cl.setMa(txtMa.getText());
        cl.setTen(txtTen.getText());

        if (mauSacService.update(mauSac.getId(), cl)) {
            JOptionPane.showMessageDialog(this, "Cập nhật màu sắc thành công");
            loadMauSac(mauSacService.getListMauSac());
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật màu sắc thất bại");
        }
    }

    private void updateSize() {
        int row = tblThuocTinh.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một size để cập nhật");
            return;
        }
        Size size = sizeService.getListSize().get(row);
        Size cl = new Size();
        cl.setMa(txtMa.getText());
        cl.setTen(txtTen.getText());

        if (sizeService.update(size.getId(), cl)) {
            JOptionPane.showMessageDialog(this, "Cập nhật size thành công");
            loadSize(sizeService.getListSize());
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật size thất bại");
        }
    }

    private void updateThuocTinh(ThuocTinhType thuocTinhType) {
        switch (thuocTinhType) {
            case CHAT_LIEU:
                updateChatLieu();
                break;
            case CO_AO:
                updateCoAo();
                break;
            case CO_TAY:
                updateCoTay();
                break;
            case DANG_AO:
                updateDangAo();
                break;
            case HANG:
                updateHang();
                break;
            case MAU_SAC:
                updateMauSac();
                break;
            case SIZE:
                updateSize();
                break;
            default:
                break;
        }
    }

    private void clearForm() {
        txtGia.setText("");
        txtSoLuong.setText("");
        addCboChatLieu();
        addCboCoAo();
        addCboCoTay();
        addCboDangAo();
        addCboHang();
        addCboMauSac();
        addCboSanPham();
        addCboSize();
    }

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup2 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        sanPhampnl = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        txtMaSP = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        btnAddSP = new javax.swing.JButton();
        btnUpdateSanPham = new javax.swing.JButton();
        btnRefreshSP = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtSearchSP = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        chiTietpnl = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnExcel = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cboTenSP = new javax.swing.JComboBox<>();
        txtSoLuong = new javax.swing.JTextField();
        txtGia = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cboHang = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cboDangAo = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cboCoTay = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cboCoAo = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        cboSize = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        cboChatLieu = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        cboMauSac = new javax.swing.JComboBox<>();
        btnHang = new javax.swing.JButton();
        btnDangAo = new javax.swing.JButton();
        btnCoTay = new javax.swing.JButton();
        btnCoAo = new javax.swing.JButton();
        btnChatLieu = new javax.swing.JButton();
        btnSize = new javax.swing.JButton();
        btnMauSac = new javax.swing.JButton();
        btnSanPham = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblChiTiet = new javax.swing.JTable();
        cboFilterSanPham = new javax.swing.JComboBox<>();
        cboFilterHang = new javax.swing.JComboBox<>();
        cboFilterChatLieu = new javax.swing.JComboBox<>();
        cboFilterDangAo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cboFilterSize = new javax.swing.JComboBox<>();
        cboFilterCoAo = new javax.swing.JComboBox<>();
        cboFilterMauSac = new javax.swing.JComboBox<>();
        cboFilterCoTay = new javax.swing.JComboBox<>();
        cboFilterTrangThai = new javax.swing.JComboBox<>();
        thuocTinhpnl = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        txtTen = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        btnAddThuocTinh = new javax.swing.JButton();
        btnUpdateThuocTinh = new javax.swing.JButton();
        btnRefreshThuocTinh = new javax.swing.JButton();
        btnRefreshThuocTinh1 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        rdoHang = new javax.swing.JRadioButton();
        rdoChatLieu = new javax.swing.JRadioButton();
        rdoMauSac = new javax.swing.JRadioButton();
        rdoSize = new javax.swing.JRadioButton();
        rdoCoAo = new javax.swing.JRadioButton();
        rdoCoTay = new javax.swing.JRadioButton();
        rdoDangAo = new javax.swing.JRadioButton();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblThuocTinh = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        setPreferredSize(new java.awt.Dimension(2046, 600));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(915, 600));

        sanPhampnl.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 14), new java.awt.Color(255, 0, 51))); // NOI18N

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Mã sản phẩm");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Tên sản phẩm");

        txtTenSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenSPActionPerformed(evt);
            }
        });

        txtMaSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaSP.setEnabled(false);
        txtMaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(66, 66, 66)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnAddSP.setBackground(new java.awt.Color(0, 204, 255));
        btnAddSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAddSP.setText("Thêm");
        btnAddSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSPActionPerformed(evt);
            }
        });

        btnUpdateSanPham.setBackground(new java.awt.Color(0, 204, 255));
        btnUpdateSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnUpdateSanPham.setText("Sửa");
        btnUpdateSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateSanPhamActionPerformed(evt);
            }
        });

        btnRefreshSP.setBackground(new java.awt.Color(0, 204, 255));
        btnRefreshSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnRefreshSP.setText("Làm mới");
        btnRefreshSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAddSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdateSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRefreshSP, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUpdateSanPham)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefreshSP)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 14), new java.awt.Color(255, 0, 51))); // NOI18N

        txtSearchSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSearchSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchSPActionPerformed(evt);
            }
        });
        txtSearchSP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchSPKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Tìm kiếm tên sản phẩm");

        tblSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchSP)
                        .addGap(337, 337, 337))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtSearchSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout sanPhampnlLayout = new javax.swing.GroupLayout(sanPhampnl);
        sanPhampnl.setLayout(sanPhampnlLayout);
        sanPhampnlLayout.setHorizontalGroup(
            sanPhampnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sanPhampnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sanPhampnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        sanPhampnlLayout.setVerticalGroup(
            sanPhampnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sanPhampnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sản Phẩm", sanPhampnl);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 14), new java.awt.Color(255, 0, 51))); // NOI18N

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnAdd.setBackground(new java.awt.Color(0, 204, 255));
        btnAdd.setText("Thêm sản phẩm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(0, 204, 255));
        btnUpdate.setText("Sửa sản phẩm");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnRefresh.setBackground(new java.awt.Color(0, 204, 255));
        btnRefresh.setText("Làm mới");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnExcel.setBackground(new java.awt.Color(0, 204, 255));
        btnExcel.setText("Xuất File Excel");
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExcel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btnAdd)
                .addGap(18, 18, 18)
                .addComponent(btnUpdate)
                .addGap(18, 18, 18)
                .addComponent(btnRefresh)
                .addGap(18, 18, 18)
                .addComponent(btnExcel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel4.setText("Tên sản phẩm");

        jLabel5.setText("Số lượng tồn");

        jLabel6.setText("Giá ");

        jLabel9.setText("Hãng");

        jLabel10.setText("Dáng áo");

        jLabel11.setText("Cổ tay");

        jLabel12.setText("Cổ áo");

        jLabel13.setText("Size");

        jLabel14.setText("Chất liệu");

        jLabel15.setText("Màu sắc");

        btnHang.setBackground(new java.awt.Color(102, 255, 255));
        btnHang.setFont(new java.awt.Font("Open Sans SemiBold", 1, 12)); // NOI18N
        btnHang.setText("+");
        btnHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHangActionPerformed(evt);
            }
        });

        btnDangAo.setBackground(new java.awt.Color(102, 255, 255));
        btnDangAo.setFont(new java.awt.Font("Open Sans SemiBold", 1, 12)); // NOI18N
        btnDangAo.setText("+");
        btnDangAo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangAoActionPerformed(evt);
            }
        });

        btnCoTay.setBackground(new java.awt.Color(102, 255, 255));
        btnCoTay.setFont(new java.awt.Font("Open Sans SemiBold", 1, 12)); // NOI18N
        btnCoTay.setText("+");
        btnCoTay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCoTayActionPerformed(evt);
            }
        });

        btnCoAo.setBackground(new java.awt.Color(102, 255, 255));
        btnCoAo.setFont(new java.awt.Font("Open Sans SemiBold", 1, 12)); // NOI18N
        btnCoAo.setText("+");
        btnCoAo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCoAoActionPerformed(evt);
            }
        });

        btnChatLieu.setBackground(new java.awt.Color(102, 255, 255));
        btnChatLieu.setFont(new java.awt.Font("Open Sans SemiBold", 1, 12)); // NOI18N
        btnChatLieu.setText("+");
        btnChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChatLieuActionPerformed(evt);
            }
        });

        btnSize.setBackground(new java.awt.Color(102, 255, 255));
        btnSize.setFont(new java.awt.Font("Open Sans SemiBold", 1, 12)); // NOI18N
        btnSize.setText("+");
        btnSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSizeActionPerformed(evt);
            }
        });

        btnMauSac.setBackground(new java.awt.Color(102, 255, 255));
        btnMauSac.setFont(new java.awt.Font("Open Sans SemiBold", 1, 12)); // NOI18N
        btnMauSac.setText("+");
        btnMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMauSacActionPerformed(evt);
            }
        });

        btnSanPham.setBackground(new java.awt.Color(102, 255, 255));
        btnSanPham.setFont(new java.awt.Font("Open Sans SemiBold", 1, 12)); // NOI18N
        btnSanPham.setText("+");
        btnSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSanPhamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMauSac))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSoLuong))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtGia)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSanPham))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSize)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cboHang, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cboDangAo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cboCoTay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(cboCoAo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cboChatLieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnHang)
                            .addComponent(btnDangAo)
                            .addComponent(btnCoTay, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addComponent(btnCoAo, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(btnChatLieu, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cboTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(cboHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(cboDangAo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDangAo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(cboCoTay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCoTay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(cboCoAo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCoAo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMauSac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(cboChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnChatLieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setToolTipText("");

        tblChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã CTSP", "Tên sản phẩm", "Hãng", "Màu sắc", "Chất liệu", "Size", "Dáng áo", "Cổ áo", "Cổ tay", "Giá", "Số lượng tồn", "Trạng thái"
            }
        ));
        tblChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiTietMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblChiTiet);

        cboFilterSanPham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sản phẩm" }));
        cboFilterSanPham.setSelectedItem(0);
        cboFilterSanPham.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboFilterSanPhamItemStateChanged(evt);
            }
        });

        cboFilterHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hãng" }));
        cboFilterHang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboFilterHangItemStateChanged(evt);
            }
        });

        cboFilterChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chất liệu" }));
        cboFilterChatLieu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboFilterChatLieuItemStateChanged(evt);
            }
        });
        cboFilterChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFilterChatLieuActionPerformed(evt);
            }
        });

        cboFilterDangAo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dáng Áo" }));
        cboFilterDangAo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboFilterDangAoItemStateChanged(evt);
            }
        });
        cboFilterDangAo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFilterDangAoActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("Danh sách sản phẩm");

        cboFilterSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Size" }));
        cboFilterSize.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboFilterSizeItemStateChanged(evt);
            }
        });

        cboFilterCoAo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cổ áo" }));
        cboFilterCoAo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboFilterCoAoItemStateChanged(evt);
            }
        });

        cboFilterMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Màu sắc" }));
        cboFilterMauSac.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboFilterMauSacItemStateChanged(evt);
            }
        });

        cboFilterCoTay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cổ tay" }));
        cboFilterCoTay.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboFilterCoTayItemStateChanged(evt);
            }
        });

        cboFilterTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Trạng thái", "Còn hàng", "Hết hàng" }));
        cboFilterTrangThai.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboFilterTrangThaiItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(cboFilterSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboFilterHang, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboFilterChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboFilterDangAo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboFilterSize, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboFilterCoAo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboFilterMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboFilterCoTay, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboFilterSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFilterHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFilterChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFilterDangAo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFilterSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFilterCoAo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFilterMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFilterCoTay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout chiTietpnlLayout = new javax.swing.GroupLayout(chiTietpnl);
        chiTietpnl.setLayout(chiTietpnlLayout);
        chiTietpnlLayout.setHorizontalGroup(
            chiTietpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chiTietpnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chiTietpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        chiTietpnlLayout.setVerticalGroup(
            chiTietpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chiTietpnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Chi tiết sản phẩm", chiTietpnl);

        thuocTinhpnl.setPreferredSize(new java.awt.Dimension(915, 600));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thiết lập thuộc tính", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 14), new java.awt.Color(255, 0, 51))); // NOI18N

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Mã thuộc tính");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("Tên thuộc tính");

        txtMa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMa.setEnabled(false);
        txtMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaActionPerformed(evt);
            }
        });

        txtTen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMa)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnAddThuocTinh.setBackground(new java.awt.Color(0, 204, 255));
        btnAddThuocTinh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAddThuocTinh.setText("Thêm");
        btnAddThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddThuocTinhActionPerformed(evt);
            }
        });

        btnUpdateThuocTinh.setBackground(new java.awt.Color(0, 204, 255));
        btnUpdateThuocTinh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnUpdateThuocTinh.setText("Sửa");
        btnUpdateThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateThuocTinhActionPerformed(evt);
            }
        });

        btnRefreshThuocTinh.setBackground(new java.awt.Color(0, 204, 255));
        btnRefreshThuocTinh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnRefreshThuocTinh.setText("Xóa");
        btnRefreshThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshThuocTinhActionPerformed(evt);
            }
        });

        btnRefreshThuocTinh1.setBackground(new java.awt.Color(0, 204, 255));
        btnRefreshThuocTinh1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnRefreshThuocTinh1.setText("Làm mới");
        btnRefreshThuocTinh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshThuocTinh1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRefreshThuocTinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddThuocTinh, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnUpdateThuocTinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRefreshThuocTinh1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddThuocTinh)
                    .addComponent(btnUpdateThuocTinh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRefreshThuocTinh)
                    .addComponent(btnRefreshThuocTinh1))
                .addGap(26, 26, 26))
        );

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        buttonGroup2.add(rdoHang);
        rdoHang.setText("Hãng");
        rdoHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoHangActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoChatLieu);
        rdoChatLieu.setText("Chất liệu");
        rdoChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoChatLieuActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoMauSac);
        rdoMauSac.setText("Màu sắc");
        rdoMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoMauSacActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoSize);
        rdoSize.setText("Size");
        rdoSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoSizeActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoCoAo);
        rdoCoAo.setText("Cổ áo");
        rdoCoAo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoCoAoActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoCoTay);
        rdoCoTay.setText("Cổ tay");
        rdoCoTay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoCoTayActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoDangAo);
        rdoDangAo.setText("Dáng áo");
        rdoDangAo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDangAoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdoHang, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdoDangAo, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoCoTay, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoCoAo, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(rdoSize, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel17Layout.createSequentialGroup()
                        .addComponent(rdoDangAo)
                        .addGap(11, 11, 11)
                        .addComponent(rdoCoTay)
                        .addGap(18, 18, 18)
                        .addComponent(rdoCoAo))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel17Layout.createSequentialGroup()
                        .addComponent(rdoHang)
                        .addGap(11, 11, 11)
                        .addComponent(rdoChatLieu)
                        .addGap(18, 18, 18)
                        .addComponent(rdoMauSac)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(rdoSize)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách thuộc tính", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 14), new java.awt.Color(255, 0, 51))); // NOI18N

        tblThuocTinh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblThuocTinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã thuộc tính", "Tên thuộc tính"
            }
        ));
        tblThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThuocTinhMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblThuocTinh);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout thuocTinhpnlLayout = new javax.swing.GroupLayout(thuocTinhpnl);
        thuocTinhpnl.setLayout(thuocTinhpnlLayout);
        thuocTinhpnlLayout.setHorizontalGroup(
            thuocTinhpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thuocTinhpnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(thuocTinhpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        thuocTinhpnlLayout.setVerticalGroup(
            thuocTinhpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thuocTinhpnlLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Thuộc tính", thuocTinhpnl);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTenSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenSPActionPerformed

    private void btnAddSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSPActionPerformed
        // Lấy danh sách mã hiện có (để kiểm tra trùng lặp)
        ArrayList<String> existingMaList = new ArrayList<>();
        for (SanPham existingSanPham : sanPhamService.getListSanPham()) {
            existingMaList.add(existingSanPham.getMa());
        }

        // Sinh mã mới
        String newMa;
        int suffix = 1;
        do {
            newMa = "SP" + String.format("%03d", suffix); // Ví dụ: SP001
            suffix++;
        } while (existingMaList.contains(newMa));

        String ten = txtTenSP.getText();

        // Hiển thị hộp thoại xác nhận
        int confirmResult = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn thêm sản phẩm?",
                "Xác nhận thêm mới",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmResult == JOptionPane.YES_OPTION) {
            SanPham sp = new SanPham();
            sp.setMa(newMa);
            sp.setTen(ten);

            if (sanPhamService.add(sp)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                loadSanPham(sanPhamService.getListSanPham());
                addCboSanPham();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        } else {
            // Người dùng không xác nhận thêm mới
            JOptionPane.showMessageDialog(this, "Hủy thêm mới");
        }

    }//GEN-LAST:event_btnAddSPActionPerformed

    private void txtSearchSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchSPActionPerformed

    private void txtMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaActionPerformed

    private void txtTenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenActionPerformed

    private void btnAddThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddThuocTinhActionPerformed
        if (!validateFormData()) {
            return;
        }
        if (rdoChatLieu.isSelected()) {
            if (showConfirmDialog("Bạn có chắc chắn muốn thêm chất liệu không?")) {
                addChatLieu();
                addCboChatLieu();
                clearThuocTinh();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy thêm mới");
            }
        } else if (rdoCoAo.isSelected()) {
            if (showConfirmDialog("Bạn có chắc chắn muốn thêm cổ áo không?")) {
                addCoAo();
                addCoAo();
                clearThuocTinh();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy thêm mới");
            }
        } else if (rdoCoTay.isSelected()) {
            if (showConfirmDialog("Bạn có chắc chắn muốn thêm cổ tay không?")) {
                addCoTay();
                addCboCoTay();
                clearThuocTinh();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy thêm mới");
            }
        } else if (rdoDangAo.isSelected()) {
            if (showConfirmDialog("Bạn có chắc chắn muốn thêm dáng áo không?")) {
                addDangAo();
                addCboDangAo();
                clearThuocTinh();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy thêm mới");
            }
        } else if (rdoHang.isSelected()) {
            if (showConfirmDialog("Bạn có chắc chắn muốn thêm hãng không?")) {
                addHang();
                addCboHang();
                clearThuocTinh();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy thêm mới");
            }
        } else if (rdoMauSac.isSelected()) {
            if (showConfirmDialog("Bạn có chắc chắn muốn thêm màu sắc không?")) {
                addMauSac();
                addCboFilterMauSac();
                clearThuocTinh();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy thêm mới");
            }
        } else if (rdoSize.isSelected()) {
            if (showConfirmDialog("Bạn có chắc chắn muốn thêm kích thước không?")) {
                addSize();
                addCboSize();
                clearThuocTinh();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy thêm mới");
            }
        }

    }//GEN-LAST:event_btnAddThuocTinhActionPerformed

    private void txtMaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaSPActionPerformed

    private void rdoDangAoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDangAoActionPerformed
        txtMa.setText("");
        txtTen.setText("");
        loadThuocTinh(ThuocTinhType.DANG_AO);
    }//GEN-LAST:event_rdoDangAoActionPerformed

    private void rdoSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoSizeActionPerformed
        txtMa.setText("");
        txtTen.setText("");
        loadThuocTinh(ThuocTinhType.SIZE);
    }//GEN-LAST:event_rdoSizeActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        ChiTietSanPham formData = getFormData();

        if (formData != null) {
            int confirmResult = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thêm sản phẩm không?", "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (confirmResult == JOptionPane.YES_OPTION) {
                if (chiTietService.add(formData)) {
                    JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công");
                    loadDataCTSP(chiTietService.getListChiTiet());
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại");
                }
            }
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void tblChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietMouseClicked
        int index = tblChiTiet.getSelectedRow();
        cboTenSP.setSelectedItem(tblChiTiet.getModel().getValueAt(index, 2));
        cboChatLieu.setSelectedItem(tblChiTiet.getModel().getValueAt(index, 5));
        cboCoAo.setSelectedItem(tblChiTiet.getModel().getValueAt(index, 8));
        cboCoTay.setSelectedItem(tblChiTiet.getModel().getValueAt(index, 9));
        cboDangAo.setSelectedItem(tblChiTiet.getModel().getValueAt(index, 7));
        cboHang.setSelectedItem(tblChiTiet.getModel().getValueAt(index, 3));
        cboMauSac.setSelectedItem(tblChiTiet.getModel().getValueAt(index, 4));
        cboSize.setSelectedItem(tblChiTiet.getModel().getValueAt(index, 6));
        txtGia.setText(tblChiTiet.getValueAt(index, 10).toString());
        txtSoLuong.setText(tblChiTiet.getValueAt(index, 11).toString());
    }//GEN-LAST:event_tblChiTietMouseClicked

    private void cboFilterSanPhamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboFilterSanPhamItemStateChanged
        String selectedItem = (String) cboFilterSanPham.getSelectedItem();
        if (selectedItem.equals("Sản phẩm")) {
            loadDataCTSP(chiTietService.getListChiTiet());
        } else if (selectedItem != null && !selectedItem.isEmpty()) {
            ArrayList<ChiTietSanPham> filteredList = chiTietService.getListChiTietByTen(selectedItem);
            loadDataCTSP(filteredList);
        }
    }//GEN-LAST:event_cboFilterSanPhamItemStateChanged

    private void btnSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSizeActionPerformed
        SizeView sizeView = new SizeView(); // khai báo 1 bảng mới
        sizeView.setVisible(true);
    }//GEN-LAST:event_btnSizeActionPerformed

    private void cboFilterHangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboFilterHangItemStateChanged
        String selectedItem = (String) cboFilterHang.getSelectedItem();
        if (selectedItem.equals("Hãng")) {
            loadDataCTSP(chiTietService.getListChiTiet());
        } else if (selectedItem != null && !selectedItem.isEmpty()) {
            ArrayList<ChiTietSanPham> filteredList = chiTietService.getListChiTietByHang(selectedItem);
            loadDataCTSP(filteredList);
        }
    }//GEN-LAST:event_cboFilterHangItemStateChanged

    private void cboFilterChatLieuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboFilterChatLieuItemStateChanged
        String selectedItem = (String) cboFilterChatLieu.getSelectedItem();
        if (selectedItem.equals("Chất liệu")) {
            loadDataCTSP(chiTietService.getListChiTiet());
        } else if (selectedItem != null && !selectedItem.isEmpty()) {
            ArrayList<ChiTietSanPham> filteredList = chiTietService.getListChiTietByChatLieu(selectedItem);
            loadDataCTSP(filteredList);
        }
    }//GEN-LAST:event_cboFilterChatLieuItemStateChanged

    private void cboFilterDangAoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboFilterDangAoItemStateChanged
        String selectedItem = (String) cboFilterDangAo.getSelectedItem();
        if (selectedItem.equals("Dáng áo")) {
            loadDataCTSP(chiTietService.getListChiTiet());
        } else if (selectedItem != null && !selectedItem.isEmpty()) {
            ArrayList<ChiTietSanPham> filteredList = chiTietService.getListChiTietByDangAo(selectedItem);
            loadDataCTSP(filteredList);
        }
    }//GEN-LAST:event_cboFilterDangAoItemStateChanged

    private void btnUpdateSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateSanPhamActionPerformed
        int row = tblSanPham.getSelectedRow();

        if (row != -1) {
            SanPham selectedSanPham = sanPhamService.getListSanPham().get(row);
            String tenSP = txtTenSP.getText();
            String maSP = txtMaSP.getText();
            SanPham sp = new SanPham();
            sp.setId(selectedSanPham.getId());  // Truyền ID của sản phẩm cần cập nhật
            sp.setMa(maSP);
            sp.setTen(tenSP);

            // Hiển thị hộp thoại xác nhận
            int confirmResult = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn cập nhật sản phẩm (id: " + sp.getId() + ")?",
                    "Xác nhận cập nhật",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmResult == JOptionPane.YES_OPTION) {
                if (sanPhamService.update(sp)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công (id: " + sp.getId() + ")");
                    loadSanPham(sanPhamService.getListSanPham());
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thất bại");
                }
            } else {
                // Người dùng không xác nhận cập nhật
                JOptionPane.showMessageDialog(this, "Hủy cập nhật");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để cập nhật");
        }

    }//GEN-LAST:event_btnUpdateSanPhamActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        txtMaSP.disable();
        int index = tblSanPham.getSelectedRow();
        txtMaSP.setText(tblSanPham.getValueAt(index, 1).toString());
        txtTenSP.setText(tblSanPham.getValueAt(index, 2).toString());

        int row = tblSanPham.getSelectedRow();
        if (row != -1) {
            String tenSP = tblSanPham.getValueAt(index, 2).toString();
            SanPhamDetail sanPhamDetail = new SanPhamDetail();
            sanPhamDetail.setTenSP(tenSP);
            sanPhamDetail.setVisible(true);
            sanPhamDetail.pack();
            sanPhamDetail.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnRefreshSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshSPActionPerformed
        txtMaSP.enable(true);
        txtMaSP.setText("");
        txtTenSP.setText("");
        loadSanPham(sanPhamService.getListSanPham());
    }//GEN-LAST:event_btnRefreshSPActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int row = tblChiTiet.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để cập nhật");
            return;
        }

        ChiTietSanPham chiTiet = chiTietService.getListChiTiet().get(row);
        ChiTietSanPham formData = getFormData();
        formData.setId(chiTiet.getId());

// Hiển thị hộp thoại xác nhận
        int confirmResult = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn cập nhật sản phẩm không?",
                "Xác nhận cập nhật",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmResult == JOptionPane.YES_OPTION) {
            // Người dùng đã xác nhận cập nhật
            if (chiTietService.update(chiTiet.getId(), formData)) {
                JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công");
                loadDataCTSP(chiTietService.getListChiTiet());

            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thất bại");
            }
        } else {
            // Người dùng không xác nhận cập nhật
            JOptionPane.showMessageDialog(this, "Hủy cập nhật");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        txtSoLuong.setText("");
        txtGia.setText("");
        loadDataCTSP(chiTietService.getListChiTiet());
        cboFilterSanPham.setSelectedItem("Sản phẩm");
        cboFilterChatLieu.setSelectedItem("Chất liệu");
        cboFilterCoAo.setSelectedItem("Cổ áo");
        cboFilterCoTay.setSelectedItem("Cổ tay");
        cboFilterDangAo.setSelectedItem("Dáng áo");
        cboFilterHang.setSelectedItem("Hãng");
        cboFilterMauSac.setSelectedItem("Màu sắc");
        cboFilterSize.setSelectedItem("Size");
        cboFilterTrangThai.setSelectedItem("Trạng thái");
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void rdoHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoHangActionPerformed
        txtMa.setText("");
        txtTen.setText("");
        loadThuocTinh(ThuocTinhType.HANG);
    }//GEN-LAST:event_rdoHangActionPerformed

    private void rdoChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoChatLieuActionPerformed
        txtMa.setText("");
        txtTen.setText("");
        loadThuocTinh(ThuocTinhType.CHAT_LIEU);
    }//GEN-LAST:event_rdoChatLieuActionPerformed

    private void rdoCoTayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoCoTayActionPerformed
        txtMa.setText("");
        txtTen.setText("");
        loadThuocTinh(ThuocTinhType.CO_TAY);
    }//GEN-LAST:event_rdoCoTayActionPerformed

    private void rdoMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoMauSacActionPerformed
        txtMa.setText("");
        txtTen.setText("");
        loadThuocTinh(ThuocTinhType.MAU_SAC);
    }//GEN-LAST:event_rdoMauSacActionPerformed

    private void rdoCoAoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoCoAoActionPerformed
        txtMa.setText("");
        txtTen.setText("");
        loadThuocTinh(ThuocTinhType.CO_AO);
    }//GEN-LAST:event_rdoCoAoActionPerformed

    private void btnRefreshThuocTinh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshThuocTinh1ActionPerformed
        rdoChatLieu.setSelected(false);
        rdoCoAo.setSelected(false);
        rdoCoTay.setSelected(false);
        rdoDangAo.setSelected(false);
        rdoHang.setSelected(true);
        rdoMauSac.setSelected(false);
        rdoSize.setSelected(false);
        txtMa.setText("");
        txtTen.setText("");
        loadDangAo(dangAoService.getListDangAo());
    }//GEN-LAST:event_btnRefreshThuocTinh1ActionPerformed

    private void tblThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThuocTinhMouseClicked
        int index = tblThuocTinh.getSelectedRow();
        txtMa.setText(tblThuocTinh.getValueAt(index, 1).toString());
        txtTen.setText(tblThuocTinh.getValueAt(index, 2).toString());
    }//GEN-LAST:event_tblThuocTinhMouseClicked

    private void txtSearchSPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchSPKeyReleased
        listSanPhams.clear();
        String strFind = "";
        strFind = txtSearchSP.getText();
        ArrayList<SanPham> listSearch = sanPhamService.searchByName(strFind);
        loadSanPham(listSearch);

    }//GEN-LAST:event_txtSearchSPKeyReleased

    private void cboFilterChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFilterChatLieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboFilterChatLieuActionPerformed

    private void btnSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSanPhamActionPerformed
        SanPhamView sanPhamView = new SanPhamView();
        sanPhamView.setVisible(true);
        sanPhamView.pack();
        sanPhamView.setLocationRelativeTo(null);
        sanPhamView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_btnSanPhamActionPerformed

    private void btnChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChatLieuActionPerformed
        ChatLieuView j = new ChatLieuView();
        j.setVisible(true);
    }//GEN-LAST:event_btnChatLieuActionPerformed

    private void btnUpdateThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateThuocTinhActionPerformed

        if (rdoChatLieu.isSelected()) {

            if (showConfirmDialog("Bạn có chắc chắn muốn cập nhật chất liệu không?")) {
                updateChatLieu();
                clearThuocTinh();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy cập nhật");
            }
        } else if (rdoCoAo.isSelected()) {
            if (showConfirmDialog("Bạn có chắc chắn muốn cập nhật cổ áo không?")) {
                updateCoAo();
                clearThuocTinh();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy cập nhật");
            }
        } else if (rdoCoTay.isSelected()) {
            if (showConfirmDialog("Bạn có chắc chắn muốn cập nhật cổ tay không?")) {
                updateCoTay();
                clearThuocTinh();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy cập nhật");
            }
        } else if (rdoDangAo.isSelected()) {
            if (showConfirmDialog("Bạn có chắc chắn muốn cập nhật dáng áo không?")) {
                updateDangAo();
                clearThuocTinh();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy cập nhật");
            }
        } else if (rdoHang.isSelected()) {
            if (showConfirmDialog("Bạn có chắc chắn muốn cập nhật hãng không?")) {
                updateHang();
                clearThuocTinh();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy cập nhật");
            }
        } else if (rdoMauSac.isSelected()) {
            if (showConfirmDialog("Bạn có chắc chắn muốn cập nhật màu sắc không?")) {
                updateMauSac();
                clearThuocTinh();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy cập nhật");
            }
        } else if (rdoSize.isSelected()) {
            if (showConfirmDialog("Bạn có chắc chắn muốn cập nhật kích thước không?")) {
                updateSize();
                clearThuocTinh();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy cập nhật");
            }
        }

    }//GEN-LAST:event_btnUpdateThuocTinhActionPerformed

    private void btnMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMauSacActionPerformed
        MauSacView mauSacView = new MauSacView();
        mauSacView.setVisible(true);
    }//GEN-LAST:event_btnMauSacActionPerformed

    private void btnHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHangActionPerformed
        HangView hangView = new HangView();
        hangView.setVisible(true);
    }//GEN-LAST:event_btnHangActionPerformed

    private void btnDangAoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangAoActionPerformed
        DangAoView dangAoView = new DangAoView();
        dangAoView.setVisible(true);
    }//GEN-LAST:event_btnDangAoActionPerformed

    private void btnCoTayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCoTayActionPerformed
        CoTayView coTayView = new CoTayView();
        coTayView.setVisible(true);
    }//GEN-LAST:event_btnCoTayActionPerformed

    private void btnCoAoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCoAoActionPerformed
        CoAoView coAoView = new CoAoView();
        coAoView.setVisible(true);
    }//GEN-LAST:event_btnCoAoActionPerformed

    private void tblSanPhamMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSanPhamMouseEntered

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed

        DefaultTableModel list = (DefaultTableModel) tblChiTiet.getModel();

        try ( XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Danh sách chi tiết sản phẩm");

            XSSFRow headerRow = sheet.createRow(4);

            String[] headers = {
                "STT", "Mã CTSP", "Tên sản phẩm", "Hãng", "Màu sắc",
                "Chất liệu", "Size", "Dáng áo", "Cổ áo", "Cổ tay",
                "Giá", "Số lượng", "Trạng thái"
            };

            for (int i = 0; i < headers.length; i++) {
                XSSFCell cell = headerRow.createCell(i, CellType.STRING);
                cell.setCellValue(headers[i]);
            }

            for (int i = 0; i < list.getRowCount(); i++) {
                XSSFRow row = sheet.createRow(5 + i);

                XSSFCell cell = row.createCell(0, CellType.NUMERIC);
                cell.setCellValue(i + 1);

                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(list.getValueAt(i, 1).toString());

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(list.getValueAt(i, 2).toString());

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(list.getValueAt(i, 3).toString());

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(list.getValueAt(i, 4).toString());

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(list.getValueAt(i, 5).toString());

                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(list.getValueAt(i, 6).toString());

                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(list.getValueAt(i, 7).toString());

                cell = row.createCell(8, CellType.STRING);
                cell.setCellValue(list.getValueAt(i, 8).toString());

                cell = row.createCell(9, CellType.STRING);
                cell.setCellValue(list.getValueAt(i, 9).toString());

                cell = row.createCell(10, CellType.NUMERIC);
                cell.setCellValue(list.getValueAt(i, 10).toString());

                cell = row.createCell(11, CellType.NUMERIC);
                cell.setCellValue(list.getValueAt(i, 11).toString());

                cell = row.createCell(12, CellType.STRING);
                cell.setCellValue(list.getValueAt(i, 12).toString());
            }

            JFileChooser excelFileChooser = new JFileChooser("C:\\Users\\FPTSHOP\\Desktop\\netbean-export-excel");
            excelFileChooser.setDialogTitle("Save As");
            FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
            excelFileChooser.setFileFilter(fnef);
            int excelChooser = excelFileChooser.showSaveDialog(null);

            if (excelChooser == JFileChooser.APPROVE_OPTION) {
                try ( FileOutputStream excelFOU = new FileOutputStream(excelFileChooser.getSelectedFile() + ".xlsx");  BufferedOutputStream excelBOU = new BufferedOutputStream(excelFOU)) {

                    workbook.write(excelBOU);
                    JOptionPane.showMessageDialog(null, "Exported Successfully !!!........");

                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error exporting to Excel");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnExcelActionPerformed

    private void cboFilterSizeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboFilterSizeItemStateChanged
        String selectedItem = (String) cboFilterSize.getSelectedItem();
        if (selectedItem.equals("Size")) {
            loadDataCTSP(chiTietService.getListChiTiet());
        } else if (selectedItem != null && !selectedItem.isEmpty()) {
            ArrayList<ChiTietSanPham> filteredList = chiTietService.getListChiTietBySize(selectedItem);
            loadDataCTSP(filteredList);
        }

    }//GEN-LAST:event_cboFilterSizeItemStateChanged

    private void cboFilterCoAoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboFilterCoAoItemStateChanged
        String selectedItem = (String) cboFilterCoAo.getSelectedItem();
        if (selectedItem.equals("Cổ áo")) {
            loadDataCTSP(chiTietService.getListChiTiet());
        } else if (selectedItem != null && !selectedItem.isEmpty()) {
            ArrayList<ChiTietSanPham> filteredList = chiTietService.getListChiTietByCoAo(selectedItem);
            loadDataCTSP(filteredList);
        }

    }//GEN-LAST:event_cboFilterCoAoItemStateChanged

    private void cboFilterMauSacItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboFilterMauSacItemStateChanged
        String selectedItem = (String) cboFilterMauSac.getSelectedItem();
        if (selectedItem.equals("Màu sắc")) {
            loadDataCTSP(chiTietService.getListChiTiet());
        } else if (selectedItem != null && !selectedItem.isEmpty()) {
            ArrayList<ChiTietSanPham> filteredList = chiTietService.getListChiTietByMauSac(selectedItem);
            loadDataCTSP(filteredList);
        }

    }//GEN-LAST:event_cboFilterMauSacItemStateChanged

    private void cboFilterCoTayItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboFilterCoTayItemStateChanged
        String selectedItem = (String) cboFilterCoTay.getSelectedItem();
        if (selectedItem.equals("Cổ tay")) {
            loadDataCTSP(chiTietService.getListChiTiet());
        } else if (selectedItem != null && !selectedItem.isEmpty()) {
            ArrayList<ChiTietSanPham> filteredList = chiTietService.getListChiTietByCoTay(selectedItem);
            loadDataCTSP(filteredList);
        }

    }//GEN-LAST:event_cboFilterCoTayItemStateChanged

    private void cboFilterTrangThaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboFilterTrangThaiItemStateChanged
        String selected = cboFilterTrangThai.getSelectedItem().toString();

        // Lấy tên cột cần lọc trong table
        String key = tblChiTiet.getColumnName(12);
        ArrayList<ChiTietSanPham> list = new ArrayList<>();

        if (selected.equals("Hãng")) {
            loadDataCTSP(chiTietService.getListChiTiet());
        } else if ("Còn hàng".equals(selected)) {
            TableRowSorter<TableModel> sorter = new TableRowSorter<>(tblChiTiet.getModel());
            tblChiTiet.setRowSorter(sorter);
            RowFilter<TableModel, Object> filter = RowFilter.regexFilter("Còn hàng", tblChiTiet.getColumnModel().getColumnIndex(key));
            sorter.setRowFilter(filter);

        } else if ("Hết hàng".equals(selected)) {
            TableRowSorter<TableModel> sorter = new TableRowSorter<>(tblChiTiet.getModel());
            tblChiTiet.setRowSorter(sorter);
            RowFilter<TableModel, Object> filter = RowFilter.regexFilter("Hết hàng", tblChiTiet.getColumnModel().getColumnIndex(key));
            sorter.setRowFilter(filter);

        } else {
            list = chiTietService.getListChiTiet();

        }
    }//GEN-LAST:event_cboFilterTrangThaiItemStateChanged

    private void cboFilterDangAoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFilterDangAoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboFilterDangAoActionPerformed

    private void btnRefreshThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshThuocTinhActionPerformed
        if (rdoChatLieu.isSelected()) {
            if (showConfirmDialog("Bạn có chắc chắn muốn xóa chất liệu không?")) {
                deleteChatLieu();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy xóa");
            }
        } else if (rdoCoAo.isSelected()) {
            if (showConfirmDialog("Bạn có chắc chắn muốn xóa cổ áo không?")) {
                deleteCoAo();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy xóa");
            }
        } else if (rdoCoTay.isSelected()) {
            if (showConfirmDialog("Bạn có chắc chắn muốn xóa cổ tay không?")) {
                deleteCoTay();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy xóa");
            }
        } else if (rdoDangAo.isSelected()) {
            deleteDangAo();
            addCboDangAo();
        } else if (rdoHang.isSelected()) {
            if (showConfirmDialog("Bạn có chắc chắn muốn xóa hãng không?")) {
                deleteHang();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy xóa");
            }
        } else if (rdoMauSac.isSelected()) {
            if (showConfirmDialog("Bạn có chắc chắn muốn xóa màu sắc không?")) {
                deleteMauSac();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy xóa");
            }
        } else if (rdoSize.isSelected()) {
            if (showConfirmDialog("Bạn có chắc chắn muốn xóa kích thước không?")) {
                deleteSize();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy xóa");
            }
        }

    }//GEN-LAST:event_btnRefreshThuocTinhActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddSP;
    private javax.swing.JButton btnAddThuocTinh;
    private javax.swing.JButton btnChatLieu;
    private javax.swing.JButton btnCoAo;
    private javax.swing.JButton btnCoTay;
    private javax.swing.JButton btnDangAo;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnHang;
    private javax.swing.JButton btnMauSac;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRefreshSP;
    private javax.swing.JButton btnRefreshThuocTinh;
    private javax.swing.JButton btnRefreshThuocTinh1;
    private javax.swing.JButton btnSanPham;
    private javax.swing.JButton btnSize;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUpdateSanPham;
    private javax.swing.JButton btnUpdateThuocTinh;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboChatLieu;
    private javax.swing.JComboBox<String> cboCoAo;
    private javax.swing.JComboBox<String> cboCoTay;
    private javax.swing.JComboBox<String> cboDangAo;
    private javax.swing.JComboBox<String> cboFilterChatLieu;
    private javax.swing.JComboBox<String> cboFilterCoAo;
    private javax.swing.JComboBox<String> cboFilterCoTay;
    private javax.swing.JComboBox<String> cboFilterDangAo;
    private javax.swing.JComboBox<String> cboFilterHang;
    private javax.swing.JComboBox<String> cboFilterMauSac;
    private javax.swing.JComboBox<String> cboFilterSanPham;
    private javax.swing.JComboBox<String> cboFilterSize;
    private javax.swing.JComboBox<String> cboFilterTrangThai;
    private javax.swing.JComboBox<String> cboHang;
    private javax.swing.JComboBox<String> cboMauSac;
    private javax.swing.JComboBox<String> cboSize;
    private javax.swing.JComboBox<String> cboTenSP;
    private javax.swing.JPanel chiTietpnl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rdoChatLieu;
    private javax.swing.JRadioButton rdoCoAo;
    private javax.swing.JRadioButton rdoCoTay;
    private javax.swing.JRadioButton rdoDangAo;
    private javax.swing.JRadioButton rdoHang;
    private javax.swing.JRadioButton rdoMauSac;
    private javax.swing.JRadioButton rdoSize;
    private javax.swing.JPanel sanPhampnl;
    private javax.swing.JTable tblChiTiet;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTable tblThuocTinh;
    private javax.swing.JPanel thuocTinhpnl;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtSearchSP;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTenSP;
    // End of variables declaration//GEN-END:variables
}
