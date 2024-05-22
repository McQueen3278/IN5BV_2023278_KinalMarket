package org.harolrodriguez.controller;

import com.jfoenix.controls.JFXTimePicker;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalTime;
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
import org.harolrodriguez.bean.Cargos;
import org.harolrodriguez.bean.Empleados;
import org.harolrodriguez.db.Conexion;
import org.harolrodriguez.system.Main;

public class MenuEmpleadosController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Empleados> listaEmpleados;
    private ObservableList<Cargos> listaCargos;
    @FXML
    private TableView tblEmpleados;
    @FXML
    private TextField txtEmpleadoID;
    @FXML
    private TextField txtNombreE;
    @FXML
    private TextField txtApellidoE;
    @FXML
    private TextField txtSueldoE;
    @FXML
    private JFXTimePicker txtHoraEntradaE;
    @FXML
    private JFXTimePicker txtHoraSalidaE;
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
    @FXML
    private Button btnBack;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cmbCargoID.setItems(getCargos());
    }

    public void cargarDatos() {
        tblEmpleados.setItems(getEmpleados());
        colEmpleadoID.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("empleadoId"));
        colNombreE.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombreEmpleado"));
        colApellidoE.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidoEmpleado"));
        colSueldoE.setCellValueFactory(new PropertyValueFactory<Empleados, Double>("sueldo"));
        colHoraEntrada.setCellValueFactory(new PropertyValueFactory<Empleados, Time>("horaEntrada"));
        colHoraSalida.setCellValueFactory(new PropertyValueFactory<Empleados, Time>("horaSalida"));
        colCargoID.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("cargoId"));
    }

    public void seleccionarElementos() {
        txtEmpleadoID.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getEmpleadoId()));
        txtNombreE.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getNombreEmpleado());
        txtApellidoE.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getApellidoEmpleado());
        txtSueldoE.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
        cmbCargoID.getSelectionModel().select(buscarCargo(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCargoId()));

    }

    public Cargos buscarCargo(int cargoId) {
        Cargos resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarCargos(?)}");
            procedimiento.setInt(1, cargoId);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Cargos(registro.getInt("cargoId"),
                        registro.getString("nombreCargo"),
                        registro.getString("descripcionCargo")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;

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
                imgAgregar.setImage(new Image("/org/harolrodriguez/images/AgregarC.png"));
                imgEliminar.setImage(new Image("/org/harolrodriguez/images/EliminarC.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }

    }

    public void guardar() {
        Empleados registro = new Empleados();
        registro.setEmpleadoId(Integer.parseInt(txtEmpleadoID.getText()));
        registro.setCargoId(((Cargos) cmbCargoID.getSelectionModel().getSelectedItem()).getCargoId());
        registro.setNombreEmpleado(txtNombreE.getText());
        registro.setApellidoEmpleado(txtApellidoE.getText());
        registro.setSueldo(Double.parseDouble(txtSueldoE.getText()));

        LocalTime horaEntradaLocal = txtHoraEntradaE.getValue();
        LocalTime horaSalidaLocal = txtHoraSalidaE.getValue();

        if (horaEntradaLocal != null && horaSalidaLocal != null) {
            Time horaEntrada = Time.valueOf(horaEntradaLocal);
            Time horaSalida = Time.valueOf(horaSalidaLocal);
            registro.setHoraEntrada(horaEntrada);
            registro.setHoraSalida(horaSalida);
        } else {

            JOptionPane.showMessageDialog(null, "Debe seleccionar las horas de entrada y salida.");
            return;
        }

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarEmpleados(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getEmpleadoId());
            procedimiento.setString(2, registro.getNombreEmpleado());
            procedimiento.setString(3, registro.getApellidoEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setTime(5, registro.getHoraEntrada());
            procedimiento.setTime(6, registro.getHoraSalida());
            procedimiento.setInt(7, registro.getCargoId());
            procedimiento.execute();
            listaEmpleados.add(registro);
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
                imgAgregar.setImage(new Image("/org/harolrodriguez/images/AgregarC.png"));
                imgEliminar.setImage(new Image("/org/harolrodriguez/images/EliminarC.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarEmpleado(?) }");
                            procedimiento.setInt(1, ((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getEmpleadoId());
                            procedimiento.execute();
                            listaEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedItem());
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

                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/harolrodriguez/images/EditarC.png"));
                    imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                    activarControles();
                    txtEmpleadoID.setEditable(false);
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
                imgEditar.setImage(new Image("/org/harolrodriguez/images/AgregarC.png"));
                imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                desactivarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_editarEmpleado(?, ?, ?, ?, ?, ?, ?) }");
            Empleados registro = (Empleados) tblEmpleados.getSelectionModel().getSelectedItem();
            registro.setEmpleadoId(Integer.parseInt(txtEmpleadoID.getText()));
            registro.setCargoId(((Cargos) cmbCargoID.getSelectionModel().getSelectedItem()).getCargoId());
            registro.setNombreEmpleado(txtNombreE.getText());
            registro.setApellidoEmpleado(txtApellidoE.getText());
            registro.setSueldo(Double.parseDouble(txtSueldoE.getText()));

            // Obtener las horas de entrada y salida desde los JFXTimePicker
            LocalTime horaEntradaLocal = txtHoraEntradaE.getValue();
            LocalTime horaSalidaLocal = txtHoraSalidaE.getValue();

            // Verificar que no sean nulos antes de convertirlos a Time
            if (horaEntradaLocal != null && horaSalidaLocal != null) {
                Time horaEntrada = Time.valueOf(horaEntradaLocal);
                Time horaSalida = Time.valueOf(horaSalidaLocal);
                registro.setHoraEntrada(horaEntrada);
                registro.setHoraSalida(horaSalida);
            } else {
                // Manejar el caso donde los tiempos no están seleccionados
                JOptionPane.showMessageDialog(null, "Debe seleccionar las horas de entrada y salida.");
                return;
            }

            procedimiento.setInt(1, registro.getEmpleadoId());
            procedimiento.setString(2, registro.getNombreEmpleado());
            procedimiento.setString(3, registro.getApellidoEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setTime(5, registro.getHoraEntrada());
            procedimiento.setTime(6, registro.getHoraSalida());
            procedimiento.setInt(7, registro.getCargoId());
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
        txtEmpleadoID.setEditable(false);
        txtNombreE.setEditable(false);
        txtApellidoE.setEditable(false);
        txtSueldoE.setEditable(false);
        txtHoraEntradaE.setEditable(false);
        txtHoraSalidaE.setEditable(false);
        cmbCargoID.setEditable(true);
    }

    public void activarControles() {
        txtEmpleadoID.setEditable(true);
        txtNombreE.setEditable(true);
        txtApellidoE.setEditable(true);
        txtSueldoE.setEditable(true);
        txtHoraEntradaE.setEditable(true);
        txtHoraSalidaE.setEditable(true);
        cmbCargoID.setEditable(false);
    }

    public void limpiarControles() {
        txtEmpleadoID.clear();
        txtNombreE.clear();
        txtApellidoE.clear();
        txtSueldoE.clear();

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
