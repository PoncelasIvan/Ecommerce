class ShoppingCart {
    constructor(){
        this.products = [];
    }
    
    add(product){
        this.push(product);
    }
    
    remove(id){
        for(let i in this.products){
            if(products[i].id == id) this.products.splice(i, 1);
        }
    }
    
    view(){
        return new ShoppingCartView(this).render();
    }
}

class ShoppingCartView {
    constructor(shoppingCart){
        this.shoppingCart = shoppingCart;
    }
    
    render(){
        return ``;
    }
}