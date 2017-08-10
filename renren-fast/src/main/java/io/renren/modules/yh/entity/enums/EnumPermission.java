package io.renren.modules.yh.entity.enums;


public enum EnumPermission {
	
	ADMIN(1,"管理员"),
	FACTORY(2,"生成厂家"),
	AGENCY(3,"经销商"),
	DELIVERY_P(4,"配送员"),
	DELIVERY_F(5,"发货员"),
	SALE(6,"销售员(零售)"),
	SALEP(7,"销售员(批发)"),
	WHOLESALE(8,"批发厂家");
	
	int type;
	String name;
	
	EnumPermission(int type,String name){
		this.type = type;
		this.name = name;
	}
	
	public static String getName(int type){
		for(EnumPermission ep:values()){
			if(ep.type == type){
				return ep.name;
			}
		}
		return "";
	} 
	
	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}

}

	