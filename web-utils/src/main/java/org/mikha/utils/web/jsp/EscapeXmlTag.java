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
import java.io.Writer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.mikha.utils.Escape;

/**
 * Implementation of &lt;escapeXml&gt; tag.<br>
 * Escapes special XML characters (&lt;, &gt;, &amp;, &quot;, &#039;).
 * @author Dmitry Mikhaylov
 * @see Escape#toValidXml(String)
 */
public class EscapeXmlTag extends SimpleTagSupport
{

    private String value;

    private String var;

    public void setValue(String value)
    {
        this.value = value;
    }

    public void setVar(String var)
    {
        this.var = var;
    }

    public void doTag() throws JspException, IOException
    {
        Writer w = (var != null ? new StringWriter() : getJspContext().getOut());
        if (value != null)
        {
            w.append(Escape.toSafeHtml(value));
        }
        JspFragment body = getJspBody();
        if (body != null)
        {
            StringWriter sw = new StringWriter();
            body.invoke(sw);
            w.append(Escape.toSafeHtml(sw.toString()));
        }
        if (var != null)
        {
            getJspContext().setAttribute(var, w.toString());
        }
    }

}
