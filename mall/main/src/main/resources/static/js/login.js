new Vue({
    data() {
        var validateUser = (rule, value, callback) => {
            if (value == '') {
                callback(new Error('用户名不能为空'))
            } else {
                callback()
            }
        }
        return {

            ruleForm: {
                user: '',
                password: ''
            },
            rules: {
                user: [{
                    validator: validateUser,
                    trigger: 'blur'
                }],

            }
        }
    },
    methods: {
        submit(ruleForm) {
            console.log(ruleForm, ruleForm.user, ruleForm.password)
            //this.$refs[ruleForm].validate((valid) => {
            axios.post("http://localhost:9001/user/loginData", {
                    "username": ruleForm.user,
                    "password": ruleForm.password
                })
                .then(res => {
                    //console.log(res.headers.message) //控制台打印请求返回的信息
                    if (res.headers.message == "user not exit") {
                        //console.log("刷新页面")
                        this.$confirm("账号错误,请重新输入账号", "提示", {
                            confirmButtonText: '确定',
                            type: 'warning'
                        })
                    } else if (res.headers.message == "password wrong") {
                        this.$confirm("密码错误,请重新输入密码", "提示", {
                            confirmButtonText: '确定',
                            type: 'warning'
                        })
                    } else if (res.headers.message == "user not active") {
                        this.$confirm("账户未激活,你确定重新向此账户的注册邮箱发送激活邮件吗?", "提示", {
                                confirmButtonText: '确定',
                                cancelButtonText: '取消',
                                type: 'warning'
                            }).then(() => {
                                //发送邮件
                            })
                            .catch(() => {
                                //一些未知错误,大概率是网络问题

                            })
                    } else if (res.headers.message == "ok") {
                        //页面跳转
                        //设置一个弹窗提醒！
                        window.location.replace(document.referrer)
                    }
                })
                .catch(err => {
                    console.log("发生错误")
                    console.log(err);
                })
            //})
        },
    },
}).$mount('#app')