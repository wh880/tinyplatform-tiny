package org.tinygroup.tinydb.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.commons.tools.CollectionUtil;

/**
 * 存储表配置信息的容器
 * @author renhui
 *
 */
public class TableConfigurationContainer {
    /**
     * 存储所有表配置信息的列表
     */
	private List<TableConfiguration> tableConfigurations=new ArrayList<TableConfiguration>();
	/**
	 * 保存模式下所有表信息
	 */
	private Map<String, List<TableConfiguration>> schemaTables=new HashMap<String, List<TableConfiguration>>();
	/**
	 * 保存所有模式配置
	 */
	private List<SchemaConfig> schemaConfigs=new ArrayList<SchemaConfig>();
	/**
	 * 保存所有模式的映射关系
	 */
	private Map<String, SchemaConfig> schemaMap=new HashMap<String, SchemaConfig>();
	
	private final static String INCREASE_KEY = "increase"; // 主键类型--自增长
	
	public void addTableConfiguration(TableConfiguration configuration){
		if(!tableConfigurations.contains(configuration)){
			tableConfigurations.add(configuration);
		}
		List<TableConfiguration> tables=schemaTables.get(configuration.getSchema());
		if(tables==null){
			tables=new ArrayList<TableConfiguration>();
			schemaTables.put(configuration.getSchema(), tables);
		}
		tables.add(configuration);
	}
	
	public void addSchemaConfigs(SchemaConfig config){
		if(!schemaConfigs.contains(config)){
			schemaConfigs.add(config);
			schemaMap.put(config.getSchema(), config);
		}
	}
	
	public SchemaConfig getSchemaConfig(String schema){
		return schemaMap.get(schema);
	}
	
	public TableConfiguration getTableConfiguration(String schema,String tableName){
	     List<TableConfiguration> tableConfigs=schemaTables.get(schema);
	     if(!CollectionUtil.isEmpty(tableConfigs)){
	    	 for (TableConfiguration tableConfig : tableConfigs) {
				if(tableConfig.getName().equalsIgnoreCase(tableName)){
					return tableConfig;
				}
			 }
	     }
		return null;
	}
	
	public List<SchemaConfig> getsSchemaConfigs(){
		return schemaConfigs;
	}
	
	public boolean isIncrease(String schema){
		SchemaConfig schemaConfig=getSchemaConfig(schema);
		if(schemaConfig!=null&&schemaConfig.getKeyType().equals(INCREASE_KEY)){
			return true;
		}
		return false;
	}
	
}
