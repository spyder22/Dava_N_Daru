<template>
     <div>
        <div>
            <Navbar/>
        </div>
        <div>
            <SubNavbar/>
        </div>
        <div class="recommondation" id="recommondation">
           <h3 class="heading"><span> ---Recommendations--- </span></h3>
           <div class="box-container">
           <Recommendations v-for='recommendation in recommendations' :key="recommendation.productId" :recommendation="recommendation"/>
           </div>
        </div>
        <div>
            <Footer/>
        </div>

    </div>
</template>

<script>
import Navbar from './Navbar'
import SubNavbar from './SubNavbar'
import Footer from './Footer'
import Recommendations from './Recommendations'
export default {
  name: 'home',
  components: {
    Navbar,
    SubNavbar,
    Footer,
    Recommendations
  },
  mounted () {
    console.log('Hi')
    this.$store.dispatch('getRecommendations')
  },
  computed: {
    recommendations () {
      return this.$store.state.recommendations
    }
  }
}
</script>

<style scoped>
 * {
    box-sizing: border-box;
  }
  .recommondation{
    min-height: 100vh;
    margin-top: 30px;
}

.recommondation .box-container{
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
  text-decoration-color: aqua;
}
</style>
