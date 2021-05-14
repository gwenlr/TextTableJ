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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TextTableBuilderTest {

    private TextTableBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new TextTableBuilder();
    }


    @Test
    void columns_shallNotAcceptNoParameters() {
        assertThatThrownBy(
                () -> new TextTableBuilder()
                        .columns())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void columns_shallAcceptSeveralParameters() {
        assertThat(builder
                .columns("label", "x", "y"))
                .isSameAs(builder);
    }

    @Test
    void add_shallAcceptObject() {
        assertThat(builder
                .columns("label", "x", "y")
                .add(new LabelPoint("A", 1, 2))
                .add(new LabelPoint("B", 3, 1))
                .toCsv())
                .isEqualTo("""
                        label,x,y
                        A,1,2
                        B,3,1""");
    }


    @Test
    void header_shallPrintHeaderWhenEnabled() {
        assertThat(builder
                .columns("Label", "x", "y")
                .add(new LabelPoint("A", 1, 2))
                .add(new LabelPoint("B", 3, 1))
                .header(true)
                .toCsv())
                .isEqualTo("""
                        Label,x,y
                        A,1,2
                        B,3,1""");
    }


    @Test
    void header_shallNotPrintHeaderWhenDisabled() {
        assertThat(builder
                .columns("label", "x", "y")
                .add(new LabelPoint("A", 1, 2))
                .add(new LabelPoint("B", 3, 1))
                .header(false)
                .toCsv())
                .isEqualTo("""
                        A,1,2
                        B,3,1""");
    }


    @Test
    void separator_shallUseDefaultValue() {
        assertThat(builder
                .columns("label", "x", "y")
                .add(new LabelPoint("A", 1, 2))
                .header(false)
                .toCsv())
                .isEqualTo("A,1,2");
    }


    @Test
    void separator_shallUseNewValue() {
        assertThat(builder
                .columns("label", "x", "y")
                .add(new LabelPoint("A", 1, 2))
                .header(false)
                .separator("|")
                .toCsv())
                .isEqualTo("A|1|2");
    }


    @Test
    void endOfLine_shallUseNewValue() {
        assertThat(builder
                .columns("label", "x", "y")
                .add(new LabelPoint("A", 1, 2))
                .add(new LabelPoint("B", 3, 1))
                .header(false)
                .endOfLine("|")
                .toCsv())
                .isEqualTo("A,1,2|B,3,1");
    }


    @Test
    void nullValue_shallUseDefaultValue() {
        assertThat(builder
                .columns("label", "x", "y")
                .add(new LabelPoint(null, 1, 2))
                .header(false)
                .toCsv())
                .isEqualTo("null,1,2");
    }

    @Test
    void nullValue_shallUseNewValue() {
        assertThat(builder
                .columns("label", "x", "y")
                .add(new LabelPoint(null, 1, 2))
                .header(false)
                .nullValue("none")
                .toCsv())
                .isEqualTo("none,1,2");
    }


    @Test
    void addAllArray_shallAcceptEmptyItems() {
        assertThat(
                builder
                        .columns("Label", "x", "y")
                        .addAll()
                        .header(false)
                        .toCsv())
                .isEqualTo("");
    }

    @Test
    void addAllArray_shallAcceptSeveralItems() {
        assertThat(builder
                .columns("label", "x", "y")
                .addAll(new LabelPoint("A", 1, 2), new LabelPoint("B", 3, 1))
                .header(false)
                .toCsv())
                .isEqualTo("""
                        A,1,2
                        B,3,1""");
    }


    @Test
    void addAllCollection_shallAcceptEmptyItems() {
        assertThat(
                builder
                        .columns("Label", "x", "y")
                        .addAll(Collections.emptyList())
                        .header(false)
                        .toCsv())
                .isEqualTo("");
    }

    @Test
    void addAllCollection_shallAcceptSeveralItems() {
        assertThat(builder
                .columns("label", "x", "y")
                .addAll(Arrays.asList(new LabelPoint("A", 1, 2), new LabelPoint("B", 3, 1)))
                .header(false)
                .toCsv())
                .isEqualTo("""
                        A,1,2
                        B,3,1""");
    }

    @Test
    void toCsv_shallNotBeCalledWhenColumnsNotDefined() {
        assertThatThrownBy(
                () -> new TextTableBuilder()
                        .toCsv())
                .isInstanceOf(ColumnNotDefinedException.class);
    }
}