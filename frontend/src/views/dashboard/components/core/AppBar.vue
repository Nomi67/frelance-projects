<template>
  <v-app-bar id="app-bar" absolute app color="transparent" flat height="75">
    <v-btn class="mr-3"  elevation="1" fab small @click="setDrawer(!drawer)">
      <v-icon v-if="value">mdi-view-quilt</v-icon>

      <v-icon v-else>mdi-dots-vertical</v-icon>
    </v-btn>

    <v-toolbar-title class="hidden-sm-and-down font-weight-light" v-text="$route.name" />

    <v-spacer />



    <v-menu bottom left offset-y origin="top right" transition="scale-transition">
      <template v-slot:activator="{ attrs, on }">
        <v-btn color="primary" class="ml-2" min-width="0" text v-bind="attrs" v-on="on">
          <v-badge color="red" overlap bordered>
            <template v-slot:badge>
              <span>5</span>
            </template>

            <v-icon>mdi-bell</v-icon>
          </v-badge>
        </v-btn>
      </template>

      <v-list :tile="false" nav>
        <div>
          <app-bar-item v-for="(n, i) in notifications" :key="`item-${i}`">
            <v-list-item-title v-text="n" />
          </app-bar-item>
        </div>
      </v-list>
    </v-menu>



    <v-menu bottom left offset-y  :close-on-content-click="!closeOnContentClick"
    :close-on-click="closeOnContentClick"
     origin="top right" transition="scale-transition">
      <template v-slot:activator="{ attrs, on }">
        <v-btn  class="ml-2" min-width="0" color="primary " v-bind="attrs" v-on="on">

            {{$store.state.username}}

        </v-btn>
      </template>

      <v-list :tile="false" nav  color="grey lighten-4" width="350">
        <div>
          <v-list-item >
            <v-list-item-title>{{this.$store.state.username}}</v-list-item-title>
            <v-list-item-avatar
            color="grey"

          >
          <v-img
            src="https://demos.creative-tim.com/vuetify-material-dashboard/favicon.ico"
            max-height="50"
          />
          </v-list-item-avatar>
          </v-list-item>
          <v-list-item class="elevation-1">
            <v-list-item-title>
              <v-row>
                <v-col cols="6">First Name</v-col>
                <v-col cols="6">{{$store.state.firstName}}</v-col>
              </v-row>
            </v-list-item-title>
          </v-list-item>
            <v-list-item class="elevation-1">
            <v-list-item-title>
              <v-row>
                <v-col cols="6">Last Name</v-col>
                <v-col cols="6">{{$store.state.lastName}}</v-col>
              </v-row>
            </v-list-item-title>
          </v-list-item>
            <v-list-item class="elevation-1">
            <v-list-item-title>
              <v-row>
                <v-col cols="6">designation</v-col>
                <v-col cols="6">{{$store.state.designation}}</v-col>
              </v-row>
            </v-list-item-title>
          </v-list-item>
          <v-list-item class="elevation-1">
            <v-list-item-title>
              <v-row>
                <v-col cols="6">
                  <v-btn dark color="red"  @click="LogoutNow()">
                    Logout</v-btn>
                </v-col>
                <v-col cols="6"><v-btn color="primary" @click="updateUserCallPopup()">Edit Profile</v-btn></v-col>
              </v-row>
            </v-list-item-title>
          </v-list-item>
        </div>
      </v-list>
    </v-menu>


    <v-dialog
      v-model="dialog"
      max-width="800"
    >
      <v-card class="text-center">

       
          <v-card-title class="primary white--text text--darken-2">
          <v-row>
            <v-col cols="12">
              <h2 justify="center">Edit Profile</h2>
            </v-col>
          </v-row>
          </v-card-title>
          <v-card-text>
            <!-- <v-divider class="my-2"/> -->
          <v-form>
            <v-container class="py-0">
              <v-row>
                <v-col
                  cols="12"
                  md="6"
                >
                  <v-text-field
                    class="purple-input"
                    label="First Name"
                    outlined
                    dense
                    v-model="user.firstName"
                  />
                </v-col>

                <v-col
                  cols="12"
                  md="6"
                >
                  <v-text-field
                    label="Last Name"
                    class="purple-input"
                    outlined
                    dense
                    v-model="user.lastName"
                  />
                </v-col>

                <v-col
                  cols="12"
                  md="6"
                >
                  <v-text-field
                    label="E-mail"
                    class="purple-input"
                    outlined
                    dense
                    v-model="user.email"
                  />
                </v-col>

                <v-col
                  cols="12"
                  md="6"
                >
                  <v-text-field
                    label="Password"
                    class="purple-input"
                    outlined
                    dense
                    v-model="user.password"
                  />
                </v-col>

                <v-col
                  cols="12"
                  class="text-right"
                >
                  <v-btn
                    color="primary"
                    class="mr-0"
                    @click="updateUserData()"
                  >
                    Update Profile
                  </v-btn>
                </v-col>
              </v-row>
            </v-container>
          </v-form>
          </v-card-text>
        <!-- </v-card> -->
      <!-- </v-col>


    </v-row> -->
  <!-- </v-container> -->
  <!-- </v-card-text> -->

        <v-card-actions>
          <v-spacer />

          <v-btn
            color="error"
            text
            @click="dialog = false"
          >
            Close
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-snackbar v-model="snackbar" :timeout="timeout">
          {{ text }}
          <v-btn color="blue" text @click="snackbar = false">Close</v-btn>
        </v-snackbar>
  </v-app-bar>
</template>

<script>
// Components
import { VHover, VListItem } from "vuetify/lib";

// Utilities
import { mapState, mapMutations } from "vuex";

import axios from 'axios';

export default {
  name: "DashboardCoreAppBar",

  components: {
    AppBarItem: {
      render(h) {
        return h(VHover, {
          scopedSlots: {
            default: ({ hover }) => {
              return h(
                VListItem,
                {
                  attrs: this.$attrs,
                  class: {
                    "black--text": !hover,
                    "white--text secondary elevation-12": hover
                  },
                  props: {
                    activeClass: "",
                    dark: hover,
                    link: true,
                    ...this.$attrs
                  }
                },
                this.$slots.default
              );
            }
          }
        });
      },
    }
  },

  props: {
    value: {
      type: Boolean,
      default: false
    }
  },

  data: () => ({
    user:{},
    updateUser:{
       oldUsername:'',
       username:'',
     firstName:'',
    lastName:'',
    designation:'',

    },
    snackbar:false,
    text:'',
    notifications: [
      "Mike John Responded to your email",
      "You have 5 new tasks",
      "You're now friends with Andrew",
      "Another Notification",
      "Another one"
    ],
    closeOnContentClick:true,
    dialog:false,
  }),

  computed: {
    ...mapState(["drawer"])
  },

  methods: {
    ...mapMutations({
      setDrawer: "SET_DRAWER"
    }),
    LogoutNow(){
      this.$store.commit('USER_LOGOUT');
      localStorage.removeItem('token');
      localStorage.removeItem('username');
      localStorage.removeItem('email');
      localStorage.removeItem('lastname');
      localStorage.removeItem('firstname');
      this.$router.push("/login");
    },
    updateUserCallPopup(){
      this.snackbar=false;
        axios.get(process.env.VUE_APP_SERVER_ADDRESS+"/auth/user?name="+this.$store.state.username,
        {
          headers: { authentication: "nomi " + localStorage.getItem("token") }
        }
        ).then(response => {
          if(response.data != null)
          {
            this.user=response.data;
            this.dialog=true;
          }
        }).catch( () => {
          this.text='Edit User Error';
          this.snackbar = true;
        })
    },
    updateUserData(){
      this.snackbar = false;
      console.log(this.user);
      // this.updateUser.oldUsername=this.$store.state.username;
      axios.put(process.env.VUE_APP_SERVER_ADDRESS+"/user",this.user,
      {
          headers: { authentication: "nomi " + localStorage.getItem("token") }
        }
      ).then(response =>{
        if(response.data.code == "0000")
        {
          this.LogoutNow();
        }
        else 
        {
          this.text='User Edit Failed';
          this.snackbar=true;
        }
      }).catch(() => {
        this.text='Server Side Error';
          this.snackbar=true;
      })
    }
  }
};
</script>
