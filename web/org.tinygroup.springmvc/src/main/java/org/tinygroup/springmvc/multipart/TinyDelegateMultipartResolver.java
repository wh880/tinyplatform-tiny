package org.tinygroup.springmvc.multipart;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.tinygroup.weblayer.webcontext.parser.ParserWebContext;
import org.tinygroup.weblayer.webcontext.parser.upload.UploadService;
import org.tinygroup.weblayer.webcontext.util.WebContextUtil;

/**
 * 由于在weblayer中parserfilter可能已经进行文件上传处理，此处将复用之前的处理方式
 * 
 * @author renhui
 *
 */
public class TinyDelegateMultipartResolver extends CommonsMultipartResolver {

	@Override
	public MultipartHttpServletRequest resolveMultipart(
			final HttpServletRequest request) throws MultipartException {
		ParserWebContext parserWebContext = WebContextUtil.findWebContext(
				request, ParserWebContext.class);
		final UploadService uploadService = parserWebContext.getUploadService();
		if (parserWebContext != null && uploadService != null
				&& uploadService.isMultipartContent(request)) {
			return new DefaultMultipartHttpServletRequest(request) {
				@Override
				protected void initializeMultipart() {
					MultipartParsingResult parsingResult = parseRequest(
							request, uploadService);
					setMultipartFiles(parsingResult.getMultipartFiles());
					setMultipartParameters(parsingResult
							.getMultipartParameters());
				}
			};
		}
		return super.resolveMultipart(request);
	}

	protected MultipartParsingResult parseRequest(HttpServletRequest request,
			UploadService uploadService) throws MultipartException {
		String encoding = determineEncoding(request);
		List<FileItem> fileItems = Arrays.asList(uploadService.getFileItems());
		return parseFileItems(fileItems, encoding);
	}

	protected MultipartParsingResult parseFileItems(List<FileItem> fileItems,
			String encoding) {
		MultiValueMap<String, MultipartFile> multipartFiles = new LinkedMultiValueMap<String, MultipartFile>();
		Map<String, String[]> multipartParameters = new HashMap<String, String[]>();

		// Extract multipart files and multipart parameters.
		for (FileItem fileItem : fileItems) {
			if (fileItem.isFormField()) {
				String value;
				String partEncoding = determineEncoding(
						fileItem.getContentType(), encoding);
				if (partEncoding != null) {
					try {
						value = fileItem.getString(partEncoding);
					} catch (UnsupportedEncodingException ex) {
						if (logger.isWarnEnabled()) {
							logger.warn("Could not decode multipart item '"
									+ fileItem.getFieldName()
									+ "' with encoding '" + partEncoding
									+ "': using platform default");
						}
						value = fileItem.getString();
					}
				} else {
					value = fileItem.getString();
				}
				String[] curParam = multipartParameters.get(fileItem
						.getFieldName());
				if (curParam == null) {
					// simple form field
					multipartParameters.put(fileItem.getFieldName(),
							new String[] { value });
				} else {
					// array of simple form fields
					String[] newParam = StringUtils.addStringToArray(curParam,
							value);
					multipartParameters.put(fileItem.getFieldName(), newParam);
				}
			} else {
				// multipart file field
				DefaultTinyMultipartFile file = new DefaultTinyMultipartFile(
						fileItem);
				multipartFiles.add(file.getName(), file);
				if (logger.isDebugEnabled()) {
					logger.debug("Found multipart file [" + file.getName()
							+ "] of size " + file.getSize()
							+ " bytes with original filename ["
							+ file.getOriginalFilename() + "], stored "
							+ file.getStorageDescription());
				}
			}
		}
		return new MultipartParsingResult(multipartFiles, multipartParameters);
	}

	protected String determineEncoding(String contentTypeHeader,
			String defaultEncoding) {
		if (!StringUtils.hasText(contentTypeHeader)) {
			return defaultEncoding;
		}
		MediaType contentType = MediaType.parseMediaType(contentTypeHeader);
		Charset charset = contentType.getCharSet();
		return charset != null ? charset.name() : defaultEncoding;
	}

	public void cleanupMultipart(MultipartHttpServletRequest request) {
		ParserWebContext parserWebContext = WebContextUtil.findWebContext(
				request, ParserWebContext.class);
		final UploadService uploadService = parserWebContext.getUploadService();
		if (parserWebContext != null && uploadService != null
				&& !uploadService.isTemporary()) {//非临时目录直接返回
			return;
		}else{
			super.cleanupMultipart(request);
		}
	}
}
