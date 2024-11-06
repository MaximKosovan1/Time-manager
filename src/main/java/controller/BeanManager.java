package controller;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanManager {
    private static AnnotationConfigApplicationContext context;

    public BeanManager()
    {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
    }
    public static void setContext(AnnotationConfigApplicationContext appContext) {
        context = appContext;
    }

    public static AnnotationConfigApplicationContext getContext() {
        if (context == null) {
            throw new IllegalStateException("Spring context has not been initialized.");
        }
        return context;
    }
}
