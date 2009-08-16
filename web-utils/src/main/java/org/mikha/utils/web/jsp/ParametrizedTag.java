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

/**
 * An interface through which &lt;wu:param&gt; communicates with parent tags.  
 * @author mikha
 */
public interface ParametrizedTag
{
    
    /**
     * Called by inner &lt;wu:param&gt; to communicate parameter value.
     * @param name parameter name
     * @param value parameter value
     */
    void setParamValue(String name, Object value);

}
