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
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

import static java.util.Arrays.asList;

class Column {

    private final String name;
    private final String methodSuffix;

    public Column(@NotNull String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("name argument shall not be blank");
        }
        this.name = name;
        this.methodSuffix = name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    @NotNull
    public String getName() {
        return name;
    }

    @Nullable
    public String extractValue(@NotNull Object item) {
        for( String methodPrefix : asList("get", "is")) {
            Method method = findMethod(item, methodPrefix+methodSuffix);
            if( method != null) {
                return extractValue(item, method);
            }
        }

        throw  new IllegalArgumentException("Cannot found getter for column '"+name+"' in item '"+item+"'");
    }

    @Nullable
    private Method findMethod( @NotNull Object item, @NotNull String methodName) {
        try {
            return item.getClass().getMethod(methodName);
        } catch (NoSuchMethodException ex) {
            return null;
        }
    }

    @Nullable
    private String extractValue( @NotNull Object item, @NotNull Method method) {
        try {
            Object result = method.invoke(item);
            return result == null ? null : result.toString();
        } catch (Exception ex) {
            return null;
        }
    }
}
