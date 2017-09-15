( function ( $ ){
 $.fn.addBack = $.fn.addBack || $.fn.andSelf;

 $.fn.extend({

   actual : function ( method, options ){
     // check if the jQuery method exist
     if( !this[ method ]){
       throw '$.actual => The jQuery method "' + method + '" you called does not exist';
     }

     var defaults = {
       absolute      : false,
       clone         : false,
       includeMargin : false
     };

     var configs = $.extend( defaults, options );

     var $target = this.eq( 0 );
     var fix, restore;

     if( configs.clone === true ){
       fix = function (){
         var style = 'position: absolute !important; top: -1000 !important; ';

         // this is useful with css3pie
         $target = $target.
           clone().
           attr( 'style', style ).
           appendTo( 'body' );
       };

       restore = function (){
         // remove DOM element after getting the width
         $target.remove();
       };
     }else{
       var tmp   = [];
       var style = '';
       var $hidden;

       fix = function (){
         // get all hidden parents
         $hidden = $target.parents().addBack().filter( ':hidden' );
         style   += 'visibility: hidden !important; display: block !important; ';

         if( configs.absolute === true ) style += 'position: absolute !important; ';

         // save the origin style props
         // set the hidden el css to be got the actual value later
         $hidden.each( function (){
           var $this = $( this );

           // Save original style. If no style was set, attr() returns undefined
           tmp.push( $this.attr( 'style' ));
           $this.attr( 'style', style );
         });
       };

       restore = function (){
         // restore origin style values
         $hidden.each( function ( i ){
           var $this = $( this );
           var _tmp  = tmp[ i ];

           if( _tmp === undefined ){
             $this.removeAttr( 'style' );
           }else{
             $this.attr( 'style', _tmp );
           }
         });
       };
     }

     fix();
     // get the actual value with user specific methed
     // it can be 'width', 'height', 'outerWidth', 'innerWidth'... etc
     // configs.includeMargin only works for 'outerWidth' and 'outerHeight'
     var actual = /(outer)/.test( method ) ?
       $target[ method ]( configs.includeMargin ) :
       $target[ method ]();

     restore();
     // IMPORTANT, this plugin only return the value of the first element
     return actual;
   }
 });
})( jQuery );

$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'pcorder/list',
        datatype: "json",
        colModel: [			
			{ label: '订单编号', name: 'orderId', index: 'order_id', width: 230, key: true },
			{ label: '经销商',hidden:true, name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: '订单生成时间', name: 'orderCreateTime', index: 'order_create_time', width: 140 }, 				
			{ label: '订单总价格', name: 'orderAllPrice', index: 'order_all_price', width: 80 }, 
			{ label: '下单地址ID', hidden:true,name: 'townId', index: 'town_id', width: 80 }, 			
			{ label: '下单地址', name: 'orderAddress', index: 'order_address', width: 150 }, 			
			{ label: '要求配送时间', name: 'orderSendTime', index: 'order_send_time', width: 140 }, 			
			{ label: '详细地址', name: 'orderDetailAddress', index: 'order_detail_address', width: 250 }, 			
			{ label: '买者电话', name: 'receiverPhone', index: 'receiver_phone', width: 120 }, 			
			{ label: '买者姓名', name: 'receiverName', index: 'receiver_name', width: 80 }, 			
			{ label: '备注', name: 'mark', index: 'mark', width: 180 }, 			
			{ label: '订单支付方式（1：支付宝，2：微信）', hidden:true,name: 'orderPayType', index: 'order_pay_type', width: 80, formatter: function(value, options, row){
				if(value===1){
					return '<span>支付宝</span>';
				}else if(value===2){
					return '<span>微信</span>';
				}else if(value===3){
					return '<span>现金</span>';
				}
		    }},
		    { label: '订单状态', name: 'orderType', index: 'order_type', width: 80, formatter: function(value, options, row){
				if(value===0){
					return '<span>待支付</span>';
				}else if(value===1){
					return '<span>已支付</span>';
				}else if(value===2){
					return '<span>正在配送</span>';
				}else if(value===3){
					return '<span>已完成</span>';
				}
		    }},	
		    { label: '配送员', name: 'deliveryUserName', index: 'delivery_user_name', width: 120 }, 	
			{ label: '操作', width: 200,formatter: function(value, options, row){
				if(row.orderType===1){
					var content="";
					
					if(hasPermission('pcorder:choiceDelivery')){
						content +='<a class="btn btn-default btn-xs" onclick=choiceDelivery("'+row.orderId+'")>配送员</a>';
					}
					
					if(hasPermission('pcorder:dispatch')){
						content +='<a class="btn btn-default btn-xs" onclick=dispatch("'+row.orderId+'")>配送</a>';
					}
					
					if(hasPermission('orderdetail:list')){
						content +='<a class="btn btn-info btn-xs" onclick=showdetail("'+row.orderId+'")>查看明细</a>';
					}
					
					if(row["deliveryUserName"]!=null&&row["deliveryUserName"]!=""){
						if(hasPermission('pcorder:print')){
							content +='<a href="javascript:;" class="btn btn-default btn-xs" onclick=print("'+row.orderId+'")>打印</a>';
						}
					}
					
					return content;
				}else if(row.orderType===2){
					
					var content="";
					if(hasPermission('pcorder:complete')){
						content +='<a class="btn btn-success btn-xs" onclick=complete("'+row.orderId+'")>完成</a>';
					}
					
					if(hasPermission('orderdetail:list')){
						content +='<a class="btn btn-info btn-xs" onclick=showdetail("'+row.orderId+'")>查看明细</a>';
					}
					return content;					
					
				}else{
					if(hasPermission('orderdetail:list')){
					  return '<a class="btn btn-info btn-xs" onclick=showdetail("'+row.orderId+'")>查看明细</a>';
					}
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
        shrinkToFit:false,
        autoScroll: false,
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
        	//$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
    
  /* $("#view_right").delegate("ul li","click",function(){
	   alert(11111);
	   console.log(11111111111111);
	   $("#userId").val($(this).attr("userid"));
	   $(this).addClass("choiceLi").siblings().removeClass("choiceLi");
   });*/
    
    $("body").on("click","#view_right ul li",function(){    	
 	   $("#userId").val($(this).attr("userid"));
 	   $(this).addClass("choiceLi").siblings().removeClass("choiceLi");
    });
});

/*function setUserId(userId,obj) {
	$("#userId").val(userId);
	li.onclick(function(){
        $(obj).addClass("choiceLi").siblings().removeClass("choiceLi");
    });
	
}*/

/*function print(orderId){
	var headstr = "<html><head><title></title></head><body>";  
	var footstr = "</body>";  
	var printData = document.getElementById("print_container").innerHTML; 
	var oldstr = document.body.innerHTML;  
	document.body.innerHTML = headstr+printData+footstr;  
	//window.parent.print();  
	window.print();  
	document.body.innerHTML = oldstr;  
}*/


//通知配送员
function choiceDelivery(orderId){
	
	$("#userId").val("");
	
	var content ='<div class="user_select_view">'
		
		/*	 +'  <div class="view_left" id="view_left">'
			 +'   <ul>'
			 +'    <li >1</li>'
			 +'    <li >1</li>'
			 +'   <li >1</li>'
			 +'  </ul>'
			 +' </div>'*/
			 +' <div class="view_right" id="view_right">'
			 +'   <ul style="margin:0;padding:0">';
			 
	
	//展示配送员
	$.ajax({
		type : "POST",
		url : baseURL + "pcorder/getDeliveryPerson",
		success : function(r) {
			if (r.code == 0) {
				if(r.deliveryPerson){
					$.each(r.deliveryPerson,function(k,v){
						/*content+='<li onclick = setUserId("'+v.user_id+'",this) userid = '+v["user_id"]+'>'+v.username+'</li>' ;*/
						content+='<li userid = '+v["user_id"]+'>'+v.username+'</li>' ;
					})
					
				}
			} 
			content +='  </ul>'
						 +' </div>'
						 +'</div>';
			

			 layer.open({
			        type: 1
			        ,title: '选择配送员' 
			        ,area: ['450px', '300px']
			        ,shade: 0
			        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
			        ,btn: ['确定', '关闭']
			        ,moveType: 1 //拖拽模式，0或者1
			        ,content: content
			    	 ,yes: function(){    
			    		 
			    		 if($("#userId").val()==""|| $("#userId").val()==null){
			    			 alert("请选择配送员之后在做操作！");
			    			 return;
			    		 }
			    		
		    			$.ajax({
		    				type : "POST",
		    				url : baseURL + "pcorder/choiceDelivery",
		    				data : {
		    					orderId : orderId,
		    					userId : $("#userId").val()
		    				},
		    				success : function(r) {
		    					if (r.code == 0) {
		    						alert('指派成功！', function() {
		    							vm.reload();
		    							layer.closeAll();
		    						});
		    					} else {
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
	});
}

//配送
function dispatch(orderId){
	
	$.ajax({
		type : "POST",
		url : baseURL + "pcorder/dispatch",
		data : {
			orderId : orderId,
			userId : $("#userId").val()
		},
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
function complete(orderId){
	$.ajax({
		type : "POST",
		url : baseURL + "pcorder/complete",
		data : {
			orderId : orderId,
			type:"accept"
		},
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


function showdetail(orderId){
	$("#orderId").val(orderId);
	 layer.open({
	        type: 2
	        ,title: '订单明细' 
	        ,area: ['800px', '400px']
	        ,shade: 0
	        ,maxmin: true
	        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
	        ,btn: ['确定']
	        ,moveType: 1 //拖拽模式，0或者1
	        ,content: '/modules/yh/orderdetail.html'
	    	 ,yes: function(){
	    		 layer.closeAll();
	        }
	      });
}

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		order: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "创建订单";
			vm.order = {};
			this.getArea();
			$("#regionId").val("");
			$("#regionName").val("");
		},
		update: function (event) {
			var orderId = getSelectedRow();
			if(orderId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(orderId)
		},
		productList:function(){
			 layer.open({
			        type: 2
			        ,title: '选择产品' 
			        ,area: ['800px', '400px']
			        ,shade: 0
			        ,maxmin: true
			        ,id: 'LAY_layuipro1' //设定一个id，防止重复弹出
			        ,btn: ['确定']
			        ,moveType: 1 //拖拽模式，0或者1
			        ,content: '/modules/yh/pcproduct.html'
			    	 ,yes: function(){
			    		 
			    		 var grid =  $('#LAY_layuipro iframe').contents().find('#jqGrid').getGridParam("selarrrow");	
			    		 
			    		 for(var i=0;i<grid.length;i++){
			    			 var rowData = $('#LAY_layuipro iframe').contents().find('#jqGrid').getRowData(grid[i]);
			    			 var content = "";
			    			 content += "<tr>" 
				    	     content += '<td style="display:none">'+rowData.productId+'</td>' 
			    			 content += '<td>'+rowData.productName+'</td>' 
			    			 content += '<td><input type="text" style="width:40px" value="'+rowData.productTradePrice+'"/></td> '
			    		     content += '<td><input type="text" style="width:40px"/></td> '
			    		     content += '<td><input type="text" style="width:40px"/></td> '
			    		     content += '<td><a onclick="deltr(this)">删除</a></td>'
			    		     content += '</tr>' 
			    			 $("#productsTable").append(content);
			    		 }
			    	
			    		 layer.closeAll();
			        }
			      });
		},
		saveOrUpdate: function (event) {
			var url = vm.order.orderId == null ? "pcorder/save" : "pcorder/update";
			var content ="[";
			var trList = $("#productsTable").children("tr")
			if(trList.length<1){
				alert("没选择产品不能下单！");
	        	return;
			}
		    for (var i=0;i<trList.length;i++) {
		    	content+="{";
		        var tdArr = trList.eq(i).find("td");
		        var productId = tdArr.eq(0).text();		       
		        var productPrice = tdArr.eq(2).find('input').val();//
		        var productNum = tdArr.eq(3).find('input').val();//   
		        var productSumPrice = tdArr.eq(4).find('input').val();//   
		        
		        if(productPrice==""){
		        	productPrice=0;		        	
		        }
		        
		        if(productNum==""){
		        	productNum=0;		        	
		        }
		        
		        if(productSumPrice==""){
		        	productSumPrice=0;		        	
		        }
		        
		        content+="productId:"+productId+",";
		        content+="productPrice:"+productPrice+",";
		        content+="productNum:"+productNum+",";
		        content+="productSumPrice:"+productSumPrice+"},";
		        
		    }
			content = content.substring(0, content.length - 1)
			content+="]";
			vm.order.mark ="";
			vm.order.mark +="@"+ content;
			console.log(vm.order.mark);
			
			vm.order.townId =$("#regionId").val();
			vm.order.orderAddress=$("#regionName").val();
			vm.order.orderSendTime=$('#orderSendTime').val();
			
			confirm('订单一经创建不能修改，立减库存，确定吗？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + url,
		            contentType: "application/json",
				    data: JSON.stringify(vm.order),
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
			});
		},
		del: function (event) {
			var orderIds = getSelectedRows();
			if(orderIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "pcorder/delete",
                    contentType: "application/json",
				    data: JSON.stringify(orderIds),
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
		getInfo: function(orderId){
			$.get(baseURL + "pcorder/info/"+orderId, function(r){
                vm.order = r.order;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
	    getArea:function(){
			
			selectDataBindByHql('province',baseURL+"region/getCitys/0");
			
			$('#province').change(function() {
				var selectProvinceId = $("#province").val();
				
				if(selectProvinceId!=-1&&selectProvinceId!=null){
					selectDataBindByHql('city', baseURL+"region/getCitys/"+selectProvinceId);
				}
				
				if(selectProvinceId!=-1){
					$('#regionId').val(selectProvinceId);
					$('#regionName').val($("#province").find("option:selected").text());
				}
				
			});
			
			$('#city').change(function() {
				var selectCityId = $('#city').val();
				
				if(selectCityId!=-1&&selectCityId!=null){
					selectDataBindByHql('county',  baseURL+"region/getCitys/"+selectCityId);
				}
				
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
				
				if(countySelectId!=-1&&countySelectId!=null){
					selectDataBindByHql('town',  baseURL+"region/getCitys/"+countySelectId);
				}
				
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


function deltr(obj){
	  var tr = $(obj).parent().parent();  
	    tr.remove();  
}

function print(orderId){
	
	$.ajax({
		type : "POST",
		url : baseURL + "pcorder/print",
		data : {
			orderId : orderId,
			userId : $("#userId").val()
		},
		success : function(r) {
			if (r.code == 0) {
				
				$("#orderId1").html(r.order.orderId);
				$("#createTime").html(r.order.createTime);
				$("#deliveryFaName").html(r.order.deliveryFaName);
				$("#deliveryName").html(r.order.deliveryName);
				$("#agency").html(r.order.agency);
				$("#productAllPrice").html(r.order.productAllPrice);
				$("#useIntegral").html(r.order.useIntegral);
				$("#productActualPrice").html(r.order.productActualPrice);
				$("#receiverName").html(r.order.receiverName);
				$("#orderDetailAddress").html(r.order.orderAddress+r.order.orderDetailAddress);
				$("#receiverPhone").html(r.order.receiverPhone);
				
				if(r.order.products){
					var content ="";
					
					$.each(r.order.products, function(k,v){
						content += ' <tr><td>'+v.productName+'</td><td>'+v.productNum+'</td><td>'+v.productSumPrice+'</td></tr>';
					})
					$("#tbody").append(content);
				}
				
				var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
				LODOP.PRINT_INIT("打印");  
				LODOP.SET_PRINT_PAGESIZE(3,580,100,"");
				//var table_height= document.getElementById("print_container").offsetHeight; 
				var table_height= $('.print_container').actual('outerHeight');
				LODOP.ADD_PRINT_HTM("0","5px","580", table_height,document.getElementById("print_container").innerHTML); 
				LODOP.PREVIEW();
				//LODOP.PRINT(); 
			} else {
				alert(r.msg);
			}
		}
	});
}