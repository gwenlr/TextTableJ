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

        for (String columnName : columnNames) {
            Column column = new Column(columnName);
            columns.add(column);
        }

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
        this.displayHeader = display;
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
        if( columns.isEmpty()) {
            throw new ColumnNotDefinedException();
        }

        StringBuilder builder = new StringBuilder();
        if (displayHeader) {
            appendHeader(builder);
            builder.append(endOfLine);
        }

        for (Object item : itemList) {
            appendRow(builder, item);
            builder.append(endOfLine);
        }
        if (!itemList.isEmpty()) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }


    private void appendHeader(StringBuilder builder) {
        boolean first = true;
        for (Column column : columns) {
            if (first) {
                first = false;
            } else {
                builder.append(separator);
            }
            builder.append(column.getName());
        }
    }


    private void appendRow(StringBuilder builder, Object item) {
        boolean first = true;
        for (Column column : columns) {
            if (first) {
                first = false;
            } else {
                builder.append(separator);
            }
            String extractedValue = column.extractValue(item);
            String value = extractedValue == null ? nullValue : extractedValue;
            builder.append(value);
        }
    }

}