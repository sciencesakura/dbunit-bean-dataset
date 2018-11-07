/*
 * The MIT License
 * Copyright (c) 2018 sciencesakura
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the 'Software'), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.sciencesakura.dbunit.dataset.bean

import org.junit.Test

import static org.hamcrest.Matchers.is
import static org.junit.Assert.assertThat

class NamingTest {

    @Test
    void resolve_name_in_RAW_mode() {
        def actual = Naming.RAW.resolve('aaaBbbCcc')
        assertThat(actual, is('aaaBbbCcc'))
    }

    @Test
    void resolve_name_in_CAMEL_TO_SNAKE_mode() {
        def actual = Naming.CAMEL_TO_SNAKE.resolve('aaaBbbCcc')
        assertThat(actual, is('aaa_bbb_ccc'))
    }

    @Test
    void resolve_name_in_CAMEL_TO_SCREAMING_SNAKE_mode() {
        def actual = Naming.CAMEL_TO_SCREAMING_SNAKE.resolve('aaaBbbCcc')
        assertThat(actual, is('AAA_BBB_CCC'))
    }

}
