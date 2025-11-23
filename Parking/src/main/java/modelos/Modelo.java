package modelos;

import conn.conector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import modelos.clases.*;

public class Modelo {

    private conector connector;

    public Modelo() {
        connector = new conector();
    }

    // Método para verificar login
    public String verificarLogin(String matricula) throws Exception {
        String sql2 = "SELECT tipo_cli, cuota_pagada FROM cliente WHERE matricula = ?";
        try (Connection con = connector.con(); PreparedStatement pstmt = con.prepareStatement(sql2)) {
            pstmt.setString(1, matricula);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                boolean cuotaPagada = rs.getBoolean("cuota_pagada");
                String tipoCliente = rs.getString("tipo_cli");

                if (!cuotaPagada) {
                    return "CUOTA_PENDIENTE";
                }
                return tipoCliente;
            } else {
                return "NO_EXISTE";
            }
        }
    }

    // Método para registrar nuevo cliente
    public boolean registrarCliente(String nombre, String apellidos, String matricula, String tipoCliente) throws Exception {
        int id_cliente = -1;
        String sql1 = "SELECT id_cliente FROM cliente order by id_cliente desc limit 1";
        try (Connection con = connector.con(); PreparedStatement pstmt = con.prepareStatement(sql1)) {
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                id_cliente = rs.getInt(1) + 1;
            }
        }
        String sql = "INSERT INTO cliente (id_cliente, nombre, apellidos, matricula, tipo_cli, cuota_pagada) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = connector.con(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id_cliente);
            pstmt.setString(2, nombre);
            pstmt.setString(3, apellidos);
            pstmt.setString(4, matricula);
            pstmt.setString(5, tipoCliente);
            pstmt.setBoolean(6, true);

            return pstmt.executeUpdate() > 0;
        }
    }

    // Método para obtener plazas disponibles según tipo de cliente
    public List<plaza> obtenerPlazasDisponibles(boolean esVip) throws Exception {
        List<plaza> plazas = new ArrayList<>();
        String sql = "SELECT p.* FROM plaza p WHERE p.disponible = true AND p.es_vip = ?";

        try (Connection con = connector.con(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setBoolean(1, esVip);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                plaza pl = new plaza();
                pl.setIdPlaza(rs.getInt("id_plaza"));
                pl.setIdPlanta(rs.getInt("id_planta"));
                pl.setNumeroPlaza(rs.getInt("numero_plaza"));
                pl.setDisponible(rs.getBoolean("disponible"));
                pl.setEsVip(rs.getBoolean("es_vip"));
                plazas.add(pl);
            }
        }
        return plazas;
    }

    // Método para hacer reserva
    public boolean hacerReserva(int idCliente, java.util.Date fechaRes, java.sql.Time horaIni,
            java.sql.Time horaFin, boolean esVip, List<Integer> servicios) throws Exception {
        Connection con = null;
        try {
            con = connector.con();
            con.setAutoCommit(false);

            // Obtener plazas disponibles
            List<plaza> plazasDisponibles = obtenerPlazasDisponibles(esVip);
            if (plazasDisponibles.isEmpty()) {
                return false;
            }

            // Seleccionar plaza aleatoria
            Random rand = new Random();
            plaza plazaSeleccionada = plazasDisponibles.get(rand.nextInt(plazasDisponibles.size()));

            // Conseguir ID de la reserva
            int id_reserva = -1;
            String sql1 = "SELECT id_reserva FROM reserva order by id_reserva desc limit 1";
            try (PreparedStatement pstmt = con.prepareStatement(sql1)) {
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    id_reserva = rs.getInt(1) + 1;
                }
            }

            // Insertar reserva
            String sqlReserva = "INSERT INTO reserva (id_reserva, id_plaza, id_cliente, fecha_res, hora_ini, hora_fin, estado) VALUES (?, ?, ?, ?, ?, ?, 'ACTIVA')";
            PreparedStatement pstmtReserva = con.prepareStatement(sqlReserva, Statement.RETURN_GENERATED_KEYS);
            pstmtReserva.setInt(1, id_reserva);
            pstmtReserva.setInt(2, plazaSeleccionada.getIdPlaza());
            pstmtReserva.setInt(3, idCliente);
            pstmtReserva.setDate(4, new java.sql.Date(fechaRes.getTime()));
            pstmtReserva.setTime(5, horaIni);
            pstmtReserva.setTime(6, horaFin);

            int affectedRows = pstmtReserva.executeUpdate();
            if (affectedRows == 0) {
                con.rollback();
                return false;
            }

            // Insertar servicios VIP si los hay
            if (esVip && servicios != null && !servicios.isEmpty()) {
                String sqlServicio = "INSERT INTO reserva_servicio (id_reserva, id_servicio) VALUES (?, ?)";
                PreparedStatement pstmtServicio = con.prepareStatement(sqlServicio);

                for (Integer idServicio : servicios) {
                    pstmtServicio.setInt(1, id_reserva);
                    pstmtServicio.setInt(2, idServicio);
                    pstmtServicio.addBatch();
                }
                pstmtServicio.executeBatch();
            }

            // Actualizar plaza como no disponible
            String sqlUpdatePlaza = "UPDATE plaza SET disponible = false WHERE id_plaza = ?";
            PreparedStatement pstmtUpdate = con.prepareStatement(sqlUpdatePlaza);
            pstmtUpdate.setInt(1, plazaSeleccionada.getIdPlaza());
            pstmtUpdate.executeUpdate();

            con.commit();
            return true;

        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }

    // Método para obtener servicios VIP
    public List<serviciovip> obtenerServiciosVip() throws Exception {
        List<serviciovip> servicios = new ArrayList<>();
        String sql = "SELECT * FROM serviciovip";

        try (Connection conn = connector.con(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                serviciovip sv = new serviciovip();
                sv.setIdServicio(rs.getInt("id_servicio"));
                sv.setNombreServ(rs.getString("nombre_serv"));
                sv.setDescr(rs.getString("descr"));
                sv.setPrecio(rs.getBigDecimal("precio"));
                servicios.add(sv);
            }
        }
        return servicios;
    }

    // Método para obtener ID del cliente por matrícula
    public int obtenerIdCliente(String matricula) throws Exception {
        String sql = "SELECT id_cliente FROM cliente WHERE matricula = ?";
        try (Connection con = connector.con(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, matricula);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_cliente");
            }
            return -1;
        }
    }

    // Método para obtener reservas del cliente
    public List<reserva> obtenerReservasCliente(int idCliente) throws Exception {
        List<reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reserva WHERE id_cliente = ? ORDER BY fecha_res DESC, hora_ini DESC";

        try (Connection con = connector.con(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                reserva r = new reserva();
                r.setIdReserva(rs.getInt("id_reserva"));
                r.setIdPlaza(rs.getInt("id_plaza"));
                r.setIdCliente(rs.getInt("id_cliente"));
                r.setFechaRes(rs.getDate("fecha_res"));
                r.setHoraIni(rs.getTime("hora_ini"));
                r.setHoraFin(rs.getTime("hora_fin"));
                r.setEstado(rs.getString("estado"));
                reservas.add(r);
            }
        }
        return reservas;
    }

    // Método para obtener reservas activas del cliente
    public List<reserva> obtenerReservasActivas(int idCliente) throws Exception {
        List<reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reserva WHERE id_cliente = ? AND estado = 'ACTIVA' ORDER BY fecha_res, hora_ini";

        try (Connection con = connector.con(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                reserva r = new reserva();
                r.setIdReserva(rs.getInt("id_reserva"));
                r.setIdPlaza(rs.getInt("id_plaza"));
                r.setIdCliente(rs.getInt("id_cliente"));
                r.setFechaRes(rs.getDate("fecha_res"));
                r.setHoraIni(rs.getTime("hora_ini"));
                r.setHoraFin(rs.getTime("hora_fin"));
                r.setEstado(rs.getString("estado"));
                reservas.add(r);
            }
        }
        return reservas;
    }

    // Método para cancelar reserva normal
    public boolean cancelarReserva(int idReserva) throws Exception {
        Connection con = null;
        try {
            con = connector.con();
            con.setAutoCommit(false);

            // Obtener id_plaza de la reserva
            String sqlGetPlaza = "SELECT id_plaza FROM reserva WHERE id_reserva = ?";
            int idPlaza = -1;
            try (PreparedStatement pstmt = con.prepareStatement(sqlGetPlaza)) {
                pstmt.setInt(1, idReserva);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    idPlaza = rs.getInt("id_plaza");
                }
            }

            // Actualizar estado de la reserva
            String sqlUpdateReserva = "UPDATE reserva SET estado = 'CANCELADA' WHERE id_reserva = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sqlUpdateReserva)) {
                pstmt.setInt(1, idReserva);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows == 0) {
                    con.rollback();
                    return false;
                }
            }

            // Liberar la plaza
            if (idPlaza != -1) {
                String sqlUpdatePlaza = "UPDATE plaza SET disponible = true WHERE id_plaza = ?";
                try (PreparedStatement pstmt = con.prepareStatement(sqlUpdatePlaza)) {
                    pstmt.setInt(1, idPlaza);
                    pstmt.executeUpdate();
                }
            }

            con.commit();
            return true;

        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }

    // Método para cancelar reserva VIP
    public boolean cancelarReservaVip(int idReserva) throws Exception {
        Connection con = null;
        try {
            con = connector.con();
            con.setAutoCommit(false);

            // Obtener id_plaza de la reserva
            String sqlGetPlaza = "SELECT id_plaza FROM reserva WHERE id_reserva = ?";
            int idPlaza = -1;
            try (PreparedStatement pstmt = con.prepareStatement(sqlGetPlaza)) {
                pstmt.setInt(1, idReserva);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    idPlaza = rs.getInt("id_plaza");
                }
            }

            // Eliminar servicios asociados
            String sqlDeleteServicios = "DELETE FROM reserva_servicio WHERE id_reserva = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sqlDeleteServicios)) {
                pstmt.setInt(1, idReserva);
                pstmt.executeUpdate();
            }

            // Actualizar estado de la reserva
            String sqlUpdateReserva = "UPDATE reserva SET estado = 'CANCELADA' WHERE id_reserva = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sqlUpdateReserva)) {
                pstmt.setInt(1, idReserva);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows == 0) {
                    con.rollback();
                    return false;
                }
            }

            // Liberar la plaza
            if (idPlaza != -1) {
                String sqlUpdatePlaza = "UPDATE plaza SET disponible = true WHERE id_plaza = ?";
                try (PreparedStatement pstmt = con.prepareStatement(sqlUpdatePlaza)) {
                    pstmt.setInt(1, idPlaza);
                    pstmt.executeUpdate();
                }
            }

            con.commit();
            return true;

        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }

    // Método para obtener servicios de una reserva
    public List<String> obtenerServiciosReserva(int idReserva) throws Exception {
        List<String> servicios = new ArrayList<>();
        String sql = "SELECT sv.nombre_serv FROM serviciovip sv "
                + "JOIN reserva_servicio rs ON sv.id_servicio = rs.id_servicio "
                + "WHERE rs.id_reserva = ?";

        try (Connection con = connector.con(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idReserva);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                servicios.add(rs.getString("nombre_serv"));
            }
        }
        return servicios;
    }

    // Método para verificar estado de cuota
    public boolean verificarEstadoCuota(int idCliente) throws Exception {
        String sql = "SELECT cuota_pagada FROM cliente WHERE id_cliente = ?";
        try (Connection con = connector.con(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("cuota_pagada");
            }
            return false;
        }
    }
}
