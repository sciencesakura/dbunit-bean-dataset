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

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

public class TestBean implements Serializable {

    private static final long serialVersionUID = 6169528351504295520L;

    private byte pByte;

    private short pShort;

    private int pInt;

    private long pLong;

    private float pFloat;

    private double pDouble;

    private boolean pBoolean;

    private Byte wByte;

    private Short wShort;

    private Integer wInt;

    private Long wLong;

    private Float wFloat;

    private Double wDouble;

    private Boolean wBoolean;

    private String string;

    private BigInteger bigInteger;

    private BigDecimal bigDecimal;

    private Date date;

    private java.sql.Date sqlDate;

    private Timestamp timestamp;

    private Time time;

    private byte[] blob;

    public byte getpByte() {
        return pByte;
    }

    public void setpByte(byte pByte) {
        this.pByte = pByte;
    }

    public short getpShort() {
        return pShort;
    }

    public void setpShort(short pShort) {
        this.pShort = pShort;
    }

    public int getpInt() {
        return pInt;
    }

    public void setpInt(int pInt) {
        this.pInt = pInt;
    }

    public long getpLong() {
        return pLong;
    }

    public void setpLong(long pLong) {
        this.pLong = pLong;
    }

    public float getpFloat() {
        return pFloat;
    }

    public void setpFloat(float pFloat) {
        this.pFloat = pFloat;
    }

    public double getpDouble() {
        return pDouble;
    }

    public void setpDouble(double pDouble) {
        this.pDouble = pDouble;
    }

    public boolean ispBoolean() {
        return pBoolean;
    }

    public void setpBoolean(boolean pBoolean) {
        this.pBoolean = pBoolean;
    }

    public Byte getwByte() {
        return wByte;
    }

    public void setwByte(Byte wByte) {
        this.wByte = wByte;
    }

    public Short getwShort() {
        return wShort;
    }

    public void setwShort(Short wShort) {
        this.wShort = wShort;
    }

    public Integer getwInt() {
        return wInt;
    }

    public void setwInt(Integer wInt) {
        this.wInt = wInt;
    }

    public Long getwLong() {
        return wLong;
    }

    public void setwLong(Long wLong) {
        this.wLong = wLong;
    }

    public Float getwFloat() {
        return wFloat;
    }

    public void setwFloat(Float wFloat) {
        this.wFloat = wFloat;
    }

    public Double getwDouble() {
        return wDouble;
    }

    public void setwDouble(Double wDouble) {
        this.wDouble = wDouble;
    }

    public Boolean getwBoolean() {
        return wBoolean;
    }

    public void setwBoolean(Boolean wBoolean) {
        this.wBoolean = wBoolean;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public BigInteger getBigInteger() {
        return bigInteger;
    }

    public void setBigInteger(BigInteger bigInteger) {
        this.bigInteger = bigInteger;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public java.sql.Date getSqlDate() {
        return sqlDate;
    }

    public void setSqlDate(java.sql.Date sqlDate) {
        this.sqlDate = sqlDate;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public byte[] getBlob() {
        return blob;
    }

    public void setBlob(byte[] blob) {
        this.blob = blob;
    }

    @Override
    public String toString() {
        return "{\"TestBean\":{"
            + "\"pByte\":\"" + pByte + "\""
            + ", \"pShort\":\"" + pShort + "\""
            + ", \"pInt\":\"" + pInt + "\""
            + ", \"pLong\":\"" + pLong + "\""
            + ", \"pFloat\":\"" + pFloat + "\""
            + ", \"pDouble\":\"" + pDouble + "\""
            + ", \"pBoolean\":\"" + pBoolean + "\""
            + ", \"wByte\":\"" + wByte + "\""
            + ", \"wShort\":\"" + wShort + "\""
            + ", \"wInt\":\"" + wInt + "\""
            + ", \"wLong\":\"" + wLong + "\""
            + ", \"wFloat\":\"" + wFloat + "\""
            + ", \"wDouble\":\"" + wDouble + "\""
            + ", \"wBoolean\":\"" + wBoolean + "\""
            + ", \"string\":\"" + string + "\""
            + ", \"bigInteger\":\"" + bigInteger + "\""
            + ", \"bigDecimal\":\"" + bigDecimal + "\""
            + ", \"date\":" + date
            + ", \"sqlDate\":" + sqlDate
            + ", \"timestamp\":" + timestamp
            + ", \"time\":" + time
            + ", \"blob\":" + Arrays.toString(blob)
            + "}}";
    }

}
