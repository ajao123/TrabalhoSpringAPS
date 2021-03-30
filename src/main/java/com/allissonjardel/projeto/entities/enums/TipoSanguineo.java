package com.allissonjardel.projeto.entities.enums;



public enum TipoSanguineo {

	A_POSITIVO(1),
	A_NEGATIVO(2),
	B_POSITIVO(3),
	B_NEGATIVO(4),
	AB_POSITIVO(5),
	AB_NEGATIVO(6),
	O_POSITIVO(7),
	O_NEGATIVO(8);
	
	
	private Integer code;
	
	private TipoSanguineo(int code) {
		this.code = code;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public static TipoSanguineo valueOf(Integer code) {
		
		for(TipoSanguineo value : TipoSanguineo.values()) {
			if(code == value.getCode()) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid Tipo Sanguineo code");
	}
}
