package tecolotl.web.herramienta;

import tecolotl.alumno.cache.ImageneGlosarioCache;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet(name = "ServletImagenGlosario", urlPatterns = {"imagen-glosario"})
public class ImagenGlosarioServlet extends HttpServlet {

    @Inject
    private ImageneGlosarioCache imageneGlosarioCache;

    @Override
    protected void doGet(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse) throws ServletException, IOException {
        byte[] buffer = new byte[1024]; int byteLeidos;
        httpServletResponse.setContentType("image/jpeg");
        try(OutputStream outputStream = httpServletResponse.getOutputStream();
            InputStream inputStream = new ByteArrayInputStream(imageneGlosarioCache.busca(
                    httpServletRequest.getParameter("palabra"),
                    Short.parseShort(httpServletRequest.getParameter("clave"))))) {
            while ((byteLeidos = inputStream.read(buffer)) > -1) {
                outputStream.write(buffer, 0, byteLeidos);
            }
        }
    }
}
