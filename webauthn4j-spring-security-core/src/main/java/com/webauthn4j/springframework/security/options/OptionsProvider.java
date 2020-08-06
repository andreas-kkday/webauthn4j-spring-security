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

package com.webauthn4j.springframework.security.options;

import com.webauthn4j.data.PublicKeyCredentialCreationOptions;
import com.webauthn4j.data.PublicKeyCredentialRequestOptions;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides {@link PublicKeyCredentialCreationOptions} and {@link PublicKeyCredentialRequestOptions} for {@link HttpServletRequest}
 */
public interface OptionsProvider {


    /**
     * provides {@link PublicKeyCredentialCreationOptions}. If <code>username</code> is <code>null</code>, <code>user</code>, <code>credentials</code> are not populated.
     *
     * @param request   request
     * @param principal principal
     * @return {@link PublicKeyCredentialCreationOptions} instance
     */
    PublicKeyCredentialCreationOptions getAttestationOptions(HttpServletRequest request, Object principal);

    /**
     * provides {@link PublicKeyCredentialRequestOptions}. If <code>username</code> is <code>null</code>, <code>credentials</code> are not populated.
     *
     * @param request   request
     * @param principal principal
     * @return {@link PublicKeyCredentialRequestOptions} instance
     */
    PublicKeyCredentialRequestOptions getAssertionOptions(HttpServletRequest request, Object principal);

    /**
     * returns effective rpId based on request origin and configured <code>rpId</code>.
     *
     * @param request request
     * @return effective rpId
     */
    String getEffectiveRpId(HttpServletRequest request);

}
