<template>
  <div>
       <v-app>
      <v-content>
        <v-row class="mx-2">
          <v-col cols="3" />
          <v-col cols="12" xs="12" sm="12" md="6" lg="6" class="elevation-6 mt-14">
            <v-row>
              <v-col cols="4" />
              <v-col cols="6">
                <h3>Forget Password</h3>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12">
                <v-text-field label="Username" required solo outlined dense v-model="name"></v-text-field>
              </v-col>
            </v-row>
            
            <v-row>
              <v-col cols="12">
                <v-btn block color="primary" dark @click="submitRequest()">Submit Request</v-btn>
              </v-col>
            </v-row>
            <v-divider/>
            <v-row>
              <v-col cols="12">
                <v-btn block color="primary" dark to="/login">Back To Login</v-btn>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
        <v-snackbar v-model="snackbar" :timeout="timeout">
            {{ text }}
          <v-btn color="blue" text @click="snackbar = false">Close</v-btn>
        </v-snackbar>
      </v-content>
    </v-app>
  </div>
</template>

<script>
import axios from "axios";
export default {
    data() {
    return {
        snackbar:false,
      name: "",
      text:'',
    };
  },
  methods: {
      submitRequest(){
          this.snackbar=false;
          axios.post(process.env.VUE_APP_SERVER_ADDRESS+"/password/request?name="+this.name)
            .then(response =>{
                if(response.data.code == "0000")
                {
                    this.text='Soon You Will Receive Email With Your New Password. Thanks';
                    this.snackbar = true;
                }
                else if(response.data.code == "1111")
                {
                    this.text='Request Has Been Submitted Already';
                    this.snackbar = true;
                }
                else if(response.data.code == "1001")
                {
                    this.text='No User Found With Name : '+this.name;
                    this.snackbar = true;
                }
            }).catch( () => {
                this.text='Server Side Error';
                    this.snackbar = true;
            })
          this.snackbar=true;
      }
  }
}
</script>

<style>

</style>