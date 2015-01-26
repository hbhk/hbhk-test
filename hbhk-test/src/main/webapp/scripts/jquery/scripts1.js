function removeElm() {
	$("body").delegate(".remove", "click", function(e) {
		e.preventDefault();
		$(this).parent().remove();
		if (!$("body .lyrow").length > 0) {
			$("body").empty();
		}
	});
}
var stopsave = 0;
var startdrag = 0;

function initContainer() {
	$("body, body .column").sortable({
		connectWith : ".column",
		opacity : .35,
		handle : ".drag",
		start : function(e, t) {
			if (!startdrag)
				stopsave++;
			startdrag = 1;
		},
		stop : function(e, t) {
			if (stopsave > 0)
				stopsave--;
			startdrag = 0;
		}
	});
}
$(document).ready(function() {
	$(".sidebar-nav .lyrow").draggable({
		connectToSortable : "body",
		helper : "clone",
		handle : ".drag",
		iframefix : true,
		start : function(e, t) {
			if (!startdrag)
				stopsave++;
			startdrag = 1;
		},
		drag : function(e, t) {
			t.helper.width(400);
		},
		stop : function(e, t) {
			$(this);
			$("body .column").sortable({
				opacity : .35,
				connectWith : ".column",
				start : function(e, t) {
					if (!startdrag)
						stopsave++;
					startdrag = 1;
				},
				stop : function(e, t) {
					if (stopsave > 0)
						stopsave--;
					startdrag = 0;
				}
			});
			if (stopsave > 0)
				stopsave--;
			startdrag = 0;
		}
	});

	initContainer();
	removeElm();

});