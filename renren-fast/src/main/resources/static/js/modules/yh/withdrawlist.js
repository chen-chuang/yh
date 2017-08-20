$(function () {

	
    $("#jqGrid").jqGrid({
        url: baseURL + 'withdraw/list',
        datatype: "json",
        colModel: [			
			{ label: 'id',hidden:true, name: 'id', index: 'id', width: 50, key: true },
			{ label: '申请用户编码', name: 'applyUserId', index: 'apply_user_id', width: 80 }, 			
			{ label: '提现金额', name: 'withdrawalamount', index: 'withdrawalAmount', width: 80 }, 			
			{ label: '提现申请时间', name: 'applyTime', index: 'apply_time', width: 80 }, 			
			{ label: '提现状态', name: 'withdrawStatus', index: 'withdraw_status', width: 80, formatter: function(value, options, row){
				if(value===1){
					return "申请中";
				}else if(value===2){
					return "已受理";
				}else if(value===3){
					return "已完成";
				}
			}}, 			
			{ label: '操作时间',name: 'operateTime', index: 'operate_time', width: 80 }, 			
			{ label: '管理员id', hidden:true,name: 'userId', index: 'user_id', width: 80 },
			{ label: '操作', width: 80,formatter: function(value, options, row){
				console.log(row.id);
				if(row.withdrawStatus===1){
					if(hasPermission('withdraw:operate')){
						return '<a class="btn btn-danger btn-xs" onclick=operate('+row.id+','+'"accept")>受理</a>';
					}
					
				}else if(row.withdrawStatus===2){
					if(hasPermission('withdraw:operate')){
						return '<a class="btn btn-success btn-xs" onclick=operate('+row.id+','+'"complete")>完成</a>';
					}
				}else{
					return "";
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
        	
        /*	$("#jqGrid").setGridParam().showCol("operateTime").trigger("reloadGrid");

        	$("#jqGrid").setGridParam().hideCol("userId").trigger("reloadGrid");*/
        	
        	if(vm.currentPermission==1){
        		
        	}
        }
    });
});


function operate(id,type){
	$.ajax({
		type : "POST",
		url : baseURL + "withdraw/operate",
		data:{id:id,type:type},
		success : function(r) {
			if (r.code == 0) {
				alert('操作成功！', function() {
					vm.reload();
					layer.closeAll();
				});
			} else {
				alert(r.msg);
			}
		}
	});
}


var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		withdraw: {},
		currentPermission:3
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.withdraw = {};
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
			var url = vm.withdraw.id == null ? "withdraw/save" : "withdraw/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.withdraw),
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
				    url: baseURL + "withdraw/delete",
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
			$.get(baseURL + "withdraw/info/"+id, function(r){
                vm.withdraw = r.withdraw;
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