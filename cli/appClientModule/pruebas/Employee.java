package pruebas;

import java.util.Date;

public class Employee {
	public String employeeId;
	public String employeeName;
	public Date fechaNacimiento;
	public Double salario;
	
	public Double getSalario() {
		return salario;
	}
	public void setSalario(Double salario) {
		this.salario = salario;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public Employee(String employeeId, String employeeName, Date fechaNacimiento, Double salario) {
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.fechaNacimiento = fechaNacimiento;
		this.salario = salario;
	}
	
	public Employee(){
		
	}
	
	
}
