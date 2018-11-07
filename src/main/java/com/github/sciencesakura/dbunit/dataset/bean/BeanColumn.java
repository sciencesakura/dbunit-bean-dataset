/*
 * The MIT License
 * Copyright (c) 2018 sciencesakura
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.sciencesakura.dbunit.dataset.bean;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.datatype.DataType;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

class BeanColumn {

    private static DataType resolveType(Class<?> javaType) {
        DataType dbUnitType;
        if (Timestamp.class.isAssignableFrom(javaType)) {
            dbUnitType = DataType.TIMESTAMP;
        } else if (Time.class.isAssignableFrom(javaType)) {
            dbUnitType = DataType.TIME;
        } else if (Date.class.isAssignableFrom(javaType)) {
            dbUnitType = DataType.DATE;
        } else if (Boolean.TYPE.equals(javaType) || Boolean.class.equals(javaType)) {
            dbUnitType = DataType.BOOLEAN;
        } else if (byte[].class.equals(javaType)) {
            dbUnitType = DataType.BLOB;
        } else {
            dbUnitType = DataType.UNKNOWN;
        }
        return dbUnitType;
    }

    private final String name;

    private final DataType type;

    private final Method getter;

    BeanColumn(PropertyDescriptor descriptor, Naming naming) {
        name = naming.resolve(descriptor.getName());
        type = resolveType(descriptor.getPropertyType());
        getter = descriptor.getReadMethod();
    }

    String getName() {
        return name;
    }

    Object getValue(Object bean) throws DataSetException {
        try {
            return getter.invoke(bean);
        } catch (IllegalAccessException e) {
            throw new DataSetException(String.format("failed to read %1s.%2s",
                getter.getDeclaringClass().getSimpleName(), name));
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof Error) {
                throw (Error) cause;
            } else if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else {
                throw new DataSetException(String.format("failed to read %1s.%2s",
                    getter.getDeclaringClass().getSimpleName(), name), cause);
            }
        }
    }

    Column toDbUnitColumn() {
        return new Column(name, type);
    }

}
