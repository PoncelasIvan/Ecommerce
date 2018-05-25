class Sell {
    constructor(sell){
        this.id = sell.id;
        this.date = new Date(sell.date);
       
        switch(sell.state){
            case  'IN_PROGRESS':
                this.status = 'En camino';
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
        let anim = (this.sell.id % 2 == 0) ? 'fadeInLeft' : 'fadeInRight';
        let books = '';
        let prod;
        for(let i in this.sell.products){
            prod =  this.sell.products[i];
            books += '<a class="dropdown-item" href="#" data-target="#product" data-back="#customer" data-product-id="' + prod.id + '">' + prod.title + '</a>';
        }
        return `
              <li class="list-group-item animated ` + anim + `">
                  <div class="row">
                      <div class="col-sm">` + this.sell.date.getYear() + `</div>
                      <div class="col-sm">` + this.sell.status + `</div>
                      <div class="col-sm">
                          <div class="dropdown">   
                              <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenuButton-` + this.sell.id +`" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Productos</button>
                              <div class="dropdown-menu" aria-labelledby="dropdownMenuButton` + this.sell.id +`">` + books + `</div>
                           </div>
                      </div>
                      <div class="col-sm">` + this.sell.price + ` &euro;</div>
                  </div>
              </li>`;
    }
}