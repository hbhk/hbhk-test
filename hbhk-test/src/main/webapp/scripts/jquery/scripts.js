function removeElm() {
	$(".web-update").contents().find(".demo").delegate(".remove", "click", function(e) {
		e.preventDefault();
		$(this).parent().remove();
		if (!$(".demo .lyrow").length > 0) {
			$(".demo").empty();
		}
	});
}
var stopsave = 0;
var startdrag = 0;

function initContainer(){
	$(".web-update").contents().find(".demo, .demo .column").sortable({
		connectWith: ".column",
		opacity: .35,
		handle: ".drag",
		start: function(e,t) {
			if (!startdrag) stopsave++;
			startdrag = 1;
		},
		stop: function(e,t) {
			if(stopsave>0) stopsave--;
			startdrag = 0;
		}
	});
}
$(document).ready(function() {
	$(".sidebar-nav .lyrow").draggable({
		connectToSortable: ".demo",
		helper: "clone",
		handle: ".drag",
		iframeFix: true,
		start: function(e,t) {
			if (!startdrag) stopsave++;
			startdrag = 1;
		},
		drag: function(e, t) {
			t.helper.width(400);
		},
		stop: function(e, t) {
			var edit = t.helper.eq(0);
			/* position: absolute; */
			/* left: 556px; */
			/* top: 167px; */
			//删除绝对布局
			var html = $(".nav-list").find("."+edit.attr("zj")).prop("outerHTML");
			$(".web-update").contents().find(".demo").append(html);
			
			$(".web-update").contents().find(".demo .column").sortable({
				opacity: .35,
				connectWith: ".column",
				start: function(e,t) {
					if (!startdrag) stopsave++;
					startdrag = 1;
				},
				stop: function(e,t) {
					if(stopsave>0) stopsave--;
					startdrag = 0;
				}
			});
			if(stopsave>0) stopsave--;
			startdrag = 0;
		}
	});
	
	initContainer();
	removeElm();
	
});