package org.delivery.takeout.domain.store.cache

import org.delivery.takeout.AbstractContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate

class RedisTemplateTest extends AbstractContainerBaseTest{

    @Autowired
    private RedisTemplate redisTemplate

    def "Redis - String check"(){
        given:
        def valueOps = redisTemplate.opsForValue()
        def key = "StringKey"
        def value = "julee"

        when:
        valueOps.set(key,value)

        then:
        def result = valueOps.get(key)
        result == value
    }

    def "Redis - Set(중복 제거) check"(){
        given:
        def setOps = redisTemplate.opsForSet()
        def key = "setKey"

        when:
        setOps.add(key,"a","a","b","b","c")

        then:
        def size = setOps.size(key)
        size == 3
    }
}
