package controlllers.admin;

import entities.ChucVu;
import entities.CuaHang;
import entities.NhanVien;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;
import repositories.ChucVuRepository;
import repositories.CuaHangRepository;
import repositories.NhanVienRepository;
import viewModel.NhanVienViewModel;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@WebServlet({
        "/nhan-vien/index",    // GET
        "/nhan-vien/create",   // GET
        "/nhan-vien/edit",     // GET
        "/nhan-vien/delete",   // GET
        "/nhan-vien/store",    // POST
        "/nhan-vien/update",   // POST
})

public class NhanVienServlet extends HttpServlet {
    private NhanVienRepository nhanVienRepository = new NhanVienRepository();

    private CuaHangRepository cuaHangRepository = new CuaHangRepository();

    private ChucVuRepository chucVuRepository = new ChucVuRepository();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
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
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.contains("store")) {
            this.store(request, response);
        } else if (uri.contains("update")) {
            update(request, response);
        }
    }

    protected void store(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        try {
            // get idCuaHang and idChucVu
            UUID idChucVu = UUID.fromString(request.getParameter("idChucVu"));
            UUID idCuaHang = UUID.fromString(request.getParameter("idCuaHang"));
            CuaHang cuaHang = new CuaHang();
            cuaHang.setId(idCuaHang);
            ChucVu chucVu = new ChucVu();
            chucVu.setId(idChucVu);
            //
            DateTimeConverter dtc = new DateConverter(new Date());
            dtc.setPattern("yyyy-MM-dd");
            ConvertUtils.register(dtc, Date.class);
            //
            NhanVien nhanVien = new NhanVien();
            nhanVien.setCuaHang(cuaHang);
            nhanVien.setChucVu(chucVu);
            BeanUtils.populate(nhanVien, request.getParameterMap());
            nhanVienRepository.insert(nhanVien);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("/Assignment_Sof3011_war_exploded/nhan-vien/index");

    }

    protected void create(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        List<CuaHang> listCuaHang = cuaHangRepository.getAll();
        request.setAttribute("listCuaHang", listCuaHang);
        List<ChucVu> listChucVu = chucVuRepository.getAll();
        request.setAttribute("listChucVu", listChucVu);
        request.setAttribute("view_nhanVien", "/views/admin/nhanVien/create.jsp");
        request.getRequestDispatcher("/views/admin/layout.jsp").forward(request, response);
    }

    protected void index(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        List<NhanVienViewModel> list = nhanVienRepository.getAll();
        request.setAttribute("list", list);
        request.setAttribute("view_nhanVien", "/views/admin/nhanVien/index.jsp");
        request.getRequestDispatcher("/views/admin/layout.jsp").forward(request, response);
    }

    protected void delete(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        String ma = request.getParameter("ma");
        NhanVien nhanVien = nhanVienRepository.findByMa(ma);
        nhanVienRepository.delete(nhanVien);
        response.sendRedirect("/Assignment_Sof3011_war_exploded/nhan-vien/index");

    }

    protected void edit(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        List<CuaHang> listCuaHang = cuaHangRepository.getAll();
        request.setAttribute("listCuaHang", listCuaHang);
        List<ChucVu> listChucVu = chucVuRepository.getAll();
        request.setAttribute("listChucVu", listChucVu);

        String maNv = request.getParameter("ma");
        request.setAttribute("idCv", nhanVienRepository.findIdChucVuByMa(maNv));
        request.setAttribute("idCh", nhanVienRepository.findIdCuaHangByMa(maNv));
        NhanVien nhanVien = nhanVienRepository.findByMa(maNv);
        request.setAttribute("nhanVien", nhanVien);
        request.setAttribute("view_nhanVien", "/views/admin/nhanVien/edit.jsp");
        request.getRequestDispatcher("/views/admin/layout.jsp").forward(request, response);
    }

    protected void update(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        try {
            String ma = request.getParameter("ma");
            // get idCuaHang and idChucVu
            UUID idChucVu = UUID.fromString(request.getParameter("idChucVu"));
            UUID idCuaHang = UUID.fromString(request.getParameter("idCuaHang"));

            CuaHang cuaHang = new CuaHang();
            cuaHang.setId(idCuaHang);
            ChucVu chucVu = new ChucVu();
            chucVu.setId(idChucVu);
            //
            DateTimeConverter dtc = new DateConverter(new Date());
            dtc.setPattern("yyyy-MM-dd");
            ConvertUtils.register(dtc, Date.class);
            //
            NhanVien nhanVien = new NhanVien();
            nhanVien.setCuaHang(cuaHang);
            nhanVien.setChucVu(chucVu);
            BeanUtils.populate(nhanVien, request.getParameterMap());
            nhanVienRepository.update(ma, nhanVien);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("/Assignment_Sof3011_war_exploded/nhan-vien/index");

    }
}
