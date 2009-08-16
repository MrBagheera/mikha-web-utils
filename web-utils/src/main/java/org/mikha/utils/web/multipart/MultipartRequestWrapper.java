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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItem;

/**
 * Request wrapper to (nearly) transparently handle
 * <code>multipart/form-data</code> uploads.
 * @author mikha
 */
public class MultipartRequestWrapper extends HttpServletRequestWrapper
{

    private static final String ATTR_FILES = "_multipart_files";

    private final Map<String, String[]> params;

    /**
     * Returns map of file names to file items.
     * @param req request
     * @return map of file names to file items or <code>null</code>, if the
     *         request didn't contain <code>multipart/form-data</code> data
     */
    @SuppressWarnings("unchecked")
    public static Map<String, FileItem> getFilesMap(HttpServletRequest req)
    {
        Map<String, FileItem> files =
            (Map<String, FileItem>) req.getAttribute(ATTR_FILES);
        if (files == null)
        {
            files = new HashMap<String, FileItem>();
            req.setAttribute(ATTR_FILES, files);
        }
        return files;
    }

    /**
     * Returns file item for file with given name.
     * @param req request
     * @param name file name
     * @return file item for file with given name or <code>null</code>, if no
     *         file with given name present
     */
    public static FileItem getFile(HttpServletRequest req, String name)
    {
        return getFilesMap(req).get(name);
    }

    public MultipartRequestWrapper(HttpServletRequest request,
        Map<String, String[]> params, Map<String, FileItem> files)
    {
        super(request);
        this.params = params;
        request.setAttribute(ATTR_FILES, files);
    }

    public Map<String, String[]> getParameterMap()
    {
        return params;
    }

    public String getParameter(String name)
    {
        String[] v = params.get(name);
        if (v == null)
        {
            return null;
        }
        return v[0];
    }

    public Enumeration<String> getParameterNames()
    {
        Vector<String> v = new Vector<String>(params.keySet());
        return v.elements();
    }

    public String[] getParameterValues(String name)
    {
        return params.get(name);
    }

}
