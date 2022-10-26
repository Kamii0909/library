# **GIỚI THIỆU**

## **THÀNH VIÊN NHÓM**

- **Trưởng nhóm:** Hà Trung Kiên - 20190078
- **Thành viên:** Nguyễn Huy Hoàng - 20183749
 
## **PROJECT I: QUẢN LÝ THƯ VIỆN**

**Yêu cầu chung:** Xây dựng hệ thống quản lý sách cho thư viện.

**Chức năng:** Kho metadata sách cho thư viện, đầy đủ các chức năng cho mượn, tính tiền, cùng một số chức năng quản lý.

## **ĐỊNH HƯỚNG PHÁT TRIỂN**

**Đối tượng sử dụng:** Phần mềm dành cho thủ thư có thể kiểm kê, quản lý các đầu sách trong thư viện. 

*Phần mềm được viết chủ yếu bằng Java.*

*TODO: Phiên bản dành cho client - người tới mượn sách.*

# **QUẢN LÝ**

Với việc các yêu cầu tính năng liên tục được thay đổi, dự án sẽ được quản lý theo mô hình scrum, với khả năng thích nghi tốt hơn.

Các phần của dự án sẽ được ưu tiên thực hiện với khả năng hoạt động động lập cao, còn gọi là modularity. Các mảnh nhỏ sẽ được thực hiện lần lượt hoặc đồng thời, sau đó được lắp ghép lại.

## **TRIỂN KHAI**

Phù hợp nhất với yêu cầu sẽ là MVC truyền thống, cho phép tách các phần của dự án làm 3 yêu tố riêng biệt. 

*Tính độc lập các phần cho phép phát triển từng nhiệm vụ riêng biệt một cách song song hoặc tuần tự, dễ dàng hơn trong việc kiểm thử cũng như kiểm tra tiến độ.*

## **GIAI ĐOẠN I**

**Mục tiêu:** Giải quyết module đầu tiên, Model
* Thống nhất cách trình bày dữ liệu: `implementation cho các DTO.`
* Thiết kế hệ cơ sở dữ liệu: với việc không yêu cầu kết nối server, cơ sở dữ liệu ưu tiên nhỏ gọn. `SQLite đang là lựa chọn hàng đầu.`
* Xây dựng API phục vụ 2 module còn lại.

# **TIẾN ĐỘ**

Link github của dự án: 
