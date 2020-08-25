<template>
  <div>
    <v-card class="elevation-6">
      <v-card-title class="primary white--text text--darken-2">Password reset request</v-card-title>
      <v-data-table
        :headers="headers"
        :items="resetRequests"
        :loading="resetLoading"
        sort-by="username"
        class="elevation-1"
        hide-default-footer
      >
        <template v-slot:item.actions="{ item }">
          <v-btn @click="changePassword(item.id)" small color="primary">Reset Password</v-btn>
        </template>
      </v-data-table>
      <div class="text-xs-center pt-2 md4 lg4">
        <v-pagination
          @input="paginationChangeHandlerOfReset"
          :value="resetPagination.page"
          :length="resetPages"
          :total-visible="7"
        ></v-pagination>
      </div>
    </v-card>

    <v-card class="mt-5 elevation-6">
      <v-card-title class="primary white--text text--darken-2">
        <v-col cols="12" xs="12" md="6" lg="6">Users</v-col>
        <v-col cols="12" xs="12" md="6" lg="6">
          <v-btn color="primary darken-3" @click="addUserDialog()" block dark>Add New User</v-btn>
        </v-col>
      </v-card-title>
      <v-data-table
        :headers="userHeaders"
        :items="users"
        :loading="userLoading"
        sort-by="username"
        class="elevation-1"
        hide-default-footer
      >
        <template v-slot:item.actions="{ item }">
          <v-btn @click="shadowUser(item)" small color="primary">Shadow User</v-btn>&nbsp;&nbsp;
          <v-btn @click="editUserData(item)" small color="primary">Edit</v-btn>
        </template>
        <template #item.name="{ item }">{{ item.firstName }} {{ item.lastName }}</template>
      </v-data-table>

      <div class="text-xs-center pt-2 md4 lg4">
        <v-pagination
          @input="paginationChangeHandlerOfUser"
          :value="userPagination.page"
          :length="userPages"
          :total-visible="7"
        ></v-pagination>
      </div>
    </v-card>

    <v-dialog v-model="dialog" max-width="800">
      <v-card class="text-center">
        <v-card-title class="primary white--text text--darken-2">
          <v-col cols="12" xs="12" md="6" lg="6">Edit User</v-col>
          <v-col cols="12" xs="12" md="6" lg="6">
            <v-btn color="red" @click="disableUser()">Disable User</v-btn>
          </v-col>
        </v-card-title>
        <v-card-text>
          <v-row justify="center">
            <v-col cols="12" md="12">
              <v-form>
                <v-container class="py-0">
                  <v-row>
                    <v-col cols="12" md="6">
                      <v-text-field
                        outlined
                        v-model="editUser.firstName"
                        dense
                        class="purple-input"
                        label="First Name"
                      />
                    </v-col>

                    <v-col cols="12" md="6">
                      <v-text-field
                        outlined
                        v-model="editUser.email"
                        dense
                        label="Email Address"
                        class="purple-input"
                      />
                    </v-col>

                    <v-col cols="12" md="6">
                      <v-text-field
                        outlined
                        v-model="editUser.phone"
                        dense
                        label="Telephone"
                        class="purple-input"
                      />
                    </v-col>

                    <v-col cols="12" md="6">
                      <v-text-field
                        outlined
                        v-model="editUser.password"
                        dense
                        label="Password"
                        class="purple-input"
                      />
                    </v-col>

                    <v-col cols="12" class="text-right">
                      <v-btn
                        color="primary"
                        class="mr-0"
                        @click="submitUpdateUserData()"
                      >Update User</v-btn>
                    </v-col>
                  </v-row>
                </v-container>
              </v-form>
              <!-- </base-material-card> -->
            </v-col>
          </v-row>
          <!-- </v-container> -->
        </v-card-text>

        <v-card-actions>
          <v-spacer />

          <v-btn color="error" text @click="dialog = false">Close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="newUserDialog" max-width="800">
      <v-card class="text-center">
        <v-card-title class="primary white--text text--darken-2">Add New User</v-card-title>
        <v-card-text>
          <v-row justify="center">
            <v-col cols="12" md="12">
              <v-form>
                <v-container class="py-0">
                  <v-row>
                    <v-col cols="12" xs="12" md="6" lg="6">
                      <v-text-field
                        outlined
                        dense
                        v-model="newuser.username"
                        solo
                        class="purple-input"
                        label="User Name"
                      />
                    </v-col>

                    <v-col cols="12" xs="12" md="6" lg="6">
                      <v-text-field
                        outlined
                        dense
                        v-model="newuser.firstName"
                        solo
                        label="First Name"
                        class="purple-input"
                      />
                    </v-col>

                    <v-col cols="12" xs="12" md="6" lg="6">
                      <v-text-field
                        outlined
                        dense
                        v-model="newuser.lastName"
                        solo
                        label="Last Name"
                        class="purple-input"
                      />
                    </v-col>

                    <v-col cols="12" xs="12" md="6" lg="6">
                      <v-text-field
                        outlined
                        dense
                        solo
                        v-model="newuser.email"
                        label="E-mail"
                        class="purple-input"
                      />
                    </v-col>
                    <v-col cols="12" xs="12" md="6" lg="6">
                      <v-text-field
                        outlined
                        dense
                        solo
                        v-model="newuser.password"
                        label="Password"
                        class="purple-input"
                      />
                    </v-col>
                    <v-col cols="12" xs="12" md="6" lg="6">
                      <v-text-field
                        outlined
                        dense
                        solo
                        v-model="newuser.designation"
                        label="Designation"
                        class="purple-input"
                      />
                    </v-col>
                    <v-col cols="12" xs="12" md="6" lg="6">
                      <v-text-field
                        outlined
                        dense
                        solo
                        v-model="newuser.phone"
                        label="Phone"
                        class="purple-input"
                      />
                    </v-col>
                    <v-col cols="12" xs="12" md="6" lg="6">
                      <v-text-field outlined dense v-model="newuser.company" solo label="Company" />
                    </v-col>
                    <v-col cols="12" xs="12" md="6" lg="6">
                      <v-autocomplete
                        v-model="newuser.role"
                        :items="roles"
                        outlined
                        dense
                        solo
                        label="Role"
                        clearable
                      ></v-autocomplete>
                    </v-col>
                    <v-col cols="12" xs="12" md="6" lg="6">
                      <v-btn block color="primary" class="mr-0" @click="addNewUser()">Create User</v-btn>
                    </v-col>
                  </v-row>
                </v-container>
              </v-form>
              <!-- </base-material-card> -->
            </v-col>
          </v-row>
          <!-- </v-container> -->
        </v-card-text>

        <v-card-actions>
          <v-spacer />

          <v-btn color="error" text @click="newUserDialog = false">Close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-snackbar v-model="snackbar" :timeout="timeout">
      {{ text }}
      <v-btn color="blue" text @click="snackbar = false">Close</v-btn>
    </v-snackbar>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data: () => ({
    userPagination: { totalItems: null, rowsPerPage: 10, page: 1 },
    dialog: false,
    newUserDialog: false,
    snackbar: false,
    text: "",
    resetLoading: false,
    userLoading: false,
    resetRequests: [],
    editUser: {},
    users: [],
    roles: [],
    newuser: {
      username: "",
      firstName: "",
      lastName: "",
      email: "",
      designation: "",
      password: "",
      phone: "",
      company: "",
      createdBy: "",
      role: ""
    },
    resetPagination: { totalItems: null, rowsPerPage: 10, page: 1 },
    headers: [
      {
        text: "Username",
        align: "start",
        sortable: false,
        value: "username"
      },
      { text: "Company", value: "company" },
      { text: "Email", value: "email" },
      { text: "", value: "actions", sortable: false }
    ],
    userHeaders: [
      {
        text: "Username",
        align: "start",
        sortable: false,
        value: "userName"
      },
      { text: "Name", value: "name" },
      { text: "Email", value: "email" },
      { text: "Last Access Date", value: "lastLogin" },
      { text: "Created", value: "created" },
      { text: "Created By", value: "createdBy" },
      { text: "", value: "actions", sortable: false }
    ],
    desserts: []
  }),
  computed: {
    resetPages() {
      if (
        this.resetPagination.rowsPerPage == null ||
        this.resetPagination.totalItems == null
      ) {
        return 0;
      }

      return Math.ceil(
        this.resetPagination.totalItems / this.resetPagination.rowsPerPage
      );
    },
    userPages() {
      if (
        this.userPagination.rowsPerPage == null ||
        this.userPagination.totalItems == null
      ) {
        return 0;
      }

      return Math.ceil(
        this.userPagination.totalItems / this.userPagination.rowsPerPage
      );
    }
  },
  created() {
    this.getPasswordResetRequest();
    this.getUserData();
  },

  methods: {
    getPasswordResetRequest() {
      this.resetLoading = true;
      axios
        .get(
          process.env.VUE_APP_SERVER_ADDRESS +
            "/password/get-all?pageNo=" +
            (this.resetPagination.page - 1) +
            "&pageSize=" +
            this.resetPagination.rowsPerPage +
            "&username=" +
            this.$store.state.username,
          {
            headers: { authentication: "nomi " + localStorage.getItem("token") }
          }
        )
        .then(response => {
          if (response.data != null) {
            this.resetRequests = response.data.content;
            this.resetPagination.totalItems = response.data.totalElements;
            this.resetLoading = false;
          }
        });
    },
    paginationChangeHandlerOfReset(pageNumber) {
      this.resetPagination.pageNumber = pageNumber;
      this.getPasswordResetRequest();
    },
    changePassword(id) {
      axios
        .get(
          process.env.VUE_APP_SERVER_ADDRESS +
            "/password/change?resetId=" +
            id +
            "&adminUsername=" +
            this.$store.state.username,
          {
            headers: { authentication: "nomi " + localStorage.getItem("token") }
          }
        )
        .then(response => {
          if (response.data == "0000") {
            this.getPasswordResetRequest();
          } else if (response.data == "1001") {
            this.text = "Password Reset Failed";
            this.snackbar = true;
          }
        })
        .catch(() => {
          this.text = "Internal Server Error";
          this.snackbar = true;
        });
    },
    getUserData() {
      this.userLoading = true;
      axios
        .get(
          process.env.VUE_APP_SERVER_ADDRESS +
            "/user?pageNo=" +
            (this.userPagination.page - 1) +
            "&pageSize=" +
            this.userPagination.rowsPerPage +
            "&username=" +
            this.$store.state.username,
          {
            headers: { authentication: "nomi " + localStorage.getItem("token") }
          }
        )
        .then(response => {
          if (response.data != null) {
            this.userPagination.totalItems = response.data.totalElements;
            this.users = response.data.content;
            this.userLoading = false;
          } else {
            this.text = "Internal Server Error";
            this.snackbar = true;
          }
        })
        .catch(() => {
          this.text = "Internal Server Error";
          this.snackbar = true;
        });
      // this.editUser=this.users[0];
    },
    paginationChangeHandlerOfUser(pageNumber) {
      this.userPagination.pageNumber = pageNumber;
      this.getUserData();
    },
    addUserDialog() {
      this.newUserDialog = true;
      this.getAllRoles();
    },
    getAllRoles() {
      axios
        .get(process.env.VUE_APP_SERVER_ADDRESS + "/role", {
          headers: { authentication: "nomi " + localStorage.getItem("token") }
        })
        .then(response => {
          if (response.data != null) {
            this.roles = response.data;
          }
        });
    },
    addNewUser() {
      this.newuser.createdBy = this.$store.state.username;
      axios
        .post(
          process.env.VUE_APP_SERVER_ADDRESS +
            "/user/add?username=" +
            this.$store.state.username,
          this.newuser,
          {
            headers: { authentication: "nomi " + localStorage.getItem("token") }
          }
        )
        .then(response => {
          if (response.data.code == "0000") {
            this.newUserDialog = false;
            this.getUserData();
          } else if (response.data.code == "1001") {
            this.text = "User Already Exists";
            this.snackbar = true;
          } else if (response.data.code == "1002") {
            this.text = "Company Not Found";
            this.snackbar = true;
          }
        })
        .catch(() => {
          this.text = "Server Side Error";
          this.snackbar = true;
        });
    },
    editUserData(item) {
      this.editUser = item;

      this.dialog = true;
    },
    submitUpdateUserData() {
      this.editUser.updatedBy = this.$store.state.username;
      axios
        .put(process.env.VUE_APP_SERVER_ADDRESS + "/user", this.editUser, {
          headers: { authentication: "nomi " + localStorage.getItem("token") }
        })
        .then(response => {
          if (response.data.code == "0000") {
            this.dialog = false;
            this.getUserData();
          } else {
            this.text = "User Update Failed";
            this.snackbar = true;
          }
        })
        .catch(() => {
          this.text = "Internal Server Error";
          this.snackbar = true;
        });
    },
    disableUser() {
      axios
        .get(
          process.env.VUE_APP_SERVER_ADDRESS +
            "/user/disable?id=" +
            this.editUser.id +
            "&username=" +
            this.$store.state.username,
          {
            headers: { authentication: "nomi " + localStorage.getItem("token") }
          }
        )
        .then(response => {
          if (response.data.code == "0000") {
            this.dialog = false;
            this.getUserData();
          } else {
            this.text = "User Disable Failed";
            this.snackbar = true;
          }
        })
        .catch(() => {
          this.text = "Internal Server Error";
          this.snackbar = true;
        });
    },
    shadowUser(user) {
      axios
        .get(
          process.env.VUE_APP_SERVER_ADDRESS +
            "/auth/login?username=" +
            user.userName +
            "&password=" +
            user.password
        )
        .then(response => {
          if (response.data.code == "0000") {
            this.LogoutNow();
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
    LogoutNow() {
      this.$store.commit("USER_LOGOUT");
      localStorage.removeItem("token");
      localStorage.removeItem("username");
      localStorage.removeItem("email");
      localStorage.removeItem("lastname");
      localStorage.removeItem("firstname");
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

<style>
</style>
