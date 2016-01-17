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
// ========================================================================
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at 
// http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// ========================================================================
package org.tinygroup.jspengine.org.apache.commons.logging;





public interface Log 
{
    void fatal(Object message);
    
    
    void fatal(Object message, Throwable t);
   
    
    void debug(Object message);
   
    
    void debug(Object message, Throwable t);
   
    
    void trace(Object message);
   
    
  
    void info(Object message);
   

    void error(Object message);
   
    
    void error(Object message, Throwable cause);
   

    void warn(Object message);
  
    
    boolean isDebugEnabled();
    
    
    boolean isWarnEnabled();
    
    
    boolean isInfoEnabled();
    
    
    
    boolean isErrorEnabled();
   
    
  
    boolean isTraceEnabled();
   
}
