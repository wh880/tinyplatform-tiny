package org.tinygroup.database.sequence.impl;

import org.tinygroup.database.config.sequence.ValueConfig;
import org.tinygroup.database.config.sequence.SeqCacheConfig;
import org.tinygroup.database.config.sequence.Sequence;
import org.tinygroup.database.sequence.SequenceSqlProcessor;

public class OracleSequenceSqlProcessor implements SequenceSqlProcessor {

	public String getCreateSql(Sequence sequence) {
		StringBuffer seqBuffer = new StringBuffer();
		seqBuffer.append("CREATE SEQUENCE ").append(sequence.getName())
				.append(" INCREMENT BY ").append(sequence.getIncrementBy()).append(" START WITH ").append(sequence.getStartWith());
		ValueConfig maxValueConfig=sequence.getValueConfig();
		if(maxValueConfig==null){
			seqBuffer.append(" NOMAXVALUE ");
		}else{
			seqBuffer.append(" MINVALUE ").append(maxValueConfig.getMinValue()).append(" MAXVALUE ").append(maxValueConfig.getMaxValue());
		}
		if(sequence.isCycle()){
			seqBuffer.append(" CYCLE ");
		}else{
			seqBuffer.append(" NOCYCLE ");
		}
		SeqCacheConfig cacheConfig=sequence.getSeqCacheConfig();
		if(cacheConfig==null||!cacheConfig.isCache()){
			seqBuffer.append(" NOCACHE ");
		}else{
			seqBuffer.append(" CACHE ").append(cacheConfig.getNumber());
		}
		if(sequence.isOrder()){
			seqBuffer.append(" ORDER ");
		}else {
			seqBuffer.append(" NOORDER ");
		}
		return seqBuffer.toString();
	}

	public String getDropSql(Sequence sequence) {
		return "DROP SEQUENCE "+sequence.getName();
	}

}
