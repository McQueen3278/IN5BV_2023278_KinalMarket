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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.harolrodriguez.bean.Productos;
import org.harolrodriguez.bean.Proveedores;
import org.harolrodriguez.bean.TipoProducto;
import org.harolrodriguez.db.Conexion;
import org.harolrodriguez.system.Main;

/**
 * FXML Controller class
 *
 * @author Harol RC
 */
public class MenuProductoController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Productos> listaProducto;
    private ObservableList<Proveedores> listaProveedores;
    private ObservableList<TipoProducto> listaTipoProducto;
    @FXML
    private TableView tblProductos;
    @FXML
    private Button btnAgregar;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private Button btnEditar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private Button btnReporte;
    @FXML
    private ImageView imgReporte;
    @FXML
    private TextField txtPID;
    @FXML
    private TextField txtNombreP;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtCantidad;
    @FXML
    private TextField txtPrecioU;
    @FXML
    private TextField txtPrecioM;
    @FXML
    private TextField txtPrecioCompra;
    @FXML
    private ComboBox cmbProveedor;
    @FXML
    private ComboBox cmbTipoP;
    @FXML
    private TableColumn colProductoId;
    @FXML
    private TableColumn colNombreP;
    @FXML
    private TableColumn colDescripcionP;
    @FXML
    private TableColumn colCantidad;
    @FXML
    private TableColumn colPrecioU;
    @FXML
    private TableColumn colPrecioVM;
    @FXML
    private TableColumn colPrecioCompra;
    @FXML
    private TableColumn colCodigoP;
    @FXML
    private TableColumn colCodP;
    @FXML
    private Button btnBack;
    @FXML
    private ImageView imgBack;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargaDatos();
        cmbProveedor.setItems(getProveedores());
        cmbTipoP.setItems(getTipoProducto());

    }

    public void cargaDatos() {
        tblProductos.setItems(getProductos());
        colProductoId.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("productoId"));
        colNombreP.setCellValueFactory(new PropertyValueFactory<Productos, String>("nombreProducto"));
        colDescripcionP.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionProducto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("cantidadStock"));
        colPrecioU.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioVentaUnitario"));
        colPrecioVM.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioVentaMayor"));
        colPrecioCompra.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioCompra"));
        colCodigoP.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoProveedor"));
        colCodP.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("categoriaProductoId"));
    }
    
    public void seleccionarElemento(){
        txtPID.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getProductoId()));
        txtNombreP.setText((((Productos)tblProductos.getSelectionModel().getSelectedItem()).getNombreProducto()));
        txtDescripcion.setText(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getDescripcionProducto());
        txtCantidad.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCantidadStock()));
        txtPrecioU.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getPrecioVentaUnitario()));
        txtPrecioM.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getPrecioVentaMayor()));
        txtPrecioCompra.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getPrecioCompra()));
    }
    
    public ObservableList<Productos> getProductos() {
        ArrayList<Productos> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Productos(resultado.getInt("productoId"),
                        resultado.getString("nombreProducto"),
                        resultado.getString("descripcionProducto"),
                        resultado.getInt("cantidadStock"),
                        resultado.getDouble("precioVentaUnitario"),
                        resultado.getDouble("precioVentaMayor"),
                        resultado.getDouble("precioCompra"),
                        resultado.getInt("codigoProveedor"),
                        resultado.getInt("categoriaProductoId")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProducto = FXCollections.observableArrayList(lista);

    }

    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProveedores = FXCollections.observableArrayList(lista);

    }

    public ObservableList<TipoProducto> getTipoProducto() {
        ArrayList<TipoProducto> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCategorias()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TipoProducto(resultado.getInt("categoriaProductoId"),
                        resultado.getString("nombreCategoria"),
                        resultado.getString("descripcionCategoria")
                ));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaTipoProducto = FXCollections.observableArrayList(lista);
    }

    public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/harolrodriguez/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/harolrodriguez/images/Cancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                cargaDatos();
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
                cargaDatos();
                break;
        }

    }

    public void guardar() {
        Productos registro = new Productos();
        registro.setProductoId(Integer.parseInt(txtPID.getText()));
        registro.setCodigoProveedor(((Proveedores) cmbProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        registro.setCategoriaProductoId(((TipoProducto) cmbTipoP.getSelectionModel().getSelectedItem()).getCategoriaProductoId());
        registro.setNombreProducto(txtNombreP.getText());
        registro.setDescripcionProducto(txtDescripcion.getText());
        registro.setCantidadStock(Integer.parseInt(txtCantidad.getText()));
        registro.setPrecioVentaUnitario(Double.parseDouble(txtPrecioU.getText()));
        registro.setPrecioVentaMayor(Double.parseDouble(txtPrecioM.getText()));
        registro.setPrecioCompra(Double.parseDouble(txtPrecioCompra.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarProducto(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getProductoId());
            procedimiento.setString(2, registro.getNombreProducto());
            procedimiento.setString(3, registro.getDescripcionProducto());
            procedimiento.setInt(4, registro.getCantidadStock());
            procedimiento.setDouble(5, registro.getPrecioVentaUnitario());
            procedimiento.setDouble(6, registro.getPrecioVentaMayor());
            procedimiento.setDouble(7, registro.getPrecioCompra());
            procedimiento.setInt(8, registro.getCodigoProveedor());
            procedimiento.setInt(9, registro.getCategoriaProductoId());
            procedimiento.execute();
            listaProducto.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/harolrodriguez/images/CarritoC.png"));
                imgEliminar.setImage(new Image("/org/harolrodriguez/images/EliminarCarrito.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarEmpleado(?) }");
                            procedimiento.setInt(1, ((Productos) tblProductos.getSelectionModel().getSelectedItem()).getProductoId());
                            procedimiento.execute();
                            listaProducto.remove(tblProductos.getSelectionModel().getSelectedItem());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, " Debe de Seleccionar un Elemento ");
                }
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:

                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/harolrodriguez/images/Guardar.png"));
                    imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                    activarControles();
                    txtPID.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un Elemento");
                }
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
                cargaDatos();
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_editarProducto(?, ?, ?, ?, ?, ?, ?, ?, ?) }");
            Productos registro = (Productos) tblProductos.getSelectionModel().getSelectedItem();
            registro.setProductoId(Integer.parseInt(txtPID.getText()));
            registro.setCodigoProveedor(((Proveedores) cmbProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            registro.setCategoriaProductoId(((TipoProducto) cmbTipoP.getSelectionModel().getSelectedItem()).getCategoriaProductoId());
            registro.setNombreProducto(txtNombreP.getText());
            registro.setDescripcionProducto(txtDescripcion.getText());
            registro.setCantidadStock(Integer.parseInt(txtCantidad.getText()));
            registro.setPrecioVentaUnitario(Double.parseDouble(txtPrecioU.getText()));
            registro.setPrecioVentaMayor(Double.parseDouble(txtPrecioM.getText()));
            registro.setPrecioCompra(Double.parseDouble(txtPrecioCompra.getText()));
            procedimiento.setInt(1, registro.getProductoId());
            procedimiento.setString(2, registro.getNombreProducto());
            procedimiento.setString(3, registro.getDescripcionProducto());
            procedimiento.setInt(4, registro.getCantidadStock());
            procedimiento.setDouble(5, registro.getPrecioVentaUnitario());
            procedimiento.setDouble(6, registro.getPrecioVentaMayor());
            procedimiento.setDouble(7, registro.getPrecioCompra());
            procedimiento.setInt(8, registro.getCodigoProveedor());
            procedimiento.setInt(9, registro.getCategoriaProductoId());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void reporte() {
        switch (tipoDeOperaciones) {
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


    public void desactivarControles() {
        txtPID.setEditable(false);
        txtNombreP.setEditable(false);
        txtDescripcion.setEditable(false);
        txtCantidad.setEditable(false);
        txtPrecioU.setEditable(false);
        txtPrecioM.setEditable(false);
        txtPrecioCompra.setEditable(false);
    }

    public void activarControles() {
        txtPID.setEditable(true);
        txtNombreP.setEditable(true);
        txtDescripcion.setEditable(true);
        txtCantidad.setEditable(true);
        txtPrecioU.setEditable(true);
        txtPrecioM.setEditable(true);
        txtPrecioCompra.setEditable(true);
    }

    public void limpiarControles() {
        txtPID.clear();
        txtNombreP.clear();
        txtDescripcion.clear();
        txtCantidad.clear();
        txtPrecioU.clear();
        txtPrecioM.clear();
        txtPrecioCompra.clear();
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void clickAtras(ActionEvent event) throws IOException {
        if (event.getSource() == btnBack) {
            escenarioPrincipal.menuPrincipalView();
        } else if (event.getSource() == imgBack) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}
