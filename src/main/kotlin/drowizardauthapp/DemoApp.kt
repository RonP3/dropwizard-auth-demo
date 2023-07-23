package drowizardauthapp

import drowizardauthapp.auth.DemoAuthFilter
import drowizardauthapp.auth.DemoAuthenticator
import drowizardauthapp.auth.DemoAuthorizer
import drowizardauthapp.auth.model.DemoUser
import drowizardauthapp.configurations.MyAppConfig
import io.dropwizard.auth.AuthDynamicFeature
import io.dropwizard.auth.AuthValueFactoryProvider
import io.dropwizard.core.Application
import io.dropwizard.core.setup.Environment
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature

class DemoApp : Application<MyAppConfig>() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            DemoApp().run(*args)
        }
    }

    override fun run(configuration: MyAppConfig, environment: Environment) { // Step 10 - wiring
        environment.jersey().register(DemoResource())
        val authFilter = DemoAuthFilter(authenticator = DemoAuthenticator(), authorizer = DemoAuthorizer())
        environment.jersey().register(AuthDynamicFeature(authFilter)) // wire up the auth filter
        environment.jersey().register(RolesAllowedDynamicFeature::class.java) // for @RolesAllowed annotation
        environment.jersey().register(AuthValueFactoryProvider.Binder(DemoUser::class.java)) // for @Auth annotation
    }
}
