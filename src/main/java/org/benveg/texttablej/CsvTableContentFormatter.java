/*
 * Copyright 2021
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
import java.util.List;
import java.util.Map;

import static org.benveg.texttablej.ConfigurationPropertyNames.COLUMN_SEPARATOR;
import static org.benveg.texttablej.ConfigurationPropertyNames.ROW_SEPARATOR;

public class CsvTableContentFormatter implements TableContentFormatter {

    private String separator = ",";
    private String endOfLine = "\n";

    private final List<String> tableContent = new ArrayList<>();


    @Override
    public @NotNull TableContentFormatter reset(@NotNull Map<String, Object> configurationProperties) {
        tableContent.clear();
        separator = (String) configurationProperties.get(COLUMN_SEPARATOR);
        endOfLine = (String) configurationProperties.get(ROW_SEPARATOR);
        return this;
    }

    @Override
    @NotNull
    public TableContentFormatter displayHeader(@NotNull List<String> columnNames) {
        String lineContent = String.join(separator, columnNames);
        tableContent.add(lineContent);
        return this;
    }

    @Override
    @NotNull
    public TableContentFormatter displayRow(@NotNull List<String> rowValues) {
        String lineContent = String.join(separator, rowValues);
        tableContent.add(lineContent);
        return this;
    }

    @Override
    public @NotNull String generate() {
        return String.join(endOfLine, tableContent);
    }
}
