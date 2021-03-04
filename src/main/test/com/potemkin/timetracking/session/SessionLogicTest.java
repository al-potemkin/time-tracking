package com.potemkin.timetracking.session;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SessionLogicTest {
    private HttpSession httpSessionMock;
    private HttpServletRequest requestMock;

    @Before
    public void setUp() {
        httpSessionMock = mock(HttpSession.class);
        requestMock = mock(HttpServletRequest.class);
    }

    @Test
    public void getSessionSuccess() {
       when(requestMock.getSession()).thenReturn(httpSessionMock);
       HttpSession expectedSession = SessionLogic.getSession(requestMock);
       assertNotNull(expectedSession);
    }

    @Test
    public void isSessionNotAliveMethodShouldReturnTrueIfSessionNull() {
        httpSessionMock = null;
        boolean result = SessionLogic.isSessionNotAlive(httpSessionMock);
        assertTrue(result);
    }

    @Test
    public void isSessionNotAliveMethodShouldReturnFalseIfSessionNotNull() {
        boolean result = SessionLogic.isSessionNotAlive(httpSessionMock);
        assertFalse(result);
    }
}