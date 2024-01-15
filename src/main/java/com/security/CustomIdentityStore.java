package com.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.CallerPrincipal;
import jakarta.security.enterprise.credential.RememberMeCredential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

import jakarta.security.enterprise.identitystore.RememberMeIdentityStore;

@ApplicationScoped
public class CustomIdentityStore implements IdentityStore{

  
    	    public CredentialValidationResult validate(UsernamePasswordCredential credential) {
    	        // Hardcoded username and password check
    	        if ("user".equals(credential.getCaller()) && "ok".equals(credential.getPasswordAsString())) {
    	            return new CredentialValidationResult("hardcodedUsername", new HashSet<>(Arrays.asList("admin")));
    	        }
    	        return CredentialValidationResult.INVALID_RESULT;
    	    }
    



}
