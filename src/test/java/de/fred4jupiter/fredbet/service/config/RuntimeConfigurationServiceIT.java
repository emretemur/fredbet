package de.fred4jupiter.fredbet.service.config;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.fred4jupiter.fredbet.AbstractTransactionalIntegrationTest;
import de.fred4jupiter.fredbet.domain.RuntimeConfig;

public class RuntimeConfigurationServiceIT extends AbstractTransactionalIntegrationTest {

	@Autowired
	private RuntimeConfigurationService runtimeConfigurationService;

	@Test
	public void loadAndSaveConfiguration() {
		RuntimeConfig runtimeConfig = runtimeConfigurationService.loadRuntimeConfig();
		assertNotNull(runtimeConfig);

		runtimeConfig.setEnabledParentChildRanking(true);

		runtimeConfigurationService.saveRuntimeConfig(runtimeConfig);

		RuntimeConfig loaded = runtimeConfigurationService.loadRuntimeConfig();
		assertNotNull(loaded);
		assertTrue(loaded.isEnabledParentChildRanking());
	}
}
