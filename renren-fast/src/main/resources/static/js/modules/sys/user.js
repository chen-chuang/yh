$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/user/list',
        datatype: "json",
        colModel: [			
			{ label: '用户ID', hidden:true,name: 'userId', index: "user_id", width: 45, key: true },
			{ label: '用户名',align: 'center', name: 'username', width: 100 },
			{ label: '邮箱', align: 'center',name: 'email', width: 150 },
			{ label: '手机号',align: 'center', name: 'mobile', width: 100 },
			{ label: '状态', align: 'center',name: 'status', width: 80, formatter: function(value, options, row){
				return value === 0 ? 
					'<span class="label label-danger">禁用</span>' : 
					'<span class="label label-success">正常</span>';
			}},
			{ label: '创建时间',align: 'center', name: 'createTime', index: "create_time", width: 150},
			{ label: '过期日期',align: 'center', name: 'expiryDate', index:"expiry_date",align: 'center', formatter: function(value, options, row){
				if(value==""||value==null||value=="null"){
					return '<a class="btn-app" onclick=setExpiryDate("'+row.userId+'")><i class="fa fa-edit"></i></a>';
				}else{
					return value;
				}
			}},
			{ label: '所属区域', align: 'center',name: 'userArea', index:"user_area", width: 75, formatter: function(value, options, row){
				if(value==""||value==null||value=="null"){
					return '<a class="btn-app" onclick=setRegion("'+row.userId+'")><i class="fa fa-edit"></i></a>';
				}else{
					return value;
				}
			}},
			{ label: '所属经销商',align: 'center', name: 'belongToAgencyName',  index:"belong_to_agency_name",width: 75 },
			{ label: '用户权限',align: 'center', name: 'userPermission',  index:"user_permission",width: 75, formatter: function(value, options, row){
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
			{ label: '积分', align: 'center',name: 'userIntegral',  index:"user_integral",width: 50, formatter: function(value, options, row){
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
        	$("th[role='columnheader']").css('text-align','center');
        }
    });    

	
});

function setRegion(userId){
	var content = 	    '<div class="form-horizontal">'
		                +'<div class="form-group">'
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
					   	+'</div>'	
		                +'</div>';
	
 layer.open({
        type: 2
        ,title: '设置权限' 
        ,area: ['450px', '300px']
        ,shade: 0
        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
        ,btn: ['确定', '关闭']
        ,moveType: 1 //拖拽模式，0或者1
        ,content: ['http://127.0.0.1:8000/renren-fast/modules/yh/select.html',"no"]
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
    		                    layer.closeAll();
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
	
	var content = '<div style="position: relative;top: 60px;">'
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
    		                    layer.closeAll();
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
	
   var content='<div style="position: relative;top: 60px;">'
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
    		                    layer.closeAll();
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
			roleIdList:[],
			currentPermission:1
		},
		province:null,
		city:null,
		county:null
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.roleList = {};
			vm.user = {status:1,roleIdList:[],currentPermission:1};
			
			//获取角色信息
			this.getRoleList();
			
			this.getUserPermission();
			
			vm.province=null;
			vm.city=null;
			vm.county=null;
			
			this.getArea();
		},
		getUserPermission : function(){
		    $.get(baseURL + "sys/user/currentLoginUser", function(r){
		    	console.log(r.currentLoginUser.userPermission);
		    	vm.user.currentPermission = r.currentLoginUser.userPermission;
			});
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
			
			this.getUserPermission();
			
		
			
		},
		getFullRegion: function(id){
			$.get(baseURL + "region/getFullRegion/"+id, function(r){
				selectDataBindByHql('province',baseURL+"region/getCitys/0");
				
				selectDataBindByHql('city',baseURL+"region/getCitys/"+r.region[0]);
				
				selectDataBindByHql('county',baseURL+"region/getCitys/"+r.region[1]);
				
				vm.province=r.region[0];
				vm.city=r.region[1];
				vm.county=r.region[2];
				
			});
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
			
			vm.user.areaId = $('#regionId').val();
			
			vm.user.userArea = $('#regionName').val();
			
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
				
			    $('#regionId').val(vm.user.areaId);
				
				$('#regionName').val(vm.user.userArea);
				
				vm.getFullRegion(vm.user.areaId);
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
		},
		getArea:function(){
			
			selectDataBindByHql('province',baseURL+"region/getCitys/0");
			
			$('#province').change(function() {
				var selectProvinceId = $("#province").val();
				selectDataBindByHql('city', baseURL+"region/getCitys/"+selectProvinceId);
				if(selectProvinceId!=-1){
					$('#regionId').val(selectProvinceId);
					$('#regionName').val($("#province").find("option:selected").text());
				}
				
			});
			
			$('#city').change(function() {
				var selectCityId = $('#city').val();
				selectDataBindByHql('county',  baseURL+"region/getCitys/"+selectCityId);
				if(selectCityId==-1){
					$('#regionId').val($("#province").val());
					$('#regionName').val($("#province").find("option:selected").text());
				}else{
					$('#regionId').val(selectCityId);
					$('#regionName').val($("#province").find("option:selected").text()
							+$("#city").find("option:selected").text());
				}			
			});
			
			$('#county').change(function(){
				var countySelectId = $('#county').val();
				selectDataBindByHql('town',  baseURL+"region/getCitys/"+countySelectId);
				if(countySelectId==-1){
					$('#regionId').val($("#city").val());
					$('#regionName').val($("#province").find("option:selected").text()
							+$("#city").find("option:selected").text());
				}else{
					$('#regionId').val(countySelectId);
					$('#regionName').val($("#province").find("option:selected").text()
							+$("#city").find("option:selected").text()
							+$("#county").find("option:selected").text());
				}
				
			});
			
			$('#town').change(function(){
				var townSelectId = $('#town').val();
				if(townSelectId==-1){
					$('#regionId').val($("#county").val());
					$('#regionName').val($("#province").find("option:selected").text()
							+$("#city").find("option:selected").text()
							+$("#county").find("option:selected").text());
				}else{
					$('#regionId').val(townSelectId);
					$('#regionName').val($("#province").find("option:selected").text()
							+$("#city").find("option:selected").text()
							+$("#county").find("option:selected").text()
							+$("#town").find("option:selected").text());
				}
				
			});
			
			
			
		}
	}
});