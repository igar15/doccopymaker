package ru.javaprojects.doccopymaker.core.pathmaker;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class DocumentPathMakerTest {
    private final DocumentPathMaker pathMaker = new DocumentPathMaker();

    @Test
    void makePathWhenSp() {
        assertEquals(Paths.get("VUIA/468172/059/SP"), pathMaker.makePath("ВУИА.468172.059"));
        assertEquals(Paths.get("_UPI_A/301159/001/SP"), pathMaker.makePath("ЮПИЯ.301159.001"));
        assertEquals(Paths.get("_UPI_A/301159/001/-02/SP"), pathMaker.makePath("ЮПИЯ.301159.001-02"));
        assertEquals(Paths.get("_UPI_A/666777/001/SP"), pathMaker.makePath("ЮПИЯ.666777.001"));
        assertEquals(Paths.get("BA/1640/016/SP"), pathMaker.makePath("БА1.640.016"));
        assertEquals(Paths.get("BA/1640/016/-03/SP"), pathMaker.makePath("БА1.640.016-03"));
        assertEquals(Paths.get("BA/5341/001/SP"), pathMaker.makePath("БА5.341.001"));
        assertEquals(Paths.get("BA/4341/001/SP"), pathMaker.makePath("БА4.341.001"));
        assertEquals(Paths.get("BA/2081/284/-02/SP"), pathMaker.makePath("БА2.081.284-02"));
    }

    @Test
    void makePathWhenDetail() {
        assertEquals(Paths.get("VUIA/777666/001/_CD"), pathMaker.makePath("ВУИА.777666.001"));
        assertEquals(Paths.get("VUIA/777666/1364/_CD"), pathMaker.makePath("ВУИА.777666.1364"));
        assertEquals(Paths.get("VUIA/777666/001/-05/_CD"), pathMaker.makePath("ВУИА.777666.001-05"));
        assertEquals(Paths.get("_UPI_A/888564/154/_CD"), pathMaker.makePath("ЮПИЯ.888564.154"));
        assertEquals(Paths.get("_UPI_A/888564/154/-101/_CD"), pathMaker.makePath("ЮПИЯ.888564.154-101"));
        assertEquals(Paths.get("BA/7145/154/_CD"), pathMaker.makePath("БА7.145.154"));
        assertEquals(Paths.get("BA/9288/261/_CD"), pathMaker.makePath("БА9.288.261"));
    }

    @Test
    void makePath() {
        assertEquals(Paths.get("VUIA/468172/059/PE3"), pathMaker.makePath("ВУИА.468172.059ПЭ3"));
        assertEquals(Paths.get("VUIA/468172/059/I33"), pathMaker.makePath("ВУИА.468172.059И33"));
        assertEquals(Paths.get("VUIA/758782/126/T8M"), pathMaker.makePath("ВУИА.758782.126Т8М"));
        assertEquals(Paths.get("_UPI_A/685664/099/SB"), pathMaker.makePath("ЮПИЯ.685664.099СБ"));
        assertEquals(Paths.get("BA/6081/284/-02/SB"), pathMaker.makePath("БА6.081.284-02СБ"));
        assertEquals(Paths.get("BA/1640/016/-01/IE2.1"), pathMaker.makePath("БА1.640.016-01ИЭ2.1"));
        assertEquals(Paths.get("BA/1640/016/-01/ZI1(B)"), pathMaker.makePath("БА1.640.016-01ЗИ1(Б)"));
    }

    @Test
    void makePathWhenSoftware() {
        assertEquals(Paths.get("VUIA/20163/-01/SP"), pathMaker.makePath("ВУИА.20163-01"));
        assertEquals(Paths.get("VUIA/02456/-01/SP"), pathMaker.makePath("ВУИА.02456-01"));
        assertEquals(Paths.get("VUIA/20163/-01/12/01"), pathMaker.makePath("ВУИА.20163-01 12 01"));
        assertEquals(Paths.get("VUIA/02456/-01/12/01"), pathMaker.makePath("ВУИА.02456-01 12 01"));
        assertEquals(Paths.get("_UPI_A/02456/-01/12/01"), pathMaker.makePath("ЮПИЯ.02456-01 12 01"));
        assertEquals(Paths.get("_UPI_A/02456/-01/SP"), pathMaker.makePath("ЮПИЯ.02456-01"));
    }

    @Test
    void makePathWhenNotSupportedCompanyCode() {
        assertThrows(UnsupportedCompanyCodeException.class, () -> pathMaker.makePath("ТПКК.468172.059ПЭ3"));
    }

    @Test
    void makePathWhenNotSupportedDocSpecifier() {
        assertThrows(UnsupportedDocSpecifierException.class, () -> pathMaker.makePath("ВУИА.468172.059ЦА"));
    }

    @Test
    void makePathWhenBadDecimalNumber() {
        assertThrows(UnsupportedCompanyCodeException.class, () -> pathMaker.makePath("ВУИА468172.059"));
        assertThrows(UnsupportedCompanyCodeException.class, () -> pathMaker.makePath("ВУИА20163-01 12 01"));
        assertThrows(UnsupportedDecimalNumberTypeException.class, () -> pathMaker.makePath("ВУИА.2016301 12 01"));
        assertThrows(UnsupportedDecimalNumberTypeException.class, () -> pathMaker.makePath("ВУИА.20163 01"));
        assertThrows(UnsupportedDecimalNumberTypeException.class, () -> pathMaker.makePath("ВУИА.20163"));
        assertThrows(UnsupportedDecimalNumberTypeException.class, () -> pathMaker.makePath("ВУИА.468172059"));
    }
}