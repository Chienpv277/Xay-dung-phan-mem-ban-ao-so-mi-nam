/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.raven.form;

import DomainModel.HoaDon;
import DomainModel.HoaDonChiTiet;
import DomainModel.HoaDonViewModel;
import DomainModel.KhachHang1;
import DomainModel.Model_form;
import DomainModel.QuanLySanPham.ChiTietSanPham;
import Repository.HoaDonRepository;
import Service.KhachHangService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JOptionPane;

/**
 *
 * @author 84386
 */
public class KhachHangBanHang extends javax.swing.JFrame {

    DefaultTableModel dtm;
    ArrayList<KhachHang1> lst = new ArrayList<>();
    KhachHangService kh = new KhachHangService();
    ArrayList<KhachHang1> list = kh.getallkhachhang();
    private HoaDonRepository hoaDonRespon = new HoaDonRepository();
    public HoaDonViewModel hoadon = null;
    public KhachHang1 khach;
    Form_7 banHangfr;
    JTable tblGioHang;
    JTable tblHoaDon;

    /**
     * Creates new form KhachHangBanHang
     */
    public KhachHangBanHang(Form_7 banHangfr, JTable tblGioHang, JTable tblHoaDon) {
        initComponents();
        setLocationRelativeTo(this);
        this.tblGioHang = tblGioHang;
        this.tblHoaDon = tblHoaDon;
        this.banHangfr = banHangfr;

        dtm = (DefaultTableModel) tblkhachhang.getModel();
        LoadDataToTable();
    }

    public void loadtable() {
        int i = 1;
        DefaultTableModel dtm = (DefaultTableModel) this.tblHoaDon.getModel();
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

    public KhachHangBanHang() {
        initComponents();

        dtm = (DefaultTableModel) tblkhachhang.getModel();
        LoadDataToTable();
    }

    private void LoadDataToTable() {
        dtm.setRowCount(0);
        ArrayList<KhachHang1> lst = kh.getallkhachhang();
        System.out.println(lst);
        for (KhachHang1 k : lst) {
            dtm.addRow(new Object[]{k.getId(), k.getMa(), k.getTen(), k.isGioitinh() ? "nam" : "nữ", k.getNgaysinh(), k.getSdt(), k.getDiachi(), k.getEmail()});
        }
    }

    public void showdetail() {
        TableModel table = tblkhachhang.getModel();
        int index = tblkhachhang.getSelectedRow();

        txtma.setText(tblkhachhang.getValueAt(index, 1).toString());
        txthoten.setText(tblkhachhang.getValueAt(index, 2).toString());
        boolean gt = tblkhachhang.getValueAt(index, 3).toString().equalsIgnoreCase("nam") ? true : false;
        rdonam.setSelected(gt);
        rdonu.setSelected(!gt);
        try {
            int srow = tblkhachhang.getSelectedRow();
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse((String) table.getValueAt(srow, 4));
            txtngaysinh.setDate(date);
        } catch (Exception e) {
        }
        txtsdt.setText(tblkhachhang.getValueAt(index, 5).toString());
        txtdiachi.setText(tblkhachhang.getValueAt(index, 6).toString());
        txtemail.setText(tblkhachhang.getValueAt(index, 7).toString());
    }

    private KhachHang1 readform() {
        KhachHang1 k = new KhachHang1();
        String ngay = Model_form.layNgayString(txtngaysinh.getDate());
        k.setMa(txtma.getText());
        k.setTen(txthoten.getText());
        k.setGioitinh(rdonam.isSelected() ? true : false);
        k.setNgaysinh(ngay);
        k.setSdt(txtsdt.getText());
        k.setDiachi(txtdiachi.getText());
        k.setEmail(txtemail.getText());

        return k;
    }

    public void clear() {
        txtma.setText("");
        txthoten.setText("");
        rdonam.setSelected(true);
        txtngaysinh.setDateFormatString("");
        txtsdt.setText("");
        txtdiachi.setText("");
        txtemail.setText("");
    }

    public void fillter(String ma) {
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        tblkhachhang.setRowSorter(tr);
        if (ma != "tất cả") {
            tr.setRowFilter(RowFilter.regexFilter(ma));
        } else {
            tblkhachhang.setRowSorter(tr);
        }
    }

    public void loatdate(ArrayList<KhachHang1> list) {
        dtm = (DefaultTableModel) tblkhachhang.getModel();
        dtm.setRowCount(0);
        // int i = 0;
        for (KhachHang1 kh : list) {
            dtm.addRow(new Object[]{
                tblkhachhang.getRowCount(),
                kh.getMa(), kh.getTen(), kh.isGioitinh() ? "nam" : "nữ", kh.getNgaysinh(), kh.getSdt(), kh.getDiachi(), kh.getEmail()
            });
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblkhachhang = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtma = new javax.swing.JTextField();
        txthoten = new javax.swing.JTextField();
        rdonam = new javax.swing.JRadioButton();
        txtsdt = new javax.swing.JTextField();
        rdonu = new javax.swing.JRadioButton();
        txtngaysinh = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtemail = new javax.swing.JTextField();
        txtdiachi = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblkhachhang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "MÃ KH", "TÊN KH", "GIỚI TÍNH", "NGÀY SINH", "SỐ ĐIỆN THOẠI", "EMAIL", "ĐỊA CHỈ"
            }
        ));
        tblkhachhang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblkhachhangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblkhachhang);

        jTabbedPane4.addTab("tab1", jScrollPane2);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("thiết lập thông tin khách hàng"));

        jLabel1.setText("MÃ KH");

        jLabel2.setText("HỌ TÊN");

        jLabel3.setText("SDT");

        jLabel4.setText("NGÀY SINH");

        jLabel5.setText("GIỚI TÍNH");

        rdonam.setSelected(true);
        rdonam.setText("nam");
        rdonam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdonamActionPerformed(evt);
            }
        });

        txtsdt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsdtActionPerformed(evt);
            }
        });

        rdonu.setText("nữ");

        jLabel6.setText("EMAIL");

        jLabel7.setText("ĐỊA CHỈ");

        jButton1.setText("Them");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Sua");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Xoa");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtma, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                    .addComponent(txthoten))
                                .addGap(105, 105, 105))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtngaysinh, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                                    .addComponent(txtdiachi))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(jButton3))
                        .addGap(259, 259, 259))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel3))
                                .addGap(58, 58, 58)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtemail, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                                    .addComponent(txtsdt)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(37, 37, 37)
                                .addComponent(rdonam)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdonu)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txthoten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(33, 33, 33)
                                .addComponent(jLabel4))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(txtngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtdiachi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(rdonam)
                    .addComponent(rdonu))
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(txtsdt, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("tab2", jPanel3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 896, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 896, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 438, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblkhachhangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblkhachhangMouseClicked
//        showdetail();
        int row = tblkhachhang.getSelectedRow();
        if (row < 0) {
            return;
        }
        KhachHang1 khachHang1 = new KhachHang1();
        String makh = tblkhachhang.getValueAt(row, 0).toString();
        String idKH = "";
        ArrayList<KhachHang1> listKH = kh.getallkhachhang();
        for (KhachHang1 x : listKH) {
            if (String.valueOf(x.getId()) != null && String.valueOf(x.getId()).equals(makh)) {
                khachHang1 = x;
            }
        }
        int indexHD = tblHoaDon.getSelectedRow();
        String maHD = tblHoaDon.getValueAt(indexHD, 1).toString();

        HoaDonViewModel hd = new HoaDonViewModel();
        hd.setId_khach_hang(khachHang1);
        hd.setId_hoa_don(Integer.parseInt(maHD));
        if (JOptionPane.showConfirmDialog(this, "Ban co muon them khach hang khong?", "Thong Bao", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }

        System.out.println(hd.getId_hoa_don());
        System.out.println(hd.getId_khach_hang());
//        banHangfr.hoadon.setId_khach_hang(khachHang1);
        hoaDonRespon.updatehoadon_thanhtoan2(hd);
        System.out.println("run  : " + hoaDonRespon.updatehoadon_thanhtoan2(hd));
        if (hoaDonRespon.updatehoadon_thanhtoan2(hd) != null) {
            JOptionPane.showMessageDialog(this, "Cập nhập thông tin hóa đơn thành công");
//            banHangfr.loadChonKH(khach.getTen());

            banHangfr.loadChonKH(hd.getId_khach_hang().getTen());
            banHangfr.loadtableTrangThaiChuaThanhToan();
        }
        this.dispose();

//        loadtable();

    }//GEN-LAST:event_tblkhachhangMouseClicked

    private void rdonamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdonamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdonamActionPerformed

    private void txtsdtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsdtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsdtActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            int chon = JOptionPane.showConfirmDialog(this, "bạn muốn thêm ?");
            if (chon != JOptionPane.YES_OPTION) {
                return;
            }
            KhachHang1 k = readform();
            if (kh.addkhachhang(k) != null) {
                LoadDataToTable();
                JOptionPane.showMessageDialog(this, "lưu thành công");
            } else {
                JOptionPane.showMessageDialog(this, "không lưu được");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "lỗi&");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try {
            int chon = JOptionPane.showConfirmDialog(this, "bạn muốn sửa ?");
            if (chon != JOptionPane.YES_OPTION) {
                return;
            }
            KhachHang1 k = readform();
            if (kh.suakhachhang(k) != null) {
                LoadDataToTable();
                JOptionPane.showMessageDialog(this, "sửa thành công");
            } else {
                JOptionPane.showMessageDialog(this, "không sửa được");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "lỗi&");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try {
            int chon = JOptionPane.showConfirmDialog(this, "bạn muốn xóa");
            if (chon != JOptionPane.YES_OPTION) {
                return;
            }

            String ma = txtma.getText();
            if (kh.deletekhachhang(ma) != null) {
                LoadDataToTable();
                clear();
                JOptionPane.showMessageDialog(this, "xóa thành công");

            } else {
                JOptionPane.showMessageDialog(this, "không xóa được dòng nào");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "lỗi");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(KhachHangBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KhachHangBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KhachHangBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KhachHangBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KhachHangBanHang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JRadioButton rdonam;
    private javax.swing.JRadioButton rdonu;
    private javax.swing.JTable tblkhachhang;
    private javax.swing.JTextField txtdiachi;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txthoten;
    private javax.swing.JTextField txtma;
    private com.toedter.calendar.JDateChooser txtngaysinh;
    private javax.swing.JTextField txtsdt;
    // End of variables declaration//GEN-END:variables
}
