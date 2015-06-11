package org.tinygroup.jdbctemplatedslsession.keygenerator;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.tinygroup.commons.tools.Assert;
import org.tinygroup.tinysqldsl.KeyGenerator;
import org.tinygroup.tinysqldsl.base.Column;
import org.tinygroup.tinysqldsl.base.InsertContext;
import org.tinygroup.tinysqldsl.insert.InsertBody;

/**
 * 
 * @author renhui
 *
 */
public class AppKeyGenerator implements KeyGenerator {
	
	private DataFieldMaxValueIncrementer incrementer;
	public AppKeyGenerator(DataFieldMaxValueIncrementer incrementer) {
		super();
		this.incrementer = incrementer;
	}

	@SuppressWarnings("unchecked")
	public <T> T generate(InsertContext insertContext) {
		Assert.assertNotNull(incrementer, "AppKeyGenerator require incrementer can not be null");
		return (T) incrementer.nextStringValue();
	}
	
	private Object appGeneratedKey(String keyName, InsertContext context) {
		Assert.assertNotNull(incrementer, "incrementer must not be null");
		Object value = incrementer.nextStringValue();
		Column primaryColumn = new Column(context.getTable(), keyName);
		context.addValues(primaryColumn.value(value));
		InsertBody insertBody = context.createInsert();
		return value;
	}

}
