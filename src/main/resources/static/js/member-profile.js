$(document).on('submit', '#memberSettingsForm', function(e) {
    e.preventDefault(); // 阻止頁面刷新

    $.post('/member/setting/update', $(this).serialize())
    .done(function(resp) {
        Swal.fire({
            title: '更新成功！',
            text: '你的會員設定已經更新完成。',
            icon: 'success',
            showConfirmButton: true,  // 顯示確認按鈕
            confirmButtonText: '確定'
        }).then((result) => {
            if (result.isConfirmed) {
                $('#memberSetting').removeClass('show'); // 按確定後關閉側欄
            }
        });
    })
    .fail(function(err) {
        Swal.fire({
            title: '更新失敗',
            text: '請稍後再試。',
            icon: 'error',
            confirmButtonText: '確認'
        });
    });
});
