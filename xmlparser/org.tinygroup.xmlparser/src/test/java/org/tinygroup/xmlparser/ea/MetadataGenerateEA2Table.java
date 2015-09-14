/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.xmlparser.ea;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.commons.file.IOUtils;
import org.tinygroup.parser.filter.FastNameFilter;
import org.tinygroup.parser.filter.NameFilter;
import org.tinygroup.xmlparser.XmlDocument;
import org.tinygroup.xmlparser.node.XmlNode;
import org.tinygroup.xmlparser.parser.XmlStringParser;

/**
 * 按模块导出
 * 
 * @author luoguo
 * 
 */
public class MetadataGenerateEA2Table {
	static String packageName = "org.tinygroup.alm";
	
	Map<String, StandardType> stdTypeMap = new HashMap<String, StandardType>();
	Map<BizTypePair, BusinessType> bizTypesMap = new HashMap<BizTypePair, BusinessType>();
	Map<StdfieldPair, StandardField> stdfieldMap = new HashMap<StdfieldPair, StandardField>();

	static Map<String, HashSet<String>> customTypeMap = new HashMap<String, HashSet<String>>();

	static XmlDocument doc = null;
	static String outputFolder = "c:/out2/";
	static String basePackagePath = "org.tinygroup.alm";

	public static void main(String[] args) throws Exception {
		File file1 = new File("src/test/java/schema.xml");
		URL resourceURl = MetadataGenerateEA2Table.class.getResource("/schema.xml");
		
		System.out.println(file1.getAbsolutePath());
		XmlStringParser parser = new XmlStringParser();
		doc = parser.parse(IOUtils.readFromInputStream(resourceURl.openStream(), "UTF-8"));

		MetadataGenerateEA2Table mg = new MetadataGenerateEA2Table();
		mg.export("Document");
		mg.export("Product");
		mg.export("Project");
		mg.export("Quality");
		mg.export("Report");
		mg.export("Service");
		mg.export("common");
	}

	public void export(String moduleName) throws Exception {
		exportModule(moduleName);
		exportStdField();
		exportBizType();
		exportStdType();
	}

	private void exportStdField() throws Exception {
		StandardFields stdfields = new StandardFields();
		if (stdfields.getStandardFieldList() == null) {
			stdfields.setStandardFieldList(new ArrayList<StandardField>());
		}
		stdfields.getStandardFieldList().addAll(stdfieldMap.values());
		
		writeFile(stdfields , StandardFields.class ,new File(outputFolder + "aaa.stdfield"));
	}

	private void exportBizType() throws Exception {
		BusinessTypes bizTypes = new BusinessTypes();
		if (bizTypes.getBusinessTypeList() == null) {
			bizTypes.setBusinessTypeList(new ArrayList<BusinessType>());
		}
		bizTypes.getBusinessTypeList().addAll(bizTypesMap.values());
		writeFile(bizTypes , BusinessTypes.class ,new File(outputFolder + "aaa.bizdatatype"));
	}
	
	private void exportStdType() throws Exception {
		StandardTypes stdfields = new StandardTypes();
		if (stdfields.getStandardTypeList() == null) {
			stdfields.setStandardTypeList(new ArrayList<StandardType>());
		}
		stdfields.getStandardTypeList().addAll(stdTypeMap.values());
		writeFile(stdfields , StandardTypes.class ,new File(outputFolder + "aaa.datatype"));
	}
	
	/**
	 * 导出模块
	 * 
	 * @param moduleName
	 * @throws FileSystemException
	 * @throws Exception
	 */
	private void exportModule(String moduleName) throws Exception {
		FastNameFilter<XmlNode> fast = new FastNameFilter<XmlNode>(
				doc.getRoot());
		List<XmlNode> elementList = fast.findNodeList("element");
		String moduleId = getModuleId(doc.getRoot(), moduleName);
		Tables tables = new Tables();
		tables.setId(Guid.createGUID());
		tables.setPackageName(packageName + "." + moduleName.toLowerCase());
		for (XmlNode element : elementList) {
			XmlNode subNode = element.getSubNode("model");
			if (subNode != null) {
				if (subNode.getAttribute("package").equals(moduleId)) {
					Table table = exportTable(moduleName, doc.getRoot(), element,
							outputFolder);
					if (table != null) {
						tables.getTableList().add(table);
					}
				}
			}
		}
		writeFile(tables , Tables.class ,new File(outputFolder + moduleName.toLowerCase() +".table"));
	}

	private void writeFile(Object object , Class cla ,File file) {
		XmlParseSerializableUtil.write(object, cla, file);
		
	}

	private static String getModuleId(XmlNode root, String moduleName) {
		NameFilter<XmlNode> filter = new NameFilter<XmlNode>(root);
		Map<String, String> atts = new HashMap<String, String>();
		atts.put("name", moduleName);
		atts.put("xmi:type", "uml:Package");
		filter.setIncludeAttribute(atts);
		XmlNode node = filter.findNode("element");
		return node.getAttribute("xmi:idref");
	}

	private Table exportTable(String moduleName, XmlNode root, XmlNode element,
			String outputFolder) throws Exception {
		XmlNode properties = element.getSubNode("properties");
		Table table = new Table();
		if (properties != null && properties.getAttribute("stereotype") != null
				&& properties.getAttribute("stereotype").equals("table")) {
			table.setId(Guid.createGUID());
			table.setName(element.getAttribute("name"));
			table.setTitle(properties.getAttribute("alias") == null ? ""
					: properties.getAttribute("alias"));
			table.setPackageName(packageName + "." + moduleName.toLowerCase());
			processAtrribute(root, element, table);
			File folder = new File(outputFolder + moduleName);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			return table;
		}
		return null;
	}

	private void processAtrribute(XmlNode root, XmlNode element,Table table) {
		XmlNode attributes = element.getSubNode("attributes");
		if (attributes != null) {
			List<XmlNode> nodeList = attributes.getSubNodes("attribute");
			for (XmlNode att : nodeList) {
				String type = att.getSubNode("properties").getAttribute("type");
				String length = StringUtils.defaultString(att.getSubNode("properties").getAttribute(
						"length"));

				XmlNode style = att.getSubNode("style");
				String cname = null;
				if (style != null) {
					cname = style.getAttribute("value");
				}
				if (cname == null) {
					cname = "";
				}
				String name = att.getAttribute("name");
				name = getLowerCase(name);
				if (type == null) {
					System.out.println("Error:" + name);
					continue;
				}
				boolean primary = isPrimary(root, element, name);
				boolean unique = isUnique(root, element, name);
				TableField field = new TableField();
				field.setId(name);
				field.setPrimary(primary);
				field.setUnique(unique);
				field.setStandardFieldId(getStdfield(name, cname, type, length).getId());
				table.getFieldList().add(field);
			}
		}
	}

	private StandardType getStdType(String type , String length , BusinessType bizType){
		StandardType stdType = stdTypeMap.get(type);
		if (stdType == null) {
			stdType = new StandardType();
			stdTypeMap.put(type, stdType);
			stdType.setId(Guid.createGUID());
			stdType.setName(type);
			stdType.setTitle(StringUtils.upperCase(type));
		}
		bizType.setTypeId(stdType.getId());
		if (bizType.getPlaceholderValueList() != null) {
			for (PlaceholderValue phv : bizType.getPlaceholderValueList()) {
				boolean status = false;
				if (stdType.getPlaceholderList() != null) {
					for (Placeholder placeholder : stdType.getPlaceholderList()) {
						if (StringUtils.equals(placeholder.getName(), phv.getName())) {
							status = true;
							break;
						}
					}
				}
				if (!status) {
					Placeholder ph = new Placeholder();
					ph.setName(phv.getName());
					if (stdType.getPlaceholderList() == null) {
						stdType.setPlaceholderList(new ArrayList<Placeholder>());
					}
					stdType.getPlaceholderList().add(ph);
				}
			}
		}
		if (stdType.getDialectTypeList() == null) {
			stdType.setDialectTypeList(new ArrayList<DialectType>());
		}
		if (stdType.getDialectTypeList().size() ==0) {
			DialectType javaDialect = new DialectType();
			javaDialect.setLanguage("java");
			stdType.getDialectTypeList().add(javaDialect);
			
			DialectType mysqlDialect = new DialectType();
			mysqlDialect.setLanguage("mysql");
			mysqlDialect.setBaseType(type);
			if (StringUtils.isNotBlank(length)) {
				mysqlDialect.setExtType(length);
			}
			stdType.getDialectTypeList().add(mysqlDialect);
		}
		
		return stdType;
	}
	
	private StandardField getStdfield(String name , String title	,String type , String length){
		StdfieldPair key = new StdfieldPair(name ,"");
		StandardField stdfield = stdfieldMap.get(key);
		if (stdfield == null) {
			stdfield = new StandardField();
			stdfieldMap.put(key, stdfield);
			stdfield.setId(Guid.createGUID());
			stdfield.setName(name);
			stdfield.setTitle(StringUtils.upperCase(StringUtils.isBlank(title) ?name:title));
			stdfield.setTypeId(getBizType(type, length).getId());
		}
		return stdfield;
	}
	
	private BusinessType getBizType(String type , String length	){
		BizTypePair key = new BizTypePair(type ,length);
		BusinessType bizType = bizTypesMap.get(key);
		if (bizType == null) {
			bizType = new BusinessType();
			bizTypesMap.put(key, bizType);
			bizType.setId(Guid.createGUID());
			bizType.setName(type + StringUtils.defaultString(length));
			bizType.setTitle(StringUtils.upperCase(type + StringUtils.defaultString(length)));
			if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(length)) {
				PlaceholderValue value = new PlaceholderValue();
				value.setName("length");
				value.setValue(length);
				if (bizType.getPlaceholderValueList() == null) {
					bizType.setPlaceholderValueList(new ArrayList<PlaceholderValue>());
				}
				bizType.getPlaceholderValueList().add(value);
			}
		}
		getStdType(type, length ,bizType);
		return bizType;
	}
	
	private static String getLowerCase(String str) {
		str = str.replaceAll("_ID", "Id");
		str = str.replaceAll("ID", "Id");
		StringBuffer sb = new StringBuffer();
		String[] strArray = str.split("_");
		for (int i = 0; i < strArray.length; i++) {
			if (i > 0) {
				sb.append("_");
			}
			String word = strArray[i];
			if (word.equalsIgnoreCase("id")) {
				sb.append(word.toLowerCase());
			} else {
				for (int j = 0; j < word.length(); j++) {
					char ch = word.charAt(j);
					if (ch >= 'A' && ch <= 'Z') {
						if (j != 0) {
							sb.append("_");
						}
						sb.append((char) (ch - 'A' + 'a'));
					} else {
						sb.append(ch);
					}
				}
			}
		}
		return sb.toString();
	}

	private static boolean isPrimary(XmlNode root, XmlNode element, String name) {
		XmlNode operations = element.getSubNode("operations");
		if (operations != null) {
			List<XmlNode> nodeList = operations.getSubNodes("operation");
			for (XmlNode operation : nodeList) {
				XmlNode stereotype = operation.getSubNode("stereotype");
				if (stereotype != null) {
					if (stereotype.getAttribute("stereotype").equals("PK")) {
						NameFilter<XmlNode> filter = new NameFilter<XmlNode>(
								root);
						filter.setIncludeAttribute("xmi:id",
								operation.getAttribute("xmi:idref"));
						XmlNode node = filter.findNode("ownedOperation");
						List<XmlNode> parameterList = node
								.getSubNodes("ownedParameter");
						if (parameterList.size() == 1
								&& getLowerCase(
										parameterList.get(0).getAttribute(
												"name")).equals(name)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private static boolean isUnique(XmlNode root, XmlNode element, String name) {
		XmlNode operations = element.getSubNode("operations");
		if (operations != null) {
			List<XmlNode> nodeList = operations.getSubNodes("operation");
			for (XmlNode operation : nodeList) {
				XmlNode stereotype = operation.getSubNode("stereotype");
				if (stereotype != null) {
					if (stereotype.getAttribute("stereotype").equals("unique")) {
						NameFilter<XmlNode> filter = new NameFilter<XmlNode>(
								root);
						filter.setIncludeAttribute("xmi:id",
								operation.getAttribute("xmi:idref"));
						XmlNode node = filter.findNode("ownedOperation");
						List<XmlNode> parameterList = node
								.getSubNodes("ownedParameter");
						if (parameterList != null
								&& parameterList.size() == 1
								&& getLowerCase(
										parameterList.get(0).getAttribute(
												"name")).equals(name)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private static void printOperations(XmlNode root, XmlNode element,
			StringBuffer buf) {
		XmlNode operations = element.getSubNode("operations");
		if (operations != null) {
			List<XmlNode> nodeList = operations.getSubNodes("operation");
			for (XmlNode operation : nodeList) {
				XmlNode stereotype = operation.getSubNode("stereotype");
				if (stereotype != null) {
					if (stereotype.getAttribute("stereotype").equals("PK")) {
						printPK(root, element, operation);
					} else if (stereotype.getAttribute("stereotype").equals(
							"unique")) {
						System.out.println(getUnique(root, element, operation));
					} else if (stereotype.getAttribute("stereotype").equals(
							"index")) {
						System.out.println(getIndex(root, element, operation));
					}
				}
			}
		}
	}

	private static void printPK(XmlNode root, XmlNode element, XmlNode operation) {
		NameFilter<XmlNode> filter = new NameFilter<XmlNode>(root);
		filter.setIncludeAttribute("xmi:id",
				operation.getAttribute("xmi:idref"));
		XmlNode node = filter.findNode("ownedOperation");
		getParameters(node);
	}

	private static String getUnique(XmlNode root, XmlNode element,
			XmlNode operation) {
		NameFilter<XmlNode> filter = new NameFilter<XmlNode>(root);
		filter.setIncludeAttribute("xmi:id",
				operation.getAttribute("xmi:idref"));
		XmlNode node = filter.findNode("ownedOperation");
		return getParameters(node);
	}

	private static String getIndex(XmlNode root, XmlNode element,
			XmlNode operation) {
		NameFilter<XmlNode> filter = new NameFilter<XmlNode>(root);
		filter.setIncludeAttribute("xmi:id",
				operation.getAttribute("xmi:idref"));
		XmlNode node = filter.findNode("ownedOperation");
		return getParameters(node);
	}

	private static String getParameters(XmlNode node) {
		StringBuffer sb = new StringBuffer();
		List<XmlNode> parameterList = node.getSubNodes("ownedParameter");
		if (parameterList != null) {
			for (XmlNode para : parameterList) {
				if (sb.length() > 0) {
					sb.append(",");
					sb.append(para.getAttribute("name"));
				}
			}
		}
		return sb.toString();
	}
}
