package org.tinygroup.cepcorenetty.util;

public class NettyCepCoreUtil {
	//由AR发向SC的服务的服务ID
	public static String AR_TO_SC_SERVICE_KEY = "ar_regto_sc_services";
	public static String AR_TO_SC = "ar_to_sc";
	
	//由SC发向AR的服务的服务ID
	public static String SC_TO_AR = "sc_to_ar";
	public static String SC_TO_AR_SERVICE_KEY = "sc_regto_ar_services";
	
	//存放请求传递中的节点列表
	public static String NODES_KEY = "nodes_key";
	//存放请求传递中的单个节点
	public static String NODE_KEY = "node_key";
	public static String NODE_PATH = "node_path";
	//请求类型KEY，值为后面两者
	public static String TYPE_KEY = "type_key";
	public static String REG_KEY = "type_reg";
	public static String UNREG_KEY = "type_unreg";
}
