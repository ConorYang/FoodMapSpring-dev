document.addEventListener("DOMContentLoaded", function () {
    const loader = document.getElementById("loader-wrapper");

    // 頁面載入完成 → 淡出 loader
    window.addEventListener("load", function () {
        if(loader) loader.style.display = "none";
    });

    // 點擊頁面連結 → 顯示 loader，再跳轉
    document.querySelectorAll("a").forEach(link => {
        link.addEventListener("click", function (e) {
            const href = link.getAttribute("href");
            if (!href || href.startsWith("#") || href.startsWith("javascript")) return;
            
            e.preventDefault();
            if(loader) loader.style.display = "flex"; // 顯示 loader
            setTimeout(() => window.location.href = href, 1500); // 延遲跳轉
        });
    });
});
