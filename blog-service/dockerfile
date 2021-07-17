FROM sapmachine/jdk11
MAINTAINER 95217902@qq.com
VOLUME /tmp
EXPOSE 8181
ADD target/blog-service-0.0.1-SNAPSHOT.jar /blog-service.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/blog-service.jar","--spring.profiles.active=prod"]
