package org.tinygroup.tinyspider;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by luoguo on 14-3-3.
 */
public class UrlUtils {
    public static String getUrl(String baseUrl, String absPath) throws URISyntaxException {
        URI baseUri = new URI(baseUrl);
        URI absUri = baseUri.resolve(absPath);
        return absUri.toString();
    }
}
