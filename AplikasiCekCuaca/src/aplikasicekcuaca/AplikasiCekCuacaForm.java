/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aplikasicekcuaca;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class AplikasiCekCuacaForm extends javax.swing.JFrame {
    private static final String API_KEY = "252d19956172e85f3aa0801c2073ff9e";
    private DefaultTableModel tableModel;
    private javax.swing.JLabel jLabelIcon;

    public AplikasiCekCuacaForm() {
        initComponents();
        setupTable();
        setListeners();
        loadWeatherDataFromCSV();
        loadFavoritesFromFile();
        
        
    }

    // Mengatur tabel untuk menampilkan data cuaca
    private void setupTable() {
        tableModel = new DefaultTableModel(new String[]{"Kota", "Cuaca", "Suhu (°C)", "Deskripsi"}, 0);
        jTable1.setModel(tableModel);
    }

    // Menambahkan ActionListeners untuk tombol dan komponen lain
   // Menambahkan ActionListeners untuk tombol dan komponen lain
private void setListeners() {
    jButtoncek.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            String city = jTextFieldinput.getText().trim();
            if (city.isEmpty()) {
                JOptionPane.showMessageDialog(AplikasiCekCuacaForm.this, "Masukkan nama kota terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            } else {
                fetchWeatherData(city);
            }
        }
    });

    jButtonfavorit.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            String city = jTextFieldinput.getText();
            if (!city.isEmpty()) {
                jComboBoxfavorit.addItem(city);
                saveFavoritesToFile();
            }
        }

        private void saveFavoritesToFile() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    });

  jButtonsave.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
        saveTableDataToCSV();
    }
});


    // Menambahkan ActionListener untuk tombol hapus
    jButtonhapus.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            removeSelectedRow();
        }
    });

    jComboBoxfavorit.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            String selectedCity = (String) jComboBoxfavorit.getSelectedItem();
            if (!selectedCity.equals("Kota Favorit")) {
                fetchWeatherData(selectedCity);
            }
        }
    });
}

 // Mengambil data cuaca dari API OpenWeatherMap
private void fetchWeatherData(String city) {
    try {
        String urlStr = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY + "&units=metric";
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            Scanner scanner = new Scanner(url.openStream());
            String response = scanner.useDelimiter("\\Z").next();
            scanner.close();

            JSONObject jsonObject = new JSONObject(response);
            String weatherMain = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            String description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
            double temp = jsonObject.getJSONObject("main").getDouble("temp");

            // Ambil kode ikon dari respons API
            String iconCode = jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");

            // Tampilkan data cuaca dan ikon
            displayWeather(city, weatherMain, temp, description, iconCode);
        } else {
            // Hanya tampilkan pesan kesalahan jika respons bukan HTTP_OK
            JOptionPane.showMessageDialog(this, "Kota tidak ditemukan atau ada masalah dengan API.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (IOException e) {
        // Tangkap IOException dan tampilkan pesan untuk masalah jaringan
        JOptionPane.showMessageDialog(this, "Kesalahan jaringan, cek koneksi internet Anda.", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error mengambil data cuaca.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


   

    
   
    // Memuat data dari file CSV saat aplikasi dibuka
private void loadWeatherDataFromCSV() {
    try (BufferedReader reader = new BufferedReader(new FileReader("weather_data.csv"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            tableModel.addRow(data);
        }
    } catch (IOException e) {
        System.out.println("Tidak ada data CSV yang ditemukan atau error saat memuat.");
    }
}
// Memuat daftar kota favorit dari file saat aplikasi dibuka
private void loadFavoritesFromFile() {
    try (BufferedReader reader = new BufferedReader(new FileReader("favorites.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            jComboBoxfavorit.addItem(line);
        }
    } catch (IOException e) {
        System.out.println("Tidak ada kota favorit yang ditemukan.");
    }
}
// Menyimpan data tabel ke file CSV
// Fungsi untuk menyimpan data tabel ke file CSV
// Fungsi untuk menyimpan data tabel ke file CSV
private void saveTableDataToCSV() {
    // Tentukan jalur folder tujuan dan nama file
    String folderPath = "C:\\DataCuaca";
    String filePath = folderPath + "\\weather_data.csv";

    // Buat objek File untuk folder
    File folder = new File(folderPath);

    // Periksa apakah folder ada; jika tidak, buat foldernya
    if (!folder.exists()) {
        folder.mkdirs(); // Membuat folder beserta subfoldernya jika diperlukan
    }

    // Simpan data ke file CSV di lokasi yang diinginkan
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) { // Menggunakan filePath di sini
        // Tulis header kolom (opsional)
        writer.write("Kota,Cuaca,Suhu (°C),Deskripsi");
        writer.newLine();

        // Menyimpan data dari tableModel ke dalam file CSV
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String kota = tableModel.getValueAt(i, 0).toString();
            String cuaca = tableModel.getValueAt(i, 1).toString();
            String suhu = tableModel.getValueAt(i, 2).toString();
            String deskripsi = tableModel.getValueAt(i, 3).toString();
            writer.write(kota + "," + cuaca + "," + suhu + "," + deskripsi);
            writer.newLine();
        }

        // Tampilkan pesan sukses
        JOptionPane.showMessageDialog(this, "Data berhasil disimpan ke " + filePath, "Sukses", JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException e) {
        // Tangani error jika terjadi masalah saat menyimpan
        JOptionPane.showMessageDialog(this, "Error menyimpan data ke CSV.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


// Menghapus baris yang dipilih di tabel
private void removeSelectedRow() {
    int selectedRow = jTable1.getSelectedRow();
    if (selectedRow != -1) {
        tableModel.removeRow(selectedRow);
        JOptionPane.showMessageDialog(this, "Baris berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, "Tidak ada baris yang dipilih.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
// Menampilkan data cuaca pada tabel, label, dan ikon
private void displayWeather(String city, String weatherMain, double temp, String description, String iconCode) {
    tableModel.addRow(new Object[]{city, weatherMain, temp, description});
    jLabelicon.setText("Cuaca di kota " + city + ": " + weatherMain + ", " + temp + "°C (" + description + ")");
    setWeatherIcon(iconCode);
}


// Mengunduh dan menampilkan ikon cuaca berdasarkan kode ikon dari API
private void setWeatherIcon(String iconCode) {
    try {
        String iconUrl = "http://openweathermap.org/img/wn/" + iconCode + "@2x.png";
        URL url = new URL(iconUrl);
        ImageIcon icon = new ImageIcon(url);
        jLabelicon.setIcon(icon); // Tampilkan ikon di jLabelicon
    } catch (Exception e) {
        e.printStackTrace();
        jLabelicon.setIcon(new ImageIcon("icons/default.png")); // Set ikon default jika terjadi kesalahan
    }
}



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldinput = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxfavorit = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtoncek = new javax.swing.JButton();
        jButtonfavorit = new javax.swing.JButton();
        jButtonsave = new javax.swing.JButton();
        jButtonhapus = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabelicon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel2.setBackground(new java.awt.Color(255, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("APLIKASI CEK CUACA");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(289, 289, 289)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel2.setText("Pilih Kota             :");

        jLabel3.setText("Pilih kota favorit :");

        jComboBoxfavorit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kota Favorit" }));
        jComboBoxfavorit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxfavoritActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButtoncek.setText("Cek");

        jButtonfavorit.setText("Tambah ke favorit");
        jButtonfavorit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonfavoritActionPerformed(evt);
            }
        });

        jButtonsave.setText("Save to Csv");

        jButtonhapus.setText("Hapus");
        jButtonhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonhapusActionPerformed(evt);
            }
        });

        jLabelicon.setText("Cuaca di kota :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabelicon)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabelicon)
                .addContainerGap(124, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldinput)
                            .addComponent(jComboBoxfavorit, 0, 554, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtoncek, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addComponent(jButtonfavorit)
                        .addGap(56, 56, 56)
                        .addComponent(jButtonhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81)
                        .addComponent(jButtonsave, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldinput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxfavorit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtoncek, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButtonhapus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonfavorit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonsave, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(25, 25, 25)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxfavoritActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxfavoritActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxfavoritActionPerformed

    private void jButtonhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonhapusActionPerformed
           // TODO add your handling code here:
    }//GEN-LAST:event_jButtonhapusActionPerformed
   
    private void jButtonfavoritActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonfavoritActionPerformed
   String selectedCity = (String) jComboBoxfavorit.getSelectedItem();
    if (selectedCity != null && !selectedCity.equals("Kota Favorit")) {
        fetchWeatherData(selectedCity);
            // TODO add your handling code here:
    }//GEN-LAST:event_jButtonfavoritActionPerformed
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AplikasiCekCuacaForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtoncek;
    private javax.swing.JButton jButtonfavorit;
    private javax.swing.JButton jButtonhapus;
    private javax.swing.JButton jButtonsave;
    private javax.swing.JComboBox<String> jComboBoxfavorit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelicon;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldinput;
    // End of variables declaration//GEN-END:variables

    
}
