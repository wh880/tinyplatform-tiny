package org.tinygroup.jdbctemplatedslsession.execute;

import org.tinygroup.jdbctemplatedslsession.Score;
import org.tinygroup.jdbctemplatedslsession.SimpleDslSession;
import org.tinygroup.tinysqldsl.Delete;
import org.tinygroup.tinysqldsl.DslSession;
import org.tinygroup.tinysqldsl.Insert;
import org.tinygroup.tinysqldsl.Select;
import org.tinygroup.tinysqldsl.expression.JdbcParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.tinygroup.jdbctemplatedslsession.ScoreTable.TSCORE;
import static org.tinygroup.tinysqldsl.Delete.delete;
import static org.tinygroup.tinysqldsl.Insert.insertInto;
import static org.tinygroup.tinysqldsl.Select.selectFrom;

/**
 * Created by wangwy11342 on 2016/2/17.
 */
public class GeneratorTest extends BaseTest{
    public void testConfiguration(){
        DslSession session = new SimpleDslSession(dataSource);
        Insert scoreInsert = insertInto(TSCORE).values(TSCORE.NAME.value("悠悠然然"),
                TSCORE.SCORE.value(98), TSCORE.COURSE.value("shuxue"));
        Score score=session.executeAndReturnObject(scoreInsert,Score.class);
        assertNotNull(score.getId());
        assertEquals(32, score.getId().length());//uuid去符号后长度为32位
        assertEquals("悠悠然然",score.getName());
        assertEquals("shuxue",score.getCourse());

        Select select = selectFrom(TSCORE);
        Score qscore = session.fetchOneResult(select,Score.class);
        assertEquals(qscore.getId(),score.getId());
        assertEquals(qscore.getName(),score.getName());
        assertEquals(qscore.getScore(),score.getScore());


    }

    public void testConfigurationBatch(){
        DslSession session = new SimpleDslSession(dataSource);
       Insert scoreInsert = insertInto(TSCORE).values(TSCORE.NAME.value("悠悠然然"),
                TSCORE.SCORE.value(new JdbcParameter()), TSCORE.COURSE.value(new JdbcParameter()));
        List<Map<String,Object>> params = new ArrayList<Map<String, Object>>();
        for(int i=0;i<11;i++){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("score",80+i);
            map.put("course","course"+i);
            params.add(map);
        }
        int[] results = session.batchInsert(scoreInsert,params,false);
        assertEquals(11,results.length);

        Select select = selectFrom(TSCORE);
        List<Score> qscores = session.fetchList(select,Score.class);
        assertEquals(11,qscores.size());
        for(Score qscore:qscores){
            assertEquals(32, qscore.getId().length());
        }
    }

    protected void setUp() throws Exception {
        super.setUp();
        Delete delete = delete(TSCORE);
        DslSession session = new SimpleDslSession(dataSource);
        session.execute(delete);
    }
}
