package org.tinygroup.tinydbdsl;

import org.tinygroup.tinydbdsl.base.Column;
import org.tinygroup.tinydbdsl.base.Table;

/**
 * Created by luoguo on 2015/3/11.
 */
public class ScoreTable extends Table {
    public static final ScoreTable TSCORE = new ScoreTable();
    public final Column ID =  new Column(this,"id");
    public final Column NAME =  new Column(this,"name");
    public final Column SCORE=  new Column(this,"score");
    public final Column COURSE=  new Column(this,"course");
    private ScoreTable() {
        super("score");
    }
}
