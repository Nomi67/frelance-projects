<template>
  <v-app>
    <dashboard-core-app-bar />

    <dashboard-core-drawer />

    <!-- <dashboard-core-view /> -->
    <v-content>
    <router-view class="mx-5"></router-view>

    <!-- <dashboard-core-footer /> -->
  </v-content>

    <dashboard-core-settings />
  </v-app>
</template>

<script>
  export default {
    name: 'DashboardIndex',

    components: {
      DashboardCoreAppBar: () => import('./components/core/AppBar'),
      DashboardCoreDrawer: () => import('./components/core/Drawer'),
      DashboardCoreSettings: () => import('./components/core/Settings'),
      // DashboardCoreView: () => import('./components/core/View'),

    },

    data: () => ({
      expandOnHover: false,
    }),
  }
</script>
