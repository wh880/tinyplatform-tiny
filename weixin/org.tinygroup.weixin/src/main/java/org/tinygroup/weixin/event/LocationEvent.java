package org.tinygroup.weixin.event;

import org.tinygroup.weixin.util.WeiXinEventMode;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 上报地理位置事件
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class LocationEvent extends CommonEvent{
	
	public LocationEvent(){
		super();
		setEvent(WeiXinEventMode.LOCATION.getEvent());
	}

	@XStreamAlias("Latitude")
	private double latitude;
	
	@XStreamAlias("Longitude")
	private double longitude;
	
	@XStreamAlias("Precision")
	private double precision;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getPrecision() {
		return precision;
	}

	public void setPrecision(double precision) {
		this.precision = precision;
	}
	
}
