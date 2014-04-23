/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.ini;

/**
 * 值对，用于存储数据
 * Created by luoguo on 14-3-28.
 */
public class ValuePair {
    String key;
    String value;
    String comment;

    public String getComment() {
        if (comment == null) {
            return "";
        }
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ValuePair(String comment) {
        this.setComment(comment);
    }

    public ValuePair(String key, String value) {
        this.setKey(key);
        this.setValue(value);
    }

    public ValuePair(String key, String value, String comment) {
        this(key, value);
        this.setComment(comment);
    }

    public ValuePair() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public <T> T getValue(Class<T> tClass) {
        if (tClass.equals(int.class) || tClass.equals(Integer.class)) {
            return (T) Integer.valueOf(value);
        }
        if (tClass.equals(float.class) || tClass.equals(Float.class)) {
            return (T) Float.valueOf(value);
        }
        if (tClass.equals(long.class) || tClass.equals(Long.class)) {
            return (T) Long.valueOf(value);
        }
        if (tClass.equals(boolean.class) || tClass.equals(Boolean.class)) {
            return (T) Boolean.valueOf(value);
        }
        if (tClass.equals(String.class)) {
            return (T) value;
        }
        return null;
    }


    public void setValue(String value) {
        this.value = value;
    }
}
