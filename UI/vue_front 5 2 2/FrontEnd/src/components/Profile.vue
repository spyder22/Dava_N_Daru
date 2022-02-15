<template>
  <div class="profile">
    <div class="centers">
      <!-- <img src="@/assets/profilepic.png" height="100px" width="150px" class="profilepic" /> -->
      <h1>Profile</h1>
    </div>
    <div class="details">

        <h5>Name: {{user.name}}</h5><input type='text'/>

      <!-- <h3>Mobile Number:</h3><input type="tel" pattern="[789][0-9]{9}"/> -->
      <h5>Email Address: {{user.email}}</h5><input type='text'>
      <!-- <h3>Address:</h3><input type='text'/> -->
      <div class="buttons">
        <input type="submit" value="Edit Profile" />
        <input type="submit" value="orders" />
      </div>
    </div>
  </div>
</template>
<script>
export default{
  name: 'profile',
  data () {
    return {
      user: {
        name: '',
        email: ''
      }
    }
  },
  created () {
    this.user.name = localStorage.getItem('name')
    this.user.email = localStorage.getItem('email')
  }
}
</script>

<style>
.profile {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 400px;
  background: white;
  /* border: 2px solid black; */
  border-radius: 10px;
  /* box-shadow: 10px 10px 15px rgba(0, 0, 0, 0.05); */
}
.profile .details {
  display: flex;
  flex-direction: column;
  color: rgb(3, 1, 1);
  margin: 20px;
  text-align: left;
}
.profile .centers {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.profile .submit {
  width: 300px;
  height: 40px;
}
.profile .buttons {
  text-align: center;
  margin-top:20px;
}
.profile input[type="submit"] {
  width: 40%;

  height: 50px;

  border: 1px solid;

  background:rgb(36, 187, 207);

  border-radius: 20px;

  font-size: 18px;

  color: #e9f4fb;

  font-weight: 520;

  cursor: pointer;

  outline: none;
}

.profile input[type="submit"]:hover {
  border-color: #2691d9;

  transition: 0.5s;
}
.profile .profilepic {
  padding: 20px;
}

</style>
