<template>
  <div>
    <div>
          <div > <Navbar/></div>
          <div class = "new">
            <div class="child1">
              <!-- <div> <h3>Sign in </h3> </div> -->
              <router-link class="btn btn-outline-dark" to="/merchant">MERCHANT</router-link>
            </div>
          </div>
      </div>
      <div class="bg">
          <img src="@/assets/ddlogo.jpg" alt="logo">
      </div>
  </div>
</template>
<script>

import Navbar from './Navbar'
export default {
  name: 'login',
  components: {
    Navbar
  }
}

</script>

<style scoped>
.new {
    width: 600px;
    margin: auto;
  }
  .child1{
    display: flex;
    justify-content: space-between;
    border: 5px solid;
    padding: 10px;
    box-shadow: 15px 20px #888888;
  }
  img {
    margin-top: 40px;
    margin-left:420px;
    margin-bottom:40px;
    height: 600px;
    border-radius: 50%;
  }
</style>
