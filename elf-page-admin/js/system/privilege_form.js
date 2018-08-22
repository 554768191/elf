layui.use(['layer', 'base', 'form', 'index'], function () {
    var layer = layui.layer;
    var base = layui.base;
    var form = layui.form;
    var index = layui.index;

    form.render('radio');
    form.render('select');

    var url = 'system/authorities/add';
    // 回显user数据
    var authoritie = base.getTempData('t_authoritie');
    if (authoritie) {
        url = 'system/authorities/update';
        //authoritie.isMenu = authoritie.isMenu.toString();
        form.val('authoritie-form', authoritie);
    }

    // 表单提交事件
    form.on('submit(authoritie-form-submit)', function (data) {
        layer.load(2);
        $.post(url, data.field, function (data) {
            layer.closeAll('loading');
            if (data.code == 200) {
                layer.msg(data.msg, {icon: 1});
                index.finishPopupCenter();
            } else {
                layer.msg(data.msg, {icon: 2});
            }
        });
        return false;
    });
});
