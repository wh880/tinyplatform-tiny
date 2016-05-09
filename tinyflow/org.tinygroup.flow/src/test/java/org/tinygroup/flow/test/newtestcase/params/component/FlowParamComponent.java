package org.tinygroup.flow.test.newtestcase.params.component;

import org.tinygroup.context.Context;
import org.tinygroup.flow.ComponentInterface;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

public class FlowParamComponent implements ComponentInterface {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FlowParamComponent.class);
	private Boolean obool;
	private boolean sbool;
	private Character ochar;
	private char schar;
	
	private int sint;
	private Integer oint;
	private Double odouble;
	private double sdouble;
	private Short oshort;
	private short sshort;
	private Long olong;
	private long slong;
	private Float ofloat;
	private float sfloat;

	public void execute(Context context) {
		LOGGER.logMessage(LogLevel.DEBUG, "流程参数传递测试");
		context.put("sint", sint);
		context.put("oint", oint);
		context.put("odouble", odouble);
		context.put("sdouble", sdouble);
		context.put("oshort", oshort);
		context.put("sshort", sshort);
		context.put("olong", olong);
		context.put("slong", slong);
		context.put("ofloat", ofloat);
		context.put("sfloat", sfloat);
		context.put("obool", obool);
		context.put("sbool", sbool);
		context.put("ochar", ochar);
		context.put("schar", schar);
		LOGGER.logMessage(LogLevel.DEBUG, "流程参数传递测试");
	}

	public int getSint() {
		return sint;
	}

	public void setSint(int sint) {
		this.sint = sint;
	}

	public Integer getOint() {
		return oint;
	}

	public void setOint(Integer oint) {
		this.oint = oint;
	}

	public Boolean getObool() {
		return obool;
	}

	public void setObool(Boolean obool) {
		this.obool = obool;
	}

	public boolean isSbool() {
		return sbool;
	}

	public void setSbool(boolean sbool) {
		this.sbool = sbool;
	}

	public Character getOchar() {
		return ochar;
	}

	public void setOchar(Character ochar) {
		this.ochar = ochar;
	}

	public char getSchar() {
		return schar;
	}

	public void setSchar(char schar) {
		this.schar = schar;
	}

	public Double getOdouble() {
		return odouble;
	}

	public void setOdouble(Double odouble) {
		this.odouble = odouble;
	}

	public double getSdouble() {
		return sdouble;
	}

	public void setSdouble(double sdouble) {
		this.sdouble = sdouble;
	}

	public Short getOshort() {
		return oshort;
	}

	public void setOshort(Short oshort) {
		this.oshort = oshort;
	}

	public short getSshort() {
		return sshort;
	}

	public void setSshort(short sshort) {
		this.sshort = sshort;
	}

	public Long getOlong() {
		return olong;
	}

	public void setOlong(Long olong) {
		this.olong = olong;
	}

	public long getSlong() {
		return slong;
	}

	public void setSlong(long slong) {
		this.slong = slong;
	}

	public Float getOfloat() {
		return ofloat;
	}

	public void setOfloat(Float ofloat) {
		this.ofloat = ofloat;
	}

	public float getSfloat() {
		return sfloat;
	}

	public void setSfloat(float sfloat) {
		this.sfloat = sfloat;
	}

}
