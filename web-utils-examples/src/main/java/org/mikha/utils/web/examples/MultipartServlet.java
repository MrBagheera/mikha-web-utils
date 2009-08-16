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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.mikha.utils.web.HttpParamsRequest;

/**
 * Servlet implementation class for Servlet: MessagesServlet
 */
@SuppressWarnings("serial")
public class MultipartServlet extends javax.servlet.http.HttpServlet
    implements javax.servlet.Servlet
{

    /*
     * (non-Java-doc)
     * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
     *      HttpServletResponse response)
     */
    protected void doPost(HttpParamsRequest request,
        HttpServletResponse response) throws ServletException, IOException
    {
        FileItem file = request.getFile("myfile");
        if (file == null)
        {
            throw new ServletException(
                "File \"myfile\" is not found in uploaded data");
        }

        response.setContentType(file.getContentType());
        response.setContentLength((int) file.getSize());
        response.getOutputStream().write(file.get());
    }

}