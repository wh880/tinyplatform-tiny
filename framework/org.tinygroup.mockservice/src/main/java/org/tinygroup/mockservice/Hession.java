package org.tinygroup.mockservice;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.caucho.hessian.io.SerializerFactory;

public class Hession {
	public static byte[] serialize(Object obj) throws IOException{  
	    if(obj==null) throw new NullPointerException();  
	    SerializerFactory serializerFactory = new SerializerFactory();
		serializerFactory.addFactory(new BigDecimalSerializerFactory());
	    ByteArrayOutputStream os = new ByteArrayOutputStream();  
	    HessianOutput ho = new HessianOutput(os);  
	    ho.setSerializerFactory(serializerFactory);
	    ho.writeObject(obj);  
	    return os.toByteArray();  
	}  
	  
	public static Object deserialize(byte[] by) throws IOException{  
	    if(by==null) throw new NullPointerException();
	    SerializerFactory serializerFactory = new SerializerFactory();
		serializerFactory.addFactory(new BigDecimalSerializerFactory());
	    ByteArrayInputStream is = new ByteArrayInputStream(by);  
	    HessianInput hi = new HessianInput(is);  
	    hi.setSerializerFactory(serializerFactory);
	    return hi.readObject();  
	}  
}
