package com.ericho.coupleshare.setting.model;


import java.io.Serializable;


public class AppTestBo implements Serializable {
    private String code;
    private String name;
    private String displayName;

    public static AppTestBo create(String code, String name, String displayName) {
        AppTestBo appTestBo = new AppTestBo();
        appTestBo.setCode(code);
        appTestBo.setName(name);
        appTestBo.setDisplayName(displayName);
        return appTestBo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AppTestBo{");
        sb.append("code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", displayName='").append(displayName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
