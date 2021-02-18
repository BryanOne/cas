package org.apereo.cas.initializr.contrib.gradle;

import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apereo.cas.initializr.contrib.TemplatedProjectContributor;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Setter
@Getter
@Accessors(chain = true)
public class OverlayGradlePropertiesContributor extends TemplatedProjectContributor {
    private final Map<String, Object> variables = new HashMap<>();

    private boolean configureApplicationServerType;

    public OverlayGradlePropertiesContributor(final ApplicationContext applicationContext) {
        super(applicationContext, "./gradle.properties", "classpath:common/gradle/gradle.properties");
    }

    public OverlayGradlePropertiesContributor putVariable(final String key, final Object value) {
        variables.put(key, value);
        return this;
    }
    
    private static void handleApplicationServerType(ProjectDescription project, Map<String, Object> defaults) {
        val dependencies = project.getRequestedDependencies();
        var appServer = "-tomcat";
        if (dependencies.containsKey("webapp-jetty")) {
            appServer = "-jetty";
        } else if (dependencies.containsKey("webapp-undertow")) {
            appServer = "-undertow";
        }
        defaults.put("appServer", appServer);
    }

    @Override
    protected Object contributeInternal(final ProjectDescription project) {
        val provider = applicationContext.getBean(InitializrMetadataProvider.class);

        val defaults = provider.get().defaults();
        if (configureApplicationServerType) {
            handleApplicationServerType(project, defaults);
        }
        var configuration = provider.get().getConfiguration();
        var boms = configuration.getEnv().getBoms();

        defaults.put("casVersion", boms.get("cas-bom").getVersion());
        defaults.put("casMgmtVersion", boms.get("cas-mgmt-bom").getVersion());
        defaults.put("springBootVersion", defaults.get("bootVersion"));

        defaults.putAll(this.variables);

        return defaults;
    }
}
