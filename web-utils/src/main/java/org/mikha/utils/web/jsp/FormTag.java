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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.mikha.utils.Escape;
import org.mikha.utils.net.UrlAndParams;

/**
 * Implementation of &lt;form&gt; tag.<br>
 * Similar to &lt;url&gt; tag, but generates form with hidden parameters
 * instead. &lt;input type="submit"&gt; must be included explicitly.
 * @author Dmitry Mikhaylov
 */
public class FormTag extends SimpleTagSupport implements ParametrizedTag
{

    private Object tagUrl = null;

    private String method = "post";

    private String id = null;

    private String cls = null;

    private String style = null;

    private Map<String, Object> tagParams = new LinkedHashMap<String, Object>();

    public void setUrl(Object value)
    {
        this.tagUrl = value;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public void setId(String formId)
    {
        this.id = formId;
    }

    public void setClass(String formClass)
    {
        this.cls = formClass;
    }

    public void setStyle(String formStyle)
    {
        this.style = formStyle;
    }

    public void setParamValue(String name, Object value)
    {
        tagParams.put(name, value);
    }

    @SuppressWarnings("unchecked")
    public void doTag() throws JspException, IOException
    {
        if (tagUrl == null)
        {
            tagUrl = ".";
        }

        String url;
        Map<String, Object> params;
        if (tagUrl instanceof UrlAndParams)
        {
            UrlAndParams uap = (UrlAndParams) tagUrl;
            url = uap.getUrl();
            params = uap.getParams();
        }
        else
        {
            url = tagUrl.toString();
            params = Collections.EMPTY_MAP;
        }

        JspWriter w = getJspContext().getOut();
        w.print("<form ");
        if (url.length() > 0 && url.charAt(0) == '/')
        {
            HttpServletRequest req = (HttpServletRequest) ((PageContext) getJspContext()).getRequest();
            url = req.getContextPath() + url;
        }
        printAttr(w, "action", Escape.toValidXml(url));
        printAttr(w, "method", method);
        printAttr(w, "id", id);
        printAttr(w, "class", cls);
        printAttr(w, "style", style);
        w.println('>');

        printParams(params, w);

        JspFragment body = getJspBody();
        if (body != null)
        {
            body.invoke(null);
        }

        printParams(tagParams, w);

        w.println("</form>");
    }

    private void printParams(Map<String, Object> params, JspWriter w) throws IOException
    {
        for (Map.Entry<String, Object> e : params.entrySet())
        {
            w.print("<input");
            printAttr(w, "type", "hidden");
            printAttr(w, "name", Escape.toValidXml(e.getKey()));
            printAttr(w, "value", Escape.toValidXml(e.getValue() != null ? e.getValue().toString() : ""));
            w.println('>');
        }
    }

    private void printAttr(JspWriter w, String attr, String value) throws IOException
    {
        if (value == null)
        {
            return;
        }
        w.print(' ');
        w.print(attr);
        w.print('=');
        w.print('"');
        w.print(value);
        w.print('"');
    }

}
