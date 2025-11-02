document.addEventListener("DOMContentLoaded", function() {
    const dropdown = document.querySelector(".nav-item.dropdown");
    const menu = dropdown.querySelector(".dropdown-menu");
    let hideTimeout;

    // 滑入顯示
    dropdown.addEventListener("mouseenter", function() {
        clearTimeout(hideTimeout);
        menu.style.opacity = "1";
        menu.style.visibility = "visible";
        menu.style.transform = "translateY(0)";
    });

    // 滑出延遲隱藏
    dropdown.addEventListener("mouseleave", function() {
        hideTimeout = setTimeout(() => {
            menu.style.opacity = "0";
            menu.style.visibility = "hidden";
            menu.style.transform = "translateY(-10px)";
        }, 300); // 300ms 延遲，給使用者操作時間
    });
});
