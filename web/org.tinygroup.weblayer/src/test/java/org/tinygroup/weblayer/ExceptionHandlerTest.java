package org.tinygroup.weblayer;

import java.io.IOException;

import javax.servlet.ServletException;

import junit.framework.TestCase;

import org.tinygroup.weblayer.exceptionhandler.WebExceptionHandler;
import org.tinygroup.weblayer.exceptionhandler.WebExceptionHandlerManager;
import org.tinygroup.weblayer.exceptionhandler.impl.WebExceptionHandlerManagerImpl;
import org.tinygroup.weblayer.impl.WebContextImpl;

public class ExceptionHandlerTest extends TestCase {

	public void handlerTest() throws Exception {

		WebExceptionHandlerManager webExceptionHandlerManager = new WebExceptionHandlerManagerImpl();
         
		final State state=new State(true);
		
		webExceptionHandlerManager.setDefaultHandler(new WebExceptionHandler() {
			
			public void handler(Throwable throwable, WebContext webContext)
					throws IOException, ServletException {
				state.setDefault(true);
				System.out.println("defaultHandler be treated");
			}
		});
		
		
		webExceptionHandlerManager.addHandler(IllegalArgumentException.class.getName(), new WebExceptionHandler() {
			
			public void handler(Throwable throwable, WebContext webContext)
					throws IOException, ServletException {
				state.setDefault(false);
				System.out.println("IllegalArgumentException be treated");
			}
		});
		
        webExceptionHandlerManager.addHandler(ArrayIndexOutOfBoundsException.class.getName(), new WebExceptionHandler() {
			
			public void handler(Throwable throwable, WebContext webContext)
					throws IOException, ServletException {
				state.setDefault(false);
				System.out.println("ArrayIndexOutOfBoundsException be treated");
			}
		});
        
        webExceptionHandlerManager.addHandler(ClassNotFoundException.class.getName(), new WebExceptionHandler() {
			
			public void handler(Throwable throwable, WebContext webContext)
					throws IOException, ServletException {
				state.setDefault(false);
				System.out.println("ClassNotFoundException be treated");
			}
		});
		
        WebContext webContext=new WebContextImpl();
		webExceptionHandlerManager.handler(new IllegalArgumentException("sdsff"), webContext);
		
		assertEquals(false, state.isDefault());
		
		webExceptionHandlerManager.handler(new ArrayIndexOutOfBoundsException("sfdsf"), webContext);
		
		assertEquals(false, state.isDefault());
		
		webExceptionHandlerManager.handler(new TestException("sfdsf"), webContext);
		
		assertEquals(false, state.isDefault());
		
		webExceptionHandlerManager.handler(new IllegalAccessException("sfdsf"), webContext);
		
		assertEquals(true, state.isDefault());

	}

	class State {
		private boolean isDefault;

		public State(boolean isDefault) {
			super();
			this.isDefault = isDefault;
		}

		public boolean isDefault() {
			return isDefault;
		}

		public void setDefault(boolean isDefault) {
			this.isDefault = isDefault;
		}

	}
	
	class TestException extends ClassNotFoundException{

		public TestException() {
			super();
		}

		public TestException(String s, Throwable ex) {
			super(s, ex);
		}

		public TestException(String s) {
			super(s);
		}
		
	}

}
