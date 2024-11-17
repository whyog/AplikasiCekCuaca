# 🚀 Aplikasi GUI Cek Cuaca Sederhana

Selamat datang di proyek ini! 🎉 Proyek ini adalah aplikasi GUI sederhana yang terintegrasi dengan API cuaca eksternal (misalnya OpenWeatherMap) untuk menampilkan data cuaca real-time berdasarkan lokasi yang dipilih. Aplikasi ini juga mendukung fitur penyimpanan data cuaca dan lokasi favorit untuk pengalaman pengguna yang lebih baik.

---

## 👨‍💻 Tentang Saya

Halo! Nama saya **Nur Yoga Andika** 👋  
- 📚 **NPM:** 2210010652  
- 🎓 **Jurusan:** Teknologi Informasi  
- 🌟 Saya selalu berusaha belajar hal-hal baru dan berbagi apa yang saya pelajari.  

💬 Jangan ragu untuk menghubungi saya untuk berdiskusi lebih lanjut tentang proyek ini atau topik lainnya! 🚀  

---

## 📝 Deskripsi Program

Aplikasi ini memungkinkan pengguna:
1. Memasukkan **nama kota** atau memilih kota dari **JComboBox** untuk memeriksa cuaca.
2. Menekan tombol:
   - **Cek Cuaca:** Untuk menampilkan data cuaca terkini, termasuk suhu, kondisi cuaca, dan ikon cuaca.
   - **Simpan:** Untuk menyimpan data cuaca ke dalam file CSV atau teks.
   - **Muat Data:** Untuk memuat data cuaca yang telah disimpan dan menampilkannya dalam tabel.
3. Menyimpan kota yang sering dicek ke dalam daftar lokasi favorit untuk akses cepat.

Aplikasi ini juga menyediakan fitur tambahan seperti:
- Menampilkan ikon cuaca (cerah, berawan, hujan, dll.) berdasarkan data cuaca.
- Mendukung penyimpanan data cuaca yang dapat diakses kembali.

---

## 💻 Komponen GUI

Berikut adalah komponen utama yang digunakan dalam aplikasi ini:
- **JFrame:** Sebagai jendela utama aplikasi.
- **JPanel:** Mengatur tata letak komponen GUI.
- **JLabel:** Menampilkan label teks dan ikon cuaca.
- **JTextField:** Untuk memasukkan nama kota.
- **JComboBox:** Untuk memilih kota dari daftar lokasi favorit.
- **JButton:** Untuk melakukan tindakan seperti cek cuaca, simpan, dan muat data.
- **JTable:** Untuk menampilkan data cuaca yang telah disimpan.

---

## 🔍 Logika Program

1. **Integrasi dengan API Cuaca Eksternal:**
   - Menggunakan API seperti OpenWeatherMap untuk mendapatkan data cuaca real-time berdasarkan lokasi.
   - Data meliputi suhu, kondisi cuaca, dan ikon cuaca.

2. **Penampilan Gambar:**
   - Menampilkan ikon cuaca (misalnya, matahari untuk cerah, awan untuk berawan) berdasarkan kondisi cuaca yang diterima dari API.

3. **Penyimpanan dan Pemrosesan Data:**
   - Menyimpan data cuaca dalam format CSV atau teks.
   - Memuat data cuaca yang tersimpan dan menampilkannya di `JTable`.

---

## 🎯 Events yang Diimplementasikan

1. **ActionListener:**  
   - Untuk tombol **Cek Cuaca**, mengambil data cuaca dari API eksternal.  
   - Untuk tombol **Simpan**, menyimpan data cuaca ke dalam file.
   - Untuk tombol **Muat Data**, memuat data cuaca yang telah disimpan.

2. **ItemListener:**  
   - Menggunakan `JComboBox` untuk memilih kota dari daftar lokasi favorit.

---

## ✨ Variasi

1. Kemampuan untuk menyimpan kota ke dalam daftar lokasi favorit:
   - Memungkinkan pengguna untuk memilih lokasi favorit dari `JComboBox`.
2. Penyimpanan data cuaca:
   - Menyimpan data cuaca dalam format CSV atau teks agar dapat diakses kembali.
3. Riwayat cuaca:
   - Memuat data cuaca yang telah disimpan dan menampilkannya di `JTable`.

---

## 🔧 Cara Menggunakan Program

1. Masukkan **nama kota** di `JTextField` atau pilih kota dari `JComboBox`.
2. Tekan tombol:
   - **Cek Cuaca:** Untuk melihat data cuaca terkini, termasuk suhu, kondisi cuaca, dan ikon cuaca.
   - **Simpan:** Untuk menyimpan data cuaca ke file.
   - **Muat Data:** Untuk memuat data cuaca yang telah disimpan dan menampilkannya di tabel.
3. Tambahkan kota ke daftar lokasi favorit untuk akses lebih cepat di masa mendatang.

---
