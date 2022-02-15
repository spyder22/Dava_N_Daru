<template>
     <div>
        <div>
            <Navbar/>
        </div>
        <div class="imgcenter">
        <img src="@/assets/ddlogo.jpg" alt="logo">
        </div>
        <div>
            <Footer/>
        </div>

    </div>
</template>

<script>
import Navbar from './Navbar'
import Footer from './Footer'
export default {
  name: 'home',
  components: {
    Navbar,
    Footer
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
.imgcenter{
  text-align: center;
}
</style>
