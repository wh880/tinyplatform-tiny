package org.tinygroup.mockservice.test;
 
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
 
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
 
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.DateSerializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.util.IOUtils;
 
/**
 * Json序列化转换入口。配置到spring的配置文件中。
 * 
 *
 */
public class Test extends FastJsonHttpMessageConverter {
 
    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        OutputStream out = outputMessage.getBody();
        String text = JSON.toJSONString(obj, getSerializeConfig(), super.getFeatures());
        byte[] bytes = text.getBytes(super.getCharset());
        out.write(bytes);
    }
     
    // 设置特定的日期转换类
    public SerializeConfig getSerializeConfig() {
        SerializeConfig config = new SerializeConfig();
        config.put(java.util.Date.class, new CustomDateSerializer());
        config.put(java.sql.Date.class, new CustomDateSerializer());
        config.put(java.sql.Timestamp.class, new CustomDateSerializer());
        return config;
    }
     
    // 重写日期转换类，以便把日期数据输出成UNIX时间戳（单位是秒）
    static class CustomDateSerializer extends DateSerializer {
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
                throws IOException {
            SerializeWriter out = serializer.getWriter();
 
            if (object == null) {
                out.writeNull();
                return;
            }
 
            Date date = (Date) object;
 
            if (out.isEnabled(SerializerFeature.WriteDateUseDateFormat)) {
                DateFormat format = serializer.getDateFormat();
                if (format == null) {
                    format = new SimpleDateFormat(JSON.DEFFAULT_DATE_FORMAT);
                }
                String text = format.format(date);
                out.writeString(text);
                return;
            }
 
            if (out.isEnabled(SerializerFeature.WriteClassName)) {
                if (object.getClass() != fieldType) {
                    if (object.getClass() == java.util.Date.class) {
                        out.write("new Date(");
                        out.writeLongAndChar(((Date) object).getTime(), ')');
                    } else {
                        out.write('{');
                        out.writeFieldName(JSON.DEFAULT_TYPE_KEY);
                        serializer.write(object.getClass().getName());
                        out.writeFieldValue(',', "val", ((Date) object).getTime());
                        out.write('}');
                    }
                    return;
                }
            }
 
            long time = date.getTime();
            if (serializer.isEnabled(SerializerFeature.UseISO8601DateFormat)) {
                if (serializer.isEnabled(SerializerFeature.UseSingleQuotes)) {
                    out.append('\'');
                } else {
                    out.append('\"');
                }
 
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(time);
 
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);
                int millis = calendar.get(Calendar.MILLISECOND);
 
                char[] buf;
                if (millis != 0) {
                    buf = "0000-00-00T00:00:00.000".toCharArray();
                    IOUtils.getChars(millis, 23, buf);
                    IOUtils.getChars(second, 19, buf);
                    IOUtils.getChars(minute, 16, buf);
                    IOUtils.getChars(hour, 13, buf);
                    IOUtils.getChars(day, 10, buf);
                    IOUtils.getChars(month, 7, buf);
                    IOUtils.getChars(year, 4, buf);
 
                } else {
                    if (second == 0 && minute == 0 && hour == 0) {
                        buf = "0000-00-00".toCharArray();
                        IOUtils.getChars(day, 10, buf);
                        IOUtils.getChars(month, 7, buf);
                        IOUtils.getChars(year, 4, buf);
                    } else {
                        buf = "0000-00-00T00:00:00".toCharArray();
                        IOUtils.getChars(second, 19, buf);
                        IOUtils.getChars(minute, 16, buf);
                        IOUtils.getChars(hour, 13, buf);
                        IOUtils.getChars(day, 10, buf);
                        IOUtils.getChars(month, 7, buf);
                        IOUtils.getChars(year, 4, buf);
                    }
                }
 
                out.write(buf);
 
                int timeZone = calendar.getTimeZone().getRawOffset() / (3600 * 1000);
                if (timeZone == 0) {
                    out.append("Z");
                } else if (timeZone > 0) {
                    out.append("+").append(String.format("%02d", timeZone)).append(":00");
                } else {
                    out.append("-").append(String.format("%02d", -timeZone)).append(":00");
                }
 
                if (serializer.isEnabled(SerializerFeature.UseSingleQuotes)) {
                    out.append('\'');
                } else {
                    out.append('\"');
                }
            } else {
                time = time / 1000; // 返回UNIX时间戳，单位是秒
                out.writeLong(time);
            }
        }
    }
}
