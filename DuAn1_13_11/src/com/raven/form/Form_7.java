/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import DomainModel.HoaDon;
import DomainModel.HoaDonViewModel;
import DomainModel.HoaDonChiTiet;
import DomainModel.KhachHang;
import DomainModel.NhanVien;
import DomainModel.QuanLySanPham.ChiTietSanPham;
import Repository.HoaDonRepository;
import Service.ChiTietService;
import Service.Impl.QuanLySanPhamServiceImpl.ChiTietServiceImpl;
import ServiceImpl.HoaDonServiceImpl;
import ServiceImpl.KhachHangServiceImpl;
import Utilities.DBConnection;
import ViewModel.HoaDonChiTietViewModel;
import static com.raven.form.BanHang.hoadon;
import static com.raven.form.BanHang.tongTien;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import mode.TableMode;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author 84386
 */
public class Form_7 extends javax.swing.JPanel {

    HoaDonServiceImpl donService = new HoaDonServiceImpl();
    KhachHangServiceImpl hangService = new KhachHangServiceImpl();
    private DefaultTableModel model = new DefaultTableModel();
    private List<HoaDon> listHoaDon = new ArrayList<>();
    private List<KhachHang> list = new ArrayList<>();
    private DefaultTableModel defaultTableModel = new DefaultTableModel();
    private ChiTietService chiTietService = new ChiTietServiceImpl();
    private ArrayList<ChiTietSanPham> chiTietSanPhams = new ArrayList<>();
    private HoaDonRepository hoaDonRespon = new HoaDonRepository();
    DefaultTableModel dtm;
    public static HoaDonViewModel hoadon = null;
//     private DefaultTableModel defaultTableModel = new DefaultTableModel();
    TableMode tbm;
    int index = 0;
    Connection cn;
    long count, soTrang, trang = 1;
    Statement st;
    ResultSet rs;
    int i = 1;
    public static String tienKhach;
    public static String tienTra;
    public static String tongTien;
    public static String tienGiam;
    public static String diemKH = "0";

    /**
     * Creates new form Form_7
     */
    public Form_7() {
        initComponents();
        loadDataCTSP(chiTietService.getListChiTiet());

//        loadtable2();
//        tb_hoaDon.setShowHorizontalLines(true);
//        tb_hoaDon.setShowVerticalLines(true);
        loadtable();
        loadGioHang();
        txt_tenkhachhang.setText("khach le");
        txt_nhanvien.setText("1");
    }

    public void loadChonKH(String ten) {
        txt_tenkhachhang.setText(ten);
//        txtSDT.setText(sdt);
    }

    public void loadDataCTSP(ArrayList<ChiTietSanPham> chiTietSanPhams) {
        int i = 1;
        defaultTableModel = (DefaultTableModel) tblChiTietSanPham.getModel();
        defaultTableModel.setRowCount(0);
        for (DomainModel.QuanLySanPham.ChiTietSanPham x : chiTietSanPhams) {
            defaultTableModel.addRow(new Object[]{
                i++,
                x.getId(),
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

    public void loadtableHoaDonChiTiet() {
        int i = 1;
        DefaultTableModel dtm = (DefaultTableModel) this.tb_giohnag.getModel();
        ArrayList<HoaDonChiTietViewModel> ds = this.donService.getListHoaDonChiTiet();
        dtm.setRowCount(0);
        for (HoaDonChiTietViewModel hv : ds) {
            Object[] rowdata = new Object[]{
                i++,
                hv.getId_chi_tiet_san_pham(),
                hv.getId_san_pham().getTen(),
                hv.getId_mau_sac().getTen(),
                hv.getId_hang().getTen(),
                hv.getId_size().getTen(),
                hv.getSo_luong(),
                hv.getGia(),
                hv.getId_hoa_don().getTrang_thai() == 1 ? "da thanh toan" : "chua thanh toan",};
            dtm.addRow(rowdata);
        }
    }

    private void fillToTable(List<HoaDon> listHoaDon) {
        model.setRowCount(0);
        for (HoaDon hv : listHoaDon) {
            Object[] rowdata = new Object[]{
                i++,
                hv.getId_hoa_don(),
                hv.getId_khach_hang().getHo_ten(),
                hv.getId_nhan_vien().getHo_ten(),
                hv.getTong_tien(),
                hv.getHinh_thuc_thanh_toan(),
                hv.getCreated_at(),
                hv.getNgay_thanh_toan(),
                hv.getTrang_thai() == 1 ? "da thanh toan" : "chua thanh toan"
//                hv.getId_voucher().getKhuyen_mai(),
            };
            dtm.addRow(rowdata);
        }
    }

    //load table
    public void loadtable() {
        int i = 1;
        DefaultTableModel dtm = (DefaultTableModel) this.tb_hoaDon.getModel();
//        ArrayList<HoaDon> ds = this.donService.getListHoaDon();
        ArrayList<HoaDon> ds = this.hoaDonRespon.findbyHD();
        dtm.setRowCount(0);
        for (HoaDon hv : ds) {
            Object[] rowdata = new Object[]{
                i++,
                hv.getId_hoa_don(),
                hv.getId_khach_hang().getHo_ten(),
                hv.getId_nhan_vien().getHo_ten(),
                hv.getTong_tien(),
                hv.getHinh_thuc_thanh_toan(),
                hv.getCreated_at(),
                hv.getNgay_thanh_toan(),
                hv.getTrang_thai() == 1 ? "da thanh toan" : "chua thanh toan"
//                hv.getId_voucher().getKhuyen_mai(),
            };
            dtm.addRow(rowdata);
        }
    }

    public void loadGioHang() {
        DefaultTableModel dtm = (DefaultTableModel) this.tb_giohnag.getModel();
        dtm.setRowCount(0);
        int row = this.tb_hoaDon.getSelectedRow();
        if (row == -1) {
            return;
        }
        int id = Integer.parseInt(tb_hoaDon.getValueAt(row, 1).toString());
        ArrayList<HoaDonChiTietViewModel> hv1 = this.hoaDonRespon.getListHoaDonChiietByIdGioHang(id);
        for (HoaDonChiTietViewModel hv : hv1) {
            Object[] rowdata = new Object[]{
                //                i++,
                hv.getId_hoa_don().getId_hoa_don(),
                hv.getId_san_pham().getTen(),
                hv.getSo_luong(),
                hv.getGia(),
                hv.getThanh_tien()
            };
            dtm.addRow(rowdata);
        }
//        float tong = 0;
//        ArrayList<HoaDonChiTiet> listHDCTM = hoaDonRespon.getListHoaDonChiTietThanhToan();
//        ArrayList<HoaDonChiTiet> listNew = new ArrayList<>();
//        for (HoaDonChiTiet x : listHDCTM) {
//            if (x.getId_hoa_don() != null && String.valueOf(x.getId_hoa_don().getId_hoa_don()).equals(txt_mahoadon.getText())) {
//                listNew.add(x);
//            }
//        }
//
//        for (HoaDonChiTiet y : listNew) {
//            tong = (Float.valueOf(y.getGia().toString()));
//
//        }

        txt_tongtien.setText("3232");
//        hoadon.setTong_tien(Float.valueOf(tong));
//        tongTien = String.valueOf(tong);
    }

    public void loadtableTrangThaiDaThanhToan() {
        int i = 1;
        DefaultTableModel dtm = (DefaultTableModel) this.tb_hoaDon.getModel();
        ArrayList<HoaDon> ds = this.donService.findbytrangthaiDaThanhToan();
        dtm.setRowCount(0);
        for (HoaDon hv : ds) {
            Object[] rowdata = new Object[]{
                i++,
                hv.getId_hoa_don(),
                hv.getId_khach_hang().getHo_ten(),
                hv.getId_nhan_vien().getHo_ten(),
                hv.getTong_tien(),
                hv.getHinh_thuc_thanh_toan(),
                hv.getCreated_at(),
                hv.getNgay_thanh_toan(),
                hv.getTrang_thai() == 1 ? "da thanh toan" : "chua thanh toan"
//                hv.getId_voucher().getKhuyen_mai(),
            };
            dtm.addRow(rowdata);
        }
    }

    public void loadtableTrangThaiChuaThanhToan() {
        int i = 1;
        DefaultTableModel dtm = (DefaultTableModel) this.tb_hoaDon.getModel();
//        ArrayList<HoaDon> ds = this.hoaDonRespon.findbytrangthaiChuaThanhToanHD();
        ArrayList<HoaDon> ds = this.hoaDonRespon.findbyHDchuatt();
        dtm.setRowCount(0);
//        Collections.sort(list, Comparator.comparing(HoaDon -> HoaDon.getId_khach_hang()));
        for (HoaDon hv : ds) {
            Object[] rowdata = new Object[]{
                i++,
                hv.getId_hoa_don(),
                hv.getId_khach_hang().getHo_ten(),
                hv.getId_nhan_vien().getHo_ten(),
                hv.getTong_tien(),
                hv.getHinh_thuc_thanh_toan(),
                hv.getCreated_at(),
                hv.getNgay_thanh_toan(),
                hv.getTrang_thai() == 1 ? "da thanh toan" : "chua thanh toan"
//                hv.getId_voucher().getKhuyen_mai(),
            };
            dtm.addRow(rowdata);
        }
    }

    public void loadtableTrangThaiChuaThanhToanHoaDonMoi() {
        int i = 1;
        DefaultTableModel dtm = (DefaultTableModel) this.tb_hoaDon.getModel();
        ArrayList<HoaDon> ds = this.hoaDonRespon.findbytrangthaiChuaThanhToanHDNull();
        dtm.setRowCount(0);
        for (HoaDon hv : ds) {
            Object[] rowdata = new Object[]{
                i++,
                hv.getId_hoa_don(),
                //                hv.getId_khach_hang(),
                //                hv.getId_nhan_vien(),
                hv.getId_khach_hang().getHo_ten(),
                hv.getId_nhan_vien().getHo_ten(),
                hv.getTong_tien(),
                hv.getHinh_thuc_thanh_toan(),
                hv.getCreated_at(),
                hv.getNgay_thanh_toan(),
                hv.getTrang_thai() == 1 ? "da thanh toan" : "chua thanh toan"
//                hv.getId_voucher().getKhuyen_mai(),
            };
            dtm.addRow(rowdata);
        }
    }

    private void tongTien() {
        float tong = 0;
        ArrayList<HoaDonChiTiet> listHDCTM = hoaDonRespon.getListHoaDonChiTietThanhToan();
        ArrayList<HoaDonChiTiet> listNew = new ArrayList<>();
        for (HoaDonChiTiet x : listHDCTM) {
            if (x.getId_hoa_don() != null && String.valueOf(x.getId_hoa_don().getId_hoa_don()).equals(txt_mahoadon.getText())) {
                listNew.add(x);
            }
        }

//        for (HoaDonChiTiet y : listNew) {
//            tong = (Float.valueOf(y.getGia().toString()));
//
//        }
        for (HoaDonChiTiet hoaDonChiTiet : listNew) {
            tong += hoaDonChiTiet.getThanh_tien();
        }
        txt_tongtien.setText(String.valueOf(tong));
        hoadon.setTong_tien(Float.valueOf(tong));
        tongTien = String.valueOf(tong);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblChiTietSanPham = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tb_giohnag = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_hoaDon = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbb_phuongthucthanhtoan = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        btn_thanhtoan = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txt_mahoadon = new javax.swing.JTextField();
        txt_tongtien = new javax.swing.JTextField();
        txt_tienkhachdua = new javax.swing.JTextField();
        txt_tienthua = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txt_tenkhachhang = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_nhanvien = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btn_taohoadon = new javax.swing.JButton();
        btn_huyhoadon = new javax.swing.JButton();
        cbb_trangthaihoadon = new javax.swing.JComboBox<>();

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("San Pham"));

        tblChiTietSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã CTSP", "Tên sản phẩm", "Hãng", "Màu sắc", "Chất liệu", "Size", "Dáng áo", "Cổ áo", "Cổ tay", "Giá", "Số lượng tồn", "Trạng thái"
            }
        ));
        tblChiTietSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiTietSanPhamMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblChiTietSanPham);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 577, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Gio Hang"));

        tb_giohnag.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Ma", "San Pham", "So Luong ", "Gia", "Thanh tien"
            }
        ));
        tb_giohnag.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_giohnagMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tb_giohnag);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Hoa Don"));

        tb_hoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tb_hoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Ma hoa don", "Khach Hang", "Nhan vien", "Tong tien", "Hinh thuc thanh toan", "Ngay tao", "Ngay thanh toan", "Trang thai"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_hoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_hoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_hoaDon);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 123, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jLabel1.setText("Ma Hoa Don");

        jLabel2.setText("Tong tien");

        jLabel3.setText("Tien khach dua");

        jLabel4.setText("Tien thua");

        cbb_phuongthucthanhtoan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "tien mat", "chuyen khoan", " " }));

        jLabel5.setText("Thanh toan");

        btn_thanhtoan.setText("Thanh toan");
        btn_thanhtoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_thanhtoanActionPerformed(evt);
            }
        });

        jButton2.setText("Thanh toan + In");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txt_tienkhachdua.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_tienkhachduaCaretUpdate(evt);
            }
        });
        txt_tienkhachdua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_tienkhachduaMouseEntered(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Tao hoa don"));

        jLabel7.setText("Ten khach hang");

        jLabel6.setText("Ma nhan vien");

        jButton1.setText("Chon");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_nhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addComponent(txt_tenkhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_nhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txt_tenkhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        btn_taohoadon.setText("Tao hoa don");
        btn_taohoadon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_taohoadonActionPerformed(evt);
            }
        });

        btn_huyhoadon.setText("Huy hoa don");
        btn_huyhoadon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_huyhoadonActionPerformed(evt);
            }
        });

        cbb_trangthaihoadon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cbb_trangthaihoadon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "tat ca", "da thanh toan", "chua thanh toan", " ", " " }));
        cbb_trangthaihoadon.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbb_trangthaihoadonItemStateChanged(evt);
            }
        });
        cbb_trangthaihoadon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbb_trangthaihoadonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(52, 52, 52)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_mahoadon)
                            .addComponent(txt_tongtien)
                            .addComponent(txt_tienkhachdua)
                            .addComponent(txt_tienthua, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbb_phuongthucthanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btn_taohoadon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                            .addComponent(btn_thanhtoan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                            .addComponent(btn_huyhoadon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(19, 19, 19)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbb_trangthaihoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbb_trangthaihoadon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_mahoadon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_tongtien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_tienkhachdua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_tienthua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbb_phuongthucthanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_thanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_taohoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_huyhoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblChiTietSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietSanPhamMouseClicked
        // lấy ra index của bảng hóa đơn
        int indexHD = tb_hoaDon.getSelectedRow();
        if (indexHD < 0) {
            JOptionPane.showMessageDialog(null, "Mời bạn chọn hóa đơn !");
            return;
        }
        String maHD = tb_hoaDon.getValueAt(indexHD, 1).toString();
        HoaDonChiTiet h = new HoaDonChiTiet();
        String soLuong = JOptionPane.showInputDialog(this, "Mời bạn chọn số lượng");
        if (soLuong.isEmpty()) {
            return;
        }
//        // lấy ra index của bảng sản phẩm
        int indexSP = tblChiTietSanPham.getSelectedRow();
        int slTon = Integer.valueOf(tblChiTietSanPham.getValueAt(indexSP, 11).toString());
        if (slTon <= 0) {
            JOptionPane.showMessageDialog(null, "Hết hàng");
            return;
        }
        if (Integer.valueOf(soLuong) > slTon) {
            JOptionPane.showMessageDialog(null, "Số lượng tồn không đủ");
            return;
        }

        String idCTSP = tb_hoaDon.getValueAt(indexHD, 1).toString();
        int idCTSP1 = Integer.parseInt(tb_hoaDon.getValueAt(indexHD, 1).toString() + "");
        int ihHD = Integer.parseInt(tblChiTietSanPham.getValueAt(indexSP, 1).toString() + "");
        String tenSP = tblChiTietSanPham.getValueAt(indexSP, 2).toString();
        String donGia = tblChiTietSanPham.getValueAt(indexSP, 10).toString();
        h.setGia(Float.valueOf(Integer.valueOf(donGia.replace(".", "")) * Integer.valueOf(soLuong)));
        HoaDon newHD = hoaDonRespon.gethdBymaHD(Integer.parseInt(idCTSP));
        ChiTietSanPham newCTSP = hoaDonRespon.gethdBymaCTSP(ihHD);
        int idnewHD = newHD.getId_hoa_don();
        h.setId_hoa_don(newHD);
//        System.out.println("2 :" + tenSP);
//        System.out.println("3 :" + donGia);
//        System.out.println("4 :" + newHD);
//        System.out.println("5 :" + soLuong);
//        System.out.println("5 :" + idnewHD);

        if (h == null) {
            return;
        }
        ArrayList<HoaDonChiTiet> listHDCT = donService.getListHoaDonChiietByIdSP(idnewHD);
        System.out.println(listHDCT);
        int dem = 0;
        if (listHDCT.size() > 0) {

            for (HoaDonChiTiet y1 : listHDCT) {
                if (String.valueOf(y1.getId_chi_tiet_san_pham().getId()).equals(idCTSP)) {
                    if (y1.getSo_luong() == slTon || ((y1.getSo_luong()) + Integer.valueOf(soLuong)) > slTon) {
                        JOptionPane.showMessageDialog(null, "Không thể vượt quá số lượng đang có");
                        return;
                    }
                    h.setSo_luong(y1.getSo_luong() + Integer.valueOf(soLuong));
                    float thanhtien = Float.valueOf(Integer.valueOf(donGia.replace(".", "")) * (y1.getSo_luong() + Integer.valueOf(soLuong)));
                    h.setThanh_tien(thanhtien);
                    txt_tongtien.setText(String.valueOf(thanhtien));
                    donService.updateHDCT(y1);

                    loadGioHang();
                    dem++;
                }
            }
        }

        if (dem == 0) {
            h.setSo_luong(Integer.valueOf(soLuong));
            long millis = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);
            System.out.println(date);
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet(newHD, newCTSP, Integer.parseInt(soLuong), Float.parseFloat(donGia));
            float thanhtien = Float.parseFloat(soLuong) * Float.parseFloat(donGia);
            HoaDonChiTiet chiTiet = new HoaDonChiTiet(newHD, newCTSP, Integer.parseInt(soLuong), Float.parseFloat(donGia), thanhtien);
            donService.add(chiTiet);
//            System.out.println(hoaDonChiTiet.getSo_luong());
//
//            System.out.println(hoaDonChiTiet.getGia());
//            System.out.println(hoaDonChiTiet.getCreated_at());
//            txt_tongtien.setText(String.valueOf(hoaDonChiTiet.getGia()));
            loadGioHang();
            tongTien();

        }
    }//GEN-LAST:event_tblChiTietSanPhamMouseClicked

    private void tb_giohnagMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_giohnagMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb_giohnagMouseClicked

    private void tb_hoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_hoaDonMouseClicked
        DefaultTableModel dtm = (DefaultTableModel) this.tb_giohnag.getModel();
        dtm.setRowCount(0);
        int row = this.tb_hoaDon.getSelectedRow();
        if (row == -1) {
            return;
        }
        int id = Integer.parseInt(tb_hoaDon.getValueAt(row, 1).toString());
//        HoaDonChiTietViewModel hv = this.hoaDonRespon.getListHoaDonChiietByIdGioHang(id);
        ArrayList<HoaDonChiTietViewModel> hv1 = this.hoaDonRespon.getListHoaDonChiietByIdGioHang(id);
        for (HoaDonChiTietViewModel hv : hv1) {
            Object[] rowdata = new Object[]{
                //                i++,
                hv.getId_hoa_don().getId_hoa_don(),
                hv.getId_san_pham().getTen(),
                hv.getSo_luong(),
                hv.getGia(),
                hv.getThanh_tien()
            };
            dtm.addRow(rowdata);
        }

        //load textfile
        txt_mahoadon.setText(tb_hoaDon.getValueAt(row, 1).toString());
        txt_tenkhachhang.setText(tb_hoaDon.getValueAt(row, 2).toString());
//        txt_nhanvien.setText(tb_hoaDon.getValueAt(row, 3).toString());
//        txt_tongtien.setText(tb_hoaDon.getValueAt(row, 4).toString());
////        String a = tb_hoaDon.getValueAt(row, 5).toString();
//        if (tb_hoaDon.getValueAt(row, 5).toString().equals("tien mat")) {
//            cbb_phuongthucthanhtoan.setSelectedItem("tien mat");
//        } else if (tb_hoaDon.getValueAt(row, 5).toString().equals("chuyen khoan")) {
//            cbb_phuongthucthanhtoan.setSelectedItem("chuyen khoan");
//        } else {
//            cbb_phuongthucthanhtoan.setSelectedItem("");
//        }
        loadGioHang();
        tongTien();
    }//GEN-LAST:event_tb_hoaDonMouseClicked

    private void btn_taohoadonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_taohoadonActionPerformed
//        HoaDon hdNew = new HoaDon();
//
//        //trangthai
//        hdNew.setTrang_thai(0);
//        ArrayList<HoaDon> listHD = hoaDonRespon.findbyHD();
////        ArrayList<HoaDon> listHD = donService.getListHoaDon();
//        int so = listHD.size() + 1;
//        String txtkh = txt_tenkhachhang.getText();
//
//        HoaDon kh = this.donService.getByHoaDonTenKhachHang(txtkh);
//        KhachHang idkh = kh.getId_khach_hang();
//        hdNew.setId_khach_hang(idkh);
//
//        int nv = Integer.parseInt(txt_nhanvien.getText());
//        HoaDon idnv = this.donService.getByHoaDonNhanVien(nv);
//        NhanVien idnvv = kh.getId_nhan_vien();
//        hdNew.setId_nhan_vien(idnvv);
//
//        if (hdNew == null) {
//            JOptionPane.showMessageDialog(null, "Tao that bai");
//            return;
//
//        }
//        ArrayList<HoaDon> listCho = donService.findbytrangthaiChuaThanhToan();
//
//        if (listCho.size() >= 10) {
//            JOptionPane.showMessageDialog(null, "Đã đạt tối đa 10 hóa đơn. Vui lòng thanh toán hóa đơn chờ");
//            return;
//        }
//        System.out.println("fsfdsg" + idkh);
//        System.out.println("sdsf" + idnvv);
////        System.out.println(idsdt);
//        if (hoaDonRespon.addHoaDonMoi(hdNew) != null) {
//            JOptionPane.showMessageDialog(null, "Tao hoa don thanh cong");
//            loadtableTrangThaiChuaThanhToan();
//        } else {
//            JOptionPane.showMessageDialog(null, "Tao that bai");
//        }

//moi
        HoaDon hdNew = new HoaDon();
        hdNew.setTrang_thai(0);
//        hdNew.setId_khach_hang(39);
//        ArrayList<HoaDon> listHD = hoaDonRespon.findbyHD();
//        int so = listHD.size() + 1;
//        int nv = Integer.parseInt(txt_nhanvien.getText());
//        HoaDon idnv = this.donService.getByHoaDonNhanVien(nv);
//        NhanVien idnvv = kh.getId_nhan_vien();
//        hdNew.setId_nhan_vien(idnvv);
        if (hdNew == null) {
            JOptionPane.showMessageDialog(null, "Tao that bai");
            return;

        }
        ArrayList<HoaDon> listCho = hoaDonRespon.findbytrangthaiChuaThanhToanHDNull();

//        if (listCho.size() >= 10) {
//            JOptionPane.showMessageDialog(null, "Đã đạt tối đa 10 hóa đơn. Vui lòng thanh toán hóa đơn chờ");
//            return;
//        }
        System.out.println(hdNew);
        if (hoaDonRespon.addHoaDonMoi(hdNew) != null) {
            JOptionPane.showMessageDialog(null, "Tao hoa don thanh cong");
//            loadtableTrangThaiChuaThanhToanHoaDonMoi();
            loadtableTrangThaiChuaThanhToan();
        } else {
            JOptionPane.showMessageDialog(null, "Tao that bai");
        }
        txt_tenkhachhang.setText("khach le");

    }//GEN-LAST:event_btn_taohoadonActionPerformed

    private void btn_thanhtoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_thanhtoanActionPerformed
        String txtkh = txt_tenkhachhang.getText();
//        HoaDon kh = this.donService.getByHoaDonTenKhachHang(txtkh);
        HoaDon kh = this.hoaDonRespon.getByHoaDonTenKhachHang1(txtkh);
        KhachHang idkh = kh.getId_khach_hang();
        int nv = Integer.parseInt(txt_nhanvien.getText());
        HoaDon idnv = this.donService.getByHoaDonNhanVien(nv);
        NhanVien idnvv = idnv.getId_nhan_vien();

        String tienThua = txt_tienthua.getText();
        String tongTien = txt_tongtien.getText();
        String maHD = txt_mahoadon.getText();

        float tong = Float.valueOf(tongTien);
        float tienKH = Float.valueOf(txt_tienkhachdua.getText());
        float tienThua_number = 0;
        if (tienThua != null) {
            tienThua_number = Float.parseFloat(tienThua);
        }
        if (tienKH < tong) {
            JOptionPane.showMessageDialog(null, "Chua du dieu kien thanh toan");
            return;
        }

//        String hinhThucThanhToan = cbb_phuongthucthanhtoan.getSelectedItem();
        String hinhThucThanhToan = String.valueOf(cbb_phuongthucthanhtoan.getSelectedItem());
//        String ghichu = txtGhiChu.getText();
        ArrayList<HoaDonChiTietViewModel> listHDCT = donService.getListHoaDonChiTiet();
        ArrayList<HoaDonChiTietViewModel> listNewHDCT = new ArrayList<>();
        HoaDonChiTietViewModel hdctvm = new HoaDonChiTietViewModel();
        HoaDon hoadon = null;
        ChiTietSanPham ctsp = new ChiTietSanPham();
        HoaDon h = new HoaDon();
//        Float thanhTien = new Float(tong);
        h.setTong_tien(tong);
        h.setId_hoa_don(Integer.parseInt(maHD));
        h.setHinh_thuc_thanh_toan(hinhThucThanhToan);
        h.setId_khach_hang(idkh);
        h.setId_nhan_vien(idnvv);

        System.out.println(" 1" + tong);
        System.out.println(" 2" + Integer.parseInt(maHD));
        System.out.println(" 3" + hinhThucThanhToan);
        System.out.println(" 4" + idkh);
        System.out.println(" 5" + idnvv);
        System.out.println(" 5" + h);
        hoaDonRespon.updatehoadon_thanhtoan(h);

//        System.out.println(h.getId_khach_hang());
//        System.out.println(h);
//        
//        System.out.println(h.getHinh_thuc_thanh_toan());
//        System.out.println(h.getTong_tien());
//        System.out.println(h.getId_hoa_don());
        hoaDonRespon.updatehoadon_thanhtoan(h);
        // Hiển thị hộp thoại xác nhận
        int confirmResult = JOptionPane.showConfirmDialog(
                this,
                "Ban chac chan muon thanh toan hoa don : " + h.getId_hoa_don() + " ?",
                "Xac nhan cap nhat",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmResult == JOptionPane.YES_OPTION) {
            if (hoaDonRespon.updatehoadon_thanhtoan(h) != null) {
                for (HoaDonChiTietViewModel x : listHDCT) {
                    if (x.getId_hoa_don() != null && String.valueOf(x.getId_hoa_don().getId_hoa_don()).equals(txt_mahoadon.getText())) {
                        listNewHDCT.add(x);
                    }
                }
                for (HoaDonChiTietViewModel y : listNewHDCT) {
                    ctsp.setSoLuong(y.getSo_luong());
                    ctsp.setId(y.getId_chi_tiet_san_pham().getId());
                    hoaDonRespon.updatehoadon_thanhtoan_soluong(ctsp);
                }
                JOptionPane.showMessageDialog(null, "Thanh toan thanh cong");
            }
        } else {
            // Người dùng không xác nhận cập nhật
            JOptionPane.showMessageDialog(this, "Thanh toan that bai");
        }
        tienKhach = txt_tienkhachdua.getText();
        tienTra = txt_tienthua.getText();
//        tienGiam = txtGiamGia.getText();

        loadtable();
        loadDataCTSP(chiTietService.getListChiTiet());

    }//GEN-LAST:event_btn_thanhtoanActionPerformed

    private void txt_tienkhachduaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_tienkhachduaCaretUpdate
        // TODO add your handling code here:
        try {
            String tienKH = "";
            float tienThanhToan = 0;
//            String tienKH;
//            float tienThanhToan;
            if (!txt_tienkhachdua.getText().trim().isEmpty()) {
                tienKH = txt_tienkhachdua.getText();
                tienThanhToan = Float.valueOf(tienKH);
            }
            float tong = Float.valueOf(txt_tongtien.getText());
//            System.out.println(tienThanhToan);
            if (tienThanhToan >= tong) {
                txt_tienthua.setText(String.valueOf(tienThanhToan - tong));
            } else {
                txt_tienthua.setText("0");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("khong tinh dc");
        }

    }//GEN-LAST:event_txt_tienkhachduaCaretUpdate

    private void txt_tienkhachduaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_tienkhachduaMouseEntered
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_tienkhachduaMouseEntered

    private void btn_huyhoadonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_huyhoadonActionPerformed
        // TODO add your handling code here:
        String maHD = txt_mahoadon.getText();
        HoaDon h = new HoaDon();
        h.setId_hoa_don(Integer.parseInt(maHD));

        // Hiển thị hộp thoại xác nhận
        int confirmResult = JOptionPane.showConfirmDialog(
                this,
                "Ban chac chan muon huy hoa don : " + txt_mahoadon.getText() + " ?",
                "Xac nhan cap nhat",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmResult == JOptionPane.YES_OPTION) {
            if (hoaDonRespon.updatehoadon_huy(h) != null) {
                JOptionPane.showMessageDialog(null, "Huy hoa don thanh cong");

            }
        } else {
            // Người dùng không xác nhận cập nhật
            JOptionPane.showMessageDialog(this, "Huy hoa don that bai");
        }

        loadtable();
    }//GEN-LAST:event_btn_huyhoadonActionPerformed

    private void cbb_trangthaihoadonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbb_trangthaihoadonItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbb_trangthaihoadonItemStateChanged

    private void cbb_trangthaihoadonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_trangthaihoadonActionPerformed
        // TODO add your handling code here:
        String selecteditem = (String) cbb_trangthaihoadon.getSelectedItem();
        if (selecteditem.equals("da thanh toan")) {
            loadtableTrangThaiDaThanhToan();
        } else if (selecteditem.equals("chua thanh toan")) {
            loadtableTrangThaiChuaThanhToan();
        } else if (selecteditem.equals("tat ca")) {
            loadtable();
        }
    }//GEN-LAST:event_cbb_trangthaihoadonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

//        try {
//            Hashtable map = new Hashtable();
//            JasperReport rpt;
//
//            rpt = JasperCompileManager.compileReport("src\\com.raven.form\\rptHoaDon.jrxml");
//
//            map.put("MaHD", hoadon.getId_hoa_don());
//            map.put("TienKhachTra", tienKhach);
//            map.put("tienThoi", tienTra);
//            map.put("tongTien", tongTien);
//            map.put("tienGiam", tienGiam);
//            map.put("diemKH", diemKH);
//            JasperPrint p = JasperFillManager.fillReport(rpt, map, DBConnection.getConnection());
//
//            JasperViewer.viewReport(p, true);
//
//        } catch (JRException ex) {
//            System.out.println(ex.getMessage());
//        }
        MessageFormat header = new MessageFormat("Hoa don");
        MessageFormat footer = new MessageFormat("(0, number, interger)");
        try {
            PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
            set.add(OrientationRequested.LANDSCAPE);
            tb_giohnag.print(JTable.PrintMode.FIT_WIDTH, header, footer, true, set, true);
            JOptionPane.showMessageDialog(null, "Printf succefully");
        } catch (java.awt.print.PrinterException e) {
            JOptionPane.showMessageDialog(null, "failed");
        }

        btn_thanhtoanActionPerformed(evt);
        System.out.println(hoadon.getId_hoa_don());
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
//        KhachHangBanHang vp = new KhachHangBanHang();
//        vp.setVisible(true);
        new KhachHangBanHang(this, tb_giohnag, tb_hoaDon).setVisible(true);

    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_huyhoadon;
    private javax.swing.JButton btn_taohoadon;
    private javax.swing.JButton btn_thanhtoan;
    private javax.swing.JComboBox<String> cbb_phuongthucthanhtoan;
    private javax.swing.JComboBox<String> cbb_trangthaihoadon;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable tb_giohnag;
    private javax.swing.JTable tb_hoaDon;
    private javax.swing.JTable tblChiTietSanPham;
    private javax.swing.JTextField txt_mahoadon;
    private javax.swing.JTextField txt_nhanvien;
    private javax.swing.JTextField txt_tenkhachhang;
    private javax.swing.JTextField txt_tienkhachdua;
    private javax.swing.JTextField txt_tienthua;
    private javax.swing.JTextField txt_tongtien;
    // End of variables declaration//GEN-END:variables
}
