<template>
  <div>
    <!-- <v-content> -->
    <router-view></router-view>
    <!-- </v-content> -->
  </div>
</template>

<script>
export default {
  name: "App",

  components: {},

  data: () => ({
    perms:[],
  }),
  created() {
    this.$store.commit("SET_USERNAME", this.CryptoJS.AES.decrypt(localStorage.getItem('username'),process.env.VUE_APP_CRYPTO_KEY).toString(this.CryptoJS.enc.Utf8));
    this.$store.commit("SET_FIRSTNAME", this.CryptoJS.AES.decrypt(localStorage.getItem('firstname'),process.env.VUE_APP_CRYPTO_KEY).toString(this.CryptoJS.enc.Utf8));
    this.$store.commit("SET_LASTNAME", this.CryptoJS.AES.decrypt(localStorage.getItem('lastname'),process.env.VUE_APP_CRYPTO_KEY).toString(this.CryptoJS.enc.Utf8));
    this.perms=localStorage.getItem('perms');
    this.setPermissions(this.perms);
    if(localStorage.getItem('token') != '')
      this.$store.commit("SET_USER_LOGIN",true);

  },
  methods: {
    setPermissions(list) {
      for (let i = 0; i < list.length; i++) {
        if (list[i].name == "ACCESS_LOCATION") {
         this.$store.commit("SET_ACCESS_LOCATION",true);
        }
        else if(list[i].name == "ACCESS_EMPLOYEE")
        {
          this.$store.commit("SET_ACCESS_EMPLOYEE",true);
        }
        else if(list[i].name == "ACCESS_USER_MANAGE")
        {
          this.$store.commit("SET_ACCESS_USER_MANAGE",true);
        }
        else if(list[i].name == "ACCESS_ROLE_MANAGEMENT")
        {
          this.$store.commit("SET_ACCESS_ROLE_MANAGEMENT",true);
        }
        else if(list[i].name == "ACCESS_PERMISSION_ASSIGNING")
        {
          this.$store.commit("SET_ACCESS_PERMISSION_ASSIGNING",true);
        }
         else if(list[i].name == "ACCESS_CREATE_COMPANY")
        {
          this.$store.commit("SET_ACCESS_CREATE_COMPANY",true);
        }
         else if(list[i].name == "ACCESS_USER_LOGS")
        {
          this.$store.commit("SET_ACCESS_USER_LOGS",true);
        }
      }
  }
  }
};
</script>
