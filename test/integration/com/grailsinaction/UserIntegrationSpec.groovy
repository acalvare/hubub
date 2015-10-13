package com.grailsinaction

import grails.test.spock.IntegrationSpec
import spock.lang.*

class UserIntegrationSpec extends Specification {

    def "Saving our first user to the database" () {
        given: "A brand new user"
        def joe = new User(loginId: "joe", password: "secret")

        when: "The user is saved"
        joe.save()

        then: "It saved successfully and can be found in the database"
        joe.errors.errorCount == 0
        joe.id != null
        User.get(joe.id).loginId == joe.loginId
    }

    def "Updating a saved user changes its properties"() {

        given: "An exisiting user"
        def existingUser = new User(loginId: 'joe', password:'secret')
        existingUser.save(failOnError: true)

        when: "A property is changed"
        def foundUser = User.get(existingUser.id)
        foundUser.password = 'sesame'
        foundUser.save(failOnError: true)

        then: "The change is reflected in the database"
       User.get(existingUser.id).password == 'sesame'
    }

    def "Deleting an exisiting user removes it from the database"() {

        given: "An existing user"
        def user = new User(loginId: 'joe', password:'secret')
        user.save(failOnError: true)

        when: "The user is deleted"
        def foundUser = User.get(user.id)
        foundUser.delete(flush: true)

        then: "The user is removed from the database"
        !User.exists(foundUser.id)
    }

    def "Saving a user with invalid properties causes an error"(){
        given: "A user which fails several field validations"
        def user = new User(loginId: 'joe', password: 'tiny')

        when: "The user is validated"
        user.validate()

        then:
        user.hasErrors()

        "size.toosmall" == user.errors.getFieldError("password").code
        "tiny"==user.errors.getFieldError("password").rejectedValue
        !user.errors.getFieldError("loginId")
    }
}
