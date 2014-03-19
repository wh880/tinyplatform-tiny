package org.tinygroup.bundle.bundle;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.bundle.BundleManager;
import org.tinygroup.bundle.config.BundleDefine;
import org.tinygroup.bundle.config.BundleDefine;
import org.tinygroup.bundle.config.BundleService;
import org.tinygroup.bundle.impl.BundleManagerImpl;

import junit.framework.TestCase;

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
		assertEquals(BundleManager.STATUS_UNINITIALIZED,
				bundleManager.status(helloBundleDefine));
	}

	public void testGetBundleDefineStringString() {
		bundleManager.add(helloBundleDefine);
		assertEquals(helloBundleDefine, bundleManager.getBundleDefine("hello"));
	}

	public void testGetBundleDefineString() {
		bundleManager.add(helloBundleDefine);
		assertEquals(helloBundleDefine,
				bundleManager.getBundleDefine("hello", "1.0"));
	}

	public void testGetServiceBundleDefineClassOfT() {
		bundleManager.add(helloBundleDefine);
		bundleManager.start(helloBundleDefine);
		assertEquals(Hello.class,
				bundleManager.getService(helloBundleDefine, Hello.class)
						.getClass());
	}

	public void testGetServiceBundleDefineClassOfTString() {
		bundleManager.add(helloBundleDefine);
		bundleManager.start(helloBundleDefine);
		assertEquals(Hello.class,
				bundleManager.getService(helloBundleDefine, Hello.class, "1.0")
						.getClass());
	}

	public void testGetServiceBundleDefineString() {
		bundleManager.add(helloBundleDefine);
		bundleManager.start(helloBundleDefine);
//		assertEquals(Hello.class,
//				bundleManager.getService(helloBundleDefine, "hello").getClass());
	}

	public void testGetServiceBundleDefineStringString() {
		bundleManager.add(helloBundleDefine);
		bundleManager.start(helloBundleDefine);
//		assertEquals(Hello.class,
//				bundleManager.getService(helloBundleDefine, "hello", "1.0")
//						.getClass());
	}

	public void testInitBundleDefine() {
		bundleManager.add(helloBundleDefine);
		bundleManager.init(helloBundleDefine);
		assertEquals(BundleManager.STATUS_INITED,
				bundleManager.status(helloBundleDefine));
	}

	public void testStartBundleDefine() {
		bundleManager.add(helloBundleDefine);
		bundleManager.start(helloBundleDefine);
		assertEquals(BundleManager.STATUS_READY,
				bundleManager.status(helloBundleDefine));
	}

	public void testStopBundleDefine() {
		bundleManager.add(helloBundleDefine);
		bundleManager.start(helloBundleDefine);
		bundleManager.stop(helloBundleDefine);
		assertEquals(BundleManager.STATUS_ASSEMBLEED,
				bundleManager.status(helloBundleDefine));
	}

	public void testPaussBundleDefine() {
		bundleManager.add(helloBundleDefine);
		bundleManager.start(helloBundleDefine);
		bundleManager.pause(helloBundleDefine);
		assertEquals(BundleManager.STATUS_PAUSED,
				bundleManager.status(helloBundleDefine));
	}

	public void testDestroyBundleDefine() {
		bundleManager.add(helloBundleDefine);
		bundleManager.start(helloBundleDefine);
		bundleManager.destroy(helloBundleDefine);
		assertEquals(BundleManager.STATUS_UNINITIALIZED,
				bundleManager.status(helloBundleDefine));
	}

	public void testAssembleBundleDefine() {
		bundleManager.add(helloBundleDefine);
		bundleManager.assemble(helloBundleDefine);
		assertEquals(BundleManager.STATUS_ASSEMBLEED,
				bundleManager.status(helloBundleDefine));
	}

	public void testDisassembleBundleDefine() {
		bundleManager.add(helloBundleDefine);
		bundleManager.assemble(helloBundleDefine);
		bundleManager.disassemble(helloBundleDefine);
		assertEquals(BundleManager.STATUS_INITED,
				bundleManager.status(helloBundleDefine));
	}

	public void testInit() {
		bundleManager.add(helloBundleDefine);
		bundleManager.init();
		assertEquals(BundleManager.STATUS_INITED,
				bundleManager.status(helloBundleDefine));
	}

	public void testAssemble() {
		bundleManager.add(helloBundleDefine);
		bundleManager.assemble();
		assertEquals(BundleManager.STATUS_ASSEMBLEED,
				bundleManager.status(helloBundleDefine));
	}

	public void testStart() {
		bundleManager.add(helloBundleDefine);
		bundleManager.start();
		assertEquals(BundleManager.STATUS_READY,
				bundleManager.status(helloBundleDefine));
	}

	public void testPauss() {
		bundleManager.add(helloBundleDefine);
		bundleManager.start();
		bundleManager.pause();
		assertEquals(BundleManager.STATUS_PAUSED,
				bundleManager.status(helloBundleDefine));
	}

	public void testStop() {
		bundleManager.add(helloBundleDefine);
		bundleManager.start();
		bundleManager.stop();
		assertEquals(BundleManager.STATUS_ASSEMBLEED,
				bundleManager.status(helloBundleDefine));
	}

	public void testDisassemble() {
		bundleManager.add(helloBundleDefine);
		bundleManager.start();
		bundleManager.disassemble();
		assertEquals(BundleManager.STATUS_INITED,
				bundleManager.status(helloBundleDefine));
	}

	public void testDestroy() {
		bundleManager.add(helloBundleDefine);
		bundleManager.start();
		bundleManager.destroy();
		assertEquals(BundleManager.STATUS_UNINITIALIZED,
				bundleManager.status(helloBundleDefine));
	}

}
