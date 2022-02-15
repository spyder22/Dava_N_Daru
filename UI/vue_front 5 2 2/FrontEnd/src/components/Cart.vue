<template>
 <div class="carts">
     <Navbar/>
  <div class="cart">
      <h1 class="heading"><span>Cart-Items</span></h1>

    <div class="row mt-2 pt-3" v-for="item in cart" :key="item.product.productId">
      <div class="col-md-3">
        <img :src="item.product.image" class="productimg"/>
      </div>
      <div class="col-md-5 px-3">
        <div class="card-block px-3">
          <div class="df">
            <h6 class="productTitle">
            {{ item.product.description }}
          </h6>
          <div class="price">
            <div> ₹ </div> {{ item.product.merchantList[0].price * item.quantity }}
          </div>
          </div>
          <div class="ratingpart">
            Ratings;- {{ item.product.merchantList[0].productRating }}
          </div>
          <p class="quantity">Quantity : {{ item.quantity }}</p>
        </div>
        <div>
          <p class="font-weight-bold priceperunit">
            ₹ {{item.product.merchantList[0].price}} per unit
          </p>
        </div>
        <div >
              <div>
                  <div class="remove" ><button @click="removeProductFromCart(product)">Remove</button></div>
              </div>
        </div>

      </div>
      <div ></div>
      <div class="col-12"><hr /></div>
    </div>

    <!-- display the price -->
    <div class="total">total price : {{ cartTotalPrice }}
    </div >
    <button class="adds" >BUY NOW</button>
  </div>
      <Footer/>
  </div>
</template>
<script>
import Navbar from '@/components/Navbar'
import Footer from '@/components/Footer'
export default {
  data () {
    return {
      quantity: 0
    }
  },
  components: {
    Navbar,
    Footer
  },
  computed: {
    cart () {
      return this.$store.state.cart
    },
    cartTotalPrice () {
      return this.$store.getters.cartTotalPrice
    }
  },
  created () {
    this.$store.dispatch('getCartItemsByEmail')
  },
  methods: {
    removeProductFromCart (product) {
      this.$store.dispatch('removeProductFromCart', product)
    },
    getCartItemByEmail () {
      this.$store.dispatch('getCartItemByEmail', localStorage.getItem('email'))
    }
  }
}
</script>

<style>
@import url('https://bootstrap/dist/css/bootstrap.min.css');
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans:wght@700&family=Poppins:wght@400;500;600&display=swap");
@import url("https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css");
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Poppins", Helvetica, Arial, sans-serif;
}
.cart {
  min-height: 100vh;
  margin-top: 20px;
}
.remove{
  text-align: center;
  margin-top: 10px;
  padding-left: 250px;
  color: rgb(248, 6, 6);
}
.ratingpart{
  /* text-align: center; */
  padding-left: 50px;
  color: rgb(255, 174, 0);
}
.cart .heading{
    text-align: center;
    color: rgb(44, 160, 160);
    margin-top: 50px;
    /* margin-bottom: 2rem; */
    position: relative;
}
.cart .texthead{
  font-size: 30px;
  text-align: center;
  margin-top: 30px;
  margin-bottom: 30px;
}
.cart .productimg{
  width: 300px;
  height: 300px;
  align-self: left;
  margin-left: 100px;
  padding: 15px;
}
.cart .productTitle{
  margin-top: 50px;
  font-size: 23px;
  text-align: left;
  padding-left: 50px;
}
.cart .price{
  margin-top: 50px;
  margin-left: 150px;
  font-size: 20px;
  text-align: left;
  color: rgb(59, 75, 226);
      display: flex;
    justify-content: space-between;
}
.cart .df{

  display: flex;
}
.cart .quantity{
  font-size: 15px;
  text-align: left;
  padding-left: 50px;
}
.cart .priceperunit{
  font-size: 18px;
    text-align: left;
    margin-left: 10px;
    padding-left: 50px;
}
.cart .total{
  font-size: 30px;
   height:70px;
    display: table-cell;
    vertical-align: bottom;
    display: flex;
    justify-content: flex-end;
    margin-top: 10px;
    margin-right:30px;
}
.adds {
  box-sizing: border-box;
  appearance: none;
  border: 2px solid black;
  background-color: transparent;
  border-radius: 0.6em;
  cursor: pointer;
  line-height: 1;
  margin-bottom: 20px;
  padding: 10px;
  margin-left: 1250px;
  font-size: 20px;
}
</style>
