package drowizardauthapp.configurations

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.core.Configuration


class MyAppConfig(@JsonProperty("configTest") val configTest: String): Configuration()
