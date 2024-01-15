package com.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.identitystore.IdentityStore;

@FormAuthenticationMechanismDefinition(
loginToContinue = @LoginToContinue(loginPage = "/login.xhtml"))
@ApplicationScoped
public class FormAuthenticationConfig{
	
	@Produces
    public IdentityStore createIdentityStore() {
        return new CustomIdentityStore();
    }
		} 