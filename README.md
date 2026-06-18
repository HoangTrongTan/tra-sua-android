# trasua_Android 🥤

Một ứng dụng Android quản lý và gọi món trà sữa, được phát triển bằng ngôn ngữ **Java** và hệ quản trị cơ sở dữ liệu **SQLite**. Ứng dụng cung cấp các tính năng đăng ký, đăng nhập tài khoản của người dùng kèm chức năng chụp ảnh trực tiếp từ camera hoặc chọn ảnh từ thư viện để làm ảnh đại diện.

---

## 🚀 Tính Năng Chính

- **Đăng ký Tài khoản (Sign Up)**:
  - Nhập thông tin đăng ký (Tên đăng nhập, Mật khẩu).
  - Tích hợp **chụp ảnh trực tiếp từ Camera** (yêu cầu quyền Camera) hoặc **chọn ảnh từ Thư viện (Gallery)** để lưu trữ làm ảnh đại diện.
  - Sử dụng Glide để tải và hiển thị hình ảnh mượt mà.
  - Lưu thông tin và ảnh đại diện dưới dạng dữ liệu nhị phân `BLOB` vào SQLite.
- **Đăng nhập Tài khoản (Log In)**:
  - Xác thực thông tin tài khoản người dùng từ SQLite database.
  - Hiển thị Toast thông báo đăng nhập thành công/thất bại tùy biến theo phong cách riêng (custom layout và biểu tượng cảm xúc).
- **Cơ sở dữ liệu SQLite**:
  - Quản lý cấu trúc bảng tài khoản (`Taikhoan`), món ăn (`MONAN`), bàn ăn (`BANAN`).
- **Hiệu ứng & Giao diện**:
  - Hỗ trợ custom dialog đẹp mắt cho form đăng ký.
  - Animation mượt mà cho các nút bấm (Button).
  - Tích hợp Material Components cho các trường nhập liệu nhập mật khẩu, tài khoản.

---

## 🛠️ Công Nghệ & Thư Viện Sử Dụng

- **Ngôn ngữ phát triển**: Java (JDK 1.8)
- **Hệ điều hành tối thiểu**: Android 5.0 (API 21 - minSdk 21)
- **Hệ điều hành phát triển**: Android 12 (API 32 - targetSdk/compileSdk 32)
- **Các thư viện cốt lõi**:
  - [Glide](https://github.com/bumptech/glide) (`v4.12.0`): Hỗ trợ load ảnh và xử lý hình ảnh mượt mà.
  - **Material Components** (`v1.8.0`): Sử dụng TextInputEditText, TextInputLayout để tăng trải nghiệm người dùng.
  - **ConstraintLayout** (`v2.1.4`): Tối ưu hóa hiệu năng render layout XML.
  - **SQLite**: Hệ quản trị cơ sở dữ liệu nhúng cục bộ.

---

## 📂 Cấu Trúc Thư Mục Dự Án

Dự án được tổ chức theo cấu trúc dạng mô hình hướng đối tượng rõ ràng:

```text
trasua_Android/
│
├── app/
│   ├── build.gradle             # Cấu hình Gradle của module app (dependencies, sdk...)
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml   # Cấu hình quyền truy cập (Camera, Storage) và các activity
│           ├── java/com/example/trasua_android/
│           │   ├── Activitys/
│           │   │   └── home.java        # Activity chính điều phối chức năng đăng nhập, đăng ký
│           │   ├── Dao/
│           │   │   ├── dangkyDao.java   # Logic hỗ trợ đăng ký (đang phát triển)
│           │   │   ├── dangnhapDao.java # Chứa custom layout Toasts hiển thị kết quả
│           │   │   └── imageloader.java # Tiện ích load ảnh từ thư viện thông qua Glide
│           │   ├── Databases/
│           │   │   └── db_sqlite.java   # Quản lý khởi tạo và nâng cấp database SQLite
│           │   ├── TruyVan/
│           │   │   └── thucthi.java     # Chứa các câu truy vấn SQL (Create table, Insert, Select)
│           │   └── animation/
│           │       └── anim.java        # Tiện ích tạo hiệu ứng chuyển động cho View (nút bấm)
│           │
│           └── res/                     # Tài nguyên giao diện (Layouts, Drawables, Values...)
│               ├── anim/                # Chứa file xml định nghĩa hiệu ứng animation
│               ├── layout/              # Chứa các file giao diện XML
│               │   ├── activity_home.xml      # Giao diện màn hình đăng nhập
│               │   ├── dangky_form.xml        # Giao diện Form đăng ký (Custom Dialog)
│               │   ├── custom_toast.xml       # Giao diện Toast khi đăng nhập thành công
│               │   └── custom_toast_khoc.xml  # Giao diện Toast khi đăng nhập thất bại
│               └── values/              # Định nghĩa chuỗi, màu sắc, theme dự án
```

---

## 🗄️ Cấu Trúc Cơ Sở Dữ Liệu (SQLite)

Cơ sở dữ liệu của ứng dụng tên là **`BanHang`** gồm các bảng chính:

### 1. Bảng `Taikhoan` (Tài khoản)
Dùng để quản lý người dùng đăng nhập hệ thống.
| Tên Cột | Kiểu Dữ Liệu | Mô Tả |
| :--- | :--- | :--- |
| `_id` | `INTEGER` | Khóa chính, tự động tăng (`PRIMARY KEY AUTOINCREMENT`) |
| `tendangnhap` | `VARCHAR(100)` | Tên đăng nhập của tài khoản |
| `matkhau` | `NVARCHAR(150)` | Mật khẩu tài khoản |
| `hinhanh` | `BLOB` | Ảnh đại diện của người dùng (Dữ liệu ảnh nhị phân) |

### 2. Bảng `MONAN` (Món ăn / Trà sữa)
Dùng để quản lý thực đơn danh sách các món trà sữa.
| Tên Cột | Kiểu Dữ Liệu | Mô Tả |
| :--- | :--- | :--- |
| `_id` | `INTEGER` | Khóa chính, tự động tăng |
| `ten_mon` | `NVARCHAR(150)` | Tên món ăn, nước uống |
| `hinh_anh` | `BLOB` | Hình ảnh minh họa của món ăn |

### 3. Bảng `BANAN` (Bàn ăn)
Quản lý vị trí bàn tại quán trà sữa.
| Tên Cột | Kiểu Dữ Liệu | Mô Tả |
| :--- | :--- | :--- |
| `_id` | `INTEGER` | Khóa chính, tự động tăng |
| `ten_ban` | `NVARCHAR(150)` | Tên hoặc số bàn |
| `trangthai` | `INTEGER` | Trạng thái bàn (ví dụ: 0 - trống, 1 - có khách) |

---

## ⚙️ Cấu Hình Quyền Hạn (Permissions)

Để ứng dụng chạy ổn định và chụp ảnh/chọn ảnh đại diện được mượt mà, ứng dụng yêu cầu các quyền sau trong file `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission
    android:name="android.permission.MANAGE_EXTERNAL_STORAGE"
    android:minSdkVersion="30"
    tools:ignore="ScopedStorage" />
```

---

## 🛠️ Hướng Dẫn Cài Đặt & Chạy Dự Án

### Yêu cầu hệ thống:
- Android Studio Chipmunk trở lên.
- Java Development Kit (JDK) 8.
- Thiết bị Android vật lý hoặc Giả lập (Emulator) chạy từ Android 5.0 trở lên.

### Các bước thực hiện:
1. **Clone dự án**:
   ```bash
   git clone https://github.com/HoangTrongTan/tra-sua-android.git
   ```
2. **Mở dự án trong Android Studio**:
   - Chọn **File** -> **Open...** và duyệt đến thư mục dự án đã tải về.
3. **Đồng bộ hóa Gradle**:
   - Android Studio sẽ tự động đồng bộ hóa Gradle và tải các thư viện phụ thuộc (`Glide`, `Material Components`).
4. **Cài đặt lên thiết bị**:
   - Kết nối điện thoại hoặc khởi động máy ảo Emulator.
   - Nhấn **Run** (biểu tượng Play màu xanh lá 🟢 hoặc tổ hợp phím `Shift + F10`) để build và cài đặt ứng dụng.

---

## 🛠️ Kế Hoạch Phát Triển Tiếp Theo

- [ ] Thực hiện phân quyền đăng nhập giữa Quản lý và Nhân viên.
- [ ] Hoàn thiện hiển thị danh sách Menu trà sữa từ bảng `MONAN`.
- [ ] Quản lý trạng thái order và thanh toán theo bàn ăn từ bảng `BANAN`.
- [ ] Cập nhật lưu trữ hình ảnh thông qua Cloud Storage thay vì lưu trực tiếp dạng `BLOB` trong SQLite để tối ưu hóa bộ nhớ ứng dụng.

---
*Dự án được thực hiện bởi nhóm phát triển ứng dụng di động quản lý trà sữa.*
