package drowizardauthapp.auth

import drowizardauthapp.auth.model.DemoCredentials
import drowizardauthapp.auth.model.DemoUser
import io.dropwizard.auth.AuthFilter
import jakarta.annotation.Priority
import jakarta.ws.rs.Priorities
import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.core.HttpHeaders
import jakarta.ws.rs.core.Response

@Priority(Priorities.AUTHENTICATION) // Step 6 - Create an AuthFilter
class DemoAuthFilter(authenticator: DemoAuthenticator, authorizer: DemoAuthorizer) : AuthFilter<DemoCredentials, DemoUser>() {

    init {
        this.authenticator = authenticator
        this.authorizer = authorizer
    }

    override fun filter(requestContext: ContainerRequestContext) {
        val authHeader = requestContext.headers.getFirst(HttpHeaders.AUTHORIZATION)
            ?: throw WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build())
        if (!authHeader.startsWith("Bearer ")) {
            throw WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build())
        }
        val token = authHeader.substring("Bearer ".length).trim()

        if (!this.authenticate(requestContext, DemoCredentials(token), "Bearer")) {
            throw WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build())
        }
    }
}

// AuthFilter
// Authenticator - 401 unauthorized
// Authorizer - 403 forbidden

// filter order
