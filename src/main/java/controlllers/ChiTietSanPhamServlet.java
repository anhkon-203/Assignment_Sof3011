package controlllers;

import entities.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import repositories.*;
import viewModel.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


@WebServlet({
        "/chi-tiet-san-pham/index",    // GET
        "/chi-tiet-san-pham/create",   // GET
        "/chi-tiet-san-pham/edit",     // GET
        "/chi-tiet-san-pham/delete",   // GET
        "/chi-tiet-san-pham/store",    // POST
        "/chi-tiet-san-pham/update",   // POST
})

public class ChiTietSanPhamServlet extends HttpServlet {
    private ChiTietSanPhamRepository chiTietSanPhamRepository = new ChiTietSanPhamRepository();
    private DongSpRepository dongSpRepository = new DongSpRepository();
    private SanPhamRepository sanPhamRepository = new SanPhamRepository();
    private MauSacRepository mauSacRepository = new MauSacRepository();
    private NSXRepository nsxRepository = new NSXRepository();
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
            store(request, response);
        } else if (uri.contains("update")) {
            update(request, response);
        }
    }

    protected void store(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        try {

            // Lấy các giá trị từ form
            String idDong = request.getParameter("idDong");
            String idNSX = request.getParameter("idNSX");
            String idMauSac = request.getParameter("idMauSac");
            String idSp = request.getParameter("idSp");
            ChiTietSp chiTietSp = new ChiTietSp();
        // Tạo đối tượng
            DongSp dongSp = new DongSp();
            dongSp.setId(idDong);
            NSX nsx = new NSX();
            nsx.setId(idNSX);
            MauSac mauSac = new MauSac();
            mauSac.setId(idMauSac);
            SanPham sanPham = new SanPham();
            sanPham.setId(idSp);
            // Set các giá trị vào đối tượng
            chiTietSp.setSanPham(sanPham);
            chiTietSp.setMauSac(mauSac);
            chiTietSp.setNsx(nsx);
            chiTietSp.setDongSp(dongSp);
            // Thêm vào database
            BeanUtils.populate(chiTietSp, request.getParameterMap());
            chiTietSanPhamRepository.insert(chiTietSp);
        } catch (Exception e) {
            e.printStackTrace();
        }
       response.sendRedirect("/Assignment_Sof3011_war_exploded/chi-tiet-san-pham/index");

    }
    protected void update(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        try {
            // Lấy các giá trị từ form
            String idDong = request.getParameter("idDong");
            String idNSX = request.getParameter("idNSX");
            String idMauSac = request.getParameter("idMauSac");
            String idSp = request.getParameter("idSp");
            String id = request.getParameter("id");
            // Tạo đối tượng
            DongSp dongSp = new DongSp();
            dongSp.setId(idDong);
            NSX nsx = new NSX();
            nsx.setId(idNSX);
            MauSac mauSac = new MauSac();
            mauSac.setId(idMauSac);
            SanPham sanPham = new SanPham();
            sanPham.setId(idSp);
            // Set các giá trị vào đối tượng
            ChiTietSp chiTietSp = new ChiTietSp();
            chiTietSp.setSanPham(sanPham);
            chiTietSp.setMauSac(mauSac);
            chiTietSp.setNsx(nsx);
            chiTietSp.setDongSp(dongSp);
            BeanUtils.populate(chiTietSp, request.getParameterMap());
            chiTietSanPhamRepository.update(id,chiTietSp);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("/Assignment_Sof3011_war_exploded/chi-tiet-san-pham/index");

    }

    protected void create(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        // Lấy danh sách các đối tượng
        List<NSX> listNSX = nsxRepository.getAll();
        request.setAttribute("listNSX", listNSX);
        List<DongSp> listDongSp = dongSpRepository.getAll();
        request.setAttribute("listDongSp", listDongSp);
        List<MauSac> listMauSac = mauSacRepository.getAll();
        request.setAttribute("listMauSac", listMauSac);
        List<SanPham> listSanPham = sanPhamRepository.getAll();
        // Gửi danh sách các đối tượng qua view
        request.setAttribute("listSanPham", listSanPham);
        request.setAttribute("view_chiTietSanPham", "/views/chiTietSanPham/create.jsp");
        request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
    }
    protected void index(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        List<ChiTietSanPhamViewModel> list = chiTietSanPhamRepository.getList();
        request.setAttribute("list", list);
        request.setAttribute("view_chiTietSanPham", "/views/chiTietSanPham/index.jsp");
        request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
    }
    protected void edit(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
// Lấy danh sách các đối tượng
        List<NSX> listNSX = nsxRepository.getAll();
        request.setAttribute("listNSX", listNSX);
        List<DongSp> listDongSp = dongSpRepository.getAll();
        request.setAttribute("listDongSp", listDongSp);
        List<MauSac> listMauSac = mauSacRepository.getAll();
        request.setAttribute("listMauSac", listMauSac);
        List<SanPham> listSanPham = sanPhamRepository.getAll();
        request.setAttribute("listSanPham", listSanPham);
        // Lấy id từ url
        String idCtsp = request.getParameter("id");
        ChiTietSp chiTietSp = new ChiTietSp();
        request.setAttribute("idDongsp", chiTietSanPhamRepository.getIdDongSp(idCtsp));
        request.setAttribute("idMauSac", chiTietSanPhamRepository.getIdMauSac(idCtsp));
        request.setAttribute("idNSX", chiTietSanPhamRepository.getIdNhaSanXuat(idCtsp));
        request.setAttribute("idSp", chiTietSanPhamRepository.getIdSanPham(idCtsp));
        ChiTietSp chiTietSpRepo = chiTietSanPhamRepository.getById(idCtsp);
        request.setAttribute("chiTietSp", chiTietSpRepo);
        request.setAttribute("view_chiTietSanPham", "/views/chiTietSanPham/edit.jsp");
        request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
    }
    protected void delete(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
            String id = request.getParameter("id");
            ChiTietSp chiTietSp = chiTietSanPhamRepository.getById(id);
            chiTietSanPhamRepository.delete(chiTietSp);
        response.sendRedirect("/Assignment_Sof3011_war_exploded/chi-tiet-san-pham/index");

    }
}
