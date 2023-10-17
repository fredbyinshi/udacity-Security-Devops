package com.example.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class testSetupUtil {
    public static final Logger logger = LoggerFactory.getLogger(testSetupUtil.class);
public static void InjectObjects(Object target,String fieldName,Object toInject){
    boolean wasPrivate = false;
    try {
        Field classField = target.getClass().getDeclaredField(fieldName);
        logger.info("Class field name:"+classField);
        logger.info("The field was private:"+classField.isAccessible());
        if(!classField.isAccessible()){
            logger.info("set the field accessible");
            classField.setAccessible(true);
            wasPrivate = true;
        }
        logger.info("setting the ToInject Field");
        classField.set(target,toInject);
        if(wasPrivate){
            logger.info("reverting the field to be inaccessible");
            classField.setAccessible(false);
        }

    } catch (NoSuchFieldException | IllegalAccessException e) {
        throw new RuntimeException(e);
    }
}

}
