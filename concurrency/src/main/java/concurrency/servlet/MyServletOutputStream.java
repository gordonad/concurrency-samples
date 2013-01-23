package concurrency.servlet;

import javax.servlet.ServletOutputStream;

/**
 * DO NOT CHANGE.
 */
class MyServletOutputStream extends ServletOutputStream {
    private final StringBuilder out = new StringBuilder();

    @Override
    public void write(int b) {
        out.append((char) b);
    }

    public String getContent() {
        return out.toString();
    }
}
