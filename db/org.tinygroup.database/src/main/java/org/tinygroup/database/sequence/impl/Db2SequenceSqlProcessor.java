package org.tinygroup.database.sequence.impl;

import java.sql.Connection;

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.database.config.sequence.SeqCacheConfig;
import org.tinygroup.database.config.sequence.Sequence;
import org.tinygroup.database.config.sequence.ValueConfig;

/**
 * db2 sequence sql处理
 * 
 * @author renhui
 * 
 */
public class Db2SequenceSqlProcessor extends AbstractSequenceSqlProcessor {

	public String getCreateSql(Sequence sequence) {
		StringBuffer seqBuffer = new StringBuffer();
		seqBuffer.append("CREATE SEQUENCE ").append(sequence.getName());
		String dataType=sequence.getDataType();
        if(StringUtil.isBlank(dataType)){
        	seqBuffer.append(" AS INTEGER ");
        }else {
        	seqBuffer.append(String.format(" AS %s ", dataType));
		}
        seqBuffer.append(" START WITH ")
				.append(sequence.getStartWith()).append(" INCREMENT BY ")
				.append(sequence.getIncrementBy());
		ValueConfig valueConfig = sequence.getValueConfig();
		if (valueConfig == null) {
			seqBuffer.append(" NO MINVALUE NO MAXVALUE  ");
		} else {
			Integer minValue=valueConfig.getMinValue();
			if(minValue==null){
				seqBuffer.append("NO MINVALUE ");
			}else{
				seqBuffer.append(" MINVALUE ").append(valueConfig.getMinValue());
			}
			Integer maxValue=valueConfig.getMaxValue();
			if(maxValue==null){
				seqBuffer.append(" NO MAXVALUE ");
			}else{
				seqBuffer.append(" MAXVALUE ").append(valueConfig.getMaxValue());
			}
		}
		if (sequence.isCycle()) {
			seqBuffer.append(" CYCLE ");
		} else {
			seqBuffer.append(" NOCYCLE ");
		}
		SeqCacheConfig cacheConfig = sequence.getSeqCacheConfig();
		if (cacheConfig == null || !cacheConfig.isCache()) {
			seqBuffer.append(" NOCACHE ");
		} else {
			seqBuffer.append(" CACHE ").append(cacheConfig.getNumber());
		}
		if(sequence.isOrder()){
			seqBuffer.append(" ORDER ");
		}else {
			seqBuffer.append(" NO ORDER ");
		}
		return seqBuffer.toString();
	}

	public String checkSequenceExist(Sequence sequence,Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

}
