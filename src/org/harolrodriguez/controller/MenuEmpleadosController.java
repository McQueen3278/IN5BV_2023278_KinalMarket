package org.harolrodriguez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.harolrodriguez.bean.Cargos;
import org.harolrodriguez.bean.Empleados;
import org.harolrodriguez.db.Conexion;
import org.harolrodriguez.system.Main;


public class MenuEmpleadosController implements Initializable {
    private Main escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList <Empleados> listaEmpleados;
    private ObservableList <Cargos> listaCargos;
    @FXML 
    private TableView tblEmpleados;
    @FXML
    private TextField txtEmpleadoID;
    @FXML 
    private TextField txrNombreE;
    @FXML
    private TextField txtApellidoE;
    @FXML
    private TextField txtSueldoE;
    @FXML
    private TextField txtHoraEntradaE;
    @FXML
    private TextField txtHoraSalidaE;
    @FXML
    private TextField txtEncargadoID;
    @FXML
    private ComboBox cmbCargoID;
    @FXML
    private TableColumn colEmpleadoID;
    @FXML
    private TableColumn colNombreE;
    @FXML 
    private TableColumn colApellidoE;
    @FXML 
    private TableColumn colSueldoE;
    @FXML 
    private TableColumn colHoraEntrada;
    @FXML
    private TableColumn colHoraSalida;
    @FXML
    private TableColumn colCargoID;
    @FXML
    private TableColumn colEncargadoID;
    @FXML
    private Button btnAgregar;
    @FXML 
    private Button btnEliminar;
    @FXML 
    private Button btnEditar;
    @FXML 
    private Button btnReporte;
    @FXML 
    private ImageView imgAgregar;
    @FXML 
    private ImageView imgEliminar;
    @FXML 
    private ImageView imgEditar;
    @FXML 
    private ImageView imgReporte;
    @FXML 
    private ImageView imgBack;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }    

    public void cargarDatos(){
        
    }
    
    public ObservableList<Empleados> getEmpleado(){
        ArrayList<Empleados> lista = new ArrayList<Empleados>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Empleados (resultado.getInt("empleadoId"),
                        resultado.getString("nombreEmpleado"),
                        resultado.getString("apellidoEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getTime("horaEntrada"),
                        resultado.getTime("horaSalida"),
                        resultado.getInt("cargoId"),
                        resultado.getInt("encargadoId")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaEmpleados = FXCollections.observableArrayList(lista);
        
    }
    
    public ObservableList<Cargos> getCargos() {
        ArrayList<Cargos> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarCargos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Cargos(resultado.getInt("cargoId"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaCargos = FXCollections.observableArrayList(lista);

    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
}
