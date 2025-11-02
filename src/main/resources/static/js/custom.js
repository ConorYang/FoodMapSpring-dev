
  (function ($) {
  
  "use strict";

    // MENU
    $('#sidebarMenu .nav-link').on('click',function(){
      $("#sidebarMenu").collapse('hide');
    });
    
    // CUSTOM LINK
    $('.smoothscroll').click(function(){
      var el = $(this).attr('href');
      var elWrapped = $(el);
      var header_height = $('.navbar').height();
  
      scrollToDiv(elWrapped,header_height);
      return false;
  
      function scrollToDiv(element,navheight){
        var offset = element.offset();
        var offsetTop = offset.top;
        var totalScroll = offsetTop-navheight;
  
        $('body,html').animate({
        scrollTop: totalScroll
        }, 300);
      }
    });
  
  })(window.jQuery);

  
  
//  custermerCenter-popup controller

    const customerBtn = document.getElementById('customer-btn');
    const customerPopup = document.getElementById('customer-popup');

    customerBtn.addEventListener('click', () => {
        if (customerPopup.style.display === "block") {
            customerPopup.style.display = "none";
        } else {
            customerPopup.style.display = "block";
        }
    });

    // 點擊頁面其他地方自動收起
    document.addEventListener('click', function(event) {
        if (!customerBtn.contains(event.target) && !customerPopup.contains(event.target)) {
            customerPopup.style.display = 'none';
        }
    });

