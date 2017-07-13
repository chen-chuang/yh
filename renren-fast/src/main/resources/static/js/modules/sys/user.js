$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/user/list',
        datatype: "json",
        colModel: [			
			{ label: '用户ID', hidden:true,name: 'userId', index: "user_id", width: 45, key: true },
			{ label: '用户名', name: 'username', width: 75 },
			{ label: '邮箱', name: 'email', width: 90 },
			{ label: '手机号', name: 'mobile', width: 100 },
			{ label: '状态', name: 'status', width: 80, formatter: function(value, options, row){
				return value === 0 ? 
					'<span class="label label-danger">禁用</span>' : 
					'<span class="label label-success">正常</span>';
			}},
			{ label: '创建时间', name: 'createTime', index: "create_time", width: 80},
			{ label: '过期日期', name: 'expiryDate', index:"expiry_date",align: 'center', formatter: function(value, options, row){
				if(value==""||value==null||value=="null"){
					return '<a class="btn-app" onclick=setExpiryDate("'+row.userId+'")><i class="fa fa-edit"></i></a>';
				}else{
					return value;
				}
			}},
			{ label: '所属区域', name: 'userArea', index:"user_area", width: 75, formatter: function(value, options, row){
				if(value==""||value==null||value=="null"){
					return '<a class="btn-app" onclick=setRegion("'+row.userId+'")><i class="fa fa-edit"></i></a>';
				}else{
					return value;
				}
			}},
			{ label: '所属经销商', name: 'belongToAgency',  index:"belong_to_agency",width: 75 },
			{ label: '用户权限', name: 'userPermission',  index:"user_permission",width: 75, formatter: function(value, options, row){
				if(value===1){
					return "管理员";
				}else if(value===2){
					return "生产厂家";
				}else if(value===3){
					return "区域经销商";
				}else if(value===4){
					return "配送员";
				}else if(value===5){
					return "发货员";
				}else if(value===6){
					return "销售员";
				}else {
					return '<a class="btn-app" onclick=setPermission("'+row.userId+'")><i class="fa fa-edit"></i></a>';
				}
			}},
			{ label: '积分', name: 'userIntegral',  index:"user_integral", formatter: function(value, options, row){
				if(value==""||value==null||value=="null"){
					return "0";
				}else{
					return value;
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
        }
    });
});

function setRegion(userId){
	var content = 	'<div class="form-group">'
					   	+'<div class="col-sm-2 control-label">省</div>'
					   	+'<div class="col-sm-10" >'
					   	+'<select   class="form-control" id="province">'
					   	+' </select>'
					   	+' </div>'
					   	+'	</div>'
					   	+'<div class="form-group">'
					   	+'	<div class="col-sm-2 control-label">市</div>'
					   	+' 	<div class="col-sm-10" >'
					   	+' 	  <select   class="form-control" id="city">'
					   	+' 	  </select>'
					   	+'  </div>'
					   	+'</div>'
					   	+'<div class="form-group">'
					   	+'	<div class="col-sm-2 control-label">区、县</div>'
					   	+'<div class="col-sm-10" >'
					   	+'  <select   class="form-control" id="county">'
					   	+'	  </select>'
					   	+' </div>'
					   	+'</div>';
	
 layer.open({
        type: 2
        ,title: '设置权限' 
        ,area: ['450px', '300px']
        ,shade: 0
        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
        ,btn: ['确定', '关闭']
        ,moveType: 1 //拖拽模式，0或者1
        ,content: 'http://127.0.0.1:8000/renren-fast/modules/yh/select.html'
    	 ,yes: function(){    		 
    		
    		 var regionId = $(window.frames["layui-layer-iframe1"].document).find("#regionId").val();
    		 var regionName = $(window.frames["layui-layer-iframe1"].document).find("#regionName").val();
    		
    		 $.ajax({
    				type: "POST",
    			    url: baseURL + "sys/user/setRegion",
    			    data: {userId:userId,regionId:regionId,regionName:regionName},
    			    success: function(r){
    					if(r.code == 0){
    						alert('操作成功', function(){
    		                    vm.reload();
    						});
    					}else{
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

function setPermission(userId){
	
	var content = '<div class="form-group">'
			   	+'<div class="col-sm-2 control-label">用户权限</div>'
			   	+'<div class="col-sm-10" >'
			   	 + '<select id="permissionSelect"  class="form-control">'
			   	  +    '<option value="1">管理员</option>'
			   	   +   '<option value="2">生产厂家</option>'
			   	    +  '<option value="3">区域经销商</option>'
			   	    + ' <option value="4">配送员</option>'
			   	    +  '<option value="5">发货员</option>'
			   	    + ' <option value="6">销售员</option>'
			   	  +'</select>'
			    +'</div>'
			+'</div>';
 	
   layer.open({
        type: 1
        ,title: '设置权限' 
        ,area: ['390px', '260px']
        ,shade: 0
        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
        ,btn: ['确定', '关闭']
        ,moveType: 1 //拖拽模式，0或者1
        ,content: content
    	 ,yes: function(){
    		 var permissionId = $('#permissionSelect').val();
    		 $.ajax({
    				type: "POST",
    			    url: baseURL + "sys/user/setPermission",
    			    data: {userId:userId,permissionId:permissionId},
    			    success: function(r){
    					if(r.code == 0){
    						alert('操作成功', function(){
    		                    vm.reload();
    						});
    					}else{
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


function setExpiryDate(userId){
	
   var content='<div class="form-group">'
				    +'<div class="col-sm-2 control-label">过期日期</div>'
				   	+'<div class="col-sm-10">'
				    +'   <input class="layui-input" id="expiryDate1" placeholder="过期日期" v-model="user.expiryDate" onClick="WdatePicker()">'
				    +'</div>'
				+'</div>';
	
   layer.open({
        type: 1
        ,title: '设置权限' 
        ,area: ['390px', '260px']
        ,shade: 0
        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
        ,btn: ['确定', '关闭']
        ,moveType: 1 //拖拽模式，0或者1
        ,content: content
    	 ,yes: function(){
    		 var expiryDate = $('#expiryDate1').val();
    		 console.log(expiryDate);
    		 $.ajax({
    				type: "POST",
    			    url: baseURL + "sys/user/setExpiryDate",
    			    data: {userId:userId,expiryDate:expiryDate},
    			    success: function(r){
    					if(r.code == 0){
    						alert('操作成功', function(){
    		                    vm.reload();
    						});
    					}else{
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




var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			username: null
		},
		showList: true,
		title:null,
		roleList:{},
		user:{
			status:1,
			roleIdList:[]
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.roleList = {};
			vm.user = {status:1,roleIdList:[]};
			
			//获取角色信息
			this.getRoleList();
		},
		update: function () {
			var userId = getSelectedRow();
			if(userId == null){
				return ;
			}
			
			vm.showList = false;
            vm.title = "修改";
			
			vm.getUser(userId);
			//获取角色信息
			this.getRoleList();
		},
		del: function () {
			var userIds = getSelectedRows();
			if(userIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/user/delete",
                    contentType: "application/json",
				    data: JSON.stringify(userIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(){
                                vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		saveOrUpdate: function () {
			var url = vm.user.userId == null ? "sys/user/save" : "sys/user/update";		
		
			vm.user.expiryDate=$('#expiryDate').val();
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.user),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		getUser: function(userId){
			$.get(baseURL + "sys/user/info/"+userId, function(r){
				vm.user = r.user;
				vm.user.password = null;
			});
		},
		getRoleList: function(){
			$.get(baseURL + "sys/role/select", function(r){
				vm.roleList = r.list;
			});
		},
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'username': vm.q.username},
                page:page
            }).trigger("reloadGrid");
		}
	}
});