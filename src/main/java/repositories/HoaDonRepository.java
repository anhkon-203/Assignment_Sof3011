package repositories;

import entities.HoaDon;
import entities.HoaDonChiTiet;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtil;
import viewModel.HoaDonChiTietUserViewModel;
import viewModel.HoaDonChiTietViewModel;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class HoaDonRepository {
    Session hSession = HibernateUtil.getFACTORY().openSession();

    public List<HoaDonChiTietViewModel> getListByIdHoaDon( UUID HoaDonId) {
        Session session = HibernateUtil.getFACTORY().openSession();
        Query query = session.createQuery("SELECT new viewModel.HoaDonChiTietViewModel(hd.hoaDon.ma, hd.hoaDon.tenNguoiNhan, hd.hoaDon.diaChi, hd.hoaDon.sdt, hd.hoaDon.tinhTrang,hd.chiTietSp.sanPham.ten,hd.chiTietSp.sanPham.srcImage,hd.soLuongTon,hd.donGia) FROM HoaDonChiTiet hd WHERE  hd.hoaDon.id =: idHD GROUP BY hd.hoaDon.ma, hd.hoaDon.tenNguoiNhan, hd.hoaDon.diaChi, hd.hoaDon.sdt, hd.hoaDon.tinhTrang,hd.chiTietSp.sanPham.ten,hd.chiTietSp.sanPham.srcImage,hd.soLuongTon,hd.donGia");
        query.setParameter("idHD", HoaDonId);
        return query.getResultList();
    }
    public List<HoaDonChiTietUserViewModel> findAll() {
        Session session = HibernateUtil.getFACTORY().openSession();
        Query query = session.createQuery("SELECT distinct new viewModel.HoaDonChiTietUserViewModel(hd.hoaDon.ma, hd.hoaDon.tenNguoiNhan, hd.hoaDon.sdt,hd.hoaDon.diaChi,hd.hoaDon.ngayTao,hd.hoaDon.ngayShip,hd.hoaDon.ngayNhan,hd.hoaDon.ngayThanhToan,hd.hoaDon.tinhTrang) FROM HoaDonChiTiet hd group by hd.hoaDon.ma, hd.hoaDon.tenNguoiNhan, hd.hoaDon.sdt,hd.hoaDon.diaChi,hd.hoaDon.ngayTao,hd.hoaDon.ngayShip,hd.hoaDon.ngayNhan,hd.hoaDon.ngayThanhToan,hd.hoaDon.tinhTrang order by hd.hoaDon.ma desc");
        return query.getResultList();
    }

    public List<HoaDonChiTietViewModel> getListByIdKH(UUID idKhachHang) {
        Session session = HibernateUtil.getFACTORY().openSession();
        Query query = session.createQuery("SELECT new viewModel.HoaDonChiTietViewModel(hd.hoaDon.ma, hd.hoaDon.tenNguoiNhan, hd.hoaDon.diaChi, hd.hoaDon.sdt, hd.hoaDon.tinhTrang,hd.chiTietSp.sanPham.ten,hd.chiTietSp.sanPham.srcImage,hd.soLuongTon,hd.donGia) FROM HoaDonChiTiet hd WHERE  hd.hoaDon.khachHang.id =: idKhachHang GROUP BY hd.hoaDon.ma, hd.hoaDon.tenNguoiNhan, hd.hoaDon.diaChi, hd.hoaDon.sdt, hd.hoaDon.tinhTrang,hd.chiTietSp.sanPham.ten,hd.chiTietSp.sanPham.srcImage,hd.soLuongTon,hd.donGia");
        query.setParameter("idKhachHang", idKhachHang);
        return query.getResultList();
    }
    public List<HoaDonChiTietViewModel> findByMaHD(String maHD) {
        Session session = HibernateUtil.getFACTORY().openSession();
        Query query = session.createQuery("SELECT new viewModel.HoaDonChiTietViewModel(hd.hoaDon.ma, hd.hoaDon.tenNguoiNhan, hd.hoaDon.diaChi, hd.hoaDon.sdt, hd.hoaDon.tinhTrang,hd.chiTietSp.sanPham.ten,hd.chiTietSp.sanPham.srcImage,hd.soLuongTon,hd.donGia) FROM HoaDonChiTiet hd WHERE  hd.hoaDon.ma =: maHD GROUP BY hd.hoaDon.ma, hd.hoaDon.tenNguoiNhan, hd.hoaDon.diaChi, hd.hoaDon.sdt, hd.hoaDon.tinhTrang,hd.chiTietSp.sanPham.ten,hd.chiTietSp.sanPham.srcImage,hd.soLuongTon,hd.donGia");
        query.setParameter("maHD", maHD);
        return query.getResultList();
    }
    public List<HoaDon> getList() {
        Session session = HibernateUtil.getFACTORY().openSession();
        Query query = session.createQuery("FROM HoaDon");
        return query.getResultList();
    }

public UUID getIDHoaDonByIdKH(UUID idKH) {
    Session session = HibernateUtil.getFACTORY().openSession();
    Query query = session.createQuery("SELECT hd.id FROM HoaDon hd WHERE hd.khachHang.id = :idKH order by hd.ma desc");
    query.setParameter("idKH", idKH);
    if (query.getResultList().isEmpty()) {
        return null;
    } else {
        return (UUID) query.getResultList().get(0);
    }
}
    public UUID insert(HoaDon hoaDon) {
        try (Session session = HibernateUtil.getFACTORY().openSession()) {
            Transaction transaction = session.beginTransaction();
            UUID id = (UUID) session.save(hoaDon);
            transaction.commit();
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private Date getDateNow() {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        return date;
    }
    public boolean updateTrangThaiHoaDon(String maHD, int tinhTrang) {
        try (Session session = HibernateUtil.getFACTORY().openSession()) {
            Transaction transaction = session.beginTransaction();
            String hql = "UPDATE HoaDon hd SET hd.tinhTrang = :tinhTrang,hd.ngayNhan=:ngayNhan,hd.ngayThanhToan=:ngayTT WHERE hd.ma = :maHD";
            Query query = session.createQuery(hql);
            query.setParameter("tinhTrang", tinhTrang);
            query.setParameter("ngayNhan", getDateNow());
            query.setParameter("ngayTT", getDateNow());
            query.setParameter("maHD", maHD);
            query.executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateHoaDonNgayShip(String maHD, int tinhTrang) {
        try (Session session = HibernateUtil.getFACTORY().openSession()) {
            Transaction transaction = session.beginTransaction();
            String hql = "UPDATE HoaDon hd SET hd.tinhTrang = :tinhTrang,hd.ngayShip=:ngayShip WHERE hd.ma = :maHD";
            Query query = session.createQuery(hql);
            query.setParameter("tinhTrang", tinhTrang);
            query.setParameter("ngayShip", getDateNow());
            query.setParameter("maHD", maHD);
            query.executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertHDCT(HoaDonChiTiet hoaDonChiTiet) {
        try (Session session = HibernateUtil.getFACTORY().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(hoaDonChiTiet);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public HoaDon findById(UUID id) {
        {
            return this.hSession.find(HoaDon.class, id);
        }
    }
}
