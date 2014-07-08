/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
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
 */
package org.tinygroup.dbf;

import java.awt.BufferCapabilities.FlipContents;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Position;

/**
 * Created by wcg on 2014/7/7.
 */
public class FoxproDBaseWriter extends DbfWriter {

    public static final int HEADER_LENGTH = 32;
    public static final int FIELD_LENGTH = 32;
    public static final int NAME_LENGTH = 10;
    public static final int RECORD_COUNT_POSITION = 3;
    public static final int RECORD_COUNT_LENGTH = 4;
    public static final int HEADER_LENGTH_POSITION = 7;
    public static final int HEADER_LENGTH_LENGTH = 2;
    public static final int RECORD_LENGTH_POSITION = 9;
    public static final int RECORD_LENGTH_LENGTH = 2;
    public static final int FIELD_LENGTH_POSITON = 16;
    public static final int FIELD_DECIMAL_POSITON = 17;
    public static final int DISPLACEMENT_POSITION = 12;
    public static final int DISPLACEMENT_LENGTH = 4;
    public static final int FIELD_FLAG_POSITION = 18;
    public static final int START_YEAR = 1900;
    public static final int YEAR_POS = 10000;
    public static final int MONTO_POS = 100;
    private int l = 0;
    private void writeByteArray(Field f) throws IOException {
    	byte[] bytearr = f.getName().getBytes(getEncode());
    	byte[] dest = new byte[NAME_LENGTH];
    	int destl = bytearr.length>NAME_LENGTH?NAME_LENGTH:bytearr.length;
    	System.arraycopy(bytearr, 0, dest, 0, destl);
    	bodybuffer.write(dest);
    	bodybuffer.write(0);
    	bodybuffer.write((byte)f.getType());
    	bodybuffer.write(Util.getByteFromInt(f.getDisplacement(), 4));
    	bodybuffer.write(Util.getByteFromInt(f.getLength(), 1));
    	bodybuffer.write(Util.getByteFromInt(f.getDecimal(), 1));
    	bodybuffer.write(new byte[14]);
    }
    
	@Override
	public void writeFields(List<Field> flist) throws IOException {
		this.setFields(flist);
		for(Field f:flist) {
			writeByteArray(f);
		}
		bodybuffer.write(13);
	}
	@Override
	public void writeHeaders() throws IOException {
	
		headbuffer.write(0X30);
		headbuffer.write(new byte[]{ //年月日
				0x72,0x07,0x07
		});
		headbuffer.write(Util.getByteFromInt(getPostion(), RECORD_COUNT_LENGTH)); //4
		headbuffer.write(Util.getByteFromInt(32+32* fields.size()+1, HEADER_LENGTH_LENGTH));//2
		headbuffer.write(Util.getByteFromInt(39, RECORD_LENGTH_LENGTH)); //2
		headbuffer.write(new byte[20]);
		
		
	}
	@Override
	public void writeData(String... args) throws UnsupportedEncodingException, IOException {
		next();
		for(int i = 0;i<args.length;i++) {
			Field f = fields.get(i);
			byte[] fillbank = new byte[f.getLength()];
			for(int j = 0;j<f.getLength();j++) fillbank[j] = 0x20;
			bodybuffer.write(fillbank);
			bodybuffer.write(args[i].getBytes(getEncode()));
		}
	}
}
