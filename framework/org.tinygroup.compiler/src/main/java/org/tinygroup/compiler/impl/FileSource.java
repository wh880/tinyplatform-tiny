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
package org.tinygroup.compiler.impl;

import org.tinygroup.compiler.Source;

import java.io.File;

/**
 * Created by luoguo on 2014/5/21.
 */
public class FileSource implements Source {
    File source;
    public FileSource(){

    }
    public FileSource(String path){
        this.source=new File(path);
    }
    public FileSource(File file){
        this.source=file;
    }
    public File getSource() {
        return source;
    }

    public void setSource(File source) {
        this.source = source;
    }
}
