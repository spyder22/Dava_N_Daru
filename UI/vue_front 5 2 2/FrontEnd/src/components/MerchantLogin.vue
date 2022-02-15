<template>
    <div>
        <div> <Navbar/></div>
            <form>
                <h3 class="head">Merchant Sign In</h3>
            <div class = "new">
                <div class="form-group">
                    <label>User Name</label>
                    <input type="username" name="userName" v-model="userName" class="form-control form-control-lg" />
                </div>

                <div class="form-group">
                    <label>Email address</label>
                    <input type="email" name="email" v-model="email" class="form-control form-control-lg" />
                </div>

                <div class="form-group">
                    <label>Password</label>
                    <input type="password" name="password" v-model="password" class="form-control form-control-lg" />
                </div>
                <p class="forgot-password text-right mt-2 mb-4">
                    <router-link to="/merchant-forgot-password">Forgot password ?</router-link>
                </p>
                <div class="submit-button">
                    <button type="button"   v-on:click="checkAdmin()" value="Home" class="btn btn-dark btn-lg btn-block">Sign In</button>
                </div>
                <div class = "new2 ">
                    <p>Don't have an account? <router-link to="/merchantsignup">Sign up Here </router-link></p>
                </div>
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

import Footer from './Footer'
// import axios from 'axios'
// import swal from 'sweetalert'

export default {
  name: 'App',
  components: {
    Navbar,
    Footer
  },
  // eslint-disable-next-line no-dupe-keys
  name: 'google',
  data () {
    return {
      islogin: false,
      userName: '',
      email: '',
      password: ''
    }
  },
  methods: {
    async logOut () {
      const result = this.$gAuth.signOut()
      console.log('result', result)
    },
    checkAdmin () {
      console.log('I got clicked ! ->', this.userName, this.email, this.password)
    },
    // async checkAdminDetails (e) {
    //   // e.preventDefault()
    //   console.log(this.username, this.password)
    //   const body = {
    //     username: this.username,
    //     password: this.password
    //   }
    //   await axios
    //     .post('  ', body).then((res) => {
    //       console.log('yes i got u')
    //       console.log(res.data)
    //       swal({
    //         text: 'login successfull',
    //         icon: 'success'
    //       })
    //       this.$emit('fetchData')
    //       this.$router.push({name: 'Home'})
    //     })
    // },
    async login () {
      const googleUser = await this.$gAuth.signIn()
      console.log('googleUser', googleUser)
      console.log('getId', googleUser.getid())
      console.log('getBaseProfile', googleUser.getBaseProfile())
      console.log('getAuthResponse', googleUser.getAuthResponse())
      console.log('getAuthResponse$G', this.$getAuth.GoogleAuth.currentUser.get().getAuthResponse())
      this.isLogin = this.$gAuth.isAuthorized
    }
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
    text-align: center;
}
  .new2{
    text-align: center;
    color: rgb(7, 6, 6);
    font-size: 15px;
    margin-top: 10px;
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
