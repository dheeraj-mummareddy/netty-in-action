import spock.lang.Specification

/**
 * Created by Dheeraj Mummareddy on 11/2/16.
 */
class HelloWorldAppSpec extends Specification {
    def setupSpec() {
    }

    def cleanupSpec() {
    }

    def setup() {
    }

    def cleanup() {
    }

    def "canary"() {
        HelloWorldApp.main()
        expect:
        true
    }
}
