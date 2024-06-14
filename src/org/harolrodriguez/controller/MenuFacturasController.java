package org.harolrodriguez.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.harolrodriguez.bean.Clientes;
import org.harolrodriguez.bean.Empleados;
import org.harolrodriguez.bean.Facturas;
import org.harolrodriguez.db.Conexion;
import org.harolrodriguez.report.GenerarReporte;
import org.harolrodriguez.system.Main;

/**
 * FXML Controller class
 *
 * @author Harol RC
 */
public class MenuFacturasController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Facturas> listaFacturas;
    private ObservableList<Empleados> listaEmpleados;
    private ObservableList<Clientes> listaClientes;
    @FXML
    private TableView tblFacturas;
    @FXML
    private TextField txtFacturaID;
    @FXML
    private TextField txtTotalF;
    @FXML
    private TextField txtApellidoC;
    @FXML
    private DatePicker dtpFechaF;
    @FXML
    private JFXTimePicker dtmHoraF;
    @FXML
    private ComboBox cmbCliente;
    @FXML
    private ComboBox cmbEmpleado;
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
    private TableColumn colFacturaID;
    @FXML
    private TableColumn colFecha;
    @FXML
    private TableColumn colHora;
    @FXML
    private TableColumn colTotal;
    @FXML
    private TableColumn colClientes;
    @FXML
    private TableColumn colEmpleados;
    @FXML
    private Button btnBack;
    @FXML
    private ImageView imgBack;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cmbCliente.setItems(getClientes());
        cmbEmpleado.setItems(getEmpleados());
    }

    public void cargarDatos() {
        tblFacturas.setItems(getFacturas());
        colFacturaID.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("facturaId"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Facturas, LocalDate>("facha"));
        colHora.setCellValueFactory(new PropertyValueFactory<Facturas, Time>("hora"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Facturas, Double>("total"));
        colClientes.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("CodigoCliente"));
        colEmpleados.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("empleadoId"));
    }

    public void seleccionarElemento() {
        txtFacturaID.setText(String.valueOf(((Facturas) tblFacturas.getSelectionModel().getSelectedItem()).getFacturaId()));
        txtTotalF.setText(String.valueOf(((Facturas) tblFacturas.getSelectionModel().getSelectedItem()).getTotal()));
        //cmbCliente.getSelectionModel().select(buscarCliente(((Facturas) tblFacturas.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        // cmbEmpleado.getSelectionModel().select(buscarEmpleado(((Facturas)tblFacturas.getSelectionModel().getSelectedItem()).getEmpleadoId()));
    }

    /* public Clientes buscarCliente(int codigoCliente) {
        Clientes resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarClientes(?)}");
            procedimiento.setInt(1, codigoCliente);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Clientes(
                        registro.getInt("CodigoCliente"),
                        registro.getString("NITCliente"),
                        registro.getString("nombreCliente"),
                        registro.getString("apellidoCliente"),
                        registro.getString("direccionCliente"),
                        registro.getString("telefonoCliente"),
                        registro.getString("correoCliente")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public Empleados buscarEmpleado(int empleadoId) {
        Empleados resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarEmpleados(?)}");
            procedimiento.setInt(1, empleadoId);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Empleados(registro.getInt("empleadoId"),
                        registro.getString("nombreEmpleado"),
                        registro.getString("apellidoEmpleado"),
                        registro.getDouble("sueldo"),
                        registro.getTime("horaEntrada"),
                        registro.getTime("horaSalida"),
                        registro.getInt("cargoId")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
     */
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

    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("CodigoCliente"),
                        resultado.getString("NITcliente"),
                        resultado.getString("nombreCliente"),
                        resultado.getString("apellidoCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("correoCliente")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaClientes = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Empleados> getEmpleados() {
        ArrayList<Empleados> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Empleados(
                        resultado.getInt("empleadoId"),
                        resultado.getString("nombreEmpleado"),
                        resultado.getString("apellidoEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getTime("horaEntrada"),
                        resultado.getTime("horaSalida"),
                        resultado.getInt("cargoId")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaEmpleados = FXCollections.observableArrayList(lista);

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
                break;
        }

    }

    public void guardar() {
        Facturas registro = new Facturas();
        registro.setFacturaId(Integer.parseInt(txtFacturaID.getText()));
        registro.setCodigoCliente(((Clientes) cmbCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());

        registro.setEmpleadoId(((Empleados) cmbEmpleado.getSelectionModel().getSelectedItem()).getEmpleadoId());
        registro.setTotal(Double.parseDouble(txtTotalF.getText()));
        LocalDate fechaSeleccionada = dtpFechaF.getValue();
        java.sql.Date fecha = java.sql.Date.valueOf(fechaSeleccionada);

        LocalTime horaSeleccionada = dtmHoraF.getValue();
        Time hora = java.sql.Time.valueOf(horaSeleccionada);
        registro.setHora(hora);
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarFactura(?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getFacturaId());
            procedimiento.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(fecha)));
            procedimiento.setTime(3, registro.getHora());
            procedimiento.setDouble(4, registro.getTotal());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getEmpleadoId());

            procedimiento.execute();
            listaFacturas.add(registro);
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
                imgAgregar.setImage(new Image("/org/harolrodriguez/images/AgregarF.png"));
                imgEliminar.setImage(new Image("/org/harolrodriguez/images/EliminarF.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblFacturas.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarFactura(?) }");
                            procedimiento.setInt(1, ((Facturas) tblFacturas.getSelectionModel().getSelectedItem()).getEmpleadoId());
                            procedimiento.execute();
                            listaEmpleados.remove(tblFacturas.getSelectionModel().getSelectedItem());
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

                if (tblFacturas.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/harolrodriguez/images/EditarF.png"));
                    imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                    activarControles();
                    txtFacturaID.setEditable(false);
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
                imgEditar.setImage(new Image("/org/harolrodriguez/images/AgregarF.png"));
                imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                desactivarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_editarFactura(?, ?, ?, ?, ?, ?) }");
            Facturas registro = (Facturas) tblFacturas.getSelectionModel().getSelectedItem();
            registro.setFacturaId(Integer.parseInt(txtFacturaID.getText()));
            LocalDate fechaSeleccionada = dtpFechaF.getValue();
            java.sql.Date fecha = java.sql.Date.valueOf(fechaSeleccionada);
             LocalTime horaSeleccionada = dtmHoraF.getValue();
            Time hora = java.sql.Time.valueOf(horaSeleccionada);
            registro.setHora(hora);
            registro.setTotal(Double.parseDouble(txtTotalF.getText()));
            registro.setCodigoCliente(((Clientes) cmbCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
            registro.setEmpleadoId(((Empleados) cmbEmpleado.getSelectionModel().getSelectedItem()).getEmpleadoId());
            procedimiento.setInt(1, registro.getFacturaId());
            procedimiento.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(fecha)));
            procedimiento.setTime(3, registro.getHora());
            procedimiento.setDouble(4, registro.getTotal());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getEmpleadoId());

            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reporte() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                imprimirReporte();
                        break;
                case ACTUALIZAR:
                    cargarDatos();

                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/harolrodriguez/images/EditarF.png"));
                imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void imprimirReporte(){
         Map parametros = new HashMap();
         int facID = ((Facturas)tblFacturas.getSelectionModel().getSelectedItem()).getFacturaId();
         parametros.put("facID", facID);
         GenerarReporte.mostrarReportes("Factura.jasper", "Facturas", parametros);
        
    }
    
    public void desactivarControles() {
        txtFacturaID.setEditable(false);
        txtTotalF.setEditable(false);
        txtApellidoC.setEditable(false);
        dtpFechaF.setEditable(false);
        dtmHoraF.setEditable(false);
        cmbCliente.setEditable(false);
        cmbEmpleado.setEditable(true);
    }

    public void activarControles() {
        txtFacturaID.setEditable(true);
        txtTotalF.setEditable(true);
        txtApellidoC.setEditable(true);
        dtpFechaF.setEditable(true);
        dtmHoraF.setEditable(true);
        cmbCliente.setDisable(false);
        cmbEmpleado.setDisable(false);
    }

    public void limpiarControles() {
        txtFacturaID.clear();
        txtTotalF.clear();
        txtApellidoC.clear();

    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void clickAtras(ActionEvent event) throws IOException {
        if (event.getSource() == btnBack) {
            escenarioPrincipal.menuPrincipalView();
        } else if (event.getSource() == imgBack) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}
