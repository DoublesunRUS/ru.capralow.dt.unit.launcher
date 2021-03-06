/**
 * Copyright (c) 2020, Alexander Kapralov
 */
package ru.capralow.dt.unit.launcher.frameworks.gson;

import com.google.gson.annotations.SerializedName;

public class FrameworkMethodParameter
{
    @SerializedName("name-en")
    public String nameEn = ""; //$NON-NLS-1$

    @SerializedName("name-ru")
    public String nameRu = ""; //$NON-NLS-1$

    @SerializedName("type")
    private String type = ""; //$NON-NLS-1$

    @SerializedName("types")
    private String[] types = { };

    @SerializedName("isOut")
    public Boolean isOut = false;

    @SerializedName("isDefaultValue")
    public Boolean isDefaultValue = false;

    public String[] getTypes()
    {
        if (type.isEmpty() && types != null)
        {
            return types;
        }

        return new String[] { type };
    }
}
