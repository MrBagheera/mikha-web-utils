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
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.mikha.utils.net.UrlAndParams;

/**
 * Implementation of &lt;url&gt; tag.<br>
 * Similar to &lt;url&gt; tag from JSTL Core Tag library, but additionally
 * encodes URL using UTF-8 and escapes &amp; with &amp;amp; to produce value to
 * be used in "href" attribute.
 * @author Dmitry Mikhaylov
 */
public class UrlTag extends SimpleTagSupport implements ParametrizedTag
{

    private Map<String, Object> params = new LinkedHashMap<String, Object>();

    private Object value;

    private String var;

    public void setValue(Object value)
    {
        this.value = value;
    }

    public void setVar(String var)
    {
        this.var = var;
    }

    public void doTag() throws JspException, IOException
    {
        JspFragment body = getJspBody();
        if (body != null)
        {
            // invoke body, ignore results
            body.invoke(new Writer()
            {
                @Override
                public void close() throws IOException
                {
                }

                @Override
                public void flush() throws IOException
                {
                }

                @Override
                public void write(char[] cbuf, int off, int len) throws IOException
                {
                }
            });
        }

        UrlAndParams url;
        if (value == null)
        {
            url = new UrlAndParams("/");
        }
        else if (value instanceof UrlAndParams)
        {
            url = (UrlAndParams) value;
            if (params.size() > 0)
            {
                url = new UrlAndParams(url); // keep original intact
            }
        }
        else
        {
            url = new UrlAndParams(String.valueOf(value));
        }
        for (Map.Entry<String, Object> e : params.entrySet())
        {
            url.add(e.getKey(), e.getValue());
        }

        Writer w = (var != null ? new StringWriter() : getJspContext().getOut());
        String path = url.getUrl();
        if (path.length() == 0 || url.getUrl().charAt(0) == '/')
        {
            HttpServletRequest req = (HttpServletRequest) ((PageContext) getJspContext()).getRequest();
            w.append(req.getContextPath());
        }

        url.writeTo(true, w);
        if (var != null)
        {
            getJspContext().setAttribute(var, w.toString());
        }
    }

    public void setParamValue(String name, Object value)
    {
        params.put(name, value);
    }

}
