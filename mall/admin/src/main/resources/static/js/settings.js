var main = {
  data() {
    return {
      //屏幕适应配置
      screenHeight: document.documentElement.clientHeight,
      //布尔调控变量
      canShow: true,
      notShow: false,
      //任务分页属性
      taskPage: {
        current: 1,
        limit: 2,
        total: 0,
        pages: 0,
        list: []
      },
      //表格显示控制,没有数据就不显示表格
      controll: true,
      //由于计数器模块没有写，所以先用秒数代替
      seconds: 0,
      //计时器
      timer: null,
      //是否加入新的任务
      addTask: false,
      addOne: {
        title: null,
        content: null,
        info: null,
      }
    }
  }, //data end
  watch: {
    //1.监听分页结果
    taskPage(val) {
      const that = this;
      if (that.taskPage.list == []) {
        that.controll = false;
      } else {
        that.controll = true;
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
          console.log("打印scrern的值", that.screenWidth)
          that.timer = false
        }, 400)
      }
    },
    addOne(val) {
      console.log(val);
    }
  },
  created() {
    const that = this;
    axios.defaults.baseURL = "http://localhost:8005";
    that.fetchPageData(that.taskPage.current, that.taskPage.limit)
    console.log("表格的数据", that.taskPage.list);
  },
  //方法
  methods: {
    changeAddStatus() {
      const that = this;
      that.addTask = !that.addTask;
    },
    handleCurrentChange: function (val) {
      const that = this; //rewriter this pointer
      that.page.current = val;
      that.fetchPageData(that.page.current, that.page.limit);
    },
    fetchPageData(curent, limit) {
      const that = this;
      axios.get("/task/api/list", {
          params: {
            current: curent,
            limit: limit
          }
        })
        .then(res => {
          console.log("返回的数据", res.data)
          if (res.status == 200) {
            that.taskPage.total = res.data.total;
            that.taskPage.pages = res.data.pages;
            that.taskPage.list = res.data.list;
            that.controll = true;
            //初始化计时器
            var count = 0;
            that.taskPage.list.forEach(element => {
              if (!that.taskPage.list[count].timer) {
                that.taskPage.list[count].timer = setInterval(function(){
                  element.useTime+=1;
                }, 1000);
                count+=1;
              }
            });
          } else {
            that.$message({
              message: '分页服务状态错误',
              type: 'warning'
            })
            that.taskPage.list = [];
            that.controll = false;
          }
        })
        .catch(error => {
          that.taskPage.list = [];
          that.controll = false;
          that.$message({
            message: error,
            type: 'error'
          })
        });

    },
    //time operations
    //start to set predict time
    setFinishTime(index) {
      this.taskPage.list[index].status = 1;
    },
    confirmSetTime(index, row) {
      const that = this;
      // console.log("测试打印时间",row.predictTime);
      that.postData("/task/continue/one",row,'启动任务');
    },
    //取消
    cancelSetTime(index, row) {
      this.taskPage.list[index].status = 0;
    },
    //由于经常改变status的值，我们可以设置一个函数专门改变它的值
    setStatus(index, val) {
      const that = this;
      that.taskPage.list[index].status = val;
    },
    //方法封装
    postData(url, post, info) {
      const that = this;
      axios.post(url, post)
        .then(res => {
          if (res.status == 200) {
            that.$message({
              message: info + '成功',
              type: 'success'
            })
            //that.updateTime();//再来更新所有的数据 如何更新时间
            //1.5秒后重新请求数据
            setTimeout(function(){
              that.fetchPageData(that.taskPage.current,that.taskPage.limit);
            },1500)
          }
        })
        .catch(err => {
          that.$message({
            message: info + '失败,错误信息:' + err,
            type: 'error'
          })
        })
    },
    //提交数据
    submitData() {
      const that = this;
      if (that.addOne.title == null || that.addOne.content == null) {
        that.$notify({
          title: '警告',
          message: '任务标题或者内容不得为空',
          type: 'warning'
        })
      } else {
        //其实我的大多数请求要么返回200要么返回500错误
        //var post=JSON.stringify(that.addOne);  //转换为JSON
        that.postData("/task/create/one", that.addOne, '创建')
      }
    },
    updateTime(){
      //更新时间
      const that=this;
      axios.post("/task/update/list",that.taskPage.list)
      .then(res => {
        //console.log(res)
      })
      .catch(err => {
        //console.error(err); 
      })
    }
  }, //methods end
  mounted() {
    const that = this
    window.onresize = () => {
      return (() => {
        window.screenHeight = document.body.clientHeight
        that.screenHeight = window.screenHeight
      })()
    }
  },

}; //main end
var Cart = Vue.extend(main);
new Cart().$mount('#app');