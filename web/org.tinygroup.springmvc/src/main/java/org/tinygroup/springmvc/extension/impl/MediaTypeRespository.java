package org.tinygroup.springmvc.extension.impl;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.tinygroup.springmvc.http.MediaType;
import org.tinygroup.springmvc.util.LinkedMultiValueMap;
import org.tinygroup.springmvc.util.MultiValueMap;

/**
 * mediatype注册器
 * @author renhui
 *
 */
public class MediaTypeRespository {

    private final MultiValueMap<String, MediaType>     mediaTypes     = new LinkedMultiValueMap<String, MediaType>(
                                                                          64);
    private final ConcurrentHashMap<MediaType, String> fileExtensions = new ConcurrentHashMap<MediaType, String>();

    public MediaTypeRespository() {
        fileExtensions.put(MediaType.TEXT_HTML, "shtm");
    }


    void register(String fileExtension, MediaType mediaType) {
        List<MediaType> mts = this.mediaTypes.get(fileExtension);
        if (mts == null || !mts.contains(mediaType)) {
            this.mediaTypes.add(fileExtension, mediaType);
        }
        if (MediaType.TEXT_HTML == mediaType) {
            return;
        }
        fileExtensions.put(mediaType, fileExtension);
    }

    public String getExtension(MediaType mediaType) {
        return fileExtensions.get(mediaType);
    }

    public List<MediaType> getContentTypes(String fileExtension) {
        return this.mediaTypes.get(fileExtension);
    }
}
