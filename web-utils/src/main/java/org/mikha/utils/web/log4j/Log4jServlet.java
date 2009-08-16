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
package org.mikha.utils.web.log4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.mikha.utils.log4j.InMemoryAppender;
import org.mikha.utils.web.BaseControllerServlet;
import org.mikha.utils.web.ControllerMethodMapping;
import org.mikha.utils.web.HttpParamsRequest;

/**
 * Helper servlet to provide access to Log4J.
 * @author mikha
 */
@SuppressWarnings("serial")
public class Log4jServlet extends BaseControllerServlet
{

    private static final Logger LOGGER = Logger.getLogger(Log4jServlet.class);
    
    private static final Pattern LEVELS_PATTERN = Pattern.compile("^(.*)=(.*)$", Pattern.MULTILINE);

    private InMemoryAppender inMemoryAppender = null;

    @SuppressWarnings("unchecked")
    @Override
    public void init() throws ServletException
    {
        LOGGER.debug("enter init()");
        super.init();
        Logger root = Logger.getRootLogger();
        Enumeration<Appender> ea = root.getAllAppenders();
        while (ea.hasMoreElements())
        {
            Appender a = ea.nextElement();
            if (a instanceof InMemoryAppender)
            {
                inMemoryAppender = (InMemoryAppender) a;
            }
        }
        if (inMemoryAppender == null)
        {
            throw new ServletException("InMemoryAppender is not configured for root logger");
        }
        LOGGER.debug("leave init()");
    }

    @ControllerMethodMapping(paths = "/view")
    public void view(HttpParamsRequest request, HttpServletResponse response) throws IOException
    {
        LOGGER.debug("enter view()");
        streamEvents(response, false);
        LOGGER.debug("leave view()");
    }

    @ControllerMethodMapping(paths = "/download")
    public void download(HttpParamsRequest request, HttpServletResponse response) throws IOException
    {
        LOGGER.debug("enter download()");
        streamEvents(response, true);
        LOGGER.debug("leave download()");
    }
    
    @SuppressWarnings("unchecked")
    @ControllerMethodMapping(paths = "/config")
    public String configure(HttpParamsRequest request) throws IOException {
        String levels = request.getParameter("levels");
        if (levels != null) {
            Matcher m = LEVELS_PATTERN.matcher(levels);
            HashSet<String> configuredLoggers = new HashSet<String>();
            while (m.find()) {
                String logger = m.group(1);
                String levelz = m.group(2);
                LOGGER.debug(logger + '=' + levelz);
                Level level = Level.toLevel(levelz, null);
                if (level == null) {
                    request.logParameterError("levels", "Invalid level value " + levelz);
                    continue;
                }
                if (logger.equals("root")) {
                    Logger.getRootLogger().setLevel(level);
                }
                else {
                    Logger.getLogger(logger).setLevel(level);
                }
                LOGGER.info("Changed log level for logger " + logger + " to " + level);
                configuredLoggers.add(logger);
            }
            
            Enumeration<Logger> loggers = LogManager.getCurrentLoggers();
            while(loggers.hasMoreElements()) {
                Logger logger = loggers.nextElement();
                if (logger.getLevel() != null && !configuredLoggers.contains(logger.getName()))
                {
                    logger.setLevel(null);
                    LOGGER.info("Removed log level for logger " + logger.getName());
                }
            }
        }
        
        StringBuffer currentConfig = new StringBuffer();
        currentConfig.append("root=").append(Logger.getRootLogger().getLevel()).append('\n');
        Enumeration<Logger> loggers = LogManager.getCurrentLoggers();
        while(loggers.hasMoreElements()) {
            Logger logger = loggers.nextElement();
            Level level = logger.getLevel();
            if (level != null) {
                currentConfig.append(logger.getName()).append('=').append(level).append('\n');
            }
        }
        request.setAttribute("levels", currentConfig.toString());
        return "logging.jsp";
    }

    private void streamEvents(HttpServletResponse response, boolean isDownload) throws IOException
    {
        Iterator<LoggingEvent> iterator;
        response.setContentType("text/plain");
        if (isDownload)
        {
            response.setHeader("Content-Disposition", "attachment; filename="
                + getServletContext().getServletContextName() + ".log");
            iterator = inMemoryAppender.iterator(false);
        }
        else
        {
            iterator = inMemoryAppender.iterator(true);
        }

        Layout layout = inMemoryAppender.getLayout();
        PrintWriter w = response.getWriter();
        while (!w.checkError() && iterator.hasNext())
        {
            LoggingEvent e = iterator.next();
            if (e != null)
            {
                w.print(layout.format(e));
            }
            if (!isDownload)
            {
                w.flush();
            }
        }
        w.close();
    }

}
