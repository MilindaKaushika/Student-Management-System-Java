// Decompiled by DJ v3.12.12.100 Copyright 2015 Atanas Neshkov  Date: 12/21/2015 12:07:40 AM
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ReflectionHelper.java

package org.smslib.helper;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;

public class ReflectionHelper
{

    public ReflectionHelper()
    {
    }

    public static Method getMethodOnlyByName(Class c, String methodName)
        throws NoSuchMethodException
    {
        Method method = null;
        Method arr$[] = c.getMethods();
        int len$ = arr$.length;
        int i$ = 0;
        do
        {
            if(i$ >= len$)
                break;
            Method m = arr$[i$];
            if(m.getName().equals(methodName))
            {
                method = m;
                break;
            }
            i$++;
        } while(true);
        if(method == null)
            throw new NoSuchMethodException(methodName);
        else
            return method;
    }

    public static Object invokeAndCast(Object returnType, Method m, Object obj, Object args[])
    {
        try
        {
            return m.invoke(obj, args);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static Enumeration invokeAndCastEnumeration(Object returnType, Method m, Object obj, Object args[])
    {
        try
        {
            return (Enumeration)m.invoke(obj, args);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static Collection invokeAndCastCollection(Object returnType, Method m, Object obj, Object args[])
    {
        try
        {
            return (Collection)m.invoke(obj, args);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
