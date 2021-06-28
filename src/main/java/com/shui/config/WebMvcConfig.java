package com.shui.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 默认日期时间格式
     */
    private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static List<String> formatList = new ArrayList<>(5);

    static {
        formatList.add("yyyy-MM");
        formatList.add("yyyy-MM-dd");
        formatList.add("yyyy-MM-dd hh:mm");
        formatList.add("yyyy-MM-dd hh:mm:ss");
        formatList.add("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // Date转换器，用于转换RequestParam和PathVariable参数
        registry.addConverter(dateConverter());
    }

    private Converter<String, Date> dateConverter() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                String value = source.trim();
                if (StringUtils.isEmpty(value)) {
                    return null;
                }

                if (source.matches("^\\d{4}-\\d{1,2}$")) {
                    return parseDate(source, formatList.get(0));
                } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
                    return parseDate(source, formatList.get(1));
                } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
                    return parseDate(source, formatList.get(2));
                } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
                    return parseDate(source, formatList.get(3));
                } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}.*T.*\\d{1,2}:\\d{1,2}:\\d{1,2}.*..*$")) {
                    return parseDate(source, formatList.get(4));
                } else {
                    throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
                }
            }
        };
    }

    private Date parseDate(String dateStr, String format) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {
            log.error("Formatted date with date: {} and format : {} ", dateStr, format);
        }
        return date;
    }


    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 负责读取二进制格式的数据和写出二进制格式的数据
        converters.add(new ByteArrayHttpMessageConverter());
        // 负责读取字符串格式的数据和写出二进制格式的数据
        converters.add(new StringHttpMessageConverter());
        // 负责读取资源文件和写出资源文件数据
        converters.add(new ResourceHttpMessageConverter());
        // 负责读取和写入json格式的数据
        converters.add(jackson2HttpMessageConverter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper());
        return converter;
    }

    private ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // 日期格式转换、全局的、JSON的
        // 1、支持（content-type=application/json）请求中格式为yyyy-MM-dd HH:mm:ss的字符串
        // 2、后台用@RequestBody接收
        // 3、返回值Date转为yyyy-MM-dd HH:mm:ss格式String
        // 4、不支持（content-type=application/json）请求中yyyy-MM-dd等，使用 @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT));
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        // Long类型转String类型
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);

        // null -> ""
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString("");
            }
        });
        return objectMapper;
    }
}
