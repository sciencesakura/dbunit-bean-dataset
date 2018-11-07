# dbunit-bean-dataset

This is an extension for [DbUnit](http://dbunit.sourceforge.net/) that provides an ITable implementation treating POJOs as a table.

You can easily compare zero or more POJOs and another ITable — database table, CSV, Excel and so on — using DbUnit's assertion feature.

## Requirements

- Java 1.6 or later

## Usage

You can obtain a `BeanTable` instance by using its builder object that provides chainable methods.

```java
List<Customer> actual = sut.findAll();
// create BeanTable instance
ITable actualTable = new BeanTable.Builder<>(Customer.class)
        .add(actual)                         // appends the POJOs
        .naming(Naming.CAMEL_TO_SNAKE)       // naming convention for columns
        .exclude("created_at", "updated_at") // excludes the properties
        .build();                            // returns BeanTable instance
Assertion.assertEquals(expected, actualTable);
```

## Licence

MIT
