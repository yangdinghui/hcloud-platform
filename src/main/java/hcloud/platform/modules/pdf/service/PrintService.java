package hcloud.platform.modules.pdf.service;

import javax.servlet.http.HttpServletResponse;

public interface PrintService {
    void print(HttpServletResponse response);
}
