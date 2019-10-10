package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return even when number input is even"
    request{
        method GET()
        url("/v1/message") {
        }
    }
    response {
        body("spring cloud contract get message")
        status 200
    }
}