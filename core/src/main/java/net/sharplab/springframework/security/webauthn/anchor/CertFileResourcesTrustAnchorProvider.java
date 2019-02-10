/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sharplab.springframework.security.webauthn.anchor;

import com.webauthn4j.anchor.CachingTrustAnchorProviderBase;
import com.webauthn4j.response.attestation.authenticator.AAGUID;
import com.webauthn4j.util.AssertUtil;
import com.webauthn4j.util.CertificateUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.stream.Collectors;

public class CertFileResourcesTrustAnchorProvider extends CachingTrustAnchorProviderBase implements InitializingBean {

    private List<Resource> certificates;

    public CertFileResourcesTrustAnchorProvider() {
    }

    public CertFileResourcesTrustAnchorProvider(List<Resource> certificates) {
        this.certificates = certificates;
    }

    @Override
    public void afterPropertiesSet() {
        checkConfig();
    }

    private void checkConfig() {
        AssertUtil.notNull(certificates, "certificates must not be null");
    }

    @Override
    protected Map<AAGUID, Set<TrustAnchor>> loadTrustAnchors() {
        Set<TrustAnchor> trustAnchors = certificates.stream().map(this::loadTrustAnchor).collect(Collectors.toSet());
        return Collections.singletonMap(AAGUID.NULL, trustAnchors);
    }

    public List<Resource> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Resource> certificates) {
        this.certificates = certificates;
    }

    private TrustAnchor loadTrustAnchor(Resource certificate) {
        checkConfig();
        try {
            X509Certificate x509Certificate = CertificateUtil.generateX509Certificate(certificate.getInputStream());
            return new TrustAnchor(x509Certificate, null);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
