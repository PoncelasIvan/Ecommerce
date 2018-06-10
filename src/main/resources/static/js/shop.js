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
                            $($('#customer-profile-edit-data p')[0]).text(json.name);
                            $($('#customer-profile-edit-data p')[1]).text(json.email);
                            // Preloading user sell history
                            $.ajax({
                                type: API.SELL_GET.type,
                                url: API.SELL_GET.url,
                                complete: function(jqXHR, textStatus) {
                                    let json = jQuery.parseJSON(jqXHR.responseText);
                                    switch (jqXHR.status) {
                                        case 200:
                                            if(!json) return;  
                                            $('#customer-history ul').children().remove();
                                            for(let i in json) $('#customer-history ul').append(new Sell(json[i]).view()); 
                                            break;
                                            
                                         case 401:
                                            new Toast('Error', 'Usuario no encontrado', 'error', 'top-right').show();
                                            // No es ni user ni admin y esta comprando -> reload
                                            $.removeCookie('ROLE');
                                            $(location).attr('href', 'index.html');
                                            break;

                                        default:
                                            new Toast('Error', 'Servicio no disponible en este momento. Intentelo de nuevo mas tarde', 'error', 'top-left').show();
                                            break;
                                    }
                                }
                            });  
                            break; 
                        case 401:
                             new Toast('Error', 'Usuario no encontrado', 'error', 'top-right').show();
                             // No es ni user y esta comprando -> reload  
                             $.removeCookie('ROLE');
                             $(location).attr('href', 'index.html');
                             break;
                                
                            default: 
                            // Delete cookie and reload
                            $.removeCookie('ROLE');
                            $(location).attr('href', 'index.html');
                            break;
                    }
                }
            });
            break;
        case 'ADMINISTRATOR' :
            $('#nTog > .ml-auto').append('<button type="button" class="btn btn-outline-info btn-lg" data-target="#administrator">Área administrador</button>');
            $('#nTog > .ml-auto').append('<button type="button" class="btn btn-outline-danger btn-just-icon" data-action="logout" title="Cerrar sesión" ><i class="fas fa-sign-out-alt"></i></button>');
            $.ajax({
            	type : API.ADMIN_CHECK.type,
            	url : API.ADMIN_CHECK.url,
            	complete : function(jqXHR, textStatus) {
                    let json = jQuery.parseJSON(jqXHR.responseText);
                    if(!json) return; 
                    switch (jqXHR.status) {
                    	case 200:
                    		$($('#administrator-profile-edit-data p')[0]).text(json.name);
                    		$($('#administrator-profile-edit-data p')[1]).text(json.email);
                    		//Precarga de productos
                    		 $.ajax({
                    		     type: API.SELL_GET_BY_STATE.type,
                    		     url: API.SELL_GET_BY_STATE.url + '1', // In progress
                    		     complete: function(jqXHR, textStatus) {
                    		         let json = jQuery.parseJSON(jqXHR.responseText);
                    		         switch (jqXHR.status) {
                    		             case 200:
                    		                 if(!json) return; 
                                             $('#administrator-request-incoming').children().remove();

                    		                 for(var e = 0; e < 10; e++){
                    		                     for(i in json){
                                                     // We must retrieve this id
                                                     json[i].id = "" + Math.floor(Math.random() * Math.floor(1000000));
                                                     $('#administrator-request-incoming').append(new Sell(json[i]).view());
                                                     
                                                 }
                    		                 }
                    		                 break;
                    		                  
                    		              default:
                    		                  new Toast('Error', 'Servicio no disponible en este momento. Intentelo de nuevo mas tarde', 'error', 'top-left').show();
                    		                  break;
                    		         }
                    		     }
                    		  });  
                    		
                    		break;
                    	case 401:
                    		new Toast('Error', 'Usuario no encontrado', 'error', 'top-right').show();
                            // No es ni user ni admin y esta comprando -> reload  
                            $.removeCookie('ROLE');
                            $(location).attr('href', 'index.html');
                    		break;
                    	default:
                    		// Delete cookie and reload
                            $.removeCookie('ROLE');
                            $(location).attr('href', 'index.html');
                            break;
                    }
            	}
            });
            break;  
        default: // Not logged user
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
                    new Toast('Error', 'Servicio no disponible en este momento. Intentelo de nuevo mas tarde', 'error', 'top-left').show();
                    break;
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
                                new Toast('Datos actualizados', 'Sus datos han sido actualizados con exito', 'success', 'bottom-right').show();       
                                break;    
                            case 401:
                                new Toast('Error', 'Usuario no encontrado', 'error', 'top-right').show();
                                // No se encuentra user y esta cambiando user -> reload
                                $.removeCookie('ROLE');
                                $(location).attr('href', 'index.html');
                                break;    
                            case 409:
                                new Toast('Error', 'Conflicto en la creacion de usuario', 'error', 'top-right').show();
                                break;    
                            default:
                                new Toast('Error', 'Servicio no disponible en este momento. Intentelo de nuevo mas tarde', 'error', 'top-left').show();
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
                                new Toast('Contraseña actualizada', 'Su contraseña ha sido actualizada con exito', 'success', 'bottom-right').show();       
                                break; 
                             case 401:
                                new Toast('Error', 'Contraseña incorrecta ', 'error', 'top-right').show();
                                break;    
                            case 400:
                                new Toast('Error', 'Contraseña vacia, inserte contraseña', 'error', 'top-right').show();
                                break;    
                            default:
                                new Toast('Error', 'Servicio no disponible en este momento. Intentelo de nuevo mas tarde', 'error', 'top-left').show();
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
                               'productId' : $($(this)[0]).attr('data-product-id'),
                               'cantidad' : 1
                           }
                       ]
                    }),
                    complete: function(jqXHR, textStatus) {
                        switch (jqXHR.status) {
                            case 201:
                                new Toast('Compra realizada', 'Su compra ha sido realizada con exito', 'success', 'bottom-right').show();       
                                break; 
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
                                            case 401:   
                                                new Toast('Error', 'Usuario no encontrado', 'error', 'top-right').show();
                                                // No es ni user ni admin y ha comprado -> reload
                                                $.removeCookie('ROLE');
                                                $(location).attr('href', 'index.html');
                                                break;  
                                            default:
                                                new Toast('Error', 'Servicio no disponible en este momento. Intentelo de nuevo mas tarde', 'error', 'top-left').show();
                                                break; 
                                        }
                                    }
                                });
                            case 401:   
                                new Toast('Error', 'Usuario no encontrado', 'error', 'top-right').show();
                                // No es ni user ni admin y ha comprado -> reload
                                $.removeCookie('ROLE');
                                $(location).attr('href', 'index.html');
                                break;   
                            default:
                                new Toast('Error', 'Servicio no disponible en este momento. Intentelo de nuevo mas tarde', 'error', 'top-left').show();
                                break;   
                        }
                    }
                });
                break;
            case 'favourite':
                // Not impelmented
                new Toast('Añadir a favorito', 'Este metodo aun no esta implementado', 'warning', 'bottom-right').show();       
                break;

            case 'saveProduct':
                let dad = $($(this).parent().parent()[0]);
                let par = dad.find('[contenteditable="true"]');
                let product = new Object();
                product.id = $(this).attr('data-product-id');
                product.title = dad.children('h1').text();
                product.description = $(par[0]).text();
                product.autor = $(par[1]).text();
                product.formato = $(par[2]).text();
                product.price = $(par[3]).text();
                alert("Implementar guardado y enviar product");
                break;
            case 'administrator-update-status':
                let sellId = $(this).attr('data-sell-id');
                let sellStatus = $(this).attr('data-sell-status');
                alert("Cambiar venta " + sellId + " a estado " + sellStatus);
                break;

            case 'administrator-change-password':
            	data = $(this).parent().find('input');
                pass = $(data[0]).val();
                pass0 = $(data[1]).val();
                pass1 = $(data[2]).val();
                if(pass0 != pass1) return;// Password don't match
                $.ajax({
                    type: API.ADMIN_CHANGE_PASS.type,
                    url: API.ADMIN_CHANGE_PASS.url,
                    contentType : "application/json; charset=utf-8",
                    data : JSON.stringify({
                        'oldPassword' : pass,
                        'newPassword' : pass0
                    }),
                    complete: function(jqXHR, textStatus) {
                        switch (jqXHR.status) {
                            case 200:
                                new Toast('Contraseña actualizada', 'Su contraseña ha sido actualizada con exito', 'success', 'bottom-right').show();       
                                break; 
                             case 401:
                                new Toast('Error', 'Contraseña incorrecta ', 'error', 'top-right').show();
                                break;    
                            case 400:
                                new Toast('Error', 'Contraseña vacia, inserte contraseña', 'error', 'top-right').show();
                                break;    
                            default:
                                new Toast('Error', 'Servicio no disponible en este momento. Intentelo de nuevo mas tarde', 'error', 'top-left').show();
                                break;     
                        }
                    }
                });
            	break;
            	
            case 'administrator-change-data':
            	data= $(this).parent().find('p');
                name = $(data[0]).text();
                email = $(data[1]).text();
                $.ajax({
                    type: API.ADMIN_UPDATE.type,
                    url: API.ADMIN_UPDATE.url,
                    contentType : "application/json; charset=utf-8",
                    data : JSON.stringify({
                        'name' : name,
                        'email' : email
                    }),
                    complete: function(jqXHR, textStatus) {
                        switch (jqXHR.status) {
                            case 200:
                                new Toast('Datos actualizados', 'Sus datos han sido actualizados con exito', 'success', 'bottom-right').show();       
                                break;    
                            case 401:
                                new Toast('Error', 'Usuario no encontrado', 'error', 'top-right').show();
                                // No se encuentra user y esta cambiando user -> reload
                                $.removeCookie('ROLE');
                                $(location).attr('href', 'index.html');
                                break;    
                            case 409:
                                new Toast('Error', 'Conflicto en la actualizacion de usuario', 'error', 'top-right').show();
                                break;    
                            default:
                                new Toast('Error', 'Servicio no disponible en este momento. Intentelo de nuevo mas tarde', 'error', 'top-left').show();
                                break;      
                        }
                    }
                });
            	break;
            	
            case 'administrator-create-admin':
            	data = $(this).parent().find('input');
                name = $(data[0]).val();
                email = $(data[1]).val();
                pass1 = $(data[2]).val();
                pass2 = $(data[3]).val();
                if(pass1 != pass2) return; //las contraseñas no son iguales
                
                $.ajax({
                    type: API.ADMIN_REGISTER.type,
                    url: API.ADMIN_REGISTER.url,
                    contentType : "application/json; charset=utf-8",
                    data : JSON.stringify({
                        'name' : name,
                        'email' : email,
                        'password' : pass1
                    }),
                    complete: function(jqXHR, textStatus) {
                        switch (jqXHR.status) {
                            case 200:
                                new Toast('Administrador creado', 'El administrador ha sido creado correctamente', 'success', 'bottom-right').show();       
                                break;    
                            case 403:
                                new Toast('Error', 'Usuario no encontrado', 'error', 'top-right').show();
                                // No se encuentra user y esta cambiando user -> reload
                                $.removeCookie('ROLE');
                                $(location).attr('href', 'index.html');
                                break;    
                            case 409:
                                new Toast('Error', 'Conflicto en la creacion de usuario', 'error', 'top-right').show();
                                break;    
                            default:
                                new Toast('Error', 'Servicio no disponible en este momento. Intentelo de nuevo mas tarde', 'error', 'top-left').show();
                                break;      
                        }
                    }
                });
                    
            	break;
            case 'administrator-request-wait':
                $.ajax({
                    type: API.SELL_GET_BY_STATE.type,
                    url: API.SELL_GET_BY_STATE.url + '0', 
                    complete: function(jqXHR, textStatus) {
                        let json = jQuery.parseJSON(jqXHR.responseText);
                        switch (jqXHR.status) {
                            case 200:
                                if(!json) return; 
                                $('#administrator-request-wait').children().remove();

                                for(var e = 0; e < 10; e++){
                                    for(i in json){
                                        // We must retrieve this id
                                        json[i].id = "" + Math.floor(Math.random() * Math.floor(1000000));
                                        $('#administrator-request-wait').append(new Sell(json[i]).view());
                                        
                                    }
                                }
                                break;
                                 
                             default:
                                 new Toast('Error', 'Servicio no disponible en este momento. Intentelo de nuevo mas tarde', 'error', 'top-left').show();
                                 break;
                        }
                    }
                 });  
               
                break;
           case 'administrator-request-incoming':
               $.ajax({
                   type: API.SELL_GET_BY_STATE.type,
                   url: API.SELL_GET_BY_STATE.url + '1', // In progress
                   complete: function(jqXHR, textStatus) {
                       let json = jQuery.parseJSON(jqXHR.responseText);
                       switch (jqXHR.status) {
                           case 200:
                               if(!json) return; 
                               $('#administrator-request-incoming').children().remove();

                               for(var e = 0; e < 10; e++){
                                   for(i in json){
                                       // We must retrieve this id
                                       json[i].id = "" + Math.floor(Math.random() * Math.floor(1000000));
                                       $('#administrator-request-incoming').append(new Sell(json[i]).view());
                                       
                                   }
                               }
                               break;
                                
                            default:
                                new Toast('Error', 'Servicio no disponible en este momento. Intentelo de nuevo mas tarde', 'error', 'top-left').show();
                                break;
                       }
                   }
                });  
              
              break;
            case 'administrator-request-done':
                $.ajax({
                    type: API.SELL_GET_BY_STATE.type,
                    url: API.SELL_GET_BY_STATE.url + '2', // In progress
                    complete: function(jqXHR, textStatus) {
                        let json = jQuery.parseJSON(jqXHR.responseText);
                        switch (jqXHR.status) {
                            case 200:
                                if(!json) return; 
                                $('#administrator-request-done').children().remove();

                                for(var e = 0; e < 10; e++){
                                    for(i in json){
                                        // We must retrieve this id
                                        json[i].id = "" + Math.floor(Math.random() * Math.floor(1000000));
                                        $('#administrator-request-done').append(new Sell(json[i]).view());
                                        
                                    }
                                }
                                break;
                                 
                             default:
                                 new Toast('Error', 'Servicio no disponible en este momento. Intentelo de nuevo mas tarde', 'error', 'top-left').show();
                                 break;
                        }
                    }
                 });  
                break;
            case 'administrator-create-product':
            	data = $(this).parent().find('input');
                title = $(data[0]).val();
                author = $(data[1]).val();
                synopsis = $(data[2]).val();
                price = $(data[3]).val();
                stock = $(data[4]).val();
                format = $(this).parent().find('input[name=format]:checked').val();
                if(Number.isInteger(price) || Number.isInteger(stock)){ new Toast('Administrador creado', 'El administrador ha sido creado correctamente', 'success', 'bottom-right').show();return;} // precio o stock no es un int
               
                $.ajax({
                    type: API.PRODUCT_CREATE.type,
                    url: API.PRODUCT_CREATE.url,
                    contentType : "application/json; charset=utf-8",
                    data : JSON.stringify({
                        'title' : title,
                        'author' : author,
                        'synopsis' : synopsis,
                        'price' : price,
                        'stock' : stock,
                        'format' : format
                    }),
                    completed: function(jqXHR, textStatus) {
                        switch (jqXHR.status) {
                        case 201:
                            new Toast('Producto creado', 'El producto ha sido creado correctamente', 'success', 'bottom-right').show();       
                            break;    
                        case 403:
                            new Toast('Error', 'Usuario no encontrado', 'error', 'top-right').show();
                            // No se encuentra user y esta cambiando user -> reload
                            $.removeCookie('ROLE');
                            $(location).attr('href', 'index.html');
                            break;     
                        default:
                            new Toast('Error', 'Servicio no disponible en este momento. Intentelo de nuevo mas tarde', 'error', 'top-left').show();
                            break;      
                    }
                }
            });
                    	
            	break;
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
                                new Toast('Error', 'Servicio no disponible en este momento. Intentelo de nuevo mas tarde', 'error', 'top-left').show();
                                break; 
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
                            case 404:
                                new Toast('Error', 'Producto no encontrado', 'error', 'top-left').show();
                                break;
                            default: 
                                new Toast('Error', 'Servicio no disponible en este momento. Intentelo de nuevo mas tarde', 'error', 'top-left').show();
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
                                new Toast('Error', 'Servicio no disponible en este momento. Intentelo de nuevo mas tarde', 'error', 'top-left').show();
                                break;
                              
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
                            case 200: break;  
                            case 401: 
                                new Toast('Error', 'Usuario erroneo', 'error', 'top-left').show();
                                break;  
                            default: 
                                new Toast('Error', 'Servicio no disponible en este momento. Intentelo de nuevo mas tarde', 'error', 'top-left').show();
                                break; 
                        }
                    }
                });
                $('#customer-profile-edit-password').hide();
                break;
                
            case '#customer-profile-edit-password':
                $('#customer-profile-edit-data').hide();
                break;
            
            case '#administrator-profile-new-admin':
            case '#administrator-profile-new-product':
            case '#administrator-profile-edit-data':
            case '#administrator-profile-edit-password':
                let me = $($(this).attr('data-target'));
                me.parent().children().hide();
                me.show();
                break;
                
        }
        $($(this).attr('data-target')).show();
    }); 
    
});