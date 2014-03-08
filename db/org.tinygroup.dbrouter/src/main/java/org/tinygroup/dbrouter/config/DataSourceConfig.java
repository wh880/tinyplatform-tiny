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
package org.tinygroup.dbrouter.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 数据源定义
 *
 * @author luoguo
 */
@XStreamAlias("data-source-config")
public class DataSourceConfig {
    /**
     * ID唯一确定一个数据源，引用时通过指定ID来引用
     */
    @XStreamAsAttribute
    String id;
    /**
     * 驱动类名
     */
    @XStreamAsAttribute
    String driver;
    /**
     * 连接URL
     */
    @XStreamAsAttribute
    String url;
    /**
     * 用户名
     */
    @XStreamAlias("user-name")
    @XStreamAsAttribute
    String userName;
    /**
     * 密码
     */
    @XStreamAsAttribute
    String password;
    /**
     * 测试SQL
     */
    @XStreamAlias("test-sql")
    @XStreamAsAttribute
    String testSql;

    public DataSourceConfig() {

    }

    public DataSourceConfig(String id, String driver, String url, String userName, String password) {
        this.id = id;
        this.driver = driver;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTestSql() {
        return testSql;
    }

    public void setTestSql(String testSql) {
        this.testSql = testSql;
    }

    public DataSourceConfigBean getDataSourceConfigBean(){
    	DataSourceConfigBean bean=new DataSourceConfigBean();
    	bean.setUrl(url);
    	bean.setPassword(password);
    	bean.setDriver(driver);
    	bean.setUserName(userName);
    	return bean;
    }
}
