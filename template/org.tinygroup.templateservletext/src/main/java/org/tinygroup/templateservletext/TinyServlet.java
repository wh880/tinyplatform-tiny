package org.tinygroup.templateservletext;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.template.*;
import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.loader.FileObjectResourceLoader;
import org.tinygroup.vfs.FileObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


public class TinyServlet extends HttpServlet {
    public static final String TEMPLATE_EXT_NAME = "page";

    public static final String LAYOUT_EXT_NAME = "layout";

    public static final String MACRO_LIBRARY_EXT_NAME = "component";

    public static final String RESOURCE_SRC = "src/main/resources";

    public static final String REQUEST = "req";

    public static final String RESPONSE = "res";

    public static final String CONTENT_TYPE = "default.contentType";

    public static final String DEFAULT_CONTENT_TYPE = "text/html";

    public static final String DEFAULT_OUTPUT_ENCODING = "ISO-8859-1";

    private static final Logger logger = LoggerFactory
            .getLogger(TinyServlet.class);

    private static TemplateEngine engine = new TemplateEngineDefault();

    private transient ResourceLoader resourceLoader;

    private static String defaultContentType;

    private String templateExtName = null;

    private String layoutExtName = null;

    private String macroLibraryExtName = null;

    private String resourcePath = null;

    public void init(ServletConfig config)
            throws ServletException {
        super.init(config);

        initTiny(config);

    }


    protected void initTiny(ServletConfig config)
            throws ServletException {
        try {
            logger.logMessage(LogLevel.INFO,
                    "TinyServlet init start ...");
            engineInit(config.getInitParameter("resourceLoaderPath"), config.getInitParameter("function"), config.getInitParameter("i18n"));
            defaultContentType = DEFAULT_CONTENT_TYPE;
            logger.logMessage(LogLevel.INFO,
                    "TinyServlet init end");
        } catch (Exception e) {
            logger.logMessage(LogLevel.ERROR,
                    "Error initializing Tiny:" + e);
            throw new ServletException("Error initializing Tiny: " + e, e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doRequest(request, response);
    }

    protected void doRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TemplateContext context = null;
        try {

            context = createContext(request, response);

            setContentType(request, response);

            String path = handleRequest(request, response, context);

            if (path == null) {
                throw new Exception("handleRequest(request, response, context) returned null - no template selected!");
            }

            mergeTemplate(path, context, response);
        } catch (Exception e) {
            error(request, response, e);
        } finally {

            requestCleanup(request, response, context);
        }
    }

    protected void requestCleanup(HttpServletRequest request, HttpServletResponse response, TemplateContext context) {
    }


    protected void mergeTemplate(String path, TemplateContext context, HttpServletResponse response)
            throws TemplateException, IOException {
        logger.logMessage(LogLevel.INFO,
                "Template " + "[" + resourcePath + "/" + path + "]" + " merge start...");
        String encoding = response.getCharacterEncoding();
        engine.setEncode(encoding);
        try {
            checkResource(getExtFileName(path));
        } catch (Exception e) {
        	logger.errorMessage("Template merge error:", e);
            return;
        }
        if (isPagelet(path)) {
            engine.renderTemplateWithOutLayout(getExtFileName(path), context, response.getOutputStream());
        } else {
            engine.renderTemplate(getExtFileName(path), context, response.getOutputStream());
        }
        logger.logMessage(LogLevel.INFO,
                "Template merge end");

    }

    protected void setContentType(HttpServletRequest request,
                                  HttpServletResponse response) {
        String contentType = TinyServlet.defaultContentType;
        int index = contentType.lastIndexOf(';') + 1;
        if (index <= 0 || (index < contentType.length() &&
                contentType.indexOf("charset", index) == -1)) {
            String encoding = chooseCharacterEncoding(request);
            if (!DEFAULT_OUTPUT_ENCODING.equalsIgnoreCase(encoding)) {
                contentType += "; charset=" + encoding;
            }
        }
        response.setContentType(contentType);
    }

    protected String chooseCharacterEncoding(HttpServletRequest request) {
        return request.getCharacterEncoding();
    }

    protected TemplateContext createContext(HttpServletRequest request, HttpServletResponse response) {


        TemplateContext context = new TemplateContextDefault();

        context.put(REQUEST, request);
        context.put(RESPONSE, response);

        return context;
    }


    protected String handleRequest(HttpServletRequest request, HttpServletResponse response, TemplateContext ctx)
            throws Exception {

        String t = handleRequest(ctx);

        if (t == null) {
            throw new Exception("handleRequest(Context) returned null - no template selected!");
        }

        return t;
    }

    protected String handleRequest(TemplateContext ctx)
            throws Exception {
        throw new Exception("You must override TinyServlet.handleRequest( Context) "
                + " or TinyServlet.handleRequest( HttpServletRequest, "
                + " HttpServletResponse, Context)");
    }

    protected void error(HttpServletRequest request, HttpServletResponse response, Exception cause)
            throws ServletException, IOException {
        StringBuffer html = new StringBuffer();
        html.append("<html>");
        html.append("<title>Error</title>");
        html.append("<body bgcolor=\"#ffffff\">");
        html.append("<h2>TinyServlet: Error processing the template</h2>");
        html.append("<pre>");
        String why = cause.getMessage();
        if (why != null && why.trim().length() > 0) {
            html.append(why);
            html.append("<br>");
        }

        StringWriter sw = new StringWriter();
        cause.printStackTrace(new PrintWriter(sw));

        html.append(sw.toString());
        html.append("</pre>");
        html.append("</body>");
        html.append("</html>");
        response.getOutputStream().print(html.toString());
    }

    public boolean checkResource(String viewPath) throws Exception {
        String path = getExtFileName(viewPath);
        try {
            engine.findTemplate(path);
            return true;
        } catch (Exception e) {
            logger.logMessage(LogLevel.ERROR,
                    "Could not load tiny template for URL [{0}]", e, path);
        }
        return false;
    }

    private boolean isPagelet(String path) {
        return StringUtils.endsWith(path, "pagelet");
    }

    private String getExtFileName(String path) {
        if (isPagelet(path)) {
            String name = path.substring(0, path.lastIndexOf("."));
            return name + ".page";
        } else if (StringUtils.endsWith(path, "page")) {
            return path;
        }
        return path + ".page";

    }

    private void engineInit(String resource, String functionPath, String i18npath) {
        logger.logMessage(LogLevel.INFO,
                "TinyTemplateEngine init start..");
        
        logger.logMessage(LogLevel.INFO,
                "ResourceLoader init start..");
        if(resource!=null) {

            String[] resourceLoaderConfig = resource.split(",");

            for (String rl : resourceLoaderConfig) {
                String[] rls = rl.split(":");
                if ("templateExtName".equals(rls[0]) && rls.length > 1) {
                    templateExtName = (!"".equals(rls[1]) && rls[1] != null) ? rls[1] : TEMPLATE_EXT_NAME;
                }
                if ("layoutExtName".equals(rls[0]) && rls.length > 1) {
                    layoutExtName = (!"".equals(rls[1]) && rls[1] != null) ? rls[1] : LAYOUT_EXT_NAME;
                }
                if ("macroLibraryExtName".equals(rls[0]) && rls.length > 1) {
                    macroLibraryExtName = (!"".equals(rls[1]) && rls[1] != null) ? rls[1] : MACRO_LIBRARY_EXT_NAME;
                }
                if ("resource".equals(rls[0]) && rls.length > 1) {
                    resourcePath = (!"".equals(rls[1]) && rls[1] != null) ? rls[1] : RESOURCE_SRC;
                }

            }
        }
        templateExtName = templateExtName == null ? TEMPLATE_EXT_NAME : templateExtName;
        layoutExtName = layoutExtName == null ? LAYOUT_EXT_NAME : layoutExtName;
        macroLibraryExtName = macroLibraryExtName == null ? MACRO_LIBRARY_EXT_NAME : macroLibraryExtName;
        resourcePath = resourcePath == null ? RESOURCE_SRC : resourcePath;
        logger.logMessage(LogLevel.INFO,
                "ResourceLoader templateExtName: " + templateExtName);
        logger.logMessage(LogLevel.INFO,
                "ResourceLoader layoutExtName: " + layoutExtName);
        logger.logMessage(LogLevel.INFO,
                "ResourceLoader macroLibraryExtName: " + macroLibraryExtName);
        logger.logMessage(LogLevel.INFO,
                "ResourceLoader resourcePath: " + resourcePath);
        resourceLoader = new FileObjectResourceLoader(templateExtName, layoutExtName, macroLibraryExtName, resourcePath);
        engine.addResourceLoader(resourceLoader);
        logger.logMessage(LogLevel.INFO,
                "Add ResourceLoader...");
        logger.logMessage(LogLevel.INFO,
                "ResourceLoader init end");
        logger.logMessage(LogLevel.INFO,
                "TemplateFunction init start..");
        String[] functionList = null;
        if (functionPath != null) {
            functionList = functionPath.split(",");

            for (String name : functionList) {
                try {
                    logger.logMessage(LogLevel.INFO,
                            "Init templateFunction " + "[" + name + "]");
                    engine.addTemplateFunction((TemplateFunction) Class.forName(name).newInstance());
                    logger.logMessage(LogLevel.INFO,
                            "Add templateFunction " + "[" + name + "]");
                } catch (InstantiationException e) {
                    logger.logMessage(LogLevel.ERROR,
                            "Could not instance TemplateFunction class for URL [{0}]", e, name);
                } catch (IllegalAccessException e) {
                    logger.logMessage(LogLevel.ERROR,
                            "The TemplateFunction class [{0}] witch wants to be instance has a private constructed function ", e, name);
                } catch (ClassNotFoundException e) {
                    logger.logMessage(LogLevel.ERROR,
                            "Could not find TemplateFunction class for URL [{0}]", e, name);
                }

            }
            logger.logMessage(LogLevel.INFO,
                    "TemplateFunction init end");
        }

        if (!"".equals(i18npath) && i18npath != null) {
            logger.logMessage(LogLevel.INFO,
                    "I18nVisitor init start..");
            try {
                logger.logMessage(LogLevel.INFO,
                        "Init I18nVisitor " + "[" + i18npath + "]");
                engine.setI18nVisitor((I18nVisitor) Class.forName(i18npath).newInstance());
                logger.logMessage(LogLevel.INFO,
                        "Add I18nVisitor " + "[" + i18npath + "]");
            } catch (InstantiationException e) {
                logger.logMessage(LogLevel.ERROR,
                        "Could not instance I18nVisitor class for URL [{0}]", e, i18npath);
            } catch (IllegalAccessException e) {
                logger.logMessage(LogLevel.ERROR,
                        "The I18nVisitor class [{0}] witch wants to be instance has a private constructed function ", e, i18npath);
            } catch (ClassNotFoundException e) {
                logger.logMessage(LogLevel.ERROR,
                        "Could not find I18nVisitor class for URL [{0}]", e, i18npath);
            }
            logger.logMessage(LogLevel.INFO,
                    "I18nVisitor init end");
        }

        initMacro();

        logger.logMessage(LogLevel.INFO,
                "TinyTemplateEngine init end");
    }

    private void initMacro() {
        logger.logMessage(LogLevel.INFO,
                "MacroLibrary init start");
        for (ResourceLoader resourceloader : engine.getResourceLoaderList()) {
            if (resourceloader instanceof FileObjectResourceLoader) {
                registerMacroFile(resourceloader);
            }
        }
        logger.logMessage(LogLevel.INFO,
                "MacroLibrary init end");
    }

    private void registerMacroFile(ResourceLoader rl) {
        FileObjectResourceLoader resourceLoader = (FileObjectResourceLoader) rl;
        FileObject file = resourceLoader.getRootFileObject();
        resolveFileDir(file);

    }

    private void resolveFileDir(FileObject file) {
        if (file.isFolder()) {
            for (FileObject f : file.getChildren()) {
                resolveFileDir(f);
            }
        } else {
            String name = file.getFileName();
            int n = name.lastIndexOf(".");
            String fileExt = name.substring(n + 1);
            String[] parentPath = resourcePath.split("/");
            String parent = parentPath[parentPath.length - 1];
            if (macroLibraryExtName.equals(fileExt)) {
                try {
                    String path = getFilePath(file, parent).substring(1);
                    engine.registerMacroLibrary(path);
                    logger.logMessage(LogLevel.INFO,
                            "ADD macroLibrary file [" + path + "]");
                } catch (TemplateException e) {
                   logger.errorMessage("resolveFileDir has error:", e);
                }
            }
        }

    }

    private String getFilePath(FileObject file, String parent) {
        if (!parent.equals(file.getFileName())) {
            return getFilePath(file.getParent(), parent) + "/" + file.getFileName();
        }
        return "";

    }
}
