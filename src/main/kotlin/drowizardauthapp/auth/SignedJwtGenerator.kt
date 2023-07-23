package drowizardauthapp.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import drowizardauthapp.auth.model.Role
import java.io.FileWriter
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.*

fun main(args: Array<String>) {
    when(args[0]) {
        "generateKeys" -> generateRsaKeys()
        "generateJwt" -> generateSignedJwt()
        else -> println("Unknown command")
    }

}

fun generateSignedJwt() {
    val (_, privateRsaKey) = RSAKeysLoader.loadKeys()

    val nowMillis = System.currentTimeMillis()
    val now = Date(nowMillis)

    // Configure JWT
    val algorithm: Algorithm = Algorithm.RSA256(null, privateRsaKey)
    val token = JWT.create()
        .withIssuer("ron")
        .withSubject("I")
        .withIssuedAt(now)
        .withClaim("email", "dropwizard_auth_demo@example.com")
        .withClaim("role", Role.USER.name)
        .withExpiresAt(Date(nowMillis + 30*60000)) // expire in 30 minute
        .sign(algorithm)

    println(token)
}

fun generateRsaKeys() {
    val generator = KeyPairGenerator.getInstance("RSA")
    generator.initialize(2048)
    val pair = generator.generateKeyPair()
    val publicKey = pair.public as RSAPublicKey
    val privateKey = pair.private as RSAPrivateKey

    // Write public key to file
    FileWriter("public_key.pem").use { out ->
        val publicKeyString = Base64.getEncoder().encodeToString(publicKey.encoded)
        out.write("-----BEGIN PUBLIC KEY-----\n")
        out.write(publicKeyString.chunked(64).joinToString("\n"))
        out.write("\n-----END PUBLIC KEY-----\n")
    }

    // Write private key to file
    FileWriter("private_key.pem").use { out ->
        val privateKeyString = Base64.getEncoder().encodeToString(privateKey.encoded)
        out.write("-----BEGIN PRIVATE KEY-----\n")
        out.write(privateKeyString.chunked(64).joinToString("\n"))
        out.write("\n-----END PRIVATE KEY-----\n")
    }
}
