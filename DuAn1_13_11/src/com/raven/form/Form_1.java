/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

//import DomainModel.HoaDon;
import DomainModel.KhachHang;
//import DomainModel.NhanVien1;
//import DomainModel.Vourcher;
import ServiceImpl.HoaDonServiceImpl;
import ServiceImpl.KhachHangServiceImpl;
import Utilities.DBConnection;
import ViewModel.HoaDonChiTietViewModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import DomainModel.TableMode;

/**
 *
 * @author 84386
 */
public class Form_1 extends javax.swing.JPanel {

    HoaDonServiceImpl donService = new HoaDonServiceImpl();
    KhachHangServiceImpl hangService = new KhachHangServiceImpl();
    private DefaultTableModel model = new DefaultTableModel();
//    private List<HoaDon> listHoaDon = new ArrayList<>();
    private List<KhachHang> list = new ArrayList<>();
    DefaultTableModel dtm;
    TableMode tbm;
    int index = 0;
    Connection cn;
    long count, soTrang, trang = 1;
    Statement st;
    ResultSet rs;
    int i = 1;

    /**
     * Creates new form Form_1_1
     */
    public Form_1() {
        initComponents();
        tb_hoaDon.setShowHorizontalLines(true);
        tb_hoaDon.setShowVerticalLines(true);
        titleTable();
        countDb();
        if (count % 5 == 0) {
            soTrang = count / 5;
        } else {
            soTrang = count / 5 + 1;
        }
        loadData(1);
        lbSoTrang.setText("1/" + soTrang);
        lbTrang.setText("1");
        loadtable();
        loadtable2();
    }

    public void titleTable() {
        tbm = new TableMode();
        tb_hoaDon.setModel(tbm);
        tb_hoaDon.setShowHorizontalLines(true);
        tb_hoaDon.setShowVerticalLines(true);
    }

    public void countDb() {
        try {
            String query = "Select count(*) from hoa_don";
            cn = DBConnection.getConnection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                count = rs.getLong(1);
            }
            rs.close();
            st.close();
            cn.close();
        } catch (Exception ex) {
            Logger.getLogger(Form_1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadData(long trang) {
        int i = 1;
        titleTable();
        this.tbm.getDataVector().removeAllElements();
        try {
            String sql = "select top 5 hoa_don.id_hoa_don,\n"
                    + " hoa_don.created_at,\n"
                    + " hoa_don.ngay_thanh_toan,\n"
                    + " khach_hang.ho_ten,\n"
                    + " hoa_don.id_khach_hang,\n"
                    + "hoa_don.tong_tien,\n"
                    + " hoa_don.hinh_thuc_thanh_toan,\n"
                    + " hoa_don.trang_thai, "
                    + " nhan_vien.ho_ten, \n"
                    + "	hoa_don.id_nhan_vien, \n"
                    + " vourcher.[%_khuyen_mai] ,\n"
                    + " hoa_don.id_voucher \n"
                    + "from hoa_don join khach_hang on hoa_don.id_khach_hang = khach_hang.id_khach_hang \n"
                    + "                                 join nhan_vien on hoa_don.id_nhan_vien = nhan_vien.id_nhan_vien \n"
                    + "                                join vourcher on hoa_don.id_voucher = vourcher.id_voucher where id_hoa_don not in (select top " + (trang * 5 - 5) + " id_hoa_don from hoa_don )";

            cn = DBConnection.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Vector v = new Vector();
                int id = rs.getInt(1);
                Date cretedat = rs.getDate(2);
                Date que = rs.getDate(3);
                String hoten = rs.getString(4);
                int id_khachHang = rs.getInt(5);
                Float tongtien = rs.getFloat(6);
                String hinhthucthanhtoan = rs.getString(7);
                int trangthai = rs.getInt(8);
                String tennv = rs.getString(9);
                int id_nhan_vien = rs.getInt(10);
                int khuyenmai = rs.getInt(11);
                int id_khuyenmai = rs.getInt(12);

                v.add(i++);
//                v.add(id);
                v.add(hoten);
                v.add(tennv);
                v.add(tongtien);
                v.add(hinhthucthanhtoan);
//                v.add(khuyenmai);
                v.add(cretedat);
                v.add(que);
                v.add(trangthai == 1 ? "da thanh toan" : "chua thanh toan");

                this.tbm.addRow(v);
            }
            rs.close();
            st.close();
            cn.close();
        } catch (Exception ex) {
            Logger.getLogger(Form_1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadtableHoaDonChiTiet() {
        int i = 1;
        DefaultTableModel dtm = (DefaultTableModel) this.tb_hoadonchitiet.getModel();
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
                //                hv.getId_hoa_don(),
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
        ArrayList<HoaDon> ds = this.donService.getListHoaDon();
        dtm.setRowCount(0);
        for (HoaDon hv : ds) {
            Object[] rowdata = new Object[]{
                i++,
                //                hv.getId_hoa_don(),
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

    public void loadtable2() {
        int i = 1;
        DefaultTableModel dtm = (DefaultTableModel) this.tb_hoaDon2.getModel();
        ArrayList<HoaDon> ds = this.donService.getListHoaDon();
        dtm.setRowCount(0);
        for (HoaDon hv : ds) {
            Object[] rowdata = new Object[]{
                i++,
                hv.getId_hoa_don(),
                //                hv.getId_hoa_don(),
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

    public void loadtableChuyenKhoan() {
        int i = 1;
        DefaultTableModel dtm = (DefaultTableModel) this.tb_hoaDon.getModel();
        ArrayList<HoaDon> ds = this.donService.getListHoaDonChuyenKhoan();
        dtm.setRowCount(0);
        for (HoaDon hv : ds) {
            Object[] rowdata = new Object[]{
                i++,
                //                hv.getId_hoa_don(),
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

    public void loadtableTienMat() {
        int i = 1;
        DefaultTableModel dtm = (DefaultTableModel) this.tb_hoaDon.getModel();
        ArrayList<HoaDon> ds = this.donService.getListHoaDonTienMat();
        dtm.setRowCount(0);
        for (HoaDon hv : ds) {
            Object[] rowdata = new Object[]{
                i++,
                //                hv.getId_hoa_don(),
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

    public void loadtableTrangThaiDaThanhToan() {
        int i = 1;
        DefaultTableModel dtm = (DefaultTableModel) this.tb_hoaDon.getModel();
        ArrayList<HoaDon> ds = this.donService.findbytrangthaiDaThanhToan();
        dtm.setRowCount(0);
        for (HoaDon hv : ds) {
            Object[] rowdata = new Object[]{
                i++,
                //                hv.getId_hoa_don(),
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
        ArrayList<HoaDon> ds = this.donService.findbytrangthaiChuaThanhToan();
        dtm.setRowCount(0);
        for (HoaDon hv : ds) {
            Object[] rowdata = new Object[]{
                i++,
                //                hv.getId_hoa_don(),
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        cbb_thanhtoan = new javax.swing.JComboBox<>();
        cbb_trangthaihoadon = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_hoaDon = new javax.swing.JTable();
        txt_timkiem = new javax.swing.JTextField();
        btn_nhoMax = new javax.swing.JButton();
        btn_nho = new javax.swing.JButton();
        btn_lon = new javax.swing.JButton();
        btn_lonMax = new javax.swing.JButton();
        lbTrang = new javax.swing.JLabel();
        lbSoTrang = new javax.swing.JLabel();
        btn_PDF = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cbb_tim = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tb_hoadonchitiet = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tb_hoaDon2 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_hoadonchitiet1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lb1 = new javax.swing.JLabel();
        lb3 = new javax.swing.JLabel();
        lb2 = new javax.swing.JLabel();
        cbb_thanhtoan1 = new javax.swing.JComboBox<>();
        rd_chuathanhtoan = new javax.swing.JRadioButton();
        rd_dathanhtoan = new javax.swing.JRadioButton();
        txt_timkiemnhieu = new javax.swing.JTextField();
        jd_ngaytao = new com.toedter.calendar.JDateChooser();
        jd_ngaythanhtoan = new com.toedter.calendar.JDateChooser();

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "HOA DON", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(51, 255, 204));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Hinh thuc thanh toan");

        cbb_thanhtoan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cbb_thanhtoan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "tat ca", "chuyen khoan", "tien mat" }));
        cbb_thanhtoan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbb_thanhtoanItemStateChanged(evt);
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

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Trang thai hoa don");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Tim kiem hoa don");

        tb_hoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tb_hoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Ma hoa don", "Ngay tao", "Ngay thanh toan", "Khach Hang", "Tong tien", "Thanh toan", "Trang thai"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, true, false
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

        txt_timkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_timkiemActionPerformed(evt);
            }
        });
        txt_timkiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_timkiemKeyReleased(evt);
            }
        });

        btn_nhoMax.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_nhoMax.setText("<<");
        btn_nhoMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nhoMaxActionPerformed(evt);
            }
        });

        btn_nho.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_nho.setText("<");
        btn_nho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nhoActionPerformed(evt);
            }
        });

        btn_lon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_lon.setText(">");
        btn_lon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lonActionPerformed(evt);
            }
        });

        btn_lonMax.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_lonMax.setText(">>");
        btn_lonMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lonMaxActionPerformed(evt);
            }
        });

        lbTrang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbTrang.setText("jLabel1");

        lbSoTrang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbSoTrang.setText("jLabel2");

        btn_PDF.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_PDF.setText("PDF");
        btn_PDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_PDFActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Trang");

        cbb_tim.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Khach hang", "Id", "Nhan vien", " " }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 909, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbb_thanhtoan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbb_trangthaihoadon, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(cbb_tim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(txt_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_PDF, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btn_nhoMax)
                .addGap(18, 18, 18)
                .addComponent(btn_nho, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(lbSoTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_lon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btn_lonMax)
                .addGap(396, 396, 396))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txt_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbb_tim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_PDF, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(cbb_trangthaihoadon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(cbb_thanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_nhoMax, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_nho, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbSoTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_lon, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_lonMax, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "HOA DON CHI TIET", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel4.setForeground(new java.awt.Color(51, 255, 204));

        tb_hoadonchitiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "San Pham", "Mau Sac", "Hang", "Size", "So Luong ", "Gia", "Trang thai"
            }
        ));
        jScrollPane3.setViewportView(tb_hoadonchitiet);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 903, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(106, 106, 106))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(501, 501, 501))
        );

        jTabbedPane1.addTab("Hoa don", jPanel1);

        tb_hoaDon2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tb_hoaDon2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Ma hoa don", "Ngay tao", "Ngay thanh toan", "Khach Hang", "Tong tien", "Thanh toan", "Trang thai"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_hoaDon2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_hoaDon2MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tb_hoaDon2);

        tb_hoadonchitiet1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "ID San Pham", "San Pham", "Mau Sac", "Hang", "Size", "So Luong ", "Gia", "Trang thai"
            }
        ));
        jScrollPane2.setViewportView(tb_hoadonchitiet1);

        jLabel2.setText("Ma hoa don");

        jLabel3.setText("Ngay tao");

        jLabel4.setText("Ngay thanh toan");

        jLabel5.setText("Khach hang");

        jLabel6.setText("Tong tien");

        jLabel7.setText("Thanh toan");

        jLabel8.setText("Trang thai");

        lb1.setText("Null");

        lb3.setText("Null");

        lb2.setText("Null");

        cbb_thanhtoan1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        buttonGroup1.add(rd_chuathanhtoan);
        rd_chuathanhtoan.setText("Chua thanh toan");

        buttonGroup1.add(rd_dathanhtoan);
        rd_dathanhtoan.setText("Da thanh toan");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(rd_chuathanhtoan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rd_dathanhtoan))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lb2)
                                            .addComponent(cbb_thanhtoan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lb3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lb1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(238, 238, 238))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jd_ngaytao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jd_ngaythanhtoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(189, 189, 189)))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_timkiemnhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 909, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(txt_timkiemnhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lb1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jd_ngaytao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jd_ngaythanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(lb2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(lb3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cbb_thanhtoan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(rd_chuathanhtoan)
                    .addComponent(rd_dathanhtoan))
                .addGap(39, 39, 39))
        );

        jTabbedPane1.addTab("Lich su hoa don", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 968, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbb_thanhtoanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbb_thanhtoanItemStateChanged
        // TODO add your handling code here:
        String selecteditem = (String) cbb_thanhtoan.getSelectedItem();
        if (selecteditem.equals("chuyen khoan")) {
            loadtableChuyenKhoan();
        } else if (selecteditem.equals("tien mat")) {
            loadtableTienMat();
        } else if (selecteditem.equals("tat ca")) {
            loadtable();
        }
    }//GEN-LAST:event_cbb_thanhtoanItemStateChanged

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

    private void tb_hoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_hoaDonMouseClicked
        DefaultTableModel dtm = (DefaultTableModel) this.tb_hoadonchitiet.getModel();
        dtm.setRowCount(0);
        //        txt_test.setText("");

        int row = this.tb_hoaDon.getSelectedRow();
        if (row == -1) {
            return;
        }
        //        txt_test.setText(tb_hoaDon.getValueAt(row, 0).toString());
        int id = Integer.parseInt(tb_hoaDon.getValueAt(row, 0).toString());
        HoaDonChiTietViewModel hv = this.donService.getListHoaDonChiietById(id);
        dtm.addRow(new Object[]{
            i++,
            //            hv.getId_chi_tiet_san_pham(),
            hv.getId_san_pham().getTen(),
            hv.getId_mau_sac().getTen(),
            hv.getId_hang().getTen(),
            hv.getId_size().getTen(),
            hv.getSo_luong(),
            hv.getGia(),
            hv.getId_hoa_don().getTrang_thai() == 1 ? "da thanh toan" : "chua thanh toan"
        });

    }//GEN-LAST:event_tb_hoaDonMouseClicked

    private void txt_timkiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_timkiemKeyReleased
        // TODO add your handling code here:
//        listNhanVien.clear();
//        DefaultTableModel defaultTableModel = (DefaultTableModel) tb_hoaDon.getModel();
//        defaultTableModel.setRowCount(0);
//        if (cbb_tim.getSelectedItem().equals("Khach hang")) {
//            String kh = txt_timkiem.getText();
//            HoaDon hv = this.donService.getByHoaDonTenKhachHang(kh);
//            defaultTableModel.addRow(new Object[]{
//                hv.getId_hoa_don(),
//                hv.getId_hoa_don(),
//                hv.getCreated_at(),
//                hv.getNgay_thanh_toan(),
//                hv.getId_khach_hang().getHo_ten(),
//                hv.getTong_tien(),
//                hv.getHinh_thuc_thanh_toan(),
//                hv.getTrang_thai() == 1 ? "da thanh toan" : "chua thanh toan",
//                hv.getId_nhan_vien().getHo_ten(),
//                hv.getId_voucher().getKhuyen_mai(),});
//
//        }
//        if (cbb_tim.getSelectedItem().equals("Id")) {
//            int id = Integer.parseInt(txt_timkiem.getText());
//            HoaDon hv = this.donService.getByHoaDonId(id);
//            defaultTableModel.addRow(new Object[]{
//                hv.getId_hoa_don(),
//                hv.getId_hoa_don(),
//                hv.getCreated_at(),
//                hv.getNgay_thanh_toan(),
//                hv.getId_khach_hang().getHo_ten(),
//                hv.getTong_tien(),
//                hv.getHinh_thuc_thanh_toan(),
//                hv.getTrang_thai() == 1 ? "da thanh toan" : "chua thanh toan",
//                hv.getId_nhan_vien().getHo_ten(),
//                hv.getId_voucher().getKhuyen_mai(),});
//        }
//        if (cbb_tim.getSelectedItem().equals("Nhan vien")) {
//            String nv = txt_timkiem.getText();
//            HoaDon hv = this.donService.getByHoaDonNhanVien(nv);
//            defaultTableModel.addRow(new Object[]{
//                hv.getId_hoa_don(),
//                hv.getId_hoa_don(),
//                hv.getCreated_at(),
//                hv.getNgay_thanh_toan(),
//                hv.getId_khach_hang().getHo_ten(),
//                hv.getTong_tien(),
//                hv.getHinh_thuc_thanh_toan(),
//                hv.getTrang_thai() == 1 ? "da thanh toan" : "chua thanh toan",
//                hv.getId_nhan_vien().getHo_ten(),
//                hv.getId_voucher().getKhuyen_mai(),});
//        }

        listHoaDon.clear();
        List<HoaDon> hoaDons = new ArrayList<>();
        float gia = 0;
        if (txt_timkiem.getText().isEmpty()) {
            gia = 0;
        } else if (txt_timkiem.getText().chars().allMatch(Character::isDigit)) {
            gia = Float.parseFloat(txt_timkiem.getText());
        }
        String sql = "select hoa_don.id_hoa_don,"
                + "hoa_don.created_at,"
                + "hoa_don.ngay_thanh_toan,"
                + "khach_hang.ho_ten,"
                + "hoa_don.id_khach_hang, "
                + "hoa_don.tong_tien,"
                + "hoa_don.hinh_thuc_thanh_toan,"
                + "hoa_don.trang_thai,"
                + "                nhan_vien.ho_ten, \n"
                + "		   hoa_don.id_nhan_vien, \n"
                + "                vourcher.[%_khuyen_mai] ,\n"
                + "                hoa_don.id_voucher \n"
                + " from hoa_don "
                + "join khach_hang on hoa_don.id_khach_hang = khach_hang.id_khach_hang \n"
                + "                join nhan_vien on hoa_don.id_nhan_vien = nhan_vien.id_nhan_vien \n"
                + "                join vourcher on hoa_don.id_voucher = vourcher.id_voucher\n"
                + "				where hoa_don.created_at like N'%" + txt_timkiem.getText() + "%' \n"
                + "				or hoa_don.ngay_thanh_toan like N'%" + txt_timkiem.getText() + "%'\n"
                + "				or khach_hang.ho_ten like N'%" + txt_timkiem.getText() + "%' \n"
                + "				or hoa_don.tong_tien = \n" + gia
                + "				or hoa_don.hinh_thuc_thanh_toan like N'%" + txt_timkiem.getText() + "%'\n"
                + "				or hoa_don.trang_thai = ?\n"
                + "				or hoa_don.id_hoa_don = ? \n"
                + "				or nhan_vien.ho_ten  like N'%" + txt_timkiem.getText() + "%'";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HoaDon list = new HoaDon();
                KhachHang khachHang = new KhachHang();
                NhanVien1 nhanVien = new NhanVien1();
                Vourcher vourcher = new Vourcher();

                list.setId_hoa_don(rs.getInt(1));
                list.setCreated_at(rs.getDate(2));
                list.setNgay_thanh_toan(rs.getDate(3));

                khachHang.setHo_ten(rs.getString(4));
                khachHang.setId_khach_hang(rs.getInt(5));
                list.setId_khach_hang(khachHang);

                list.setId_khach_hang(khachHang);
                list.setTong_tien(rs.getFloat(6));
                list.setHinh_thuc_thanh_toan(rs.getString(7));
                list.setTrang_thai(rs.getInt(8));

                nhanVien.setHo_ten(rs.getString(9));
                nhanVien.setId_nhan_vien(rs.getInt(10));
                list.setId_nhan_vien(nhanVien);

                vourcher.setKhuyen_mai(rs.getInt(11));
                vourcher.setId_voucher(rs.getInt(12));
                list.setId_voucher(vourcher);

                hoaDons.add(list);

//                vourcher.setKhuyen_mai(rs.getInt(10));
//                list.setId_voucher(vourcher);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (HoaDon hv : hoaDons) {
            Object[] rowdata = new Object[]{
                i++,
                hv.getId_hoa_don(),
                hv.getCreated_at(),
                hv.getNgay_thanh_toan(),
                hv.getId_khach_hang().getHo_ten(),
                hv.getTong_tien(),
                hv.getHinh_thuc_thanh_toan(),
                hv.getTrang_thai() == 1 ? "da thanh toan" : "chua thanh toan",
                hv.getId_nhan_vien().getHo_ten(),
                hv.getId_voucher().getKhuyen_mai(),};
            dtm.addRow(rowdata);
        }


    }//GEN-LAST:event_txt_timkiemKeyReleased

    private void btn_nhoMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nhoMaxActionPerformed
        // TODO add your handling code here:
        trang = 1;
        loadData(trang);
        lbTrang.setText("1");
        lbSoTrang.setText("1/" + soTrang);
    }//GEN-LAST:event_btn_nhoMaxActionPerformed

    private void btn_nhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nhoActionPerformed
        // TODO add your handling code here:
        if (trang > 1) {
            trang--;
            loadData(trang);
            lbTrang.setText("" + trang);
            lbSoTrang.setText(trang + "/" + soTrang);
        }
    }//GEN-LAST:event_btn_nhoActionPerformed

    private void btn_lonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lonActionPerformed
        // TODO add your handling code here:
        if (trang < soTrang) {
            trang++;
            loadData(trang);
            lbTrang.setText("" + trang);
            lbSoTrang.setText(trang + "/" + soTrang);
        }
    }//GEN-LAST:event_btn_lonActionPerformed

    private void btn_lonMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lonMaxActionPerformed
        // TODO add your handling code here:
        trang = soTrang;
        loadData(trang);
        lbTrang.setText("" + soTrang);
        lbSoTrang.setText(soTrang + "/" + soTrang);
    }//GEN-LAST:event_btn_lonMaxActionPerformed

    private void btn_PDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_PDFActionPerformed
        // TODO add your handling code here:
        MessageFormat header = new MessageFormat("Form title");
        MessageFormat footer = new MessageFormat("(0, number, interger)");
        try {
            PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
            set.add(OrientationRequested.LANDSCAPE);
            tb_hoaDon.print(JTable.PrintMode.FIT_WIDTH, header, footer, true, set, true);
            JOptionPane.showMessageDialog(null, "Printf succefully");
        } catch (java.awt.print.PrinterException e) {
            JOptionPane.showMessageDialog(null, "failed");
        }
    }//GEN-LAST:event_btn_PDFActionPerformed

    private void tb_hoaDon2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_hoaDon2MouseClicked
        DefaultTableModel dtm = (DefaultTableModel) this.tb_hoadonchitiet1.getModel();
        dtm.setRowCount(0);
        int row = this.tb_hoaDon2.getSelectedRow();
        if (row == -1) {
            return;
        }
        int id = Integer.parseInt(tb_hoaDon2.getValueAt(row, 0).toString());
        HoaDonChiTietViewModel hv = this.donService.getListHoaDonChiietById(id);
        dtm.addRow(new Object[]{
            i++,
            hv.getId_chi_tiet_san_pham(),
            hv.getId_san_pham().getTen(),
            hv.getId_mau_sac().getTen(),
            hv.getId_hang().getTen(),
            hv.getId_size().getTen(),
            hv.getSo_luong(),
            hv.getGia(),
            hv.getId_hoa_don().getTrang_thai() == 1 ? "da thanh toan" : "chua thanh toan"
        });

        lb1.setText(tb_hoaDon2.getValueAt(row, 1).toString());
        jd_ngaytao.setDate((Date) tb_hoaDon2.getValueAt(row, 2));
        jd_ngaythanhtoan.setDate((Date) tb_hoaDon2.getValueAt(row, 3));
        lb2.setText(tb_hoaDon2.getValueAt(row, 4).toString());
        lb3.setText(tb_hoaDon2.getValueAt(row, 5).toString());
        cbb_thanhtoan1.setSelectedItem(tb_hoaDon2.getValueAt(row, 6));
        if (tb_hoaDon2.getValueAt(row, 7).toString().equalsIgnoreCase("da thanh toan")) {
            rd_dathanhtoan.setSelected(true);
        } else {
            rd_chuathanhtoan.setSelected(true);
        }


    }//GEN-LAST:event_tb_hoaDon2MouseClicked

    private void txt_timkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_timkiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_timkiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_PDF;
    private javax.swing.JButton btn_lon;
    private javax.swing.JButton btn_lonMax;
    private javax.swing.JButton btn_nho;
    private javax.swing.JButton btn_nhoMax;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbb_thanhtoan;
    private javax.swing.JComboBox<String> cbb_thanhtoan1;
    private javax.swing.JComboBox<String> cbb_tim;
    private javax.swing.JComboBox<String> cbb_trangthaihoadon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.toedter.calendar.JDateChooser jd_ngaytao;
    private com.toedter.calendar.JDateChooser jd_ngaythanhtoan;
    private javax.swing.JLabel lb1;
    private javax.swing.JLabel lb2;
    private javax.swing.JLabel lb3;
    private javax.swing.JLabel lbSoTrang;
    private javax.swing.JLabel lbTrang;
    private javax.swing.JRadioButton rd_chuathanhtoan;
    private javax.swing.JRadioButton rd_dathanhtoan;
    private javax.swing.JTable tb_hoaDon;
    private javax.swing.JTable tb_hoaDon2;
    private javax.swing.JTable tb_hoadonchitiet;
    private javax.swing.JTable tb_hoadonchitiet1;
    private javax.swing.JTextField txt_timkiem;
    private javax.swing.JTextField txt_timkiemnhieu;
    // End of variables declaration//GEN-END:variables
}
