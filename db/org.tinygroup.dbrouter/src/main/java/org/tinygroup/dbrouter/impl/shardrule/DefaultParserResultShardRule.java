package org.tinygroup.dbrouter.impl.shardrule;

import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.dbrouter.config.Partition;
import org.tinygroup.dbrouter.config.Shard;
import org.tinygroup.dbrouter.parser.SqlParserResult;
import org.tinygroup.dbrouter.parser.base.ColumnInfo;
import org.tinygroup.dbrouter.parser.base.Condition;
import static org.tinygroup.logger.LogLevel.DEBUG;


/**
 * 具有sql解析结果对象的分片规则
 * @author renhui
 *
 */
public class DefaultParserResultShardRule extends AbstractParserResultShardRule {

	@XStreamAlias("expression")
	@XStreamAsAttribute
	private String expression;


	@Override
	protected boolean internalMatch(Partition partition, Shard shard,
			SqlParserResult parserResult) {
		if(parserResult.isDDL()){//如果是ddl语句，直接返回true，匹配此分片
			return true;
		}
        List<Condition> conditions=parserResult.getConditions();
        if(CollectionUtil.isEmpty(conditions)){//没有参数信息的sql，也认为匹配此分片
        	return true;
        }
        //TODO 组装参数map,作为groovy函数的参数，根据expression创建groovy编写的class代码
		if(StringUtil.isBlank(expression)) return false;//表达式为空直接返回false
		Map conditionMap = new CaseInsensitiveMap();//忽略大小写
		for (Condition condition : conditions) {
			ColumnInfo columnInfo=condition.getColumn();
			List<Object> values = condition.getValues();
			Object value =  (values!=null && values.size()==1)?values.get(0):values;
			conditionMap.put(columnInfo.getName(), value);
		}
		try {
			return GroovyRuleEngine.eval(expression, conditionMap);
		} catch (Exception e) {
//			throw new DbrouterRuntimeException(e);
			LOGGER.logMessage(DEBUG,"解析表达式失败,规则不匹配",e);//解析失败统一返回false
			return false;
		}
	}


	public String getExpression() {
		return expression;
	}


	public void setExpression(String expression) {
		this.expression = expression;
	}
	

}
