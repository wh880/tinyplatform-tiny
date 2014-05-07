/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
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
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.convert.xsdjava;

import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.Driver;
import com.sun.tools.xjc.util.Util;

/**
 * 
 * 功能说明:对原生Driver进行重写 

 * 开发人员: renhui <br>
 * 开发时间: 2013-8-27 <br>
 * <br>
 */
public class DriverWrapper{

	 public static void main(final String[] args) throws Exception {
	        // use the platform default proxy if available.
	        // see sun.net.spi.DefaultProxySelector for details.
	        try {
	            System.setProperty("java.net.useSystemProxies","true");
	        } catch (SecurityException e) {
	            // failing to set this property isn't fatal
	        }

	        if( Util.getSystemProperty(Driver.class,"noThreadSwap")!=null )
                // for the ease of debugging
	            _main(args);

	        // run all the work in another thread so that the -Xss option
	        // will take effect when compiling a large schema. See
	        // http://developer.java.sun.com/developer/bugParade/bugs/4362291.html
	        final Throwable[] ex = new Throwable[1];

	        Thread th = new Thread() {
	            
	            public void run() {
	                try {
	                    _main(args);
	                } catch( Exception e ) {
	                    ex[0]=e;
	                }
	            }
	        };
	        th.start();
	        th.join();

	        if(ex[0]!=null) {
	            // re-throw
	            if( ex[0] instanceof Exception )
	                throw (Exception)ex[0];
	            else
	                throw (Error)ex[0];
	        }
	    }
	 private static void _main( String[] args ) throws Exception {
	        try {
	        	Driver.run( args, System.out, System.out );
	        } catch (BadCommandLineException e) {
	            // there was an error in the command line.
	            // print usage and abort.
	            if(e.getMessage()!=null) {
	                System.out.println(e.getMessage());
	                System.out.println();
	            }

	            Driver.usage(e.getOptions(),false);
	        }
	    }
}
