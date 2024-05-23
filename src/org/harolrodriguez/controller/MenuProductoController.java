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
import javafx.scene.image.ImageView;
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
        colNombreP.setCellValueFactory(new PropertyValueFactory<Productos, String >("nombreProducto"));
        colDescripcionP.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionProducto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("cantidadStock"));
        colPrecioU.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioVentaUnitario"));
        colPrecioVM.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioVentaMayor"));
        colPrecioCompra.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioCompra"));
        colCodigoP.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoProveedor"));
        colCodP.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("categoriaProductoId"));
    }

    public ObservableList<Productos> getProductos() {
        ArrayList<Productos> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
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

        return listaProducto  = FXCollections.observableArrayList(lista);

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
