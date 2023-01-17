package example

import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("vcs.github-microsoft")
class GithubConfigurationProperties implements GithubConfiguration {
    String organization
    String repository
}
