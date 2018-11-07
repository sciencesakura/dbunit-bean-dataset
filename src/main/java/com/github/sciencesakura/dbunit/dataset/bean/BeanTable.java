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

import org.dbunit.DatabaseUnitRuntimeException;
import org.dbunit.dataset.AbstractTable;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultTableMetaData;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.NoSuchColumnException;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of the {@link org.dbunit.dataset.ITable ITable} interface that treats the
 * specified zero or more POJOs as a table.
 *
 * @param <T> the type of the POJO
 * @see BeanTable.Builder
 */
public class BeanTable<T> extends AbstractTable {

    private final Map<String, BeanColumn> columns;

    private final Object[] rows;

    private final ITableMetaData tableMetaData;

    private BeanTable(Builder<T> builder) {
        rows = builder.beanList.toArray();
        PropertyDescriptor[] descriptors;
        try {
            descriptors = Introspector.getBeanInfo(builder.type, Object.class)
                .getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new DatabaseUnitRuntimeException(e);
        }
        Map<String, BeanColumn> columnMap = new HashMap<String, BeanColumn>();
        List<Column> dbUnitColumns = new ArrayList<Column>(descriptors.length);
        for (PropertyDescriptor descriptor : descriptors) {
            if (descriptor.getReadMethod() == null) continue;
            if (builder.excludedTypes.contains(descriptor.getPropertyType())) continue;
            BeanColumn column = new BeanColumn(descriptor, builder.naming);
            if (builder.excludedNames.contains(column.getName())) continue;
            columnMap.put(column.getName(), column);
            dbUnitColumns.add(column.toDbUnitColumn());
        }
        columns = Collections.unmodifiableMap(columnMap);
        tableMetaData = new DefaultTableMetaData(builder.tableName, dbUnitColumns.toArray(new Column[0]));
    }

    @Override
    public int getRowCount() {
        return rows.length;
    }

    @Override
    public ITableMetaData getTableMetaData() {
        return tableMetaData;
    }

    @Override
    public Object getValue(int row, String column) throws DataSetException {
        assertValidRowIndex(row);
        BeanColumn c = columns.get(column);
        if (c == null) throw new NoSuchColumnException(tableMetaData.getTableName(), column);
        return c.getValue(rows[row]);
    }

    /**
     * A builder to create a {@link BeanTable} instances providing chainable methods.
     *
     * @param <T> the type of the POJO
     */
    public static class Builder<T> {

        private final Class<T> type;

        private final String tableName;

        private final List<T> beanList = new LinkedList<T>();

        private final Set<String> excludedNames = new HashSet<String>();

        private final Set<Class<?>> excludedTypes = new HashSet<Class<?>>();

        private Naming naming = Naming.RAW;

        /**
         * Constructs a builder.
         *
         * @param type the type of the POJO, non-nullable
         */
        public Builder(Class<T> type) {
            this(type, type.getSimpleName());
        }

        /**
         * Constructs a builder.
         *
         * @param type      the type of the POJO, non-nullable
         * @param tableName the table name, non-nullable
         */
        public Builder(Class<T> type, String tableName) {
            if (type == null) throw new NullPointerException("type must not be null");
            if (tableName == null) throw new NullPointerException("tableName must not be null");
            this.type = type;
            this.tableName = tableName;
        }

        /**
         * Appends the specified POJO to this builder.
         *
         * @param bean the POJO to add, non-nullable
         * @return the reference to this object
         */
        public Builder add(T bean) {
            if (bean == null) throw new NullPointerException("bean must not be null");
            beanList.add(bean);
            return this;
        }

        /**
         * Appends the specified POJO array to this builder.
         *
         * @param beans the POJOs to add, non-nullable
         * @return the reference to this object
         */
        public Builder add(T... beans) {
            for (T bean : beans) {
                if (bean == null) throw new NullPointerException("beans must not contain null");
                beanList.add(bean);
            }
            return this;
        }

        /**
         * Appends the specified POJO collection to this builder.
         *
         * @param beans the POJOs to add, non-nullable
         * @return the reference to this object
         */
        public Builder add(Collection<? extends T> beans) {
            for (T bean : beans) {
                if (bean == null) throw new NullPointerException("beans must not contain null");
                beanList.add(bean);
            }
            return this;
        }

        /**
         * Constructs a {@link BeanTable} instance.
         *
         * @return a new {@link BeanTable} instance
         */
        public BeanTable<T> build() {
            return new BeanTable<T>(this);
        }

        /**
         * Specifies the property names to exclude from table column.
         *
         * @param propertyNames the property names to exclude, non-nullable
         * @return the reference to this object
         */
        public Builder exclude(String... propertyNames) {
            for (String name : propertyNames) {
                if (name == null) throw new NullPointerException("propertyNames must not contain null");
                excludedNames.add(name);
            }
            return this;
        }

        /**
         * Specifies the property types to exclude from table column.
         *
         * @param propertyTypes the property types to exclude, non-nullable
         * @return the reference to this object
         */
        public Builder exclude(Class<?>... propertyTypes) {
            for (Class<?> type : propertyTypes) {
                if (type == null) throw new NullPointerException("propertyTypes must not contain null");
                excludedTypes.add(type);
            }
            return this;
        }

        /**
         * Specifies the naming convention for columns.
         *
         * <p>Default: {@link Naming#RAW}</p>
         *
         * @param naming the naming convention, non-nullable
         * @return the reference to this object
         */
        public Builder naming(Naming naming) {
            if (naming == null) throw new NullPointerException("naming must not be null");
            this.naming = naming;
            return this;
        }

    }

}
