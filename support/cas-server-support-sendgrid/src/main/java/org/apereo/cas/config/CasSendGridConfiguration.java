package org.apereo.cas.config;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.mail.SendGridEmailSender;
import org.apereo.cas.notifications.mail.EmailSender;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;

import com.sendgrid.SendGridAPI;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.HierarchicalMessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * This is {@link CasSendGridConfiguration}.
 *
 * @author Misagh Moayyed
 * @since 7.0.0
 */
@EnableConfigurationProperties(CasConfigurationProperties.class)
@ConditionalOnFeatureEnabled(feature = CasFeatureModule.FeatureCatalog.Notifications, module = "sendgrid")
@AutoConfiguration
public class CasSendGridConfiguration {
    @Bean
    @RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
    @ConditionalOnMissingBean(name = "sendGridEmailSender")
    public EmailSender emailSender(
        @Qualifier("messageSource") final HierarchicalMessageSource messageSource,
        @Qualifier("sendGrid") final SendGridAPI sendGridAPI) {
        return new SendGridEmailSender(sendGridAPI, messageSource);
    }

}
