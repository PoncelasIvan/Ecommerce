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
        return `
            <div class="col-sm animated bounceInLeft">
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
        let btnBuy = '';
        let btnFav = '';
        let btnEdit = '';
        switch($.cookie('ROLE')){
        case 'CUSTOMER' :
            btnBuy = '<button type="button" class="btn btn-lg btn-outline-success animated bounce infinite" title="Comprar" data-action="buy">Comprar</button>';
            btnFav = '<button type="button" class="btn btn-outline-warning  animated shake infinite" style="margin-left: 10px;" title="Añadir al carrito" data-action="favourite"><i class="far fa-star"></i> Añadir al carrito</button>'

            if(!this.product.stock > 0) {
                btnBuy = '<button type="button" class="btn btn-lg  btn-outline-danger" title="El producto esta agotado" disabled>Producto agotado</button>';
                btnFav = '';
            }
            break;
        case 'ADMINISTRATOR':
            btnEdit = '<button type="button" class="btn btn-lg  btn-outline-success" title="Guardar cambios">Guardar</button>';
            break;
        }
        
           
        return `
            <h1 class="animated bounceInLeft">` + this.product.title + `</h1>
            <div id="ipanema" class="animated zoomInRight carousel slide" data-ride="carousel" style="width: 60%; margin : 0 auto; margin-top: 40px;">
                <ol class="carousel-indicators">
                    <li data-target="#ipanema" data-slide-to="0" class="active"></li>
                    <li data-target="#ipanema" data-slide-to="1"></li>
                    <li data-target="#ipanema" data-slide-to="2"></li>
                </ol>
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img class="d-block w-100" src="images/book.jpg" alt="First slide">
                    </div>
                    <div class="carousel-item">
                        <img class="d-block w-100" src="images/book.jpg" alt="Second slide">
                    </div>
                    <div class="carousel-item">
                        <img class="d-block w-100" src="images/book.jpg" alt="Third slide">
                    </div>
                </div>
                <a class="carousel-control-prev" href="#ipanema" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Anterior</span>
                </a>
                <a class="carousel-control-next" href="#ipanema" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Siguiente</span>
                </a>
            </div>
            <h4>` + this.product.synopsis + `</h4>
            <div class="container-fluid" style="margin-top: 40px; margin-bottom: 40px;">
                <div class="row">
                    <div class="col-sm text-right">
                        <h4>Autor</h4>
                    </div>
                    <div class="col-sm text-left">
                        <h4>` + this.product.author + `</h4>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-sm text-right">
                        <h4>Formato</h4>
                    </div>
                    <div class="col-sm text-left">
                        <h4>` + this.product.format + `</h4>
                    </div>
                </div>
                
                <div class="row" style="color: green;">
                    <div class="col-sm text-right">
                        <h4>Precio</h4>
                    </div>
                    <div class="col-sm text-left">
                        <h4>` + this.product.price + ` &euro;</h4>
                    </div>
                </div>
            </div>
            <div>
            ` + btnBuy + btnFav + btnEdit + `  
            </div>`;
    }
}