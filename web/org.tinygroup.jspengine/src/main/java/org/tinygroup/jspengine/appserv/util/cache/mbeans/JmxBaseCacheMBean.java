/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
/**
 * @Version $Id: JmxBaseCacheMBean.java,v 1.5 2007/05/05 05:35:30 tcfujii Exp $
 * Created on May 4, 2005 11:43 AM
 */

package org.tinygroup.jspengine.appserv.util.cache.mbeans;

/**
 * This interface defines the attributes exposed by the BaseCache MBean
 *
 * @author Krishnamohan Meduri (Krishna.Meduri@Sun.com)
 *
 */
public interface JmxBaseCacheMBean {

    /**
     * Returns a unique identifier for this MBean inside the domain
     */
    String getName();

    /**
     * Returns maximum possible number of entries
     */
    Integer getMaxEntries();

    /**
     * Returns threshold. This when reached, an overflow will occur
     */
    Integer getThreshold();

    /**
     * Returns current number of buckets
     */
    Integer getTableSize();

    /**
     * Returns current number of Entries
     */
    Integer getEntryCount();

    /**
     * Return the number of cache hits
     */
    Integer getHitCount();

    /**
     * Returns the number of cache misses
     */
    Integer getMissCount();

    /**
     * Returns the number of entries that have been removed
     */
    Integer getRemovalCount();

    /**
     * Returns the number of values that have been refreshed 
     * (replaced with a new value in an existing extry)
     */
    Integer getRefreshCount();

    /**
     * Returns the number of times that an overflow has occurred
     */
    Integer getOverflowCount();

    /**
     * Returns the number of times new entries have been added
     */
    Integer getAddCount();
}
