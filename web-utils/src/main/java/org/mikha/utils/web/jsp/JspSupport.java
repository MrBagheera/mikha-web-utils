/**
 * Copyright 2009 by Dmitry Mikhaylov.
 * 
 * This file is part of web-utils.
 * 
 * web-utils is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * web-utils is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with web-utils.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.mikha.utils.web.jsp;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.mikha.utils.web.HttpParamsRequest;

/**
 * Collection of useful functions.
 * @author mikha
 */
public class JspSupport
{

    /**
     * Returns effective value for input parameter with given name (tries HTTP
     * parameter first, uses given default value if HTTP parameter is not
     * found).
     * @param ctx page context
     * @param name input parameter name
     * @param def default value for input parameter
     * @return effective value for input parameter with given name
     */
    public static Object getParam(PageContext ctx, String name, Object def)
    {
        HttpServletRequest r = (HttpServletRequest) ctx.getRequest();
        String[] v = (String[]) r.getParameterMap().get(name);
        if (v == null)
        {
            return def;
        }
        return (v.length == 1 ? v[0] : v);
    }

    /**
     * Tests if current request has any input parameter errors.
     * @param ctx page context
     * @return <code>true</code> if current request has some input parameter
     *         errors; <code>false</code> otherwise
     */
    @SuppressWarnings("unchecked")
    public static boolean hasErrors(PageContext ctx)
    {
        Set<String> errs =
            (Set<String>) ctx.getAttribute(HttpParamsRequest.ATTR_ERROR_STATES, PageContext.REQUEST_SCOPE);
        return (errs != null && errs.size() > 0);
    }

    /**
     * Tests if there is an error for input parameter with given name.
     * @param ctx page context
     * @param name input parameter name
     * @return <code>true</code> if current request has an error for input
     *         parameter with given name; <code>false</code> otherwise
     */
    @SuppressWarnings("unchecked")
    public static boolean hasError(PageContext ctx, String name)
    {
        Set<String> errs =
            (Set<String>) ctx.getAttribute(HttpParamsRequest.ATTR_ERROR_STATES, PageContext.REQUEST_SCOPE);
        return (errs != null && errs.contains(name));
    }

    /**
     * Returns custom error message (if any) for input parameter with given name.
     * @param ctx page context
     * @param name input parameter name
     * @return custom error message for input parameter with given name of <code>null</code>,
     *         if there is no error
     */
    @SuppressWarnings("unchecked")
    public static String getError(PageContext ctx, String name)
    {
        Map<String, String> errs =
            (Map<String, String>) ctx.getAttribute(HttpParamsRequest.ATTR_ERROR_MESSAGES, PageContext.REQUEST_SCOPE);
        return (errs != null ? errs.get(name) : null);
    }

    /**
     * Tests if given array contains given value. Non-array object are treated as single-object arrays.
     * @param a array
     * @param v value
     * @return <code>true</code> if given array contains given value;
     *         <code>false</code> otherwise
     */
    public static boolean inArray(Object a, Object v)
    {
        if (a == null || v == null) {
            return false;
        }
        String [] arr;
        if (a instanceof String[]) {
            arr = (String[]) a; 
        }
        else {
            arr = new String[] { String.valueOf(a) };
        }
        String var = String.valueOf(v);
        for (String s : arr) {
            if (s.equals(var)) {
                return true;
            }
        }
        return false;
    }

}
