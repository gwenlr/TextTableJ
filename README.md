# TextTableJ
A simple builder of text table in Java

## Usage


### Overview
The simple usage is the following:
* define the columns
* add the items (one for each row)
* use *toCsv()* to convert into text
```java
    String textTable = new TextTableBuilder()
        .columns("Label", "x", "y")
        .add(new LabelPoint("A", 3, 4))
        .add(new LabelPoint("B", 1, 2))
        .toCsv();

    assertThat(textTable)
        .isEqualTo("""
                        Label,x,y
                        A,3,4
                        B,1,2""");
```

The mapping between the column and the object are made by using the getter generated from the column name.
Thus, for column xyz, the getter methods *getXyz()* and *isXyz()* are called.

### add several items in single call
```java
        String textTable = new TextTableBuilder()
                .columns("Label", "x", "y")
                .addAll(new LabelPoint("A", 3, 4), new LabelPoint("B", 1, 2))
                .toCsv();

        assertThat(textTable)
                .isEqualTo("""
                        Label,x,y
                        A,3,4
                        B,1,2""");

```


### Add list
```java
    List<LabelPoint> pointList = Arrays.asList(new LabelPoint("A", 3, 4), new LabelPoint("B", 1, 2));
    String textTable = new TextTableBuilder()
        .columns("Label", "x", "y")
        .addAll(pointList)
        .toCsv();

    assertThat(textTable)
        .isEqualTo("""
                        Label,x,y
                        A,3,4
                        B,1,2""");
```



### Disable header

This option removes the header from the generated text.

```java
        String textTable = new TextTableBuilder()
                .columns("Label", "x", "y")
                .header(false)
                .add(new LabelPoint("A", 3, 4))
                .add(new LabelPoint("B", 1, 2))
                .toCsv();

        assertThat(textTable)
                .isEqualTo("""
                        A,3,4
                        B,1,2""");
```


### Set separator

This option replaces the separator between column values from the generated text.


```java
        String textTable = new TextTableBuilder()
            .columns("Label", "x", "y")
            .header(false)
            .separator("|")
            .add(new LabelPoint("A", 3, 4))
            .add(new LabelPoint("B", 1, 2))
            .toCsv();

        assertThat(textTable)
            .isEqualTo("""
                            A|3|4
                            B|1|2""");
```


### Set end of line

This option replaces the separator between the lines from the generated text.

```java
        String textTable = new TextTableBuilder()
            .columns("Label", "x", "y")
            .header(false)
            .endOfLine("|")
            .add(new LabelPoint("A", 3, 4))
            .add(new LabelPoint("B", 1, 2))
            .toCsv();

        assertThat(textTable)
            .isEqualTo("A,3,4|B,1,2");
```

### Set end null value
This option set the text associated to a null column value.

```java
        String textTable = new TextTableBuilder()
            .columns("Label", "x", "y")
            .header(false)
            .nullValue("none")
            .add(new LabelPoint(null, 3, 4))
            .toCsv();

        assertThat(textTable)
            .isEqualTo("none,3,4");
```
