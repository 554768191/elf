layui.use(['form', 'table', 'util', 'config', 'index', 'base', 'laydate'], function () {
    var form = layui.form;
    var table = layui.table;
    var config = layui.config;
    var layer = layui.layer;
    var util = layui.util;
    var index = layui.index;
    var base = layui.base;
    var laydate = layui.laydate;

    //时间范围
    laydate.render({
        elem: '#privilege-date',
        type: 'date',
        range: true,
        theme: 'molv'
    });

    // 渲染表格
    var renderTable = function (){
        table.render({
            elem: '#auth-table',
            url: config.base_server + 'privilege',
            where: {
                token: base.getToken()
            },
            page: true,
            cols: [[
                {type: 'numbers'},
                {field: 'privilegeName', sort: false, title: '权限'},
                {field: 'parentName', sort: false, title: '上级'},
                {field: 'link', sort: false, title: 'url'},
                {field: 'seq', sort: true, title: '排序'},
                {
                    field: 'category', width: 80, align: 'center', templet: function (d) {
                        if (d.category == 3) {
                            return '<span class="layui-badge layui-bg-gray">按钮</span>';
                        }
                        if (d.category == 1) {
                            return '<span class="layui-badge layui-bg-blue">目录</span>';
                        } else {
                            return '<span class="layui-badge-rim">菜单</span>';
                        }
                    }, title: '类型'
                },
                {
                    field: 'createTime', sort: true, templet: function (d) {
                        return util.toDateString(d.createTime);
                    }, title: '创建时间'
                },
                {templet: '#auth-opt', width: 120, align: 'center', title: '操作'}
            ]]
        });
    }

    renderTable();

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

    // 工具条点击事件
    table.on('tool(auth-table)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        console.log(data);
        if (layEvent === 'edit') { // 修改
            showEditModel(data);
        } else if (layEvent === 'del') { // 重置密码
            layer.confirm('确定删除此权限吗？', function () {
                layer.load(2);
                $.post('system/authorities/delete', {
                    authorityId: obj.data.authorityId
                }, function (data) {
                    layer.closeAll('loading');
                    if (data.code == 200) {
                        layer.msg(data.msg, {icon: 1});
                        renderTable();
                    } else {
                        layer.msg(data.msg, {icon: 2});
                    }
                });
            });
        }
    });

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

    // 同步按钮点击事件
    $('#privilege-btn-add').click(function () {
        showEditModel();
    });

    // 显示表单弹窗
    var showEditModel = function (data) {
        var title = data ? '修改权限' : '添加权限';
        base.putTempData('t_authoritie', data);
        index.popupCenter({
            title: title,
            path: 'template/system/privilege_form.html',
            finish: function () {
                renderTable();
            }
        });
    };

});
