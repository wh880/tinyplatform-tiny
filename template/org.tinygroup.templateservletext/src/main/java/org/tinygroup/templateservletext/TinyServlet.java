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

    public static final String MACROLIBRARYEXTNAME = "component";
    
    public static final String RESOURCE_SRC = "src/main/resources";

    public static final String REQUEST = "req";

    public static final String RESPONSE = "res";

    public static final String CONTENT_TYPE = "default.contentType";

    public static final String DEFAULT_CONTENT_TYPE = "text/html";

    public static final String DEFAULT_OUTPUT_ENCODING = "ISO-8859-1";

    private static final Logger logger = LoggerFactory
            .getLogger(TinyServlet.class);

    private static TemplateEngine engine;

    private ResourceLoader resourceLoader;

    private static String defaultContentType;

    private String templateextname = null;
    
    private String layoutextname = null;
    
    private String macrolibraryextname = null;
    
    private String resourcepath = null;
    public void init( ServletConfig config )
            throws ServletException
    {
        super.init( config );

        initTiny( config );

    }


    protected void initTiny( ServletConfig config )
            throws ServletException
    {
        try
        {
        	logger.logMessage(LogLevel.INFO,
                     "TinyServlet init start ...");
            engineInit(config.getInitParameter("resourceLoaderPath"),config.getInitParameter("function"),config.getInitParameter("i18n"));          
            defaultContentType = DEFAULT_CONTENT_TYPE;
            logger.logMessage(LogLevel.INFO,
                    "TinyServlet init end");
        }
        catch( Exception e )
        {
        	logger.logMessage(LogLevel.ERROR,
                    "Error initializing Tiny:"+e);
            throw new ServletException("Error initializing Tiny: " + e, e);
        }
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException
    {
        doRequest(request, response);
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException
    {
        doRequest(request, response);
    }

    protected void doRequest(HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException
    {
        TemplateContext context = null;
        try
        {

            context = createContext( request, response );

            setContentType( request, response );

            String path = handleRequest( request, response, context );

            if ( path == null )
            {
                return;
            }

            mergeTemplate( path, context, response );
        }
        catch (Exception e)
        {
            error( request, response, e);
        }
        finally
        {

            requestCleanup( request, response, context );
        }
    }

    protected void requestCleanup( HttpServletRequest request, HttpServletResponse response, TemplateContext context )
    {
    }


    protected void mergeTemplate(String path, TemplateContext context, HttpServletResponse response )
            throws TemplateException, IOException {
        // ASSUMPTION: response.setContentType() has been called.
    	logger.logMessage(LogLevel.INFO,
                "Template "+"["+resourcepath+"/"+path+"]"+" merge start...");
        String encoding = response.getCharacterEncoding();
        engine.setEncode(encoding);
        try {
            checkResource(getExtFileName(path));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if(isPagelet(path)){
            engine.renderTemplateWithOutLayout(getExtFileName(path),context,response.getOutputStream());
        }else{
            engine.renderTemplate(getExtFileName(path),context,response.getOutputStream());
        }
        logger.logMessage(LogLevel.INFO,
                "Template merge end");

    }

    protected void setContentType(HttpServletRequest request,
                                  HttpServletResponse response)
    {
        String contentType = TinyServlet.defaultContentType;
        int index = contentType.lastIndexOf(';') + 1;
        if (index <= 0 || (index < contentType.length() &&
                contentType.indexOf("charset", index) == -1))
        {
            // Append the character encoding which we'd like to use.
            String encoding = chooseCharacterEncoding(request);
            //RuntimeSingleton.debug("Chose output encoding of '" +
            //                       encoding + '\'');
            if (!DEFAULT_OUTPUT_ENCODING.equalsIgnoreCase(encoding))
            {
                contentType += "; charset=" + encoding;
            }
        }
        response.setContentType(contentType);
        //RuntimeSingleton.debug("Response Content-Type set to '" +
        //                       contentType + '\'');
    }

    protected String chooseCharacterEncoding(HttpServletRequest request)
    {
        return "";
    }

    protected TemplateContext createContext(HttpServletRequest request, HttpServletResponse response )
    {


        TemplateContext context = new TemplateContextDefault();

        context.put( REQUEST,  request );
        context.put( RESPONSE, response );

        return context;
    }


    protected String handleRequest( HttpServletRequest request, HttpServletResponse response, TemplateContext ctx )
            throws Exception
    {

        String t =  handleRequest( ctx );

        if (t == null)
        {
            throw new Exception ("handleRequest(Context) returned null - no template selected!" );
        }

        return t;
    }

    protected String handleRequest(TemplateContext ctx )
            throws Exception
    {
        throw new Exception ("You must override TinyServlet.handleRequest( Context) "
                + " or TinyServlet.handleRequest( HttpServletRequest, "
                + " HttpServletResponse, Context)" );
    }

    protected void error(HttpServletRequest request, HttpServletResponse response, Exception cause )
            throws ServletException, IOException
    {
        StringBuffer html = new StringBuffer();
        html.append("<html>");
        html.append("<title>Error</title>");
        html.append("<body bgcolor=\"#ffffff\">");
        html.append("<h2>TinyServlet: Error processing the template</h2>");
        html.append("<pre>");
        String why = cause.getMessage();
        if (why != null && why.trim().length() > 0)
        {
            html.append(why);
            html.append("<br>");
        }

        StringWriter sw = new StringWriter();
        cause.printStackTrace( new PrintWriter( sw ) );

        html.append( sw.toString()  );
        html.append("</pre>");
        html.append("</body>");
        html.append("</html>");
        response.getOutputStream().print( html.toString() );
    }

    public boolean checkResource(String viewpath) throws Exception {
        String path = getExtFileName(viewpath);
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
            String name = path.substring(0,path.lastIndexOf("."));
            return name+".page";
        }else if(StringUtils.endsWith(path,"page")){
            return path;
        }
        return path+".page";

    }
    
    private void engineInit(String resource, String functionpath,String i18npath){
    	logger.logMessage(LogLevel.INFO,
                "TinyTemplateEngine init start..");
    	engine =engine==null? new TemplateEngineDefault():engine;
    	logger.logMessage(LogLevel.INFO,
                "ResourceLoader init start..");
    	String[] resourceloader = resource.split(",");

    	for(String rl : resourceloader){
    		String[] rls = rl.split(":");
    		if("templateextname".equals(rls[0])&&rls.length>1){
    			templateextname = (!"".equals(rls[1])&&rls[1]!=null)?rls[1]:TEMPLATE_EXT_NAME;
    		}
    		if("layoutextname".equals(rls[0])&&rls.length>1){
    			layoutextname = (!"".equals(rls[1])&&rls[1]!=null)?rls[1]:LAYOUT_EXT_NAME;
    		}
    		if("macrolibraryextname".equals(rls[0])&&rls.length>1){
    			macrolibraryextname = (!"".equals(rls[1])&&rls[1]!=null)?rls[1]:MACROLIBRARYEXTNAME;
    		}
    		if("resource".equals(rls[0])&&rls.length>1){
    			resourcepath = (!"".equals(rls[1])&&rls[1]!=null)?rls[1]:RESOURCE_SRC;
    		}
    		
    	}
    	templateextname = templateextname == null?TEMPLATE_EXT_NAME:templateextname;
    	layoutextname = layoutextname == null?LAYOUT_EXT_NAME:layoutextname;
    	macrolibraryextname = macrolibraryextname == null?MACROLIBRARYEXTNAME:macrolibraryextname;
    	resourcepath = resourcepath == null?RESOURCE_SRC:resourcepath;
    	logger.logMessage(LogLevel.INFO,
                "ResourceLoader templateextname: "+templateextname);
    	logger.logMessage(LogLevel.INFO,
                "ResourceLoader layoutextname: "+layoutextname);
    	logger.logMessage(LogLevel.INFO,
                "ResourceLoader macrolibraryextname: "+macrolibraryextname);
    	logger.logMessage(LogLevel.INFO,
                "ResourceLoader resourcepath: "+resourcepath);
    	resourceLoader = new FileObjectResourceLoader(templateextname,layoutextname,macrolibraryextname,resourcepath);
    	 engine.addResourceLoader(resourceLoader);
    	 logger.logMessage(LogLevel.INFO,
                 "Add ResourceLoader...");
    	 logger.logMessage(LogLevel.INFO,
                 "ResourceLoader init end");
    	logger.logMessage(LogLevel.INFO,
                "TemplateFunction init start..");
    	 String[] functionlist = functionpath.split(",");
         for(String name:functionlist){
             try {
            	 logger.logMessage(LogLevel.INFO,
 		                "Init templateFunction "+"["+name+"]");
				engine.addTemplateFunction((TemplateFunction)Class.forName(name).newInstance());
				logger.logMessage(LogLevel.INFO,
 		                "Add templateFunction "+"["+name+"]");
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				logger.logMessage(LogLevel.ERROR,
	                    "Could not instance TemplateFunction class for URL [{0}]", e, name);				
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				logger.logMessage(LogLevel.ERROR,
	                    "The TemplateFunction class [{0}] witch wants to be instance has a private constructed function ", e, name);	
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				logger.logMessage(LogLevel.ERROR,
	                    "Could not find TemplateFunction class for URL [{0}]", e, name);	
			}
             
         }
         logger.logMessage(LogLevel.INFO,
                 "TemplateFunction init end");
         
         if(!"".equals(i18npath)&&i18npath!=null){
        	 logger.logMessage(LogLevel.INFO,
                     "I18nVisitor init start..");
        	 try {
        		 logger.logMessage(LogLevel.INFO,
 		                "Init I18nVisitor "+"["+i18npath+"]");
				engine.setI18nVisitor((I18nVisitor)Class.forName(i18npath).newInstance());
				logger.logMessage(LogLevel.INFO,
 		                "Add I18nVisitor "+"["+i18npath+"]");
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				logger.logMessage(LogLevel.ERROR,
	                    "Could not instance I18nVisitor class for URL [{0}]", e, i18npath);				
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				logger.logMessage(LogLevel.ERROR,
	                    "The I18nVisitor class [{0}] witch wants to be instance has a private constructed function ", e, i18npath);	
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
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
    
    private void initMacro(){
    	logger.logMessage(LogLevel.INFO,
                "MacroLibrary init start");
		for(ResourceLoader resourceloader : engine.getResourceLoaderList()){
			if(resourceloader instanceof FileObjectResourceLoader){
				registerMacroFile(resourceloader);
			}
		}
		logger.logMessage(LogLevel.INFO,
                "MacroLibrary init end");
	}

	private void registerMacroFile(ResourceLoader rl){
		FileObjectResourceLoader resourceloader= (FileObjectResourceLoader)rl;
		FileObject file = resourceloader.getRootFileObject();
		resolveFileDir(file);
		
	}
	
	private void resolveFileDir(FileObject file){
		if(file.isFolder()){
			for(FileObject f:file.getChildren()){
				resolveFileDir(f);
			}
		}else{
			String name = file.getFileName();
			int n = name.lastIndexOf(".");
			String fileext = name.substring(n+1);
			String[] parentpath = resourcepath.split("/");
			String parent = parentpath[parentpath.length-1];
			if(macrolibraryextname.equals(fileext)){
				try {
					String path =getFilePath(file,parent).substring(1);
					engine.registerMacroLibrary(path);
					logger.logMessage(LogLevel.INFO,
			                "ADD macroLibrary file ["+path+"]");
				} catch (TemplateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private String getFilePath(FileObject file, String parent){
		if(!parent.equals(file.getFileName())){
			return getFilePath(file.getParent(), parent)+"/"+file.getFileName();
		}
		return "";

	}
}
