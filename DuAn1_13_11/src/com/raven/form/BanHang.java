/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.raven.form;

import DomainModel.HoaDon;
import DomainModel.HoaDonChiTiet;
import DomainModel.KhachHang;
import DomainModel.NhanVien;
import DomainModel.QuanLySanPham.ChiTietSanPham;
import Repository.HoaDonRepository;
import Service.ChiTietService;
import Service.Impl.QuanLySanPhamServiceImpl.ChiTietServiceImpl;
import ServiceImpl.HoaDonServiceImpl;
import ServiceImpl.KhachHangServiceImpl;
import ViewModel.HoaDonChiTietViewModel;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mode.TableMode;

/**
 *
 * @author 84386
 */
public class BanHang extends javax.swing.JFrame {

    HoaDonServiceImpl donService = new HoaDonServiceImpl();
    KhachHangServiceImpl hangService = new KhachHangServiceImpl();
    private DefaultTableModel model = new DefaultTableModel();
    private List<HoaDon> listHoaDon = new ArrayList<>();
    private List<KhachHang> list = new ArrayList<>();
    private List<ChiTietSanPham> list2 = new ArrayList<>();
    private DefaultTableModel defaultTableModel = new DefaultTableModel();
    private ChiTietService chiTietService = new ChiTietServiceImpl();
    private ArrayList<ChiTietSanPham> chiTietSanPhams = new ArrayList<>();
    private HoaDonRepository hoaDonRespon = new HoaDonRepository();
    public static HoaDon hoadon = null;
    public static String tongTien;
    private Map<HoaDonRepository, Integer> maps;
    DefaultTableModel dtm;
//     private DefaultTableModel defaultTableModel = new DefaultTableModel();
    TableMode tbm;
    int index = 0;
    Connection cn;
    long count, soTrang, trang = 1;
    Statement st;
    ResultSet rs;
    int i = 1;

    /**
     * Creates new form BanHang
     */
    public BanHang() {
        initComponents();
        loadDataCTSP(chiTietService.getListChiTiet());

//        loadtable2();
//        tb_hoaDon.setShowHorizontalLines(true);
//        tb_hoaDon.setShowVerticalLines(true);
        loadtable();
        loadGioHang();
    }

//       loafd dât chi tiets san pham 
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
//        DefaultTableModel dtm = (DefaultTableModel) this.tb_giohnag.getModel();
//        dtm.setRowCount(1);
//        int row = this.tb_hoaDon.getSelectedRow();
//        if (row == -1) {
//            return;
//        }
//        int id = Integer.parseInt(tb_hoaDon.getValueAt(row, 1).toString());
//        HoaDonChiTietViewModel hv = this.hoaDonRespon.getListHoaDonChiietByIdGioHang(id);
//        dtm.addRow(new Object[]{
//            i++,
//            hv.getId_san_pham().getTen(),
//            hv.getSo_luong(),
//            hv.getGia(),
//            hv.getThanh_tien()
//        });
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
                i++,
                hv.getId_san_pham().getId(),
                hv.getId_san_pham().getTen(),
                hv.getSo_luong(),
                hv.getGia(),
                hv.getThanh_tien()
            };
            dtm.addRow(rowdata);
        }
    }

    public void loadtableTrangThaiDaThanhToan() {
        int i = 1;
        DefaultTableModel dtm = (DefaultTableModel) this.tb_hoaDon.getModel();
        ArrayList<HoaDon> ds = this.donService.findbytrangthaiDaThanhToan();
        dtm.setRowCount(0);
        for (HoaDon hv : ds) {
            Object[] rowdata = new Object[]{
                i++,
                //                                hv.getId_hoa_don(),
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
        ArrayList<HoaDon> ds = this.hoaDonRespon.findbytrangthaiChuaThanhToanHD();
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

    private void tongTien() {
        float tong = 0;
        ArrayList<HoaDonChiTietViewModel> listHDCTM = donService.getListHoaDonChiTiet();
        ArrayList<HoaDonChiTietViewModel> listNew = new ArrayList<>();
        for (HoaDonChiTietViewModel x : listHDCTM) {
            if (x.getId_hoa_don() != null && String.valueOf(x.getId_hoa_don().getId_hoa_don()).equals(txt_mahoadon.getText())) {
                listNew.add(x);
            }
        }

        for (HoaDonChiTietViewModel y : listNew) {
            tong += (Float.valueOf(y.getGia().toString()));

        }

        txt_tongtien.setText(String.valueOf(tong));
        hoadon.setTong_tien(Float.valueOf(tong));
        tongTien = String.valueOf(tong);
    }

//    private void showTableSanPham(List<ChiTietSanPham> lists) {
//        dtm.setRowCount(0);
//        for (ChiTietSanPham sp : lists) {
//            dtm.addRow(sp.toDataRow());
//        }
//    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_hoaDon = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tb_giohnag = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblChiTietSanPham = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        btn_taohoadon = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbb_phuongthucthanhtoan = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btn_thanhtoan = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txt_mahoadon = new javax.swing.JTextField();
        txt_tongtien = new javax.swing.JTextField();
        txt_tienkhachdua = new javax.swing.JTextField();
        txt_tienthua = new javax.swing.JTextField();
        txt_nhanvien = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_tenkhachhang = new javax.swing.JTextField();
        txt_sodienthoai = new javax.swing.JTextField();
        jd_ngay = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        btn_taohoadon.setText("Tao hoa don");
        btn_taohoadon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_taohoadonActionPerformed(evt);
            }
        });

        jLabel1.setText("Ma Hoa Don");

        jLabel2.setText("Tong tien");

        jLabel3.setText("Tien khach dua");

        jLabel4.setText("Tien thua");

        cbb_phuongthucthanhtoan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "tien mat", "chuyen khoan", " " }));

        jLabel5.setText("Thanh toan");

        jLabel6.setText("Nhan vien");

        btn_thanhtoan.setText("Thanh toan");
        btn_thanhtoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_thanhtoanActionPerformed(evt);
            }
        });

        jButton2.setText("Thanh toan + In");

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

        jLabel7.setText("Ten khach hang");

        jLabel8.setText("So dien thoai");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(31, 31, 31)
                        .addComponent(txt_sodienthoai))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txt_tenkhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(txt_tenkhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_sodienthoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel9.setText("Ngay thanh toan");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_taohoadon)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel9))))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jd_ngay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_mahoadon, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                                .addComponent(txt_tongtien)
                                .addComponent(txt_tienkhachdua)
                                .addComponent(txt_tienthua)
                                .addComponent(cbb_phuongthucthanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_nhanvien)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(btn_thanhtoan)
                .addGap(26, 26, 26)
                .addComponent(jButton2)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btn_taohoadon)
                        .addGap(18, 18, 18)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_tienthua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cbb_phuongthucthanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_nhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jd_ngay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9))
                .addGap(82, 82, 82)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_thanhtoan)
                    .addComponent(jButton2))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 125, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tb_hoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_hoaDonMouseClicked
//        load table
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
                i++,
                hv.getId_san_pham().getId(),
                hv.getId_san_pham().getTen(),
                //             hv.getId_chi_tiet_san_pham().getSanPham().getTen(),
                hv.getSo_luong(),
                hv.getGia(),
                hv.getThanh_tien()
            };
            dtm.addRow(rowdata);
        }

        //load textfile
        txt_mahoadon.setText(tb_hoaDon.getValueAt(row, 1).toString());
        txt_tenkhachhang.setText(tb_hoaDon.getValueAt(row, 2).toString());
        txt_nhanvien.setText(tb_hoaDon.getValueAt(row, 3).toString());
        txt_tongtien.setText(tb_hoaDon.getValueAt(row, 4).toString());
//        String a = tb_hoaDon.getValueAt(row, 5).toString();
        if (tb_hoaDon.getValueAt(row, 5).toString().equals("tien mat")) {
            cbb_phuongthucthanhtoan.setSelectedItem("tien mat");
        } else if (tb_hoaDon.getValueAt(row, 5).toString().equals("chuyen khoan")) {
            cbb_phuongthucthanhtoan.setSelectedItem("chuyen khoan");
        } else {
            cbb_phuongthucthanhtoan.setSelectedItem("");
        }
//        String b = tb_hoaDon.getValueAt(row, 8).toString();
//        if (b.equals("da thanh toan")) {
//
//            rd1.setSelected(true);
//        } else {
//            rd2.setSelected(true);
//        }


    }//GEN-LAST:event_tb_hoaDonMouseClicked

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
        System.out.println("2 :" + tenSP);
        System.out.println("3 :" + donGia);
        System.out.println("4 :" + newHD);
        System.out.println("5 :" + soLuong);
        System.out.println("5 :" + idnewHD);

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

            loadGioHang();
            
        }

    }//GEN-LAST:event_tblChiTietSanPhamMouseClicked

    private void tb_giohnagMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_giohnagMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb_giohnagMouseClicked

    private void btn_taohoadonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_taohoadonActionPerformed
        HoaDon hdNew = new HoaDon();

        //trangthai
        hdNew.setTrang_thai(0);
        ArrayList<HoaDon> listHD = donService.getListHoaDon();
        int so = listHD.size() + 2;
//        String i = "HD" + so;
//        hdNew.setId_hoa_don(so);

        String txtkh = txt_tenkhachhang.getText();

        HoaDon kh = this.donService.getByHoaDonTenKhachHang(txtkh);
        KhachHang idkh = kh.getId_khach_hang();
        hdNew.setId_khach_hang(idkh);

        String txt_sodienthoai = this.txt_sodienthoai.getText();
        HoaDon sdt = this.hoaDonRespon.getByHoaDonSDTKhachHang(txt_sodienthoai);
        String idsdt = kh.getSdt();

        int nv = Integer.parseInt(txt_nhanvien.getText());
        HoaDon idnv = this.hoaDonRespon.getByHoaDonNhanVien(nv);
        NhanVien idnvv = kh.getId_nhan_vien();
        hdNew.setId_nhan_vien(idnvv);

        if (hdNew == null) {
            return;
        }
        ArrayList<HoaDon> listCho = donService.findbytrangthaiChuaThanhToan();

        if (listCho.size() >= 10) {
            JOptionPane.showMessageDialog(null, "Đã đạt tối đa 10 hóa đơn. Vui lòng thanh toán hóa đơn chờ");
            return;
        }
        System.out.println(idkh);
        System.out.println(idnv);
        System.out.println(idsdt);

        if (hoaDonRespon.addHoaDonMoi(hdNew) != null) {
            JOptionPane.showMessageDialog(null, "Tạo hóa đơn thành công");
            loadtableTrangThaiChuaThanhToan();
        } else {
            JOptionPane.showMessageDialog(null, "Tạo thất bại");
        }

    }//GEN-LAST:event_btn_taohoadonActionPerformed

    private void txt_tienkhachduaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_tienkhachduaCaretUpdate
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_tienkhachduaCaretUpdate

    private void txt_tienkhachduaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_tienkhachduaMouseEntered
//        // TODO add your handling code here:
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
    }//GEN-LAST:event_txt_tienkhachduaMouseEntered

    private void btn_thanhtoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_thanhtoanActionPerformed
        // TODO add your handling code here: 
        String tienThua = txt_tienthua.getText();
        String tongTien = txt_tongtien.getText();
        float tong = Float.valueOf(tongTien);
        float tienKH = Float.valueOf(txt_tienkhachdua.getText());
        int tienThua_number = 0;
        if (tienThua != null) {
            tienThua_number = Integer.valueOf(tienThua);
        }
        if (tienKH < tong) {
            JOptionPane.showMessageDialog(null, "Chưa đủ điều kiện thanh toán");
            return;
        }

//        String hinhThucThanhToan = cbb_phuongthucthanhtoan.getSelectedItem();
        String hinhThucThanhToan = String.valueOf(cbb_phuongthucthanhtoan.getSelectedIndex());
//        String ghichu = txtGhiChu.getText();
        ArrayList<HoaDonChiTietViewModel> listHDCT = donService.getListHoaDonChiTiet();
        ArrayList<HoaDonChiTietViewModel> listNewHDCT = new ArrayList<>();
        HoaDonChiTietViewModel hdctvm = new HoaDonChiTietViewModel();
        HoaDon hoadon = null;
        ChiTietSanPham ctsp = new ChiTietSanPham();
        HoaDon h = new HoaDon();
//        Float thanhTien = new Float(tong);
        h.setTong_tien(tong);
        h.setId_hoa_don(hoadon.getId_hoa_don());
        h.setHinh_thuc_thanh_toan(hinhThucThanhToan);

        if (hoaDonRespon.updatehoadon_thanhtoan(h) != null) {

            for (HoaDonChiTietViewModel x : listHDCT) {
                if (x.getId_hoa_don() != null && String.valueOf(x.getId_hoa_don().getId_hoa_don()).equals(txt_mahoadon.getText())) {
                    listNewHDCT.add(x);
                }
            }
//            for (HoaDonChiTietViewModel y : listNewHDCT) {
//                hdctvm.setIdHDCT(y.getIdHDCT());
//                ctsp.setIdCTSP(y.getIdCTSP().getIdCTSP());
//                ctsp.setSoLuong(y.getSoLuong());
//                chiTietSanPhamService.update_ThanhToan(ctsp);
//                hoaDonChiTieservice.updateHDCT_ThanhToan(hdctvm);
//            }

//            tienKhach = txt_tenkhachhang.getText();
//            tienTra = txt_tienthua.getText();
            JOptionPane.showMessageDialog(null, "Thanh toán thành công");
        }
//        loadThanhToan();
    }//GEN-LAST:event_btn_thanhtoanActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BanHang().setVisible(true);
            }
        });
    }
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_taohoadon;
    private javax.swing.JButton btn_thanhtoan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbb_phuongthucthanhtoan;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private com.toedter.calendar.JDateChooser jd_ngay;
    private javax.swing.JTable tb_giohnag;
    private javax.swing.JTable tb_hoaDon;
    private javax.swing.JTable tblChiTietSanPham;
    private javax.swing.JTextField txt_mahoadon;
    private javax.swing.JTextField txt_nhanvien;
    private javax.swing.JTextField txt_sodienthoai;
    private javax.swing.JTextField txt_tenkhachhang;
    private javax.swing.JTextField txt_tienkhachdua;
    private javax.swing.JTextField txt_tienthua;
    private javax.swing.JTextField txt_tongtien;
    // End of variables declaration//GEN-END:variables

