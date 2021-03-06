/**
 * Copyright (c) 2020, Alexander Kapralov
 */
package ru.capralow.dt.unit.launcher.frameworks.gson;

import com.google.gson.annotations.SerializedName;

public class TestFramework
{
    @SerializedName("name")
    private String name = ""; //$NON-NLS-1$

    @SerializedName("bundle-name")
    private String bundleName = ""; //$NON-NLS-1$

    public String getBundleName()
    {
        return bundleName;
    }

    public String getName()
    {
        return name;
    }

    public void setBundleName(String value)
    {
        bundleName = value;
    }

    public void setName(String value)
    {
        name = value;
    }
}
