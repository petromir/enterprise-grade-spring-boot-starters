# data-conversion-spring-boot-starter

Various converters for handling common data format - Json, Csv, Pdf, Excel etc.

The project uses Java 21

## Usage
### Code

```java
@RequiredArgsConstructor
public class MyDomainService() {

	private final JsonConverter jsonConverter;
	private final CsvConverter csvConverter;
	private final PdfConverter pdfConverter;
	private final ExcelConverter excelConverter;
	private final XmlConverter xmlConverter;
}
```
Examples of all operations with:
1. `JsonConverter` could be found [here](./src/test/java/com/petromirdzhunev/spring/boot/conversion/json/JsonConverterTest.java)
2. `CsvConverter` could be found [here](./src/test/java/com/petromirdzhunev/spring/boot/conversion/json/CsvConverterTest.java)
3. `PdfConverter` could be found [here](./src/test/java/com/petromirdzhunev/spring/boot/conversion/json/PdfConverterTest.java)
4. `ExcelConverter` could be found [here](./src/test/java/com/petromirdzhunev/spring/boot/conversion/json/ExcelConverterTest.java)
5. `XmlConverter` could be found [here](./src/test/java/com/petromirdzhunev/spring/boot/conversion/json/XmlConverterTest.java)

### Maven
#### Import the bom
TODO: Fill this section when the bom is ready

#### Add the dependency without a version
```xml
<dependencies>
    <dependency>
        <groupId>com.petromirdzhunev</groupId>
        <artifactId>data-conversion-spring-boot-starter</artifactId>
    </dependency>
</dependencies>
```

## Development

### Build and publish `SNAPSHOT` versions to local repository
Change the version by adding a `-SNAPSHOT` suffix in the [pom.xml](pom.xml) file and then execute:
```shell
(cd .. && mvnd -B -pl .,data-conversion-spring-boot-starter clean install)
```

### Pointing to a local version
```xml
<dependencies>
    <dependency>
        <groupId>com.petromirdzhunev.libraries</groupId>
        <artifactId>data-conversion-spring-boot-starter</artifactId>
        <version>${latest_version}-SNAPSHOT</version>
    </dependency>
</dependencies>
```