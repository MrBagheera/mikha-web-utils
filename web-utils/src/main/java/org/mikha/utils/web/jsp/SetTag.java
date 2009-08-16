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

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Implementation of &lt;set&gt; tag.<br>
 * Sets the result of an expression evaluation in 'page' scope. Similar to
 * &lt;set&gt; tag from JSTL Core Tag library, but overrides value if there is HTTP
 * parameter with the same name.
 * @author Dmitry Mikhaylov
 */
public class SetTag extends SimpleTagSupport
{

    private String name;

    private Object value;

    private String var;

    public void doTag() throws JspException, IOException
    {
        JspFragment body = getJspBody();
        if (body != null)
        {
            StringWriter sw = new StringWriter();
            body.invoke(sw);
            setValue(sw.toString());
        }
        
        Object ev = JspSupport.getParam((PageContext) getJspContext(), name, value);
        if (var != null)
        {
            getJspContext().setAttribute(var, JspSupport.getParam((PageContext) getJspContext(), name, value));
        }
        else {
            getJspContext().getOut().append(String.valueOf(ev));
        }
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public void setVar(String var)
    {
        this.var = var;
    }

}
