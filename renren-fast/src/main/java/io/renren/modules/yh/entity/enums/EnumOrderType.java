package io.renren.modules.yh.entity.enums;

public enum EnumOrderType {
	
	TOBEPAID(0,"待支付"),
	PAID(1,"已支付"),
	DISPATCHING(2,"正在配送"),
	COMPLETE(3,"已完成");
	
	
	int status;
	String name;
	
	EnumOrderType(int status,String name){
		this.status = status;
		this.name = name;
	}
	
	public static String getName(int status){
		for(EnumOrderType es:values()){
			if(es.status == status){
				return es.name;
			}
		}
		return "";
	}

	public int getStatus() {
		return status;
	}

	public String getName() {
		return name;
	} 

}
