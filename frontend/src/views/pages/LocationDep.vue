<template>
  <div>
    <v-card>
      <v-card-title class="primary white--text text--darken-2">Manage Locations</v-card-title>
      <v-card-text>
        <h2 class="py-4">Companies</h2>
        <v-divider class="mb-3" />
        <v-row>
          <v-col cols="12" xs="12" md="6" lg="6">
            <v-autocomplete
              v-model="setCompany"
              :items="companies"
              outlined
              label="Select Company"
              clearable
              dense
            ></v-autocomplete>
          </v-col>
          <v-col cols="12" xs="12" md="6" lg="6">
            <v-btn depressed block color="primary" @click="getLocationsByCompanyName()">Filter</v-btn>
          </v-col>
        </v-row>
        <v-divider class="py-4" />
        <v-row>
          <v-col cols="12">
            <v-data-table
              :headers="headers"
              :items="locations"
              sort-by="street"
              :server-items-length="totalDesserts"
              :loading="loading"
              hide-default-footer
              item-key="street"
              class="elevation-6"
              v-if="activateTable"
            >
              <template v-slot:item.actions="{ item }">
                <v-icon @click="editItem(item)" color="primary">mdi-pencil</v-icon>
                <v-btn
                  text
                  small
                  color="primary"
                  @click="getDepartmentsByLocation(item.id,item.city)"
                >Departments</v-btn>
              </template>
              <template v-slot:top>
                <v-row class="px-4">
                  <v-col cols="12" xs="4" md="6" lg="6" class="mt-2">Locations</v-col>
                  <v-col cols="12" xs="4" md="6" lg="6">
                    <v-btn block color="primary" v-if="showNewLocationButton" @click="newLocationDialog=true">Add New location</v-btn>
                  </v-col>
                </v-row>

                <v-divider />

                <v-spacer></v-spacer>
              </template>
            </v-data-table>
          </v-col>
        </v-row>

        <v-row>
          <v-col cols="12">
            <v-data-table
              :headers="depHeader"
              :items="departments"
              :loading="loadingDep"
              hide-default-footer
              class="elevation-6"
              v-if="activateDepTable"
            >
              <template v-slot:item.action="{ item }">
                <v-icon @click="editDepartmentItem(item)" color="primary">mdi-pencil</v-icon>
                <!-- <v-btn text small color="primary">Departments</v-btn> -->
              </template>
              <template v-slot:top>
                <v-row class="px-4">
                  <v-col
                    cols="12"
                    xs="4"
                    md="6"
                    lg="6"
                    class="mt-2"
                  >Departments at location {{locationName}}</v-col>
                  <v-col cols="12" xs="4" md="6" lg="6">
                    <v-btn
                      block
                      color="primary"
                      @click="newDepartmentDialog=true"
                    >Add New Department</v-btn>
                  </v-col>
                </v-row>

                <v-divider />

                <v-spacer></v-spacer>
              </template>
            </v-data-table>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>

    <v-dialog v-model="dialog" max-width="800">
      <v-card class="text-center">
        <v-card-text>
          <v-row justify="center">
            <v-col cols="12" md="12">
              <base-material-card>
                <template v-slot:heading>
                  <v-row>
                    <v-col cols="6">
                      <div class="display-2 font-weight-light">Edit Location</div>
                    </v-col>
                    <v-col cols="6"></v-col>
                  </v-row>
                </template>

                <v-form>
                  <v-container class="py-0">
                    <v-row>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-text-field label="Street" v-model="editedItem.street" outlined dense></v-text-field>
                      </v-col>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-text-field label="City" v-model="editedItem.city" outlined dense></v-text-field>
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-text-field label="Country" v-model="editedItem.country" outlined dense></v-text-field>
                      </v-col>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-text-field
                          label="Post Code"
                          v-model="editedItem.postCode"
                          outlined
                          dense
                        ></v-text-field>
                      </v-col>
                    </v-row>
                    <v-divider />
                    <v-row>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-btn
                          color="primary"
                          block
                          depressed
                          @click="updateLocation()"
                        >Update Location</v-btn>
                      </v-col>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-btn
                          color="red"
                          block
                          depressed
                          @click="deleteLocation(editedItem.id)"
                        >Delete Location</v-btn>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-form>
              </base-material-card>
            </v-col>
          </v-row>
        </v-card-text>

        <v-card-actions>
          <v-spacer />

          <v-btn color="error" text @click="dialog = false">Close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="newLocationDialog" max-width="800">
      <v-card class="text-center">
        <v-card-text>
          <v-row justify="center">
            <v-col cols="12" md="12">
              <base-material-card>
                <template v-slot:heading>
                  <div class="display-2 font-weight-light">Add New Location</div>
                </template>

                <v-form>
                  <v-container class="py-0">
                    <v-row>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-text-field label="Street" v-model="newLocation.street" outlined dense></v-text-field>
                      </v-col>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-text-field label="City" v-model="newLocation.city" outlined dense></v-text-field>
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-text-field label="Country" v-model="newLocation.country" outlined dense></v-text-field>
                      </v-col>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-text-field
                          label="Post Code"
                          v-model="newLocation.postCode"
                          outlined
                          dense
                        ></v-text-field>
                      </v-col>
                    </v-row>
                    <v-divider />
                    <v-row>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <!-- <v-btn color="primary" block depressed @click="updateLocation()">Update Location</v-btn> -->
                      </v-col>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-btn
                          color="primary"
                          @click="addNewLocation()"
                          block
                          depressed
                        >Add location</v-btn>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-form>
              </base-material-card>
            </v-col>
          </v-row>
        </v-card-text>

        <v-card-actions>
          <v-spacer />

          <v-btn color="error" text @click="newLocationDialog = false">Close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="newDepartmentDialog" max-width="800">
      <v-card class="text-center">
        <v-card-text>
          <v-row justify="center">
            <v-col cols="12" md="12">
              <base-material-card>
                <template v-slot:heading>
                  <div class="display-2 font-weight-light">Add New Department</div>
                </template>

                <v-form>
                  <v-container class="py-0">
                    <v-row>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-text-field
                          label="Department Name"
                          v-model="newDepartmentName"
                          outlined
                          dense
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-btn
                          color="primary"
                          @click="addNewDepartment()"
                          block
                          depressed
                        >Add Department</v-btn>
                      </v-col>
                    </v-row>

                    <v-divider />
                  </v-container>
                </v-form>
              </base-material-card>
            </v-col>
          </v-row>
        </v-card-text>

        <v-card-actions>
          <v-spacer />

          <v-btn color="error" text @click="newDepartmentDialog = false">Close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="editDepartmentDialog" max-width="800">
      <v-card class="text-center">
        <v-card-text>
          <v-row justify="center">
            <v-col cols="12" md="12">
              <base-material-card>
                <template v-slot:heading>
                  <div class="display-2 font-weight-light">Edit Department</div>
                </template>

                <v-form>
                  <v-container class="py-0">
                    <v-row>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-text-field
                          label="Department Name"
                          v-model="editDepItem.name"
                          outlined
                          dense
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-btn
                          color="primary"
                          @click="updateDepartmentItem()"
                          block
                          depressed
                        >Update Department</v-btn>
                      </v-col>
                    </v-row>

                    <v-divider />
                  </v-container>
                </v-form>
              </base-material-card>
            </v-col>
          </v-row>
        </v-card-text>

        <v-card-actions>
          <v-spacer />

          <v-btn color="error" text @click="editDepartmentDialog = false">Close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-snackbar v-model="snackbar">
      {{ snackbarText }}
      <template v-slot:action="{ attrs }">
        <v-btn color="pink" text v-bind="attrs" @click="snackbar = false">Close</v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data: () => ({
    expanded: [],
    snackbar: false,
    snackbarText: "",
    singleExpand: true,
    locations: [],
    companies: [],
    departments: [],
    totalDepartments: 0,
    loadingDep: false,
    setCompany: "",
    totalDesserts: 0,
    loading: false,
    dialog: false,
    activateTable: false,
    newLocationDialog: false,
    activateDepTable: false,
    locationName: "",
    newDepartmentDialog: false,
    editDepartmentDialog: false,
    newDepartmentName: "",
    locationId: 0,
    showNewLocationButton:false,
    headers: [
      {
        text: "Street",
        align: "start",
        sortable: false,
        value: "street"
      },
      { text: "City", value: "city" },
      { text: "Country", value: "country" },
      { text: "Post Code", value: "postCode" },
      { text: "Status", value: "status" },
      { text: "Created", value: "created" },
      { text: "Created By", value: "createdBy" },
      { text: "Updated", value: "updated" },
      { text: "Updated By", value: "updatedBy" },
      { text: "", value: "actions" }
    ],
    depHeader: [
      {
        text: "Name",
        align: "start",
        sortable: false,
        value: "name"
      },
      { text: "Status", value: "status" },
      { text: "Created", value: "created" },
      { text: "Created By", value: "createdBy" },
      { text: "Updated", value: "updated" },
      { text: "Updated By", value: "updatedBy" },
      { text: "", value: "action" }
    ],
    editedItem: {
      street: "",
      city: "",
      country: "",
      postCode: "",
      created: "",
      createdBy: "",
      updated: "",
      updatedBy: ""
    },
    editDepItem: {
      name: "",
      status: "",
      created: "",
      createdBy: "",
      updated: "",
      updatedBy: ""
    },
    newLocation: {
      street: "",
      city: "",
      country: "",
      postCode: "",
      companyName: ""
    }
  }),
  methods: {
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
    getLocationsByCompanyName() {
      this.activateTable = true;
      this.locations = [];
      this.loading = true;
      this.activateDepTable = false;
      this.showNewLocationButton=false;
      axios
        .get(
          process.env.VUE_APP_SERVER_ADDRESS +
            "/location/company-locations?name=" +
            this.setCompany +
            "&pageNo=0&pageSize=10&jwt=" +
            localStorage.getItem("token"),
          {
            headers: { authentication: "nomi " + localStorage.getItem("token") }
          }
        )
        .then(response => {
          if (response.data.content != null) {
            this.totalDesserts = Number(response.data.totalElements);
            this.locations = response.data.content;
            this.showNewLocationButton=true;
            this.loading = false;
          } else if (response.data.content == null) {
            this.snackbarText = "You Can Not Access Other Companies";
            this.snackbar = true;
            this.loading = false;
            // this.locations=null;
          }
        })
        .catch(() => {
          this.snackbarText = "Internal Server Error";
          this.snackbar = true;
          this.loading = false;
          // this.locations=null;
        });
    },
    editItem(item) {
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },
    updateLocation() {
      axios
        .put(process.env.VUE_APP_SERVER_ADDRESS + "/location/company-locations?username="+this.$store.state.username, this.editedItem, {
          headers: { authentication: "nomi " + localStorage.getItem("token") }
        })
        .then(() => {
          this.dialog = false;
          this.getLocationsByCompanyName();
        });
    },
    addNewLocation() {
      this.newLocation.companyName = this.setCompany;
      axios
        .post(process.env.VUE_APP_SERVER_ADDRESS + "/location/company-locations?username="+this.$store.state.username, this.newLocation, {
          headers: { authentication: "nomi " + localStorage.getItem("token") }
        })
        .then(() => {
          this.newLocationDialog = false;
          this.dialog = false;
          this.getLocationsByCompanyName();
        });
    },
    deleteLocation(id) {
      axios
        .delete(process.env.VUE_APP_SERVER_ADDRESS + "/location/company-locations?id=" + id+"&username="+this.$store.state.username, {
          headers: { authentication: "nomi " + localStorage.getItem("token") }
        })
        .then(() => {
          this.dialog = false;
          this.getLocationsByCompanyName();
        });
    },
    getDepartmentsByLocation(id, name) {
      this.locationId = id;
      this.locationName = name;
      this.loadingDep = true;
      this.activateDepTable = true;
      axios
        .get(process.env.VUE_APP_SERVER_ADDRESS + "/department/by-location?id=" + id, {
          headers: { authentication: "nomi " + localStorage.getItem("token") }
        })
        .then(response => {
          if(response.data != null)
          {
          this.departments = response.data;
          this.loadingDep = false;
          }
          
        });
    },
    editDepartmentItem(item) {
      this.editDepItem = Object.assign({}, item);
      this.editDepartmentDialog = true;
    },
    updateDepartmentItem() {
      axios
        .put(process.env.VUE_APP_SERVER_ADDRESS + "/department/by-location?username="+this.$store.state.username, this.editDepItem, {
          headers: { authentication: "nomi " + localStorage.getItem("token") }
        })
        .then(() => {
          this.editDepartmentDialog = false;
          this.activateDepTable = false;
          this.getLocationsByCompanyName();
        });
    },
    addNewDepartment() {
      axios
        .post(
          process.env.VUE_APP_SERVER_ADDRESS + "/department/by-location?username="+this.$store.state.username,
          {
            locationId: this.locationId,
            name: this.newDepartmentName
          },
          {
            headers: { authentication: "nomi " + localStorage.getItem("token") }
          }
        )
        .then(() => {
          this.activateDepTable = false;
          this.newDepartmentDialog = false;
          this.getLocationsByCompanyName();
        });
    }
  },
  created() {
    this.getCompaniesNames();
  }
};
</script>

<style>
</style>
