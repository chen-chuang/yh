$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'product/list',
        datatype: "json",
        colModel: [			
			{ label: 'productId', hidden:true,name: 'productId', index: 'product_id', width: 50, key: true },
			{ label: '产品名称', align: 'center',name: 'productName', index: 'product_name', width: 80 }, 			
			{ label: '产品图片',align: 'center', name: 'productPictureUrl', index: 'product_picture_url', formatter: function(value, options, row){
				
				if(value!=""&&value!=null){
					return '<img style="width:80px;height:80px" src='+value+'>';
				}else{
					return "";
				}
			}}, 						
			{ label: '简介', align: 'center',name: 'productDetail', index: 'product_detail', width: 80 }, 			
			{ label: '产品类型',align: 'center', name: 'productTypeName', index: 'product_type_name', width: 80 }, 	
			{ label: '视频地址', align: 'center',name: 'productVideoUrl', index: 'product_video_url', formatter: function(value, options, row){
				
				if(value!=""&&value!=null){
					return '<a style="cursor:pointer" href='+value+' class="fa fa-caret-square-o-right"></a>';
				}else{
					return "暂无视频";
				}
			}}, 			
			{ label: '库存', align: 'center',name: 'productNum', index: 'product_num', width: 80 }, 			
			{ label: '批发价',align: 'center', name: 'productTradePrice', index: 'product_trade_price', width: 80 }, 			
			{ label: '零售价',align: 'center', name: 'productRetailPrice', index: 'product_retail_price', width: 80 }, 			
			{ label: '企业ID',align: 'center', hidden:true,name: 'enterpriseId', index: 'enterprise_id', width: 80 }, 
			{ label: '企业名称',align: 'center', name: 'enterpriseName', index: 'enterprise_name', width: 80 },
			{ label: '是否热销',align: 'center', name: 'isHot', index: 'is_hot', width: 80, formatter: function(value, options, row){
				return value === 0 ? 
						'热销' : 
						'不热销';
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
        	$("th[role='columnheader']").css('text-align','center')        	
        	
        	 $.get(baseURL + "sys/user/currentLoginUser", function(r){
  		    	var p = r.currentLoginUser.userPermission;
  		    	if(p==1||p==2){
  		    		var len=$("#jqGrid").getGridParam("width");
  		        	$("#jqGrid").setGridParam().hideCol("isHot");
  		        	$("#jqGrid").setGridParam().hideCol("productNum");
  		        	$("#jqGrid").setGridParam().hideCol("productTradePrice");
  		        	$("#jqGrid").setGridParam().hideCol("productRetailPrice");
  		        	$("#jqGrid").setGridParam().hideCol("productTypeName");
  		        	$("#jqGrid").setGridWidth(len);
  		    	}
  			});
        	
        }
    });
    
    
	  $("#enterpriseName").autocomplete({  
          minLength: 0,  
          source: function( request, response ) {  
              $.ajax({  
                   url : baseURL + 'enterpriseinfo/getByName',  
                   type : "post",  
                   dataType : "json",  
                   data : {"enterpriseName":$("#enterpriseName").val()},                           
                   success: function( data ) {  
                        console.log(data.enterpriseinfo);  
                        response( $.map( data.enterpriseinfo, function( item ) {  
                              return {  
                                label: item.enterprise_name,  
                                value: item.enterprise_id  
                              }  
                        }));  
                  }  
             });  
          },  
          focus: function( event, ui ) {  
              $("#enterpriseName").val( ui.item.label );  
              $("#enterpriseId").val( ui.item.value );  
                return false;  
              },  
          select: function( event, ui ) {  
              $("#enterpriseName").val( ui.item.label );  
              $("#enterpriseId").val( ui.item.value );  
              return false;  
          }  
       });  
	  
	  $('#videoFile').change(function() {  
	        var file = this.files[0]; //假设file标签没打开multiple属性，那么只取第一个文件就行了  
	           
	        name = file.name;  
	        size = file.size;  
	        type = file.type;  
	        url = window.URL.createObjectURL(file); //获取本地文件的url，如果是图片文件，可用于预览图片  
	        
	        if(type)
	        
	        if((size/1024)>51200){
	        	alert("上传视频不能大于50M");
	        	$('#videoFile').val("");
	        	return false;
	        }
	       
	          
	    });  
	  
	  $('#picFile').change(function() {  
	        var file = this.files[0]; //假设file标签没打开multiple属性，那么只取第一个文件就行了  
	        name = file.name;  
	        size = file.size;  
	        type = file.type;  
	        
	        url = window.URL.createObjectURL(file); //获取本地文件的url，如果是图片文件，可用于预览图片  
	          
	        
	          
	    });  
});


function loadingBegin(){
	$("#load_mask").show();
	var h = $(document).height();
	$(".overlay").css({"height": h });
	$(".overlay").css({'display':'block','opacity':'0.8'});
	$(".showbox").stop(true).animate({'margin-top':'300px','opacity':'1'},200);
}
function loadingEnd(){
	$(".showbox").stop(true).animate({'margin-top':'250px','opacity':'0'},400);
	$(".overlay").css({'display':'none','opacity':'0'});
	$("#load_mask").hide();
}

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
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.product = {isHot:1};
			
			$("#picFile").val("");
			$("#videoFile").val("");
			
			this.getEnterprise();
			
			this.getProductType();	
			
			this.getUserPermission();
			
		},
		getUserPermission : function(){
		    $.get(baseURL + "sys/user/currentLoginUser", function(r){
		    	vm.currentPermission = r.currentLoginUser.userPermission;
			});
		},	
		getEnterprise:function(){
			
			//selectDataBindByHql("enterpriseId",baseURL + "product/getEnterprise");
			
			$.get(baseURL + "product/getEnterprise", function(r){
                vm.enterPrises = r.citys;
            });
			
		},
		getProductType:function(){
			$.get(baseURL + "producttype/getProductType", function(r){
                vm.types = r.types;
            });
		},
		update: function (event) {
			var productId = getSelectedRow();
			if(productId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            this.getEnterprise();
            
            this.getProductType();
            
            this.getUserPermission();
            
            vm.getInfo(productId); 
            
            
		},
		saveOrUpdate: function (event) {
			
			var url = vm.product.productId == null ? "product/save" : "product/update";
			
			/*$("#enterpriseName").val($("#enterpriseId").find("option:selected").text());*/
			$("#productTypeName").val($("#productType").find("option:selected").text());
			
			if(vm.currentPermission==1){
				if($("#enterpriseId").val()==null||$("#enterpriseId").val()==""){
					alert("您输入的企业暂未录入到本系统中，请添加企业后在录入产品！");
					return;
				}
			}			
			
			
			if(vm.currentPermission==3){
				var length = $("#productType").size();
				if(length<1){
					alert("产品隶属于分类下，请先录入分类哦~");
					return;
				}
				
				var productName= $("#productName").val();
				if(productName==null||productName==""){
					alert("产品名不允许为空");
					return;
				}
				
				var productType = $("#productType").val();
				if(productType==null){
					alert("分类不允许为空");
					return;
				}
				
				var productNum = $("#productNum").val();
				if(productNum==null||productNum==""){
					alert("库存不允许为空");
					return;
				}
				
				var productTradePrice = $("#productTradePrice").val();
				if(productTradePrice==null||productTradePrice==""){
					alert("批发价不允许为空");
					return;
				}
				
				var  productRetailPrice= $("#productRetailPrice").val();
				if(productRetailPrice==null||productRetailPrice==""){
					alert("零售价不允许为空");
					return;
				}
			}	
			
			var formData = new FormData($("form")[0]);				
			loadingBegin();
			 $.ajax({  
		            url : baseURL + url,  
		            type : 'POST',  
		            data : formData,  
		            cache : false,  
		            contentType : false,// 告诉jQuery不要去设置Content-Type请求头  
		            processData : false,// 告诉jQuery不要去处理发送的数据  
		            xhr: function(){ //获取ajaxSettings中的xhr对象，为它的upload属性绑定progress事件的处理函数
		            	myXhr = $.ajaxSettings.xhr();
		            	if(myXhr.upload){ //检查upload属性是否存在
			            	//绑定progress事件的回调函数
			            	myXhr.upload.addEventListener('progress',this.progressHandlingFunction, false);
		            	}
		            	return myXhr; //xhr对象返回给jQuery使用
	            	},
		            success : function(r) {  
		            	if(r.code === 0){
							alert('操作成功', function(index){
								vm.reload();
							});
						}else{
							alert(r.msg);
						}
		            	loadingEnd();
		            },
		            error:function(r){
		            	loadingEnd();
		            }
			 
		        }); 
		},
		del: function (event) {
			var productIds = getSelectedRows();
			if(productIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "product/delete",
                    contentType: "application/json",
				    data: JSON.stringify(productIds),
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
		getInfo: function(productId){
			$.get(baseURL + "product/info/"+productId, function(r){
                vm.product = r.product;
                console.log(vm.product.isHot);
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

function progressHandlingFunction(e) {
	if (e.lengthComputable) {
	$('progress').attr({value : e.loaded, max : e.total}); //更新数据到进度条
	var percent = e.loaded/e.total*100;
		$('#progress').html(e.loaded + "/" + e.total+" bytes. " + percent.toFixed(2) + "%");
	}
}