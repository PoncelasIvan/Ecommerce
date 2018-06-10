$(document).ready(function(){
    $("button[data-scroll-to='#productsSection'], button[data-scroll-to='#newProductSection'], button[data-scroll-to='#customerSection']").click(function(){
       $('header').hide();
       $('section').hide();
       $($(this).attr('data-scroll-to')).show();
    });
});
