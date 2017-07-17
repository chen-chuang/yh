$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'product/list',
        datatype: "json",
        colModel: [			
			{ label: 'productId', hidden:true,name: 'productId', index: 'product_id', width: 50, key: true },
			{ label: '产品名称', name: 'productName', index: 'product_name', width: 80 }, 			
			{ label: '图片地址', name: 'productPictureUrl', index: 'product_picture_url', width: 80 }, 			
			{ label: '简介', name: 'productDetail', index: 'product_detail', width: 80 }, 			
			{ label: '产品类型', name: 'productTypeName', index: 'product_type_name', width: 80 }, 	
			{ label: '视频地址', name: 'productVideoUrl', index: 'product_video_url', width: 80 }, 			
			{ label: '库存', name: 'productNum', index: 'product_num', width: 80 }, 			
			{ label: '批发价', name: 'productTradePrice', index: 'product_trade_price', width: 80 }, 			
			{ label: '零售价', name: 'productRetailPrice', index: 'product_retail_price', width: 80 }, 			
			{ label: '企业ID', hidden:true,name: 'enterpriseId', index: 'enterprise_id', width: 80 }, 
			{ label: '企业名称', name: 'enterpriseName', index: 'enterprise_name', width: 80 },
			{ label: '是否热销（1：热销，2：不热销）', name: 'isHot', index: 'is_hot', width: 80 }		
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
		product: {},
        enterPrises:{},
        types:{}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.product = {};
			
			this.getEnterprise();
			
			this.getProductType();
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
            
            vm.getInfo(productId);
            
           
            
            
		},
		saveOrUpdate: function (event) {
			var url = vm.product.productId == null ? "product/save" : "product/update";
			
			$("#enterpriseName").val($("#enterpriseId").find("option:selected").text());
			$("#productTypeName").val($("#productType").find("option:selected").text());
			
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