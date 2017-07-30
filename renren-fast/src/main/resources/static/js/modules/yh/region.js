/*$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'region/list',
        datatype: "json",
        colModel: [			
			{ label: '行政区划编码', name: 'id', index: 'id', width: 50, key: true },
			{ label: '行政区划名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '父编码', name: 'pid', index: 'pid', width: 80 }			
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
});*/


function getRegionId () {
    var selected = $('#regionTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        return null;
    } else {
        return selected[0].id;
    }
}


var Region = {
	    id: "regionTable",
	    table: null,
	    layerIndex: -1
	};

	/**
	 * 初始化表格的列
	 */
Region.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '区域编码', field: 'id', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '区域名称', field: 'name', align: 'center', valign: 'middle', sortable: true, width: '180px'}]
    return columns;
};

$(function () {
    var colunms = Region.initColumn();
    var table = new TreeTable(Region.id, baseURL + "region/getAll", colunms);
    table.setExpandColumn(2);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("pid");
    table.setExpandAll(false);
    table.init();
    Region.table = table;
});



var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		region: {},
		fatherShow:true
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.region = {};			
			
			var id = getRegionId();			
			
			if(id==null){
				// $('input:radio:first').attr('checked', 'checked');
				 vm.fatherShow=false;
				 vm.region.pid=0;
			}else{
				//$('input:radio:last').attr('checked', 'checked');
				$.ajax({
					type: "POST",
				    url: baseURL + "region/info/"+id,
				    success: function(r){
				    	if(r.code === 0){
				    		vm.region.pid = r.region.id;
						   $("#pname").val(r.region.name);
						}
					}
				});
				vm.fatherShow=true;
			}
	        $("#regionId").removeAttr('disabled');
		},
		update: function (event) {
			var id = getRegionId();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";            
            vm.getInfo(id);  
            
            vm.fatherShow = false;
			 $("#regionId").attr('disabled','true');
		},
		saveOrUpdate: function (event) {
			
			var url=null;
			
			if(vm.title=="新增"){
				url ="region/save";			    
			}
			
			if(vm.title=="修改"){
				url="region/update";
			}
			//var url = vm.region.id == null ? "region/save" : "region/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.region),
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
			var id = getRegionId();
			if(id == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "region/delete",
                    contentType: "application/json",
				    data: JSON.stringify(id),
				    success: function(r){
						if(r.code == 0){
							/*alert(r.msg, function(index){
								$("#jqGrid").trigger("reloadGrid");
							});*/
							alert(r.msg, function(){
								vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "region/info/"+id, function(r){
                vm.region = r.region;
            });
		},
		reload: function (event) {
			vm.showList = true;
			/*var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");*/
            Region.table.refresh();
		}
	}
});