package controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Configuration
@ComponentScan(basePackages = {"controller", "view"})  // Вкажіть свій пакет, де знаходяться сервіси
public class AppConfig {

}
