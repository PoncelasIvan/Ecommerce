class Sell {
    constructor(sell){
        this.id = sell;
        /*this.date = sell.date;
        this.status = sell.status;
        this.products = sell.products;*/ 
    }
    
    view(){
        return new SellView(this).render();
    }
}

class SellView {
    constructor(sell){
        this.sell = sell;
    }
    
    render(){
        let id = this.sell.id;
        let anim = (this.sell.id % 2 == 0) ? 'fadeInLeft' : 'fadeInRight';
        return `
              <li class="list-group-item animated ` + anim + `">
                  <div class="row">
                      <div class="col-sm">` + 'Fecha de hoy' + `</div>
                      <div class="col-sm">` + 'pendiente de completar' + `</div>
                      <div class="col-sm">
                          <div class="dropdown">   
                              <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenuButton-` + id +`" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Productos</button>
                              <div class="dropdown-menu" aria-labelledby="dropdownMenuButton` + id +`">
                                  <a class="dropdown-item" href="#" data-target="#product" data-back="#customer" data-product-id="1">Libro 1</a>
                                  <a class="dropdown-item" href="#" data-target="#product" data-back="#customer" data-product-id="2">Libro 2</a>
                              </div>
                           </div>
                      </div>
                      <div class="col-sm">` + 'Precio '+ `&euro;</div>
                  </div>
              </li>`;
    }
}