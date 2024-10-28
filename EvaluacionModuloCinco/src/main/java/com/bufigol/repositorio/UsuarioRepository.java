package com.bufigol.repositorio;

import com.bufigol.configuracion.DatabaseConnection;
import com.bufigol.constantes.Constantes;
import com.bufigol.interfaces.repositorios.INT_UsuarioRepository;
import com.bufigol.modelo.Horoscopo;
import com.bufigol.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepository implements INT_UsuarioRepository {
    @Override
    public List<Usuario> listarUsuarios() {
        ArrayList<Usuario> out = new ArrayList<>();
        DatabaseConnection db = DatabaseConnection.getInstance();
        try {
            PreparedStatement pstm = db.getConnection().prepareStatement(Constantes.BUSQUEDA_COMPLETA_USUARIOS_ORDENADA);
            ResultSet rs = pstm.executeQuery();
            if( rs.getFetchSize() == 0 ) {
                pstm.close();
                throw new RuntimeException("No hay usuarios registrados");
            }
            while (rs.next()) {
                Usuario usuario = extraerUsuarioDeResultSet(rs);
                out.add(usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL",e);
        } finally {
            db.closeConnection();
        }
        return out;
    }



    @Override
    public void insertarUsuario(Usuario usuario) {
        DatabaseConnection db = DatabaseConnection.getInstance();
        try {
            PreparedStatement pstm = db.getConnection().prepareStatement(Constantes.INSERTAR_USUARIO);
            //INSERT INTO usuarios (nombre, username, email, fecha_nacimiento, password, animal)  VALUES (?, ?, ?, ?, ?, ?);
            pstm.setString(1, usuario.getNombre());
            pstm.setString(2, usuario.getUserName());
            pstm.setString(3, usuario.getEmail());
            pstm.setDate(4, usuario.getFechaNacimiento());
            pstm.setString(5, usuario.getPassword());
            pstm.setInt(6, usuario.getHoroscopo().getId());
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL",e);
        } finally {
            db.closeConnection();
        }
    }

    @Override
    public Optional<Usuario> buscarUsuario(int id) {
        DatabaseConnection db = DatabaseConnection.getInstance();
        try {
            PreparedStatement pstm = db.getConnection().prepareStatement(Constantes.BUSCAR_USUARIO_POR_ID);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Usuario out = extraerUsuarioDeResultSet(rs);
                pstm.close();
                return Optional.of(out);
            }else{
                pstm.close();
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el usuario por ID", e);
        } finally {
            db.closeConnection();
        }
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        DatabaseConnection db = DatabaseConnection.getInstance();
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement pstm = conn.prepareStatement(Constantes.ACTUALIZAR_USUARIO);
            pstm.setString(1, usuario.getNombre());
            pstm.setString(2, usuario.getUserName());
            pstm.setString(3, usuario.getEmail());
            pstm.setDate(4, usuario.getFechaNacimiento());
            pstm.setString(5, usuario.getPassword());
            pstm.setInt(6, usuario.getHoroscopo().getId());
            pstm.setInt(7, usuario.getId());
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el usuario", e);
        }
    }

    @Override
    public void eliminarUsuario(int id) {
        DatabaseConnection db = DatabaseConnection.getInstance();
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement pstm = conn.prepareStatement(Constantes.ELIMINAR_USUARIO);
            pstm.setInt(1, id);
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el usuario", e);
        }
    }

    private Usuario extraerUsuarioDeResultSet(ResultSet rs) throws SQLException {
        Usuario out = new Usuario();
        out.setId(rs.getInt("id"));
        out.setNombre(rs.getString("nombre"));
        out.setUserName(rs.getString("username"));
        out.setEmail(rs.getString("email"));
        out.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
        out.setPassword(rs.getString("password"));
        HoroscopoRepository horoscopoRepository = new HoroscopoRepository();
        Optional<Horoscopo> horoscopo = horoscopoRepository.buscarHoroscopo(rs.getInt("horoscopo_id"));
        horoscopo.ifPresent(out::setHoroscopo);
        return out;
    }
}
