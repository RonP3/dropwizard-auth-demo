package drowizardauthapp.auth


import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import drowizardauthapp.auth.model.DemoUser
import drowizardauthapp.auth.model.Role
import drowizardauthapp.auth.model.DemoCredentials
import io.dropwizard.auth.Authenticator

import java.util.*


class DemoAuthenticator : Authenticator<DemoCredentials, DemoUser> { // Step 7 - Create custom authenticator
    override fun authenticate(credentials: DemoCredentials): Optional<DemoUser> {
        val (publicRsaKey, _) = RSAKeysLoader.loadKeys()
        val algorithm: Algorithm = Algorithm.RSA256(publicRsaKey, null)

        val verifier: JWTVerifier = JWT.require(algorithm)
            .withIssuer("ron")
            .withClaimPresence("email")
            .withClaimPresence("role")
            .build()

        return try {
            val decodedJWT: DecodedJWT = verifier.verify(credentials.jwt) // Step 8 - Verify JWT and return user
            Optional.of(
                DemoUser(
                    email = decodedJWT.getClaim("email").asString(),
                    role = Role.valueOf(decodedJWT.getClaim("role").asString())
                )
            )
        } catch (e: Exception) {
            println("invalid jwt: ${e.message}")
            Optional.empty()
        }
    }
}

// Jump to real Auth0JwtAuthenticator + RobinConfigurableAuthenticator
