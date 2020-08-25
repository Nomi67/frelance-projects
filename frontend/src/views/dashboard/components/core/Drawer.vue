<template>
  <v-navigation-drawer
    id="core-navigation-drawer"
    v-model="drawer"
    :dark="barColor !== 'rgba(228, 226, 226, 1), rgba(255, 255, 255, 0.7)'"
    :expand-on-hover="expandOnHover"
    color="primary"
    :src="barImage"
    mobile-break-point="960"
    app
    width="260"
    v-bind="$attrs"
  >
    <template v-slot:img="props">
      <v-img
        :gradient="`to bottom, ${barColor}`"
        v-bind="props"
      />
    </template>

    <v-divider class="mb-1" />

    <v-list
      
      nav
    >
      <v-list-item>
        <v-list-item-avatar
          class="align-self-center"
          color="white"
          
        >
          <v-img
            src="https://demos.creative-tim.com/vuetify-material-dashboard/favicon.ico"
            max-height="30"
          />
        </v-list-item-avatar>

        <v-list-item-content>
          <v-list-item-title
            class="display-1"
           
          >Test App</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>

    <v-divider class="mb-2" />

    <v-list
          dense
          nav
          class="py-2"
        >
          <!-- <v-list-item two-line :class="miniVariant && 'px-0'">
            <v-list-item-avatar>
              <img src="https://randomuser.me/api/portraits/men/81.jpg">
            </v-list-item-avatar>

            <v-list-item-content>
              <v-list-item-title>Application</v-list-item-title>
              <v-list-item-subtitle>Subtext</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item> -->


          <v-list-item
            link
            to="location"
            class="py-2"
            v-if="this.$store.state.ACCESS_LOCATION"
          >
            <v-list-item-icon>
              <v-icon>mdi-view-dashboard</v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title>Location/Department</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item
            link
            to="emp-manage"
            class="py-2"
            v-if="this.$store.state.ACCESS_EMPLOYEE"
          >
            <v-list-item-icon>
              <v-icon>mdi-account</v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title>Manage Employee</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item
            link
            to="manage"
            class="py-2"
            v-if="this.$store.state.ACCESS_USER_MANAGE"
          >
            <v-list-item-icon>
              <v-icon>mdi-account</v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title>User Management</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item
            link
            to="role-management"
            class="py-2"
            v-if="this.$store.state.ACCESS_ROLE_MANAGEMENT"
          >
            <v-list-item-icon>
              <v-icon>mdi-account-settings</v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title>Roles Management</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item
            link
            to="role-assigning"
            class="py-2"
            v-if="this.$store.state.ACCESS_PERMISSION_ASSIGNING"
          >
            <v-list-item-icon>
              <v-icon>mdi-chart-bubble</v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title>Permission Assigning</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item
            link
            to="createcompany"
            class="py-2"
            v-if="this.$store.state.ACCESS_CREATE_COMPANY"
          >
            <v-list-item-icon>
              <v-icon>mdi-office-building</v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title>Create Company</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item
            link
            to="user-logs"
            class="py-2"
            v-if="this.$store.state.ACCESS_USER_LOGS"
          >
            <v-list-item-icon>
              <v-icon>mdi-clipboard-outline</v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title>User Logs</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
  </v-navigation-drawer>
</template>

<script>
  // Utilities
  import {
    mapState,
  } from 'vuex'

  export default {
    name: 'DashboardCoreDrawer',

    props: {
      expandOnHover: {
        type: Boolean,
        default: false,
      },
    },

    data: () => ({
      
      
    }),

    computed: {
      ...mapState(['barColor', 'barImage']),
      drawer: {
        get () {
          return this.$store.state.drawer
        },
        set (val) {
          this.$store.commit('SET_DRAWER', val)
        },
      },
      computedUserLinks () {
        return this.userLinks.map(this.mapItem)
      },
      computedAdminLinks () {
        return this.adminLinks.map(this.mapItem)
      },
      profile () {
        return {
          avatar: true,
          // title: this.$t('avatar'),
        }
      },
    },

    methods: {
      mapItem (item) {
        return {
          ...item,
          children: item.children ? item.children.map(this.mapItem) : undefined,
          // title: this.$t(item.title),
        }
      },
    },
  }
</script>

<style lang="sass">
  @import '~vuetify/src/styles/tools/_rtl.sass'

  #core-navigation-drawer
    .v-list-group__header.v-list-item--active:before
      opacity: .24

    .v-list-item
      &__icon--text,
      &__icon:first-child
        justify-content: center
        text-align: center
        width: 20px

        +ltr()
          margin-right: 24px
          margin-left: 12px !important

        +rtl()
          margin-left: 24px
          margin-right: 12px !important

    .v-list--dense
      .v-list-item
        &__icon--text,
        &__icon:first-child
          margin-top: 10px

    .v-list-group--sub-group
      .v-list-item
        +ltr()
          padding-left: 8px

        +rtl()
          padding-right: 8px

      .v-list-group__header
        +ltr()
          padding-right: 0

        +rtl()
          padding-right: 0

        .v-list-item__icon--text
          margin-top: 19px
          order: 0

        .v-list-group__header__prepend-icon
          order: 2

          +ltr()
            margin-right: 8px

          +rtl()
            margin-left: 8px
</style>
