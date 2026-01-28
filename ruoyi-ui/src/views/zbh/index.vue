<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!-- 杂项检测 -->
      <el-col :span="12" :xs="24">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>杂项检测</span>
          </div>
          <el-table :data="miscData" border style="width: 100%" :show-header="false">
            <el-table-column prop="name" label="名称" width="120" align="center" class-name="table-header-bg"></el-table-column>
            <el-table-column prop="value" label="信息">
              <template slot-scope="scope">
                <span v-html="scope.row.value"></span>
                <el-button v-if="scope.row.hasDetail" type="text" size="small" @click="showDetail(scope.row.detail)">详情</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <!-- 云垒检测 -->
      <el-col :span="12" :xs="24">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>云垒检测时间：{{ yunLeiTime }}</span>
          </div>
          <el-table :data="yunLeiList" border style="width: 100%" height="250">
            <el-table-column prop="id" label="序号" width="50" align="center"></el-table-column>
            <el-table-column prop="hostName" label="服务器名称" align="center"></el-table-column>
            <el-table-column prop="serverIp" label="服务器IP" align="center"></el-table-column>
            <el-table-column prop="serverScore" label="安全得分" align="center"></el-table-column>
            <el-table-column prop="lastUpdateTime" label="最后在线时间" align="center"></el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="24">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>检测时间：{{ stats.checkTime }}。服务端最近软件更新：{{ stats.updateTime }}，计算机：{{ stats.pcCount }}台，软件记录：{{ stats.softwareCount }}条</span>
            <div style="float: right; padding: 3px 0">
               <!-- External Links -->
               <el-link href="360_software_search.jsp" target="_blank" type="primary" style="margin-right: 10px">软件明细</el-link>
               <el-link href="360_hardware_search.jsp" target="_blank" type="primary" style="margin-right: 10px">硬件明细</el-link>
               <el-link href="fwlog.jsp" target="_blank" type="primary" style="margin-right: 10px">流控明细</el-link>
               <el-link href="ce_email_uid.jsp" target="_blank" type="primary" style="margin-right: 10px">集团员工</el-link>
               <el-link href="sis_station.jsp" target="_blank" type="primary" style="margin-right: 10px">SIS站状态</el-link>
               <el-link href="https://10.167.109.139:8888/0" target="_blank" type="primary">UPS状态</el-link>
            </div>
          </div>
          
          <el-table :data="softwareList" border stripe style="width: 100%" v-loading="loading">
            <el-table-column type="index" label="序号" width="50" align="center"></el-table-column>
            <el-table-column prop="ip" label="IP" align="center"></el-table-column>
            <el-table-column prop="softName" label="软件名称" align="center"></el-table-column>
            <el-table-column prop="version" label="软件版本" align="center"></el-table-column>
            <el-table-column prop="installTime" label="安装日期" align="center"></el-table-column>
            <el-table-column prop="corp" label="软件公司" align="center"></el-table-column>
            <el-table-column prop="isUpdate" label="是否有更新" align="center"></el-table-column>
          </el-table>
          
          <pagination
            v-show="total>0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="handlePageChange"
          />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getStatus, getYunLeiList, getSoftwareList } from "@/api/zbh";

export default {
  name: "ZbhDashboard",
  data() {
    return {
      miscData: [],
      yunLeiList: [],
      yunLeiTime: '',
      allSoftwareList: [], // Store all data for client-side pagination
      softwareList: [],
      stats: {
        checkTime: '',
        updateTime: '',
        pcCount: 0,
        softwareCount: 0
      },
      loading: true,
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10
      }
    };
  },
  created() {
    this.fetchStatus();
    this.fetchYunLei();
    this.getList();
  },
  methods: {
    fetchStatus() {
      getStatus().then(response => {
        const data = response.data;
        this.stats.checkTime = data.checkTime;
        this.stats.updateTime = data.updateTime;
        this.stats.pcCount = data.pcCount;
        this.stats.softwareCount = data.softwareCount;
        
        // Prepare Misc Data with formatting
        this.miscData = [
          { name: '大数据指标', value: data.bigDataIndex, hasDetail: data.errorMessages && data.errorMessages.length > 0, detail: data.errorMessages },
          { name: '大网管报警', value: data.networkWarning, hasDetail: false },
          { name: '数据湖状态', value: data.dataLakeDiff > 5 ? `<span style="color: red">${data.dataLakeStatus}</span>` : data.dataLakeStatus, hasDetail: false },
          { name: '采集器离线', value: data.collectorOffline && data.collectorOffline.length > 18 ? `<span style="color: lightcoral">${data.collectorOffline}</span>` : data.collectorOffline, hasDetail: false },
          { name: '错误信息', value: data.errorMessages ? '有错误信息' : '无', hasDetail: !!data.errorMessages, detail: data.errorMessages }
        ];
      });
    },
    fetchYunLei() {
      getYunLeiList().then(response => {
        this.yunLeiList = response.rows;
        if (this.yunLeiList.length > 0) {
           this.yunLeiTime = this.yunLeiList[0].tm;
        }
      });
    },
    getList() {
      this.loading = true;
      getSoftwareList(this.queryParams).then(response => {
        this.allSoftwareList = response.rows;
        this.total = response.total;
        this.handlePageChange();
        this.loading = false;
      });
    },
    handlePageChange() {
      const start = (this.queryParams.pageNum - 1) * this.queryParams.pageSize;
      const end = this.queryParams.pageNum * this.queryParams.pageSize;
      this.softwareList = this.allSoftwareList.slice(start, end);
    },
    showDetail(msg) {
      if (!msg) return;
      this.$alert(msg, '详情', {
        dangerouslyUseHTMLString: true
      });
    }
  }
};
</script>

<style scoped>
.table-header-bg {
  background-color: #d9edf7; /* Bootstrap info color approx */
  font-weight: bold;
}
</style>