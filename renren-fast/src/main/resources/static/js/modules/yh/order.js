$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'order/list',
        datatype: "json",
        colModel: [			
			{ label: '订单编号', name: 'orderId', index: 'order_id', width: 50, key: true },
			{ label: '销售员id',hidden:true, name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: '订单生成时间', name: 'orderCreateTime', index: 'order_create_time', width: 80 }, 			
			{ label: '使用积分数', name: 'userIntegralCount', index: 'user_integral_count', width: 80 }, 			
			{ label: '订单总价格', name: 'orderAllPrice', index: 'order_all_price', width: 80 }, 			
			{ label: '订单状态', name: 'orderType', index: 'order_type', width: 80, formatter: function(value, options, row){
				if(value===0){
					return '<span>待支付</span>';
				}else if(value===1){
					return '<span>已支付</span>';
				}else if(value===2){
					return '<span>待配送</span>';
				}else if(value===3){
					return '<span>已支付</span>';
				}
		    }},				
			{ label: '下单地址ID', hidden:true,name: 'townId', index: 'town_id', width: 80 }, 			
			{ label: '下单地址', name: 'orderAddress', index: 'order_address', width: 80 }, 			
			{ label: '要求配送时间', name: 'orderSendTime', index: 'order_send_time', width: 80 }, 			
			{ label: '详细地址', name: 'orderDetailAddress', index: 'order_detail_address', width: 80 }, 			
			{ label: '买者电话', name: 'receiverPhone', index: 'receiver_phone', width: 80 }, 			
			{ label: '买者姓名', name: 'receiverName', index: 'receiver_name', width: 80 }, 			
			{ label: '备注', name: 'mark', index: 'mark', width: 80 }, 			
			{ label: '订单支付方式（1：支付宝，2：微信）', hidden:true,name: 'orderPayType', index: 'order_pay_type', width: 80, formatter: function(value, options, row){
				if(value===1){
					return '<span>支付宝</span>';
				}else if(value===2){
					return '<span>微信</span>';
				}
		    }},	
			{ label: '操作', width: 80,formatter: function(value, options, row){
				console.log(row.id);
				return '<a onclick=dispatch('+row.orderId+')>配送</a>|<a onclick=complete('+row.orderId+')>完成</a>|<a onclick=showdetail('+row.orderId+')>查看明细</a>';
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
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

function setUserId(userId) {
	$("#userId").val(userId);
}

//配送
function dispatch(orderId){
	
	var content ='<div class="user_select_view">'
	
		/*	 +'  <div class="view_left" id="view_left">'
			 +'   <ul>'
			 +'    <li >1</li>'
			 +'    <li >1</li>'
			 +'   <li >1</li>'
			 +'  </ul>'
			 +' </div>'*/
			 +' <div class="view_right" id="view_right">'
			 +'   <ul>';
			 
	
	//展示配送员
	$.ajax({
		type : "POST",
		url : baseURL + "order/getDeliveryPerson",
		success : function(r) {
			if (r.code == 0) {
				if(r.deliveryPerson){
					$.each(r.deliveryPerson,function(k,v){
						content+='<li onclick = setUserId('+v["user_id"]+') userid = '+v["user_id"]+'>'+v["user_name"]+'</li>' ;
					})
					
				}
			} 
			content +='  </ul>'
						 +' </div>'
						 +'</div>';
		}
	});
	
	
	
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
	    		
	    		/* var regionId = $(window.frames["layui-layer-iframe1"].document).find("#regionId").val();
	    		 var regionName = $(window.frames["layui-layer-iframe1"].document).find("#regionName").val();*/
	    		
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
	        ,btn2: function(){
	          layer.closeAll();
	        }
	      });
	 

}
function complete(orderId){
	$.ajax({
		type : "POST",
		url : baseURL + "order/complete",
		data : {
			id : id,
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
		order: {}
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