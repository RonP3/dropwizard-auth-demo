package drowizardauthapp.auth.model

import java.security.Principal


enum class Role { // Step 3 - Create Roles
    ADMIN,
    USER,
    GUEST
}

data class DemoUser(val email: String, val role: Role): Principal { // Step 4 - Create Principal user
    override fun getName(): String {
        return email
    }
}
