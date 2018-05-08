var FORMCODES = {
        ERROR : 0,
        SUCCESS : 1
};
/*
 * This clases can be used for show and clean
 * error/success messages from a form
 */
class FormController {
    constructor(){}
    
    static showInInput(input, msg, code){
        let type;
        switch(code){
            case FORMCODES.ERROR :
                type = 'danger';
            break;
            case FORMCODES.SUCCESS :
                type = 'success';
            break;
        }
        input.parent().addClass("has-"+type);
        input.addClass('form-control-'+type);
        input.parent().append('<div class="form-control-feedback">' + msg + '</div>');
    
        
    }
    
    static clean(input) {
        input.parent().removeClass();
        input.parent().addClass('form-group');
        input.removeClass();
        input.addClass('form-control');
        input.parent().children('.form-control-feedback').remove();
    }
}

