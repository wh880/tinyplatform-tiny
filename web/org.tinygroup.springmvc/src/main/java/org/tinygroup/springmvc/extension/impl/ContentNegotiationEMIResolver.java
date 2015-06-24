package org.tinygroup.springmvc.extension.impl;

import org.springframework.util.StringUtils;
import org.springframework.web.util.UrlPathHelper;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.springmvc.extension.FileExtensionResolver;
import org.tinygroup.springmvc.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

/**
 * 内容协商解析对象,先根据请求路径的后缀查找请求的扩展名,再根据请求参数format对应的值,最后根据请求头Accept的mediaType
 * 
 * @author renhui
 * 
 */
public class ContentNegotiationEMIResolver extends
		AbstractCachableExtensionMappingInstanceResolver {
	private static final Logger logger = LoggerFactory
			.getLogger(ContentNegotiationEMIResolver.class);

	private final FileExtensionResolver<HttpServletRequest> uriFileExtensionResolver = new RequestURIFileExtensionResolver();
	private static final String ACCEPT_HEADER = "Accept";

	private boolean favorParameter = true;

	private String parameterName = "format";

	private boolean ignoreAcceptHeader = false;

	private MediaTypeRespository mediaTypeRespository;

	public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
		super.setUrlPathHelper(urlPathHelper);
		if (uriFileExtensionResolver instanceof RequestURIFileExtensionResolver) {
			((RequestURIFileExtensionResolver) uriFileExtensionResolver)
					.setUrlPathHelper(urlPathHelper);
		}
	}

	public MediaTypeRespository getMediaTypeRespository() {
		return mediaTypeRespository;
	}

	public void setMediaTypeRespository(
			MediaTypeRespository mediaTypeRespository) {
		this.mediaTypeRespository = mediaTypeRespository;
	}

	public boolean isIgnoreAcceptHeader() {
		return ignoreAcceptHeader;
	}

	public void setIgnoreAcceptHeader(boolean ignoreAcceptHeader) {
		this.ignoreAcceptHeader = ignoreAcceptHeader;
	}

	public boolean isFavorParameter() {
		return favorParameter;
	}

	public void setFavorParameter(boolean favorParameter) {
		this.favorParameter = favorParameter;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	@Override
	protected String doGetExtension(HttpServletRequest request) {
		String extension = null;
		// request url
		List<String> exts = uriFileExtensionResolver
				.resolveFileExtensions(request);
		if (!CollectionUtil.isEmpty(exts) && supportExtension(exts.get(0))) {
			return exts.get(0);
		}

		// request parameter
		if (this.favorParameter) {
			if (request.getParameter(this.parameterName) != null) {
				String parameterValue = request
						.getParameter(this.parameterName);
				extension = getMediaTypeFromParameter(parameterValue);
				if (extension != null) {
					logger.logMessage(
							LogLevel.DEBUG,
							"Requested file extension is {0} (based on parameter {1}={2})",
							extension, parameterName, parameterValue);

					if (supportExtension(extension)) {
						return extension;
					}
					logger.logMessage(
							LogLevel.WARN,
							"Requested file extension is {0} (based on parameter {1}={2}). but is invalid, can not find the right extensionMappingInstance!",
							extension, parameterName, parameterValue);
				}
			}
		}

		// request header => mediaType => extension
		if (!this.ignoreAcceptHeader) {
			String acceptHeader = request.getHeader(ACCEPT_HEADER);
			if (StringUtils.hasText(acceptHeader)) {
				List<MediaType> mediaTypes = MediaType
						.parseMediaTypes(acceptHeader);
				MediaType.sortByQualityValue(mediaTypes);
				logger.logMessage(
						LogLevel.DEBUG,
						"Requested media types are {0} (based on Accept header)",
						mediaTypes);
				for (MediaType mediaType : mediaTypes) {
					String ext = mediaTypeRespository.getExtension(mediaType);
					if (supportExtension(ext)) {
						logger.logMessage(
								LogLevel.INFO,
								"the file extension is [{0}] of the first MediaType({1}) of  MediaTypes {2}",
								ext, mediaType, mediaTypes);
						return ext;
					}
				}

			}
		}
		return extension;
	}

	private String getMediaTypeFromParameter(String parameterValue) {
		return parameterValue.toLowerCase(Locale.ENGLISH);
	}

	public List<MediaType> getContentTypes(HttpServletRequest request) {
		return mediaTypeRespository
				.getContentTypes(get(request).getExtension());
	}

}
