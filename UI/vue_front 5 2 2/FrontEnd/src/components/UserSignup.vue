<template>
    <div>
        <div > <Navbar/></div>
        <form>
            <h3 class="head"> User Sign Up</h3>
        <div class = "new">
            <div class="form-group">
                <label>Full Name</label>
                <input type="username" name="userName" v-model="name" class="form-control form-control-lg" />
            </div>

            <div class="form-group">
                <label>Email address</label>
                <input type="email" name="email" v-model="email" class="form-control form-control-lg" />
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" v-model="password" class="form-control form-control-lg" />
            </div>
            <!-- <div class="form-group">
                <label>Confirm Password</label>
                <input type="password" name="cpassword" v-model="cpassword" class="form-control form-control-lg" />
            </div> -->
            <div class="submit-button">
            <button type="button" v-on:click="addUser()" class="btn btn-dark btn-lg btn-block">Sign Up</button>
            </div>
            <!-- <p class="forgot-password text-right">
                Already registered
                <router-link :to="{name: 'login'}">sign in?</router-link>
            </p> -->

            <div>
                    <button @click="login()" id="google-button" class="google btngoogle">
                    <i class="fa fa-google"></i> &nbsp; Google
                    </button>
            </div>
        </div>
        </form>
    </div>
</template>

<script>

import Navbar from './Navbar'
import axios from 'axios'

export default {
  name: 'App',
  components: {
    Navbar
  },
  // eslint-disable-next-line no-dupe-keys
  name: 'google',
  data () {
    return {
      islogin: false,
      name: '',
      email: '',
      password: ''
    }
  },
  methods: {
    async logOut () {
      const result = this.$gAuth.signOut()
      console.log('result', result)
    },
    // sending details to backend
    addUser () {
      let result = axios.post('http://localhost:1000/user/signup', {
        name: this.name,
        email: this.email,
        password: this.password})

      console.log(result)
      console.log('I got clicked ! ->', this.name, this.email, this.password)
    }
  },
  async login1 () {
    const googleUser = await this.$gAuth.signIn()
    console.log('googleUser', googleUser)
    console.log('getId', googleUser.getid())
    console.log('getBaseProfile', googleUser.getBaseProfile())
    console.log('getAuthResponse', googleUser.getAuthResponse())
    console.log('getAuthResponse$G', this.$getAuth.GoogleAuth.currentUser.get().getAuthResponse())
    this.isLogin = this.$gAuth.isAuthorized
  }
}

</script>

<style scoped>
.new {
    width: 450px;
    margin: auto;
  }
  .submit-button{
    margin-top: 20px;
}
  .new2{
    text-align: center;
    color: rgb(7, 6, 6);
    font-size: 15px;
    margin-top: -10px;
  }
  .head{
      text-align: center;
  }
  .forgot-password,
  .forgot-password a {
    text-align: right;
    font-size: 13px;
    padding-top: 10px;
    color: #7a7a7a;
    margin: 0;
  }
  .forgot-password a {
    color: #1220e6;
  }
.social-icons {
    text-align: center;
    font-family: "Open Sans";
    font-weight: 300;
    font-size: 1.5em;
    color: #222222;
  }
  .social-icons ul {
    list-style: none;
    margin: 0;
    padding: 0;
  }
  .social-icons ul li {
    display: inline-block;
    zoom: 1;
    width: 65px;
    vertical-align: middle;
    border: 1px solid #e3e8f9;
    font-size: 15px;
    height: 40px;
    line-height: 40px;
    margin-right: 5px;
    background: #f4f6ff;
  }
  .social-icons ul li a {
    display: block;
    font-size: 1.4em;
    margin: 0 5px;
    text-decoration: none;
  }
  .social-icons ul li a i {
    -webkit-transition: all 0.2s ease-in;
    -moz-transition: all 0.2s ease-in;
    -o-transition: all 0.2s ease-in;
    -ms-transition: all 0.2s ease-in;
    transition: all 0.2s ease-in;
  }
  .social-icons ul li a:focus i,
  .social-icons ul li a:active i {
    transition: none;
    color: #352e2e;
  }
  .google {
  background-color: #dd4b39;
  color: white;
}

.btngoogle {
width: 100%;
padding: 12px;
border: none;
border-radius: 4px;
margin: 5px 0;
opacity: 0.85;
display: inline-block;
font-size: 17px;
line-height: 20px;
text-decoration: none; /* remove underline from anchors */
}
</style>
