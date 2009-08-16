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

import java.util.ListResourceBundle;

/**
 * Resource bundle for i18n demonstration - English.
 * @author Dmitry Mikhaylov
 */
public class I18nResources_en extends ListResourceBundle
{
    
    /** messages */
    private Object[][] MESSAGES = 
    {
        { "error.param1", "Test error in test parameter!" },
        { "message0", "Test message, current time is {0}" }
    };

    /* (non-Javadoc)
     * @see java.util.ListResourceBundle#getContents()
     */
    protected Object[][] getContents()
    {
        return MESSAGES;
    }

}
