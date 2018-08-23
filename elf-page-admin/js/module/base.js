layui.define(['config'], function (exports) {
    var config = layui.config;

    var base = {
        tableName: 'local',  // 存储表名
        // 获取缓存的token
        getToken: function () {
            var t = layui.data(base.tableName).token;
            if (t) {
                return t;
            }
        },
        // 清除user
        removeToken: function () {
            layui.data(base.tableName, {
                key: 'token',
                remove: true
            });
        },
        // 缓存token
        putToken: function (token) {
            layui.data(base.tableName, {
                key: 'token',
                value: token
            });
        },

        // 当前登录的用户
        getUser: function () {
            var u = layui.data(base.tableName).loginUser;
            if (u) {
                return JSON.parse(u);
            }
        },
        // 缓存user
        putUser: function (user) {
            layui.data(base.tableName, {
                key: 'loginUser',
                value: JSON.stringify(user)
            });
        },
        // 缓存临时数据
        putTempData: function (key, value) {
            if (value) {
                layui.sessionData('tempData', {key: key, value: value});
            } else {
                layui.sessionData('tempData', {key: key, remove: true});
            }
        },
        // 获取缓存临时数据
        getTempData: function (key) {
            return layui.sessionData('tempData')[key];
        },
        // 判断是否有权限
        hasPerm: function (auth) {
            /*
            var user = config.getUser();
            if(user.isSuper==1){
                return true;
            }
            if (user.authorities) {
                for (var i = 0; i < user.authorities.length; i++) {
                    if (auth == user.authorities[i].authority) {
                        return true;
                    }
                }
            }
            return false;
            */
            return true;
        },
        // 封装ajax请求
        req: function (url, data, success, method) {
            var token = base.getToken();
            $.ajax({
                url: config.base_server + url,
                data: JSON.stringify(data),
                type: method,
                contentType : "application/json",
                dataType: 'JSON',
                success: function (data) {
                    success(data);
                },
                error: function (xhr) {
                    console.log(xhr.status + ' - ' + xhr.statusText);
                    if (xhr.status == 401) {
                        base.removeToken();
                        layer.msg('登录过期', {icon: 2}, function () {
                            location.href = '/login.html';
                        });
                    } else {
                        success({code: xhr.status, msg: xhr.statusText});
                    }
                },
                beforeSend: function (xhr) {
                    var token = base.getToken();
                    if (token) {
                        xhr.setRequestHeader('token', token);
                    }
                }
            });
        }

    }

    exports('base', base);
});
