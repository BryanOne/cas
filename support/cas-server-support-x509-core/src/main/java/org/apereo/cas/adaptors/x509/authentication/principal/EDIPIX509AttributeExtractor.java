package org.apereo.cas.adaptors.x509.authentication.principal;

import org.apereo.cas.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

/**
 * Extracts EDIPI as an attribute in addition to default attributes.
 * Only certain types of certificates have EDIPI numbers so users of those certificates
 * must choose to use this extractor if they want the value extracted.
 * @author Hal Deadman
 * @since 6.4
 */
@Slf4j
public class EDIPIX509AttributeExtractor extends DefaultX509AttributeExtractor {

    /**
     * Get additional attributes from the certificate.
     *
     * @param certificate X509 Certificate of user
     * @return map of attributes
     */
    public Map<String, List<Object>> extractPersonAttributes(final X509Certificate certificate) {
        var personAttributes = super.extractPersonAttributes(certificate);
        val subjectPrincipal = certificate.getSubjectX500Principal();
        val commonName = X509ExtractorUtils.retrieveTheCommonName(subjectPrincipal.getName());
        val edipi = X509ExtractorUtils.retrieveTheEDIPI(commonName);
        if (StringUtils.isNotEmpty(edipi)) {
            personAttributes.put("x509EDIPI", CollectionUtils.wrapList(edipi));
        } else {
            LOGGER.debug("EDIPI not found in certificate common name: [{}]", commonName);
        }
        return personAttributes;
    }
}
