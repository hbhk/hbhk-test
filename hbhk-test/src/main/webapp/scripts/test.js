// 1. 设置cookie的值，把name变量的值设为value
$.cookie("name", "value");
// 2.新建一个cookie 包括有效期 路径 域名等
$.cookie("name", "value", {
	expires : 7,
	path : "/",
	domain : "jquery.com",
	secure : true
});
// 3.新建cookie
$.cookie("name", "value");
// 4.删除一个cookie
$.cookie("name", null);
// 5.取一个cookie(name)值给myvar
var account = $.cookie('name');