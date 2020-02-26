package com.microastudio.demo;

/**
 * @author peng
 * @date 2019/3/16
 */
public class EvaluateBean {
    private String name;
    private boolean checked;

    EvaluateBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
