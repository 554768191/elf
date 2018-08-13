layui.config({
    base: 'js/module/'
}).use(['config', 'form'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var config = layui.config;

    $('.form-control').on("focus", function() {
        $(this).parents('.layui-form-item').addClass("input-group-focus");
    }).on("blur", function() {
        $(this).parents(".layui-form-item").removeClass("input-group-focus");
    });

    /*
    if (config.getToken()) {
        location.replace('./');
        return;
    }

    // 表单提交
    form.on('submit(login-submit)', function (obj) {
        var field = obj.field;
        field.grant_type = 'password';
        field.scope = 'select';
        field.client_id = 'client_2';
        field.client_secret = '123456';

        layer.load(2);
        $.ajax({
            url: config.base_server + 'oauth/token',
            data: field,
            type: 'POST',
            dataType: 'JSON',
            success: function (data) {
                console.log(JSON.stringify(data));
                if (data.access_token) {
                    config.putToken(data);
                    layer.msg('登录成功', {icon: 1}, function () {
                        location.replace('./');
                    });
                } else {
                    layer.closeAll('loading');
                    layer.msg('登录失败，请重试', {icon: 5});
                }
            },
            error: function (xhr) {
                layer.closeAll('loading');
                if (xhr.status == 400) {
                    layer.msg('账号或密码错误', {icon: 5});
                } else {
                    layer.msg('登录失败，请按f12查看console错误信息', {icon: 5});
                }
            }
        });
    });
    */
    // 图形验证码
    $('.login-captcha').click(function () {
        this.src = this.src + '?t=' + (new Date).getTime();
    });

});