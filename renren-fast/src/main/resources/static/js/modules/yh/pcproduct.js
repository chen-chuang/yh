$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'product/pclist',
        datatype: "json",
        colModel: [			
			{ label: 'productId', hidden:true,name: 'productId', index: 'product_id', width: 50, key: true },
			{ label: '产品名称', align: 'center',name: 'productName', index: 'product_name', width: 80 }, 		
			{ label: '产品类型',align: 'center', name: 'productTypeName', index: 'product_type_name', width: 80 }, 			
			{ label: '库存', align: 'center',name: 'productNum', index: 'product_num', width: 80 }, 			
			{ label: '批发价',align: 'center', name: 'productTradePrice', index: 'product_trade_price', width: 80 }, 			
			{ label: '零售价',align: 'center', name: 'productRetailPrice', index: 'product_retail_price', width: 80 }, 			
			{ label: '是否热销',align: 'center', name: 'isHot', index: 'is_hot', width: 80, formatter: function(value, options, row){
				return value === 0 ? 
						'热销' : 
						'不热销';
			}}	
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
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
        	$("th[role='columnheader']").css('text-align','center')    
        	
        }
    });
});


var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		product: {isHot:1},
        enterPrises:{},
		currentPermission:1,
        types:{}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		getUserPermission : function(){
		    $.get(baseURL + "sys/user/currentLoginUser", function(r){
		    	vm.currentPermission = r.currentLoginUser.userPermission;
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
