layui.use(['form', 'table', 'util', 'config', 'index'], function () {
    var form = layui.form;
    var table = layui.table;
    var config = layui.config;
    var layer = layui.layer;
    var util = layui.util;
    var index = layui.index;

    // 渲染表格
    table.render({
        elem: '#user-table',
        url: config.base_server + 'user',
        method: 'get',
        where: {
            token: config.getToken()
        },
        page: true,
        cols: [[
            {type: 'numbers'},
            {field: 'account', sort: true, title: '账号'},
            {field: 'nickName', sort: true, title: '昵称'},
            {field: 'phone', sort: true, title: '手机号'},
            {field: 'sex', sort: true, title: '性别'},
            {
                sort: true, templet: function (d) {
                    return util.toDateString(d.createTime);
                }, title: '创建时间'
            },
            // {field: 'state', sort: true, templet: '#user-tpl-state', title: '状态'},
            {align: 'center', toolbar: '#user-table-bar', title: '操作'}
        ]]
    });

    // 添加按钮点击事件
    $('#user-btn-add').click(function () {
        showEditModel();
    });

    // 工具条点击事件
    table.on('tool(user-table)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') { // 修改
            showEditModel(data);
        } else if (layEvent === 'reset') { // 重置密码
            layer.confirm('确定重置此用户的密码吗？', function (i) {
                layer.close(i);
                layer.load(2);
                config.req('user/psw/' + obj.data.userId, {}, function (data) {
                    layer.closeAll('loading');
                    if (data.code == 200) {
                        layer.msg(data.msg, {icon: 1});
                    } else {
                        layer.msg(data.msg, {icon: 2});
                    }
                }, 'PUT');
            });
        }
    });

    //显示表单弹窗
    var showEditModel = function (data) {
        indx.putTempData('t_user', data);
        var title = data ? '修改用户' : '添加用户';
        index.popupCenter({
            title: title,
            path: 'template/system/user_form.html',
            finish: function () {
                table.reload('user-table', {});
            }
        });
    };

    // 搜索按钮点击事件
    $('#user-btn-search').click(function () {
        var key = $('#user-search-key').val();
        var value = $('#user-search-value').val();
        table.reload('user-table', {where: {searchKey: key, searchValue: value}});
    });

    // 修改user状态
    form.on('switch(user-tpl-state)', function (obj) {
        layer.load(2);
        config.req('user/state', {
            userId: obj.elem.value,
            state: obj.elem.checked ? 0 : 1
        }, function (data) {
            layer.closeAll('loading');
            if (data.code == 200) {
                layer.msg(data.msg, {icon: 1});
                //table.reload('table-user', {});
            } else {
                layer.msg(data.msg, {icon: 2});
                $(obj.elem).prop('checked', !obj.elem.checked);
                form.render('checkbox');
            }
        }, 'PUT');
    });
});
