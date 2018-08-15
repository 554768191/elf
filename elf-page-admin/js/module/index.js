layui.define(['config', 'layer', 'element', 'form', 'laytpl'], function (exports) {
    var config = layui.config;
    var layer = layui.layer;
    var element = layui.element;
    var form = layui.form;
    var laytpl = layui.laytpl;

    var popupRightIndex, popupCenterIndex, popupCenterParam;

    var index = {
        // 渲染左侧导航栏
        initLeftNav: function () {
            var authMenus = new Array();
            for (var i = 0; i < config.menus.length; i++) {
                var tempMenu = config.menus[i];
                var tempSubMenus = new Array();
                for (var j = 0; j < tempMenu.subMenus.length; j++) {
                    var tempSubMenu = tempMenu.subMenus[j];
                    if (index.hasPerm(tempSubMenu.auth)) {
                        tempSubMenus.push(tempSubMenu);
                    }
                }
                if (tempSubMenus.length > 0) {
                    tempMenu.subMenus = tempSubMenus;
                    authMenus.push(tempMenu);
                }
            }

            laytpl(menu.innerHTML).render(authMenus, function(html){
                $(".layui-nav-tree").innerHTML = html;
                // layui.element.render('nav', 'index-nav');
            });

            index.activeNav(Q.lash);
        },
        // 路由注册
        initRouter: function () {
            index.regRouter(config.menus);
            Q.init({
                index: 'home'
            });
        },
        // 使用递归循环注册
        regRouter: function (menus) {
            $.each(menus, function (i, data) {
                if (data.url) {
                    Q.reg(data.url, function () {
                        index.loadView('template/' + data.path);
                    });
                }
                if (data.subMenus) {
                    index.regRouter(data.subMenus);
                }
            });
        },
        // 路由加载组件
        loadView: function (path) {
            index.showLoading('.layui-layout-admin .layui-body');
            $('.layui-layout-admin .layui-body').load(path, function () {
                element.render('breadcrumb');
                form.render('select');
                index.removeLoading('.layui-layout-admin .layui-body');
            });
            index.activeNav(Q.lash);
            // 移动设备切换页面隐藏侧导航
            if (document.body.clientWidth <= 750) {
                index.flexible(true);
            }
        },

        // 从服务器获取登录用户的信息
        getUser: function (success) {
            /*
            layer.load(2);
            admin.req('userInfo', {}, function (data) {
                layer.closeAll('loading');
                if (200 == data.code) {
                    config.putUser(data.user);
                    success(data.user);
                } else {
                    layer.msg('获取用户失败', {icon: 2});
                }
            }, 'GET');
            */
            user = {"userId":"29ogl979","username":"demo","password":"{bcrypt}$2a$10$kKqZaoluuMjfvusTuB5Z6e/RPhQgCXtkmBdhGokWPIi0RdhoWxD42","nickName":"demo","avatar":null,"sex":"男","phone":"13625436602","email":null,"emailVerified":null,"personId":null,"state":0,"createTime":"2018-06-28T08:19:32.000+0000","updateTime":"2018-06-29T05:52:52.000+0000","authorities":null,"roles":[{"roleId":"user","roleName":"普通用户","comments":"系统普通用户","isDelete":0,"createTime":"2018-02-23T00:32:11.000+0000","updateTime":"2018-02-23T03:19:08.000+0000"}],"enabled":true,"accountNonExpired":true,"accountNonLocked":true,"credentialsNonExpired":true},{"userId":"admin","username":"admin","password":"{bcrypt}$2a$10$qrL6p6FKEitfGQnVRa.PPO.PsOJ4Dj9BSjEXll6fnruqBXABb/51O","nickName":"管理员","avatar":"","sex":"女","phone":"13125062807","email":"whvcse@foxmail.com","emailVerified":null,"personId":null,"state":0,"createTime":"2017-08-14T06:12:36.000+0000","updateTime":"2018-06-29T09:51:13.000+0000","authorities":null,"roles":[{"roleId":"admin","roleName":"管理员","comments":"系统管理员","isDelete":0,"createTime":"2018-02-23T00:31:22.000+0000","updateTime":"2018-02-23T03:18:26.000+0000"}],"enabled":true,"accountNonExpired":true,"accountNonLocked":true,"credentialsNonExpired":true};
            config.putUser(user);
            success(user);
        },
        // 页面元素绑定事件监听
        bindEvent: function () {
            // 退出登录
            $('#btnLogout').click(function () {
                layer.confirm('确定退出登录？', function (i) {
                    layer.close(i);
                    config.removeToken();
                    location.replace('login.html');
                });
            });
            // 修改密码
            $('#setPsw').click(function () {
                index.popupRight('template/password.html');
            });
            // 个人信息
            $('#setInfo').click(function () {

            });
            // 消息
            $('#btnMessage').click(function () {
                index.popupRight('template/message.html');
            });
        },


        // 设置侧栏折叠
        flexible: function (expand) {
            var isExapnd = $('.layui-layout-admin').hasClass('admin-nav-mini');
            if (isExapnd == !expand) {
                return;
            }
            if (expand) {
                $('.layui-layout-admin').removeClass('admin-nav-mini');
            } else {
                $('.layui-layout-admin').addClass('admin-nav-mini');
            }
            index.onResize();
        },
        // 设置导航栏选中
        activeNav: function (url) {
            $('.layui-layout-admin .layui-side .layui-nav .layui-nav-item .layui-nav-child dd').removeClass('layui-this');
            if (url && url != '') {
                $('.layui-layout-admin .layui-side .layui-nav .layui-nav-item').removeClass('layui-nav-itemed');
                var $a = $('.layui-layout-admin .layui-side .layui-nav>.layui-nav-item>.layui-nav-child>dd>a[href="#!' + url + '"]');
                $a.parent('dd').addClass('layui-this');
                $a.parent('dd').parent('.layui-nav-child').parent('.layui-nav-item').addClass('layui-nav-itemed');
            }
        },
        // 右侧弹出
        popupRight: function (path) {
            popupRightIndex = layer.open({
                type: 1,
                id: 'adminPopupR',
                anim: 2,
                isOutAnim: false,
                title: false,
                closeBtn: false,
                offset: 'r',
                shade: .2,
                shadeClose: true,
                resize: false,
                area: '336px',
                skin: 'layui-layer-adminRight',
                success: function () {
                    //admin.showLoading('#adminPopupR');
                    $('#adminPopupR').load(path, function () {
                        //admin.removeLoading('#adminPopupR');
                    });
                },
                end: function () {
                    layer.closeAll('tips');
                }
            });
        },
        // 关闭右侧弹出
        closePopupRight: function () {
            layer.close(popupRightIndex);
        },
        // 中间弹出
        popupCenter: function (param) {
            popupCenterParam = param;
            popupCenterIndex = layer.open({
                type: 1,
                id: 'adminPopupC',
                title: param.title ? param.title : false,
                shade: .2,
                offset: '120px',
                area: param.area ? param.area : '450px',
                resize: false,
                skin: 'layui-layer-adminCenter',
                success: function () {
                    $('#adminPopupC').load(param.path, function () {
                        $('#adminPopupC .close').click(function () {
                            layer.close(popupCenterIndex);
                        });
                        param.success ? param.success() : '';
                    });
                },
                end: function () {
                    layer.closeAll('tips');
                    param.end ? param.end() : '';
                }
            });
        },
        // 关闭中间弹出并且触发finish回调
        finishPopupCenter: function () {
            layer.close(popupCenterIndex);
            popupCenterParam.finish ? popupCenterParam.finish() : '';
        },
        // 关闭中间弹出
        closePopupCenter: function () {
            layer.close(popupCenterIndex);
        },

        // 窗口大小改变监听
        onResize: function () {
            if (config.autoRender) {
                if ($('.layui-table-view').length > 0) {
                    setTimeout(function () {
                        admin.events.refresh();
                    }, 800);
                }
            }
        },
        // 显示加载动画
        showLoading: function (element) {
            $(element).append('<i class="layui-icon layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop admin-loading"></i>');
        },
        // 移除加载动画
        removeLoading: function (element) {
            $(element + '>.admin-loading').remove();
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
        }
    };

    // ewAdmin提供的事件
    index.events = {
        flexible: function (e) {  // 折叠侧导航
            var expand = $('.layui-layout-admin').hasClass('admin-nav-mini');
            admin.flexible(expand);
        },
        refresh: function () {  // 刷新主体部分
            Q.refresh();
        },
        back: function () {  //后退
            history.back();
        },
        theme: function () {  // 设置主题
            admin.popupRight('components/tpl/theme.html');
        },
        fullScreen: function (e) {  // 全屏
            var ac = 'layui-icon-screen-full', ic = 'layui-icon-screen-restore';
            var ti = $(this).find('i');

            var isFullscreen = document.fullscreenElement || document.msFullscreenElement || document.mozFullScreenElement || document.webkitFullscreenElement || false;
            if (isFullscreen) {
                var efs = document.exitFullscreen || document.webkitExitFullscreen || document.mozCancelFullScreen || document.msExitFullscreen;
                if (efs) {
                    efs.call(document);
                } else if (window.ActiveXObject) {
                    var ws = new ActiveXObject('WScript.Shell');
                    ws && ws.SendKeys('{F11}');
                }
                ti.addClass(ac).removeClass(ic);
            } else {
                var el = document.documentElement;
                var rfs = el.requestFullscreen || el.webkitRequestFullscreen || el.mozRequestFullScreen || el.msRequestFullscreen;
                if (rfs) {
                    rfs.call(el);
                } else if (window.ActiveXObject) {
                    var ws = new ActiveXObject('WScript.Shell');
                    ws && ws.SendKeys('{F11}');
                }
                ti.addClass(ic).removeClass(ac);
            }
        }
    };

    // 所有ew-event
    $('body').on('click', '*[ew-event]', function () {
        var event = $(this).attr('ew-event');
        var te = admin.events[event];
        te && te.call(this, $(this));
    });

    // 移动设备遮罩层点击事件
    $('.site-mobile-shade').click(function () {
        admin.flexible(true);
    });

    // 侧导航折叠状态下鼠标经过显示提示
    $('body').on('mouseenter', '.layui-layout-admin.admin-nav-mini .layui-side .layui-nav .layui-nav-item>a', function () {
        var tipText = $(this).find('cite').text();
        if (document.body.clientWidth > 750) {
            layer.tips(tipText, this);
        }
    }).on('mouseleave', '.layui-layout-admin.admin-nav-mini .layui-side .layui-nav .layui-nav-item>a', function () {
        layer.closeAll('tips');
    });

    // 侧导航折叠状态下点击展开
    $('body').on('click', '.layui-layout-admin.admin-nav-mini .layui-side .layui-nav .layui-nav-item>a', function () {
        if (document.body.clientWidth > 750) {
            layer.closeAll('tips');
            admin.flexible(true);
        }
    });

    // 所有lay-tips处理
    $('body').on('mouseenter', '*[lay-tips]', function () {
        var tipText = $(this).attr('lay-tips');
        var dt = $(this).attr('lay-direction');
        layer.tips(tipText, this, {tips: dt || 1, time: -1});
    }).on('mouseleave', '*[lay-tips]', function () {
        layer.closeAll('tips');
    });

    exports('index', index);
});
