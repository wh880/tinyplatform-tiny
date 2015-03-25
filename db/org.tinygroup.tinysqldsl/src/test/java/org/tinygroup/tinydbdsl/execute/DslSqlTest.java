package org.tinygroup.tinydbdsl.execute;

import static org.tinygroup.tinydbdsl.ComplexSelect.union;
import static org.tinygroup.tinydbdsl.CustomTable.CUSTOM;
import static org.tinygroup.tinydbdsl.Delete.delete;
import static org.tinygroup.tinydbdsl.Insert.insertInto;
import static org.tinygroup.tinydbdsl.ScoreTable.TSCORE;
import static org.tinygroup.tinydbdsl.Select.select;
import static org.tinygroup.tinydbdsl.Select.selectFrom;
import static org.tinygroup.tinydbdsl.Update.update;
import static org.tinygroup.tinydbdsl.select.Join.leftJoin;

import java.sql.SQLException;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.tinygroup.tinydbdsl.ComplexSelect;
import org.tinygroup.tinydbdsl.Custom;
import org.tinygroup.tinydbdsl.CustomScore;
import org.tinygroup.tinydbdsl.Delete;
import org.tinygroup.tinydbdsl.DslSqlExecute;
import org.tinygroup.tinydbdsl.DslSqlQuery;
import org.tinygroup.tinydbdsl.Insert;
import org.tinygroup.tinydbdsl.Select;
import org.tinygroup.tinydbdsl.Update;
import org.tinygroup.tinydbdsl.impl.SimpleDslSqlExecute;
import org.tinygroup.tinydbdsl.impl.SimpleDslSqlQuery;

public class DslSqlTest extends BaseTest {

	public void testDsl() throws SQLException {
		
		Delete delete = delete(CUSTOM);
		DslSqlExecute customExecute = new SimpleDslSqlExecute(dataSource, delete);
		int affect = customExecute.execute();
		
		delete = delete(TSCORE);
		customExecute = new SimpleDslSqlExecute(dataSource, delete);
		affect = customExecute.execute();
		
		Insert customInsert = insertInto(CUSTOM).values(
				CUSTOM.ID.value("10001"), CUSTOM.NAME.value("悠悠然然"),
				CUSTOM.AGE.value(22));
		 customExecute = new SimpleDslSqlExecute(dataSource,
				customInsert);
		 affect = customExecute.execute();
		assertEquals(1, affect);

		Insert scoreInsert = insertInto(TSCORE).values(
				TSCORE.ID.value("10002"), TSCORE.NAME.value("悠悠然然"),
				TSCORE.SCORE.value(98), TSCORE.COURSE.value("shuxue"));
		DslSqlExecute scoreExecute = new SimpleDslSqlExecute(dataSource,
				scoreInsert);
		affect = scoreExecute.execute();
		assertEquals(1, affect);

		Select select = selectFrom(CUSTOM).where(CUSTOM.NAME.like("悠"));
		DslSqlQuery customQuery = new SimpleDslSqlQuery(dataSource, select);
		Custom custom = customQuery.fetchOneResult(Custom.class);
		assertEquals("悠悠然然", custom.getName());

		select = select(CUSTOM.NAME, CUSTOM.AGE, TSCORE.SCORE, TSCORE.COURSE)
				.from(CUSTOM).join(
						leftJoin(TSCORE, CUSTOM.NAME.eq(TSCORE.NAME)));
		DslSqlQuery query = new SimpleDslSqlQuery(dataSource, select);
		CustomScore customScore = query.fetchOneResult(CustomScore.class);
		assertEquals("悠悠然然", customScore.getName());
		assertEquals(98, customScore.getScore());
		assertEquals(22, customScore.getAge());
		assertEquals("shuxue", customScore.getCourse());
		
		SqlRowSet rowSet=query.fetchResult();
		if(rowSet.next()){
			assertEquals("悠悠然然", rowSet.getString("name"));
			assertEquals(98, rowSet.getInt("score"));
			assertEquals(22, rowSet.getInt("age"));
			assertEquals("shuxue", rowSet.getString("course"));
		}
		
		Select select1=select(CUSTOM.NAME).from(CUSTOM);
		Select select2=select(TSCORE.NAME).from(TSCORE);
		ComplexSelect complexSelect=union(select1,select2);
		query=new SimpleDslSqlQuery(dataSource, complexSelect);
		rowSet=query.fetchResult();
		if(rowSet.next()){
			assertEquals("悠悠然然", rowSet.getString("name"));
		}
		

		Update update = update(CUSTOM).set(CUSTOM.NAME.value("flank"),
				CUSTOM.AGE.value(30)).where(CUSTOM.NAME.eq("悠悠然然"));
		customExecute = new SimpleDslSqlExecute(dataSource, update);
		affect = customExecute.execute();
		assertEquals(1, affect);

		delete = delete(CUSTOM).where(CUSTOM.NAME.eq("flank"));
		customExecute = new SimpleDslSqlExecute(dataSource, delete);
		affect = customExecute.execute();
		assertEquals(1, affect);
		delete = delete(TSCORE).where(TSCORE.NAME.eq("悠悠然然"));
		customExecute = new SimpleDslSqlExecute(dataSource, delete);
		affect = customExecute.execute();
		assertEquals(1, affect);
	}
}
