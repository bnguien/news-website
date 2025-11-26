---

## 🚀 Hướng dẫn Cài đặt Môi trường (BẮT BUỘC)

Để ứng dụng có thể chạy và xử lý upload file, tất cả thành viên team cần thực hiện **một lần cài đặt duy nhất** sau:

### 1. Tạo thư mục Upload
Ứng dụng được cấu hình (trong `FileStorageConfig.java`) để lưu tất cả file upload (ảnh bài báo...) vào một thư mục tên là **`my-news-uploads`** nằm trong thư mục "home" của user.

Vui lòng **tạo thủ công** thư mục này tại các vị trí sau, tùy theo hệ điều hành của bạn:

* **Windows:**
    `C:\Users\<TênUserCủaBạn>\my-news-uploads`

* **macOS:**
    `/Users/<TênUserCủaBạn>/my-news-uploads`

* **Linux:**
    `/home/<TênUserCủaBạn>/my-news-uploads`

### 2. Tại sao?
* Chúng ta dùng `System.getProperty("user.home")` để code có thể chạy trên máy của tất cả mọi người mà không cần sửa cấu hình.
* Việc này giúp chúng ta **KHÔNG** lưu file ảnh vào project và **KHÔNG** push ảnh lên GitHub, giữ cho repository của project luôn gọn nhẹ.

**Lưu ý:** Code đã tự động tạo thư mục con (`article_images`) bên trong đó, nhưng bạn **bắt buộc** phải tạo thư mục gốc (`my-news-uploads`) trước.