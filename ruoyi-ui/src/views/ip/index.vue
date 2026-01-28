<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="IP地址" prop="ip">
            <el-input
              v-model="queryParams.ip"
              placeholder="请输入IP地址"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="备注">
            <el-input
              v-model="noteFilter"
              placeholder="输入备注关键字过滤"
              clearable
              size="small"
              style="width: 240px"
              @input="handleNoteFilter"
            />
          </el-form-item>
          <el-form-item label="MAC" prop="mac">
            <el-input
              v-model="queryParams.mac"
              placeholder="请输入MAC地址"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="管理IP" prop="swip">
            <el-input
              v-model="queryParams.swip"
              placeholder="请输入管理IP"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
            >添加备注</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['monitor:ip:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="ipList" @cell-dblclick="handleDblClick" @sort-change="handleSortChange" :default-sort="{prop: 'ip', order: 'ascending'}" :empty-text="emptyText" border>
          <el-table-column label="IP地址" align="center" prop="ip" sortable="custom" width="130" />
          <el-table-column label="备注" align="center" prop="note" min-width="150" :show-overflow-tooltip="true">
             <template slot-scope="scope">
                <span 
                  v-if="scope.row.note" 
                  @click="handleEdit(scope.row)" 
                  style="cursor: pointer;"
                >{{ scope.row.note }}</span>
                <span 
                  v-else 
                  @click="handleEdit(scope.row)" 
                  style="cursor: pointer; color: #ccc;"
                >无</span>
             </template>
          </el-table-column>
          <el-table-column label="MAC地址" align="center" prop="mac" width="140" />
          <el-table-column label="管理IP" align="center" prop="swip" sortable="custom" width="90">
             <template slot-scope="scope">
               <span>{{ scope.row.swip ? scope.row.swip.split('.').pop() : '' }}</span>
             </template>
          </el-table-column>
          <el-table-column label="记录时间" align="center" prop="sj" width="100" sortable="custom">
            <template slot-scope="scope">
              <div v-if="scope.row.sj">
                  <div>{{ parseTime(scope.row.sj).split(' ')[0] }}</div>
                  <div style="font-size: 12px; color: #888;">{{ parseTime(scope.row.sj).split(' ')[1] }}</div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="VLAN" align="center" prop="vlan" width="80" sortable="custom" />
          <el-table-column label="PORT" align="center" prop="port" sortable="custom" />
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          :page-sizes="[10, 20, 30, 50, 100]"
          @pagination="handlePagination"
        />
      </el-col>
    </el-row>

    <!-- 修改/添加备注对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="IP地址" prop="ip">
          <el-input v-model="form.ip" :disabled="isEdit" placeholder="请输入IP地址" />
        </el-form-item>
        <el-form-item label="备注" prop="note">
          <el-input v-model="form.note" type="textarea" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listIp, updateIp, delIp } from "@/api/ip/ip";

export default {
  name: "Ip",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 所有IP数据（用于客户端分页）
      allIpList: [],
      filteredIpList: [],
      // IP表格数据
      ipList: [],
      noteFilter: "",
      emptyText: "暂无数据",
      currentSort: {
        prop: "ip",
        order: "ascending"
      },
      noteFilterTimer: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否编辑模式
      isEdit: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 100,
        ip: '10.113.',
        mac: undefined,
        swip: undefined,
        showDuplicate: false
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        ip: [
          { required: true, message: "IP地址不能为空", trigger: "blur" }
        ],
        note: [
          { required: true, message: "备注不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询IP列表 */
    getList() {
      this.loading = true;
      listIp(this.queryParams).then(response => {
        if (response.rows && response.rows.length > 0) {
            this.allIpList = response.rows;
            this.applyFilters(true);
        } else {
            this.allIpList = [];
            this.filteredIpList = [];
            this.ipList = [];
            this.total = 0;
            this.emptyText = this.noteFilter && this.noteFilter.trim() ? "无匹配结果" : "暂无数据";
        }
        this.loading = false;
      });
    },
    applyFilters(resetPage) {
        const keyword = this.noteFilter ? this.noteFilter.trim().toLowerCase() : "";
        const keywordCompact = keyword.replace(/\s+/g, "");
        this.filteredIpList = this.allIpList.filter(row => {
            const note = row.note == null ? "" : String(row.note);
            const mac = row.mac == null ? "" : String(row.mac);
            const noteCompact = note.replace(/\s+/g, "");
            const macCompact = mac.replace(/\s+/g, "");
            const hasNote = noteCompact.length > 0;
            const hasMac = macCompact.length > 0;
            if (!hasNote && !hasMac) return false;
            if (keywordCompact) {
                if (!hasNote) return false;
                return noteCompact.toLowerCase().includes(keywordCompact);
            }
            return true;
        });
        this.total = this.filteredIpList.length;
        if (resetPage) {
            this.queryParams.pageNum = 1;
        }
        this.sortFilteredList();
        this.sliceList();
        this.emptyText = keyword && this.filteredIpList.length === 0 ? "无匹配结果" : "暂无数据";
    },
    sortFilteredList() {
        const prop = this.currentSort.prop || "ip";
        const order = this.currentSort.order || "ascending";
        this.filteredIpList.sort((a, b) => {
            let valA = a[prop];
            let valB = b[prop];

            if (valA == null) valA = "";
            if (valB == null) valB = "";

            let result = 0;
            if (prop === "ip" || prop === "swip") {
                const ipAStr = String(valA).trim().replace(/\s+/g, "");
                const ipBStr = String(valB).trim().replace(/\s+/g, "");
                if (ipAStr === "" && ipBStr === "") return 0;
                if (ipAStr === "") return 1;
                if (ipBStr === "") return -1;

                const ipA = ipAStr.split(".").map(Number);
                const ipB = ipBStr.split(".").map(Number);
                for (let i = 0; i < 4; i++) {
                    const numA = ipA[i] || 0;
                    const numB = ipB[i] || 0;
                    if (numA !== numB) {
                        result = numA - numB;
                        break;
                    }
                }
            } else if (prop === "vlan" || prop === "port") {
                const numA = parseFloat(valA);
                const numB = parseFloat(valB);
                if (isNaN(numA) && isNaN(numB)) result = 0;
                else if (isNaN(numA)) result = 1;
                else if (isNaN(numB)) result = -1;
                else result = numA - numB;
            } else {
                if (valA < valB) result = -1;
                else if (valA > valB) result = 1;
                else result = 0;
            }

            return order === "ascending" ? result : -result;
        });
    },
    /** 客户端分页切片 */
    sliceList() {
        const page = this.queryParams.pageNum;
        const limit = this.queryParams.pageSize;
        const start = (page - 1) * limit;
        const end = page * limit;
        this.ipList = this.filteredIpList.slice(start, end);
    },
    /** 分页事件处理 */
    handlePagination() {
      this.sliceList();
    },
    /** 排序事件处理 */
    handleSortChange({ column, prop, order }) {
      if (!order) {
          // 如果取消排序，默认按IP地址升序
          prop = 'ip';
          order = 'ascending';
      }
      this.currentSort = { prop, order };
      this.sortFilteredList();
      this.sliceList();
    },
    handleNoteFilter() {
      if (this.noteFilterTimer) {
        clearTimeout(this.noteFilterTimer);
      }
      this.noteFilterTimer = setTimeout(() => {
        this.applyFilters(true);
      }, 120);
    },
    /** 搜索按钮操作 */
    handleQuery() {
      // 兼容旧系统的输入习惯
      if (this.queryParams.swip && this.queryParams.swip.length > 0 && this.queryParams.swip.length < 3) {
        this.queryParams.swip = "172.1.1." + this.queryParams.swip;
      }
      if (this.queryParams.ip) {
          this.queryParams.ip = this.queryParams.ip.replace(/。/g, ".");
      }
      
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.noteFilter = "";
      this.handleQuery();
    },
    /** 强力刷新 */
    handleForceRefresh() {
        this.dataCache = {};
        this.getList();
    },
    /** 添加按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加备注";
      this.isEdit = false;
    },
    /** 修改按钮操作 */
    handleEdit(row) {
      this.reset();
      this.form = {
        ip: row.ip,
        note: row.note
      };
      this.open = true;
      this.title = "修改备注";
      this.isEdit = true;
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateIp(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.dataCache = {}; // 清除缓存
            this.getList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ip = row.ip;
      this.$modal.confirm('是否确认删除IP为"' + ip + '"的数据项？').then(function() {
        return delIp(row);
      }).then(() => {
        this.dataCache = {}; // 清除缓存
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('module/ip/export', {
        ...this.queryParams
      }, `ip_${new Date().getTime()}.xlsx`)
    },
    cancel() {
        this.open = false;
        this.reset();
    },
    reset() {
        this.form = {
            ip: undefined,
            note: undefined
        };
        this.resetForm("form");
    },
    /** 双击单元格操作 */
    handleDblClick(row, column, cell, event) {
        if (column.property === 'note') {
            this.handleEdit(row);
        } else if (column.property === 'ip') {
            this.handleDelete(row);
        }
    }
  }
};
</script>
