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
package org.mikha.utils.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Helper class for HTTP servlets. Supports:
 * <ul>
 * <li>mapping paths to methods
 * <li>mapping request parameters to method parameters
 * <li>mapping view names to views
 * </ul>
 * @author mikha
 */
@SuppressWarnings("serial")
public class BaseControllerServlet extends HttpServlet
{
    
    private ControllerDispatcher dispatcher;
    
    @Override
    public void init() throws ServletException
    {
        super.init();
        dispatcher = new ControllerDispatcher(getClass(), getServletContext(), getServletName());
    }
    
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse rsp)
        throws ServletException, IOException
    {
        dispatcher.service(this, req, rsp);
    }

}
