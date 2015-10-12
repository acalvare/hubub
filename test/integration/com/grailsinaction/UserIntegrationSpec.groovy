package com.grailsinaction

import grails.test.spock.IntegrationSpec
import spock.lang.*

class UserIntegrationSpec extends Specification {

    def "Saving our first user to the database" () {
        given: "A brand new user"
        def joe = new User(loginId: "joe", password: "secret", homepage : "www.google.com")

        when: "The user is saved"
        joe.save()

        then: "It saved successfully and can be found in the database"
        joe.errors.errorCount == 0
        joe.id != null
        User.get(joe.id).loginId != joe.loginId
    }

}
