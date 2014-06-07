package org.tinygroup.template.resource;

import org.tinygroup.template.TemplateResource;

import java.util.Date;

/**
 * Created by luoguo on 2014/6/7.
 */
public class StringTemplateResource implements TemplateResource {
    private final long date = new Date().getTime();
    private final String path;
    private final String content;

    public StringTemplateResource(String path, String content) {
        this.path = path;
        this.content = content;
    }

    public StringTemplateResource(String content) {
        path = "/string/template/StingTemplateNo" + date;
        this.content = content;
    }

    @Override
    public long getTimestamp() {
        return date;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getContent() {
        return content;
    }
}
