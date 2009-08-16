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
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Implementation of &lt;param&gt; tag.<br>
 * Used inside &lt;url&gt; and &lt;form&gt; tag to provide named parameters.
 * @author Dmitry Mikhaylov
 */
public class ParamTag extends SimpleTagSupport
{

    private String name;

    private Object value;

    public void doTag() throws JspException, IOException
    {
        JspFragment body = getJspBody();
        if (body != null)
        {
            StringWriter sw = new StringWriter();
            body.invoke(sw);
            value = sw.toString();
        }
        JspTag parent = getParent();
        if (parent != null && parent instanceof ParametrizedTag)
        {
            ParametrizedTag pt = (ParametrizedTag) parent;
            pt.setParamValue(name, value);
        }
        else
        {
            throw new JspException("<param> tag can occur only within <url> tag");
        }
    }

    public void setName(String v)
    {
        this.name = v;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

}
