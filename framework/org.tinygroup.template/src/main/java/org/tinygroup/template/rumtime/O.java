package org.tinygroup.template.rumtime;

import org.tinygroup.template.TemplateException;
import org.tinygroup.template.rumtime.impl.operator.*;
import org.tinygroup.template.rumtime.impl.convert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作符工具类
 * 之所以起一个字母，是为了生成的代码短一些
 * Created by luoguo on 2014/6/5.
 */
public final class O {
    private static Map<String, Operator> operationMap = new HashMap<String, Operator>();
    private static Map<String, Converter> converterMap = new HashMap<String, Converter>();
    private static List<String> numberTypeOrder = new ArrayList<String>();

    static {
        numberTypeOrder.add("Byte");
        numberTypeOrder.add("Character");
        numberTypeOrder.add("Integer");
        numberTypeOrder.add("Float");
        numberTypeOrder.add("Double");
        numberTypeOrder.add("BigDecimal");

        addConverter(new ByteCharacter());
        addConverter(new ByteInteger());
        addConverter(new ByteFloat());
        addConverter(new ByteDouble());
        addConverter(new ByteBigDecimal());
        addConverter(new CharacterInteger());
        addConverter(new CharacterFloat());
        addConverter(new CharacterDouble());
        addConverter(new CharacterBigDecimal());
        addConverter(new IntegerFloat());
        addConverter(new IntegerDouble());
        addConverter(new IntegerBigDecimal());
        addConverter(new FloatDouble());
        addConverter(new FloatBigDecimal());
        addConverter(new DoubleBigDecimal());
        addOperator(new AddOperator());
        addOperator(new SubtractOperator());
        addOperator(new MultiplyOperator());
        addOperator(new DevideOperator());
        addOperator(new XorOperator());
        addOperator(new AdOperator());
        addOperator(new OrOperator());
        addOperator(new ModOperator());
    }
    public static Object convert(Object object,String sourceType,String destType){
        Converter converter=converterMap.get(sourceType+destType);
        return converter.convert(object);
    }
    public static int compare(String type1,String type2){
        int index1 = numberTypeOrder.indexOf(type1);
        int index2 = numberTypeOrder.indexOf(type2);
        if(index1==index2){
            return 0;
        }
        if(index1>index2){
            return 1;
        }else{
            return -1;
        }
    }
    public static String getResultType(String type1, String type2) {
        int index1 = numberTypeOrder.indexOf(type1);
        int index2 = numberTypeOrder.indexOf(type2);
        return index1 < index2 ? type2 : type1;
    }

    public static boolean isNumberic(String typeName) {
        return numberTypeOrder.contains(typeName);
    }

    public static void addConverter(Converter converter) {
        converterMap.put(converter.getClass().getName(), converter);
    }

    public static void addOperator(Operator operator) {
        operationMap.put(operator.getOperation(), operator);
    }

    public static Object e(String op, Object... parameters) throws TemplateException {
        Operator operator = operationMap.get(op);
        if (operator == null) {
            throw new TemplateException();
        }
        return operator.operation(parameters);
    }

    public static void main(String[] args) throws TemplateException {
        System.out.println(O.e("+",1,2));
        System.out.println(O.e("-",1,2));
        System.out.println(O.e("*",1,2));
        System.out.println(O.e("/",12,2));
        System.out.println(O.e("%",12,2));
        System.out.println(O.e("^",3,2));
        System.out.println(O.e("&",1,2));
        System.out.println(O.e("|",1,2));
        System.out.println(O.e("+","1","2"));
        System.out.println(O.e("+","1",2));
        System.out.println(~12);
    }
 /*   public static boolean isType(Class type, Object... parameters) {
        for (Object obj : parameters) {
            if (!(type.isInstance(obj))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isType(Class typeA, Class typeB, Object... parameters) {
        boolean isType = true;
        for (Object obj : parameters) {
            if (!typeA.isInstance(obj) || !typeB.isInstance(obj)) {
                return false;
            }
        }
        return false;
    }*/
}
