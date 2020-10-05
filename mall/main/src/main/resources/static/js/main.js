//document.write("<script type='text/javascript' src='https://unpkg.com/axios/dist/axios.min.js'></script>")
var productData = [{
    productCode: '12987122',
    productName: '《堂吉诃德》',
    productCategory: '测试',
    desc: '我只是测试',
    productPrice: 66.66,
    productUnit: '本',
    productNum: 10,
    imgAddr: "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3909007992,4223073026&fm=26&gp=0.jpg"
}, ]
var Main = {
    data() {
        return {
            screenHeight: document.documentElement.clientHeight,
            treeData: [],
            tableData: productData,
            defaultProps: { //树形节点辅助参数
                children: 'children',
                label: 'name'
            },
            sendtmp: {},
            global_user: 'default', //全局用户名，默认default
            login: false,
            buyOne: false,
            buyOneData: []
        };
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
        handleOpen(key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
            console.log(key, keyPath);
        },
        handleNodeClick(node, data) {
            const that = this
            //console.log(node, data);
            productName = node.label;
            if (node.catLevel == 3) {
                axios.get("http://localhost:9001/product/listCatName", {
                    params: {
                        catName: node.name
                    }
                }).then(function (res) {
                    //console.log('所有返回的商品', res.data) //控制台调试
                    that.tableData = res.data
                    if (that.tableData.length == 0) {
                        that.$notify({
                            title: '警告',
                            message: '暂无数据',
                            type: 'warning'
                        });
                    }
                })
            } else {
                //console.log("没有访问")
            };
            //console.log("tableData的数据", that.tableData)
        },
        getDataList(data) {
            const that = this
            axios.get("http://localhost:9001/category/listTree")
                .then(function (data) {
                    //console.log("成功了！,获取到了分类信息", data.data) //控制台查阅数据
                    that.data = data.data
                })
                .catch(function (err) {
                    //console.error(err); //控制台测试
                })
        },

        //加入购物车
        addCart(item) {
            //登录检测
            if (this.global_user == "default") {
                //没有登录，提醒登录
                this.$confirm("检测到您未登录，是否登录?", "操作提示", {
                    confirmButtonText: "登录页面",
                    cancelButtonText: "暂不登录",
                    type: "warning"
                }).then(() => {
                    window.location.replace("http://localhost:9001/page/login")
                })
            }
            //已经登录，数据封装,再通过ajax发送到后端
            else {
                const url = "http://localhost:9001/cart/insert/one";
                //console.log("item", item, item.productName)   //打印测试，调试使用
                axios.post(url, {
                        "userName": this.global_user,
                        "productName": item.productName,
                        "productCode": item.productCode,
                        "productCategory": item.productCategory,
                        "eachPrice": item.productPrice,
                        "productNum": 1,
                        "amount": item.productPrice
                    }).then((res) => {
                        //todo code status
                        //console.log("加入购物车成功", res)  //打印测试，调试使用
                        this.$notify({
                            title: '成功',
                            message: '加入购物车成功',
                            type: 'success'
                        });
                    })
                    .catch((error) => {
                        //console.log("加入购物车失败", error)  //打印测试，调试使用
                        this.$notify({
                            title: '警告',
                            message: '加入购物车失败',
                            type: 'warning'
                        });
                    })
            }
        },
        logout() {
            const url = "http://localhost:9001/user/logout";
            axios.post(url)
                .then(res => {
                    // todo code status
                    this.$notify({
                        title: '成功',
                        message: '登出成功,3秒后刷新页面',
                        type: 'success'
                    });
                    setTimeout(this.refresh, 3000);
                })
                .catch(err => {
                    this.$notify.error({
                        title: '警告',
                        message: '登出失败',
                    });
                })
        },
        //直接购买商品
        buyOneProduct(item) {
            const that = this;
            if (that.global_user == "default") {
                that.$message({
                    message: '请先登录',
                    type: 'warning'
                })
            } else {
                that.buyOne = true;
                that.buyOneData = [{
                    "productName": item.productName,
                    "productCategory": item.productCategory,
                    "productCode": item.productCode,
                    "eachPrice": item.productPrice,
                    "productNum": 1,
                    "amount": item.productPrice * 1
                }]
            }
        },
        buyOneConfirm(data) {
            const that = this;
            const url = "http://localhost:9001/order/paying";
            that.buyOne = false;
            data[0].amount = data[0].productNum * data[0].eachPrice;
            data[0].userName = that.global_user;
            console.log("data", data[0])
            axios.post(url, data)
                .then(res => {
                    //console.log(res)
                    if (res.status == 200) {
                        that.$notify({
                            title: '成功',
                            message: '下单成功,邮件已发送',
                            type:'success'
                        });
                    }
                    else{
                        that.$messege({
                            message:'下单失败,内部服务出错,请检查',
                            type:'warning'
                        })
                    }
                })
                .catch(err => {
                    that.$notify.error({
                        title: '错误',
                        message: '访问失败'
                    })
                });
        },
        buyOneCancel() {
            const that = this;
            that.buyOne = false;
        },
        //刷新服务
        refresh() {
            window.location.reload();
        }
    },
    mounted() {
        const that = this;
        window.onresize = () => {
            return (() => {
                window.screenHeight = document.body.clientHeight
                that.screenHeight = window.screenHeight
            })()
        };
    },
    created() {
        console.log(window.location.href)
        var aa = window.location.href.substr(21, 1);
        //console.log(aa)
        const that = this;
        axios.get("http://localhost:9001/category/listTree")
            .then(function (res) {
                //console.log("成功了！,获取到了分类信息", data.data) //控制台查阅数据
                that.treeData = res.data
            })
            .catch(function (err) {
                console.error(err); //控制台测试
            });
        //用户检测
        axios.get("http://localhost:9001/user/get/session")
            .then((res) => {
                // console.log(res.data)
                this.global_user = res.data
            })
            .catch(err => {
                that.$notify({
                    title: '警告',
                    message: '获取会话信息失败',
                    type: 'warning'
                });
            });

        if (that.global_user == "default") {
            this.login = false;
        } else {
            this.login = true;
        }

    },
    watch: {
        global_user(val) {
            if (this.global_user != "default") {
                this.login = true;
            } else {
                this.login = false;
            }
        },
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
        }
    },

};
var Ctor = Vue.extend(Main)
new Ctor().$mount('#app')