$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'configtable/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', hidden:true,name: 'id', index: 'id', width: 50, key: true },
			{ label: '配置key',hidden:true, name: 'configKey', index: 'config_key', width: 80 }, 			
			{ label: '配置名称', name: 'configName', index: 'config_name', width: 80 }, 			
			{ label: '配置值', name: 'configValue', index: 'config_value', width: 80 }, 			
			{ label: '配置人id', hidden:true,name: 'configUserId', index: 'config_user_id', width: 80 }, 			
			{ label: '配置人名称', name: 'configUserName', index: 'config_user_name', width: 80 }, 			
			{ label: '配置人区域id', hidden:true,name: 'configRegionId', index: 'config_region_id', width: 80 }, 			
			{ label: '区域名称', name: 'configReginName', index: 'config_regin_name', width: 80 }, 			
			{ label: '配置时间', name: 'configCreateTime', index: 'config_create_time', width: 80 }			
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
		configtable: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(type){
			vm.showList = false;
			
			vm.configtable = {};
			
			if(type==1){
				vm.title = "设置销售员兑换比例";
				vm.configtable.configKey="sale";
				configtable.configName="销售员兑换比例";
			}else if(type==2){
				vm.title = "设置配送员兑换比例";
				vm.configtable.configKey="delivery"
				configtable.configName="配送员兑换比例";
			}else if(type==3){
				vm.title = "设置起送金额";
				vm.configtable.configKey="delivery_amount";
				configtable.configName="起送金额";
			}
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
			var url = vm.configtable.id == null ? "configtable/save" : "configtable/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.configtable),
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
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "configtable/delete",
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
		},
		getInfo: function(id){
			$.get(baseURL + "configtable/info/"+id, function(r){
                vm.configtable = r.configtable;
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