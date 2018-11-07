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
package com.github.sciencesakura.dbunit.dataset.bean

import org.dbunit.dataset.IDataSet
import org.dbunit.dataset.ITable
import org.dbunit.dataset.csv.CsvDataSet
import org.junit.Test

import java.sql.Time
import java.sql.Timestamp
import java.text.SimpleDateFormat

import static org.dbunit.Assertion.assertEquals
import static org.hamcrest.Matchers.allOf
import static org.hamcrest.Matchers.hasItem
import static org.hamcrest.Matchers.not
import static org.junit.Assert.assertThat

class BeanTableTest {

    private static final IDataSet expectedDataSet

    private static final Map<String, MetaProperty> beanProperties

    private static final def dateFormat = new SimpleDateFormat('yyyy-MM-dd')

    static {
        def expectedDataLocation = BeanTableTest.getResource("${BeanTableTest.simpleName}.csv")
        expectedDataSet = new CsvDataSet(new File(expectedDataLocation.toURI()))
        beanProperties = TestBean.metaClass.properties.findAll { it.name != 'class' }
            .collectEntries { [(Naming.CAMEL_TO_SNAKE.resolve(it.name)): it] }
    }

    @Test
    void excludes_properties_by_name() {
        def sut = new BeanTable.Builder(TestBean).naming(Naming.CAMEL_TO_SNAKE)
            .exclude('big_decimal', 'date')
            .build()
        def actual = sut.tableMetaData.columns.collect { it.columnName }
        println actual
        assertThat(actual, allOf(
            not(hasItem('big_decimal')),
            not(hasItem('date'))
        ))
    }

    @Test
    void excludes_properties_by_type() {
        def sut = new BeanTable.Builder(TestBean).naming(Naming.CAMEL_TO_SNAKE)
            .exclude(BigDecimal, Date)
            .build()
        def actual = sut.tableMetaData.columns.collect { it.columnName }
        assertThat(actual, allOf(
            not(hasItem('big_decimal')),
            not(hasItem('date'))
        ))
    }

    @Test
    void assertion_of_zero_rows_table() {
        def expected = expectedDataSet.getTable('zero_rows_table')
        def actual = new BeanTable.Builder(TestBean).naming(Naming.CAMEL_TO_SNAKE).build()
        assertEquals(expected, actual)
    }

    @Test
    void assertion_of_n_rows_table() {
        def expected = expectedDataSet.getTable('n_rows_table')
        def beans = tableToBeans(expected)
        def actual = new BeanTable.Builder(TestBean).naming(Naming.CAMEL_TO_SNAKE)
            .add(beans[0])
            .add(beans[1..2])
            .add(beans[3..4].toArray())
            .build()
        assertEquals(expected, actual)
    }

    private List<TestBean> tableToBeans(ITable table) {
        return (0..<table.rowCount).collect { r ->
            def bean = new TestBean()
            beanProperties.each { c, p ->
                def value = table.getValue(r, c) as String
                def typedValue = convert(value, p.type)
                bean[p.name] = typedValue
            }
            return bean
        }
    }

    private Object convert(String value, Class<?> type) {
        if (value == null) return null
        switch (type) {
            case byte:
            case Byte:
                return Byte.valueOf(value)
            case short:
            case Short:
                return Short.valueOf(value)
            case int:
            case Integer:
                return Integer.valueOf(value)
            case long:
            case Long:
                return Long.valueOf(value)
            case float:
            case Float:
                return Float.valueOf(value)
            case double:
            case Double:
                return Double.valueOf(value)
            case boolean:
            case Boolean:
                return Boolean.valueOf(value)
            case String:
                return value
            case BigInteger:
                return new BigInteger(value)
            case BigDecimal:
                return new BigDecimal(value)
            case Timestamp:
                return Timestamp.valueOf(value)
            case Time:
                return Time.valueOf(value)
            case java.sql.Date:
                return java.sql.Date.valueOf(value)
            case Date:
                return dateFormat.parse(value)
            case byte[]:
                return (value - '[BASE64]').decodeBase64()
            default:
                throw new AssertionError("unexpected type: ${type.name}")
        }
    }

}
