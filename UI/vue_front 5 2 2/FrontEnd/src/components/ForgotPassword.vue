<template>
    <div>
        <div > <Navbar/></div>
        <form>
          <div class = "new">
            <h3 class="head">Forgot Password</h3>

            <div class="form-group">
                <label>Email address</label>
                <input type="email" class="form-control form-control-lg" />
            </div>
            <div class = "submit-button">
            <button type="submit" class="btn btn-dark btn-lg btn-block">Reset password</button>
            </div>
          </div>
        </form>
    </div>
</template>
<script>

import Navbar from './Navbar'
export default {
  name: 'forgotpassword',
  components: {
    Navbar
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
.head{
      text-align: center;
  }
</style>
