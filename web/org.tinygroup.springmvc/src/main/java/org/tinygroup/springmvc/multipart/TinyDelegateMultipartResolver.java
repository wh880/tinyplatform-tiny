package org.tinygroup.springmvc.multipart;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartException;
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

}
