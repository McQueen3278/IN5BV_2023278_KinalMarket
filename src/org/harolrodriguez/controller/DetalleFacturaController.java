package org.harolrodriguez.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
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
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import org.harolrodriguez.bean.DetalleFactura;
import org.harolrodriguez.bean.Facturas;
import org.harolrodriguez.bean.Productos;
import org.harolrodriguez.bean.TipoProducto;
import org.harolrodriguez.db.Conexion;
import org.harolrodriguez.system.Main;

public class DetalleFacturaController implements Initializable {

    private Main escenarioPrincipal;
    @FXML
    private TextField txtDetalleFacturaID;
    @FXML
    private ComboBox cmbFactura;
    @FXML
    private ComboBox cmbProducto;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnBack;
    @FXML
    private ImageView imgBack;
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
    private TableView tblDeltalleFactura;
    @FXML
    private TableColumn colDetalleFactura;
    @FXML
    private TableColumn colFactura;
    @FXML
    private TableColumn colProducto;

    @FXML
    private void clickAtras(MouseEvent event) {
    }

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<DetalleFactura> listaDFactura;
    private ObservableList<Facturas> listaFacturas;
    private ObservableList<Productos> listaProducto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cmbFactura.setItems(getFacturas());
        cmbProducto.setItems(getProductos());
    }

    public void cargarDatos() {
        tblDeltalleFactura.setItems(getDetalleFactura());
        colDetalleFactura.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("detalleFacturaId"));
        colFactura.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("facturaId"));
        colProducto.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("productoId"));
    }

    @FXML
    public void seleccionarElemento() {
        txtDetalleFacturaID.setText(String.valueOf(((DetalleFactura) tblDeltalleFactura.getSelectionModel().getSelectedItem()).getDetalleFacturaId()));
    }

    public ObservableList<DetalleFactura> getDetalleFactura() {
        ArrayList<DetalleFactura> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarDetalleFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new DetalleFactura(resultado.getInt("detalleFacturaId"),
                        resultado.getInt("facturaId"),
                        resultado.getInt("productoId")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaDFactura = FXCollections.observableArrayList(lista);

    }

    public ObservableList<Facturas> getFacturas() {
        ArrayList<Facturas> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarFacturas()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                java.sql.Date fecha = resultado.getDate("facha");
                LocalDate fechaF = fecha.toLocalDate();
                lista.add(new Facturas(resultado.getInt("facturaId"),
                        resultado.getInt("CodigoCliente"),
                        resultado.getInt("empleadoId"),
                        fechaF,
                        resultado.getTime("hora"),
                        resultado.getDouble("total")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaFacturas = FXCollections.observableArrayList(lista);

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

    @FXML
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
                cargarDatos();
                break;
            case ACTUALIZAR:
                guardar();
                activarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/harolrodriguez/images/AgregarF.png"));
                imgEliminar.setImage(new Image("/org/harolrodriguez/images/EliminarF.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }

    }

    public void guardar() {
        DetalleFactura registro = new DetalleFactura();
        registro.setDetalleFacturaId(Integer.parseInt(txtDetalleFacturaID.getText()));
        registro.setFacturaId(((Facturas) cmbFactura.getSelectionModel().getSelectedItem()).getFacturaId());
        registro.setProductoId(((Productos) cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarDetalleFactura(?, ?, ?)}");
            procedimiento.setInt(1, registro.getDetalleFacturaId());
            procedimiento.setInt(2, registro.getFacturaId());
            procedimiento.setInt(3, registro.getProductoId());
            procedimiento.execute();
            listaDFactura.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                cargarDatos();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/harolrodriguez/images/AgregarF.png"));
                imgEliminar.setImage(new Image("/org/harolrodriguez/images/EliminarF.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                
                break;
            default:
                if (tblDeltalleFactura.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Detalle Factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarDetalleFactura(?) }");
                            procedimiento.setInt(1, ((DetalleFactura) tblDeltalleFactura.getSelectionModel().getSelectedItem()).getDetalleFacturaId());
                            procedimiento.execute();
                            listaProducto.remove(tblDeltalleFactura.getSelectionModel().getSelectedItem());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, " Debe de Seleccionar un Elemento ");
                }
        }
    }

    @FXML
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:

                if (tblDeltalleFactura.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/harolrodriguez/images/Guardar.png"));
                    imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                    activarControles();
                    txtDetalleFacturaID.setEditable(false);
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
                imgEditar.setImage(new Image("/org/harolrodriguez/images/EditarF.png"));
                imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                desactivarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_editarDetalleFactura(?, ?, ?) }");
            DetalleFactura registro = (DetalleFactura) tblDeltalleFactura.getSelectionModel().getSelectedItem();
            registro.setDetalleFacturaId(Integer.parseInt(txtDetalleFacturaID.getText()));
            registro.setFacturaId(((Facturas) cmbFactura.getSelectionModel().getSelectedItem()).getFacturaId());
            registro.setProductoId(((Productos) cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
            procedimiento.setInt(1, registro.getDetalleFacturaId());
            procedimiento.setInt(2, registro.getFacturaId());
            procedimiento.setInt(3, registro.getProductoId());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
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
        txtDetalleFacturaID.setEditable(false);
    }

    public void activarControles() {
        txtDetalleFacturaID.setEditable(true);
    }

    public void limpiarControles() {
        txtDetalleFacturaID.clear();
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
