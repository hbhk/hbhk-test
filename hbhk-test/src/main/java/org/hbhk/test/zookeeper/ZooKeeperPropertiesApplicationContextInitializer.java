package org.hbhk.test.zookeeper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.support.ResourcePropertySource;

public class ZooKeeperPropertiesApplicationContextInitializer implements
		ApplicationContextInitializer<ConfigurableApplicationContext> {
	private static final Logger logger = LoggerFactory
			.getLogger(ZooKeeperPropertiesApplicationContextInitializer.class);

	private final CuratorFramework curator;
	private String projectName;
	private String projectVersion;

	public ZooKeeperPropertiesApplicationContextInitializer()
			throws IOException {
		logger.trace("Attempting to construct CuratorFramework instance");

		RetryPolicy retryPolicy = new ExponentialBackoffRetry(10, 100);
		curator = CuratorFrameworkFactory.newClient("zookeeper", retryPolicy);
		curator.start();
	}

	/**
	 * Add a primary property source to the application context, populated from
	 * a pre-existing ZooKeeper node.
	 */
	public void initialize(ConfigurableApplicationContext applicationContext) {
		logger.trace("Attempting to add ZooKeeper-derived properties to ApplicationContext PropertySources");

		try {
			populateProjectProperties();
			Properties properties = populatePropertiesFromZooKeeper();
			PropertiesPropertySource propertySource = new PropertiesPropertySource(
					"zookeeper", properties);
			applicationContext.getEnvironment().getPropertySources()
					.addFirst(propertySource);

			logger.debug("Added ZooKeeper-derived properties to ApplicationContext PropertySources");
			curator.close();
		} catch (IOException e) {
			logger.error(
					"IO error attempting to load properties from ZooKeeper", e);
			throw new IllegalStateException(
					"Could not load ZooKeeper configuration");
		} catch (Exception e) {
			logger.error(
					"IO error attempting to load properties from ZooKeeper", e);
			throw new IllegalStateException(
					"Could not load ZooKeeper configuration");
		} finally {
			if (curator != null && curator.isStarted()) {
				curator.close();
			}
		}
	}

	/**
	 * Populate the Maven artifact name and version from a property file that
	 * should be on the classpath, with values entered via Maven filtering.
	 * 
	 * There is a way of doing these with manifests, but it's a right faff when
	 * creating shaded uber-jars.
	 * 
	 * @throws IOException
	 */
	private void populateProjectProperties() throws IOException {
		logger.trace("Attempting to get project name and version from properties file");

		try {
			ResourcePropertySource projectProps = new ResourcePropertySource(
					"project.properties");
			this.projectName = (String) projectProps
					.getProperty("project.name");
			this.projectVersion = (String) projectProps
					.getProperty("project.version");
		} catch (IOException e) {
			logger.error("IO error trying to find project name and version, in order to get properties from ZooKeeper");
		}
	}

	/**
	 * Do the actual loading of properties.
	 * 
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	private Properties populatePropertiesFromZooKeeper() throws Exception,
			IOException {
		logger.debug("Attempting to get properties from ZooKeeper");

		try {
			byte[] bytes = curator.getData()
					.forPath(
							"/distributed-config/" + projectName + "/"
									+ projectVersion);
			InputStream in = new ByteArrayInputStream(bytes);
			Properties properties = new Properties();
			properties.load(in);
			return properties;
		} catch (NoNodeException e) {
			logger.error(
					"Could not load application configuration from ZooKeeper as no node existed for project [{}]:[{}]",
					projectName, projectVersion);
			throw e;
		}

	}

}
