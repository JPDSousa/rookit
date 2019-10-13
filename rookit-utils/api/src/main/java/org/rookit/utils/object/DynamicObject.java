package org.rookit.utils.object;

public interface DynamicObject {

    DynamicObject getDynamicObject(String name);

    double getDouble(String name);

    boolean getBoolean(String name);

    int getInt(String name);

    String getString(String name);

}
