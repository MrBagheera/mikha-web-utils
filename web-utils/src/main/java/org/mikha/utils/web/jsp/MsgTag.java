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
import java.text.MessageFormat;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.fmt.LocaleSupport;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.mikha.utils.web.HttpParamsRequest;

/**
 * Implementation of <code>message</code> tag. Localizes and formats message
 * created using {@link HttpParamsRequest#setMessage(String, Object...)}.
 * @author Dmitry Mikhaylov
 */
public class MsgTag extends SimpleTagSupport
{

    /** do i18n? */
    private boolean myI18n = false;
    
    /**
     * Formats given message.
     * @param ctx page context
     * @param i18n if true, internationalization will be performed
     * @param msg message or key for finding message
     * @param params positional paremeters for message, can be <code>null</code>
     * @return formatted message
     */
    public static String format(JspContext ctx, boolean i18n, String msg, Object[] params)
    {
        if (i18n)
        {
            if (params != null)
            {
                return LocaleSupport.getLocalizedMessage(
                    (PageContext) ctx, msg, params);
            }
            else
            {
                return LocaleSupport.getLocalizedMessage(
                    (PageContext) ctx, msg);
            }
        }
        else
        {
            if (params != null)
            {
                return MessageFormat.format(msg, params);
            }
            else
            {
                return msg;
            }
        }
    }

    /**
     * Sets i18n. If the value is <code>true</code>, message text
     * will be treated as a key to find actual message in resource bundle.
     * @param i18n do i18n?
     */
    public void setI18n(boolean i18n)
    {
        myI18n = i18n;
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
     */
    public void doTag() throws JspException, IOException
    {
        String msg =
            (String) getJspContext().getAttribute(
                HttpParamsRequest.ATTR_MESSAGE_TEXT,
                PageContext.REQUEST_SCOPE);

        if (msg == null)
        {
            return;
        }
        
        Object[] params =
            (Object[]) getJspContext().getAttribute(
                HttpParamsRequest.ATTR_MESSAGE_PARAMS,
                PageContext.REQUEST_SCOPE);
        
        msg = format(getJspContext(), myI18n, msg, params);
        
        getJspContext().getOut().print(msg);
    }

}
