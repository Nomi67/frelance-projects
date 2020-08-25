<template>
  <div class="pa-5">
    <v-card class="elevation-5">
      <v-card-title class="primary white--text text--darken-2">Add New Role</v-card-title>
      <v-card-text class="mt-6">
        <v-row>
          <v-col cols="12" xs="12" sm="12" md="6" lg="6">
            <v-text-field v-model="newRoleName" solo label="Role Name" outlined></v-text-field>
          </v-col>
          <v-col cols="12" xs="12" sm="12" md="6" lg="6">
            <!-- <v-autocomplete
            v-model="selectedPermissions"
            :items="permissions"
            outlined
            chips
            small-chips
            label="Permissions"
            multiple
            clearable
            
            ></v-autocomplete>-->
            <v-autocomplete
              v-model="selectedPermissions"
              :items="permissions"
              outlined
              solo
              chips
              small-chips
              multiple
              label="Permissions"
              clearable
            ></v-autocomplete>
            
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="1" xs="1" sm="9" md="9" lg="9" />
          <v-col cols="11" xs="11" sm="3" md="3" lg="3">
            <v-btn @click="addNewRole()" color="primary">Create Role</v-btn>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>

    <v-card  class="mt-4 elevation-5">
      <v-card-title class="primary white--text text--darken-2">Manage Permissions</v-card-title>
      <v-card-text class="pt-5">
        <v-row>
          <v-col cols="12" xs="12" sm="12" md="6" lg="6">
            <v-autocomplete
              v-model="selectedRole"
              :items="roles"
              outlined
              label="Select Role"
              clearable
              solo
              dense
            ></v-autocomplete>
          </v-col>
          <v-col cols="12" xs="12" sm="12" md="6" lg="6">
            <v-btn block class="primary" @click="getPermissionsByRole()">Get Permissions</v-btn>
          </v-col>
        </v-row>
        <v-row >
          <v-col cols="1" />
          <v-col cols="10">
            
            <v-list shaped nav flat class="mt-3 pa-3 elevation-4">
              <v-list-item-group>
                <h2>Assigned Permissions</h2>
                <v-list-item v-for="(item, i) in permsByRole" :key="i">
                  <!-- <v-list-item-icon>
            <v-icon v-text="item.icon"></v-icon>
                  </v-list-item-icon>-->
                  <v-list-item-content>
                    <v-list-item-title v-text="item.name"></v-list-item-title>
                  </v-list-item-content>
                 
                </v-list-item>
              </v-list-item-group>
            </v-list>
          </v-col>
          <v-col cols="1" />
        </v-row>
      </v-card-text>
    </v-card>
    <v-snackbar v-model="snackbar" :timeout="timeout">
          {{ text }}
          <v-btn color="blue" text @click="snackbar = false">Close</v-btn>
        </v-snackbar>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data: () => {
    return {
      selectedPermissions: [],
      permissions: [
       
      ],
      roles:[],
      companies: [],
      text:'',
      snackbar: false,
      selectedRole: "",
      
      newRoleName:'',
      permsByRole:[],
    };
  },
  created() {
    this.getPermissions();
    this.getAllRoles();
  },
  methods: {
    getPermissions() {
      axios
        .get(
          process.env.VUE_APP_SERVER_ADDRESS +
            "/auth/permissions?jwt=" +
            localStorage.getItem("token"),
          {
            headers: { authentication: "nomi " + localStorage.getItem("token") }
          }
        )
        .then(response => {
          if (response.data.code == "0000") {
            for (let i = 0; i < response.data.data.length; i++) {
              let perm={text:response.data.data[i].name};
              this.permissions.push(perm);
            }
          }
        });
    },
    getCompaniesNames() {
      axios
        .get(process.env.VUE_APP_SERVER_ADDRESS + "/company/names", {
          headers: { authentication: "nomi " + localStorage.getItem("token") }
        })
        .then(response => {
          if (response.data != null) {
            this.companies = response.data;
          }
        });
    },
       getAllRoles(){
      axios.get(process.env.VUE_APP_SERVER_ADDRESS+"/role",
       {
            headers: { authentication: "nomi " + localStorage.getItem("token") }
       }
      ).then(response => {
        if(response.data!=null)
        {
          this.roles=response.data;
        }
      })
    },
    getPermissionsByRole(){
      axios.get(process.env.VUE_APP_SERVER_ADDRESS+"/permission/by-role?name="+this.selectedRole,
      {
            headers: { authentication: "nomi " + localStorage.getItem("token") }
       }
      ).then(response => {
        if(response.data.code == "0000")
        {
          this.permsByRole= response.data.data;
        }
        else
        {
          this.text='Something wrong with request';
          this.snackbar = true;
        }
      }).catch( () => {
        this.text='Internal Server Error';
          this.snackbar = true;
      })
    },
    addNewRole(){
      axios.post(process.env.VUE_APP_SERVER_ADDRESS+"/role?name="+this.newRoleName+"&username="+this.$store.state.username,this.selectedPermissions,
      {
            headers: { authentication: "nomi " + localStorage.getItem("token") }
       }
      ).then(response => {
        if(response.data.code == "0000")
        {
          this.roles=response.data.data;
          this.text='Role Added Successfully';
          this.snackbar = true;
        }
        else if(response.data == "1001")
        {
          this.text='Role Already Exists';
          this.snackbar=true;
        }
      }).catch( () => {
        this.text='Internal Server Error';
          this.snackbar=true;
      })
    }
  }
};
</script>

<style>
</style>
