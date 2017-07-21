$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'order/list',
        datatype: "json",
        colModel: [			
			{ label: 'orderId', name: 'orderId', index: 'order_id', width: 50, key: true },
			{ label: '销售员id', name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: '订单生成时间', name: 'orderCreateTime', index: 'order_create_time', width: 80 }, 			
			{ label: '使用积分数', name: 'userIntegralCount', index: 'user_integral_count', width: 80 }, 			
			{ label: '订单总价格', name: 'orderAllPrice', index: 'order_all_price', width: 80 }, 			
			{ label: '订单状态(0待支付 1已支付 2代配送 3已完成)', name: 'orderType', index: 'order_type', width: 80 }, 			
			{ label: '下单地址ID', name: 'townId', index: 'town_id', width: 80 }, 			
			{ label: '下单地址', name: 'orderAddress', index: 'order_address', width: 80 }, 			
			{ label: '要求配送时间', name: 'orderSendTime', index: 'order_send_time', width: 80 }, 			
			{ label: '详细地址', name: 'orderDetailAddress', index: 'order_detail_address', width: 80 }, 			
			{ label: '买者电话', name: 'receiverPhone', index: 'receiver_phone', width: 80 }, 			
			{ label: '买者姓名', name: 'receiverName', index: 'receiver_name', width: 80 }, 			
			{ label: '备注', name: 'mark', index: 'mark', width: 80 }, 			
			{ label: '订单支付方式（1：支付宝，2：微信）', name: 'orderPayType', index: 'order_pay_type', width: 80 }			
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