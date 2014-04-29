package org.tinygroup.dbf;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * Created by luoguo on 2014/4/26.
 */
public interface Reader {

    void setFileChannel(FileChannel fileChannel);

    String getEncode();

    Header getHeader();

    List<Field> getFields();

    boolean isRecordRemoved();

    void moveBeforeFirst() throws IOException;

    void absolute(int position) throws IOException;

    void close() throws IOException;

    boolean hasNext();

    void next() throws IOException;
}
