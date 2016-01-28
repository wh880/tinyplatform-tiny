package org.tinygroup.databasechange;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.BeanWrapper;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.beanwrapper.BeanWrapperHolder;
import org.tinygroup.commons.io.StreamUtil;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.database.ProcessorManager;
import org.tinygroup.database.customesql.CustomSqlProcessor;
import org.tinygroup.database.customesql.impl.CustomSqlProcessorImpl;
import org.tinygroup.database.fileresolver.InitDataFileResolver;
import org.tinygroup.database.fileresolver.ProcedureFileResolver;
import org.tinygroup.database.fileresolver.TableFileResolver;
import org.tinygroup.database.fileresolver.ViewFileResolver;
import org.tinygroup.database.impl.ProcessorManagerImpl;
import org.tinygroup.database.initdata.InitDataProcessor;
import org.tinygroup.database.initdata.impl.InitDataProcessorImpl;
import org.tinygroup.database.procedure.ProcedureProcessor;
import org.tinygroup.database.procedure.impl.ProcedureProcessorImpl;
import org.tinygroup.database.sequence.SequenceProcessor;
import org.tinygroup.database.sequence.impl.SequenceProcessorImpl;
import org.tinygroup.database.table.TableProcessor;
import org.tinygroup.database.table.impl.TableProcessorImpl;
import org.tinygroup.database.trigger.TriggerProcessor;
import org.tinygroup.database.trigger.impl.TriggerProcessorImpl;
import org.tinygroup.database.view.ViewProcessor;
import org.tinygroup.database.view.impl.ViewProcessorImpl;
import org.tinygroup.databasebuinstaller.DataSourceHolder;
import org.tinygroup.databasebuinstaller.DatabaseInstallerProcessor;
import org.tinygroup.databasebuinstaller.InstallProcessor;
import org.tinygroup.databasebuinstaller.impl.CustomSqlInstallProcessor;
import org.tinygroup.databasebuinstaller.impl.InitDataInstallProcessor;
import org.tinygroup.databasebuinstaller.impl.ProcedureInstallProcessor;
import org.tinygroup.databasebuinstaller.impl.SequenceInstallProcessor;
import org.tinygroup.databasebuinstaller.impl.TableInstallProcessor;
import org.tinygroup.databasebuinstaller.impl.TriggerInstallProcessor;
import org.tinygroup.databasebuinstaller.impl.ViewInstallProcessor;
import org.tinygroup.fileresolver.FileResolver;
import org.tinygroup.fileresolver.FileResolverFactory;
import org.tinygroup.fileresolver.FileResolverUtil;
import org.tinygroup.fileresolver.impl.I18nFileProcessor;
import org.tinygroup.fileresolver.impl.XStreamFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.metadata.bizdatatype.impl.BusinessTypeProcessorImpl;
import org.tinygroup.metadata.constants.impl.ConstantsProcessorImpl;
import org.tinygroup.metadata.errormessage.impl.ErrorMessageProcessorImpl;
import org.tinygroup.metadata.fileresolver.BusinessTypeFileResolver;
import org.tinygroup.metadata.fileresolver.ConstantFileResolver;
import org.tinygroup.metadata.fileresolver.ErrorMessageFileResolver;
import org.tinygroup.metadata.fileresolver.StandardFieldFileResolver;
import org.tinygroup.metadata.fileresolver.StandardTypeFileResolver;
import org.tinygroup.metadata.stddatatype.impl.StandardTypeProcessorImpl;
import org.tinygroup.metadata.stdfield.impl.StandardFieldProcessorImpl;
import org.tinygroup.parser.filter.PathFilter;
import org.tinygroup.springutil.SpringBeanContainer;
import org.tinygroup.xmlparser.node.XmlNode;
import org.tinygroup.xmlparser.parser.XmlStringParser;

/**
 * 数据库按照启动类
 * 
 * @author renhui
 *
 */
public class DatabaseInstallerStart {

	private static String DEFAULT_CONFIG = "application.xml";
	private static final String TYPE = "type";
	private static final String PROPERTY = "property";
	private static final String TINY_JAR_PATTERN = "org\\.tinygroup\\.(.)*\\.jar";
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DatabaseInstallerStart.class);
	private DatabaseInstallerProcessor installer;
	private XmlNode applicationNode;

	public DatabaseInstallerStart() {
		applicationNode = getRootNode();
		installer = initInstaller();
		installer.setDbLanguage(resolverLanguageType());
		BeanContainerFactory.initBeanContainer(SpringBeanContainer.class.getName());
		DataSourceHolder.setDataSource(createDataSource());// 绑定数据源
		initFileResolver();
	}

	private DatabaseInstallerProcessor initInstaller() {
		DatabaseInstallerProcessor installer = new DatabaseInstallerProcessor();
		List<InstallProcessor> installProcessors = new ArrayList<InstallProcessor>();
		ProcessorManager processorManager = ProcessorManagerImpl
				.getProcessorManager();

		TableInstallProcessor tableInstallProcessor = new TableInstallProcessor();
		TableProcessor tableProcessor = createTableProcessor(processorManager);
		tableInstallProcessor.setTableProcessor(tableProcessor);
		installProcessors.add(tableInstallProcessor);

		ViewInstallProcessor viewInstallProcessor = new ViewInstallProcessor();
		ViewProcessor viewProcessor = createViewProcessor(processorManager);
		viewInstallProcessor.setViewProcessor(viewProcessor);
		installProcessors.add(viewInstallProcessor);

		InitDataInstallProcessor initDataInstallProcessor = new InitDataInstallProcessor();
		InitDataProcessor initDataProcessor = createInitDataProcessor(processorManager);
		initDataInstallProcessor.setInitDataProcessor(initDataProcessor);
		installProcessors.add(initDataInstallProcessor);

		ProcedureInstallProcessor procedureInstallProcessor = new ProcedureInstallProcessor();
		ProcedureProcessor procedureProcessor = createProcedureProcessor(processorManager);
		procedureInstallProcessor.setProcedureProcessor(procedureProcessor);
		installProcessors.add(procedureInstallProcessor);

		TriggerInstallProcessor triggerInstallProcessor = new TriggerInstallProcessor();
		TriggerProcessor triggerProcessor = createTriggerProcessor(processorManager);
		triggerInstallProcessor.setProcessor(triggerProcessor);
		installProcessors.add(triggerInstallProcessor);

		SequenceInstallProcessor sequenceInstallProcessor = new SequenceInstallProcessor();
		SequenceProcessor sequenceProcessor = createSequenceProcessor(processorManager);
		sequenceInstallProcessor.setProcessor(sequenceProcessor);
		installProcessors.add(sequenceInstallProcessor);

		CustomSqlInstallProcessor customSqlInstallProcessor = new CustomSqlInstallProcessor();
		CustomSqlProcessor customSqlProcessor = createCustomSqlProcessor();
		customSqlInstallProcessor.setCustomSqlProcessor(customSqlProcessor);
		installProcessors.add(customSqlInstallProcessor);
		installer.setInstallProcessors(installProcessors);
		return installer;
	}

	private String resolverLanguageType() {
		PathFilter<XmlNode> filter = new PathFilter<XmlNode>(applicationNode);
		XmlNode xmlNode = filter
				.findNode("/application/database-install-processor/database-installer");
		XmlNode node = xmlNode.getSubNode("database");
		String language = node.getAttribute("type");
		if (StringUtil.isBlank(language)) {
			language = "oracle";
		}
		return language;
	}

	/**
	 * 数据库安装启动
	 */
	public void installer() {
		LOGGER.logMessage(LogLevel.INFO, "开始启动数据库安装操作");
		databaseInstaller();
		LOGGER.logMessage(LogLevel.INFO, "数据库安装操作过程结束");

	}

	public Map<Class, List<String>> getChangeSqls() {
		LOGGER.logMessage(LogLevel.INFO, "开始检测数据库变化");
		Map<Class, List<String>> sqls = installer.getChangeSqls();
		LOGGER.logMessage(LogLevel.INFO, "检测数据库变化过程结束");
		return sqls;
	}
	
	public Map<Class, List<String>> getFullSqls() {
		LOGGER.logMessage(LogLevel.INFO, "开始生成全量sql");
		Map<Class, List<String>> sqls = installer.getFullSqls();
		LOGGER.logMessage(LogLevel.INFO, "生成全量sql结束");
		return sqls;
	}

	private void initFileResolver() {
		FileResolver fileResolver = createFileResolver();
		addI18nFileProcessor(fileResolver);
		addXstreamFileProcessor(fileResolver);
		addConstantFileProcessor(fileResolver);
		addStandardTypeFileProcessor(fileResolver);
		addErrorMessageFileProcessor(fileResolver);
		addBusinessTypeFileResolver(fileResolver);
		addStandardFieldFileResolver(fileResolver);
		addTableFileResolver(fileResolver);
		addInitDataFileResolver(fileResolver);
		addCustomSqlFileResolver(fileResolver);
		addViewFileResolver(fileResolver);
		addProcedureFileResolver(fileResolver);
		startFileResolver(fileResolver);
	}

	private void databaseInstaller() {
		installer.process();
	}

	private void startFileResolver(FileResolver fileResolver) {
		fileResolver.resolve();
	}

	private void addProcedureFileResolver(FileResolver fileResolver) {
		ProcedureFileResolver procedureFileResolver = new ProcedureFileResolver();
		procedureFileResolver.setProcedureProcessor(ProcedureProcessorImpl
				.getProcedureProcessor());
		fileResolver.addFileProcessor(procedureFileResolver);
	}

	private void addViewFileResolver(FileResolver fileResolver) {
		ViewFileResolver viewFileResolver = new ViewFileResolver();
		viewFileResolver.setViewProcessor(ViewProcessorImpl.getViewProcessor());
		fileResolver.addFileProcessor(viewFileResolver);
	}

	private void addCustomSqlFileResolver(FileResolver fileResolver) {

	}

	private void addInitDataFileResolver(FileResolver fileResolver) {
		InitDataFileResolver initDataFileResolver = new InitDataFileResolver();
		initDataFileResolver.setInitDataProcessor(InitDataProcessorImpl
				.getInitDataProcessor());
		fileResolver.addFileProcessor(initDataFileResolver);
	}

	private void addTableFileResolver(FileResolver fileResolver) {
		TableFileResolver tableFileResolver = new TableFileResolver();
		tableFileResolver.setTableProcessor(TableProcessorImpl
				.getTableProcessor());
		fileResolver.addFileProcessor(tableFileResolver);
	}

	private void addStandardFieldFileResolver(FileResolver fileResolver) {
		StandardFieldFileResolver standardFieldFileResolver = new StandardFieldFileResolver();
		standardFieldFileResolver
				.setStandardFieldProcessor(StandardFieldProcessorImpl
						.getStandardFieldProcessor());
		fileResolver.addFileProcessor(standardFieldFileResolver);
	}

	private void addBusinessTypeFileResolver(FileResolver fileResolver) {
		BusinessTypeFileResolver businessTypeFileResolver = new BusinessTypeFileResolver();
		businessTypeFileResolver
				.setBusinessTypeProcessor(BusinessTypeProcessorImpl
						.getBusinessTypeProcessor());
		fileResolver.addFileProcessor(businessTypeFileResolver);
	}

	private void addErrorMessageFileProcessor(FileResolver fileResolver) {
		ErrorMessageFileResolver errorMessageFileResolver = new ErrorMessageFileResolver();
		errorMessageFileResolver
				.setErrorMessageProcessor(ErrorMessageProcessorImpl
						.getErrorMessageProcessor());
		fileResolver.addFileProcessor(errorMessageFileResolver);
	}

	private void addStandardTypeFileProcessor(FileResolver fileResolver) {
		StandardTypeFileResolver standardTypeFileResolver = new StandardTypeFileResolver();
		standardTypeFileResolver
				.setStandardDataTypeProcessor(StandardTypeProcessorImpl
						.getStandardTypeProcessor());
		fileResolver.addFileProcessor(standardTypeFileResolver);
	}

	private void addConstantFileProcessor(FileResolver fileResolver) {
		ConstantFileResolver constantFileResolver = new ConstantFileResolver();
		constantFileResolver.setConstantProcessor(ConstantsProcessorImpl
				.getConstantProcessor());
		fileResolver.addFileProcessor(constantFileResolver);
	}

	private void addXstreamFileProcessor(FileResolver fileResolver) {
		fileResolver.addFileProcessor(new XStreamFileProcessor());
	}

	private void addI18nFileProcessor(FileResolver fileResolver) {
		fileResolver.addFileProcessor(new I18nFileProcessor());
	}

	private FileResolver createFileResolver() {
		FileResolver fileResolver = FileResolverFactory.getFileResolver();
		FileResolverUtil.addClassPathPattern(fileResolver);
		fileResolver
				.addResolvePath(FileResolverUtil.getClassPath(fileResolver));
		fileResolver.addResolvePath(FileResolverUtil.getWebClasses());
		try {
			fileResolver.addResolvePath(FileResolverUtil
					.getWebLibJars(fileResolver));
		} catch (Exception e) {
			LOGGER.errorMessage("为文件扫描器添加webLibJars时出现异常", e);
		}
		fileResolver.addIncludePathPattern(TINY_JAR_PATTERN);
		loadFileResolverConfig(fileResolver);
		return fileResolver;
	}

	private XmlNode getRootNode() {
		InputStream inputStream = DatabaseInstallerStart.class.getClassLoader()
				.getResourceAsStream(DEFAULT_CONFIG);
		if (inputStream == null) {
			inputStream = DatabaseInstallerStart.class
					.getResourceAsStream(DEFAULT_CONFIG);
		}
		String applicationConfig = null;
		try {
			applicationConfig = StreamUtil.readText(inputStream, "UTF-8", true);
		} catch (IOException e1) {
			LOGGER.errorMessage("读取application.xml文件出错", e1);
			throw new RuntimeException(e1);
		}
		XmlStringParser parser = new XmlStringParser();
		return parser.parse(applicationConfig).getRoot();
	}

	private DataSource createDataSource() {
		PathFilter<XmlNode> filter = new PathFilter<XmlNode>(applicationNode);
		XmlNode xmlNode = filter.findNode("/application/datasource");
		if (xmlNode != null) {
			String type = xmlNode.getAttribute(TYPE);
			try {
				DataSource dataSource = newDataSourceInstance(type);
				setProperties(xmlNode, dataSource);
				return dataSource;
			} catch (Exception e) {
				LOGGER.errorMessage("实例化数据源出错", e);
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	private void setProperties(XmlNode node, Object object) {
		Map<String, String> properties = new HashMap<String, String>();
		List<XmlNode> subNodes = node.getSubNodes(PROPERTY);
		if (!CollectionUtil.isEmpty(subNodes)) {
			for (XmlNode subNode : subNodes) {
				properties.put(subNode.getAttribute("name"),
						subNode.getAttribute("value"));
			}
		}
		BeanWrapper wrapperImpl = BeanWrapperHolder.getInstance()
				.getBeanWrapper(object);
		wrapperImpl.setPropertyValues(properties);
	}

	public static void setProperties(Object object,
			Map<String, String> properties) {
		BeanWrapper wrapperImpl = BeanWrapperHolder.getInstance()
				.getBeanWrapper(object);
		for (String attribute : properties.keySet()) {
			try {
				String value = properties.get(attribute);
				wrapperImpl.setPropertyValue(attribute, value);
			} catch (Exception e) {
				throw new RuntimeException("设置对象属性出现异常", e);
			}
		}
	}

	private DataSource newDataSourceInstance(String type)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Class dataSourceType = Class.forName(type);
		DataSource dataSource = (DataSource) dataSourceType.newInstance();
		return dataSource;
	}

	private void loadFileResolverConfig(FileResolver fileResolver) {
		PathFilter<XmlNode> filter = new PathFilter<XmlNode>(applicationNode);
		List<XmlNode> classPathList = filter
				.findNodeList("/application/file-resolver-configuration/class-paths/class-path");
		for (XmlNode classPath : classPathList) {
			fileResolver.addResolvePath(classPath.getAttribute("path"));
		}
		List<XmlNode> includePatternList = filter
				.findNodeList("/application/file-resolver-configuration/include-patterns/include-pattern");
		for (XmlNode includePatternNode : includePatternList) {
			fileResolver.addIncludePathPattern(includePatternNode
					.getAttribute("pattern"));
		}

	}

	private CustomSqlProcessor createCustomSqlProcessor() {
		CustomSqlProcessor customSqlProcessor = CustomSqlProcessorImpl
				.getCustomSqlProcessor();
		return customSqlProcessor;
	}

	private SequenceProcessor createSequenceProcessor(
			ProcessorManager processorManager) {
		SequenceProcessor sequenceProcessor = SequenceProcessorImpl
				.getSequenceProcessor();
		sequenceProcessor.setProcessorManager(processorManager);
		return sequenceProcessor;
	}

	private TriggerProcessor createTriggerProcessor(
			ProcessorManager processorManager) {
		TriggerProcessor triggerProcessor = TriggerProcessorImpl
				.getTriggerProcessor();
		triggerProcessor.setProcessorManager(processorManager);
		return triggerProcessor;
	}

	private ProcedureProcessor createProcedureProcessor(
			ProcessorManager processorManager) {
		ProcedureProcessor procedureProcessor = ProcedureProcessorImpl
				.getProcedureProcessor();
		procedureProcessor.setProcessorManager(processorManager);
		return procedureProcessor;
	}

	private InitDataProcessor createInitDataProcessor(
			ProcessorManager processorManager) {
		InitDataProcessor initDataProcessor = InitDataProcessorImpl
				.getInitDataProcessor();
		initDataProcessor.setProcessorManager(processorManager);
		return initDataProcessor;
	}

	private ViewProcessor createViewProcessor(ProcessorManager processorManager) {
		ViewProcessor viewProcessor = ViewProcessorImpl.getViewProcessor();
		viewProcessor.setProcessorManager(processorManager);
		return viewProcessor;
	}

	private TableProcessor createTableProcessor(
			ProcessorManager processorManager) {
		TableProcessor tableProcessor = TableProcessorImpl.getTableProcessor();
		tableProcessor.setProcessorManager(processorManager);
		return tableProcessor;
	}

}
