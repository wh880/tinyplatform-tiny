package org.tinygroup.codegen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.codegen.config.CodeGenMetaData;
import org.tinygroup.codegen.config.MacroDefine;
import org.tinygroup.codegen.config.TemplateDefine;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.context.Context;
import org.tinygroup.context.util.ContextFactory;
import org.tinygroup.docgen.DocumentGenerater;
import org.tinygroup.docgen.impl.DocumentGeneraterImpl;
import org.tinygroup.fileresolver.FullContextFileRepository;
import org.tinygroup.fileresolver.impl.FullContextFileRepositoryImpl;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.velocity.VelocityHelper;
import org.tinygroup.velocity.config.VelocityContextConfig;
import org.tinygroup.velocity.impl.VelocityHelperImpl;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;

import com.thoughtworks.xstream.XStream;

public class CodeGenerator{
	
	private static final String CODE_FULL_CONTEXT_FILE_REPOSITORY = "codeFullContextFileRepository";
	private static final String CODE_GENERATER = "codeDocumentGenerater";
	public static final String JAVA_ROOT="JAVA_ROOT";
	public static final String JAVA_TEST_ROOT="JAVA_TEST_ROOT";
	public static final String JAVA_RES_ROOT="JAVA_RES_ROOT";
	public static final String JAVA_TEST_RES_ROOT="JAVA_TEST_RES_ROOT";
	public static final String CODE_META_DATA="CODE_META_DATA";
	public static final String TEMPLATE_FILE="TEMPLATE_FILE";
	public static final String XSTEAM_PACKAGE_NAME="codegen";
	public static final String ABSOLUTE_PATH="absolute_path";
    //public static final String CODE_GEN_BEANS_XML = "/codegenbeans.xml";
	//private static Factory factory;
	private static Logger logger = LoggerFactory.getLogger(CodeGenerator.class);
    
	public static FullContextFileRepository repository;//暂时定为静态
	private DocumentGenerater generater;
	/* static{
		  factory = BeanFactory.getFactory();
	        XStream xStream = XStreamFactory.getXStream();
	        logger.logMessage(LogLevel.INFO, "加载Bean配置文件{}开始...", CODE_GEN_BEANS_XML);
	        try {
	            Beans beans = (Beans) xStream.fromXML(CodeGenerater.class.getResourceAsStream(CODE_GEN_BEANS_XML));
	            factory.addBeans(beans);
	            factory.init();
	            logger.logMessage(LogLevel.INFO, "加载Bean配置文件{}结束。", CODE_GEN_BEANS_XML);
	        } catch (Exception e) {
	            logger.errorMessage("加载Bean配置文件{}时发生错误", e, CODE_GEN_BEANS_XML);
	        }
	 }*/
	
	public CodeGenerator(){
		generater=new DocumentGeneraterImpl();
		VelocityHelperImpl documentGeneraterVelocityHelper=new VelocityHelperImpl();
		generater.setDocumentGeneraterVelocityHelper(documentGeneraterVelocityHelper);
		if(repository==null){
			repository=new FullContextFileRepositoryImpl();
		}
		documentGeneraterVelocityHelper.setFullContextFileRepository(repository);
	}
	

	/**
	 * @param context
	 *            默认文件需要用到的上下文环境
	 * @throws IOException 
	 */
	public List<String> generate(Context context) throws IOException{
		CodeGenMetaData metaData=context.get(CODE_META_DATA);
		if(metaData==null){
			throw new RuntimeException("代码生成器的元数据不存在");
		}
		addUtilClass(generater.getDocumentGeneraterVelocityHelper());
		List<MacroDefine> macroDefines=metaData.getMacroDefines();
		for (MacroDefine macroDefine : macroDefines) {
		   logger.logMessage(LogLevel.INFO, "开始加载宏文件路径：{0}",macroDefine.getMacroPath());
		   String macroPath=macroDefine.getMacroPath();
		   if (StringUtils.startsWith(macroPath, "/") || StringUtils.startsWith(macroPath, "\\")) {
			   macroPath = StringUtils.substring(macroPath, 1);
			}
		   FileObject fileObject=VFS.resolveFile(context.get(ABSOLUTE_PATH).toString());
		   generater.addMacroFile(fileObject.getChild(macroPath));
		   logger.logMessage(LogLevel.INFO, "宏文件路径：{0}，加载完毕",macroDefine.getMacroPath());
		}
		List<TemplateDefine> templateDefines=metaData.getTemplateDefines();
		List<String> fileList=new ArrayList<String>();
		for (TemplateDefine templateDefine : templateDefines) {
			Context newContext=createNewContext(context, templateDefine);
			String templatePath=templateDefine.getTemplatePath();
			logger.logMessage(LogLevel.INFO, "开始加载模板文件路径：{0}",templatePath);
//			if(templatePath.startsWith("/")){
//				templatePath=CodeGenerator.class.getResource(templatePath).getPath();
//			}
			FileObject templateFileObject=VFS.resolveFile(context.get(ABSOLUTE_PATH).toString());
			logger.logMessage(LogLevel.INFO, "模板文件路径：{0}，加载完毕",templatePath);
			String fileName = templatePath;
			if (StringUtils.startsWith(templatePath, "/") || StringUtils.startsWith(templatePath, "\\")) {
				fileName = StringUtils.substring(fileName, 1);
			}
			repository.addFileObject(templatePath, templateFileObject.getChild(fileName));
			String generateFile=generater.evaluteString(newContext, templateDefine.getFileNameTemplate());
			File file=new File(generateFile);
			if(!file.exists()){
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			FileWriter writer=new FileWriter(file);
			try {
				generater.generate(templatePath, newContext,writer);
			}finally{
				writer.close();
			}
			fileList.add(file.getPath());
		}
		return fileList;
		
	}
	
	private void addUtilClass(VelocityHelper documentGeneraterVelocityHelper) {
//		XStream stream = XStreamFactory.getXStream(VelocityHelper.XSTEAM_PACKAGE_NAME);
		XStream stream = new XStream();
		stream.setClassLoader(getClass().getClassLoader());
		stream.autodetectAnnotations(true);
		stream.processAnnotations(VelocityContextConfig.class);
		VelocityContextConfig config=(VelocityContextConfig)stream.fromXML(getClass().getResourceAsStream("/codegen.util.xml"));
		documentGeneraterVelocityHelper.setVelocityContextConfig(config);
	}

	private Context createNewContext(Context context,TemplateDefine templateDefine){
		Context newContext=ContextFactory.getContext();
		newContext.setParent(context);
		newContext.put(TEMPLATE_FILE, templateDefine);
		String templatePath=templateDefine.getTemplatePath();
//		if(templatePath.startsWith("/")){
//			templatePath=CodeGenerator.class.getResource(templatePath).getPath().replaceAll("/", "\\"+File.separator);
//		}
//		String filePath=StringUtil.substringBeforeLast(templatePath, File.separator);
//		if(!filePath.endsWith(File.separator)){
//			filePath=filePath+File.separator;
//		}
		String templateFilePath = templatePath;
		templateFilePath = StringUtil.substringBeforeLast(templateFilePath, "/");
		templateFilePath = StringUtil.substringBeforeLast(templateFilePath, "\\");
		newContext.put("templateFilePath", templateFilePath);
		String fileName=templatePath;
		if (StringUtils.indexOf(fileName, "/") > -1) {
			fileName=StringUtil.substringAfterLast(fileName, "/");
		}
		if (StringUtils.indexOf(fileName, "\\") > -1) {
			fileName=StringUtil.substringAfterLast(fileName, "\\");
		}
		newContext.put("templateFileName", StringUtil.substringBeforeLast(fileName, "."));
		return newContext;
	}

}
