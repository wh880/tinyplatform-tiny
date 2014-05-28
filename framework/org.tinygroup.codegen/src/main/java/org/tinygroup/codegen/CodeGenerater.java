package org.tinygroup.codegen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.tinygroup.codegen.config.CodeGenMetaData;
import org.tinygroup.codegen.config.MacroDefine;
import org.tinygroup.codegen.config.TemplateDefine;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.config.Configuration;
import org.tinygroup.context.Context;
import org.tinygroup.context.util.ContextFactory;
import org.tinygroup.docgen.DocumentGenerater;
import org.tinygroup.fileresolver.FullContextFileRepository;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.velocity.VelocityHelper;
import org.tinygroup.velocity.config.VelocityContextConfig;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;
import org.tinygroup.xmlparser.node.XmlNode;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

public class CodeGenerater implements Configuration{
	
	private static final String CODE_FULL_CONTEXT_FILE_REPOSITORY = "fullContextFileRepository";
	private static final String CODE_GENERATER = "codeDocumentGenerater";
	public static final String JAVA_ROOT="JAVA_ROOT";
	public static final String JAVA_TEST_ROOT="JAVA_TEST_ROOT";
	public static final String JAVA_RES_ROOT="JAVA_RES_ROOT";
	public static final String JAVA_TEST_RES_ROOT="JAVA_TEST_RES_ROOT";
	public static final String CODE_META_DATA="CODE_META_DATA";
	public static final String TEMPLATE_FILE="TEMPLATE_FILE";
	public static final String XSTEAM_PACKAGE_NAME="codegen";

	/**
	 * @param context
	 *            默认文件需要用到的上下文环境
	 * @throws IOException 
	 */
	public void generate(Context context) throws IOException{
		CodeGenMetaData metaData=context.get(CODE_META_DATA);
		if(metaData==null){
			throw new RuntimeException("代码生成器的元数据不存在");
		}
		DocumentGenerater generater=SpringUtil.getBean(CODE_GENERATER);
		addUtilClass(generater.getDocumentGeneraterVelocityHelper());
		List<MacroDefine> macroDefines=metaData.getMacroDefines();
		for (MacroDefine macroDefine : macroDefines) {
		   FileObject fileObject=VFS.resolveFile(macroDefine.getMarcoPath());
		   generater.addMacroFile(fileObject);
		}
		List<TemplateDefine> templateDefines=metaData.getTemplateDefines();
		for (TemplateDefine templateDefine : templateDefines) {
			Context newContext=createNewContext(context, templateDefine);
			String templatePath=templateDefine.getTemplatePath();
			FileObject templateFileObject=VFS.resolveFile(templatePath);
			FullContextFileRepository repository=SpringUtil.getBean(CODE_FULL_CONTEXT_FILE_REPOSITORY);
			repository.addFileObject(templateFileObject.getPath(), templateFileObject);
			String generateFile=generater.evaluteString(newContext, templateDefine.getFileNameTemplate());
			File file=new File(generateFile);
			if(!file.exists()){
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			generater.generate(templateFileObject.getPath(), newContext, new FileWriter(file));
		}
		
	}
	
	private void addUtilClass(VelocityHelper documentGeneraterVelocityHelper) {//可以调整为fileprocessor
		XStream stream = XStreamFactory.getXStream(VelocityHelper.XSTEAM_PACKAGE_NAME);
		VelocityContextConfig config=(VelocityContextConfig)stream.fromXML(getClass().getResourceAsStream("/codegen.util.xml"));
		documentGeneraterVelocityHelper.setVelocityContextConfig(config);
	}

	private Context createNewContext(Context context,TemplateDefine templateDefine){
		Context newContext=ContextFactory.getContext();
		newContext.setParent(context);
		newContext.put(TEMPLATE_FILE, templateDefine);
		String templatePath=templateDefine.getTemplatePath();
		String filePath=StringUtil.substringBeforeLast(templatePath, File.separator);
		if(!filePath.endsWith(File.separator)){
			filePath=filePath+File.separator;
		}
		newContext.put("templateFilePath", filePath);
		String fileName=StringUtil.substringAfterLast(templatePath, File.separator);
		newContext.put("templateFileName", StringUtil.substringBeforeLast(fileName, "."));
		return newContext;
	}

	public String getApplicationNodePath() {
		return null;
	}

	public String getComponentConfigPath() {
		return null;
	}

	public void config(XmlNode applicationConfig, XmlNode componentConfig) {
		
	}

	public XmlNode getComponentConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	public XmlNode getApplicationConfig() {
		// TODO Auto-generated method stub
		return null;
	}

}
