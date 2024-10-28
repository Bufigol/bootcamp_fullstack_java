package com.bufigol.configuracion;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.naming.Name;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {
    private DatabaseConnection instanciaDePrueba;

    @BeforeEach
    void setUp() {
        this.instanciaDePrueba = DatabaseConnection.getInstance();
    }

    @AfterEach
    void tearDown() {
        this.instanciaDePrueba.closeConnection();
    }

    @Test
    @DisplayName("Pruebas de la instancia de la base de datos")
    void getInstance() {
        DatabaseConnection instancia = DatabaseConnection.getInstance();
        assertNotEquals(null, instancia);
        assertEquals(instancia, DatabaseConnection.getInstance());
        assertEquals(this.instanciaDePrueba, DatabaseConnection.getInstance());
    }

    @Test
    @DisplayName("Pruebas de la conexion a la base de datos")
    void getConnection() {
        try {
            DatabaseConnection instancia = DatabaseConnection.getInstance();
            assertNotNull(this.instanciaDePrueba.getConnection());
            assertEquals(this.instanciaDePrueba.getConnection(), this.instanciaDePrueba.getConnection());
            assertNotNull(instancia.getConnection());
            assertEquals(instancia.getConnection(), instancia.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Pruebas de cierre la base de datos")
    void closeConnection() {

    }
}