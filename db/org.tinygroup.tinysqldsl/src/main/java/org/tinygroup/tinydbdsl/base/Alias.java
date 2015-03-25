package org.tinygroup.tinydbdsl.base;


/**
 * @author renhui
 */
public class Alias {

    private String name;
    private boolean useAs = true;

    public Alias() {

    }

    public Alias(String name) {
        this.name = name;
    }

    public Alias(String name, boolean useAs) {
        this.name = name;
        this.useAs = useAs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUseAs() {
        return useAs;
    }

    public void setUseAs(boolean useAs) {
        this.useAs = useAs;
    }


    public String toString() {
        return (useAs ? " AS " : " ") + name;
    }
}
