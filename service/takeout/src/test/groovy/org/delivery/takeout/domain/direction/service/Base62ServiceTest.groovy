package org.delivery.takeout.domain.direction.service

import spock.lang.Specification

class Base62ServiceTest extends Specification {

    private Base62Service base62Service;

    def setup(){
        base62Service = new Base62Service()
    }

    def "base62 - encode/decode"(){
        given:
        long num = 10

        when:
        def encodedId = base62Service.encodeDirectionId(num)
        def decodedId = base62Service.decodeDirectionId(encodedId)

        then:
        num == decodedId
        println(encodedId)
    }
}
