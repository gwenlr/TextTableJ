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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ColumnTest {

    @Test
    void constructor_shallNotAcceptBlank() {
        assertThatThrownBy(
                () -> new Column("")
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_shallAcceptNoBlank() {
        new Column("Label");
    }


    @Test
    void getName_shallReturnName() {
        assertThat(
                new Column("Label").getName()
        ).isEqualTo("Label");
    }

    @SuppressWarnings({"unused", "SameReturnValue"})
    static class Fruit {
        @SuppressWarnings("SameReturnValue")
        public String getName() {
            return "Banana";
        }
        public boolean isExotic() {
            return true;
        }
    }

    @Test
    void extractValue_shallNotAcceptItemWithNoGetterMethod() {
        Column column = new Column("Label");
        assertThatThrownBy(
                () -> column.extractValue(new Fruit())
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void extractValue_shallAcceptGetMethod() {
        Column column = new Column("Name");
        assertThat(
                 column.extractValue(new Fruit())
        ).isEqualTo("Banana");
    }


    @Test
    void extractValue_shallAcceptIsMethod() {
        Column column = new Column("Exotic");
        assertThat(
                column.extractValue(new Fruit())
        ).isEqualTo("true");
    }
}