package org.harolrodriguez.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import org.harolrodriguez.bean.TipoProducto;
import org.harolrodriguez.db.Conexion;
import org.harolrodriguez.system.Main;


public class MenuTipoProductoController implements Initializable {

    private Main escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<TipoProducto> listaTipoProducto;
    @FXML private Button btnBack;
    @FXML private ImageView imgBack;
    @FXML private TextField txtCategoriaPID;
    @FXML private TextField txtNombreCa;
    @FXML private TextField txtDireccionCa;
    @FXML private TableView tblTipoProducto;
    @FXML private TableColumn colCategoriaPID;
    @FXML private TableColumn colNombreCa;
    @FXML private TableColumn colDireccionCa;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }    
    
    
    public void cargarDatos(){
       tblTipoProducto.setItems(getTipoProducto());
       colCategoriaPID.setCellValueFactory(new PropertyValueFactory<TipoProducto, Integer>("categoriaProductoId"));
       colNombreCa.setCellValueFactory(new PropertyValueFactory<TipoProducto, String>("nombreCategoria"));
       colDireccionCa.setCellValueFactory(new PropertyValueFactory<TipoProducto, String>("descripcionCategoria"));
       
        
    }
    
    public void seleccionarElementos(){
       txtCategoriaPID.setText(String.valueOf(((TipoProducto)tblTipoProducto.getSelectionModel().getSelectedItem()).getCategoriaProductoId()));
       txtNombreCa.setText(((TipoProducto)tblTipoProducto.getSelectionModel().getSelectedItem()).getNombreCategoria());
       txtDireccionCa.setText(((TipoProducto)tblTipoProducto.getSelectionModel().getSelectedItem()).getDescripcionCategoria());
       
    }
    
    public ObservableList<TipoProducto> getTipoProducto(){
        ArrayList<TipoProducto> lista = new ArrayList<>();
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCategorias()}");
                ResultSet resultado = procedimiento.executeQuery();
                while(resultado.next()){
                    lista.add(new TipoProducto(resultado.getInt("categoriaProductoId"),
                            resultado.getString("nombreCategoria"),
                            resultado.getString("descripcionCategoria")
                    ));
                    
                    
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        
        
        return listaTipoProducto = FXCollections.observableArrayList(lista);
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
                imgAgregar.setImage(new Image("/org/harolrodriguez/images/CarritoC.png"));
                imgEliminar.setImage(new Image("/org/harolrodriguez/images/EliminarCarrito.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
        
        
    }
     
    public void guardar(){
        TipoProducto registro = new TipoProducto();
        registro.setCategoriaProductoId(Integer.parseInt(txtCategoriaPID.getText()));
        registro.setNombreCategoria(txtNombreCa.getText());
        registro.setDescripcionCategoria(txtDireccionCa.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarCategoriaProductos(?, ?, ?) }");
            procedimiento.setInt(1, registro.getCategoriaProductoId()); 
            procedimiento.setString(2, registro.getNombreCategoria());
            procedimiento.setString(3, registro.getDescripcionCategoria());
            procedimiento.execute();
            listaTipoProducto.add(registro);
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
               imgAgregar.setImage(new Image("/org/harolrodriguez/CarritoC.png"));
               imgEliminar.setImage(new Image("/org/harolrodriguez/images/EliminarCarrito.png"));
               tipoDeOperaciones = operaciones.NINGUNO;
               break;
           default:
               if(tblTipoProducto.getSelectionModel().getSelectedItem() != null){
                   int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Tipo de Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                   if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ElimiarCategoria(?) }");
                            procedimiento.setInt(1,((TipoProducto)tblTipoProducto.getSelectionModel().getSelectedItem()).getCategoriaProductoId());
                            procedimiento.execute();
                            listaTipoProducto.remove(tblTipoProducto.getSelectionModel().getSelectedItem());
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
                
                if(tblTipoProducto.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/harolrodriguez/images/Guardar.png"));
                    imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                    activarControles();
                    txtCategoriaPID.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_editarCategoria(?, ?, ?) }");
            TipoProducto registro = (TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem();
            registro.setCategoriaProductoId(Integer.parseInt(txtCategoriaPID.getText()));
            registro.setNombreCategoria(txtNombreCa.getText());
            registro.setDescripcionCategoria(txtDireccionCa.getText());
            procedimiento.setInt(1, registro.getCategoriaProductoId());
            procedimiento.setString(2, registro.getNombreCategoria());
            procedimiento.setString(3, registro.getDescripcionCategoria());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void reporte(){
        switch (tipoDeOperaciones){
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
    
    public void desactivarControles(){
        txtCategoriaPID.setEditable(false);
        txtNombreCa.setEditable(false);
        txtDireccionCa.setEditable(false);
        
    }
    
     public void activarControles(){
        txtCategoriaPID.setEditable(true);
        txtNombreCa.setEditable(true);
        txtDireccionCa.setEditable(true);
        
    }
    
    public void limpiarControles(){
        txtCategoriaPID.clear();
        txtNombreCa.clear();
        txtDireccionCa.clear();
        
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
