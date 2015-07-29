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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlParseSerializableUtil {

	public static void write(Object object, Class cla, FileOutputStream ops) {
		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.processAnnotations(cla);
		OutputStreamWriter writer = null;
		try {
			writer = new OutputStreamWriter(ops, Charset.forName("UTF-8")); 
			xstream.toXML(object, writer);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if (writer != null) {
					writer.close();
				}
				if (ops != null) {
					ops.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void write2CDATA(Object object ,File file , Class<?>[] classes){
		write(initXStream(true), object, file, classes);
	}
	
	public static void write(Object object ,File file , Class<?>[] classes){
		write(initXStream(false), object, file, classes);
	}
	
	private static void write(XStream xstream ,Object object ,File file , Class<?>[] classes){
		xstream.autodetectAnnotations(true);
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.processAnnotations(classes);
		FileOutputStream ops = null;
		OutputStreamWriter writer = null;
		try {
			ops = new FileOutputStream(file);
			writer = new OutputStreamWriter(ops, Charset.forName("UTF-8"));
			xstream.toXML(object, writer);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if (writer != null) {
					writer.close();
				}
				if (ops != null) {
					ops.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void write2CDATA(Object object, Class cla, File file) {
		write(initXStream(true), object, cla, file);
	}
	
	public static void write(Object object, Class cla, File file) {
		write(initXStream(false), object, cla, file);
	}

	private static void write(XStream xstream ,Object object, Class cla, File file){
		xstream.autodetectAnnotations(true);
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.processAnnotations(cla);
		FileOutputStream ops = null;
		OutputStreamWriter writer = null;
		try {
			ops = new FileOutputStream(file);
			writer = new OutputStreamWriter(ops, Charset.forName("UTF-8")); 
			xstream.toXML(object, writer);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if (writer != null) {
					writer.close();
				}
				if (ops != null) {
					ops.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Object read(InputStream input , Class<?> cla){
		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.processAnnotations(cla);
		xstream.setClassLoader(cla.getClassLoader());
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(input, Charset.forName("UTF-8")); 
			Object obj = xstream.fromXML(reader);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if (reader != null) {
					reader.close();
				}
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static Object read(InputStream input ,ClassLoader loader , Class<?>[] classes){
		XStream xstream = new XStream(new DomDriver());
		xstream.autodetectAnnotations(true);
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.processAnnotations(classes);
		xstream.setClassLoader(loader);
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(input, Charset.forName("UTF-8")); 
			Object obj = xstream.fromXML(reader);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if (reader != null) {
					reader.close();
				}
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static Object read(File file ,ClassLoader loader , Class<?>[] classes){
		FileInputStream input = null;
		InputStreamReader reader = null;
		try {
			input = new FileInputStream(file);
			reader = new InputStreamReader(input, Charset.forName("UTF-8")); 
			return read(input, loader, classes);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if (reader != null) {
					reader.close();
				}
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static Object read(File file , Class<?> cla){
		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.processAnnotations(cla);
		xstream.setClassLoader(cla.getClassLoader());
		FileInputStream input = null;
		InputStreamReader reader = null;
		try {
			input = new FileInputStream(file);
			reader = new InputStreamReader(input, Charset.forName("UTF-8")); 
			if (input.available() != 0) {
				return xstream.fromXML(input);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if (reader != null) {
					reader.close();
				}
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static List<Object> read(List<File> files , Class cla) {
		List<Object> objects = new ArrayList<Object>();
		for(File f : files){
			objects.add(read(f , cla));		
		}
		return objects;
	}

	public static XStream initXStream(boolean isAddCDATA){   
		return new XStream();      
    }  
	
}
