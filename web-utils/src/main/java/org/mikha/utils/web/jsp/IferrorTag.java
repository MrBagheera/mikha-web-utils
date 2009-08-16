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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Implementation of &lt;iferror&gt; tag.<br>
 * Executes body only if there is a error in a form parameter with specified
 * name. Error text is (optionally) available in 'page' scope variable.
 * @author Dmitry Mikhaylov
 */
public class IferrorTag extends SimpleTagSupport
{

    private String name;

    private String var;

    public void doTag() throws JspException, IOException
    {
        String err = JspSupport.getError((PageContext) getJspContext(), name);
        if (err == null)
        {
            return;
        }

        JspFragment f = getJspBody();
        if (f != null)
        {
            if (var != null)
            {
                getJspContext().setAttribute(var, err);
            }
            f.invoke(null);
        }
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setVar(String var)
    {
        this.var = var;
    }

}
