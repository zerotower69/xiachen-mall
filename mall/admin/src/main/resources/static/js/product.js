var main = {
    data() {
        return {
            //屏幕适应配置
            screenHeight: document.documentElement.clientHeight,
            //商品追加,增加的一条数据
            oneData: {
                productName: '',
                productCategory: '',
                productPrice: 0.00
            },
            visible: {
                productAdding: false,
                fullscreen:true,  //全屏显示对话框
                modal:true  //遮屏罩
            },
            adding: false,
            revise: [],
            options: [],
            submitData: [],
            submitButton: false,
            removeData: [],
            //分页的数据
            page: {
                list: [], // 列表
                total: 0, // 总记录数
                current: 1, // 页码
                limit: 8, // 每页记录数
                pages: '0' //总页码数
            }
        }
    },
    filters: {
        priceFilter(value) {
            // 截取当前数据到小数点后三位
            let tempVal = parseFloat(value).toFixed(3)
            let realVal = tempVal.substring(0, tempVal.length - 1)
            return realVal
        }
    },

    methods: {
        //点击编辑
        handleEdit(index, row) {
            this.$set(this.revise, index, false)
        },
        //取消编辑
        handelCancel(index, row) {
            this.$set(this.revise, index, true)
            this.addData.push(row);
            console.log(row)
        },
        //删除
        handleDelete(index, row) {
            this.tableData.splice(index, 1);
            this.removeData.push(row.id);
            this.submitData = true;
            console.log(this.removeData);
        },
        //追加商品
        showAddDialog() {
            const that = this;
            that.visible.productAdding = true;
        },
        //推送到待提交
        addOneProduct() {
            const that = this;
            that.submitData.push(that.addOneProduct);
        },
        closeAddDialog() {
            const that = this;
            that.visible.productAdding = false; //关闭对话框
        },
        submitProducts() {
            const that = this;
            console.log(that.addData.length, that.removeData.length);
            //提交新增数据和修改数据
            if (that.addData.length != 0) {
                axios.post("/product/save/products", this.addData)
                    .then(res => {
                        //console.log(res) 浏览器输出检查
                    })
                    .catch(err => {
                        console.error(err);
                    })
                that.addData = [];
            }
            if (that.removeData.length != 0) {
                axios.post("/product/delete/products", that.removeData)
                    .then(res => {
                        console.log(res)
                    })
                    .catch(err => {
                        console.error(err);
                    })
                that.removeData = [];
            };
            that.submitData = false;
        },
        cancelOperateData() {
            const that = this;
            that.addData = [];
            that.removeData = [];
            that.submitData = false;
        },
        //分页的部分
        handleCurrentChange: function (val) {
            const that = this; //rewriter this pointer
            that.page.current = val;
            that.fetchPageData(that.page.current, that.page.limit);
        },
        fetchPageData: function (current, limit) {
            const that = this;
            axios.get("/product/api/list", {
                    params: {
                        current: current,
                        limit: limit
                    }
                })
                .then(res => {
                    //console.log(res.data); //测试使用
                    if (res.status == 200) {
                        that.revise = [];
                        that.page.list = res.data.data;
                        that.page.total = res.data.total;
                        that.page.pages = res.data.pages;
                        that.page.list.forEach(item => {
                            that.revise.push(true);
                        })
                    } else {
                        that.$notify({
                            tile: '警告',
                            message: '获取分页服务内部错误',
                            type: 'warning'
                        })
                    }
                })
                .catch(err => {
                    that.$notify.error({
                        title: '错误',
                        message: '获取分页数据失败',
                    })
                })
        }
    },
    created() {
        //不知道这样对不对，初始化操作
        const that = this;
        axios.defaults.baseURL = "http://localhost:8005";
        that.fetchPageData(that.page.current, that.page.limit);
        //选择器内容获取
        var count = 1;
        //todo
        axios.get("/category/list/three")
            .then(res => {
                res.data.forEach(element => {
                    var option = {
                        value: '',
                        label: ''
                    };
                    option.value = "选项" + count;
                    option.label = element;
                    that.options.push(option);
                    count += 1;
                })
            })
    },
    mounted() {
        const that = this
        window.onresize = () => {
            return (() => {
                window.screenHeight = document.body.clientHeight
                that.screenHeight = window.screenHeight
            })()
        }
    },
    watch: {
        screenWidth(val) {
            // 为了避免频繁触发resize函数导致页面卡顿，使用定时器
            if (!this.timer) {
                // 一旦监听到的screenWidth值改变，就将其重新赋给data里的screenWidth
                this.screenWidth = val
                this.timer = true
                let that = this
                setTimeout(function () {
                    // 打印screenWidth变化的值
                    console.log(that.screenWidth)
                    that.timer = false
                }, 400)
            }
        },
        addData(val) {
            if (val == '') {
                this.submitData = false;
            } else {
                this.submitData = true;
            }
        }
    },
};
var Cart = Vue.extend(main);
new Cart().$mount('#app');