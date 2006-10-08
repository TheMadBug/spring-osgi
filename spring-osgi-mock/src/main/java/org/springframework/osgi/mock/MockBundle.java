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
package org.springframework.osgi.mock;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

/**
 * @author Costin Leau
 * 
 */
public class MockBundle implements Bundle {

	private String location;
	private Dictionary headers;
	private BundleContext bundleContext;

	private class EmptyEnumeration implements Enumeration {
		public boolean hasMoreElements() {
			return false;
		}

		public Object nextElement() {
			return null;
		}
	}

	public MockBundle() {
		this(null, null, null);
	}

	public MockBundle(Dictionary headers) {
		this(null, headers, null);
	}

	public MockBundle(String location) {
		this(location, null, null);
	}

	public MockBundle(BundleContext context) {
		this(null, null, context);
	}

	public MockBundle(String location, Dictionary headers, BundleContext context) {
		this.location = (location == null ? "<default location>" : location);
		this.headers = (headers == null ? new Hashtable() : headers);
		this.bundleContext = (context == null ? new MockBundleContext(this) : context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#findEntries(java.lang.String,
	 *      java.lang.String, boolean)
	 */
	public Enumeration findEntries(String path, String filePattern, boolean recurse) {
		return new EmptyEnumeration();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getBundleId()
	 */
	public long getBundleId() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getEntry(java.lang.String)
	 */
	public URL getEntry(String name) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getEntryPaths(java.lang.String)
	 */
	public Enumeration getEntryPaths(String path) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getHeaders()
	 */
	public Dictionary getHeaders() {
		return headers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getHeaders(java.lang.String)
	 */
	public Dictionary getHeaders(String locale) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getLastModified()
	 */
	public long getLastModified() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getLocation()
	 */
	public String getLocation() {
		return location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getRegisteredServices()
	 */
	public ServiceReference[] getRegisteredServices() {
		return new ServiceReference[] {};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getResource(java.lang.String)
	 */
	public URL getResource(String name) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getResources(java.lang.String)
	 */
	public Enumeration getResources(String name) throws IOException {
		return new EmptyEnumeration();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getServicesInUse()
	 */
	public ServiceReference[] getServicesInUse() {
		return new ServiceReference[] {};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getState()
	 */
	public int getState() {
		return Bundle.ACTIVE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getSymbolicName()
	 */
	public String getSymbolicName() {
		return "MockBundle";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#hasPermission(java.lang.Object)
	 */
	public boolean hasPermission(Object permission) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#loadClass(java.lang.String)
	 */
	public Class loadClass(String name) throws ClassNotFoundException {
		throw new ClassNotFoundException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#start()
	 */
	public void start() throws BundleException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#stop()
	 */
	public void stop() throws BundleException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#uninstall()
	 */
	public void uninstall() throws BundleException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#update()
	 */
	public void update() throws BundleException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#update(java.io.InputStream)
	 */
	public void update(InputStream in) throws BundleException {
	}

}
