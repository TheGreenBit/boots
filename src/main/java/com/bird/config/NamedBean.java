package com.bird.config;

import javafx.beans.NamedArg;
import lombok.Data;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Data
public class NamedBean {
    @Inject
    public Bird bird;

    private ConfigDolphin configDolphin;


    @Inject
    public ConfigDolphin getConfigDolphin() {
        return configDolphin;
    }

    @Inject
    public void setConfigDolphin(ConfigDolphin configDolphin) {
        this.configDolphin = configDolphin;
    }

    @Inject
    public void setF(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        System.out.println("................."+requestMappingHandlerMapping);
        requestMappingHandlerMapping.setAlwaysUseFullPath(true);
    }
}
