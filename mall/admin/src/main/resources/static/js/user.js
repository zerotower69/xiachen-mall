var main={
    data(){
        return{
            //适应屏幕尺寸
            screenHeight: document.documentElement.clientHeight,
            userData:[],
            input_disabled:true,
        }
    },
    created(){
        const that=this;
        //请求用户数据
        const userUrl="http://localhost:8005/member/list";
        axios.post(userUrl)
        .then(res => {
            console.log(res)
            that.userData=res.data;
        })
        .catch(err => {
            console.error(err); 
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
    },
    methods: {
        reverseActiveStatus(index,row){
            row.active+=1;
            row.active%=2;
        }
    },
}

var Cart=Vue.extend(main);
new Cart().$mount('#app');