package org.tinygroup.imda.defaultvalue;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.tinygroup.commons.i18n.LocaleUtil;
import org.tinygroup.commons.tools.ObjectUtil;
import org.tinygroup.imda.GetDefaultValue;
import org.tinygroup.springutil.TypeConverterUtil;
import org.tinygroup.support.BeanSupport;
import org.tinygroup.weblayer.webcontext.parser.propertyedit.CustomDateRegistrar;

/**
 * 
 * 功能说明:获取默认时间值
 * <p>

 * 开发人员: renhui <br>
 * 开发时间: 2014-1-21 <br>
 * 功能描述: 写明作用，调用方式，使用场景，以及特殊情况<br>
 */
public class GetDefaultDateValue extends BeanSupport implements GetDefaultValue<Date>{

	private String format;
	private Locale locale;
	private TimeZone timeZone;

	public GetDefaultDateValue() {
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setLocale(String locale) {
		this.locale = LocaleUtil.parseLocale(locale);
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = TimeZone.getTimeZone(timeZone);
	}
	
	

	protected void init() throws Exception {
		if (format == null) {
			format = "yyyy-MM-dd";
		}
		if (locale == null) {
			locale = LocaleUtil.getContext().getLocale();
		}
		if (timeZone == null) {
			timeZone = TimeZone.getDefault();
		}
		CustomDateRegistrar registrar=new CustomDateRegistrar();
		registrar.setFormat(format);
		registrar.setLocaleObject(locale);
		registrar.setTimeZoneObject(timeZone);
		TypeConverterUtil.registerCustomEditors(registrar);
	}

	public Date getDefaultValue(Object value, String defaultValue) {
		if (ObjectUtil.isEmptyObject(value)) {
			if (defaultValue != null) {
				if (defaultValue.trim().equals("")
						|| defaultValue.equals("currentDate")) {
					return new Date();
				} else {
					value = defaultValue;
				}
			}else{
				return null;
			}
		}
		return (Date) TypeConverterUtil.typeConverter(value, Date.class);
	}

}
