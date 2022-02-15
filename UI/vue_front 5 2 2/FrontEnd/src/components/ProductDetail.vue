<template>
  <div>
     <Navbar/>
    <div class="detail">
      <div class="side">
        <div>
          <img
            :src="product.image"
            width="400px"
            height="400px"
            class="furniture"
          />
        </div>
        <div class="leftpad">
          <h4 class="heading4">{{ product.productName }}</h4>
          <!-- <div class="stars">
            <h5>Rating:</h5>
            <i><i>{{ product.rating.rate }}</i>/5.0</i>
          </div> -->
          <div>
          <h5 class="heading5">Description:</h5>
          <p class="para">{{ product.description }}
          </p>
          </div>
          <div>
          <h5 class="heading5">Category Name:</h5>
          <p class="para">{{ product.merchantList[0].price }}
          </p>
          </div>
          <div class="p-list">
            <span> M.R.P. :</span>
            <span class="price"> Rs. {{ product.merchantList[0].price }}</span>
          </div>
          <div class="dropdown">
            <span for="merchants" class="increasesize">Choose a merchant:</span>
            <select name="merchants" id="merchants">
              <option value="hide">-- Merchants --</option>
              <option value="m1">merchant1</option>
              <option value="m2">merchant2</option>
              <option value="m3">merchant3</option>
              <option value="m4">merchant4</option>
            </select>
          </div>
          <div class="flexop">
            <div class="button1">
            <a><button class="btn btn-info"><i class="fa fa-shopping-cart" style="font-size:18px;color:black;" @click="addToCart()">Add to Cart</i></button></a>
            </div>
            <div class="button2">
            <a><button class="btn btn-info"><i style="font-size:18px;color:black;" @click="addToCart()">Buy Now</i></button></a>
            </div>
          </div>
        </div>
      </div>
    </div>
    <Footer/>
  </div>
</template>
<script>
import swal from 'sweetalert'
import Navbar from '@/components/Navbar'
import Footer from '@/components/Footer'
export default {
  props: ['productId'],
  components: {
    Navbar,
    Footer
  },
  computed: {
    product () {
      return this.$store.state.product
    }
  },
  mounted () {
    let productId = this.$route.params.id
    console.log(productId)
    this.$store.dispatch('getProduct', productId)
  },
  methods: {
    addToCart () {
      this.$store.dispatch('addProductToCart', {
        product: this.product,
        quantity: 1
      })
      swal({
        text: 'Added to the cart, Go check it!',
        icon: 'success'
      })
    }
  }
}
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Poppins:wght@100;300;400;500;600&display=swap");
@import url("https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css");
@import url("https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css");

.detail {
  min-height: 80vh;
  margin-top: 30px;
  padding-left: 15px;
}
.detail .side {
  display: flex;
  padding: 5px;
}
.detail .furniture {
  padding: 15px;
  margin-left: 30px;
}
.detail .heading4 {
  padding: 15px;
  margin-top: 30px;
  font-size: 22px;
  text-align:center;
  color: #000000;
}
.detail .heading5 {
  padding: 5px;
  font-size: 18px;
  text-align: left;
  color: #000000;
}
.detail .para {
  padding: 5px;
  font-size: 18px;
  text-align: left;
  color: #1479d8;
}
.detail .quote {
  margin: 15px;
}
.detail .stars {
  color: orange;
  font-size: 20px;
  text-align: left;
}
.detail .p-list span {
  margin-right: 15px;
  font-size: 20px;
}
.detail .p-list span.price {
  font-size: 22px;
  color: #318234;
}
.detail .p-list {
  text-align: left;
  padding: 5px;
}
.detail .leftpad {
  padding-left: 15px;
}
.detail .increasesize{
    font-size: 15px
}
.detail .dropdown{
    text-align: left;
    margin: 10px;
}
.detail .btn-sm{
  width: 150px;
  height:40px;
  text-align: center;
  font-size: 150%;
}
.detail a i{
  color: white;

}
.buttonstyle{
  width: 150px;
  height: 140px;
  text-align: center;
  margin-top: 0px;
  padding: 2px;
}
.button1{
  margin-top: 51px;
}
.button2{
  margin-right: 20px;
  margin-top: 50px;
}
.flexop{
  display: flex;
  justify-content: space-between;
  /* margin-right: 300px;
  padding: 5px;  */
}
</style>
