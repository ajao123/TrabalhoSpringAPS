package com.allissonjardel.projeto.entities.enums;



public enum StatusConsulta {

	MARCADA(1),
	REALIZADA(2),
	CANCELADA(3),
	INDEFERIDA(4);
	
	
	
	private Integer code;
	
	private StatusConsulta(int code) {
		this.code = code;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public static StatusConsulta valueOf(Integer code) {
		
		for(StatusConsulta value : StatusConsulta.values()) {
			if(code == value.getCode()) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid Status Consulta code");
	}
}
