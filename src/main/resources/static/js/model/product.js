class Product {
    constructor(product){
        this.id = product.id;
        this.title = product.title;
        this.images = product.images;
        this.author = product.author;        
        this.price = product.price;
    }
    
    view(){
        return new ProductView(this).render();
    }
}

class ProductView {
    constructor(product){
        this.product = product;
    }
    
    render(){
        return `<div class="col-sm">
                     <div class="card text-center" style="width: 17rem; margin-left:auto;margin-right:auto;display:block;" data-target="#product" data-product-id="` + this.product.id + `">
                         <img class="card-img-top" src="images/book.jpg" alt="` + this.product.title + `">
                         <div class="card-body">
                             <h5 class="card-title">` + this.product.title + `</h5>
                             <p class="card-text">` + this.product.author + `</p>
                             <p class="card-text text-right" style="color: green;">` + this.product.price + ` &euro;</p>
                         </div>
                     </div>
                 </div>`;
    }
}

class ProductDescription {
    constructor(product){
        this.id = product.id;
        this.title = product.title;
        this.images = product.images;
        this.author = product.author;
        this.synopsis = product.synopsis;
        this.format = product.format;
        this.stock = product.stock;
        this.price = product.price;
    }
    
    view(){
        return new ProductDescriptionView(this).render();
    }
}

class ProductDescriptionView {
    constructor(product){
        this.product = product;
    }
    
    render(){
        return `<h1>Producto</h1>`;
    }
}