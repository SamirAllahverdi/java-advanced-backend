package com.epam.ld.module2.testing.template;

import java.util.Map;

/**
 * The type Template.
 */
public abstract class Template {
    public abstract Map<String,String> getPlaceholders();
    public abstract void printResult(String template);
}
