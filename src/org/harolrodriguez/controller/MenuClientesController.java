package org.harolrodriguez.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.harolrodriguez.bean.Clientes;
import org.harolrodriguez.db.Conexion;
import org.harolrodriguez.report.GenerarReporte;
import org.harolrodriguez.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuClientesController implements Initializable {

    private Main escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Clientes> listaClientes;
    @FXML private Button btnBack;
    @FXML private TextField txtDireccionC;
    @FXML private TextField txtCorreoC;
    @FXML private TextField txtCodigoC;
    @FXML private TextField txtNit;
    @FXML private TextField txtNombreC;
    @FXML private TextField txtApellidoC;
    @FXML private TextField txtTelefonoC;
    @FXML private TableView tblClientes;
    @FXML private TableColumn colCodigoC;
    @FXML private TableColumn colNombreC;
    @FXML private TableColumn colNITC;
    @FXML private TableColumn colApellidoC;
    @FXML private TableColumn colDireccionC;
    @FXML private TableColumn colTelefonoC;
    @FXML private TableColumn colCorreoC;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    @FXML private ImageView imgBack;


 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }   
    
    public void cargarDatos(){
        tblClientes.setItems(getClientes());
        colCodigoC.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("CodigoCliente"));
        colNITC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("NITcliente"));
        colNombreC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombreCliente"));
        colApellidoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidoCliente"));
        colDireccionC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccionCliente"));
        colTelefonoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefonoCliente"));
        colCorreoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correoCliente"));
    }
    
    
    public void selecdcionarElemento(){
        txtCodigoC.setText(String.valueOf(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        txtNit.setText((((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getNITcliente()));
        txtNombreC.setText((((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getNombreCliente()));
        txtApellidoC.setText((((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getApellidoCliente()));
        txtDireccionC.setText((((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente()));
        txtTelefonoC.setText((((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente()));
        txtCorreoC.setText((((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCorreoCliente()));
    }
    public ObservableList<Clientes> getClientes(){
        ArrayList<Clientes> lista = new ArrayList<>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Clientes(resultado.getInt("CodigoCliente"),
                        resultado.getString("NITcliente"),
                        resultado.getString("nombreCliente"),
                        resultado.getString("apellidoCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("correoCliente")
                        
                        ));
            }
        }catch (Exception e){
           e.printStackTrace();
        }
        
        
        return listaClientes = FXCollections.observableArrayList(lista);
    }
    
    public void Agregar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/harolrodriguez/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/harolrodriguez/images/Cancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                 guardar();
                 activarControles();
                 limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/harolrodriguez/images/AgregarC.png"));
                imgEliminar.setImage(new Image("/org/harolrodriguez/images/EliminarC.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
        
        
    }
    
    public void guardar(){
        Clientes registro = new Clientes();
        registro.setCodigoCliente(Integer.parseInt(txtCodigoC.getText()));
        registro.setNombreCliente(txtNombreC.getText());
        registro.setApellidoCliente(txtApellidoC.getText());
        registro.setNITcliente(txtNit.getText());
        registro.setTelefonoCliente(txtTelefonoC.getText());
        registro.setDireccionCliente(txtDireccionC.getText());
        registro.setCorreoCliente(txtCorreoC.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarCliente(?,?,?,?,?,?,?) }");
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(3,registro.getNombreCliente());
            procedimiento.setString(4, registro.getApellidoCliente());
            procedimiento.setString(2, registro.getNITcliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
            listaClientes.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
       switch(tipoDeOperaciones){
           case ACTUALIZAR:
               desactivarControles();
               limpiarControles();  
               btnAgregar.setText("Agregar");
               btnEliminar.setText("Eliminar");
               btnEditar.setDisable(false);
               btnReporte.setDisable(false);
               imgAgregar.setImage(new Image("/org/harolrodriguez/images/AgregarC.png"));
               imgEliminar.setImage(new Image("/org/harolrodriguez/images/EliminarC.png"));
               tipoDeOperaciones = operaciones.NINGUNO;
               break;
           default:
               if(tblClientes.getSelectionModel().getSelectedItem() != null){
                   int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Clientes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                   if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarClientes(?) }");
                            procedimiento.setInt(1,((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            procedimiento.execute();
                            listaClientes.remove(tblClientes.getSelectionModel().getSelectedItem());
                        }catch (Exception e){
                            e.printStackTrace();
                        }   
                   }
               }else{
                   JOptionPane.showMessageDialog(null, " Debe de Seleccionar un Elemento ");
               }
       } 
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                
                if(tblClientes.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/harolrodriguez/images/EditarC.png"));
                    imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                    activarControles();
                    txtCodigoC.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un Elemento");
                break;
            case ACTUALIZAR:
                
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reportes");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/harolrodriguez/images/AgregarC.png"));
                imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                desactivarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
        }        
    }
          
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCliente(?, ?, ?, ?, ?, ?, ?) }");
            Clientes registro = (Clientes) tblClientes.getSelectionModel().getSelectedItem();
            registro.setNITcliente(txtNit.getText());
            registro.setNombreCliente(txtNombreC.getText());
            registro.setApellidoCliente(txtApellidoC.getText());
            registro.setDireccionCliente(txtDireccionC.getText());
            registro.setTelefonoCliente(txtTelefonoC.getText());
            registro.setCorreoCliente(txtCorreoC.getText());
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNITcliente());
            procedimiento.setString(3, registro.getNombreCliente());
            procedimiento.setString(4, registro.getApellidoCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void reporte(){
        switch (tipoDeOperaciones){
            case NINGUNO:
                imprimirReporte();
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/harolrodriguez/images/EditarC.png"));
                imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }
    
    public void imprimirReporte(){
         Map parametros = new HashMap();
         parametros.put("CodigoCliente", null);
         GenerarReporte.mostrarReportes("ReporteClientes.jasper", "Reporte de Clientes", parametros);
        
    }
    
    public void desactivarControles(){
        txtCodigoC.setEditable(false);
        txtNombreC.setEditable(false);
        txtApellidoC.setEditable(false);
        txtDireccionC.setEditable(false);
        txtCorreoC.setEditable(false);
        txtNit.setEditable(false);
        txtTelefonoC.setEditable(false);
    }
    
     public void activarControles(){
        txtCodigoC.setEditable(true);
        txtNombreC.setEditable(true);
        txtApellidoC.setEditable(true);
        txtDireccionC.setEditable(true);
        txtCorreoC.setEditable(true);
        txtNit.setEditable(true);
        txtTelefonoC.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoC.clear();
        txtNombreC.clear();
        txtApellidoC.clear();
        txtDireccionC.clear();
        txtCorreoC.clear();
        txtNit.clear();
        txtTelefonoC.clear();
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void clickAtras (ActionEvent event) throws IOException{
        if(event.getSource() == btnBack){
            escenarioPrincipal.menuPrincipalView();
        }else if (event.getSource() == imgBack){
            escenarioPrincipal.menuPrincipalView();
        }
    }

    
    
}
