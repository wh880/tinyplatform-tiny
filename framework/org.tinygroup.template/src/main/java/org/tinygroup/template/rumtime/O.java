package org.tinygroup.template.rumtime;

import org.tinygroup.template.TemplateException;
import org.tinygroup.template.rumtime.convert.*;
import org.tinygroup.template.rumtime.operator.*;

import java.math.BigDecimal;
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
        numberTypeOrder.add(Byte.class.getName());
        numberTypeOrder.add(Character.class.getName());
        numberTypeOrder.add(Integer.class.getName());
        numberTypeOrder.add(Float.class.getName());
        numberTypeOrder.add(Double.class.getName());
        numberTypeOrder.add(BigDecimal.class.getName());

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
        //数学操作
        addOperator(new AddOperator());
        addOperator(new SubtractOperator());
        addOperator(new MultiplyOperator());
        addOperator(new DevideOperator());
        addOperator(new XorOperator());
        addOperator(new AdOperator());
        addOperator(new OrOperator());
        addOperator(new ModOperator());
        //一元操作符
        addOperator(new LeftAddOperator());
        addOperator(new LeftSubtractOperator());
        addOperator(new LeftPlusPlusOperator());
        addOperator(new LeftSubtractSubtractOperator());
        addOperator(new LeftLiteralOperator());
        addOperator(new LeftNotOperator());
        //逻辑比较符
        addOperator(new EqualsOperator());
        addOperator(new NotEqualsOperator());
        addOperator(new LessEqualsOperator());
        addOperator(new LessOperator());
        addOperator(new BigOperator());
        addOperator(new BigEqualsOperator());
    }

    public static Object convert(Object object, String sourceType, String destType) {
        Converter converter = converterMap.get(sourceType + destType);
        return converter.convert(object);
    }

    public static int compare(String type1, String type2) {
        int index1 = numberTypeOrder.indexOf(type1);
        int index2 = numberTypeOrder.indexOf(type2);
        if (index1 == index2) {
            return 0;
        }
        if (index1 > index2) {
            return 1;
        } else {
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
        converterMap.put(converter.getType(), converter);
    }

    public static void addOperator(Operator operator) {
        operationMap.put(operator.getOperation(), operator);
    }

    public static Object e(String op, Object... parameters) throws TemplateException {
        Operator operator = operationMap.get(op);
        if (operator == null) {
            throw new TemplateException("找不对对应于：" + op + "的处理器。");
        }
        return operator.operation(parameters);
    }

}
