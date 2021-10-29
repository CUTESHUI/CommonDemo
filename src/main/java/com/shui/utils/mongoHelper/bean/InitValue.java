package com.shui.utils.mongoHelper.bean;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface InitValue {

	String value();

}
