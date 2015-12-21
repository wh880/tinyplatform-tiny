package org.tinygroup.springmvc.multipart;

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
		final ParserWebContext parserWebContext = WebContextUtil.findWebContext(
				request, ParserWebContext.class);
		final UploadService uploadService = parserWebContext.getUploadService();
		if (parserWebContext != null && uploadService != null
				&& uploadService.isMultipartContent(request)) {
			return new DefaultMultipartHttpServletRequest(request) {
				@Override
				protected void initializeMultipart() {
					MultipartParsingResult parsingResult = parseRequest(
							request, uploadService,parserWebContext);
					setMultipartFiles(parsingResult.getMultipartFiles());
					setMultipartParameters(parsingResult
							.getMultipartParameters());
				}
			};
		}
		return super.resolveMultipart(request);
	}

	protected MultipartParsingResult parseRequest(HttpServletRequest request,
			UploadService uploadService, ParserWebContext parserWebContext) throws MultipartException {
		String encoding = determineEncoding(request);
		List<FileItem> fileItems = Arrays.asList(uploadService.getFileItems());
		return parseFileItems(fileItems, encoding,parserWebContext);
	}

	protected MultipartParsingResult parseFileItems(List<FileItem> fileItems,
			String encoding, ParserWebContext parserWebContext) {
		MultiValueMap<String, MultipartFile> multipartFiles = new LinkedMultiValueMap<String, MultipartFile>();
		Map<String, String[]> multipartParameters = new HashMap<String, String[]>();

		// Extract multipart files and multipart parameters.
		for (FileItem fileItem : fileItems) {
			if (fileItem.isFormField()) {
				if(!multipartParameters.containsKey(fileItem.getFieldName())){
					Object value=parserWebContext.get(fileItem.getFieldName());
					if(value==null||value instanceof String){
						multipartParameters.put(fileItem.getFieldName(), new String[]{(String)value});
					}else if(value instanceof String[]){
						multipartParameters.put(fileItem.getFieldName(), (String[])value);
					}
				}
			} else {//暂时不支持filter文件过滤功能
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
