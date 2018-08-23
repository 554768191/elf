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



});
