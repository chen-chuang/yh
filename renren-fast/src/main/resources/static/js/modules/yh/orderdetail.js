$(function () {
	var orderId = parent.$('#orderId').val();
    $("#jqGrid").jqGrid({
        url: baseURL + 'orderdetail/list',
        datatype: "json",
        colModel: [			
			{ label: '订单编号', name: 'orderId', index: 'order_id', width: 50, key: true },		
			{ label: '产品名称', name: 'productName', index: 'product_name', width: 80 }, 	
			{ label: '产品编号', hidden:true,name: 'productId', index: 'product_id', width: 80 }, 			
			{ label: '产品数量', name: 'productNum', index: 'product_num', width: 80 }, 			
			{ label: '产品单价', name: 'productPrice', index: 'product_price', width: 80 }, 			
			{ label: '产品总价', name: 'productSumPrice', index: 'product_sum_price', width: 80 }, 			
			{ label: '企业编号',hidden:true, name: 'enterpriseId', index: 'enterprise_id', width: 80 }, 			
			{ label: '企业名称',hidden:true, name: 'enterpriseName', index: 'enterprise_name', width: 80 }, 				
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
        postData:{orderId:orderId}
        ,
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
		orderdetail: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.orderdetail = {};
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
			var url = vm.orderdetail.orderId == null ? "orderdetail/save" : "orderdetail/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.orderdetail),
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
				    url: baseURL + "orderdetail/delete",
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
			$.get(baseURL + "orderdetail/info/"+orderId, function(r){
                vm.orderdetail = r.orderdetail;
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