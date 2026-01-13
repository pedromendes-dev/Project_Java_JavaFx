package org.example.repository;

import org.example.model.Cliente;
import org.example.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public Cliente insert(Cliente c) throws SQLException {
        String sql = "INSERT INTO clientes (nome, documento, email) VALUES (?,?,?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getDocumento());
            ps.setString(3, c.getEmail());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    c.setId(rs.getInt(1));
                }
            }
        }
        return c;
    }

    public void update(Cliente c) throws SQLException {
        String sql = "UPDATE clientes SET nome=?, documento=?, email=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getDocumento());
            ps.setString(3, c.getEmail());
            ps.setInt(4, c.getId());
            ps.executeUpdate();
        }
    }

    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id=?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Cliente findById(Integer id) throws SQLException {
        String sql = "SELECT id, nome, documento, email FROM clientes WHERE id=?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(rs.getInt("id"), rs.getString("nome"), rs.getString("documento"), rs.getString("email"));
                }
            }
        }
        return null;
    }

    public List<Cliente> findAll() throws SQLException {
        String sql = "SELECT id, nome, documento, email FROM clientes";
        List<Cliente> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Cliente(rs.getInt("id"), rs.getString("nome"), rs.getString("documento"), rs.getString("email")));
            }
        }
        return list;
    }
}

