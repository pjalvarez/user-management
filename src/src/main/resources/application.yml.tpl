server:
  port: _MICROSERVICE_PORT_
eureka:
  instance:
    leaseRenewalIntervalInSeconds: 10
    leaseExpirationDurationInSeconds: 11
  client:
    serviceUrl:
      defaultZone: http://_EUREKA_VHOST_:_EUREKA_PORT_/eureka/
    healthcheck:
      enabled: true
    lease: null
duration: 5
spring:
  application:
    name: _MICROSERVICE_
logging:
  file: logs/${spring.application.name}.log
  level:
    org.springframework.cloud: DEBUG
    
security:
  oauth2:
    tokenTimeout : 3600
    client:
      client-id: select4cities
      client-secret: w-@y7FC)~y#9Kuj.b@_Ltr3n&amF
