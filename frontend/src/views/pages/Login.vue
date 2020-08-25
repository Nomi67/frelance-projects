<template>
  <div>
    <!-- <v-content class=" mr-n9 mt-n2" > -->
    <!-- <v-row class="mt-n2 mr-n11">
      <v-col cols="3" />

      <v-col cols="9">
        <v-img src="../assets/uconnect.png" position="top" class="mt-n3"></v-img>
      </v-col>
    </v-row>-->
    <v-app>
      <v-content>
        <v-row class="mx-2">
          <v-col cols="3" />
          <v-col cols="12" xs="12" sm="12" md="6" lg="6" class="elevation-6 mt-14">
            <v-row>
              <v-col cols="3" />
              <v-col cols="6">
                <h3>Welcome to uconnect</h3>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12">
                <v-text-field label="Username" solo required outlined dense v-model="username"></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  v-model="password"
                  :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                  :type="show1 ? 'text' : 'password'"
                  outlined
                  solo
                  dense
                  label="Password"
                  @click:append="show1 = !show1"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12">
                <v-btn block color="primary" dark @click="login()">Login</v-btn>
              </v-col>
            </v-row>
            <v-divider />
            <v-row>
              <v-col cols="12">
                <v-btn block color="primary" to="/forget">Forget Password</v-btn>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
        <v-snackbar v-model="snackbar" :timeout="timeout">
          {{ text }}
          <v-btn color="blue" text @click="snackbar = false">Close</v-btn>
        </v-snackbar>
        <!-- </v-content> -->
      </v-content>
    </v-app>
  </div>
</template>
<script>
import axios from "axios";
export default {
  data() {
    return {
      show1: false,
      text: "Invalid Credientials",
      snackbar: false,
      username: "",
      password: ""
    };
  },
  methods: {
    login() {
      axios
        .get(
          process.env.VUE_APP_SERVER_ADDRESS +
            "/auth/login?username=" +
            this.username +
            "&password=" +
            this.password
        )
        .then(response => {
          if (response.data.code == "0000") {
            this.setPermissions(response.data.data.user.role.permissions);
            this.$store.commit("SET_ROLE", response.data.data.role);
            this.$store.commit("SET_USER_LOGIN", true);

            localStorage.setItem("token", response.data.data.jwt);
            this.$store.commit(
              "SET_USERNAME",
              response.data.data.user.userName
            );
            this.$store.commit(
              "SET_FIRSTNAME",
              response.data.data.user.firstName
            );
            this.$store.commit(
              "SET_LASTNAME",
              response.data.data.user.lastName
            );
            this.$store.commit(
              "SET_DESIGNATION",
              response.data.data.user.designation
            );
            localStorage.setItem(
              "email",
              this.CryptoJS.AES.encrypt(
                response.data.data.user.email,
                process.env.VUE_APP_CRYPTO_KEY
              ).toString()
            );
            localStorage.setItem(
              "username",
              this.CryptoJS.AES.encrypt(
                response.data.data.user.userName,
                process.env.VUE_APP_CRYPTO_KEY
              ).toString()
            );
            localStorage.setItem(
              "firstname",
              this.CryptoJS.AES.encrypt(
                response.data.data.user.firstName,
                process.env.VUE_APP_CRYPTO_KEY
              ).toString()
            );
            localStorage.setItem(
              "lastname",
              this.CryptoJS.AES.encrypt(
                response.data.data.user.lastName,
                process.env.VUE_APP_CRYPTO_KEY
              ).toString()
            );
            if (response.data.data.role == "ADMIN") {
              this.$store.commit("SET_ROLE_ADMIN", true);
            } else if (response.data.data.role == "MANAGER") {
              this.$store.commit("SET_ROLE_MANAGER", true);
            }
            this.$router.push("/");
          } else if (response.data.code == "1001") {
            this.text = "Invalid Credientials";
            this.snackbar = true;
          }
        })
        .catch(() => {
          this.text = "Server Side Error";
          this.snackbar = true;
        });
    },
    setPermissions(list) {
      for (let i = 0; i < list.length; i++) {
        if (list[i].name == "ACCESS_LOCATION") {
          this.$store.commit("SET_ACCESS_LOCATION", true);
        } else if (list[i].name == "ACCESS_EMPLOYEE") {
          this.$store.commit("SET_ACCESS_EMPLOYEE", true);
        } else if (list[i].name == "ACCESS_USER_MANAGE") {
          this.$store.commit("SET_ACCESS_USER_MANAGE", true);
        } else if (list[i].name == "ACCESS_ROLE_MANAGEMENT") {
          this.$store.commit("SET_ACCESS_ROLE_MANAGEMENT", true);
        } else if (list[i].name == "ACCESS_PERMISSION_ASSIGNING") {
          this.$store.commit("SET_ACCESS_PERMISSION_ASSIGNING", true);
        } else if (list[i].name == "ACCESS_CREATE_COMPANY") {
          this.$store.commit("SET_ACCESS_CREATE_COMPANY", true);
        } else if (list[i].name == "ACCESS_USER_LOGS") {
          this.$store.commit("SET_ACCESS_USER_LOGS", true);
        }
      }
    }
  }
};
</script>
<style scoped>
a {
  text-decoration: none;
}
</style>