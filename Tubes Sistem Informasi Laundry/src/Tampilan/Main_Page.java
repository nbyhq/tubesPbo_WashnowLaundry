/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Tampilan;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.Normalizer;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Nawa
 */
public class Main_Page extends javax.swing.JFrame implements LaundryCRUD{
    
// Implementasi metode createData (INSERT)
    @Override
    public void createData() {
        String url = "INSERT INTO data_laundry VALUES(?,?,?,?,?,?,?)";
        if (txt_harga.getText().isEmpty() || txt_berat.getText().isEmpty() || txt_totalHarga.getText().isEmpty() || txt_bayar.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Mohon Isi Semua Kolom Terlebih Dahulu");
        } else {
            int bayar = Integer.parseInt(txt_bayar.getText());
            int kembalian = bayar - Total_Harga;

         
            if (bayar < Total_Harga) {
            JOptionPane.showMessageDialog(null, "Uang Tidak Cukup!\nHarap periksa jumlah uang yang dibayarkan.");
        } else {
            try {
                PreparedStatement stat = (PreparedStatement) conn.prepareStatement(url);
                stat.setString(1, null);
                stat.setString(2, String.valueOf(combo_jenis.getSelectedItem()));
                stat.setString(3, txt_harga.getText());
                stat.setString(4, txt_berat.getText());
                stat.setString(5, txt_totalHarga.getText());
                stat.setString(6, txt_bayar.getText());
                stat.setString(7, String.valueOf(kembalian));
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Kembalian Anda:\nRp. " + kembalian);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Transaksi Gagal " + e);
            }
            }
            readData();
            reset();
        }
    }

    
// Implementasi metode readData (SELECT)
    @Override
    public void readData() {
        String[] Data = {"ID Transaksi", "Jenis Cuci", "Harga/Kg", "Berat (Kg)", "Total Harga", "Bayar"};
        tabmodel = new DefaultTableModel(null, Data);
        tabel_data.setModel(tabmodel);
        String sql = "SELECT * FROM data_laundry";

        try {
            Statement stat = (Statement) conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String FieldId = String.valueOf(hasil.getInt("field_id"));
                String JenisCuci = hasil.getString("jenis_cuci");
                String Harga = hasil.getString("harga");
                String Berat = hasil.getString("berat");
                String totalHarga = hasil.getString("total_harga");
                String Bayar = hasil.getString("bayar");
                String[] DataLaundry = {FieldId, JenisCuci, Harga, Berat, totalHarga, Bayar};
                tabmodel.addRow(DataLaundry);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Normalizer.Form.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
// Implementasi metode updateData (UPDATE)
    @Override
    public void updateData() {
    String JenisCuci = combo_jenis.getSelectedItem().toString();
    String Harga = txt_harga.getText();
    String Berat = txt_berat.getText();
    String TotalHarga = txt_totalHarga.getText();
    String Bayar = txt_bayar.getText();

    
// Cek apakah ada kolom yang kosong
    if (Harga.isEmpty() || Berat.isEmpty() || TotalHarga.isEmpty() || Bayar.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Lengkapi kolom terlebih dahulu");
    } else {
        
        // Kalkulasi kembalian
        int totalHarga = Integer.parseInt(TotalHarga);
        int bayar = Integer.parseInt(Bayar);
        int Kembalian = bayar - totalHarga;

        // Cek apakah uang bayar mencukupi
        if (bayar < totalHarga) {
            JOptionPane.showMessageDialog(null, "Uang Tidak Cukup!\nHarap periksa jumlah uang yang dimasukkan.");
        } else {
            // Jika uang cukup, lakukan proses update
            String sql = "UPDATE data_laundry SET jenis_cuci='" + JenisCuci + "',harga='" + Harga + "',berat='" + Berat + "',total_harga='" + TotalHarga + "',bayar='" + Bayar + "',kembalian='" + Kembalian + "' WHERE field_id=" + field_id;
            try {
                PreparedStatement stat = (PreparedStatement) conn.prepareStatement(sql);
                stat.executeUpdate();

                JOptionPane.showMessageDialog(null, "Data berhasil diupdate\nKembalian Anda: Rp. " + Kembalian);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Update Gagal: " + e.getMessage());
            }
            readData();
            reset();
            field_id = 0;
        }
    }
    }
        
// Implementasi metode deleteData (DELETE)
    @Override
    public void deleteData() {
        if (field_id == 0) {
            JOptionPane.showMessageDialog(null, "Pilih data yang akan dihapus terlebih dahulu");
        } else {
            String sql = "DELETE FROM data_laundry WHERE field_id=" + field_id + "";
            try {
                PreparedStatement stat = (PreparedStatement) conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
            } catch (SQLException e) {
                Logger.getLogger("Data gagal dihapus " + e);
            }
            readData();
            reset();
        }
    }
    /**
     * Creates new form Main_Page
     */
    public Main_Page() {
        initComponents();
        
        Tampilkan_Data();
        TampilkanNama();
    }
    
    private Connection conn = (Connection) new Koneksi().Connect();
    private DefaultTableModel tabmodel;
    
    public int Total_Harga;
    public int field_id;
    
    public static String username;
    
    public void TampilkanNama(){
        txt_user.setText(username);
    }
    
    public void reset(){
        combo_jenis.setSelectedIndex(0);
        txt_harga.setText("");
        txt_berat.setText("");
        txt_bayar.setText("");
        txt_totalHarga.setText("");
        field_id = 0;
    }
    
    public void Tampilkan_Data(){
        String[] Data = {"ID Transaksi","Jenis Cuci","Harga/Kg","Berat (Kg)","Total Harga","Bayar"};
        tabmodel = new DefaultTableModel(null, Data);
        tabel_data.setModel(tabmodel);
        String sql = "SELECT * FROM data_laundry";

        try {
            Statement stat = (Statement) conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {                
                String FieldId = String.valueOf(hasil.getInt("field_id"));
                String JenisCuci = hasil.getString("jenis_cuci");
                String Harga = hasil.getString("harga");
                String Berat = hasil.getString("berat");
                String totalHarga = hasil.getString("total_harga");
                String Bayar = hasil.getString("bayar");
                String[] DataLaundry = {FieldId,JenisCuci,Harga,Berat,totalHarga,Bayar};
                tabmodel.addRow(DataLaundry);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Normalizer.Form.class.getName()).log(Level.SEVERE, null, ex);
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
        logo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_data = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        t_jenisCuci = new javax.swing.JLabel();
        t_hargaKg = new javax.swing.JLabel();
        t_berat = new javax.swing.JLabel();
        t_totalHarga = new javax.swing.JLabel();
        t_totalBayar = new javax.swing.JLabel();
        txt_berat = new javax.swing.JTextField();
        combo_jenis = new javax.swing.JComboBox<>();
        txt_harga = new javax.swing.JTextField();
        txt_totalHarga = new javax.swing.JTextField();
        txt_bayar = new javax.swing.JTextField();
        btn_batal = new javax.swing.JButton();
        btn_bayar = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        t_hallo = new javax.swing.JLabel();
        txt_user = new javax.swing.JLabel();
        btn_logout = new javax.swing.JButton();
        txt_search = new javax.swing.JTextField();
        btn_search = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        logo.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        logo.setForeground(new java.awt.Color(0, 0, 0));
        logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/wash.Now.png"))); // NOI18N

        tabel_data.setAutoCreateRowSorter(true);
        tabel_data.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 0));
        tabel_data.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabel_data.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_dataMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_data);

        jPanel2.setBackground(new java.awt.Color(10, 87, 162));
        jPanel2.setForeground(new java.awt.Color(10, 87, 162));

        t_jenisCuci.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        t_jenisCuci.setForeground(new java.awt.Color(255, 255, 255));
        t_jenisCuci.setText("Jenis Cuci");

        t_hargaKg.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        t_hargaKg.setForeground(new java.awt.Color(255, 255, 255));
        t_hargaKg.setText("Harga/Kg");

        t_berat.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        t_berat.setForeground(new java.awt.Color(255, 255, 255));
        t_berat.setText("Berat (Kg)");

        t_totalHarga.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        t_totalHarga.setForeground(new java.awt.Color(255, 255, 255));
        t_totalHarga.setText("Total Harga (Rp)");

        t_totalBayar.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        t_totalBayar.setForeground(new java.awt.Color(255, 255, 255));
        t_totalBayar.setText("Total Bayar (Rp)");

        txt_berat.setBackground(new java.awt.Color(255, 255, 255));
        txt_berat.setForeground(new java.awt.Color(0, 0, 0));
        txt_berat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_beratActionPerformed(evt);
            }
        });
        txt_berat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_beratKeyReleased(evt);
            }
        });

        combo_jenis.setBackground(new java.awt.Color(255, 255, 255));
        combo_jenis.setForeground(new java.awt.Color(0, 0, 0));
        combo_jenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Jenis Cuci", "Cuci Basah", "Cuci Kering", "Cuci Setrika" }));
        combo_jenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_jenisActionPerformed(evt);
            }
        });

        txt_harga.setBackground(new java.awt.Color(255, 255, 255));
        txt_harga.setForeground(new java.awt.Color(0, 0, 0));
        txt_harga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hargaActionPerformed(evt);
            }
        });

        txt_totalHarga.setBackground(new java.awt.Color(255, 255, 255));
        txt_totalHarga.setForeground(new java.awt.Color(0, 0, 0));
        txt_totalHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalHargaActionPerformed(evt);
            }
        });

        txt_bayar.setBackground(new java.awt.Color(255, 255, 255));
        txt_bayar.setForeground(new java.awt.Color(0, 0, 0));
        txt_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bayarActionPerformed(evt);
            }
        });

        btn_batal.setBackground(new java.awt.Color(108, 117, 125));
        btn_batal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_batal.setForeground(new java.awt.Color(0, 0, 0));
        btn_batal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/cancel.png"))); // NOI18N
        btn_batal.setText("Batal");
        btn_batal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });

        btn_bayar.setBackground(new java.awt.Color(40, 209, 29));
        btn_bayar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_bayar.setForeground(new java.awt.Color(255, 255, 255));
        btn_bayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/payment.png"))); // NOI18N
        btn_bayar.setText("Bayar");
        btn_bayar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bayarActionPerformed(evt);
            }
        });

        btn_delete.setBackground(new java.awt.Color(255, 0, 0));
        btn_delete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_delete.setForeground(new java.awt.Color(255, 255, 255));
        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/delete.png"))); // NOI18N
        btn_delete.setText("Delete");
        btn_delete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_update.setBackground(new java.awt.Color(0, 123, 255));
        btn_update.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_update.setForeground(new java.awt.Color(255, 255, 255));
        btn_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/update.png"))); // NOI18N
        btn_update.setText("Update");
        btn_update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(t_jenisCuci, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(t_berat, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(t_totalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(t_hargaKg, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(t_totalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combo_jenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txt_harga, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_berat, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_totalHarga, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_bayar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_update)
                        .addGap(18, 18, 18)
                        .addComponent(btn_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_jenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t_jenisCuci))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t_hargaKg))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(t_berat)
                    .addComponent(txt_berat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_totalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t_totalHarga))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t_totalBayar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34))
        );

        jPanel3.setBackground(new java.awt.Color(10, 87, 162));

        t_hallo.setBackground(new java.awt.Color(255, 255, 255));
        t_hallo.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        t_hallo.setForeground(new java.awt.Color(255, 255, 255));
        t_hallo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        t_hallo.setText("Hallo");

        txt_user.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txt_user.setForeground(new java.awt.Color(255, 255, 255));
        txt_user.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        btn_logout.setBackground(new java.awt.Color(255, 255, 255));
        btn_logout.setForeground(new java.awt.Color(255, 255, 255));
        btn_logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/logout.png"))); // NOI18N
        btn_logout.setBorder(null);
        btn_logout.setContentAreaFilled(false);
        btn_logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(t_hallo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_user, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(353, 353, 353)
                .addComponent(btn_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_logout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_user, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(t_hallo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7))
        );

        txt_search.setBackground(new java.awt.Color(255, 255, 255));
        txt_search.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        txt_search.setForeground(new java.awt.Color(0, 0, 0));
        txt_search.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_search.setText("cari data");
        txt_search.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_searchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_searchFocusLost(evt);
            }
        });
        txt_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_searchActionPerformed(evt);
            }
        });

        btn_search.setBackground(new java.awt.Color(255, 255, 255));
        btn_search.setForeground(new java.awt.Color(255, 255, 255));
        btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/search.png"))); // NOI18N
        btn_search.setBorder(null);
        btn_search.setBorderPainted(false);
        btn_search.setContentAreaFilled(false);
        btn_search.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(logo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_search)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addGap(52, 52, 52))
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
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bayarActionPerformed

    private void txt_totalHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalHargaActionPerformed

    private void txt_hargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hargaActionPerformed

    private void combo_jenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_jenisActionPerformed
        // TODO add your handling code here:
        if(combo_jenis.getSelectedItem().equals("Pilih Jenis Cuci")){
        txt_harga.setText("0");
        txt_harga.setEditable(false); // Nonaktifkan input
        txt_totalHarga.setEditable(false);
    } else if(combo_jenis.getSelectedItem().equals("Cuci Basah")){
        txt_harga.setText("10000");
        txt_harga.setEditable(false); // Nonaktifkan input
        txt_totalHarga.setEditable(false);
    } else if(combo_jenis.getSelectedItem().equals("Cuci Kering")){
        txt_harga.setText("15000");
        txt_harga.setEditable(false); // Nonaktifkan input
        txt_totalHarga.setEditable(false);
    } else if(combo_jenis.getSelectedItem().equals("Cuci Setrika")){
        txt_harga.setText("20000");
        txt_harga.setEditable(false); // Nonaktifkan input
        txt_totalHarga.setEditable(false);
    }

    }//GEN-LAST:event_combo_jenisActionPerformed

    private void txt_beratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_beratActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_beratActionPerformed

    private void txt_beratKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_beratKeyReleased
        // TODO add your handling code here:
        if (txt_berat.getText().equals("")){
            txt_totalHarga.setText("0");
        } else{
            int harga = Integer.parseInt(txt_harga.getText());
            int berat = Integer.parseInt(txt_berat.getText());
            Total_Harga = harga * berat;
            txt_totalHarga.setText(String.valueOf(Total_Harga));
        }
    }//GEN-LAST:event_txt_beratKeyReleased

    private void btn_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bayarActionPerformed
        // TODO add your handling code here:
        createData();
    }//GEN-LAST:event_btn_bayarActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        // TODO add your handling code here:
        updateData();
    }//GEN-LAST:event_btn_updateActionPerformed

    private void tabel_dataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_dataMouseClicked
        // TODO add your handling code here:
        combo_jenis.setSelectedItem(tabel_data.getValueAt(tabel_data.getSelectedRow(), 1));
        txt_harga.setText((String) tabel_data.getValueAt(tabel_data.getSelectedRow(), 2));
        txt_berat.setText((String) tabel_data.getValueAt(tabel_data.getSelectedRow(), 3));
        txt_totalHarga.setText((String) tabel_data.getValueAt(tabel_data.getSelectedRow(), 4));
        txt_bayar.setText((String) tabel_data.getValueAt(tabel_data.getSelectedRow(), 5));

        field_id = Integer.parseInt((String)tabel_data.getValueAt(tabel_data.getSelectedRow(), 0));
    }//GEN-LAST:event_tabel_dataMouseClicked

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        deleteData();
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
        // TODO add your handling code here:
        int response = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin meninggalkan halaman ini?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            new Login_Page().setVisible(true);
            dispose();
            JOptionPane.showMessageDialog(null, "Anda berhasil keluar");
        }else if (response == JOptionPane.NO_OPTION) {
            
        }
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed
        // TODO add your handling code here:
        
        // Ambil kata kunci dari txt_search
        String keyword = txt_search.getText();
        String[] Data = {"ID Transaksi", "Jenis Cuci", "Harga/Kg", "Berat (Kg)", "Total Harga", "Bayar"};
        tabmodel = new DefaultTableModel(null, Data);
        tabel_data.setModel(tabmodel);

        // Query pencarian
        String sql = "SELECT * FROM data_laundry WHERE jenis_cuci LIKE ? OR harga LIKE ? OR berat LIKE ?";

        try {
            PreparedStatement stat = (PreparedStatement) conn.prepareStatement(sql);
            stat.setString(1, "%" + keyword + "%");
            stat.setString(2, "%" + keyword + "%");
            stat.setString(3, "%" + keyword + "%");

            ResultSet hasil = stat.executeQuery();
            while (hasil.next()) {
                String FieldId = String.valueOf(hasil.getInt("field_id"));
                String JenisCuci = hasil.getString("jenis_cuci");
                String Harga = hasil.getString("harga");
                String Berat = hasil.getString("berat");
                String totalHarga = hasil.getString("total_harga");
                String Bayar = hasil.getString("bayar");
                String[] DataLaundry = {FieldId, JenisCuci, Harga, Berat, totalHarga, Bayar};
                tabmodel.addRow(DataLaundry);
            }

            // Pesan jika tidak ada data ditemukan
            if (tabmodel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Pencarian Gagal: " + ex.getMessage());
        }
    }//GEN-LAST:event_txt_searchActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        // TODO add your handling code here:
        String keyword = txt_search.getText();
        
        if (keyword.equals("") || keyword.equalsIgnoreCase("cari data")) {
        txt_search.setText("cari data");
        JOptionPane.showMessageDialog(null, "Masukkan kata kunci untuk mencari data!");
        return;
    }
        
        // Ambil kata kunci dari txt_search
        String[] Data = {"ID Transaksi", "Jenis Cuci", "Harga/Kg", "Berat (Kg)", "Total Harga", "Bayar"};
        tabmodel = new DefaultTableModel(null, Data);
        tabel_data.setModel(tabmodel);

        // Query pencarian
        String sql = "SELECT * FROM data_laundry WHERE "
               + "field_id LIKE ? OR "
               + "jenis_cuci LIKE ? OR "
               + "harga LIKE ? OR "
               + "berat LIKE ? OR "
               + "total_harga LIKE ? OR "
               + "bayar LIKE ?";

        try {
            PreparedStatement stat = (PreparedStatement) conn.prepareStatement(sql);
            String searchPattern = "%" + keyword + "%";
            stat.setString(1, searchPattern); // Untuk field_id
            stat.setString(2, searchPattern); // Untuk jenis_cuci
            stat.setString(3, searchPattern); // Untuk harga
            stat.setString(4, searchPattern); // Untuk berat
            stat.setString(5, searchPattern); // Untuk total_harga
            stat.setString(6, searchPattern); // Untuk bayar

            ResultSet hasil = stat.executeQuery();
            boolean dataFound = false;

            while (hasil.next()) {
                dataFound = true;
                String FieldId = String.valueOf(hasil.getInt("field_id"));
                String JenisCuci = hasil.getString("jenis_cuci");
                String Harga = hasil.getString("harga");
                String Berat = hasil.getString("berat");
                String totalHarga = hasil.getString("total_harga");
                String Bayar = hasil.getString("bayar");
                String[] DataLaundry = {FieldId, JenisCuci, Harga, Berat, totalHarga, Bayar};
                tabmodel.addRow(DataLaundry);
            }

            if (!dataFound) {
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Pencarian Gagal: " + ex.getMessage());
        }
    }//GEN-LAST:event_btn_searchActionPerformed

    private void txt_searchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_searchFocusGained
        // TODO add your handling code here:
        String keyword = txt_search.getText();
        if (keyword.equals("") || keyword.equals("cari data")) {
            txt_search.setText("");
        }
    }//GEN-LAST:event_txt_searchFocusGained

    private void txt_searchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_searchFocusLost
        // TODO add your handling code here:
        String keyword = txt_search.getText();
        if (keyword.equals("") || keyword.equals("cari data")) {
            txt_search.setText("cari data");
        }
    }//GEN-LAST:event_txt_searchFocusLost
        
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
            java.util.logging.Logger.getLogger(Main_Page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main_Page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main_Page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main_Page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main_Page().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_bayar;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_search;
    private javax.swing.JButton btn_update;
    private javax.swing.JComboBox<String> combo_jenis;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel logo;
    private javax.swing.JLabel t_berat;
    private javax.swing.JLabel t_hallo;
    private javax.swing.JLabel t_hargaKg;
    private javax.swing.JLabel t_jenisCuci;
    private javax.swing.JLabel t_totalBayar;
    private javax.swing.JLabel t_totalHarga;
    private javax.swing.JTable tabel_data;
    private javax.swing.JTextField txt_bayar;
    private javax.swing.JTextField txt_berat;
    private javax.swing.JTextField txt_harga;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_totalHarga;
    private javax.swing.JLabel txt_user;
    // End of variables declaration//GEN-END:variables

  
    
}
