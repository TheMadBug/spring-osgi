/*
 * Copyright 2002-2006 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.osgi.iandt.propertyplaceholder;

import java.util.Dictionary;
import java.util.Hashtable;
import java.io.File;

import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.springframework.osgi.context.support.OsgiBundleXmlApplicationContext;
import org.springframework.osgi.context.ConfigurableOsgiBundleApplicationContext;
import org.springframework.osgi.util.OsgiServiceUtils;
import org.springframework.osgi.test.AbstractConfigurableBundleCreatorTests;

/**
 * 
 * Integration test for OsgiPropertyPlaceholder.
 * 
 * @author Costin Leau
 * 
 */
public class PropertyPlaceholderTest extends AbstractConfigurableBundleCreatorTests {

	private final static String ID = "PropertyPlaceholderTest-123";

	private final static Dictionary DICT = new Hashtable();

	private ConfigurableOsgiBundleApplicationContext ctx;

    private static String CONFIG_DIR = "test-config";

    protected static void initializeDirectory(String dir) {
        File directory = new File(dir);
        remove(directory);
        assertTrue(dir + " directory successfully created", directory.mkdirs());
    }


    private static void remove(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isDirectory()) {
                    remove(file);
                } else {
                    assertTrue(file + " deleted", file.delete());
                }
            }
            assertTrue(directory + " directory successfully cleared",
                       directory.delete());
        }
    }

	protected String getManifestLocation() {
		return "classpath:org/springframework/osgi/iandt/propertyplaceholder/PropertyPlaceholder.MF";
	}

	protected String[] getBundles() {
		return new String[] {
				localMavenArtifact("org.springframework.osgi", "commons-collections.osgi", "3.2-SNAPSHOT"),
				// required by cm_all for logging
				localMavenArtifact("org.knopflerfish.bundles", "log_all", "2.0.0"),
				localMavenArtifact("org.knopflerfish.bundles", "cm_all", "2.0.0") };
	}

	protected void onSetUp() throws Exception {
		DICT.put("foo", "bar");
		DICT.put("white", "horse");
        // Set up the bundle storage dirctory
        System.setProperty("com.gatespace.bundle.cm.store", CONFIG_DIR);
        initializeDirectory(CONFIG_DIR);
		prepareConfiguration();

        String[] locations = new String[]{"org/springframework/osgi/iandt/propertyplaceholder/placeholder.xml"};
        ctx = new OsgiBundleXmlApplicationContext(locations);
        ctx.setBundleContext(getBundleContext());
        ctx.refresh();
	}

	protected void onTearDown() throws Exception {
		if (ctx != null)
			ctx.close();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.osgi.iandt.AbstractConfigurableBundleCreatorTests#getBundleContentPattern()
	 */
	protected String[] getBundleContentPattern() {
		return super.getBundleContentPattern();
	}

	// add a default table into OSGi
	private void prepareConfiguration() throws Exception {

		ServiceReference ref = OsgiServiceUtils.getService(getBundleContext(), ConfigurationAdmin.class, null);

		ConfigurationAdmin admin = (ConfigurationAdmin) getBundleContext().getService(ref);
		Configuration config = admin.getConfiguration(ID);
		config.update(DICT);
	}

	public void testFoundProperties() throws Exception {
		String bean = (String) ctx.getBean("bean1");
		assertEquals("horse", bean);
	}

	public void testFallbackProperties() throws Exception {
		String bean = (String) ctx.getBean("bean2");
		assertEquals("treasures", bean);
	}
}
