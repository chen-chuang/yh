$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'orderintegration/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', hidden:true,name: 'id', index: 'id', width: 50, key: true },
			{ label: '用户名', name: 'userName', index: 'user_name', width: 80 }, 			
			{ label: '订单id', name: 'orderId', index: 'order_id', width: 80 }, 			
			{ label: '订单金额', name: 'orderSumPrice', index: 'order_sum_price', width: 80 }, 			
			{ label: '生成积分', name: 'integration', index: 'integration', width: 80 }, 				
			{ label: '配送完时间', name: 'time', index: 'time', width: 80 }, 				
			{ label: '价格积分类型（1：配送积分?，2：销售积分）',hidden:true, name: 'priceIntegrationType', index: 'price_integration_type', width: 80 },
			{ label: '是否返点', name: 'isRebate', index: 'is_rebate', width: 80, formatter: function(value, options, row){
				  if(value==0){
					  return "未返点";
				  }
				  if(value==1){
					  return "已返点";
				  }
				}
			}
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
    
    $("#deliveryUser").autocomplete({  
        minLength: 0,  
        source: function( request, response ) {  
            $.ajax({  
                 url : baseURL + 'sys/user/getDelivery',  
                 type : "post",  
                 dataType : "json",  
                 data : {"name":$("#deliveryUser").val()},                           
                 success: function( data ) {  
                      response( $.map( data.userinfo, function( item ) {  
                            return {  
                              label: item.username,  
                              value: item.user_id  
                            }  
                      }));  
                }  
           });  
        },  
        focus: function( event, ui ) {  
            $("#deliveryUser").val( ui.item.label );  
            $("#deliveryUserId").val( ui.item.value );  
              return false;  
            },  
        select: function( event, ui ) {  
            $("#deliveryUser").val( ui.item.label );  
            $("#deliveryUserId").val( ui.item.value );  
            return false;  
        }  
     });  
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		orderintegration: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		rebate:function(){
			//var type = $("#rebate").val();
			var deliveryUserId = $("#deliveryUserId").val();
			if(deliveryUserId==null || deliveryUserId==""){
				alert("为确保准确返点，请输入配送员名，按人头逐一返点！");
				return;
			}
			
			var ids = getSelectedRows();
			if(ids == null){
				confirm('由于您未选中需要返点的记录，默认根据查询条件（时间）进行批量返点，确定吗？', function(){					

					$.ajax({
						type: "POST",
					    url: baseURL + "orderintegration/rebateDetail",
					    data: {'startTime':$("#startTime").val(),'endTime': $("#endTime").val(),'deliveryUserId':$("#deliveryUserId").val()  },
					    success: function(r){
							if(r.code == 0){
								var content = "请核对！当前用户:"+r.detail.user_id+",兑换总积分："+r.detail.sum_integration+",兑换金额为："+r.detail.ableCash+"";
								
								confirm(content, function(){
									
									$.ajax({
										type: "POST",
									    url: baseURL + "orderintegration/rebate",
									    data: {'startTime':$("#startTime").val(),
									    	'endTime': $("#endTime").val(),
									    	'deliveryUserId':$("#deliveryUserId").val(),
									    	'sumIntegration':r.detail.sum_integration},									    
									    success: function(r){
											if(r.code == 0){
												alert('批量返点成功', function(index){
													$("#jqGrid").trigger("reloadGrid");
												});
											}else{
												alert(r.msg);
											}
										}
									});
								});
							}else{
								alert(r.msg);
							}
						}
					});					
				
				});
			}else{
				
				$.ajax({
					type: "POST",
				    url: baseURL + "orderintegration/rebateDetailByIds",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							var content = "请核对！当前用户:"+r.detail.user_id+",兑换总积分："+r.detail.sum_integration+",兑换金额为："+r.detail.ableCash+"";
							
							confirm(content, function(){
								
								$.ajax({
									type: "POST",
								    url: baseURL + "orderintegration/rebateByIds",
								    data: {'ids':JSON.stringify(ids),
								    	'deliveryUserId':$("#deliveryUserId").val(),
								    	'sumIntegration':r.detail.sum_integration},									    
								    success: function(r){
										if(r.code == 0){
											alert('批量返点成功', function(index){
												$("#jqGrid").trigger("reloadGrid");
											});
										}else{
											alert(r.msg);
										}
									}
								});
							});
						}else{
							alert(r.msg);
						}
					}
				});	
			}
			
			
			
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.orderintegration = {};
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
			var url = vm.orderintegration.id == null ? "orderintegration/save" : "orderintegration/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.orderintegration),
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
				    url: baseURL + "orderintegration/delete",
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
			$.get(baseURL + "orderintegration/info/"+id, function(r){
                vm.orderintegration = r.orderintegration;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'deliveryUser':$("#deliveryUser").val(),'startTime': $("#startTime").val(),'endTime': $("#endTime").val(),'type':$("#type").val()},
                page:page
            }).trigger("reloadGrid");
		}
	}
});