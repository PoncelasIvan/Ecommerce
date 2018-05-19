$(document).ready(function(){
    $.cookie('ROLE', 'ADMINISTRATOR');
    //$.cookie('ROLE', 'CUSTOMER');
    let whoami = $.cookie('ROLE');
  
    switch(whoami){
        case 'CUSTOMER' :
            $('#nTog > .ml-auto').append('<button type="button" class="btn btn-outline-info btn-lg" data-target="#customer">Área personal</button>');
            $('#nTog > .ml-auto').append('<button type="button" class="btn btn-outline-danger btn-just-icon" data-action="logout" title="Cerrar sesión" ><i class="fas fa-sign-out-alt"></i></button>');
            break;
        case 'ADMINISTRATOR' :
            $('#nTog > .ml-auto').append('<button type="button" class="btn btn-outline-info btn-lg" data-target="#administrator">Área administrador</button>');
            $('#nTog > .ml-auto').append('<button type="button" class="btn btn-outline-danger btn-just-icon" data-action="logout" title="Cerrar sesión" ><i class="fas fa-sign-out-alt"></i></button>');
            break;             
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
        switch($(this).attr('data-action')){
            case 'logout':
                $.removeCookie('ROLE');
                $(location).attr('href', 'index.html');
                break;
            case 'buy':
                alert("Comprando");
                break;
            case 'favourite':
                alert("Favorito");
                break;
        }
    });
    
    // Changes mains screens
    $('body').on('click', '[data-target]', function(){
        $('.wrapper').hide();
        $($(this).attr('data-target')).show();
        switch($(this).attr('data-target')){
            // Open all products section
            case '#products':
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
                let id  = ($(this).attr('data-product-id') && $(this).attr('data-product-id') != "") ? $(this).attr('data-product-id') : 1;
                $.ajax({
                    type: API.PRODUCT_DETAILS.type,
                    url: API.PRODUCT_DETAILS.url + id,
                    complete: function(jqXHR, textStatus) {
                        let json = jQuery.parseJSON(jqXHR.responseText);
                        switch (jqXHR.status) {
                            case 200:
                                let product = new ProductDescription(json);
                                
                                $('.navbar-translate').prepend('<button type="button" class="btn btn-outline-info btn-just-icon" title="Atras" data-target="#products"><i class="fas fa-arrow-left"></i></button>');
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
                if($('.navbar-brand').length > 0) $('.navbar-brand').remove();
                if($('.navbar-translate button').length > 0) $('.navbar-translate button').remove();
                $('.navbar-translate').append('<button type="button" class="btn btn-outline-success btn-lg" data-target="#products">Tienda <i class="fas fa-shopping-cart"></i></button>');
                
                
                break;
                
                
            case '#customer':
                if($('.navbar-brand').length > 0) $('.navbar-brand').remove();
                if($('.navbar-translate button').length > 0) $('.navbar-translate button').remove();
                $('.navbar-translate').append('<button type="button" class="btn btn-outline-success btn-lg" data-target="#products">Tienda <i class="fas fa-shopping-cart"></i></button>');
               
                break;                
        }
    });    
});