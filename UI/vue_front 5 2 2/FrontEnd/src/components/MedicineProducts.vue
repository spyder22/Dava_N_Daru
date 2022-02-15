<template>
<div>
  <Navbar/>
 <div class="product" id="products">
    <h1 class="heading"><span>Our Products</span></h1>

    <div class="box-container">
      <ProductCart v-for='product in productsByMedicine' :key="product.productId" :product="product"/>
    </div>
  </div>
  <Footer/>
</div>
</template>
<script>
import Navbar from '@/components/Navbar'
import Footer from '@/components/Footer'
import ProductCart from '@/components/ProductCart'
export default {
  components: {
    Navbar,
    Footer,
    ProductCart
  },
  mounted () {
    this.$store.dispatch('getProductsByCategoryMedicine')
  },
  computed: {
    productsByMedicine () {
      return this.$store.state.products
    }
  }
}
</script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@100;300;400;500;600&display=swap');
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css');
@import url('https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css');

* {
    box-sizing: border-box;
  }
  .product{
    min-height: 100vh;
    margin-top: 30px;
}

.product .box-container{
    display: flex;
    align-items: center;
    justify-content: center;
    flex-wrap: wrap;
    padding:2rem 0;
    width: 85%;
    margin:0 auto;
}
.heading{
  text-align: center;
  color: rgb(1, 219, 190);
}
</style>
