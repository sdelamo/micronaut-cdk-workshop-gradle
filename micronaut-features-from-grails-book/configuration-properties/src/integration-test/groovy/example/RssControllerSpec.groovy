package example

import grails.testing.mixin.integration.Integration
import grails.testing.spock.OnceBefore
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.DefaultHttpClientConfiguration
import spock.lang.Shared
import spock.lang.Specification
import io.micronaut.http.client.HttpClient

@Integration
class GithubControllerSpec extends Specification {
    @Shared
    HttpClient httpClient

    @OnceBefore
    void init() {
        String baseUrl = "http://localhost:$serverPort"
        DefaultHttpClientConfiguration config = new DefaultHttpClientConfiguration()
        config.followRedirects = false
        this.httpClient = HttpClient.create(baseUrl.toURL(), config)
    }

    def cleanupSpec() {
        this.httpClient.close()
    }

    void "use retrieve method if you are only interested in the response body"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        client.retrieve('/github')

        then:
        noExceptionThrown()
    }

    void "use exchange method if you are interested in the HTTP response wrapper"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        HttpResponse<String> response = client.exchange('/github', String)

        then:
        noExceptionThrown()
        HttpStatus.OK == response.status()

    }
}
