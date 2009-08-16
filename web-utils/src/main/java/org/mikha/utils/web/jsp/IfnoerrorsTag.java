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
 * Implementation of &lt;ifnoerrors&gt; tag.<br>
 * Executes body only if there are no errors in form parameters.
 * @author Dmitry Mikhaylov
 */
public class IfnoerrorsTag extends SimpleTagSupport
{

    public void doTag() throws JspException, IOException
    {
        if (JspSupport.hasErrors((PageContext) getJspContext()))
        {
            return;
        }

        JspFragment f = getJspBody();
        if (f != null)
        {
            f.invoke(null);
        }
    }

}
