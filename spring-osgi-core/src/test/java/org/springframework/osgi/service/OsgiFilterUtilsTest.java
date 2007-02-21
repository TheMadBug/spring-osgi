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
package org.springframework.osgi.service;

import java.io.Serializable;
import java.util.Dictionary;
import java.util.Hashtable;

import junit.framework.TestCase;

import org.osgi.framework.Constants;
import org.osgi.framework.Filter;

/**
 * @author Costin Leau
 * 
 */
public class OsgiFilterUtilsTest extends TestCase {

	private String[] classes;

	private Dictionary dictionary;

	protected void setUp() throws Exception {
		classes = new String[] { Object.class.getName(), Cloneable.class.getName(), Serializable.class.getName() };
		dictionary = new Hashtable();
		dictionary.put(Constants.OBJECTCLASS, classes);
	}

	protected void tearDown() throws Exception {
		dictionary = null;
		classes = null;
	}

	public void testNoArgument() {
		try {
			OsgiFilterUtils.unifyFilter((String) null, null);
			fail("should have thrown exception");
		}
		catch (Exception ex) {
			// expected
		}
	}

	public void testClassArrayWithGarbage() {
		String[] garbage = new String[] { null, null };
		try {
			OsgiFilterUtils.unifyFilter(garbage, null);
			fail("should have thrown exception " + IllegalArgumentException.class.getName());
		}
		catch (IllegalArgumentException iae) {
			// expected
		}
	}

	public void testOnlyClassArgument() {
		String filter = OsgiFilterUtils.unifyFilter(classes[0], null);
		assertNotNull(filter);
		assertTrue(OsgiFilterUtils.isValidFilter(filter));
	}

	public void testJustClassWithNoFilter() {
		String fl = OsgiFilterUtils.unifyFilter(classes[0], null);
		String filter = OsgiFilterUtils.unifyFilter((String) null, fl);

		assertEquals("filter shouldn't have been modified", fl, filter);
	}

	public void testClassWithExistingFilter() {
		String filter = "(o=univ*of*mich*)";
		String fl = OsgiFilterUtils.unifyFilter(classes[0], filter);
		assertTrue(OsgiFilterUtils.isValidFilter(fl));
	}

	public void testMultipleClassesWithExistingFilter() {
		String filter = "(|(sn=Jensen)(cn=Babs J*))";
		String fl = OsgiFilterUtils.unifyFilter(classes, filter);
		assertTrue(OsgiFilterUtils.isValidFilter(fl));
	}

	public void testMultipleClassesAddedOneByOne() {
		String filter = OsgiFilterUtils.unifyFilter(classes[0], null);
		filter = OsgiFilterUtils.unifyFilter(classes[1], filter);
		filter = OsgiFilterUtils.unifyFilter(classes[2], filter);

		Filter osgiFilter = OsgiFilterUtils.createFilter(filter);

		// verify the filter using the matching against a dictionary
		assertTrue(osgiFilter.matchCase(dictionary));
	}

	public void testMultipleClassesAddedAtOnce() {
		String filter = OsgiFilterUtils.unifyFilter(classes, null);
		Filter osgiFilter = OsgiFilterUtils.createFilter(filter);
		// verify the filter using the matching against a dictionary
		assertTrue(osgiFilter.matchCase(dictionary));
	}

	public void testNonMatching() {
		String filter = OsgiFilterUtils.unifyFilter(classes, null);
		Filter osgiFilter = OsgiFilterUtils.createFilter(filter);
		dictionary.put(Constants.OBJECTCLASS, new String[] { classes[0] });

		// verify the filter using the matching against a dictionary
		assertFalse(osgiFilter.matchCase(dictionary));
	}
}