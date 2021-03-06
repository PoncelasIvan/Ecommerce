class Sell {
    constructor(sell){
        this.id = sell.id;
        this.date = sell.date;
       
        switch(sell.state){
            case  'IN_PROGRESS':
                this.status = 'En camino';
                break;
            case 'RECEIVED' :
                this.status = 'Recibido';
                break;
            case 'COMPLETED':
                this.status = 'Completado';
                break;
        }

        this.products = sell.products;
        this.price = 0;
        for(let i in sell.products)
            this.price = this.price + sell.products[i].price * sell.products[i].cuantity;
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
        let anim = 'slideInUp';
        let books = '';
        let prod;
        let adminStatus = '';
        if($.cookie('ROLE') == 'ADMINISTRATOR'){
            adminStatus = `
                <div class="col-sm">
                    <div class="dropdown">   
                        <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenuButtonStatus-` + this.sell.id +`" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Cambiar estado</button>
                            <div class="dropdown-menu" aria-labelledby="dropdownMenuButtonStatus` + this.sell.id +`">
                                <a class="dropdown-item" href="#" data-action="administrator-update-status" data-sell-id="` + this.sell.id + `" data-sell-status="RECEIVED">Recibido</a>
                                <a class="dropdown-item" href="#" data-action="administrator-update-status" data-sell-id="` + this.sell.id + `" data-sell-status="IN_PROGRESS">En camino</a>
                                <a class="dropdown-item" href="#" data-action="administrator-update-status" data-sell-id="` + this.sell.id + `" data-sell-status="COMPLETED">Completado</a>

                            </div>
                     </div>
                </div>
            `;
        }
        for(let i in this.sell.products){
            prod =  this.sell.products[i];
            if($.cookie('ROLE') == 'ADMINISTRATOR')
                books += '<a class="dropdown-item" href="#" data-target="#product" data-back="#administrator" data-product-id="' + prod.id + '">' + prod.title + '</a>';
            else
                books += '<a class="dropdown-item" href="#" data-target="#product" data-back="#customer" data-product-id="' + prod.id + '">' + prod.title + '</a>';   
        }
        return `
              <li class="list-group-item animated ` + anim + `">
                  <div class="row">
                      <div class="col-sm">` + this.sell.date + `</div>
                      <div class="col-sm">` + this.sell.status + `</div>
                      <div class="col-sm">
                          <div class="dropdown">   
                              <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenuButton-` + this.sell.id +`" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Productos</button>
                              <div class="dropdown-menu" aria-labelledby="dropdownMenuButton` + this.sell.id +`">` + books + `</div>
                           </div>
                      </div>
                      <div class="col-sm">` + this.sell.price + ` &euro;</div>
                      ` + adminStatus + `
                  </div>
              </li>`;
    }
}

class AdminSell {
    
}

class AdminSellView {
    
}