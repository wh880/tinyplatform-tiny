package org.tinygroup.dbf;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
/**
 * Created by wcg on 2014/7/7.
 */
public interface Writer {
	public void writeFields(List<Field> fieldlist) throws IOException;
	public void writeRecord(String... args) throws UnsupportedEncodingException, IOException,NullPointerException;
	public void save() throws IOException;
}
