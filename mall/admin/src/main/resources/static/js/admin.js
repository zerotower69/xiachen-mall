var Main = {
    data() {
        return {
            screenHeight: document.documentElement.clientHeight,
            mainHeight:document.getElementById("main").offsetHeight,
            mainWidth:document.getElementById('main').offsetWidth,
            listData:[
                {
                    name:"测试11"
                },
                {
                    name:"测试22"
                }
            ],
            iframeUrl:"../static/page/product.html"
        }
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
    methods: {
        handleOpen(key,keyPath){
            console.log(key,keyPath);
        },
        menuHandleSelect(url,title){
            const that=this;
            that.iframeUrl=url;
            var oj=document.getElementById('title-center').innerText=title;
            oj.inner
        }
    },
    watch: {
        screenWidth(val){
            // 为了避免频繁触发resize函数导致页面卡顿，使用定时器
            if(!this.timer){
                // 一旦监听到的screenWidth值改变，就将其重新赋给data里的screenWidth
                this.screenWidth = val
                this.timer = true
                let that = this
                setTimeout(function(){
                    // 打印screenWidth变化的值
                    console.log(that.screenWidth)
                    that.timer = false
                },400)
            }
        }
    },
};
var Ctor = Vue.extend(Main)
new Ctor().$mount('#app')