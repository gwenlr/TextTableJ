/*
 * Copyright 2021 [name of copyright owner]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.benveg.texttablej;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

import static org.benveg.texttablej.ConfigurationPropertyNames.COLUMN_SEPARATOR;
import static org.benveg.texttablej.ConfigurationPropertyNames.ROW_SEPARATOR;

public class TextTableBuilder {

    private final List<Column> columns = new ArrayList<>();
    private final List<Object> itemList = new ArrayList<>();
    private boolean displayHeader = true;
    private String nullValue = "null";

    private final Map<String, Object> configurationProperties = new HashMap<>();

    private final TableContentFormatter contentGenerator = new CsvTableContentFormatter();

    public TextTableBuilder() {
        configurationProperties.put(COLUMN_SEPARATOR, ",");
        configurationProperties.put(ROW_SEPARATOR, "\n");
    }


    @NotNull
    public TextTableBuilder columns(String... columnNames) {
        if (columnNames.length == 0) {
            throw new IllegalArgumentException("columnNames argument shall not be empty");
        }

        Arrays.stream(columnNames)
                .map(Column::new)
                .forEach(columns::add);
        return this;
    }


    @NotNull
    public TextTableBuilder addAll(@NotNull Object... items) {
        itemList.addAll(Arrays.asList(items));
        return this;
    }

    @NotNull
    public TextTableBuilder addAll(@NotNull Collection<?> items) {
        itemList.addAll(items);
        return this;
    }


    @NotNull
    public TextTableBuilder add(@NotNull Object item) {
        itemList.add(item);
        return this;
    }


    @NotNull
    public TextTableBuilder header(boolean display) {
        displayHeader = display;
        return this;
    }


    @NotNull
    public TextTableBuilder separator(@NotNull String separator) {
        configurationProperties.put(COLUMN_SEPARATOR, separator);
        return this;
    }


    @NotNull
    public TextTableBuilder endOfLine(@NotNull String endOfLine) {
        configurationProperties.put(ROW_SEPARATOR, endOfLine);
        return this;
    }


    @NotNull
    public TextTableBuilder nullValue(@NotNull String nullValue) {
        this.nullValue = nullValue;
        return this;
    }


    @NotNull
    public String toCsv() {
        contentGenerator.reset(configurationProperties);

        if (columns.isEmpty()) {
            throw new ColumnNotDefinedException();
        }

        if (displayHeader) {
            List<String> columnNames = extractColumnNames();
            contentGenerator.displayHeader(columnNames);
        }

        for (Object item : itemList) {
            List<String> rowValues = extractRowValues(item);
            contentGenerator.displayRow(rowValues);
        }

        return contentGenerator.generate();
    }

    @NotNull
    private List<String> extractColumnNames() {
        return columns.stream()
                .map(Column::getName)
                .collect(Collectors.toList());
    }

    @NotNull
    private List<String> extractRowValues(Object item) {
        return columns.stream()
                .map(it -> it.extractValue(item))
                .map(it -> it == null ? nullValue : it)
                .collect(Collectors.toList());
    }
}