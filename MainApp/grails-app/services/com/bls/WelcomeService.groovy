package com.bls

class WelcomeService {

  HelloService helloService
  GoodbyeService goodbyeService

  def sayHello() {
      helloService.sayHello()
    }

  def sayGoodbye() {
    goodbyeService.sayGoodbye()
  }
}
