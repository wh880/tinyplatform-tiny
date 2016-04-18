package org.tinygroup.menucommand.config;

import java.util.regex.Pattern;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 基本属性对象
 * @author yancheng11334
 *
 */
public class BaseObject {

	/**
	 * 唯一Id
	 */
	@XStreamAsAttribute
	private String id;
	
	/**
	 * 命令名称
	 */
	@XStreamAsAttribute
	private String name;
	
	/**
	 * 短标题
	 */
	@XStreamAsAttribute
	private String title;

	/**
	 * 正则匹配条件
	 */
	private String regex;
	
	/**
	 * 正则匹配对象
	 */
	@XStreamOmitField
	private Pattern pattern;
	
	/**
	 * 描述说明
	 */
	private String description;
	
	/**
	 * 渲染的模板路径
	 */
	@XStreamAsAttribute
	private String path;
	
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
	
	/**
	 * 创建正则匹配对象
	 */
	public void compile(){
		if(regex!=null){
		   pattern = Pattern.compile(regex);
		}
	}
	
	/**
	 * 是否匹配命令
	 * @param command
	 * @return
	 */
	public boolean match(String command){
		if(pattern!=null){
		   return pattern.matcher(command).matches();
		}
		return false;
	}
}
