package org.apereo.cas.initializr.contrib.gradle;

import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import lombok.val;
import org.apereo.cas.initializr.contrib.TemplatedProjectContributor;
import org.springframework.context.ApplicationContext;

public class OverlayGradleSettingsContributor extends TemplatedProjectContributor {
    public OverlayGradleSettingsContributor(final ApplicationContext applicationContext) {
        super(applicationContext, "./settings.gradle", "classpath:common/gradle/settings.gradle");
    }

    @Override
    protected Object contributeInternal(ProjectDescription project) {
        val provider = applicationContext.getBean(InitializrMetadataProvider.class);
        return provider.get().defaults();
    }
}
