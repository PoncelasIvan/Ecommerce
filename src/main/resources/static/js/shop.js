$(document).ready(function(){
    //$.cookie('ROLE', 'ADMINISTRATOR');
    //$.cookie('ROLE', 'CUSTOMER');
    var whoami = $.cookie('ROLE');
  
    switch(whoami){
        case 'CUSTOMER' :
            $('#nTog > .ml-auto').append('<button type="button" class="btn btn-outline-info btn-lg" data-target="#customer">Área personal</button>');
            $('#nTog > .ml-auto').append('<button type="button" class="btn btn-outline-danger btn-just-icon" data-action="logout" title="Cerrar sesión" ><i class="fas fa-sign-out-alt"></i></button>');
            $.ajax({
                type: API.USER_CHECK.type,
                url: API.USER_CHECK.url,
                complete: function(jqXHR, textStatus) {
                    let json = jQuery.parseJSON(jqXHR.responseText);
                    switch (jqXHR.status) {
                        case 200:
                            console.log(json);
                            console.log($($('#customer-profile-edit-data p')[0]));
                           $($('#customer-profile-edit-data p')[0]).text(json.name);
                           $($('#customer-profile-edit-data p')[1]).text(json.email);
                            break; 
                        case 401:
                            break;
                        default: 
                             
                    }
                }
            });
            $.ajax({
                type: API.SELL_GET.type,
                url: API.SELL_GET.url,
                complete: function(jqXHR, textStatus) {
                    let json = jQuery.parseJSON(jqXHR.responseText);
                    switch (jqXHR.status) {
                        case 200:
                            if(!json) return;  
                            console.log("json", json);
                            $('#customer-history ul').children().remove();
                            for(let i in json) $('#customer-history ul').append(new Sell(json[i]).view()); 
                            break;
                            
                        default:
                          
                    }
                }
            });  
            break;
        case 'ADMINISTRATOR' :
            $('#nTog > .ml-auto').append('<button type="button" class="btn btn-outline-info btn-lg" data-target="#administrator">Área administrador</button>');
            $('#nTog > .ml-auto').append('<button type="button" class="btn btn-outline-danger btn-just-icon" data-action="logout" title="Cerrar sesión" ><i class="fas fa-sign-out-alt"></i></button>');
            break;  
        default:
            $('#nTog > .ml-auto').append('<button type="button" class="btn btn-outline-info btn-lg" onclick="$(location).attr(\'href\', \'index.html\');"><i class="fas fa-sign-in-alt"></i>Entrar</button>');            
    }
    
    $.ajax({
        type: API.PRODUCT_GET.type,
        url: API.PRODUCT_GET.url,
        complete: function(jqXHR, textStatus) {
            let json = jQuery.parseJSON(jqXHR.responseText);
            switch (jqXHR.status) {
                case 200:
                    if(!json) return;  
                    let dash = $('#products section .row');
                    for(let i in json) dash.append(new Product(json[i]).view());
                    break;
                    
                default:
                  
            }
        }
    });  
    
    // Actions
    $('body').on('click', '[data-action]', function(){
        let data;
        switch($(this).attr('data-action')){
            /**
             * Common actions
             */
            case 'logout':
                $.removeCookie('ROLE');
                $(location).attr('href', 'index.html');
                break;
                
            /**
             * Customer actions
             */
            case 'customer-change-data':
                data= $(this).parent().find('p');
                let name = $(data[0]).text();
                let email = $(data[1]).text();
                $.ajax({
                    type: API.USER_UPDATE.type,
                    url: API.USER_UPDATE.url,
                    contentType : "application/json; charset=utf-8",
                    data : JSON.stringify({
                        'name' : name,
                        'email' : email
                    }),
                    complete: function(jqXHR, textStatus) {
                        switch (jqXHR.status) {
                            case 200:
                                alert("Datos cambiados");
                                break;    
                        }
                    }
                });
                break;
            case 'customer-change-password':
                data = $(this).parent().find('input');
                let pass = $(data[0]).val();
                let pass0 = $(data[1]).val();
                let pass1 = $(data[2]).val();
                if(pass0 != pass1) return;// Password don't match
                $.ajax({
                    type: API.USER_CHANGE_PASS.type,
                    url: API.USER_CHANGE_PASS.url,
                    contentType : "application/json; charset=utf-8",
                    data : JSON.stringify({
                        'oldPassword' : pass,
                        'newPassword' : pass0
                    }),
                    complete: function(jqXHR, textStatus) {
                        switch (jqXHR.status) {
                            case 200:
                                alert("Cambiada");
                                break;    
                        }
                    }
                });
                break;
            case 'buy':
                $.ajax({
                    type: API.SELL_CREATE.type,
                    url: API.SELL_CREATE.url,
                    contentType : "application/json; charset=utf-8",
                    data : JSON.stringify({
                       'products' : [
                           {
                               'productId' : 1,
                               'cantidad' : 18
                           }
                       ]
                    }),
                    complete: function(jqXHR, textStatus) {
                        switch (jqXHR.status) {
                            case 200:
                                alert("Comproado");
                                break;    
                        }
                    }
                });
                break;
            case 'favourite':
                // Not impelmented
                alert("Favorito");
                break;
            /**
             * Administrator actions
             */
        }
    });
    
    // Change screens
    $('body').on('click', '[data-target]', function(){
        switch($(this).attr('data-target')){
            case '#products':
                $('.wrapper').hide();
                if($('nav [data-target="#products"]').length > 0) $('nav [data-target="#products"]').remove();
                if($('.navbar-brand').length == 0) $('.navbar-translate').append('<h5 class="navbar-brand">Tienda</h5>');       
                $('.navbar-brand').text('Tienda');
                $.ajax({
                    type: API.PRODUCT_GET.type,
                    url: API.PRODUCT_GET.url,
                    complete: function(jqXHR, textStatus) {
                        let json = jQuery.parseJSON(jqXHR.responseText);
                        switch (jqXHR.status) {
                            case 200:
                                if(!json) return;  
                                let dash = $('#products section .row');
                                dash.children().remove();
                                for(let i in json) dash.append(new Product(json[i]).view());
                                break;
                                
                            default:
                              
                        }
                    }
                });
                break;
                
            case '#product' :
                $('.wrapper').hide();
                let id  = ($(this).attr('data-product-id') && $(this).attr('data-product-id') != "") ? $(this).attr('data-product-id') : 1;
                var back = $(this).attr('data-back');
                $.ajax({
                    type: API.PRODUCT_DETAILS.type,
                    url: API.PRODUCT_DETAILS.url + id,
                    complete: function(jqXHR, textStatus) {
                        let json = jQuery.parseJSON(jqXHR.responseText);
                        switch (jqXHR.status) {
                            case 200:
                                let product = new ProductDescription(json);
                                
                                $('.navbar-translate').prepend('<button type="button" class="btn btn-outline-info btn-just-icon" title="Atras" data-target="' + back + '"><i class="fas fa-arrow-left"></i></button>');
                                $('.navbar-brand').text(product.title);
                                let dash = $('#product section section');
                                dash.children().remove();
                                dash.append(product.view());
                                break;    
                        }
                    }
                });
                
                
                break;
                
            case '#administrator':
                $('.wrapper').hide();
                if($('.navbar-brand').length > 0) $('.navbar-brand').remove();
                if($('.navbar-translate button').length > 0) $('.navbar-translate button').remove();
                $('.navbar-translate').append('<button type="button" class="btn btn-outline-success btn-lg" data-target="#products">Tienda <i class="fas fa-shopping-cart"></i></button>');
                break;    
                
            case '#customer':
                
               
               
                $.ajax({
                    type: API.PRODUCT_GET.type,
                    url: API.PRODUCT_GET.url,
                    complete: function(jqXHR, textStatus) {
                        let json = jQuery.parseJSON(jqXHR.responseText);
                        switch (jqXHR.status) {
                            case 200:
                                if(!json) return;  
                                let dash = $('#customer-sCart .row');
                                for(let i in json) dash.append(new Product(json[i]).view());
                                break;
                                
                            default:
                              
                        }
                    }
                });  
                $('.wrapper').hide();
                if($('.navbar-brand').length > 0) $('.navbar-brand').remove();
                if($('.navbar-translate button').length > 0) $('.navbar-translate button').remove();
                $('.navbar-translate').append('<button type="button" class="btn btn-outline-success btn-lg" data-target="#products">Tienda <i class="fas fa-shopping-cart"></i></button>');
                break; 
                
            case '#customer-profile-edit-data':
                $.ajax({
                    type: API.USER_CHECK.type,
                    url: API.USER_CHECK.url,
                    complete: function(jqXHR, textStatus) {
                        let json = jQuery.parseJSON(jqXHR.responseText);
                        switch (jqXHR.status) {
                            case 200:
                                console.log(json);
                                break;    
                        }
                    }
                });
                $('#customer-profile-edit-password').hide();
                break;
                
            case '#customer-profile-edit-password':
                $('#customer-profile-edit-data').hide();
                break;
        }
        $($(this).attr('data-target')).show();
    }); 
    
});