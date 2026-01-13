package org.example.repository;

import org.example.model.Movimentacao;
import org.example.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovimentacaoDAO {

    public Movimentacao insert(Movimentacao m) throws SQLException {
        String sql = "INSERT INTO movimentacao (conta_id, data, acao, valor, observacao) VALUES (?,?,?,?,?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, m.getContaId());
            ps.setDate(2, Date.valueOf(m.getData()));
            ps.setString(3, m.getAcao());
            ps.setBigDecimal(4, m.getValor());
            ps.setString(5, m.getObservacao());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) m.setId(rs.getInt(1));
            }
        }
        return m;
    }

    public List<Movimentacao> findAll() throws SQLException {
        String sql = "SELECT id, conta_id, data, acao, valor, observacao FROM movimentacao";
        List<Movimentacao> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Movimentacao m = new Movimentacao();
                m.setId(rs.getInt("id"));
                m.setContaId(rs.getInt("conta_id"));
                m.setData(rs.getDate("data").toLocalDate());
                m.setAcao(rs.getString("acao"));
                m.setValor(rs.getBigDecimal("valor"));
                m.setObservacao(rs.getString("observacao"));
                list.add(m);
            }
        }
        return list;
    }
}

