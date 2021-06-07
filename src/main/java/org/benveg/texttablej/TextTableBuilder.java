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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TextTableBuilder {

    private final List<Column> columns = new ArrayList<>();
    private final List<Object> itemList = new ArrayList<>();
    private boolean displayHeader = true;
    private String separator = ",";
    private String endOfLine = "\n";
    private String nullValue = "null";


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
        this.separator = separator;
        return this;
    }


    @NotNull
    public TextTableBuilder endOfLine(@NotNull String endOfLine) {
        this.endOfLine = endOfLine;
        return this;
    }

    @NotNull
    public TextTableBuilder nullValue(@NotNull String nullValue) {
        this.nullValue = nullValue;
        return this;
    }


    @NotNull
    public String toCsv() {
        if (columns.isEmpty()) {
            throw new ColumnNotDefinedException();
        }

        StringBuilder builder = new StringBuilder();
        if (displayHeader) {
            String header = createHeader();
            builder.append(header).append(endOfLine);
        }

        for (Object item : itemList) {
            String row = createItemRow(item);
            builder.append(row).append(endOfLine);
        }

        if (!itemList.isEmpty()) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }


    private String createHeader() {
        return columns.stream()
                .map(Column::getName)
                .collect(Collectors.joining(separator));
    }


    private String createItemRow(Object item) {
        return columns.stream()
                .map(it -> it.extractValue(item))
                .map(it -> it == null ? nullValue : it)
                .collect(Collectors.joining(separator));
    }
}