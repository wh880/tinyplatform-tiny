/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
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
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.bundle;

import junit.framework.TestCase;
import org.tinygroup.bundle.config.BundleDefine;
import org.tinygroup.bundle.config.BundleService;
import org.tinygroup.bundle.impl.BundleManagerImpl;

import java.util.ArrayList;
import java.util.List;

public class BundleManagerTest extends TestCase {

    BundleDefine helloBundleDefine;
    BundleManager bundleManager;

    protected void setUp() throws Exception {
        super.setUp();
        bundleManager = new BundleManagerImpl();
        helloBundleDefine = new BundleDefine();
        helloBundleDefine.setId("hello");
        helloBundleDefine.setType(HelloBundle.class.getName());
        helloBundleDefine.setVersion("1.0");

        List<BundleService> bundleServiceList = new ArrayList<BundleService>();
        BundleService bundleService = new BundleService();
        bundleService.setId("hello");
        bundleService.setType(Hello.class.getName());
        bundleService.setVersion("1.0");
        bundleServiceList.add(bundleService);
        helloBundleDefine.setBundleServices(bundleServiceList);

    }

    public void testAddBundleDefine() {
        bundleManager.add(helloBundleDefine);
        assertEquals(1, bundleManager.size());
    }


    public void testAddBundleDefineArray() {
        BundleDefine[] pia = new BundleDefine[1];
        pia[0] = helloBundleDefine;
        bundleManager.add(pia);
        assertEquals(1, bundleManager.size());
    }

    public void testAddListOfBundleDefine() {
        List<BundleDefine> pil = new ArrayList<BundleDefine>();
        pil.add(helloBundleDefine);
        bundleManager.add(pil);
        assertEquals(1, bundleManager.size());
    }

    public void testStatus() {
        bundleManager.add(helloBundleDefine);
        assertEquals(BundleManager.STATUS_UNINITIALIZED, bundleManager.status(helloBundleDefine));
    }

    public void testGetBundleDefineStringString() {
        bundleManager.add(helloBundleDefine);
        assertEquals(helloBundleDefine, bundleManager.getBundleDefine("hello"));
    }

    public void testGetBundleDefineString() {
        bundleManager.add(helloBundleDefine);
        assertEquals(helloBundleDefine, bundleManager.getBundleDefine("hello", "1.0"));
    }

    public void testGetServiceBundleDefineClassOfT() {
        bundleManager.add(helloBundleDefine);
        bundleManager.start(helloBundleDefine);
        assertEquals(Hello.class, bundleManager.getService(helloBundleDefine, Hello.class).getClass());
    }

    public void testGetServiceBundleDefineClassOfTString() {
        bundleManager.add(helloBundleDefine);
        bundleManager.start(helloBundleDefine);
        assertEquals(Hello.class, bundleManager.getService(helloBundleDefine, Hello.class, "1.0").getClass());
    }

    public void testGetServiceBundleDefineString() {
        bundleManager.add(helloBundleDefine);
        bundleManager.start(helloBundleDefine);
//        assertEquals(Hello.class, bundleManager.getService(helloBundleDefine, "hello").getClass());
    }

    public void testGetServiceBundleDefineStringString() {
        bundleManager.add(helloBundleDefine);
        bundleManager.start(helloBundleDefine);
//        assertEquals(Hello.class, bundleManager.getService(helloBundleDefine, "hello", "1.0").getClass());
    }

    public void testInitBundleDefine() {
        bundleManager.add(helloBundleDefine);
        bundleManager.init(helloBundleDefine);
        assertEquals(BundleManager.STATUS_INITED, bundleManager.status(helloBundleDefine));
    }

    public void testStartBundleDefine() {
        bundleManager.add(helloBundleDefine);
        bundleManager.start(helloBundleDefine);
        assertEquals(BundleManager.STATUS_READY, bundleManager.status(helloBundleDefine));
    }

    public void testStopBundleDefine() {
        bundleManager.add(helloBundleDefine);
        bundleManager.start(helloBundleDefine);
        bundleManager.stop(helloBundleDefine);
        assertEquals(BundleManager.STATUS_ASSEMBLEED, bundleManager.status(helloBundleDefine));
    }

    public void testPaussBundleDefine() {
        bundleManager.add(helloBundleDefine);
        bundleManager.start(helloBundleDefine);
        bundleManager.pause(helloBundleDefine);
        assertEquals(BundleManager.STATUS_PAUSED, bundleManager.status(helloBundleDefine));
    }

    public void testDestroyBundleDefine() {
        bundleManager.add(helloBundleDefine);
        bundleManager.start(helloBundleDefine);
        bundleManager.destroy(helloBundleDefine);
        assertEquals(BundleManager.STATUS_UNINITIALIZED, bundleManager.status(helloBundleDefine));
    }

    public void testAssembleBundleDefine() {
        bundleManager.add(helloBundleDefine);
        bundleManager.assemble(helloBundleDefine);
        assertEquals(BundleManager.STATUS_ASSEMBLEED, bundleManager.status(helloBundleDefine));
    }

    public void testDisassembleBundleDefine() {
        bundleManager.add(helloBundleDefine);
        bundleManager.assemble(helloBundleDefine);
        bundleManager.disassemble(helloBundleDefine);
        assertEquals(BundleManager.STATUS_INITED, bundleManager.status(helloBundleDefine));
    }

    public void testInit() {
        bundleManager.add(helloBundleDefine);
        bundleManager.init();
        assertEquals(BundleManager.STATUS_INITED, bundleManager.status(helloBundleDefine));
    }

    public void testAssemble() {
        bundleManager.add(helloBundleDefine);
        bundleManager.assemble();
        assertEquals(BundleManager.STATUS_ASSEMBLEED, bundleManager.status(helloBundleDefine));
    }

    public void testStart() {
        bundleManager.add(helloBundleDefine);
        bundleManager.start();
        assertEquals(BundleManager.STATUS_READY, bundleManager.status(helloBundleDefine));
    }

    public void testPauss() {
        bundleManager.add(helloBundleDefine);
        bundleManager.start();
        bundleManager.pause();
        assertEquals(BundleManager.STATUS_PAUSED, bundleManager.status(helloBundleDefine));
    }

    public void testStop() {
        bundleManager.add(helloBundleDefine);
        bundleManager.start();
        bundleManager.stop();
        assertEquals(BundleManager.STATUS_ASSEMBLEED, bundleManager.status(helloBundleDefine));
    }

    public void testDisassemble() {
        bundleManager.add(helloBundleDefine);
        bundleManager.start();
        bundleManager.disassemble();
        assertEquals(BundleManager.STATUS_INITED, bundleManager.status(helloBundleDefine));
    }

    public void testDestroy() {
        bundleManager.add(helloBundleDefine);
        bundleManager.start();
        bundleManager.destroy();
        assertEquals(BundleManager.STATUS_UNINITIALIZED, bundleManager.status(helloBundleDefine));
    }

}
