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
package org.tinygroup.databasebuinstaller.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.tinygroup.database.initdata.InitDataProcessor;

/**
 * 
 * 功能说明:数据库初始化安装处理器 

 * 开发人员: renhui <br>
 * 开发时间: 2013-8-15 <br>
 * <br>
 */
public class InitDataInstallProcessor extends AbstractInstallProcessor {
	
	private InitDataProcessor initDataProcessor;
	
	
	public InitDataProcessor getInitDataProcessor() {
		return initDataProcessor;
	}



	public void setInitDataProcessor(InitDataProcessor initDataProcessor) {
		this.initDataProcessor = initDataProcessor;
	}


	
	protected List<String> getDealSqls(Connection con) throws SQLException {
		List<String> sqls=new ArrayList<String>();
		sqls.addAll(initDataProcessor.getDeinitSql(language));
		sqls.addAll(initDataProcessor.getInitSql(language));
		return sqls;
	}


}