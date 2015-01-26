
var currentDocument = null;
var timerSave = 1000;
var stopsave = 0;
var startdrag = 0;

$(document).ready(function() {
	// Drag.init(document.getElementById("foo"));

	// $("[id]").each(function(i, dom) {
	// var me = $(this);
	// Drag.init(me.get(0));
	// });

	// $('p').mouseup(function() {
	// alert('mouseup function is running !');
	// });
//	CKEDITOR.disableAutoInline = true;
//	var contenthandle = CKEDITOR.replace( 'contenteditor' ,{
//		language: 'zh-cn',
//		allowedContent: true
//	});
	$(".column").sortable({ 
		revert: false 
	});
	$(".sidebar-nav .lyrow").draggable({
		connectToSortable: ".demo",
		helper: "clone",
		handle: ".drag",
		start: function(e,t) {
			if (!startdrag) stopsave++;
			startdrag = 1;
		},
		drag: function(e, t) {
			t.helper.width(400)
		},
		stop: function(e, t) {
			$(".demo .column").sortable({
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
	$(".sidebar-nav .box").draggable({
		connectToSortable:".column",
		helper : "clone",
		//handle: ".drag",
		start : function(e, t) {
			if (!startdrag)
				stopsave++;
			startdrag = 1;
		},
		drag : function(e, t) {
			t.helper.width(400);
		},
		stop : function(event, ui) {
			//$(this).html(ui.helper.eq(0).prop("outerHTML"));
//			$(".demo .column").sortable({
//				opacity: .35,
//				connectWith: ".column",
//				start: function(e,t) {
//					if (!startdrag) stopsave++;
//					startdrag = 1;
//				},
//				stop: function(e,t) {
//					if(stopsave>0) stopsave--;
//					startdrag = 0;
//				}
//			});
			handleJsIds();
			if (stopsave > 0){
				stopsave--;
			}
			startdrag = 0;
		}
	});
	
	$(".demo").droppable({
	      drop: function( event, ui ) {
	        $(this).append(ui.draggable.prop("outerHTML"));
	      }
	});
	
//	$('.box').mouseenter(function(){
//        alert('mouseenter function is running !');
//    });
});

function handleJsIds() {
	handleModalIds();
	handleAccordionIds();
	handleCarouselIds();
	handleTabsIds();
}
function handleAccordionIds() {
	var e = $(".demo #myAccordion");
	var t = randomNumber();
	var n = "accordion-" + t;
	var r;
	e.attr("id", n);
	e.find(".accordion-group").each(function(e, t) {
		r = "accordion-element-" + randomNumber();
		$(t).find(".accordion-toggle").each(function(e, t) {
			$(t).attr("data-parent", "#" + n);
			$(t).attr("href", "#" + r);
		});
		$(t).find(".accordion-body").each(function(e, t) {
			$(t).attr("id", r);
		});
	});
}
function handleCarouselIds() {
	var e = $(".demo #myCarousel");
	var t = randomNumber();
	var n = "carousel-" + t;
	e.attr("id", n);
	e.find(".carousel-indicators li").each(function(e, t) {
		$(t).attr("data-target", "#" + n);
	});
	e.find(".left").attr("href", "#" + n);
	e.find(".right").attr("href", "#" + n);
}
function handleModalIds() {
	var e = $(".demo #myModalLink");
	var t = randomNumber();
	var n = "modal-container-" + t;
	var r = "modal-" + t;
	e.attr("id", r);
	e.attr("href", "#" + n);
	e.next().attr("id", n);
}
function handleTabsIds() {
	var e = $(".demo #myTabs");
	var t = randomNumber();
	var n = "tabs-" + t;
	e.attr("id", n);
	e.find(".tab-pane").each(function(e, t) {
		var n = $(t).attr("id");
		var r = "panel-" + randomNumber();
		$(t).attr("id", r);
		$(t).parent().parent().find("a[href=#" + n + "]").attr("href", "#" + r)
	});
}
function randomNumber() {
	return randomFromInterval(1, 1e6);
}
function randomFromInterval(e, t) {
	return Math.floor(Math.random() * (t - e + 1) + e);
}