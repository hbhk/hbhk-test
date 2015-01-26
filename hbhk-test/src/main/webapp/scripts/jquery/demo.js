$(document).ready(function() {
	// 在需要悬浮的内容外加一个id=Float
//	var BoxMarginTop = $('#PcPoPmarket').offset().top; 
//	
//	$(window).scroll(function() {
//		if ($(window).scrollTop() > BoxMarginTop) {
//			$('#PcPoPmarket').animate({
//				'margin-top' : $(window).scrollTop() - BoxMarginTop + 'px'
//			}, {
//				duration : 600,
//				queue : false
//			});
//		} else {
//			$('#PcPoPmarket').css('margin-top', 0);
//		}
//	});
	
	$(".popShow").on("click",function(){
		$('#PcPoPmarket').css("width", 350+'px');
		$('#PcPoPmarket').css("height" , 238+'px');
		$('#showvod').hide();
		$('#hidevod').show();
		var lmt = $('#lmt');
		if(lmt!=null){
			lmt.css("background-image" , 'url(bg_open.png)');
		}
	});
	
	$(".popHide").on("click",function(){
		$('#PcPoPmarket').css("width", 226+'px');
		$('#PcPoPmarket').css("height" , 37+'px');
		$('#showvod').show();
		$('#hidevod').hide();
		var lmt = $('#lmt');
		if(lmt!=null){
			lmt.css("background-image" , 'url(bg_min.png)');
		}
		
	});
	$("#PcPoPmarket" ).draggable();
	
});