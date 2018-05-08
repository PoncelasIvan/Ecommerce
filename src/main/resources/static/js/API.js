/*
 * In this file we are goin to define all API calls 
 * for use in all web
 */  
var API = {
        USER_LOGIN : {
            type : 'POST',
            url : '/api/customer/login/'
        },
        USER_CHECK : {
            type : 'GET',
            url : '/api/customer/'
        },
        ADMIN_LOGIN : {
            type : 'POST',
            url : '/api/admin/login/'
        },
        ADMIN_CHECK : {
            type : 'GET',
            url : '/api/admin/'
        }    
};