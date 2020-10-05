var main = ({
    data() {
        var validateUser = (rule, value, callback) => {
            if (value == '') {
                callback(new Error('用户名不得为空'))
            } else if (value.length < 3) {
                callback(new Error('用户名不得小于三位'))
            } else {

                axios.post("http://localhost:9001/member/check", {
                        "username":value
                    })
                    .then(res => {
                        console.log(res)
                        if (res.status == 230) {
                            callback()
                        } else if(res.status==200) {
                            callback(new Error('用户名已存在'))
                        }
                    })
            }
        }
        var isEmail = (rule, value, callback) => {
            if (!value) {
                callback(new Error('邮箱不得为空'));
            } else {
                const reg =
                    /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
                const email = reg.test(value)
                if (!email) {
                    callback(new Error('邮箱格式如:admin@163.com'))
                } else {
                    //邮箱格式校验成功，接下来检查邮箱地址是否可用
                    const url = "http://localhost:9001/mail/check";
                    axios.post(url, {
                            "email": value
                        })
                        .then(res => {
                            console.log(res.status);
                            if (res.status == 200) {
                                this.disabled = false; //邮件校验初步成功才能发送邮件
                                callback()
                            } else {
                                callback(new Error("邮箱地址不存在,请重新填写"))
                            }
                        })
                        .catch(err => {
                            callback(new Error('请求失效'))
                        })
                }
            }
        }
        //密码验证，长度大于六位
        var isPassword = (rule, value, callback) => {
            if (!value) {
                callback(new Error('密码不得为空'))
            } else {
                if (value.length < 6) {
                    callback(new Error('密码长度不得小于六位'))
                } else {
                    callback()
                }
            }
        }
        var checkPassword = (rule, value, callback) => {
            console.log(value)
            if (!value) {
                callback(new Error('不得为空'))
            } else {
                if (value != this.ruleForm.passw) {
                    console.log(value)
                    callback(new Error('两次密码不一致!'))
                } else {
                    callback()
                }
            }
        }
        var isCode = (rule, value, callback) => {
            //console.log(value)
            const number = /^[0-9]*$/;
            if (!value) {
                this.validatorButton = true;
                callback(new Error('验证码不得为空'))
            }
            if (number.test(value) && value.length == 6) {
                this.validatorButton = false;
                callback()
            } else {
                this.validatorButton = true;
                callback(new Error('验证码格式错误'))
            }
        }
        return {

            // 验证码按钮
            sending: true,
            disabled: true,
            second: '',
            timer: null,
            //验证按钮
            validatorButton: true,
            //注册按钮
            register: true,
            //表单验证
            ruleForm: {
                user: '',
                email: '',
                passw: '',
                chepw: '',
                code: ''
            },
            rules: {
                user: [{
                    required: true,
                    validator: validateUser,
                    trigger: 'change'
                }],
                email: [{
                    required: true,
                    validator: isEmail,
                    trigger: 'change'
                }],
                passw: [{
                    required: true,
                    validator: isPassword,
                    trigger: 'change'
                }],
                chepw: [{
                    required: true,
                    validator: checkPassword,
                    trigger: 'change'
                }],
                code: [{
                    validator: isCode,
                    trigger: 'change'
                }]
            }
        }
    },
    methods: {
        submit(formName) {
            console.log("打印提交的用户信息", formName)
            this.$refs[formName].validate((valid) => {
                console.log("valid打印", valid)
                //验证成功后发起请求,把数据传入后端
                if (valid) {
                    const url = "http://localhost:9001/user/add/one";
                    //发送JSON数据
                    axios.post(url, {
                            "username": this.ruleForm.user,
                            "email": this.ruleForm.email,
                            "password": this.ruleForm.passw
                        })
                        .then(res => {
                            // console.log(res); //打印返回的信息
                            if (res.status == 200) {
                                this.$confirm('注册成功,且激活', '提示', {
                                        confirmButtonText: '登录页面',
                                        cancelButtonText: '商城主页'
                                    })
                                    .then(() => {
                                        //返回登录页面
                                        window.location.href =
                                            "http://localhost:9001/page/login"
                                    })
                                    .catch(() => {
                                        //返回主页面
                                        window.location.href = "http://localhost:9001"
                                    })
                            } else {
                                //后端数据库连接失败或者发送激活邮件失败
                                console.log("失败了,打印状态代码",res.status)
                            }
                        })
                        .catch(err => {
                            console.error(err); //打印错误信息
                        })
                }
            })
        },
        getCode() {
            //没发送,不计时
            const TIME_COUNT = 60;
            this.disabled = true; //禁止使用按钮
            this.sending = false; //显示
            if (!this.timer) {
                this.disabled = true;
                this.second = TIME_COUNT;
                this.timer = setInterval(() => {
                    if (this.second > 0 && this.second <= TIME_COUNT) {
                        this.second--; //1-59秒倒计时
                    } else {
                        this.disabled = false; //按钮可用
                        clearInterval(this.timer); //清除计时器
                        this.timer = null;
                        this.sending = true;
                    }
                }, 1000)
            }
            const url = "http://localhost:9001/user/register/code";
            axios.post(url, {
                    "email": this.ruleForm.email
                })
                .then(res => {
                    console.log(res)
                })
                .catch(err => {
                    console.error(err);
                })
        },
        //验证注册码
        validateCode() {
            const url = "http://localhost:9001/user/validate/register/code";
            axios.post(url, {
                    "code": this.ruleForm.code,
                    "email": this.ruleForm.email
                })
                .then(res => {
                    console.log(res)
                    if (res.status == 200) {
                        this.register = false;
                    }
                })
                .catch(err => {
                    console.error(err);
                })
        }
    },
}) 
var Cart = Vue.extend(main)
new Cart().$mount('#app')