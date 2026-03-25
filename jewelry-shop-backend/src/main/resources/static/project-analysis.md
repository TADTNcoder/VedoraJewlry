PHÂN TÍCH CHỨC NĂNG DỰ ÁN
1. Tên dự án

Website bán trang sức Vedora Jewelry

2. Mục tiêu dự án

Xây dựng website bán trang sức cho phép khách hàng xem sản phẩm, thêm vào giỏ hàng, đặt hàng và theo dõi đơn hàng.
Hệ thống có trang quản trị để admin quản lý danh mục, sản phẩm và đơn hàng.

3. Vai trò trong hệ thống
   3.1 USER

Là khách hàng sử dụng website để:

đăng ký tài khoản
đăng nhập
xem sản phẩm
thêm sản phẩm vào giỏ hàng
đặt hàng
xem lịch sử đơn hàng
3.2 ADMIN

Là người quản trị hệ thống để:

quản lý danh mục
quản lý sản phẩm
quản lý đơn hàng
quản lý người dùng cơ bản
4. Chức năng của USER
   4.1 Đăng ký tài khoản
   Người dùng nhập họ tên, email, số điện thoại, mật khẩu
   Hệ thống kiểm tra dữ liệu hợp lệ
   Nếu hợp lệ thì tạo tài khoản mới
   4.2 Đăng nhập
   Người dùng nhập email và mật khẩu
   Hệ thống xác thực thông tin
   Nếu đúng thì cho phép truy cập và lưu phiên đăng nhập
   4.3 Đăng xuất
   Người dùng thoát khỏi tài khoản
   Hệ thống xóa trạng thái đăng nhập
   4.4 Xem trang chủ
   Hiển thị banner
   Hiển thị danh mục nổi bật
   Hiển thị sản phẩm mới hoặc nổi bật
   4.5 Xem danh sách sản phẩm
   Hiển thị tất cả sản phẩm
   Có thể phân trang
   4.6 Xem chi tiết sản phẩm
   Tên sản phẩm
   Giá
   Mô tả
   Chất liệu
   Ảnh sản phẩm
   Biến thể như size, màu, loại đá nếu có
   Số lượng còn hàng
   4.7 Tìm kiếm và lọc sản phẩm
   Tìm theo tên
   Lọc theo danh mục
   Lọc theo giá
   Có thể thêm lọc theo chất liệu ở giai đoạn sau
   4.8 Quản lý giỏ hàng
   Thêm sản phẩm vào giỏ
   Cập nhật số lượng
   Xóa sản phẩm khỏi giỏ
   Xem tổng tiền
   4.9 Đặt hàng
   Nhập thông tin người nhận
   Chọn địa chỉ giao hàng
   Chọn phương thức thanh toán
   Xác nhận đặt hàng
   4.10 Xem lịch sử đơn hàng
   Xem danh sách đơn đã đặt
   Xem trạng thái từng đơn
   Xem chi tiết từng đơn
   4.11 Quản lý thông tin cá nhân
   Xem thông tin tài khoản
   Cập nhật họ tên, số điện thoại, địa chỉ
5. Chức năng của ADMIN
   5.1 Đăng nhập admin
   Admin đăng nhập bằng tài khoản có quyền quản trị
   5.2 Quản lý danh mục
   Thêm danh mục
   Sửa danh mục
   Xóa danh mục
   Xem danh sách danh mục

Ví dụ:

Nhẫn
Dây chuyền
Bông tai
Lắc tay
5.3 Quản lý sản phẩm
Thêm sản phẩm mới
Sửa thông tin sản phẩm
Xóa hoặc ẩn sản phẩm
Xem danh sách sản phẩm

Thông tin sản phẩm gồm:

tên sản phẩm
mã sản phẩm
danh mục
giá
mô tả
chất liệu
ảnh đại diện
trạng thái
5.4 Quản lý biến thể sản phẩm
Size
Màu sắc
Giá theo biến thể
Tồn kho theo biến thể

Ví dụ:

Nhẫn bạc size 6
Nhẫn bạc size 7
Nhẫn vàng trắng size 6
5.5 Quản lý ảnh sản phẩm
Thêm nhiều ảnh cho một sản phẩm
Chọn ảnh chính
Xóa ảnh không dùng
5.6 Quản lý đơn hàng
Xem danh sách đơn hàng
Xem chi tiết đơn hàng
Cập nhật trạng thái đơn hàng

Các trạng thái:

Chờ xác nhận
Đã xác nhận
Đang giao
Đã giao
Đã hủy
5.7 Quản lý người dùng
Xem danh sách tài khoản
Xem thông tin cơ bản của user
Khóa hoặc mở tài khoản nếu cần
5.8 Thống kê cơ bản
Tổng số sản phẩm
Tổng số đơn hàng
Tổng doanh thu
Số lượng người dùng
6. Phạm vi MVP

Đây là phần tối thiểu bạn nên làm trước để dự án chạy được hoàn chỉnh.

6.1 MVP phía USER
Đăng ký
Đăng nhập
Xem danh sách sản phẩm
Xem chi tiết sản phẩm
Tìm kiếm sản phẩm cơ bản
Thêm vào giỏ hàng
Cập nhật giỏ hàng
Đặt hàng
Xem lịch sử đơn hàng
6.2 MVP phía ADMIN
Đăng nhập admin
CRUD danh mục
CRUD sản phẩm
Quản lý đơn hàng
Xem danh sách người dùng cơ bản
6.3 Chưa làm trong MVP
Thanh toán online
Mã giảm giá
Wishlist
Chat với khách hàng
Đánh giá sản phẩm
Gửi email tự động
Dashboard nâng cao
AI gợi ý sản phẩm
7. Luồng nghiệp vụ chính
   7.1 Luồng mua hàng của USER
   Người dùng vào trang chủ
   Xem danh sách sản phẩm
   Chọn một sản phẩm để xem chi tiết
   Chọn biến thể nếu có
   Thêm sản phẩm vào giỏ hàng
   Vào giỏ hàng để kiểm tra
   Cập nhật số lượng nếu cần
   Nhập thông tin nhận hàng
   Xác nhận đặt hàng
   Hệ thống tạo đơn hàng
   Người dùng xem lại lịch sử đơn hàng
   7.2 Luồng quản lý sản phẩm của ADMIN
   Admin đăng nhập
   Vào trang quản trị
   Tạo danh mục sản phẩm
   Thêm sản phẩm mới
   Thêm biến thể sản phẩm
   Tải ảnh sản phẩm lên
   Sửa thông tin sản phẩm khi cần
   Ẩn hoặc xóa sản phẩm
   7.3 Luồng xử lý đơn hàng của ADMIN
   Admin đăng nhập
   Xem danh sách đơn hàng
   Chọn đơn hàng cần xử lý
   Xem chi tiết đơn
   Cập nhật trạng thái đơn
   Hệ thống lưu trạng thái mới
   Người dùng thấy trạng thái đơn được cập nhật
8. Quy tắc nghiệp vụ cơ bản
   8.1 Quy tắc tài khoản
   Email là duy nhất
   Mỗi tài khoản có một vai trò: USER hoặc ADMIN
   Tài khoản bị khóa thì không được đăng nhập
   8.2 Quy tắc sản phẩm
   Mỗi sản phẩm thuộc một danh mục
   Một sản phẩm có thể có nhiều ảnh
   Một sản phẩm có thể có nhiều biến thể
   Giá bán có thể theo sản phẩm hoặc theo biến thể
   8.3 Quy tắc giỏ hàng
   Mỗi user có một giỏ hàng
   Một biến thể sản phẩm chỉ xuất hiện một dòng trong giỏ hàng
   Số lượng trong giỏ không được vượt quá tồn kho
   8.4 Quy tắc đơn hàng
   Một đơn hàng thuộc về một user
   Một đơn hàng có nhiều sản phẩm
   Khi đặt hàng thành công, hệ thống lưu giá tại thời điểm đặt
   Admin được phép cập nhật trạng thái đơn
9. Danh sách module sẽ làm ở giai đoạn sau

Sau giai đoạn 2, hệ thống sẽ được chia thành các module để code:

Auth module
User module
Category module
Product module
Product Variant module
Product Image module
Cart module
Order module
Admin module
10. Kết luận giai đoạn 2

Sau giai đoạn 2, dự án Vedora Jewelry được chốt với:

2 vai trò chính: USER và ADMIN
bộ chức năng mua hàng cơ bản cho khách
bộ chức năng quản trị cơ bản cho admin
phạm vi MVP rõ ràng để triển khai
luồng nghiệp vụ chính đã xác định