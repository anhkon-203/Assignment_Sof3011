package controlllers.admin;

import entities.ChucVu;
import entities.MauSac;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import repositories.ChucVuRepository;

import java.io.IOException;
import java.util.List;

@WebServlet({
        "/chuc-vu/index",    // GET
        "/chuc-vu/create",   // GET
        "/chuc-vu/edit",     // GET
        "/chuc-vu/delete",   // GET
        "/chuc-vu/store",    // POST
        "/chuc-vu/update",   // POST
})
public class ChucVuServlet extends HttpServlet {
    private ChucVuRepository chucVuRepository = new ChucVuRepository();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws
            ServletException, IOException {

        String uri = request.getRequestURI();
        if (uri.contains("create")) {
            create(request, response);
        } else if (uri.contains("edit")) {
            edit(request, response);
        } else if (uri.contains("delete")) {
            delete(request, response);
        } else {
            this.index(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String uri = request.getRequestURI();
            if (uri.contains("store")) {
                this.store(request, response);
            } else if (uri.contains("update")) {
                update(request, response);
            }
    }

    protected void create(
            HttpServletRequest request,
            HttpServletResponse response)
            throws
            ServletException, IOException {
        request.setAttribute("view_chucVu", "/views/admin/chucVu/create.jsp");
        request.getRequestDispatcher("/views/admin/layout.jsp").forward(request, response);
    }

    protected void store(
            HttpServletRequest request,
            HttpServletResponse response)
            throws
            ServletException, IOException {
        try {
            String ma = request.getParameter("ma");
            String ten = request.getParameter("ten");
            if (ma.trim().isEmpty() || ten.trim().isEmpty()) {
                request.getSession().setAttribute("mess_error", "Vui lòng nhập đầy đủ thông tin");
                response.sendRedirect(request.getContextPath() + "/chuc-vu/create");
                return;
            }
            ChucVu chucVu = new ChucVu();
            BeanUtils.populate(chucVu, request.getParameterMap());
            if (chucVuRepository.findByMa(ma) != null) {
                request.getSession().setAttribute("mess_error", "Mã đã tồn tại");
                response.sendRedirect(request.getContextPath() + "/chuc-vu/create");
                return;
            }
            if (chucVuRepository.insert(chucVu)) {
                request.getSession().setAttribute("message", "Thêm mới thành công");
                response.sendRedirect(request.getContextPath() + "/chuc-vu/index");
            } else {
                request.getSession().setAttribute("mess_error", "Thêm mới thất bại");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void update(
            HttpServletRequest request,
            HttpServletResponse response)
            throws
            ServletException, IOException {
        try {
            String ma = request.getParameter("ma");
            String ten = request.getParameter("ten");
            if (ma.trim().isEmpty() || ten.trim().isEmpty()) {
                request.getSession().setAttribute("mess_error", "Vui lòng nhập đầy đủ thông tin");
                response.sendRedirect(request.getContextPath() + "/chuc-vu/edit?ma=" + ma);
                return;
            }
            ChucVu chucVu = new ChucVu();
            BeanUtils.populate(chucVu, request.getParameterMap());
            if (chucVuRepository.update(ma,chucVu)) {
                request.getSession().setAttribute("message", "Update thành công");
                response.sendRedirect(request.getContextPath() + "/chuc-vu/index");
            } else {
                request.getSession().setAttribute("mess_error", "Update thất bại");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void index(
            HttpServletRequest request,
            HttpServletResponse response)
            throws
            ServletException, IOException {
        List<ChucVu> list = chucVuRepository.getAll();
        request.setAttribute("list", list);
        request.setAttribute("view_chucVu", "/views/admin/chucVu/index.jsp");
        request.getRequestDispatcher("/views/admin/layout.jsp").forward(request, response);
    }

    protected void delete(
            HttpServletRequest request,
            HttpServletResponse response)
            throws
            ServletException, IOException {
        String ma = request.getParameter("ma");
        ChucVu chucVu = chucVuRepository.findByMa(ma);
        chucVuRepository.delete(chucVu);
        response.sendRedirect("/Assignment_Sof3011_war_exploded/chuc-vu/index");
    }
    protected void edit(
            HttpServletRequest request,
            HttpServletResponse response)
            throws
            ServletException, IOException {
        String ma = request.getParameter("ma");
        ChucVu chucVu = chucVuRepository.findByMa(ma);
        request.setAttribute("chucVu", chucVu);
        request.setAttribute("view_chucVu", "/views/admin/chucVu/edit.jsp");
        request.getRequestDispatcher("/views/admin/layout.jsp").forward(request, response);
    }
}
