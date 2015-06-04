package org.tinygroup.springmvc.extension.impl;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.springmvc.extension.MalformedMediaTypeException;
import org.tinygroup.springmvc.extension.MediaTypeRegistrar;
import org.tinygroup.springmvc.http.MediaType;

/**
 * mediaType注册工厂
 * @author renhui
 *
 */
public class MediaTypeRegistrarSupport implements MediaTypeRegistrar {
	private MediaTypeRespository mediaTypeRespository;

	public void setMediaTypeRespository(
			MediaTypeRespository mediaTypeRespository) {
		this.mediaTypeRespository = mediaTypeRespository;
	}

	public void setMediaTypes(String fileExtension, String mediaTypes) {
		if (StringUtils.isBlank(mediaTypes)) {
			return;
		}
		int pos = mediaTypes.indexOf(MediaTypeRegistrar.MEDIA_TYPE_SPLIT);
		try {
			if (pos == -1) {
				MediaType mediaType = MediaType.valueOf(mediaTypes);
				mediaTypeRespository.register(fileExtension, mediaType);
			} else {
				String[] mediaTypez = mediaTypes
						.split(MediaTypeRegistrar.MEDIA_TYPE_SPLIT);
				for (String mtStr : mediaTypez) {
					if (StringUtils.isNotBlank(mtStr)) {
						MediaType mediaType = MediaType.valueOf(mtStr);
						mediaTypeRespository.register(fileExtension, mediaType);
					}
				}
			}
		} catch (Throwable e) {
			throw new MalformedMediaTypeException("FileExtension ["
					+ fileExtension + "]--mediaTypes [" + mediaTypes
					+ "] are invalid!", e);
		}

	}
}
