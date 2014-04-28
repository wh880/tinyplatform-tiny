package org.tinygroup.dbf;

import java.io.IOException;

/**
 * Created by luoguo on 2014/4/25.
 */
public class DbfReaderTest {
    static String[] files = {"OFPROFILE20140401"};

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException {
        for (String file : files) {
            printFile(file);
        }
    }

    public static void printFile(String fileName) throws IOException, InstantiationException, IllegalAccessException {
        Reader dbfReader = DbfReader.parse("E:\\20140401\\" + fileName + ".DBF");
        for (Field field : dbfReader.getFields()) {
            System.out.printf("name:%s %s(%d,%d)\n", field.getName(), field.getType(), field.getLength(), field.getDecimal());
        }
        System.out.println();
        for (int i = 0; i < dbfReader.getHeader().getRecordCount(); i++) {
            dbfReader.next();
            for (Field field : dbfReader.getFields()) {
                if(field.getName().startsWith("ISSTAT")){
                    System.out.print("");
                }
                System.out.printf("%" + field.getLength() + "s", field.getStringValue());
            }
            System.out.println();
        }
        dbfReader.close();

    }
}
