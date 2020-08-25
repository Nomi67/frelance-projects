<template>
  <div>
    <v-card>
      <v-card-title class="primary white--text text--darken-2">Manage Emplpyees</v-card-title>
      <v-card-text>
        <v-row>
          <v-col cols="12" xs="12" md="6" lg="6">
            <v-autocomplete
              :items="companies"
              v-model="setCompany"
              outlined
              label="Select Company"
              clearable
              dense
            ></v-autocomplete>
          </v-col>
          <v-col cols="12" xs="12" md="6" lg="6">
            <v-btn depressed block color="primary" @click="getEmployeeByCompany()">Filter</v-btn>
          </v-col>
        </v-row>
        <v-divider class="py-3" />
        <v-data-table
          :headers="headers"
          :items="employee"
          :loading="loading"
          :pagination.sync="pagination"
          hide-default-footer
          item-key="id"
          sort-by="calories"
          class="elevation-1"
          expand-icon
        >
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
                <v-btn v-if="showAddEmp" block color="primary" @click="dialog=true">Add New Employee</v-btn>
              </v-col>
            </v-row>
            <v-divider/>
          </template>
          <template v-slot:item.actions="{ item }">
            <v-btn small text @click="editEmployeeData(item)" color="primary">Manage</v-btn>
          </template>
           <template #item.name="{ item }">{{ item.firstName }} {{ item.lastName }}</template>
          <!-- <template v-slot:no-data>
            <v-btn color="primary" @click="initialize">Reset</v-btn>
          </template>-->
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
        <v-card-text>
          <v-row justify="center">
            <v-col cols="12" md="12">
              <v-tabs color="red" centered background-color="primary">
                <v-tab>
                  <v-icon left>mdi-pencil</v-icon>Edit Employee
                </v-tab>
                <!-- </v-col> -->
                <!-- <v-col cols="6"> -->
                <v-tab justify="right">
                  <v-icon left>mdi-account</v-icon>Add New Employee
                </v-tab>
                <!-- </v-col> -->
                <!-- </v-row> -->
                <!-- <v-tab>
        <v-icon left>mdi-access-point</v-icon>
        Option 3
                </v-tab>-->

                <v-tab-item>
                  <v-card flat>
                    <v-card-text>
                      <v-row>
                        <v-col cols="12" xs="12" md="6" lg="6">
                          <v-text-field
                            label="First Name"
                            v-model="editEmployee.firstName"
                            outlined
                            dense
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" xs="12" md="6" lg="6">
                          <v-text-field
                            label="Last Name"
                            v-model="editEmployee.lastName"
                            outlined
                            dense
                          ></v-text-field>
                        </v-col>
                      </v-row>
                      <v-row>
                        <v-col cols="12" xs="12" md="6" lg="6">
                          <v-text-field label="Reg No" v-model="editEmployee.regNo" outlined dense></v-text-field>
                        </v-col>
                        <v-col cols="12" xs="12" md="6" lg="6">
                          <v-text-field label="Email" v-model="editEmployee.email" outlined dense></v-text-field>
                        </v-col>
                      </v-row>
                      <v-row>
                        <v-col cols="12" xs="12" md="6" lg="6">
                          <v-text-field label="Phone" v-model="editEmployee.phone" outlined dense></v-text-field>
                        </v-col>
                        <v-col cols="12" xs="12" md="6" lg="6">
                          <v-text-field
                            label="Designation"
                            v-model="editEmployee.designation"
                            outlined
                            dense
                          ></v-text-field>
                        </v-col>
                      </v-row>
                      <v-row>
                        <v-col cols="12" xs="12" md="6" lg="6"></v-col>
                        <v-col cols="12" xs="12" md="6" lg="6">
                          <v-btn block color="primary" @click="updateEmployeeData()">Update</v-btn>
                        </v-col>
                      </v-row>
                    </v-card-text>
                  </v-card>
                </v-tab-item>
                <v-tab-item>
                  <v-card flat>
                    <v-card-text>
                      <v-row>
                        <v-col cols="12" xs="12" md="6" lg="6">
                          <v-text-field
                            label="First Name"
                            v-model="newEmployee.firstName"
                            outlined
                            dense
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" xs="12" md="6" lg="6">
                          <v-text-field
                            label="Last Name"
                            v-model="newEmployee.lastName"
                            outlined
                            dense
                          ></v-text-field>
                        </v-col>
                      </v-row>
                      <v-row>
                        <v-col cols="12" xs="12" md="6" lg="6">
                          <v-text-field label="Reg No" v-model="newEmployee.regNo" outlined dense></v-text-field>
                        </v-col>
                        <v-col cols="12" xs="12" md="6" lg="6">
                          <v-text-field label="Email" v-model="newEmployee.email" outlined dense></v-text-field>
                        </v-col>
                      </v-row>
                      <v-row>
                        <v-col cols="12" xs="12" md="6" lg="6">
                          <v-text-field label="Phone" v-model="newEmployee.phone" outlined dense></v-text-field>
                        </v-col>
                        <v-col cols="12" xs="12" md="6" lg="6">
                          <v-text-field
                            label="Designation"
                            v-model="newEmployee.designation"
                            outlined
                            dense
                          ></v-text-field>
                        </v-col>
                      </v-row>
                      <v-row>
                        <v-col cols="12" xs="12" md="6" lg="6"></v-col>
                        <v-col cols="12" xs="12" md="6" lg="6">
                          <v-btn block color="primary" @click="createNewEmployee()">Create Employee</v-btn>
                        </v-col>
                      </v-row>
                    </v-card-text>
                  </v-card>
                </v-tab-item>
              </v-tabs>
            </v-col>
          </v-row>
        </v-card-text>
        <v-divider />
        <v-card-actions>
          <v-spacer />

          <v-btn color="error" text @click="dialog = false">Close</v-btn>
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
    pagination: { totalItems: null, rowsPerPage: 10, page: 1 },
    companies: [],
    snackbar: false,
    snackbarText: "",
    setCompany: "",
    employee: [],
    dialog: false,
    loading: false,

    cmpId: 0,
    showAddEmp: false,
    headers: [
      {
        text: "Name",
        align: "start",
        sortable: false,
        value: "name"
      },
      { text: "Reg No", value: "regNo" },
      { text: "Email", value: "email" },
      { text: "Phone", value: "phone" },
      { text: "Designation", value: "designation" },
      { text: "Created", value: "created" },
      { text: "Created By", value: "createdBy" },
      { text: "Updated", value: "updated" },
      { text: "Updated By", value: "updatedBy" },
      { text: "", value: "actions", sortable: false }
    ],
    editEmployee: {
      firstName: "",
      lastName: "",
      regNo: "",
      email: "",
      phone: "",
      designation: ""
    },
    newEmployee: {
      firstName: "",
      lastName: "",
      regNo: "",
      email: "",
      phone: "",
      designation: "",
      createdBy: "",
      companyName: ""
    }
  }),
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
      this.getEmployeeByCompany();
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
    getEmployeeByCompany() {
      this.loading = true;
      this.showAddEmp = false;
      axios
        .get(
          process.env.VUE_APP_SERVER_ADDRESS +
            "/employee/by-company?company=" +
            this.setCompany +
            "&pageNo=" +
            (this.pagination.page - 1) +
            "&pageSize=" +
            this.pagination.rowsPerPage +
            "&jwt=" +
            localStorage.getItem("token"),
          {
            headers: { authentication: "nomi " + localStorage.getItem("token") }
          }
        )
        .then(response => {
          if (response.data.content != null) {
            this.employee = response.data.content;
            this.pagination.totalItems = response.data.totalElements;
            this.loading = false;
            this.showAddEmp = true;
          } else if (response.data.content == null) {
            this.snackbarText = "You Can Not Access Other Comapnies";
            this.snackbar = true;
            this.loading = false;
          }
        })
        .catch(() => {
          this.snackbarText = "Internal Server Error";
          this.snackbar = true;
          this.loading = false;
        });
    },
    editEmployeeData(item) {
      this.editEmployee = Object.assign({}, item);
      this.dialog = true;
    },
    updateEmployeeData() {
      axios
        .put(process.env.VUE_APP_SERVER_ADDRESS + "/employee/by-company?username="+this.$store.state.username, this.editEmployee, {
          headers: { authentication: "nomi " + localStorage.getItem("token") }
        })
        .then(() => {
          this.dialog = false;
          this.getEmployeeByCompany();
        });
    },
    createNewEmployee() {
      this.newEmployee.companyName = this.setCompany;
      this.newEmployee.createdBy=this.$store.state.username;
      axios
        .post(process.env.VUE_APP_SERVER_ADDRESS + "/employee/by-company?username="+this.$store.state.username, this.newEmployee, {
          headers: { authentication: "nomi " + localStorage.getItem("token") }
        })
        .then(() => {
          this.dialog = false;
          this.getEmployeeByCompany();
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
