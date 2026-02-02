<template>
  <div class="app-container">
    <div class="header-actions">
      <el-button
        type="primary"
        plain
        icon="el-icon-plus"
        size="mini"
        @click="handleAdd"
        v-hasPermi="['system:navigation:add']"
        class="manage-btn"
      >管理导航</el-button>
    </div>

    <div class="category-container">
      <div v-for="(group, category) in groupedNavigations" :key="category" class="category-row">
        <div class="category-label">{{ category || '未分类' }}</div>
        <div class="category-items">
          <div v-for="item in group" :key="item.id" class="nav-item-wrapper">
            <a :href="formatUrl(item.url)" target="_blank" class="nav-item">
              <span class="nav-title" :title="item.title">{{ item.title }}</span>
            </a>
            <div class="item-actions" v-if="checkPermi(['system:navigation:edit', 'system:navigation:remove'])">
               <i class="el-icon-edit action-icon" @click.prevent="handleUpdate(item)"></i>
               <i class="el-icon-delete action-icon" @click.prevent="handleDelete(item)"></i>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 添加或修改综合导航对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="title">
          <el-input v-model="form.title" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="链接" prop="url">
          <el-input v-model="form.url" placeholder="请输入链接" />
        </el-form-item>
        <el-form-item label="分类" prop="category">
          <el-input v-model="form.category" placeholder="请输入分类" />
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <el-popover
            placement="bottom-start"
            width="460"
            trigger="click"
            @show="$refs['iconSelect'].reset()"
          >
            <IconSelect ref="iconSelect" @selected="selected" />
            <el-input slot="reference" v-model="form.icon" placeholder="点击选择图标或输入图片URL" readonly>
              <svg-icon
                v-if="form.icon"
                slot="prefix"
                :icon-class="form.icon"
                class="el-input__icon"
                style="height: 32px;width: 16px;"
              />
              <i v-else slot="prefix" class="el-icon-search el-input__icon" />
            </el-input>
          </el-popover>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="form.sort" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">正常</el-radio>
            <el-radio label="1">停用</el-radio>
          </el-radio-group>
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
import { listNavigation, getNavigation, delNavigation, addNavigation, updateNavigation } from "@/api/system/navigation";
import IconSelect from "@/components/IconSelect";
import { checkPermi } from "@/utils/permission"; // 权限校验方法

export default {
  name: "Navigation",
  components: { IconSelect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 导航表格数据
      navigationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        title: null,
        category: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        url: [
          { required: true, message: "链接不能为空", trigger: "blur" }
        ],
      },
    };
  },
  computed: {
    groupedNavigations() {
      const groups = {};
      this.navigationList.forEach(item => {
        if (item.status === '0') { // 仅显示正常状态
           const category = item.category || '默认';
           if (!groups[category]) {
             groups[category] = [];
           }
           groups[category].push(item);
        }
      });
      return groups;
    }
  },
  created() {
    this.getList();
  },
  methods: {
    checkPermi,
    formatUrl(url) {
      if (!url) return '#';
      if (url.startsWith('http://') || url.startsWith('https://')) {
        return url;
      }
      return 'http://' + url;
    },
    /** 查询综合导航列表 */
    getList() {
      this.loading = true;
      listNavigation(this.queryParams).then(response => {
        this.navigationList = response.rows;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        title: null,
        url: null,
        icon: null,
        category: null,
        sort: 0,
        status: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 选择图标
    selected(name) {
      this.form.icon = name;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加综合导航";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getNavigation(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改综合导航";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateNavigation(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addNavigation(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除综合导航编号为"' + ids + '"的数据项？').then(function() {
        return delNavigation(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    isSvg(icon) {
      return icon && !icon.includes('http') && !icon.includes('.');
    }
  }
};
</script>

<style scoped lang="scss">
.app-container {
  padding: 20px;
  background-color: #f0f2f5;
  min-height: calc(100vh - 84px);
}

.header-actions {
  position: absolute;
  top: 20px;
  right: 20px;
  z-index: 100;
}

.category-container {
  background: #fff;
  padding: 20px;
  border-radius: 4px;
  margin-top: 30px; /* 给管理按钮留空间 */
}

.category-row {
  display: flex;
  align-items: flex-start;
  margin-bottom: 15px;
  border-bottom: 1px dashed #eee;
  padding-bottom: 15px;

  &:last-child {
    border-bottom: none;
    margin-bottom: 0;
    padding-bottom: 0;
  }
}

.category-label {
  width: 100px;
  font-weight: bold;
  font-size: 14px;
  color: #333;
  padding-top: 6px;
  flex-shrink: 0;
  text-align: right;
  padding-right: 15px;
  margin-right: 15px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  
  @media (max-width: 768px) {
    display: none;
  }
}

.category-items {
  flex: 1;
  display: flex;
  flex-wrap: wrap;
}

.nav-item-wrapper {
  position: relative;
  margin-right: 8px;
  margin-bottom: 8px;
  
  &:hover .item-actions {
    display: flex;
  }
}

.nav-item {
  display: flex;
  align-items: center;
  justify-content: center;
  text-decoration: none;
  color: #606266;
  font-size: 12px;
  padding: 4px 5px;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  background: #fff;
  transition: all 0.3s;
  width: 70px; /* 4个汉字 + padding/border 约 60-70px */
  box-sizing: border-box;

  &:hover {
    color: #409EFF;
    border-color: #c6e2ff;
    background-color: #ecf5ff;
  }
}

.nav-title {
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  text-align: center;
}

.item-actions {
  position: absolute;
  top: -15px;
  right: -10px;
  background: #fff;
  border: 1px solid #eee;
  border-radius: 10px;
  padding: 2px;
  display: none;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  z-index: 100;
}

.action-icon {
  font-size: 12px;
  color: #909399;
  cursor: pointer;
  padding: 2px;
  
  &:hover {
    color: #409EFF;
  }
  
  &.el-icon-delete:hover {
    color: #F56C6C;
  }
}
</style>
