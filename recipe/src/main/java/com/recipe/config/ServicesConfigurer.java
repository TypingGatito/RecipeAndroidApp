package com.recipe.config;

public class ServicesConfigurer {
    private static final ElementConfigurer elementConfigurer = new ElementConfigurer();

    private static ServicesConfig servicesConfig;

    static {
        if (servicesConfig == null) {
            servicesConfig = new ServicesConfig();
            elementConfigurer.configureObject(servicesConfig);
        }
    }

    public static ServicesConfig getServicesConfig() {
        if (servicesConfig == null) {
            servicesConfig = new ServicesConfig();
            elementConfigurer.configureObject(servicesConfig);
        }
        return servicesConfig;
    }
}
