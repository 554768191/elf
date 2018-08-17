layui.use(['laydate', 'table', 'util', 'config'], function () {
    var laydate = layui.laydate;
    var table = layui.table;
    var config = layui.config;
    var util = layui.util;

    //渲染表格
    table.render({
        elem: '#log-table',
        url: config.base_server + 'log',
        where: {
            token: config.getToken()
        },
        page: true,
        cols: [[
            {type: 'numbers'},
            {field: 'username', sort: true, title: '账号'},
            {field: 'nickName', sort: true, title: '用户名'},
            {field: 'ipAddress', sort: true, title: 'IP'},
            {field: 'device', sort: true, title: '设备'},
            {field: 'osName', sort: true, title: '设备类型'},
            {field: 'browserType', sort: true, title: '浏览器'},
            {
                field: 'createTime', sort: true, templet: function (d) {
                    return util.toDateString(d.createTime);
                }, title: '登录时间'
            }
        ]]
    });

    //时间范围
    laydate.render({
        elem: '#log-edt-date',
        type: 'date',
        range: true,
        theme: 'molv'
    });

    //搜索按钮点击事件
    $('#log-btn-search').click(function () {
        var searchDate = $('#log-edt-date').val().split(' - ');
        var searchAccount = $('#log-edt-account').val();
        table.reload('log-table', {
            where: {
                startDate: searchDate[0],
                endDate: searchDate[1],
                account: searchAccount
            }
        });
    });
});
