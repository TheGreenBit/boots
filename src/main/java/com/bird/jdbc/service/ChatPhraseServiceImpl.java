package com.bird.jdbc.service;

import com.bird.jdbc.bean.ChatPhrase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.JodaTimeConverters;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.ValidationUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ChatPhraseServiceImpl implements ChatPhraseService{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveChatPhrase(ChatPhrase chatPhrase) {
        String s = objToInsertSql(chatPhrase);
        System.out.println("=========================={"+s+"}================");
        jdbcTemplate.execute(s);
    }

    @Override
    public List<ChatPhrase> getChatPhrase() {
        List<ChatPhrase> phrases = jdbcTemplate.query("select * from b_chat_phrase where userId=?",new Object[]{1397},new BeanPropertyRowMapper(ChatPhrase.class));
        return phrases;
    }


    private String objToInsertSql(ChatPhrase chatPhrase) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(chatPhrase.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            StringBuilder builder = new StringBuilder("insert into b_chat_phrase(");
            Arrays.stream(propertyDescriptors).filter((a)->{return  !"phraseId".equals(a.getName())&&!"class".equals(a.getName());}).forEach((a)->{builder.append(a.getName()).append(",");});
            builder.deleteCharAt(builder.lastIndexOf(","));
            builder.append(") values (");
            Arrays.stream(propertyDescriptors).filter((a)->{return  !"phraseId".equals(a.getName())&&!"class".equals(a.getName());}).forEach((a)->{
                try {
                    Object invoke = a.getReadMethod().invoke(chatPhrase);
                    if(invoke instanceof String) {
                        builder.append("\"").append(invoke).append("\"");
                    }else if (invoke instanceof Date){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        builder.append("\"").append(sdf.format(invoke)).append("\"");
                    }else {
                        builder.append(invoke);
                    }
                    builder.append(",");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
            builder.deleteCharAt(builder.lastIndexOf(","));
            builder.append(")");
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "show databases";
    }
}
