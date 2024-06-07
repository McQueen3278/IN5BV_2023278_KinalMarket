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
import org.harolrodriguez.bean.Proveedores;
import org.harolrodriguez.db.Conexion;
import org.harolrodriguez.report.GenerarReporte;
import org.harolrodriguez.system.Main;


public class MenuProveedoresController implements Initializable {
    
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private Main escenarioPrincipal;
    private ObservableList<Proveedores> listaProveedores;
    @FXML private Button btnBack;
    @FXML private ImageView imgBack;
    @FXML private TextField txtcodigoP;
    @FXML private TextField txtNIT;
    @FXML private TextField txtnombbreP;
    @FXML private TextField txtapellidoP;
    @FXML private TextField txtdireccionP;
    @FXML private TextField txtrazonSocial;
    @FXML private TextField txtcontactoPrincipal;
    @FXML private TextField txtpaginaWeb;
    @FXML private TableView tblProveedores;
    @FXML private TableColumn colcodigoP;
    @FXML private TableColumn colNIT;
    @FXML private TableColumn colnombbreP;
    @FXML private TableColumn colapellidoP;
    @FXML private TableColumn coldireccionP;
    @FXML private TableColumn colrazonSocial;
    @FXML private TableColumn colcontactoPrincipal;
    @FXML private TableColumn colpaginaWeb;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        
    }

    
    public void cargarDatos(){
     tblProveedores.setItems(getProveedores());
     colcodigoP.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
     colNIT.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("NITproveedor"));
     colnombbreP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombreProveedor"));
     colapellidoP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("apellidoProveedor"));
     coldireccionP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
     colrazonSocial.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("razonSocial"));
     colcontactoPrincipal.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contactoPrincipal"));
     colpaginaWeb.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));
    }
    
    public void selecdcionarElemento(){
        txtcodigoP.setText(String.valueOf(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        txtNIT.setText((((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getNITproveedor()));
        txtnombbreP.setText((((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getNombreProveedor()));
        txtapellidoP.setText((((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getApellidoProveedor()));
        txtdireccionP.setText((((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor()));
        txtrazonSocial.setText((((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getRazonSocial()));
        txtcontactoPrincipal.setText((((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getContactoPrincipal()));
        txtpaginaWeb.setText((((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getPaginaWeb()));
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
        Proveedores registro = new Proveedores();
        registro.setCodigoProveedor(Integer.parseInt(txtcodigoP.getText()));
        registro.setNITproveedor(txtNIT.getText());
        registro.setNombreProveedor(txtnombbreP.getText());
        registro.setApellidoProveedor(txtapellidoP.getText());
        registro.setDireccionProveedor(txtdireccionP.getText());
        registro.setRazonSocial(txtrazonSocial.getText());
        registro.setContactoPrincipal(txtcontactoPrincipal.getText());
        registro.setPaginaWeb(txtpaginaWeb.getText());
        try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProveedores(?,?,?,?,?,?,?,?) }"); 
           procedimiento.setInt(1, registro.getCodigoProveedor());
           procedimiento.setString(2, registro.getNITproveedor());
           procedimiento.setString(3, registro.getNombreProveedor());
           procedimiento.setString(4, registro.getApellidoProveedor());
           procedimiento.setString(5, registro.getDireccionProveedor());
           procedimiento.setString(6, registro.getRazonSocial());
           procedimiento.setString(7, registro.getContactoPrincipal());
           procedimiento.setString(8, registro.getPaginaWeb());
           procedimiento.execute();           
           listaProveedores.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public ObservableList<Proveedores> getProveedores(){
        ArrayList<Proveedores> lista = new ArrayList<>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("NITproveedor"),
                        resultado.getString("nombreProveedor"),
                        resultado.getString("apellidoProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")
                
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaProveedores = FXCollections.observableArrayList(lista);
        
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
               if(tblProveedores.getSelectionModel().getSelectedItem() != null){
                   int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Proveedores", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                   if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProveedor(?) }");
                            procedimiento.setInt(1,((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            listaProveedores.remove(tblProveedores.getSelectionModel().getSelectedItem());
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
                
                if(tblProveedores.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/harolrodriguez/images/Guardar.png"));
                    imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                    activarControles();
                    txtcodigoP.setEditable(false);
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
                imgEditar.setImage(new Image("/org/harolrodriguez/images/EditarC.png"));
                imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                desactivarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
        }        
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarProveedor(?, ?, ?, ?, ?, ?, ?, ?) }");
            Proveedores registro = (Proveedores) tblProveedores.getSelectionModel().getSelectedItem();
                registro.setNITproveedor(txtNIT.getText());
                registro.setNombreProveedor(txtnombbreP.getText());
                registro.setApellidoProveedor(txtapellidoP.getText());
                registro.setDireccionProveedor(txtdireccionP.getText());
                registro.setRazonSocial(txtrazonSocial.getText());
                registro.setContactoPrincipal(txtcontactoPrincipal.getText());
                registro.setPaginaWeb(txtpaginaWeb.getText());
                procedimiento.setInt(1, registro.getCodigoProveedor());
                procedimiento.setString(2, registro.getNITproveedor());
                procedimiento.setString(3, registro.getNombreProveedor());
                procedimiento.setString(4, registro.getApellidoProveedor());
                procedimiento.setString(5, registro.getDireccionProveedor());
                procedimiento.setString(6, registro.getRazonSocial());
                procedimiento.setString(7, registro.getContactoPrincipal());
                procedimiento.setString(8, registro.getPaginaWeb());
                procedimiento.execute();           
        }catch(Exception e){
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
         parametros.put("codigoProveedor", null);
         GenerarReporte.mostrarReportes("ReporteProveedores.jasper", "Reporte de Producto", parametros);
     }
     
    
    public void desactivarControles(){
        txtcodigoP.setEditable(false);
        txtNIT.setEditable(false);
        txtnombbreP.setEditable(false);
        txtapellidoP.setEditable(false);
        txtdireccionP.setEditable(false);
        txtrazonSocial.setEditable(false);
        txtcontactoPrincipal.setEditable(false);
        txtpaginaWeb.setEditable(false);
    }
    
     public void activarControles(){
        txtcodigoP.setEditable(true);
        txtNIT.setEditable(true);
        txtnombbreP.setEditable(true);
        txtapellidoP.setEditable(true);
        txtdireccionP.setEditable(true);
        txtrazonSocial.setEditable(true);
        txtcontactoPrincipal.setEditable(true);
        txtpaginaWeb.setEditable(true);
    }
    
    public void limpiarControles(){
        txtcodigoP.clear();
        txtNIT.clear();
        txtnombbreP.clear();
        txtapellidoP.clear();
        txtdireccionP.clear();
        txtrazonSocial.clear();
        txtcontactoPrincipal.clear();
        txtpaginaWeb.clear();
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
