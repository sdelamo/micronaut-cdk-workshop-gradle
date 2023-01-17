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
    BlockingHttpClient client

    @OnceBefore
    void init() {
        String baseUrl = "http://localhost:$serverPort"
        DefaultHttpClientConfiguration config = new DefaultHttpClientConfiguration()
        config.followRedirects = false
        this.client = HttpClient.create(baseUrl.toURL(), config).toBlocking()
    }

    void "use retrieve method if you are only interested in the response body"() {
        expect:
        'Hello World' == client.retrieve('/helloWorld')
    }

    void "use exchange method if you are interested in the HTTP response wrapper"() {
        when:
        HttpResponse<String> response = client.exchange('/helloWorld')

        then:
        HttpStatus.OK == response.status()
        'Hello World' == response.body()
    }
}
