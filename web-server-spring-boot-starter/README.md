# web-server-spring-boot-starter

The library provides all the necessary components, filters, and error handling when using Spring MVC.

The project uses Java 21

## Usage
### Code
TODO: Describe the code changes needed in the project

### Maven
#### Import the bom
TODO: Fill this section when the bom is ready

#### Add the dependency without a version
```xml
<dependencies>
    <dependency>
        <groupId>com.petromirdzhunev.libraries</groupId>
        <artifactId>web-server-spring-boot-starter</artifactId>
    </dependency>
</dependencies>
```

## Development

### Build and publish `SNAPSHOT` versions to local repository
Change the version by adding a `-SNAPSHOT` suffix in the [pom.xml](pom.xml) file and then execute:
```shell
(cd .. && mvnd -B -pl .,web-server-spring-boot-starter clean install)
```

### Pointing to a local version
```xml
<dependencies>
    <dependency>
        <groupId>com.petromirdzhunev.libraries</groupId>
        <artifactId>web-server-spring-boot-starter</artifactId>
        <version>${latest_version}-SNAPSHOT</version>
    </dependency>
</dependencies>
```