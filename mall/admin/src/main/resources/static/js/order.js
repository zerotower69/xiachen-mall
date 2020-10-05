new Vue({
    el: '#app',
    data() {
        return {
            screenHeight: document.documentElement.clientHeight,
            order_data: ["ceshi1"],
            //json文件接收到的数据
            //分页的数据
            page: {
                list: [], // 列表
                total: 0, // 总记录数
                current: 1, // 页码
                limit: 7, // 每页记录数
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
        //get data
        formatTime(val) {
            //格式化时间
            var time = val;
            var y = val.getY
        },
        // handleSizeChange: function (val) {
        //     const that = this; //rewriter this pointer
        //     that.limit = val;
        //     that.fetchPageData(that.current, that.limit);
        // },
        handleCurrentChange: function (val) {
            const that = this; //rewriter this pointer
            that.page.current = val;
            that.fetchPageData(that.page.current, that.page.limit);
        },
        fetchPageData: function (current, limit) {
            const that = this;
            axios.get("/order/api/list/info", {
                    params: {
                        current: current,
                        limit: limit
                    }
                })
                .then(res => {
                    //console.log(res.data); //测试使用
                    if (res.status == 200) {
                        that.page.list = res.data.data;
                        that.page.total = res.data.total;
                        that.page.pages = res.data.pages;
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
    async created() {
        const that = this;
        //    //加载文件
        //    var json="";
        //    const that=this;
        //    window.onload=function(){
        //        var url="../json/axios.json";
        //        var request=new XMLHttpRequest();
        //        request.open("get",url);
        //        request.send(null);
        //        request.onload=function(){
        //            if(request.status==200){
        //                json=JSON.parse(request.responseText);
        //                console.log(json);
        //                that.link=json;
        //                console.log("link被赋值",that.link);
        //            }
        //        }
        //    }
        //axios 地址定义以及url获取
        axios.defaults.baseURL = "http://localhost:8005";
        //加载订单的信息   全部加载
        // axios.get("/order/list/info")
        //     .then(res => {
        //         if (res.status == 200) {
        //             that.order_data = res.data;
        //         } else {
        //             that.order_data = null;
        //             that.$message({
        //                 message: '订单服务内部服务错误',
        //                 type: 'warning'
        //             });
        //         }
        //     })
        //     .catch(err => {
        //         that.$message({
        //             message: err,
        //             type: 'error'
        //         });
        //         that.order_data = null;
        //     })
        //加载订单信息  分页加载
        that.fetchPageData(that.page.current, that.page.limit);
    },
    mounted() {
        const that = this
        window.onresize = () => {
            return (() => {
                window.screenHeight = document.body.clientHeight
                that.screenHeight = window.screenHeight
            })()
        }
        //console.log("axios定义",axios.defaults.baseURL);
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
    }

})