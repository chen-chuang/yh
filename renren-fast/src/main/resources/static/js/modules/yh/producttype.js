$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'producttype/list',
        datatype: "json",
        colModel: [			
			{ label: 'id',hidden:true, name: 'id', index: 'id', width: 50, key: true },
			{ label: '分类名称', name: 'type', index: 'type', width: 80 }, 			
			{ label: '图标', name: 'imageUrl', index: 'image_url', width: 80 ,formatter: function(value, options, row){
				
				if(value!=""&&value!=null){
					return '<img src='+value+'>';
				}else{
					return value;
				}
			}}, 								
			{ label: '是否展示在首页', name: 'showInHomepage', index: 'show_in_homepage', width: 80, formatter: function(value, options, row){
				if(value===1){
					return "是";
				}else if(value===0){
					return "否";
				}
			}},			
			{ label: '',hidden:true, name: 'enterId', index: 'enter_id', width: 80 }			
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
		producttype: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.producttype = {};
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
			var url = vm.producttype.id == null ? "producttype/save" : "producttype/update";
			
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
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "producttype/delete",
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
			$.get(baseURL + "producttype/info/"+id, function(r){
                vm.producttype = r.producttype;
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