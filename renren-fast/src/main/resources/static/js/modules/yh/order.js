( function ( $ ){
 $.fn.addBack = $.fn.addBack || $.fn.andSelf;

 $.fn.extend({

   actual : function ( method, options ){
     // check if the jQuery method exist
     if( !this[ method ]){
       throw '$.actual => The jQuery method "' + method + '" you called does not exist';
     }

     var defaults = {
       absolute      : false,
       clone         : false,
       includeMargin : false
     };

     var configs = $.extend( defaults, options );

     var $target = this.eq( 0 );
     var fix, restore;

     if( configs.clone === true ){
       fix = function (){
         var style = 'position: absolute !important; top: -1000 !important; ';

         // this is useful with css3pie
         $target = $target.
           clone().
           attr( 'style', style ).
           appendTo( 'body' );
       };

       restore = function (){
         // remove DOM element after getting the width
         $target.remove();
       };
     }else{
       var tmp   = [];
       var style = '';
       var $hidden;

       fix = function (){
         // get all hidden parents
         $hidden = $target.parents().addBack().filter( ':hidden' );
         style   += 'visibility: hidden !important; display: block !important; ';

         if( configs.absolute === true ) style += 'position: absolute !important; ';

         // save the origin style props
         // set the hidden el css to be got the actual value later
         $hidden.each( function (){
           var $this = $( this );

           // Save original style. If no style was set, attr() returns undefined
           tmp.push( $this.attr( 'style' ));
           $this.attr( 'style', style );
         });
       };

       restore = function (){
         // restore origin style values
         $hidden.each( function ( i ){
           var $this = $( this );
           var _tmp  = tmp[ i ];

           if( _tmp === undefined ){
             $this.removeAttr( 'style' );
           }else{
             $this.attr( 'style', _tmp );
           }
         });
       };
     }

     fix();
     // get the actual value with user specific methed
     // it can be 'width', 'height', 'outerWidth', 'innerWidth'... etc
     // configs.includeMargin only works for 'outerWidth' and 'outerHeight'
     var actual = /(outer)/.test( method ) ?
       $target[ method ]( configs.includeMargin ) :
       $target[ method ]();

     restore();
     // IMPORTANT, this plugin only return the value of the first element
     return actual;
   }
 });
})( jQuery );

$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'order/list',
        datatype: "json",
        colModel: [			
			{ label: '订单编号', name: 'orderId', index: 'order_id', width: 230, key: true },
			{ label: '销售员id',hidden:true, name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: '订单生成时间', name: 'orderCreateTime', index: 'order_create_time', width: 140 }, 			
			{ label: '使用积分数', name: 'userIntegralCount', index: 'user_integral_count', width: 80 }, 			
			{ label: '订单总价格', name: 'orderAllPrice', index: 'order_all_price', width: 80 }, 
			{ label: '下单地址ID', hidden:true,name: 'townId', index: 'town_id', width: 80 }, 			
			{ label: '下单地址', name: 'orderAddress', index: 'order_address', width: 150 }, 			
			{ label: '要求配送时间', name: 'orderSendTime', index: 'order_send_time', width: 140 }, 			
			{ label: '详细地址', name: 'orderDetailAddress', index: 'order_detail_address', width: 250 }, 			
			{ label: '买者电话', name: 'receiverPhone', index: 'receiver_phone', width: 120 }, 			
			{ label: '买者姓名', name: 'receiverName', index: 'receiver_name', width: 80 }, 			
			{ label: '备注', name: 'mark', index: 'mark', width: 180 }, 			
			{ label: '订单支付方式（1：支付宝，2：微信）', hidden:true,name: 'orderPayType', index: 'order_pay_type', width: 80, formatter: function(value, options, row){
				if(value===1){
					return '<span>支付宝</span>';
				}else if(value===2){
					return '<span>微信</span>';
				}
		    }},
		    { label: '订单状态', name: 'orderType', index: 'order_type', width: 80, formatter: function(value, options, row){
				if(value===0){
					return '<span>待支付</span>';
				}else if(value===1){
					return '<span>已支付</span>';
				}else if(value===2){
					return '<span>正在配送</span>';
				}else if(value===3){
					return '<span>已完成</span>';
				}
		    }},	
		    { label: '配送员', name: 'deliveryUserName', index: 'delivery_user_name', width: 120 }, 	
			{ label: '操作', width: 200,formatter: function(value, options, row){
				if(row.orderType===1){
					var content="";
					
					if(hasPermission('order:choiceDelivery')){
						content +='<a class="btn btn-default btn-xs" onclick=choiceDelivery("'+row.orderId+'")>配送员</a>';
					}
					
					if(hasPermission('order:dispatch')){
						content +='<a class="btn btn-default btn-xs" onclick=dispatch("'+row.orderId+'")>配送</a>';
					}
					
					if(hasPermission('orderdetail:list')){
						content +='<a class="btn btn-info btn-xs" onclick=showdetail("'+row.orderId+'")>查看明细</a>';
					}
					
					if(row["deliveryUserName"]!=null&&row["deliveryUserName"]!=""){
						if(hasPermission('order:print')){
							content +='<a href="javascript:;" class="btn btn-default btn-xs" onclick=print("'+row.orderId+'")>打印</a>';
						}
					}
					
					return content;
				}else if(row.orderType===2){
					
					var content="";
					if(hasPermission('order:complete')){
						content +='<a class="btn btn-success btn-xs" onclick=complete("'+row.orderId+'")>完成</a>';
					}
					
					if(hasPermission('orderdetail:list')){
						content +='<a class="btn btn-info btn-xs" onclick=showdetail("'+row.orderId+'")>查看明细</a>';
					}
					return content;					
					
				}else{
					if(hasPermission('orderdetail:list')){
					  return '<a class="btn btn-info btn-xs" onclick=showdetail("'+row.orderId+'")>查看明细</a>';
					}
				}
			}}
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        shrinkToFit:false,
        autoScroll: false,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	//$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
    
  /* $("#view_right").delegate("ul li","click",function(){
	   alert(11111);
	   console.log(11111111111111);
	   $("#userId").val($(this).attr("userid"));
	   $(this).addClass("choiceLi").siblings().removeClass("choiceLi");
   });*/
    
    $("body").on("click","#view_right ul li",function(){    	
 	   $("#userId").val($(this).attr("userid"));
 	   $(this).addClass("choiceLi").siblings().removeClass("choiceLi");
    });
});

/*function setUserId(userId,obj) {
	$("#userId").val(userId);
	li.onclick(function(){
        $(obj).addClass("choiceLi").siblings().removeClass("choiceLi");
    });
	
}*/

/*function print(orderId){
	var headstr = "<html><head><title></title></head><body>";  
	var footstr = "</body>";  
	var printData = document.getElementById("print_container").innerHTML; 
	var oldstr = document.body.innerHTML;  
	document.body.innerHTML = headstr+printData+footstr;  
	//window.parent.print();  
	window.print();  
	document.body.innerHTML = oldstr;  
}*/


//通知配送员
function choiceDelivery(orderId){
	
	$("#userId").val("");
	
	var content ='<div class="user_select_view">'
		
		/*	 +'  <div class="view_left" id="view_left">'
			 +'   <ul>'
			 +'    <li >1</li>'
			 +'    <li >1</li>'
			 +'   <li >1</li>'
			 +'  </ul>'
			 +' </div>'*/
			 +' <div class="view_right" id="view_right">'
			 +'   <ul style="margin:0;padding:0">';
			 
	
	//展示配送员
	$.ajax({
		type : "POST",
		url : baseURL + "order/getDeliveryPerson",
		success : function(r) {
			if (r.code == 0) {
				if(r.deliveryPerson){
					$.each(r.deliveryPerson,function(k,v){
						/*content+='<li onclick = setUserId("'+v.user_id+'",this) userid = '+v["user_id"]+'>'+v.username+'</li>' ;*/
						content+='<li userid = '+v["user_id"]+'>'+v.username+'</li>' ;
					})
					
				}
			} 
			content +='  </ul>'
						 +' </div>'
						 +'</div>';
			

			 layer.open({
			        type: 1
			        ,title: '选择配送员' 
			        ,area: ['450px', '300px']
			        ,shade: 0
			        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
			        ,btn: ['确定', '关闭']
			        ,moveType: 1 //拖拽模式，0或者1
			        ,content: content
			    	 ,yes: function(){    
			    		 
			    		 if($("#userId").val()==""|| $("#userId").val()==null){
			    			 alert("请选择配送员之后在做操作！");
			    			 return;
			    		 }
			    		
		    			$.ajax({
		    				type : "POST",
		    				url : baseURL + "order/choiceDelivery",
		    				data : {
		    					orderId : orderId,
		    					userId : $("#userId").val()
		    				},
		    				success : function(r) {
		    					if (r.code == 0) {
		    						alert('指派成功！', function() {
		    							vm.reload();
		    							layer.closeAll();
		    						});
		    					} else {
		    						alert(r.msg);
		    					}
		    				}
		    			});
			        }
			        ,btn2: function(){
			          layer.closeAll();
			        }
			      });
		}
	});
}

//配送
function dispatch(orderId){
	
	$.ajax({
		type : "POST",
		url : baseURL + "order/dispatch",
		data : {
			orderId : orderId,
			userId : $("#userId").val()
		},
		success : function(r) {
			if (r.code == 0) {
				alert('指派成功！', function() {
					vm.reload();
					layer.closeAll();
				});
			} else {
				alert(r.msg);
			}
		}
	});
}
function complete(orderId){
	$.ajax({
		type : "POST",
		url : baseURL + "order/complete",
		data : {
			orderId : orderId,
			type:"accept"
		},
		success : function(r) {
			if (r.code == 0) {
				alert('受理成功！', function() {
					vm.reload();
					layer.closeAll();
				});
			} else {
				alert(r.msg);
			}
		}
	});
}


function showdetail(orderId){
	console.log(orderId);
	$("#orderId").val(orderId);
	 layer.open({
	        type: 2
	        ,title: '订单明细' 
	        ,area: ['800px', '600px']
	        ,shade: 0
	        ,maxmin: true
	        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
	        ,btn: ['确定']
	        ,moveType: 1 //拖拽模式，0或者1
	        ,content: '/modules/yh/orderdetail.html'
	    	 ,yes: function(){
	    		 layer.closeAll();
	        }
	      });
}

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		order: {},
		delivery:{}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.order = {};
		},
		update: function (event) {
			var orderId = getSelectedRow();
			if(orderId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(orderId)
		},
		saveOrUpdate: function (event) {
			var url = vm.order.orderId == null ? "order/save" : "order/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.order),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var orderIds = getSelectedRows();
			if(orderIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "order/delete",
                    contentType: "application/json",
				    data: JSON.stringify(orderIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(orderId){
			$.get(baseURL + "order/info/"+orderId, function(r){
                vm.order = r.order;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});


function print(orderId){
	
	$.ajax({
		type : "POST",
		url : baseURL + "order/print",
		data : {
			orderId : orderId,
			userId : $("#userId").val()
		},
		success : function(r) {
			if (r.code == 0) {
				
				$("#orderId1").html(r.order.orderId);
				$("#createTime").html(r.order.createTime);
				$("#deliveryFaName").html(r.order.deliveryFaName);
				$("#deliveryName").html(r.order.deliveryName);
				$("#agency").html(r.order.agency);
				$("#productAllPrice").html(r.order.productAllPrice);
				$("#useIntegral").html(r.order.useIntegral);
				$("#productActualPrice").html(r.order.productActualPrice);
				$("#receiverName").html(r.order.receiverName);
				$("#orderDetailAddress").html(r.order.orderDetailAddress);
				$("#receiverPhone").html(r.order.receiverPhone);
				
				if(r.order.products){
					var content ="";
					
					$.each(r.order.products, function(k,v){
						content += ' <tr><td>'+v.productName+'</td><td>'+v.productNum+'</td><td>'+v.productSumPrice+'</td></tr>';
					})
					$("#tbody").append(content);
				}
				
				var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
				LODOP.PRINT_INIT("打印");  
				LODOP.SET_PRINT_PAGESIZE(3,580,100,"");
				//var table_height= document.getElementById("print_container").offsetHeight; 
				var table_height= $('.print_container').actual('outerHeight');
				LODOP.ADD_PRINT_HTM("0","5px","580", table_height,document.getElementById("print_container").innerHTML); 
				LODOP.PREVIEW();
				//LODOP.PRINT(); 
			} else {
				alert(r.msg);
			}
		}
	});
}