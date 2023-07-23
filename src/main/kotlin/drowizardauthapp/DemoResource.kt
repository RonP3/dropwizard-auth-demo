package drowizardauthapp

import drowizardauthapp.auth.model.DemoUser
import io.dropwizard.auth.Auth
import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path

// Step 5: Create a resource class
@Path("/")
class DemoResource {

    @Path("hello_world")
    @GET
    @RolesAllowed("ADMIN", "USER")
    fun helloWorld(@Auth user: DemoUser) = "Hello World ${user.email} :)"
}
