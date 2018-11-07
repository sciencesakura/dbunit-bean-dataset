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

/**
 * Defines the conventions for resolving column names from property names.
 */
public enum Naming {

    /**
     * No change.
     *
     * <p>Example: {@code catLeg} &#x2192; {@code catLeg}</p>
     */
    RAW,

    /**
     * Converting camel-case to snake-case.
     *
     * <p>Example: {@code catLeg} &#x2192; {@code cat_leg}</p>
     */
    CAMEL_TO_SNAKE,

    /**
     * Converting camel-case to screaming-snake-case.
     *
     * <p>Example: {@code catLeg} &#x2192; {@code CAT_LEG}</p>
     */
    CAMEL_TO_SCREAMING_SNAKE;

    String resolve(String name) {
        switch (this) {
            case RAW:
                return name;
            case CAMEL_TO_SNAKE:
                return camelToSnake(name);
            case CAMEL_TO_SCREAMING_SNAKE:
                return camelToScreamingSnake(name);
            default:
                throw new UnsupportedOperationException(String.format("%1s is not supported", this));
        }
    }

    private String camelToSnake(String s) {
        StringBuilder sb = new StringBuilder(s.length() << 1);
        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) {
                sb.append('_').append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private String camelToScreamingSnake(String s) {
        StringBuilder sb = new StringBuilder(s.length() << 1);
        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) {
                sb.append('_').append(c);
            } else {
                sb.append(Character.toUpperCase(c));
            }
        }
        return sb.toString();
    }

}
