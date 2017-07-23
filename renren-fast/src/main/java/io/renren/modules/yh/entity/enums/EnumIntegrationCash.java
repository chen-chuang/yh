package io.renren.modules.yh.entity.enums;

public enum EnumIntegrationCash {
	
	APPLY(1,"申请中"),
	ACCEPTED(2,"已受理"),
	COMPLETE(3,"已完成");
	
	
	int status;
	String name;
	
	EnumIntegrationCash(int status,String name){
		this.status = status;
		this.name = name;
	}
	
	public static String getName(int status){
		for(EnumIntegrationCash es:values()){
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
