$(document).ready(function(){

   let whoami = $.cookie('ROLE');
   switch(whoami){
        case 'CUSTOMER' :
            // It says that is an user, we are going to check it
            $.ajax({
                type: API.USER_CHECK.type,
                url: API.USER_CHECK.url,
                complete: function(jqXHR, textStatus) {
                    switch (jqXHR.status) {
                        case 200:
                            // Thats true
                            $(location).attr('href', 'shop.html');
                            break;
                        default:
                            // Thats false
                            $.removeCookie('ROLE');
                    }
                }
            });
            break;
        case 'ADMINISTRATOR' :
            // It says that is an administrator, we are going to check it
            $.ajax({
                type: API.ADMIN_CHECK.type,
                url: API.ADMIN_CHECK.url,
                complete: function(jqXHR, textStatus) {
                    switch (jqXHR.status) {
                        case 200:
                            // Thats true
                            $(location).attr('href', 'shop.html');
                            break;
                        default:
                            // Thats false
                            $.removeCookie('ROLE');
                    }
                }
            });
            break;
   }
       
   // Smooth scrolling
   $('[data-scroll-to]').click(function(event){
       $('html, body').animate({
           scrollTop: $($(this).attr("data-scroll-to")).offset().top
         }, 900);
   });
   
   $('#shop').click(function(){
       $(location).attr('href', 'shop.html');
   });
   
   $('#loginSection input, #registerSection input').focus(function(){
       FormController.clean($(this));
   });
   
   // Login
   $('#loginSection button').click(function(e){
       var error = false;
       $(this).parent().children('.form-group').each(function(){
           FormController.clean($(this).children('input'));

          if(!$(this).children('input').val()){
              // Void field
              FormController.showInInput($(this).children('input'), 'Campo vacio', FORMCODES.ERROR);
              error = true;
          }
       });
       if(!error){
           var user = $($(this).parent().children('.form-group')[0]).children('input').val();
           var pass = $($(this).parent().children('.form-group')[1]).children('input').val();
           $.ajax({
               url : API.USER_LOGIN.url,
               type : API.USER_LOGIN.type,
               contentType : "application/json; charset=utf-8",
               data : JSON.stringify({
                       'user' : user,
                       'password' : pass
               }),
               complete: function(jqXHR, textStatus) {
                   switch (jqXHR.status) {
                       case 200:
                           $.cookie('ROLE', 'CUSTOMER');
                           $(location).attr('href', 'shop.html');
                           break;
                       case 401:
                           // Password incorrect
                           FormController.showInInput($($('#loginSection').children('form').children('.form-group')[1]).children('input'), 'Contraseña incorrecta', FORMCODES.ERROR);
                           new Toast('Error', 'Contraseña incorrecta', 'error', 'top-right').show();       
                           break;
                       case 404: 
                           // Not user login, maybe is and admin?
                           $.ajax({
                               url : API.ADMIN_LOGIN.url,
                               type : API.ADMIN_LOGIN.type,
                               contentType : "application/json; charset=utf-8",
                               data : JSON.stringify({
                                       'user' : user,
                                       'password' : pass
                               }),
                               complete: function(jqXHR, textStatus) {
                                   switch (jqXHR.status) {
                                       case 200:
                                           $.cookie('ROLE', 'ADMINISTRATOR');
                                           $(location).attr('href', 'shop.html');
                                           break;
                                       case 401:
                                           // Password incorrect
                                           FormController.showInInput($($('#loginSection').children('form').children('.form-group')[1]).children('input'), 'Contraseña incorrecta', FORMCODES.ERROR);
                                           new Toast('Error', 'Contraseña incorrecta', 'error', 'top-right').show();       
                                           break;
                                       case 404:
                                           FormController.showInInput($($('#loginSection').children('form').children('.form-group')[0]).children('input'), 'EL usuario no es valido', FORMCODES.ERROR);
                                           break;
                                       default:
                                           new Toast('Error', 'Servicio no disponible en este momento. Intentelo de nuevo mas tarde', 'error', 'top-left').show();
                                           break;  
                                   }
                               }
                           });
                           break;
                       default:
                           new Toast('Error', 'Servicio no disponible en este momento. Intentelo de nuevo mas tarde', 'error', 'top-left').show();
                           break;
                   }
               }
           });
       }
   });
   
   // Register
   $('#registerSection  button').click(function(){
       $(this).parent().children('.form-group').each(function(){
           FormController.clean($(this).children('input'));
           if(!$(this).children('input').val()){
               // Void field
               FormController.showInInput($(this).children('input'), 'Campo vacio', FORMCODES.ERROR);
           }
        });
       
   });
});


