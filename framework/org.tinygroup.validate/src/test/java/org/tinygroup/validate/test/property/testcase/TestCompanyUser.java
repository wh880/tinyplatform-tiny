package org.tinygroup.validate.test.property.testcase;

import junit.framework.TestCase;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.tinytestutil.AbstractTestUtil;
import org.tinygroup.validate.ValidateResult;
import org.tinygroup.validate.ValidatorManager;
import org.tinygroup.validate.XmlValidatorManager;
import org.tinygroup.validate.impl.ValidateResultImpl;
import org.tinygroup.validate.test.property.CompanyUser;

public class TestCompanyUser extends TestCase {
	protected ValidatorManager validatorManager;

	private void init() {
		AbstractTestUtil.init(null, true);
	}

	protected void setUp() throws Exception {
		super.setUp();
		init();
		validatorManager = BeanContainerFactory.getBeanContainer(
				this.getClass().getClassLoader()).getBean(
				XmlValidatorManager.VALIDATOR_MANAGER_BEAN_NAME);
	}
	public void testCompanyUserSucess() {
		ValidateResult result = new ValidateResultImpl();
		validatorManager.validate("", getSucessUser(), result);
		if(!result.hasError()){
			assertTrue(true);
		}else{
			assertFalse(false);
		}
	}
	
	private CompanyUser getSucessUser(){
		CompanyUser u = new CompanyUser();
		u.setName("1234567890123");
		return u;
	}
}
