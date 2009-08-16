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
package org.mikha.utils.web.multipart;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Filter to (nearly) transparently handle <code>multipart/form-data</code>
 * uploads.
 * @author mikha
 */
public class MultiparFilter implements Filter
{

    private FileItemFactory fileItemFactory;

    public void init(FilterConfig config) throws ServletException
    {
        File dir;
        String s = config.getInitParameter("uploadDir");
        if (s != null)
        {
            dir = new File(s);
        }
        else
        {
            dir =
                (File) config.getServletContext().getAttribute(
                    "javax.servlet.context.tempdir");
            if (dir == null)
            {
                throw new ServletException(
                    "Cannot determine temporary upload directory. Set an uploadDir "
                        + "init parameter or ensure the javax.servlet.context.tempdir "
                        + "directory is valid");
            }
        }

        int maxSize = Integer.MAX_VALUE;
        s = config.getInitParameter("maxSize");
        if (s != null)
        {

            try
            {
                maxSize = Integer.parseInt(s);
                if (maxSize <= 0)
                {
                    throw new IllegalArgumentException("Must be positive");
                }
            }
            catch (IllegalArgumentException iaex)
            {
                throw new ServletException("Value \"" + s
                    + "\" is not valid for maxSize parameter", iaex);
            }

        }

        fileItemFactory = new DiskFileItemFactory(maxSize, dir);
    }

    @SuppressWarnings("unchecked")
    public void doFilter(ServletRequest req, ServletResponse rsp,
        FilterChain chain) throws IOException, ServletException
    {
        if (!(req instanceof HttpServletRequest))
        {
            chain.doFilter(req, rsp);
            return;
        }
        HttpServletRequest hreq = (HttpServletRequest) req;
        if (!ServletFileUpload.isMultipartContent(hreq))
        {
            chain.doFilter(req, rsp);
            return;
        }
        try
        {
            Map<String, List<String>> tmp =
                new HashMap<String, List<String>>();
            Map<String, FileItem> files = new HashMap<String, FileItem>();
            ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
            Iterator<FileItem> i = upload.parseRequest(hreq).iterator();
            while (i.hasNext())
            {
                FileItem item = i.next();
                String name = item.getFieldName();
                if (item.isFormField())
                {
                    List<String> list = tmp.get(name);
                    if (list == null)
                    {
                        list = new LinkedList<String>();
                        tmp.put(name, list);
                    }
                    list.add(item.getString());
                }
                else
                {
                    files.put(name, item);
                }
            }
            Map<String, String[]> params =
                new HashMap<String, String[]>(tmp.size());
            for (Map.Entry<String, List<String>> me : tmp.entrySet())
            {
                List<String> l = me.getValue();
                params.put(me.getKey(), l.toArray(new String[l.size()]));
            }

            chain.doFilter(new MultipartRequestWrapper(hreq, params, files),
                rsp);
        }
        catch (FileUploadException fuex)
        {
            throw new ServletException("Failed to process uploaded file",
                fuex);
        }
    }

    public void destroy()
    {
    }

}
