<template>
  <div class="app-container">
    <el-tabs v-model="activeTab" type="border-card">

      <!-- 海康刷脸 Tab -->
      <el-tab-pane label="海康刷脸" name="face">
        <div v-loading="loadingFace">
          
          <el-card class="box-card" shadow="hover">
            <div slot="header" class="clearfix">
              <span><i class="el-icon-sunrise-1"></i> 上班打卡 (08:00前)</span>
            </div>
            <el-table :data="faceData.startWork" size="small" style="width: 100%">
              <el-table-column prop="name" label="姓名" align="center"></el-table-column>
              <el-table-column prop="eventTime" label="签到时间" align="center">
                <template slot-scope="scope">
                  <span>{{ parseTime(scope.row.eventTime) }}</span>
                </template>
              </el-table-column>
            </el-table>
          </el-card>

          <el-card class="box-card" shadow="hover" style="margin-top: 20px;">
            <div slot="header" class="clearfix">
              <span><i class="el-icon-sunset"></i> 下班打卡 (17:00/16:30后)</span>
            </div>
            <el-table :data="faceData.endWork" size="small" style="width: 100%">
              <el-table-column prop="name" label="姓名" align="center"></el-table-column>
              <el-table-column prop="eventTime" label="签到时间" align="center">
                <template slot-scope="scope">
                  <span>{{ parseTime(scope.row.eventTime) }}</span>
                </template>
              </el-table-column>
            </el-table>
          </el-card>

          <el-card class="box-card" shadow="hover" style="margin-top: 20px;">
            <div slot="header" class="clearfix">
              <span><i class="el-icon-time"></i> 全部记录 (最新200条)</span>
            </div>
            <el-table :data="faceData.allLogs" size="small" height="300" style="width: 100%">
              <el-table-column prop="name" label="姓名" align="center"></el-table-column>
              <el-table-column prop="eventTime" label="事件时间" align="center">
                <template slot-scope="scope">
                  <span>{{ parseTime(scope.row.eventTime) }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="createdAt" label="记录时间" align="center">
                <template slot-scope="scope">
                  <span>{{ parseTime(scope.row.createdAt) }}</span>
                </template>
              </el-table-column>
            </el-table>
          </el-card>

        </div>
      </el-tab-pane>

      

    </el-tabs>
  </div>
</template>

<script>
import { getFaceData } from "@/api/kq";

export default {
  name: "KqIndex",
  data() {
    return {
      activeTab: "face",
      // Face Data
      loadingFace: false,
      faceData: {
        startWork: [],
        endWork: [],
        allLogs: []
      },
      
    };
  },
  created() {
    this.getFaceList();
    
  },
  beforeDestroy() {
  },
  methods: {
    // Face
    getFaceList() {
      this.loadingFace = true;
      getFaceData().then(response => {
        this.faceData = response.data;
        this.loadingFace = false;
      });
    },
    
  }
};
</script>

<style scoped>
.box-card {
  margin-bottom: 20px;
}
</style>
