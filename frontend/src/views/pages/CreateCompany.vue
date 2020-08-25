<template>
  <div class="pa-5">
    <v-card>
      <v-card-title class="primary white--text text--darken-2">Companies</v-card-title>
      <v-card-text>
        <!-- <v-row>
          <v-col cols="12" xs="12" sm="12" md="6" lg="6">
            <v-text-field label="Company Name" v-model="searchCompany.name" outlined dense></v-text-field>
          </v-col>
          <v-col cols="12" xs="12" sm="12" md="6" lg="6">
            <v-text-field label="Location" v-model="searchCompany.location" outlined dense></v-text-field>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="12" xs="12" md="6" lg="6">
            <v-text-field label="Reg No" v-model="searchCompany.regNo" outlined dense></v-text-field>
          </v-col>
          <v-col cols="12" xs="12" md="6" lg="6">
            <v-btn class="right" depressed block color="primary" @click="filterCompany()">Search</v-btn>
          </v-col>
        </v-row>-->
        <v-row>
          <v-col cols="12" xs="12" md="6" lg="6" />
          <v-col cols="12" xs="12" md="6" lg="6">
            <v-text-field label="Search By Name" v-model="companyName" outlined dense></v-text-field>
          </v-col>
        </v-row>
        <!-- <v-divider class="mb-5"></v-divider> -->

        <v-data-table
          :headers="headers"
          :items="companies"
          :loading="loading"
          :pagination.sync="pagination"
          :search="companyName"
          hide-default-footer
          sort-by="calories"
          class="elevation-1"
        >
          <template v-slot:item.status="{ item }">
            <v-icon color="primary" @click="editCompany(item)">mdi-pencil</v-icon>&nbsp;&nbsp;&nbsp;&nbsp;
            <v-btn
              v-if="item.status == 'INACTIVE'"
              @click="changeCompanyStatus(item.id,'ACTIVE')"
              small
              color="green"
            >Enable</v-btn>
            <v-btn
              v-if="item.status =='ACTIVE'"
              @click="changeCompanyStatus(item.id,'INACTIVE')"
              small
              color="red"
            >Disable</v-btn>
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
        <v-divider />
        <v-card-actions>
          <v-row>
            <v-col cols="12" xs="12" sm="12" md="6" lg="6"></v-col>
            <v-col cols="12" xs="12" sm="12" md="6" lg="6">
              <v-btn block depressed color="primary" @click="dialog=true">Add Company</v-btn>
            </v-col>
          </v-row>
        </v-card-actions>
      </v-card-text>
    </v-card>

    <v-dialog v-model="dialog" max-width="800">
      <v-card class="text-center">
        <v-card-title class="primary white--text text--darken-2">Add New Company</v-card-title>
        <v-card-text>
          <v-row justify="center">
            <v-col cols="12" md="12">
              

                <v-form>
                  <v-container class="py-0">
                    <v-row>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-text-field solo label="Company Name" v-model="newCompany.name" outlined dense></v-text-field>
                      </v-col>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-text-field
                          label="TelePhone"
                          v-model="newCompany.telePhone"
                          outlined
                          dense
                          solo
                        ></v-text-field>
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-text-field
                          label="Business Reg No"
                          v-model="newCompany.businessRegNo"
                          outlined
                          dense
                          solo
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-text-field solo label="Address" v-model="newCompany.address" outlined dense></v-text-field>
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col cols="12">
                        <v-textarea
                          label="Description"
                          v-model="newCompany.description"
                          auto-grow
                          outlined
                          rows="1"
                          solo
                          row-height="15"
                        ></v-textarea>
                      </v-col>
                    </v-row>
                    <v-divider />
                    <v-btn color="primary" block depressed @click="createNewCompany()">Create</v-btn>
                  </v-container>
                </v-form>
            </v-col>
          </v-row>
        </v-card-text>

        <v-card-actions>
          <v-spacer />

          <v-btn color="error" text @click="dialog = false">Close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="editDialog" max-width="800">
      <v-card class="text-center">
        <v-card-title class="primary white--text text--darken-2">Edit Company</v-card-title>
        <v-card-text>
          <v-row justify="center">
            <v-col cols="12" md="12">
              <!-- <base-material-card> -->
                <!-- <template v-slot:heading>
                  <div class="display-2 font-weight-light">Edit Company</div>
                </template> -->

                <v-form>
                  <v-container class="py-0">
                    <v-row>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-text-field
                          label="Company Name"
                          v-model="editCompanyObject.name"
                          outlined
                          dense
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-text-field
                          label="TelePhone"
                          v-model="editCompanyObject.telePhone"
                          outlined
                          dense
                        ></v-text-field>
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-text-field
                          label="Business Reg No"
                          v-model="editCompanyObject.businessRegNo"
                          outlined
                          dense
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12" xs="12" sm="12" md="6" lg="6">
                        <v-text-field
                          label="Address"
                          v-model="editCompanyObject.address"
                          outlined
                          dense
                        ></v-text-field>
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col cols="12">
                        <v-textarea
                          label="Description"
                          v-model="editCompanyObject.description"
                          auto-grow
                          outlined
                          rows="1"
                          row-height="15"
                        ></v-textarea>
                      </v-col>
                    </v-row>
                    <v-divider />

                    <v-btn
                      color="primary"
                      block
                      depressed
                      @click="updateCompanyEditedObject()"
                    >Update Company</v-btn>
                  </v-container>
                </v-form>
              <!-- </base-material-card> -->
            </v-col>
          </v-row>
        </v-card-text>

        <v-card-actions>
          <v-spacer />

          <v-btn color="error" text @click="editDialog = false">Close</v-btn>
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
    pagination: { totalItems: null, rowsPerPage: 10, page: 1 },
    dialog: false,
    editDialog: false,
    enable: true,
    loading: false,
    companyName: "",
    text:'',
    snackbar: false,
    companies: [],
    headers: [
      {
        text: "Name",
        align: "start",
        sortable: false,
        value: "name"
      },
      { text: "Phone", value: "telePhone" },
      { text: "Reg No", value: "businessRegNo" },
      { text: "Last Access Date", value: "carbs" },
      { text: "Address", value: "address" },
      { text: "Created", value: "created" },
      { text: "Created By", value: "createdBy" },
      { text: "Updated", value: "updated" },
      { text: "Updated By", value: "updatedBy" },
      { text: "", value: "status" }
    ],
    searchCompany: {
      name: "",
      location: "",
      regNo: ""
    },
    editCompanyObject: {
      name: "",
      telePhone: "",
      businessRegNo: "",
      description: "",
      address: "",
      created: "",
      createdBy: "",
      updated: "",
      updatedBy: "",
      status: ""
    },
    newCompany: {
      name: "",
      telePhone: "",
      businessRegNo: "",
      description: "",
      address: "",
      createdBy: ""
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

  created() {
    this.getAllCompanies();
  },

  methods: {
    paginationChangeHandler(pageNumber) {
      this.pagination.page = pageNumber;
      this.getAllCompanies();
    },
    getAllCompanies() {
      this.loading = true;
      axios
        .get(
          process.env.VUE_APP_SERVER_ADDRESS +
            "/company/all?pageNo=" +
            (this.pagination.page - 1) +
            "&pageSize=" +
            this.pagination.rowsPerPage,
            { headers: { authentication: 'nomi '+localStorage.getItem('token') } }
        )
        .then(response => {
          if (response.data != null) {
            this.companies = response.data.content;
            this.pagination.totalItems = response.data.totalElements;
            this.loading = false;
          }
        });
    },
    filterCompany() {
      this.loading = true;
      axios
        .get(
         process.env.VUE_APP_SERVER_ADDRESS +
            "/company/company-filter?name=" +
            this.searchCompany.name +
            "&regNo=" +
            this.searchCompany.regNo +
            "&location=" +
            this.searchCompany.location,
            { headers: { authentication: 'nomi '+localStorage.getItem('token') } }
        )
        .then(response => {
          if (response.data.code == "0000") {
            this.companies = null;
            this.companies[0] = response.data.data;
            this.loading = false;
          }
        });
    },
    createNewCompany() {
      this.newCompany.createdBy = this.$store.state.username;
      axios
        .post(process.env.VUE_APP_SERVER_ADDRESS + "/company/create", this.newCompany,{ headers: { authentication: 'nomi '+localStorage.getItem('token') } })
        .then(response => {
          if (response.data.code == "0000") {
            this.companies = response.data.data;
            this.itemsLength = this.companies.length;
            this.dialog = false;
          } 
        });
    },
    editCompany(item) {
      this.editCompanyObject = Object.assign({}, item);
      this.editDialog = true;
    },
    updateCompanyEditedObject() {
      axios
        .put(process.env.VUE_APP_SERVER_ADDRESS + "/company/edit?username="+this.$store.state.username, this.editCompanyObject,{ headers: { authentication: 'nomi '+localStorage.getItem('token') } })
        .then(response => {
          if (response.data.code == "0000") {
            this.companies = response.data.data;
            this.editDialog = false;
          } 
        });
    },
    changeCompanyStatus(id, status) {
      this.loading = true;
      axios
        .get(
          process.env.VUE_APP_SERVER_ADDRESS + "/company/change-status?id=" + id + "&status=" + status+"&username="+this.$store.state.username,
          { headers: { authentication: 'nomi '+localStorage.getItem('token') } }
        )
        .then(response => {
          if (response.data.code == "0000") {
            this.companies = response.data.data;
            this.loading = false;
        }
        else
        {
          this.text='Create Company Failed';
          this.snackbar = true;
        }
        });
       
    }
  }
};
</script>
