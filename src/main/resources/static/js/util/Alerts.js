/**
 * Creates a toast
 * title : Title for the toast, can use html
 * msj : Message for toast, can use html
 * type : Type of the toast
 *  + success
 *  + info
 *  + warning
 *  + error 
 * position: Position of the toast
 *  + top-right
 *  + bottom-right
 *  + bottom-left
 *  + top-left
 *  + top-full-width
 *  + bottom-full-width
 *  + top-center
 *  + bottom-center
 * hOff : Handler with toast disappears(includes buttons)
 * hOn : Handler when toast appears
 * hClick : Handler onClick in the toast
 * 
 * <!--Based on https://github.com/CodeSeven/toastr-->
 */
class Toast {
    constructor(title, msj, type, position, hOff, hOn, hClick){
        this.title = title;
        this.msj = msj;
        this.type = type;
        this.position = position;
        this.hOff = hOff;
        this.hOn = hOn;
        this.hClick = hClick;
    }
    
    show(){
        toastr.options = {
                "closeButton": true,
                "debug": false,
                "newestOnTop": false,
                "progressBar": true,
                "positionClass": "toast-"+this.position,
                "preventDuplicates": false,
                "showDuration": "300",
                "hideDuration": "1000",
                "timeOut": "3500",
                "extendedTimeOut": "1000",
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeIn",
                "hideMethod": "fadeOut"
              };
        toastr.options.onShown = this.hOn;
        toastr.options.onHidden = this.hOff;
        toastr.options.onclick = this.hClick;
        toastr[this.type](this.msj, this.title);
    }
}



var FORMCODES = {
        ERROR : 0,
        SUCCESS : 1
};

/*
 * This class can be used for show and clean
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
