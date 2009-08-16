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

import org.mikha.utils.web.BaseControllerServlet;
import org.mikha.utils.web.HttpParamsRequest;
import org.mikha.utils.web.ControllerMethodMapping;

/**
 * Servlet implementation class for Servlet: MessagesServlet
 */
@SuppressWarnings("serial")
public class I18nServlet extends BaseControllerServlet
{

    @ControllerMethodMapping()
    public String service(HttpParamsRequest req) {
        req.getString("param1");
        req.setMessage("message0", new Date());
        return "i18n.jsp";
    }
}