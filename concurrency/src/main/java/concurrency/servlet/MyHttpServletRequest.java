package concurrency.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DO NOT CHANGE.
 */
public class MyHttpServletRequest implements HttpServletRequest {
    private final Map<String, String> parameters = new ConcurrentHashMap<String, String>();

    public MyHttpServletRequest(String... parameterPairs) {
        for (int i = 0; i < parameterPairs.length; i += 2) {
            parameters.put(parameterPairs[i], parameterPairs[i + 1]);
        }
    }

    @Override
    public String getParameter(String name) {
        return parameters.get(name);
    }

    @Override
    public String getAuthType() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public Cookie[] getCookies() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public long getDateHeader(String s) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getHeader(String s) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public Enumeration getHeaders(String s) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public Enumeration getHeaderNames() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public int getIntHeader(String s) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getMethod() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getPathInfo() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getPathTranslated() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getContextPath() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getQueryString() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getRemoteUser() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public boolean isUserInRole(String s) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public Principal getUserPrincipal() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getRequestedSessionId() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getRequestURI() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public StringBuffer getRequestURL() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getServletPath() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public HttpSession getSession(boolean b) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public HttpSession getSession() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public Object getAttribute(String s) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public Enumeration getAttributeNames() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getCharacterEncoding() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public int getContentLength() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getContentType() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        throw new UnsupportedOperationException("todo");
    }


    @Override
    public Enumeration getParameterNames() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String[] getParameterValues(String s) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public Map getParameterMap() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getProtocol() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getScheme() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getServerName() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public int getServerPort() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public BufferedReader getReader() throws IOException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getRemoteAddr() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getRemoteHost() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void setAttribute(String s, Object o) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void removeAttribute(String s) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public Locale getLocale() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public Enumeration getLocales() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public boolean isSecure() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getRealPath(String s) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public int getRemotePort() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getLocalName() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getLocalAddr() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public int getLocalPort() {
        throw new UnsupportedOperationException("todo");
    }
}
