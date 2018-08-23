layui.use(['layer', 'base', 'form', 'index', 'config'], function () {
    var layer = layui.layer;
    var base = layui.base;
    var form = layui.form;
    var index = layui.index;
    var config = layui.config;

    //form.render('radio');
    form.render('select');

    var method = 'POST';
    var url = 'privilege';
    // 回显user数据
    var privilege = base.getTempData('t_privilege');
    if (privilege) {
        method = 'PUT';
        url = url + '/' + privilege.id;
        //authoritie.isMenu = authoritie.isMenu.toString();
        form.val('privilege-form', privilege);
    }

    // 表单提交事件
    form.on('submit(privilege-form-submit)', function (data) {
        layer.load(2);
        // base.req(config.base_server + 'privilege', data.field, callback(), 'POST');
        // var token = base.getToken();
        // console.log(data);
        $.ajax({
            url: config.base_server + url,
            data: JSON.stringify(data.field),
            type: method,
            contentType : "application/json",
            dataType: 'JSON',
            success: function (data) {
                console.log(JSON.stringify(data));
                if (data.code==0) {
                    //base.putToken(data.data.token);
                    layer.closeAll('loading');
                    layer.msg('成功', {icon: 1}, function () {
                        index.finishPopupCenter();
                    });
                } else {
                    layer.closeAll('loading');
                    layer.msg(data.msg, {icon: 5});
                }
            },
            error: function () {
                layer.msg('失败，请按f12查看console错误信息', {icon: 5});
            },
            beforeSend: function (xhr) {
                var token = base.getToken();
                if (token) {
                    xhr.setRequestHeader('token', token);
                }
            }
        });

        return false;
    });

});
