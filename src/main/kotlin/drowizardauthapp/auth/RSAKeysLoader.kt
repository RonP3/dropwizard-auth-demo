package drowizardauthapp.auth

import java.io.File
import java.nio.file.Files
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

data class RSAKeys(val publicKey: RSAPublicKey, val privateKey: RSAPrivateKey)

object RSAKeysLoader {
    fun loadKeys(): RSAKeys {
        // Load public key
        val publicKeyContent = String(Files.readAllBytes(File("public_key.pem").toPath()))
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replace("\\s+".toRegex(), "")
        val publicKeySpec = X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent))
        val publicKey = KeyFactory.getInstance("RSA").generatePublic(publicKeySpec) as RSAPublicKey

        // Load private key
        val privateKeyContent = String(Files.readAllBytes(File("private_key.pem").toPath()))
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replace("\\s+".toRegex(), "")
        val privateKeySpec = PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent))
        val privateKey = KeyFactory.getInstance("RSA").generatePrivate(privateKeySpec) as RSAPrivateKey

        return RSAKeys(publicKey, privateKey)
    }
}
