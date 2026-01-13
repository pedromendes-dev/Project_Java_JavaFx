package org.example.repository;

import org.example.model.Conta;
import org.example.util.DBUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContaDAO {

    public Conta insert(Conta c) throws SQLException {
        String sql = "INSERT INTO contas (descricao, valor, tipo, cliente_id) VALUES (?,?,?,?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getDescricao());
            ps.setBigDecimal(2, c.getValor());
            ps.setString(3, c.getTipo());
            if (c.getClienteId() != null) ps.setInt(4, c.getClienteId()); else ps.setNull(4, Types.INTEGER);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getInt(1));
            }
        }
        return c;
    }

    public void update(Conta c) throws SQLException {
        String sql = "UPDATE contas SET descricao=?, valor=?, tipo=?, cliente_id=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getDescricao());
            ps.setBigDecimal(2, c.getValor());
            ps.setString(3, c.getTipo());
            if (c.getClienteId() != null) ps.setInt(4, c.getClienteId()); else ps.setNull(4, Types.INTEGER);
            ps.setInt(5, c.getId());
            ps.executeUpdate();
        }
    }

    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM contas WHERE id=?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Conta findById(Integer id) throws SQLException {
        String sql = "SELECT id, descricao, valor, tipo, cliente_id FROM contas WHERE id=?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public List<Conta> findAll() throws SQLException {
        String sql = "SELECT id, descricao, valor, tipo, cliente_id FROM contas";
        List<Conta> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    private Conta mapRow(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String desc = rs.getString("descricao");
        BigDecimal valor = rs.getBigDecimal("valor");
        String tipo = rs.getString("tipo");
        Integer clienteId = rs.getObject("cliente_id") != null ? rs.getInt("cliente_id") : null;
        return new Conta(id, desc, valor, tipo, clienteId);
    }
}

