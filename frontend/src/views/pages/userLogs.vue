<template>
  <div>
    <v-card>
      <v-card-title class="primary white--text text--darken-2">
          <v-col cols="12" xs="12" sm="12" md="6" lg="6">User Logs</v-col>
          <v-col cols="12" xs="12" sm="12" md="6" lg="6">
              <v-btn color="green" dark @click="print()" block>Print Logs</v-btn>
          </v-col>
      </v-card-title>
      <v-card-text>
        <v-data-table
          :headers="headers"
          :items="logs"
          :pagination.sync="pagination"
          hide-default-footer
          class="elevation-1"
          id="table"
          :loading="loading"
        ></v-data-table>
        <div class="text-center pt-2">
          <v-pagination
            @input="paginationChangeHandler"
            :value="pagination.page"
            :length="pages"
            :total-visible="7"
            
          ></v-pagination>
        </div>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import jsPDF from "jspdf";
import "jspdf-autotable";
import axios from 'axios';
export default {
  data() {
    return {
      pagination: { totalItems: null, rowsPerPage: 10, page: 1 },
      loading:false,
      headers: [
        {
          text: "User Name",
          align: "start",
          sortable: false,
          value: "username"
        },
        { text: "Company", value: "company" },
        { text: "User Action", value: "action" },
        { text: "Log Date", value: "logDate" }
      ],
      logs: []
    };
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
    created() {
        this.getUserLogs();
    },
  methods: {
      paginationChangeHandler(pageNumber) {
      this.pagination.page = pageNumber;
      this.getUserLogs();
    },
    print() {
      const doc = new jsPDF();
      let tableBody=[];
      // let tableData=[];
      // console.log(this.logs.length)
      for(let log of this.logs)
      {
        // tableData[0]=log.username;
        // tableData[1]=log.company;
        // tableData[2]=log.action;
        // tableData[3]=log.logDate;
        tableBody.push([log.username,log.company,log.action,log.logDate]);
        console.log(tableBody);
        // console.log(tableData);
        // console.log(tableBody);
      }
      // console.log(tableBody);
      doc.autoTable({
        head: [["User Name", "Company", "Action","Log Date"]],
        
        body: tableBody
      });
       

      doc.save("user-logs.pdf");
    },
    getUserLogs() {
        this.loading = true;
        axios.get(process.env.VUE_APP_SERVER_ADDRESS+"/logs?pageNo=" +
            (this.pagination.page - 1) +
            "&pageSize=" +
            this.pagination.rowsPerPage
            +"&username="+this.$store.state.username,
            { headers: { authentication: 'nomi '+localStorage.getItem('token') } }
            ).then(response =>{
                if(response.data !=null)
                {

                    this.pagination.totalItems = response.data.totalElements;
                    this.logs= response.data.content;
                    this.loading=false;
                }
            })
    }
  }
};
</script>

<style>
</style>