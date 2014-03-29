package org.tinygroup.ini.impl;

import org.tinygroup.ini.IniOperator;
import org.tinygroup.ini.Section;
import org.tinygroup.ini.Sections;
import org.tinygroup.ini.ValuePair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 目前不支持转义符;
 * Created by luoguo on 14-3-29.
 */
public class IniOperatorDefault implements IniOperator {
    private Sections sections = null;
    private String commentChar = ";";
    private static final Pattern SECTION_PATTERN = Pattern.compile("([\\[])(.*)([\\]])");
    private static final Pattern VALUE_PAIR_PATTERN = Pattern.compile("(.*)=([^;]*)");

    public static void main(String[] args) {
        Matcher matcher = VALUE_PAIR_PATTERN.matcher("aa=bb\\;aa");
        if (matcher.find()) {
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
        }
    }

    public IniOperatorDefault() {
    }

    public IniOperatorDefault(Sections sections) {
        setSections(sections);
    }

    public void setSections(Sections sections) {
        this.sections = sections;

    }

    public Sections getSections() {
        return sections;
    }

    public void read(InputStream inputStream, String charset) throws IOException {
        sections = new Sections();
        InputStreamReader reader = new InputStreamReader(inputStream, charset);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String string = bufferedReader.readLine();
        String sectionName = null;
        while (string != null) {
            string = string.trim();
            if (string.length() == 0 || string.startsWith(commentChar)) {
                //如果是注释
            } else if (string.startsWith("[")) {
                //如果是Section
                Matcher matcher = SECTION_PATTERN.matcher(string);
                if (matcher.find()) {
                    sectionName = matcher.group(2);
                }
            } else {
                Matcher matcher = VALUE_PAIR_PATTERN.matcher(string);
                if (matcher.find()) {
                    add(sectionName, matcher.group(1).trim(), matcher.group(2).trim());
                } else {
                    System.out.println("不符全规范的内容：" + string);
                }
            }
            string = bufferedReader.readLine();
        }
        reader.close();
    }


    public void write(OutputStream outputStream, String charset) throws IOException {
        if (sections != null) {
            for (Section section : sections.getSectionList()) {
                if (section.getName() != null) {
                    outputStream.write(String.format("[%s]\r\n", section.getName()).getBytes(charset));
                }
                for (ValuePair valuePair : section.getValuePairList()) {
                    outputStream.write(String.format("%s=%s\n", valuePair.getKey(), valuePair.getValue()).getBytes(charset));
                }
            }
        }
    }

    public Section getSection(String sectionName) {
        if (sections != null) {
            return sections.getSection(sectionName);
        }
        return null;
    }

    public void setCommentChar(char commentChar) {
        this.commentChar = commentChar + "";
    }

    public <T> void put(String sectionName, String key, T value) {
        Section section = checkSection(sectionName);
        section.set(key, value);
    }

    private Section checkSection(String sectionName) {
        if (sections == null) {
            sections = new Sections();
        }
        Section section = sections.getSection(sectionName);
        if (section == null) {
            section = new Section(sectionName);
            sections.addSection(section);
        }
        return section;
    }

    public <T> void add(String sectionName, String key, T value) {
        add(sectionName, new ValuePair(key, value.toString()));
    }

    public <T> T get(Class<T> tClass, String sectionName, String key, T defaultValue) {
        return null;
    }

    public String get(String sectionName, String key, String defaultValue) {
        String value = get(sectionName, key);
        if (value != null) {
            return value;
        }
        return defaultValue;
    }

    public String get(String sectionName, String key) {
        Section section = sections.getSection(sectionName);
        if (section != null) {
            return section.getValue(key);
        }
        return null;
    }

    public <T> List<T> getList(Class<T> tClass, String sectionName, String key) {
        List<T> list = new ArrayList<T>();
        Section section = getSection(sectionName);
        if (section != null) {
            for (ValuePair valuePair : section.getValuePairList()) {
                if (valuePair.getKey().equals(key)) {
                    list.add(valuePair.getValue(tClass));
                }
            }
        }
        return list;
    }


    public <T> T get(Class<T> tClass, String sectionName, String key) {
        Section section = sections.getSection(sectionName);
        if (section != null) {
            T value = section.getValue(tClass, key);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    public void add(String sectionName, ValuePair valuePair) {
        Section section = checkSection(sectionName);
        section.add(valuePair);
    }

    public void set(String sectionName, ValuePair valuePair) {

    }

    public void add(String sectionName, List<ValuePair> valuePairList) {
        Section section = checkSection(sectionName);
        section.getValuePairList().addAll(valuePairList);
    }

    public List<ValuePair> getValuePairList(String sectionName, String key) {
        Section section = sections.getSection(sectionName);
        if (section != null) {
            return section.getValuePairList(key);
        }
        return null;
    }

    public ValuePair getValuePair(String sectionName, String key) {
        Section section = sections.getSection(sectionName);
        if (section != null) {
            return section.getValuePair(key);
        }
        return null;
    }
}
