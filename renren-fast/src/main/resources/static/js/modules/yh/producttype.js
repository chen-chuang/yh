$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'producttype/list',
        datatype: "json",
        colModel: [			
			{ label: 'id',hidden:true, name: 'id', index: 'id', width: 50, key: true },
			{ label: '分类名称', name: 'type', index: 'type', width: 80 }			
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
		producttype: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.producttype = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.producttype.id == null ? "producttype/save" : "producttype/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.producttype),
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
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}		
			
			
			$.ajax({
				type: "POST",
			    url: baseURL + "producttype/getProductByType",
                contentType: "application/json",
			    data: JSON.stringify(ids),
			    success: function(r){
					if(r.count > 0){
						
						confirm('所选分类下有产品，删除分类产品将一并删除，确定吗？', function(){
							$.ajax({
								type: "POST",
							    url: baseURL + "producttype/delete",
			                    contentType: "application/json",
							    data: JSON.stringify(ids),
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
						
					}
				}
			});
			
			
		},
		getInfo: function(id){
			$.get(baseURL + "producttype/info/"+id, function(r){
                vm.producttype = r.producttype;
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