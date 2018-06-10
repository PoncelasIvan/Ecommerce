/*
 * In this file we are going to define all API calls 
 * for use in all web
 */  
var API = {
		
		//Customers
        USER_LOGIN : {
            type : 'POST',
            url : '/api/customer/login/'
        },
        USER_LOGOUT : {
        	type : 'DELETE',
        	url : '/api/customer/logout/'
        },
        USER_CHECK : {
            type : 'GET',
            url : '/api/customer/'
        },
        USER_UPDATE : {
        	type : 'PUT',
        	url : '/api/customer/'
        },
        USER_CHANGE_PASS : {
            type : 'PUT',
            url : '/api/customer/password/'
        },
        USER_REGISTER : {
        	type : 'POST',
        	url : '/api/customer/'
        },
        USER_DELETE : {
        	type : 'DELETE',
        	url : '/api/customer/'
        },
        
        //Amdinistrators
        ADMIN_REGISTER : {
        	type : 'POST',
        	url : '/api/admin/'
        },
        ADMIN_UPDATE : {
        	type : 'PUT',
        	url : '/api/admin/'
        },
        ADMIN_CHANGE_PASS : {
        	type : 'PUT',
        	url : '/api/admin/password/'
        },
        ADMIN_DELETE : {
        	type : 'DELETE',
        	url : '/api/admin/'
        },
        ADMIN_LOGIN : {
            type : 'POST',
            url : '/api/admin/login/'
        },
        ADMIN_LOGOUT : {
        	type : 'DELETE',
        	url : '/api/admin/logout/'
        },
        ADMIN_CHECK : {
            type : 'GET',
            url : '/api/admin/'
        },
        ADMIN_UPDATE : {
        	type : 'PUT',
        	url : '/api/admin/'
        },
        ADMIN_GET_ALL_ADMINS : {
        	type : 'GET',
        	url : '/api/admin/administrators/'
        },
        ADMIN_DELETE_ADMIN : {
        	type : 'DELETE',
        	url : '/api/admin/administrator/' //Requiere parametro {id}
        },
        ADMIN_GET_ALL_CUST : {
        	type : 'GET',
        	url : '/api/admin/customers/'
        },
        ADMIN_GET_CUST : {
        	type : 'GET',
        	url : '/api/admin/customer/' //Requiere parametro {id}
        },
        ADMIN_DELETE_CUST : {
        	type : 'DELETE',
        	url : '/api/admin/customer/' //Requiere parametro {id}
        },
        
        //Products(to be done)
        PRODUCT_GET : {
        	type : 'GET',
        	url : '/api/product/'
        },
        PRODUCT_CREATE : {
        	type : 'POST',
        	url : '/api/product/'
        },
        PRODUCT_DETAILS : {
        	type : 'GET',
        	url : '/api/product/'
        },
        PRODUCT_UPDATE : {
            type : 'PUT',
            url : '/api/product/'
        },
        PRODUCT_DELETE : {
        	type : 'DELETE',
        	url : '/api/product/' //Requiere parametro {id}
        },
        PRODUCT_SET_IMAGE : {
        	type : 'POST',
        	url : '/api/product/' //Requiere parametro {id}/image/
        },
        PRODUCT_DELETE_IMAGE : {
        	type : 'DELETE',
        	url : '/api/product/image/' //Requiere parametro {imageId}
        },
        PRODUCT_SEARCH : {
        	type : 'POST',
        	url : '/api/product/search/'
        },
        
        //Sells
        SELL_GET : {
        	type : 'GET',
        	url : '/api/sell/'
        },
        SELL_GET_BY_STATE : {
        	type : 'GET',
        	url : '/api/sell/' //Requiere parametro {state}
        },
        SELL_CREATE : {
        	type : 'POST',
        	url : '/api/sell/'
        },
        SELL_UPDATE : {
        	type : 'PUT',
        	url : '/api/sell/'
        },
        SELL_DELETE : {
        	type : 'DELETE',
        	url : '/api/sell/'
        }    
};
