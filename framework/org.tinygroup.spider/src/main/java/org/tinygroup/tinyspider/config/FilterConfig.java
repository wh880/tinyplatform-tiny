package org.tinygroup.tinyspider.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.util.List;

/**
 * 过滤器配置
 * Created by luoguo on 2014/5/29.
 */
@XStreamAlias("filter")
public class FilterConfig {
    @XStreamAlias("include-attribute-with-values")
    List<Item> includeAttributeWithValues;
    @XStreamAlias("exclude-attribute-with-values")
    List<Item> excludeAttributeWithValues;
    @XStreamAlias("include-attributes")
    String includeAttributes;
    @XStreamAlias("exclude-attributes")
    String excludeAttributes;

    @XStreamAlias("include-texts")
    String includeTexts;
    @XStreamAlias("exclude-texts")
    String excludeTexts;
    @XStreamAlias("include-nodes")
    String includeNodes;
    @XStreamAlias("exclude-nodes")
    String excludeNodes;
    @XStreamAlias("include-by-nodes")
    String includeByNodes;
    @XStreamAlias("exclude-by-nodes")
    String excludeByNodes;
    @XStreamAlias("include-xor-nodes")
    String includeXorNodes;
    @XStreamAlias("include-xor-attributes")
    String includeXorAttributes;
    @XStreamAlias("node-name")
    @XStreamAsAttribute
    String nodeName;

    public List<Item> getIncludeAttributeWithValues() {
        return includeAttributeWithValues;
    }

    public void setIncludeAttributeWithValues(List<Item> includeAttributeWithValues) {
        this.includeAttributeWithValues = includeAttributeWithValues;
    }

    public List<Item> getExcludeAttributeWithValues() {
        return excludeAttributeWithValues;
    }

    public void setExcludeAttributeWithValues(List<Item> excludeAttributeWithValues) {
        this.excludeAttributeWithValues = excludeAttributeWithValues;
    }

    public String getIncludeAttributes() {
        return includeAttributes;
    }

    public void setIncludeAttributes(String includeAttributes) {
        this.includeAttributes = includeAttributes;
    }

    public String getExcludeAttributes() {
        return excludeAttributes;
    }

    public void setExcludeAttributes(String excludeAttributes) {
        this.excludeAttributes = excludeAttributes;
    }

    public String getIncludeTexts() {
        return includeTexts;
    }

    public void setIncludeTexts(String includeTexts) {
        this.includeTexts = includeTexts;
    }

    public String getExcludeTexts() {
        return excludeTexts;
    }

    public void setExcludeTexts(String excludeTexts) {
        this.excludeTexts = excludeTexts;
    }

    public String getIncludeNodes() {
        return includeNodes;
    }

    public void setIncludeNodes(String includeNodes) {
        this.includeNodes = includeNodes;
    }

    public String getExcludeNodes() {
        return excludeNodes;
    }

    public void setExcludeNodes(String excludeNodes) {
        this.excludeNodes = excludeNodes;
    }

    public String getIncludeByNodes() {
        return includeByNodes;
    }

    public void setIncludeByNodes(String includeByNodes) {
        this.includeByNodes = includeByNodes;
    }

    public String getExcludeByNodes() {
        return excludeByNodes;
    }

    public void setExcludeByNodes(String excludeByNodes) {
        this.excludeByNodes = excludeByNodes;
    }

    public String getIncludeXorNodes() {
        return includeXorNodes;
    }

    public void setIncludeXorNodes(String includeXorNodes) {
        this.includeXorNodes = includeXorNodes;
    }

    public String getIncludeXorAttributes() {
        return includeXorAttributes;
    }

    public void setIncludeXorAttributes(String includeXorAttributes) {
        this.includeXorAttributes = includeXorAttributes;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
