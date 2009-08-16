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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Turns any class into web controller.
 * @author mikha
 */
public class ControllerDispatcher
{

    /** Invokes method with correct parameters. */
    private static class MethodInvoker
    {

        /** Fills parameters for actual method call */
        private static interface ParamAdapter
        {
            public Object adapt(HttpParamsRequest req, HttpServletResponse rsp);
        }

        /** Passes request */
        private static final ParamAdapter REQUEST_ADAPTER = new ParamAdapter()
        {
            public Object adapt(HttpParamsRequest req, HttpServletResponse rsp)
            {
                return req;
            };
        };

        /** Passes response */
        private static final ParamAdapter RESPONSE_ADAPTER = new ParamAdapter()
        {
            public Object adapt(HttpParamsRequest req, HttpServletResponse rsp)
            {
                return rsp;
            };
        };

        private final Method method;

        private final ParamAdapter[] paramAdapters;

        /**
         * Constructor.
         * @param method actual method
         * @throws IllegalArgumentException if method is invalid
         */
        public MethodInvoker(Method method) throws IllegalArgumentException
        {
            super();
            this.method = method;

            if (method.getReturnType() != void.class && method.getReturnType() != String.class)
            {
                throw new IllegalArgumentException("Invalid method " + method
                    + ": only void and String return types are supported");
            }

            Class<?>[] clss = method.getParameterTypes();
            this.paramAdapters = new ParamAdapter[clss.length];
            for (int i = 0; i < clss.length; i++)
            {
                try
                {
                    if (clss[i].isAssignableFrom(HttpParamsRequest.class))
                    {
                        paramAdapters[i] = REQUEST_ADAPTER;
                    }
                    else if (clss[i].isAssignableFrom(HttpServletResponse.class))
                    {
                        paramAdapters[i] = RESPONSE_ADAPTER;
                    }
                    else
                    {
                        throw new IllegalArgumentException("Unsupported parameter type " + clss[i].getCanonicalName());
                    }
                }
                catch (Exception ex)
                {
                    throw new IllegalArgumentException("Invalid method " + method + ": invalid parameter #" + (i + 1),
                        ex);
                }
            }
        }

        /**
         * Binds method parameters and invokes associated method.
         * @param ctr controller on which to invoke method
         * @param req enhanced request
         * @param rsp response
         * @return view name or null
         * @throws ServletException if controller failed
         * @throws IOException if controller failed
         */
        public String invoke(Object ctr, HttpParamsRequest req, HttpServletResponse rsp) throws ServletException,
            IOException
        {
            try
            {
                Object[] params = new Object[paramAdapters.length];
                for (int i = 0; i < paramAdapters.length; i++)
                {
                    params[i] = paramAdapters[i].adapt(req, rsp);
                }
                return (String) method.invoke(ctr, params);
            }
            catch (InvocationTargetException itex)
            {
                Throwable cause = itex.getCause();
                if (cause instanceof ServletException)
                {
                    throw (ServletException) cause;
                }
                if (cause instanceof IOException)
                {
                    throw (IOException) cause;
                }
                throw new ServletException("Unexpected exception in method " + method + " of " + ctr.getClass(), cause);
            }
            catch (Exception ex)
            {
                throw new ServletException("Unexpected exception in method " + method + " of " + ctr.getClass(), ex);
            }
        }

    }

    public static final String VIEW_BASE = "/WEB-INF/view/";

    private final Class<?> controllerClass;

    private final ServletContext servletContext;

    private final Map<HttpMethod, Map<String, MethodInvoker>> mappings = new HashMap<HttpMethod, Map<String, MethodInvoker>>();

    private final ConcurrentHashMap<String, RequestDispatcher> viewDispatchers = new ConcurrentHashMap<String, RequestDispatcher>();

    /**
     * Constructor.
     * @param controllerClass controller class
     * @param servletContext servlet context
     * @param controllerName controller name
     * @throws ServletException if controller class is invalid
     */
    public ControllerDispatcher(Class<?> controllerClass, ServletContext servletContext, String controllerName)
        throws ServletException
    {
        this.controllerClass = controllerClass;
        this.servletContext = servletContext;

        for (Method m : controllerClass.getMethods())
        {
            ControllerMethodMapping um = m.getAnnotation(ControllerMethodMapping.class);
            if (um == null)
            {
                continue;
            }

            MethodInvoker mi = new MethodInvoker(m);

            for (HttpMethod hm : um.methods())
            {
                Map<String, MethodInvoker> mm = mappings.get(hm);
                if (mm == null)
                {
                    mm = new HashMap<String, MethodInvoker>();
                    mappings.put(hm, mm);
                }

                for (String path : um.paths())
                {
                    MethodInvoker old = mm.put(path, mi);
                    if (old != null)
                    {
                        throw new ServletException(
                            String.format(
                                "Invalid controller class %s: mapping conflicts for HTTP method %s and path %s - %s vs. %s",
                                controllerClass, hm, path, mi, old));
                    }
                }

            }

        }

        if (mappings.size() == 0)
        {
            throw new ServletException("Invalid controller class " + controllerClass + ": no mappings defined");
        }
    }

    public void service(Object ctr, HttpServletRequest req, HttpServletResponse rsp) throws ServletException,
        IOException
    {
        if (ctr == null || !controllerClass.isAssignableFrom(ctr.getClass()))
        {
            throw new IllegalArgumentException("Invalid controller instance " + ctr + ": not an instance of "
                + controllerClass.getCanonicalName());
        }

        HttpMethod hm = HttpMethod.valueOf(req.getMethod());
        String path = req.getPathInfo();
        MethodInvoker m = null;
        Map<String, MethodInvoker> mm = mappings.get(hm);
        if (mm != null)
        {
            if (path != null)
            {
                m = mm.get(path);
            }
            if (m == null)
            {
                m = mm.get("");
            }
        }
        if (m == null)
        {
            // no controller method defined for given path and HTTP method
            rsp.sendError(HttpServletResponse.SC_NOT_FOUND, "no mapping defined for path \"" + path
                + "\" and HTTP method " + hm);
            return;
        }

        HttpParamsRequest ereq = HttpParamsRequest.wrap(req);
        String view = m.invoke(ctr, ereq, rsp);
        if (view != null)
        {
            RequestDispatcher rd = viewDispatchers.get(view);
            if (rd == null)
            {
                rd = servletContext.getRequestDispatcher(VIEW_BASE + view);
                if (rd == null)
                {
                    throw new ServletException("Cannot find view " + view + " for method " + m + " of "
                        + controllerClass);
                }
                viewDispatchers.put(view, rd);
            }
            rd.forward(ereq, rsp);
        }
    }

}
