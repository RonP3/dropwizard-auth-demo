package drowizardauthapp.auth

import drowizardauthapp.auth.model.DemoUser
import drowizardauthapp.auth.model.Role
import io.dropwizard.auth.Authorizer
import jakarta.ws.rs.container.ContainerRequestContext


class DemoAuthorizer: Authorizer<DemoUser> { // Step 9 - Create a custom Authorizer
    override fun authorize(user: DemoUser, role: String, requestContext: ContainerRequestContext?): Boolean {
        return Role.valueOf(role) == user.role
    }
}
