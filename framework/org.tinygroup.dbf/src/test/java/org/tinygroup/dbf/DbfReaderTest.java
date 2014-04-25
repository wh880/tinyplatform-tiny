package org.tinygroup.dbf;

import java.io.IOException;

/**
 * Created by luoguo on 2014/4/25.
 */
public class DbfReaderTest {
    static String[] files = {"BESTIMATE20140401", "BHDQUOTE20140401", "BMIRATE20140401", "BONDDT20140401", "BONDDT20140403",
            "CFPROFILE20140401", "CHIBOHDQUOTE20140401", "GIBORATE20140401", "INTERESTRATE20140401", "NAV_CUR20140401",
            "NAV20140401", "OFPROFILE20140401", "RATEOFFEX20140401", "REPO20140401"};

    public static void main(String[] args) throws IOException {
        for (String file : files) {
            printFile(file);
        }
    }

    public static void printFile(String fileName) throws IOException {
        DbfReader dbfReader = new DbfReader("E:\\20140401\\" + fileName + ".DBF");
        for (DbfField field : dbfReader.getDbfFields()) {
            System.out.printf("name:%s %s(%d,%d)\n", field.getName(), field.getType(), field.getLength(), field.getDecimal());
        }
        System.out.println();
        for (int i = 0; i < dbfReader.getDbfHeader().getRecordCount(); i++) {
            dbfReader.readRecord();
            for (DbfField field : dbfReader.getDbfFields()) {
                System.out.printf("%" + field.getLength() + "s", field.getStringValue());
            }
            System.out.println();
        }
        dbfReader.close();

    }
}
