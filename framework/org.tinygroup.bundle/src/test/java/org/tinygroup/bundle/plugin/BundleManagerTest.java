package org.tinygroup.bundle.plugin;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.bundle.BundleManager;
import org.tinygroup.bundle.config.PluginInfo;
import org.tinygroup.bundle.config.PluginService;
import org.tinygroup.bundle.impl.BundleManagerImpl;

import junit.framework.TestCase;

public class BundleManagerTest extends TestCase {

	PluginInfo helloPluginInfo;
	BundleManager bundleManager;

	protected void setUp() throws Exception {
		super.setUp();
		bundleManager = new BundleManagerImpl();
		helloPluginInfo = new PluginInfo();
		helloPluginInfo.setId("hello");
		helloPluginInfo.setType(HelloBundle.class.getName());
		helloPluginInfo.setVersion("1.0");

		List<PluginService> pluginServiceList = new ArrayList<PluginService>();
		PluginService pluginService = new PluginService();
		pluginService.setId("hello");
		pluginService.setType(Hello.class.getName());
		pluginService.setVersion("1.0");
		pluginServiceList.add(pluginService);
		helloPluginInfo.setPluginServices(pluginServiceList);

	}

	public void testAddPluginInfo() {
		bundleManager.add(helloPluginInfo);
		assertEquals(1, bundleManager.size());
	}

	public void testAddPluginInfoArray() {
		PluginInfo[] pia = new PluginInfo[1];
		pia[0] = helloPluginInfo;
		bundleManager.add(pia);
		assertEquals(1, bundleManager.size());
	}

	public void testAddListOfPluginInfo() {
		List<PluginInfo> pil = new ArrayList<PluginInfo>();
		pil.add(helloPluginInfo);
		bundleManager.add(pil);
		assertEquals(1, bundleManager.size());
	}

	public void testStatus() {
		bundleManager.add(helloPluginInfo);
		assertEquals(BundleManager.STATUS_UNINITIALIZED,
				bundleManager.status(helloPluginInfo));
	}

	public void testGetPluginInfoStringString() {
		bundleManager.add(helloPluginInfo);
		assertEquals(helloPluginInfo, bundleManager.getPluginInfo("hello"));
	}

	public void testGetPluginInfoString() {
		bundleManager.add(helloPluginInfo);
		assertEquals(helloPluginInfo,
				bundleManager.getPluginInfo("hello", "1.0"));
	}

	public void testGetServicePluginInfoClassOfT() {
		bundleManager.add(helloPluginInfo);
		bundleManager.start(helloPluginInfo);
		assertEquals(Hello.class,
				bundleManager.getService(helloPluginInfo, Hello.class)
						.getClass());
	}

	public void testGetServicePluginInfoClassOfTString() {
		bundleManager.add(helloPluginInfo);
		bundleManager.start(helloPluginInfo);
		assertEquals(Hello.class,
				bundleManager.getService(helloPluginInfo, Hello.class, "1.0")
						.getClass());
	}

	public void testGetServicePluginInfoString() {
		bundleManager.add(helloPluginInfo);
		bundleManager.start(helloPluginInfo);
//		assertEquals(Hello.class,
//				bundleManager.getService(helloPluginInfo, "hello").getClass());
	}

	public void testGetServicePluginInfoStringString() {
		bundleManager.add(helloPluginInfo);
		bundleManager.start(helloPluginInfo);
//		assertEquals(Hello.class,
//				bundleManager.getService(helloPluginInfo, "hello", "1.0")
//						.getClass());
	}

	public void testInitPluginInfo() {
		bundleManager.add(helloPluginInfo);
		bundleManager.init(helloPluginInfo);
		assertEquals(BundleManager.STATUS_INITED,
				bundleManager.status(helloPluginInfo));
	}

	public void testStartPluginInfo() {
		bundleManager.add(helloPluginInfo);
		bundleManager.start(helloPluginInfo);
		assertEquals(BundleManager.STATUS_READY,
				bundleManager.status(helloPluginInfo));
	}

	public void testStopPluginInfo() {
		bundleManager.add(helloPluginInfo);
		bundleManager.start(helloPluginInfo);
		bundleManager.stop(helloPluginInfo);
		assertEquals(BundleManager.STATUS_ASSEMBLEED,
				bundleManager.status(helloPluginInfo));
	}

	public void testPaussPluginInfo() {
		bundleManager.add(helloPluginInfo);
		bundleManager.start(helloPluginInfo);
		bundleManager.pause(helloPluginInfo);
		assertEquals(BundleManager.STATUS_PAUSED,
				bundleManager.status(helloPluginInfo));
	}

	public void testDestroyPluginInfo() {
		bundleManager.add(helloPluginInfo);
		bundleManager.start(helloPluginInfo);
		bundleManager.destroy(helloPluginInfo);
		assertEquals(BundleManager.STATUS_UNINITIALIZED,
				bundleManager.status(helloPluginInfo));
	}

	public void testAssemblePluginInfo() {
		bundleManager.add(helloPluginInfo);
		bundleManager.assemble(helloPluginInfo);
		assertEquals(BundleManager.STATUS_ASSEMBLEED,
				bundleManager.status(helloPluginInfo));
	}

	public void testDisassemblePluginInfo() {
		bundleManager.add(helloPluginInfo);
		bundleManager.assemble(helloPluginInfo);
		bundleManager.disassemble(helloPluginInfo);
		assertEquals(BundleManager.STATUS_INITED,
				bundleManager.status(helloPluginInfo));
	}

	public void testInit() {
		bundleManager.add(helloPluginInfo);
		bundleManager.init();
		assertEquals(BundleManager.STATUS_INITED,
				bundleManager.status(helloPluginInfo));
	}

	public void testAssemble() {
		bundleManager.add(helloPluginInfo);
		bundleManager.assemble();
		assertEquals(BundleManager.STATUS_ASSEMBLEED,
				bundleManager.status(helloPluginInfo));
	}

	public void testStart() {
		bundleManager.add(helloPluginInfo);
		bundleManager.start();
		assertEquals(BundleManager.STATUS_READY,
				bundleManager.status(helloPluginInfo));
	}

	public void testPauss() {
		bundleManager.add(helloPluginInfo);
		bundleManager.start();
		bundleManager.pause();
		assertEquals(BundleManager.STATUS_PAUSED,
				bundleManager.status(helloPluginInfo));
	}

	public void testStop() {
		bundleManager.add(helloPluginInfo);
		bundleManager.start();
		bundleManager.stop();
		assertEquals(BundleManager.STATUS_ASSEMBLEED,
				bundleManager.status(helloPluginInfo));
	}

	public void testDisassemble() {
		bundleManager.add(helloPluginInfo);
		bundleManager.start();
		bundleManager.disassemble();
		assertEquals(BundleManager.STATUS_INITED,
				bundleManager.status(helloPluginInfo));
	}

	public void testDestroy() {
		bundleManager.add(helloPluginInfo);
		bundleManager.start();
		bundleManager.destroy();
		assertEquals(BundleManager.STATUS_UNINITIALIZED,
				bundleManager.status(helloPluginInfo));
	}

}
