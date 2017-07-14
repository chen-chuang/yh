$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'enterpriseinfo/list',
        datatype: "json",
        colModel: [			
			{ label: 'enterpriseId', name: 'enterpriseId', index: 'enterprise_id', width: 50, key: true },
			{ label: '企业名称', name: 'enterpriseName', index: 'enterprise_name', width: 80 }, 			
			{ label: '企业图片', name: 'enterpriseImageUrl', index: 'enterprise_image_url', width: 80 }, 			
			{ label: '企业地址', name: 'enterpriseAddress', index: 'enterprise_address', width: 80 }, 			
			{ label: '手机', name: 'enterprisePhone', index: 'enterprise_phone', width: 80 }, 			
			{ label: '电话', name: 'enterpriseTel', index: 'enterprise_tel', width: 80 }, 			
			{ label: '联系人', name: 'enterpriseContact', index: 'enterprise_contact', width: 80 }, 			
			{ label: '简介', name: 'enterpriseIntroduction', index: 'enterprise_introduction', width: 80 }, 			
			{ label: '经度', name: 'enterpriseLongitude', index: 'enterprise_longitude', width: 80 }, 			
			{ label: '纬度', name: 'enterpriseLatitude', index: 'enterprise_latitude', width: 80 }, 			
			{ label: '行政区域', name: 'enterpriseAreaId', index: 'enterprise_area_id', width: 80 }, 			
			{ label: '类型（1：生产厂家，2：经销商）', name: 'enterpriseType', index: 'enterprise_type', width: 80 , formatter: function(value, options, row){
				if(value===1){
					return "生产厂家";
				}else if(value===2){
					return "区域经销商";
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

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		enterpriseinfo: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.enterpriseinfo = {};
		},
		update: function (event) {
			var enterpriseId = getSelectedRow();
			if(enterpriseId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(enterpriseId)
		},
		/*saveOrUpdate: function (event) {
			var url = vm.enterpriseinfo.enterpriseId == null ? "enterpriseinfo/save" : "enterpriseinfo/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.enterpriseinfo),
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
		},*/
		saveOrUpdate:function(event){
			var url = vm.enterpriseinfo.enterpriseId == null ? "enterpriseinfo/save" : "enterpriseinfo/update";
			var formData = new FormData($("form")[0]);
			vm.enterpriseinfo.
			
			 $.ajax({  
		            url : baseURL + url,  
		            type : 'POST',  
		            data : formData,  
		            async : false,  
		            cache : false,  
		            contentType : false,// 告诉jQuery不要去设置Content-Type请求头  
		            processData : false,// 告诉jQuery不要去处理发送的数据  
		            success : function(data) {  
		                alert(data);  
		                //...  
		            },  
		            error : function(data) {  
		                alert(data);  
		                //...  
		            }  
		        });  
			
			
			/*	if(picFile==null||picFile==""){
				$.ajax({
					type: "POST",
				    url: baseURL + url,
	                contentType: "application/json",
				    data: JSON.stringify(vm.enterpriseinfo),
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
			}else{
				 if(picFile.indexOf("jpg")!=-1 || picFile.indexOf("jpeg")!=-1 || picFile.indexOf("png")!=-1 || picFile.indexOf("gif")!=-1){
				    	
				    	$.ajaxFileUpload({  
					        url : baseURL + url,//用于文件上传的服务器端请求地址  
					        secureuri : false,          //一般设置为false  
					        fileElementId : 'picFile',     //文件上传空间的id属性  <input type="file" id="file" name="file" />  
					        dataType : 'json',//返回值类型 一般设置为json
					        data : JSON.stringify(vm.enterpriseinfo), 
					        success : function(data, r) { 
					        	console.log(r);
					        	  if(r.code == 0){
						                vm.reload();
						            }else{
						                alert(r.msg);
						            }				        	
					        }  
					    });
				    }else{
				       	alert("请上传图片格式的文件！");
				        return false;
				    }	
				
				  $.AjaxUpload({
				        action: baseURL + url+'?token=' + token,
				        id:"picFile",
				        data:JSON.stringify(vm.enterpriseinfo),
				        autoSubmit:true,
				        responseType:"json",
				        onSubmit:function(file, extension){
				            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))){
				                alert('只支持jpg、png、gif格式的图片！');
				                return false;
				            }
				        },
				        onComplete : function(file, r){
				            if(r.code == 0){
				                alert(r.url);
				                vm.reload();
				            }else{
				                alert(r.msg);
				            }
				        }
				    });
			}
		  */
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