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
package org.mikha.utils.web.examples;

import java.util.Date;

import org.mikha.utils.net.UrlAndParams;
import org.mikha.utils.web.BaseControllerServlet;
import org.mikha.utils.web.HttpParamsRequest;
import org.mikha.utils.web.ControllerMethodMapping;

/**
 * Demonstrates usage of message-related methods.
 */
@SuppressWarnings("serial")
public class MessagesServlet extends BaseControllerServlet
{

    @ControllerMethodMapping(paths = "/0")
    public String demoMessage0(HttpParamsRequest req)
    {
        req.setMessage("Default message");
        return "message.jsp";
    }

    @ControllerMethodMapping(paths = "/1")
    public String demoMessage1(HttpParamsRequest req)
    {
        req.setMessage("Default message, param1={0}", new Date());
        req.setAttribute("OkUrl", new UrlAndParams("/action.jsp").p("act", "Ok"));
        return "message.jsp";
    }

    @ControllerMethodMapping(paths = "/2")
    public String demoMessage2(HttpParamsRequest req)
    {
        req.setMessage("Custom message, Date={0}, Server={1}", new Date(), req.getServerName());
        req.setAttribute("OkUrl", new UrlAndParams("/action.jsp").add("act", "Ok!"));
        req.setAttribute("AbortUrl", new UrlAndParams("http://www.google.com/search").add("q", "президент Буш"));
        return "message.jsp";
    }

}