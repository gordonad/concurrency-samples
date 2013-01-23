package concurrency.servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * DO NOT CHANGE.
 */
public class MyHttpServletResponse implements HttpServletResponse {
    private final MyServletOutputStream out = new MyServletOutputStream();

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return out;
    }

    public String getContent() {
        return out.getContent();
    }

    @Override
    public void addCookie(Cookie cookie) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public boolean containsHeader(String s) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String encodeURL(String s) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String encodeRedirectURL(String s) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String encodeUrl(String s) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String encodeRedirectUrl(String s) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void sendError(int i, String s) throws IOException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void sendError(int i) throws IOException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void sendRedirect(String s) throws IOException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void setDateHeader(String s, long l) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void addDateHeader(String s, long l) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void setHeader(String s, String s1) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void addHeader(String s, String s1) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void setIntHeader(String s, int i) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void addIntHeader(String s, int i) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void setStatus(int i) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void setStatus(int i, String s) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getCharacterEncoding() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getContentType() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void setCharacterEncoding(String s) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void setContentLength(int i) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void setContentType(String s) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void setBufferSize(int i) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public int getBufferSize() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void flushBuffer() throws IOException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void resetBuffer() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public boolean isCommitted() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void setLocale(Locale locale) {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public Locale getLocale() {
        throw new UnsupportedOperationException("todo");
    }

}
