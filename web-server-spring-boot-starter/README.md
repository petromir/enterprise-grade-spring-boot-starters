# web-server-spring-boot-starter

A Spring Boot starter provides all the necessary components, filters, and exception handling when using Spring 
MVC in enterprise applications.

The project uses Java 21

## Usage
### Code
#### Exception handling
By default, a base exception handler is defined to process predefined exceptions and return a response which
adheres to [RFC 7807](https://datatracker.ietf.org/doc/html/rfc7807) specification.
Extending `BaseHttpExceptionHandler` class allows you to simplify the handling of your domain exception, e.g.

```java
public class EntityNotFoundException extends RuntimeException {

	public EntityNotFoundException(String message) {
		super(message);
	}
}

@RestControllerAdvice
public class HttpExceptionHandler extends GlobalHttpExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ProblemDetail handleEntityNotFoundException(final EntityNotFoundException exception) {
		return handleCustomException(exception, HttpStatus.NOT_FOUND);
	}
}
```

### Maven
#### Import the bom
TODO: Fill this section when the bom is ready

#### Add the dependency
TODO: Once the bom is imported, adjust the example to add the dependency without a version
```xml
<dependencies>
    <dependency>
        <groupId>com.petromirdzhunev.libraries</groupId>
        <artifactId>web-server-spring-boot-starter</artifactId>
        <version>${latest-web-server-spring-boot-starter-version}</version>
    </dependency>
</dependencies>
```

## Development

### Build and publish `SNAPSHOT` versions to local repository
#### Switch to `SNAPSHOT` version
```shell
mvnd versions:set -DnewVersion=$(mvnd -B help:evaluate -Dexpression=project.version -DforceStdout | grep -E "^[0-9]+\.[0-9]+\.[0-9]+")-SNAPSHOT
```

#### Push `SNAPSHOT` version to the local repository
```shell
(cd .. && mvnd -B -pl .,web-server-spring-boot-starter clean install)
```

#### Point to the `SNAPSHOT` version
Go to the project where `web-server-spring-boot-starter` library is used.
Run the following command to point to the `SNAPSHOT` version:
```shell
mvnd versions:use-latest-snapshots -Dincludes=com.petromirdzhunev:web-server-spring-boot-starter -DallowMajorUpdates=true -DallowMinorUpdates=true
```

#### Revert to previous version
When you finish the development you can revert to the previous version by running:
```shell
mvnd versions:revert -Dincludes=com.petromirdzhunev:web-server-spring-boot-starter
```