package com.osteching.litemongo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD/*, ElementType.METHOD*/ })
public @interface Field {
    
    /**
     * field in given collection in MongoDB
     * @return
     */
    String value();
    
}
