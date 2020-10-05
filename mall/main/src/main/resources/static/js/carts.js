var main = ({
    data() {
        return {
            screenHeight: document.documentElement.clientHeight,
            tableData: [],
            multipleSelection: [],
            allAmount: 0.00,
            disableButton: true,
            global_name: "default",
            login: false,
            dialogTableVisible: false,
        }
    },
    filters: {
        rounding(value) {
            return value.toFixed(2)
        }
    },
    methods: {
        handleSelectionChange(val) {
            this.multipleSelection = val;
            //console.log("数据打印222", $(this.multipleSelection))
            this.allAmount = 0
            if (this.multipleSelection.length == 0) {
                this.allAmount = 0
                this.disableButton = true
            } else {
                this.multipleSelection.forEach(item => {
                    this.allAmount += item.productNum * item.eachPrice
                    this.disableButton = false
                })
            }
        },
        handleDelete(index, row) {
            //console.log(index, row)
            document.getElementById('pay-button').disabled = true
            var Data = {
                "userName": this.global_name,
                "productCode": this.tableData[index].productCode
            }
            const url = "http://localhost:9001/cart/delete/one";
            axios.post(url, this.tableData[index])
                .then(res => {
                    //console.log(res)  //打印数据，测试时使用
                    this.$notify({
                        title: '成功',
                        message: '删除数据成功',
                        type: 'success',
                        duration:1500
                    })
                })
                .catch(err => {
                    this.$notify.error({
                        title: '失败',
                        message: '删除数据失败',
                        duration: 1500
                    });
                })
            this.tableData.splice(index, 1)
            this.allAmount = 0
            if (this.multipleSelection.length == 0) {
                this.allAmount = 0
                this.disableButton = true
            } else {
                this.multipleSelection.forEach(item => {
                    this.allAmount += item.productNum * item.eachPrice
                    this.disableButton = false
                })
            }
        },
        changeNum(index) {
            // 计算总金额
            this.allAmount = 0
            if (this.multipleSelection.length == 0) {
                this.allAmount = 0
            }
            this.multipleSelection.forEach(item => {
                this.allAmount += item.productNum * item.eachPrice
            })
            //更新数据库
            console.log("打印传递的参数", this.tableData[index].productNum);
            const url = "http://localhost:9001/cart/update/number";
            axios.post(url, this.tableData[index]).then((res) => {
                //todo
                console.log(res)
            })
        },
        paying() {  //结算按钮,通过改变参数弹出弹窗
            //结算购物车
            const that = this;
            //弹窗确认
            that.dialogTableVisible = true;
        },
        payConfirm() {
            const that = this;
            const url = "http://localhost:9001/order/paying";
            that.dialogTableVisible = false;
            axios.post(url, that.multipleSelection)
                .then(res => {
                    //console.log(res)
                    if (res.status == 200) {
                        that.$notify({
                            title: '成功',
                            message: '下单成功,3秒后刷新',
                            type: 'success'
                        });
                        setTimeout(that.refresh,3000)
                    } else {
                        that.$notify.error({
                            title: '错误',
                            message: '下单失败'
                        });
                    }
                })
                .catch(err => {
                    that.$notify.error({
                        title: '错误',
                        message: '访问失败'
                    })
                });
            //window.location.reload();  //想弹窗结束之后刷新页面

        },
        payCancel() {
            const that = this;
            that.dialogTableVisible = false;
        },
        logout() {
            const that=this;
            const url = "http://localhost:9001/user/logout";
            axios.get(url)
                .then(res => {
                    if (res.status == 200) {
                        that.$notify({
                            title: '成功',
                            message: '退出当前账户',
                            type: 'success',
                            duration: 2000
                        })
                       setTimeout(that.refresh,3000);//3s later refresh
                    }
                    else{
                        that.$Notice.warning({
                            content: '网络或者服务状态异常',
                            duration: 2
                        })
                    }
                })
                .catch(err => {
                    //todo
                    // that.$notify.error({
                    //     message:'请求失败',
                    //     duration:2000
                    // })
                })
        },
        refresh(){
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
        const that = this;
        //console.log("url打印", window.location.href)
        axios.post("http://localhost:9001/user/get/session")
            .then((res) => {
                //console.log("打印Session返回的数据", res.data)
                that.global_name = res.data
                //console.log("打印加载后的当前用户名", this.global_name)
                if (that.global_name == "default") {
                    that.login = false;
                } else {
                    that.login = true;
                }
                //console.log("打印即将请求的用户名", this.global_name)
                axios.get("http://localhost:9001/cart/list/user/carts", { //发送请求
                    params: {
                        username: that.global_name
                    }
                }).then((res) => {
                    //todo
                    that.tableData = res.data;  //购物车返回的数据
                }).catch(e => {
                    this.$notify.error({
                        title: '错误',
                        message: '购物车列出功能失败,请检查网络连接或者订单服务状态'
                    })
                })
            })
            .catch(err => {
                this.$notify.error({
                    title: '错误',
                    message: '用户检查功能失败,请检查网络连接或者用户服务状态'
                })
            })
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
        }
    },
    updated() {},
    activated() {
        this.loadData()
    },
});
var Cart = Vue.extend(main)
new Cart().$mount('#app')