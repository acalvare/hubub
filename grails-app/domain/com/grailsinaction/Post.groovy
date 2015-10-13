package com.grailsinaction

class Post {

    String content
    Date dateCreated

    static hasMany = [tags : Tag]
    static constraints = {
        content blank: false
    }

    static belongsTo = [uesr : User]
}
