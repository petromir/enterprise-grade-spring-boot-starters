# Spring Boot enterprise starters

A set of crafted Spring Boot starters focusing on one common (sometimes grouped) problem in Enterprise Java development.

## Prerequisites
1. [Install SDKMAN](https://sdkman.io/install)
2. Initialize SDKMAN environment
```shell
sdk env install
```
3. Configure Maven settings
    - Copy [settings.xml](ci-cd/.m2/settings.xml) to your local `.m2` folder
    - Secure your Nexus password using the following [Maven guide](https://maven.apache.org/guides/mini/guide-encryption.html)
    - Replace username & password under `<server>` element

## Usage
- [web-server-spring-boot-starter](web-server-spring-boot-starter/README.md)
- [data-conversion-spring-boot-starter](data-conversion-spring-boot-starter/README.md)

## Distribution to Nexus
Distribution to Nexus is configured in two places:
1. [pom.xml](pom.xml) - `<distributionManagement>` tag
2. [settings.xml](ci-cd/.m2/settings.xml) file, passed to Maven during the deployment in GitLab pipeline.
[gitlab-ci.yml](ci-cd/gitlab/.gitlab-ci.yml) file defines the actual execution of the distribution

## TODO
- Automatic incrementation of the tag version
- Publish the starters to Maven Central