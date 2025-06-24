# TECH. – Ứng dụng Quản lý Cửa hàng Điện tử

Đây là một ứng dụng desktop viết bằng Java, được phát triển trong khuôn khổ đồ án môn học **"Cơ sở 1"**. Phần mềm hỗ trợ quản lý các hoạt động của cửa hàng điện tử như quản lý sản phẩm, khách hàng và đơn hàng, với giao diện người dùng thân thiện.

##  Tính năng chính

- Quản lý sản phẩm (thêm, sửa, xóa, tìm kiếm)
- Quản lý khách hàng
- Quản lý hóa đơn hoặc giao dịch mua bán
- Giao diện người dùng được thiết kế bằng Java Swing
- Kết nối và lưu trữ dữ liệu với **MySQL** thông qua **JDBC**

##  Công nghệ sử dụng

- Java (JDK 8+)
- Java Swing (Giao diện người dùng)
- JDBC (Java Database Connectivity)
- MySQL (Lưu trữ dữ liệu)
- NetBeans IDE (phát triển và biên dịch)
- Apache Ant (`build.xml`) để build dự án

## 🛠 Yêu cầu cài đặt

- Java Development Kit (JDK) 8 trở lên
- MySQL Server
- NetBeans IDE (hoặc IDE hỗ trợ Ant project)
- Thư viện JDBC cho MySQL (`mysql-connector-java.jar`) nằm trong thư mục `lib/`

##  Cách chạy dự án

1. Tạo cơ sở dữ liệu MySQL và các bảng cần thiết (xem file hướng dẫn SQL nếu có).
2. Cập nhật thông tin kết nối database trong mã nguồn (ví dụ: URL, user, password).
3. Mở dự án bằng NetBeans.
4. Thực hiện `Clean and Build`.
5. Chạy lớp `Main.java` (hoặc lớp chính trong thư mục `src/`).

##  Cấu trúc thư mục
TECH.-master/
│
├── src/ # Mã nguồn chính (Java)
├── lib/ # Thư viện JDBC và các JAR cần thiết
├── nbproject/ # Cấu hình của NetBeans
├── build.xml # Tệp build cho Apache Ant
├── manifest.mf # Tệp Manifest khi đóng gói


##  Ghi chú thêm

- Ứng dụng sử dụng JDBC để kết nối trực tiếp đến cơ sở dữ liệu MySQL.
- Toàn bộ dữ liệu (sản phẩm, khách hàng...) sẽ được lưu trong MySQL thay vì file.

##  Tác giả

- ThuyAn

