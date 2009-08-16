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
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.mikha.utils.Escape;

/**
 * Implementation of &lt;error&gt; tag.<br>
 * Prints error text or stores it in specified 'page' scope variable if there is
 * a error in form parameter with specified name.
 * @author Dmitry Mikhaylov
 */
public class ErrorTag extends SimpleTagSupport
{

    private String name;

    private String var;

    public void doTag() throws JspException, IOException
    {
        String err = JspSupport.getError((PageContext) getJspContext(), name);
        if (var != null)
        {
            getJspContext().setAttribute(var, err);
        }
        else
        {
            if (err != null)
            {
                getJspContext().getOut().append(Escape.toSafeHtml(err));
            }
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
