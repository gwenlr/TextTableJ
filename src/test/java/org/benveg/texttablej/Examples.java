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

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Examples {

    @Test
    void example1() {
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
    }


    @Test
    void example2() {
        String textTable = new TextTableBuilder()
                .columns("Label", "x", "y")
                .addAll(new LabelPoint("A", 3, 4), new LabelPoint("B", 1, 2))
                .toCsv();

        assertThat(textTable)
                .isEqualTo("""
                        Label,x,y
                        A,3,4
                        B,1,2""");
    }


    @Test
    void example3() {
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
    }


    @Test
    void example4() {
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
    }

    @Test
    void example5() {
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
    }


    @Test
    void example6() {
        String textTable = new TextTableBuilder()
                .columns("Label", "x", "y")
                .header(false)
                .endOfLine("|")
                .add(new LabelPoint("A", 3, 4))
                .add(new LabelPoint("B", 1, 2))
                .toCsv();

        assertThat(textTable)
                .isEqualTo("A,3,4|B,1,2");
    }

    @Test
    void example7() {
        String textTable = new TextTableBuilder()
                .columns("Label", "x", "y")
                .header(false)
                .nullValue("none")
                .add(new LabelPoint(null, 3, 4))
                .toCsv();

        assertThat(textTable)
                .isEqualTo("none,3,4");
    }

}