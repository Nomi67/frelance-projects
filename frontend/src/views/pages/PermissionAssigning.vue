<template>
  <div class="pa-5">
    <v-card>
      <v-card-title class="primary white--text text--darken-2">System Users</v-card-title>
      <v-card-text>
        <v-row>
          <v-col cols="12" xs="12" sm="12" md="6" lg="6">
            <v-text-field label="Username" v-model="username" solo outlined dense></v-text-field>
          </v-col>
          <v-col cols="12" xs="12" sm="12" md="6" lg="6">
            <v-text-field label="Company Name" v-model="companyName" solo outlined dense></v-text-field>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="12" xs="12" md="6" lg="6">
            <!-- <v-text-field label="" solo outlined dense></v-text-field> -->
          </v-col>
          <v-col cols="12" xs="12" md="6" lg="6">
            <v-btn class="right" @click="searchCompany()" block color="primary">Search</v-btn>
          </v-col>
        </v-row>
        <v-divider class="mb-5"></v-divider>
        <v-data-table
          :headers="headers"
          :items="users"
          :loading="loading"
          sort-by="calories"
          class="elevation-1"
          expand-icon
          hide-default-footer
        >
          <template v-slot:item.actions="{ item }">
            <v-btn small @click="managerUser(item)" solo color="primary">Manage Perms</v-btn>
          </template>
          <!-- <template v-slot:no-data>
            <v-btn color="primary" @click="initialize">Reset</v-btn>
          </template>-->
          <template #item.name="{ item }">{{ item.firstName }} {{ item.lastName }}</template>
            <template v-slot:top>
            <v-row class="mx-1">
              <v-col cols="12" xs="12" md="6" lg="6">
                <v-text-field
                  :value="pagination.rowsPerPage"
                  label="Items per page"
                  type="number"
                  min="-1"
                  max="15"
                  @input="pagination.rowsPerPage = parseInt($event, 10)"
                  outlined
                  
                  dense
                ></v-text-field>
              </v-col>
              <v-col cols="12" xs="12" md="6" lg="6">
                <!-- <v-btn v-if="showAddEmp" block color="primary" @click="dialog=true">Add New Employee</v-btn> -->
              </v-col>
            </v-row>
            <v-divider/>
          </template>
        </v-data-table>
        <div class="text-xs-center pt-2 md4 lg4">
          <v-pagination
            @input="paginationChangeHandler"
            :value="pagination.page"
            :length="pages"
            :total-visible="7"
            
          ></v-pagination>
        </div>
      </v-card-text>
    </v-card>

    <v-dialog v-model="dialog" max-width="800">
      <v-card class="text-center">
        <v-card-title justify="center" class="primary white--text text--darken-2">
          <v-row>
            <v-col cols="12" xs="12" sm="12" md="6" lg="6">Manage User</v-col>
            <v-col cols="12" xs="12" sm="12" md="6" lg="6">
              <v-btn block color="error" @click="deleteUser()">Delete User</v-btn>
            </v-col>
          </v-row>
        </v-card-title>
        <v-card-text>
          <v-row justify="center">
            <v-col cols="12" md="12">
              <!-- <base-material-card> -->
              <!-- <template v-slot:heading>
                  <div class="display-2 font-weight-light">Manage Permissions</div>
              </template>-->

              <v-form>
                <v-container class="py-0">
                  <v-row class="elevation-1">
                    <v-col cols="6">User Role</v-col>
                    <v-col cols="6"><h3> {{roleName}} </h3></v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                      <v-autocomplete
                        :items="roles"
                        v-model="getRole"
                        outlined
                        label="Select Role"
                        clearable
                        solo
                        dense
                      ></v-autocomplete>
                    </v-col>
                    <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                      <v-btn block color="primary" @click="changeUserRole()">Change User Role</v-btn>
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
    pagination: { totalItems: null, rowsPerPage: 5, page: 1 },
    dialog: false,
    snackbar:false,
    loading: false,
    getRole:'', 
    roleName:'',
    username:'',
    companyName: "",
    text:'',
    roles:[],
    editUser: {},
    users: [],
    headers: [
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
    items: [
      { text: "Real-Time", icon: "mdi-clock" },
      { text: "Audience", icon: "mdi-account" },
      { text: "Conversions", icon: "mdi-flag" }
    ]
  }),

  created() {
    this.getUserData();
  },
  computed: {
    pages() {
      if (
        this.pagination.rowsPerPage == null ||
        this.pagination.totalItems == null
      ) {
        return 0;
      }

      return Math.ceil(
        this.pagination.totalItems / this.pagination.rowsPerPage
      );
    }
  },
  methods: {
    paginationChangeHandler(pageNumber) {
      this.pagination.page = pageNumber;
      this.getUserData();
    },

    getUserData() {
      this.loading = true;
      axios
        .get(
          process.env.VUE_APP_SERVER_ADDRESS +
            "/user?pageNo=" +
            (this.pagination.page - 1) +
            "&pageSize=" +
            this.pagination.rowsPerPage
            +"&username="+this.$store.state.username,
          {
            headers: { authentication: "nomi " + localStorage.getItem("token") }
          }
        )
        .then(response => {
          if (response.data != null) {
            this.pagination.totalItems = response.data.totalElements;
            this.users = response.data.content;
            this.loading = false;
          }
          else
            {
              this.text='Internal Server Error';
              this.snackbar = true;
            }
        }).catch( () => {
           this.text='Internal Server Error';
            this.snackbar = true;
        });
        // this.editUser=this.users[0];
    },
    managerUser(item) {
      this.editUser = item;
      this.roleName=this.editUser.role.name;
      this.dialog = true;
      this.getAllRoles();
    },
    getAllRoles(){
      axios.get(process.env.VUE_APP_SERVER_ADDRESS+"/role",
       {
            headers: { authentication: "nomi " + localStorage.getItem("token") }
       }
      ).then(response => {
        if(response.data!=null)
        {
          // for(let i=0;i<response.data.length;i++)
          // {
          //   this.roles[i]=response.data[i].name;
          // }
          this.roles=response.data;
        }
      })
    },
    changeUserRole(){
      axios.put(process.env.VUE_APP_SERVER_ADDRESS+"/user/change-role?id="+this.editUser.id+"&role="+this.getRole+"&updatedBy="+this.$store.state.username,null,
      {
            headers: { authentication: "nomi " + localStorage.getItem("token") }
       }
      ).then(response =>{
        if(response.data.code == "0000")
        {
          this.getUserData();
          this.dialog=false;
        }
        else
        {
          this.text='Change User Role Failed';
          this.snackbar=true;
        }
      }).catch( () => {
        this.text='Server side error';
          this.snackbar=true;
      })
    },
    deleteUser(){
      axios.delete(process.env.VUE_APP_SERVER_ADDRESS+"/user?id="+this.editUser.id+"&username="+this.$store.state.username,
      {
            headers: { authentication: "nomi " + localStorage.getItem("token") }
       }
      ).then(response => {
        if(response.data.code == "0000")
        {
          this.dialog = false;
          this.getUserData();
        }
        else
        { 
          this.text='User Delete Failed';
          this.snackbar = true;
        }
      }).catch( () => {
        this.text='Internal Server Error';
          this.snackbar = true;
      })
    },
    searchCompany(){
      this.loading = true;
      axios.get(process.env.VUE_APP_SERVER_ADDRESS+"/user/filter?username="+this.username
      +"&cName="+this.companyName+"&pageNo=0&pageSize="+this.pagination.rowsPerPage,

      {
            headers: { authentication: "nomi " + localStorage.getItem("token") }
       }
      ).then(response => {
        if (response.data != null) {
            this.pagination.totalItems = response.data.totalElements;
            this.users = response.data.content;
            this.loading = false;
          }
          else
            {
              this.text='Internal Server Error';
              this.snackbar = true;
            }
        }).catch( () => {
           this.text='Internal Server Error';
            this.snackbar = true;
        });
    }
  }
};
</script>

<style>
</style>
