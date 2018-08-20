layui.use(['form', 'table', 'util', 'config'], function () {
    var form = layui.form;
    var table = layui.table;
    var config = layui.config;
    var layer = layui.layer;
    var util = layui.util;
    // var index = layui.index;

    // 渲染表格
    table.render({
        elem: '#auth-table',
        url: config.base_server + 'privilege',
        where: {
            token: config.getToken()
        },
        page: false,
        cols: [[
            {type: 'numbers'},
            {field: 'authorityName', sort: true, title: '权限'},
            {field: 'authority', sort: true, title: '权限标识'},
            {
                field: 'createTime', sort: true, templet: function (d) {
                    return util.toDateString(d.createTime);
                }, title: '创建时间'
            },
            {field: 'checked', sort: true, templet: '#auth-state', title: '授权'}
        ]]
    });

    // 角色切换事件
    form.on('select(auth-slt-role)', function (data) {
        table.reload('auth-table', {where: {roleId: data.value}});
    });

    // 同步按钮点击事件
    $('#auth-btn-sync').click(function () {
        layer.confirm('确定进行同步？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            layer.load(2);
            $.get(config.base_server + 'v2/api-docs', function (data) {
                config.req('authorities/sync', {
                    json: JSON.stringify(data)
                }, function (data) {
                    layer.closeAll('loading');
                    if (200 == data.code) {
                        layer.msg(data.msg, {icon: 1});
                        table.reload('auth-table');
                    } else {
                        layer.msg(data.msg, {icon: 2});
                    }
                }, 'POST');
            });
        });
    });

    // 获取角色
    //admin.showLoading('.toolbar');
    config.req('role', {}, function (data) {
        //admin.removeLoading('.toolbar');
        if (0 == data.code) {
            $('#auth-slt-role').vm({roles: data.data});
            form.render('select');
        } else {
            layer.msg('获取角色失败', {icon: 2});
        }
    }, 'GET');

    // 监听状态开关操作
    form.on('switch(auth-state)', function (obj) {
        var roleId = $('#auth-slt-role').val();
        if (!roleId) {
            layer.msg('请选择角色再进行授权操作', {icon: 2});
            $(obj.elem).prop('checked', !obj.elem.checked);
            form.render('checkbox');
            return;
        }
        layer.load(2);
        config.req('authorities/role', {
            roleId: roleId,
            authId: obj.value
        }, function (data) {
            layer.closeAll('loading');
            if (data.code == 200) {
                layer.msg(data.msg, {icon: 1});
            } else {
                layer.msg(data.msg, {icon: 2});
                $(obj.elem).prop('checked', !obj.elem.checked);
                form.render('checkbox');
            }
        }, obj.elem.checked ? 'POST' : 'DELETE');
    });
});
