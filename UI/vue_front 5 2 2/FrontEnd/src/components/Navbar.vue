<template>
    <div class="main-navbar">
      <nav class="navbar shadow bg-white rounded justify-content-between flex-nowrap flex-row fixed-top">
      <div class="container">
        <a class="navbar-brand float-left" href="/#/">
          <div class="logo">
            <img id="logo-child" src="https://www.kindpng.com/picc/m/234-2347418_5th-edition-d-d-logo-hd-png-download.png" alt="logo" height="35px" width="70">
            <!-- <h5 >dawa & daru</h5> -->
          </div>
        </a>
        <ul class="nav navbar-nav flex-row float-right">
        <div class="wrap">
           <div class="search">
           <input type="text" class="searchTerm" placeholder="Search for the products ?">
            <button type="submit" class="searchButton">
              <i class="fa fa-search"></i>
            </button>
           </div>
          </div>
          <li class="nav-item">
            <router-link class="btn btn-outline-dark" to="/login">Login </router-link>
            <!-- <a href="/#/login" class="btn btn-outline-dark" >Login</a> -->
          </li>
          <div>
            &nbsp;
            &nbsp;
            &nbsp;
            &nbsp;
          </div>
          <div class="icon">
              <!-- <a href="#" class="fas fa-shopping-cart" style="font-size:35px;color:black"></a> -->
              <router-link class="fas fa-shopping-cart" style="font-size:35px;color:black" to="/cart"></router-link>
          </div>
          <div>
            &nbsp;
            &nbsp;
            &nbsp;
            &nbsp;
          </div>
          <div class="icon">
              <!-- <div id="login-btn" class="fas fa-user" style="font-size:35px" to="/profile"></div> -->
              <router-link class="fas fa-user" style="font-size:35px;color:black" to="/profile"></router-link>
          </div>
          <div>
            &nbsp;
            &nbsp;
            &nbsp;
            &nbsp;
          </div>
          <div>
          <li class="nav-item">
            <!-- <router-link class="btn btn-outline-dark" to="/login">LogOut </router-link> -->
            <div class="btn btn-outline-dark" @click="gologout()">LogOut</div>
            <!-- <a href="/#/login" class="btn btn-outline-dark" >Login</a> -->
          </li>
          </div>
          <!-- <li class="nav-item">
            <router-link class="btn btn-outline-info" to="/signup">Sign up</router-link>
          </li> -->
        </ul>
      </div>
    </nav>
    </div>
</template>

<script>
export default {
  name: 'Navbar',
  methods: {
    gologout () {
      localStorage.removeItem('name')
      localStorage.removeItem('email')
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
@import url(https://fonts.googleapis.com/css?family=Open+Sans);

@import url("https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css");

@import url("https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css");

img{
    border-radius: 50%;
}
.main-navbar{
    margin-bottom: 100px;
}
.search {
  width: 100%;
  position: relative;
  display: flex;
}

.searchTerm {
  width: 100%;
  border: 3px solid #25cbd1;
  border-right: none;
  border-radius: 5px 0 0 5px;
  outline: none;
  color: #9DBFAF;
}

.searchTerm:focus{
  color: #080808;
}

.searchButton {
  width: 40px;
  height: 36px;
  border: 1px solid #07c1da;
  background: #23dde4;
  text-align: center;
  color: #fff;
  border-radius: 0 5px 5px 0;
  cursor: pointer;
  font-size: 20px;
}

/*Resize the wrap to see the search bar change!*/
.wrap{
  width: 30%;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>
