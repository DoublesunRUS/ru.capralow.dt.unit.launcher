/**
 * Copyright (c) 2021, Aleksandr Kapralov
 */
package ru.capralow.dt.unit.junit.model;

public interface ITestCaseElement
    extends ITestElement
{
    public String getTestClassName();

    public String getTestMethodName();
}