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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wcg on 2014/7/7.
 */
public class DbfWriterTest {
    static String[] files = {"aaa1"};

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException {
        for (String file : files) {
            writetoFile(file);
        }
    }

    public static void writetoFile(String fileName) throws IOException, InstantiationException, IllegalAccessException {
    		DbfWriter w = DbfWriter.generate("G:\\"+fileName+".DBF", "utf-8");
    		List<Field> list = new ArrayList<Field>();
    		Field f = new Field();
    		f.setDecimal(1);
    		f.setType('N');
    		f.setFlag((byte)1);
    		f.setDisplacement(1);
    		f.setName("aaa");
    		f.setLength(19);
    		list.add(f);
    		
    		Field f1= new Field();
    		f1.setDecimal(1);
    		f1.setType('N');
    		f1.setFlag((byte)1);
    		f1.setName("aaa1");
    		f1.setLength(19);
    		list.add(f1);
    		
    		w.writeFields(list);
    		
    		
    		w.writeData("11","22");
    		w.writeData("33","44");
    		
    		w.write();
    		

    }
}
