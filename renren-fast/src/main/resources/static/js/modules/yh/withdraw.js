$(function () {
	
	getCurrentLoginUser();
	
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
			{ label: '操作时间',hidden:true, name: 'operateTime', index: 'operate_time', width: 80 }, 			
			{ label: '管理员id', hidden:true,name: 'userId', index: 'user_id', width: 80 },
			{ label: '操作', hidden:false, width: 80,formatter: function(value, options, row){
				console.log(row.id);
				return '<a onclick=accept('+row.id+')>受理</a>|<a onclick=complete('+row.id+')>完成</a>';
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

function getCurrentLoginUser(){
    $.get(baseURL + "sys/user/currentLoginUser", function(r){
    	vm.currentPermission = r.currentLoginUser.userPermission;
	});
}

function accept(id){
	$.ajax({
		type : "POST",
		url : baseURL + "withdraw/operate",
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

function complete(id){
	$.ajax({
		type : "POST",
		url : baseURL + "withdraw/operate",
		data : {
			id : id,
			type:"complete"
		},
		success : function(r) {
			if (r.code == 0) {
				alert('已完成！', function() {
					vm.reload();
					layer.closeAll();
				});
			} else {
				alert(r.msg);
			}
		}
	});
}

function openAccount() {
	var content = '<div style="position: relative;top: 60px;">'
			+ '<div class="col-sm-2 control-label">提现金额</div>'
			+ '<div class="col-sm-10" >'
			+ '<input id="accountNum"  class="form-control"></input>' 
			+ '</div>'
			+ '</div>';

	layer.open({
		type : 1,
		title : '设置权限',
		area : [ '390px', '260px' ],
		shade : 0,
		id : 'LAY_layuipro' // 设定一个id，防止重复弹出
		,
		btn : [ '确定', '关闭' ],
		moveType : 1 // 拖拽模式，0或者1
		,
		content : content,
		yes : function() {
			var accountNum = $('#accountNum').val();
			$.ajax({
				type : "POST",
				url : baseURL + "withdraw/apply",
				data : {
					accountNum : accountNum
				},
				success : function(r) {
					if (r.code == 0) {
						alert('申请成功！', function() {
							vm.reload();
							layer.closeAll();
						});
					} else {
						alert(r.msg);
					}
				}
			});
		},
		btn2 : function() {
			layer.closeAll();
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