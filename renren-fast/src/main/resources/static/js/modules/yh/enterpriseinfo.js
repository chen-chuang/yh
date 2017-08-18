$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'enterpriseinfo/list',
        datatype: "json",
        colModel: [			
			{ label: '企业ID', hidden : true,name: 'enterpriseId', index: 'enterprise_id', width: 50, key: true },
			{ label: '企业名称', align: 'center',name: 'enterpriseName', index: 'enterprise_name', width: 80 }, 			
			{ label: '企业图片', align: 'center',name: 'enterpriseImageUrl', index: 'enterprise_image_url', width: 80, formatter: function(value, options, row){
				
				if(value!=""&&value!=null){
					return '<img src='+value+'>';
				}else{
					return "";
				}
			}}, 						 			
			{ label: '企业地址', align: 'center',name: 'enterpriseAddress', index: 'enterprise_address', width: 80 }, 			
			{ label: '手机', align: 'center',name: 'enterprisePhone', index: 'enterprise_phone', width: 80 }, 			
			{ label: '电话', align: 'center',name: 'enterpriseTel', index: 'enterprise_tel', width: 80 }, 			
			{ label: '联系人', align: 'center',name: 'enterpriseContact', index: 'enterprise_contact', width: 80 }, 			
			{ label: '简介', align: 'center',name: 'enterpriseIntroduction', index: 'enterprise_introduction', width: 80 }, 			
			{ label: '经度',align: 'center',hidden:true, name: 'enterpriseLongitude', index: 'enterprise_longitude', width: 80 }, 			
			{ label: '纬度',align: 'center',hidden:true, name: 'enterpriseLatitude', index: 'enterprise_latitude', width: 80 }, 			
			{ label: '行政区域', align: 'center',name: 'enterpriseAreaName', index: 'enterprise_area_name', width: 80 }, 			
			{ label: '商家类型', align: 'center',name: 'enterpriseType', index: 'enterprise_type', width: 80 , formatter: function(value, options, row){
				if(value===1){
					return "生产厂家";
				}else if(value===2){
					return "区域经销商";
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
        	$("th[role='columnheader']").css('text-align','center');
        }
    });
    


});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		enterpriseinfo: {},
		province:null,
		city:null,
		county:null,
		currentPermission:1
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.enterpriseinfo = {};	
			
			this.getArea();
			
			this.getUserPermission();
		},
		getUserPermission : function(){
		    $.get(baseURL + "sys/user/currentLoginUser", function(r){
		    	vm.currentPermission = r.currentLoginUser.userPermission;
		    	if(vm.currentPermission==1){
		    		$("#enterpriseType").removeAttr("disable");
		    	}
		    	
                if(vm.currentPermission==2){//生产厂家
                	$("#enterpriseType").attr("disable","disable");
                	$("#enterpriseType").val(1);
		    	}
                
                if(vm.currentPermission==3){//区域经销商
                	$("#enterpriseType").attr("disable","disable");
                	$("#enterpriseType").val(2);
		    	}
			});
		},	
		update: function (event) {
			var enterpriseId = getSelectedRow();
			if(enterpriseId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            this.getArea();
            vm.getInfo(enterpriseId);
            
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
				
				$("#province").val(r.region[0]);
				$("#city").val(r.region[1]);
				$("#county").val(r.region[2]);
				
				
			});
		},
	    getArea:function(){
	    	
	    	vm.province=null;
			vm.city=null;
			vm.county=null;
			
			selectDataBindByHql('province',baseURL+"region/getCitys/0");
			
			$('#province').change(function() {
				var selectProvinceId = $("#province").val();
				
				if(selectProvinceId!=null&&selectProvinceId!=-1){
					selectDataBindByHql('city', baseURL+"region/getCitys/"+selectProvinceId);
				}
				
				if(selectProvinceId!=-1){
					$('#regionId').val(selectProvinceId);
					$('#regionName').val($("#province").find("option:selected").text());
					
					$("#enterpriseAddress").val($('#regionName').val());
				}
				
			});
			
			$('#city').change(function() {
				var selectCityId = $('#city').val();
				
				if(selectCityId!=null&&selectCityId!=-1){
					selectDataBindByHql('county',  baseURL+"region/getCitys/"+selectCityId);
				}
				
				if(selectCityId==-1){
					$('#regionId').val($("#province").val());
					$('#regionName').val($("#province").find("option:selected").text());
					
					$("#enterpriseAddress").val($('#regionName').val());
					
				}else{
					$('#regionId').val(selectCityId);
					$('#regionName').val($("#province").find("option:selected").text()
							+$("#city").find("option:selected").text());
					
					$("#enterpriseAddress").val($('#regionName').val());
					
				}			
			});
			
			$('#county').change(function(){
				var countySelectId = $('#county').val();
				if(countySelectId==-1){
					$('#regionId').val($("#city").val());
					$('#regionName').val($("#province").find("option:selected").text()
							+$("#city").find("option:selected").text());
					
					$("#enterpriseAddress").val($('#regionName').val());
					
				}else{
					$('#regionId').val(countySelectId);
					$('#regionName').val($("#province").find("option:selected").text()
							+$("#city").find("option:selected").text()
							+$("#county").find("option:selected").text());
					
					$("#enterpriseAddress").val($('#regionName').val());
				}
				
			});
			
			
			
		},
		saveOrUpdate:function(event){		
			
			var county = $('#county').val();
			if(county==null||county=="-1"){
				alert("用户必须要选择到区县哦~");
				return;
			}
			
			var url = vm.enterpriseinfo.enterpriseId == null ? "enterpriseinfo/save" : "enterpriseinfo/update";
			var formData = new FormData($("form")[0]);
			
			 $.ajax({  
		            url : baseURL + url,  
		            type : 'POST',  
		            data : formData,  
		            async : false,  
		            cache : false,  
		            contentType : false,// 告诉jQuery不要去设置Content-Type请求头  
		            processData : false,// 告诉jQuery不要去处理发送的数据  
		            success : function(r) {  
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
			var enterpriseIds = getSelectedRows();
			if(enterpriseIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "enterpriseinfo/delete",
                    contentType: "application/json",
				    data: JSON.stringify(enterpriseIds),
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
		getInfo: function(enterpriseId){
			$.get(baseURL + "enterpriseinfo/info/"+enterpriseId, function(r){
                vm.enterpriseinfo = r.enterpriseinfo;
                
                $("#regionId").val(r.enterpriseinfo.enterpriseAreaId);
                $("#enterpriseAddress").val(r.enterpriseinfo.enterpriseAddress);
                
                if(vm.title == "修改"){
                	vm.getFullRegion(r.enterpriseinfo.enterpriseAreaId);
                }
                
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