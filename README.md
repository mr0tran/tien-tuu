# Tien Tuu - Game Bốc Bài

Ứng dụng game "Tien Tuu" được phát triển bằng Java cho Android với API level 24 (Android 7.0 Nougat).

## Tính năng

### 1. Màn hình Start
- Nhập tên 2 người chơi
- Giao diện đơn giản, dễ sử dụng
- Validation tên người chơi

### 2. Màn hình Game
- Hiển thị tên 2 người chơi
- Nút "Bốc bài" để lấy lá bài ngẫu nhiên
- Hiển thị lá bài với chất và số
- Hiển thị nhiệm vụ tương ứng với lá bài
- Luân phiên lượt chơi giữa 2 người
- Nút "Kết thúc game" để chuyển sang màn hình kết quả

### 3. Màn hình Kết thúc
- Tổng kết số lượt uống của từng người chơi
- Xác định người thắng cuộc
- Thống kê tổng lượt chơi và tổng lượt uống
- Nút "Chơi lại" với cùng người chơi
- Nút "Game mới" để bắt đầu từ đầu

## Cách chạy ứng dụng

### Yêu cầu hệ thống
- Android Studio (phiên bản mới nhất)
- Android SDK API 24 trở lên
- Thiết bị Android hoặc máy ảo Android 7.0 trở lên

### Các bước chạy

1. **Mở project trong Android Studio**
   ```
   File -> Open -> Chọn thư mục tientuu
   ```

2. **Đồng bộ project**
   - Android Studio sẽ tự động đồng bộ Gradle
   - Hoặc click "Sync Now" nếu có thông báo

3. **Chạy ứng dụng**
   - Kết nối thiết bị Android hoặc tạo máy ảo
   - Click nút "Run" (▶️) trên toolbar
   - Hoặc sử dụng phím tắt Shift + F10

4. **Chọn thiết bị**
   - Chọn thiết bị Android đã kết nối
   - Hoặc chọn máy ảo Android
   - Click "OK"

### Cấu trúc project

```
tientuu/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/tientuu/
│   │   │   ├── MainActivity.java
│   │   │   ├── StartActivity.java
│   │   │   ├── GameActivity.java
│   │   │   └── ResultActivity.java
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_main.xml
│   │   │   │   ├── activity_start.xml
│   │   │   │   ├── activity_game.xml
│   │   │   │   └── activity_result.xml
│   │   │   ├── values/
│   │   │   │   ├── colors.xml
│   │   │   │   ├── strings.xml
│   │   │   │   └── themes.xml
│   │   │   └── drawable/
│   │   │       └── player_background.xml
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
└── build.gradle.kts
```

## Luật chơi

1. **Bắt đầu**: Nhập tên 2 người chơi
2. **Chơi**: Luân phiên bốc bài, thực hiện nhiệm vụ
3. **Nhiệm vụ**: Dựa trên lá bài được bốc
   - Uống 1-5 ly
   - Người chơi khác uống
   - Cả hai cùng uống
   - Thực hiện các hành động khác
4. **Kết thúc**: Người uống ít hơn sẽ thắng

## Giao diện

- Sử dụng **ConstraintLayout** và **LinearLayout** để responsive
- **Material Design** với Material3 theme
- Màu sắc đẹp mắt và dễ nhìn
- Giao diện thân thiện với người dùng

## Lưu ý

- Ứng dụng yêu cầu Android 7.0 (API 24) trở lên
- Đảm bảo thiết bị có đủ bộ nhớ để chạy ứng dụng
- Nếu gặp lỗi, hãy kiểm tra log trong Android Studio
